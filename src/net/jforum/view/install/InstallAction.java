/*
 * Copyright (c) 2003, 2004 Rafael Steil
 * All rights reserved.

 * Redistribution and use in source and binary forms, 
 * with or without modification, are permitted provided 
 * that the following conditions are met:

 * 1) Redistributions of source code must retain the above 
 * copyright notice, this list of conditions and the 
 * following  disclaimer.
 * 2)  Redistributions in binary form must reproduce the 
 * above copyright notice, this list of conditions and 
 * the following disclaimer in the documentation and/or 
 * other materials provided with the distribution.
 * 3) Neither the name of "Rafael Steil" nor 
 * the names of its contributors may be used to endorse 
 * or promote products derived from this software without 
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT 
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, 
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL 
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE 
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 * 
 * This file creation date: 27/08/2004 - 18:15:54
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.install;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

import net.jforum.Command;
import net.jforum.InstallServlet;
import net.jforum.SimpleConnection;
import net.jforum.util.I18n;
import net.jforum.util.MD5;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.log4j.Logger;

import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: InstallAction.java,v 1.1 2004/08/30 23:51:21 rafaelsteil Exp $
 */
public class InstallAction extends Command
{
	private static Logger logger = Logger.getLogger(InstallAction.class);
	
	public void welcome() throws Exception
	{
		InstallServlet.getContext().put("moduleAction", "default/install.htm");
	}
	
	public void doInstall() throws Exception
	{
		Connection conn = null;
		
		if (!"passed".equals(InstallServlet.getRequest().getParameter("configureDatabase"))) {
			conn = this.configureDatabase();
			if (conn == null) {
				InstallServlet.getContext().put("error", I18n.getMessage("Install.databaseError"));
				this.welcome();
				return;
			}
		}
		
		InstallServlet.getContext().put("configureDatabase", "passed");
		
		SimpleConnection simpleConnection = new SimpleConnection();
		if (conn == null) {
			conn = simpleConnection.getConnection();
		}
		
		if (!"passed".equals(InstallServlet.getRequest().getParameter("createTables")) && !this.createTables(conn)) {
			InstallServlet.getContext().put("error", I18n.getMessage("Install.createTablesError"));
			simpleConnection.releaseConnection(conn);
			this.welcome();
			return;
		}
		
		InstallServlet.getContext().put("createTables", "passed");
		
		if (!"passed".equals(InstallServlet.getRequest().getParameter("importTablesData")) && !this.importTablesData(conn)) {
			InstallServlet.getContext().put("error", I18n.getMessage("Install.importTablesDataError"));
			simpleConnection.releaseConnection(conn);
			this.welcome();
			return;
		}
		
		InstallServlet.getContext().put("importTablesData", "passed");
		
		this.configureSystemGlobals();

		InstallServlet.setRedirect(InstallServlet.getRequest().getContextPath() + "/install/install.page?module=install&action=finished");
	}
	
	public void finished() throws Exception
	{
		InstallServlet.getContext().put("moduleAction", "default/install_finished.htm");
	}
	
	private void configureSystemGlobals() throws Exception
	{
		SystemGlobals.setValue(ConfigKeys.USER_HASH_SEQUENCE, MD5.crypt(InstallServlet.getRequest().getParameter("dbpasswd")
				+ System.currentTimeMillis()));

		SystemGlobals.setValue(ConfigKeys.FORUM_LINK, InstallServlet.getRequest().getParameter("server_name")
				+ ":"
				+ InstallServlet.getRequest().getParameter("server_port")
				+ InstallServlet.getRequest().getParameter("context_path"));
		
		SystemGlobals.setValue(ConfigKeys.I18N_DEFAULT, InstallServlet.getRequest().getParameter("language"));
		SystemGlobals.setValue(ConfigKeys.INSTALLED, "true");
		SystemGlobals.saveInstallation();
	}
	
	private boolean importTablesData(Connection conn) throws Exception
	{
		boolean status = true;
		boolean autoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		
		String dbType = InstallServlet.getRequest().getParameter("database");
		
		Properties queries = this.loadProperties(SystemGlobals.getApplicationPath() + "install/" + dbType +"_dump.dat");
		for (Enumeration e = queries.keys(); e.hasMoreElements(); ) {
			String key = (String)e.nextElement();
			String query = queries.getProperty(key);
			
			Statement s = conn.createStatement();
			
			try {
				s.executeUpdate(query);
			}
			catch (SQLException ex) {
				status = false;
				conn.rollback();
				logger.error("Error importing data for " + key + ": " + ex.getMessage());
				break;
			}
			finally {
				s.close();
			}
		}
		
		conn.setAutoCommit(autoCommit);
		return status;
	}
	
	private Properties loadProperties(String filename) throws IOException
	{
		Properties p = new Properties();
		FileInputStream inputStream = new FileInputStream(filename);
		p.load(inputStream);
		inputStream.close();
		
		return p;
	}
	
	private boolean createTables(Connection conn) throws Exception
	{
		String dbType = InstallServlet.getRequest().getParameter("database");
		boolean status = true;
		boolean autoCommit = conn.getAutoCommit();
		
		Properties statements = this.loadProperties(SystemGlobals.getApplicationPath() + "install/" + dbType + "_structure.dat");
		
		conn.setAutoCommit(false);
		for (Enumeration e = statements.keys(); e.hasMoreElements(); ) {
			String key = (String)e.nextElement();
			String query = statements.getProperty(key);
			
			Statement s = conn.createStatement();
			
			try {
				s.executeUpdate(query);
			}
			catch (SQLException ex) {
				status = false;
				conn.rollback();
				
				logger.error("Error creating table " + key + ": " + ex.getMessage());
				
				break;
			}
			finally {
				s.close();
			}
		}
		
		conn.setAutoCommit(autoCommit);
		return status;
	}
	
	private Connection configureDatabase() throws Exception
	{
		String username = InstallServlet.getRequest().getParameter("dbuser");
		String password = InstallServlet.getRequest().getParameter("dbpasswd");
		String dbName = InstallServlet.getRequest().getParameter("dbname");
		String host = InstallServlet.getRequest().getParameter("dbhost");
		String type = InstallServlet.getRequest().getParameter("database");
		String encoding = InstallServlet.getRequest().getParameter("dbencoding");

		String implementation = "yes".equals(InstallServlet.getRequest().getParameter("use_pool")) 
			? "net.jforum.PooledConnection"
			: "net.jforum.SimpleConnection";
		
		SystemGlobals.setValue(ConfigKeys.DATABASE_DRIVER_NAME, type);
		SystemGlobals.setValue(ConfigKeys.DATABASE_CONNECTION_IMPLEMENTATION, implementation);
		
		Properties p = new Properties();
		p.load(new FileInputStream(SystemGlobals.getValue(ConfigKeys.DATABASE_DRIVER_CONFIG)));
		
		p.setProperty(ConfigKeys.DATABASE_CONNECTION_HOST, host);
		p.setProperty(ConfigKeys.DATABASE_CONNECTION_USERNAME, username);
		p.setProperty(ConfigKeys.DATABASE_CONNECTION_PASSWORD, password);
		p.setProperty(ConfigKeys.DATABASE_CONNECTION_DBNAME, dbName);
		p.setProperty(ConfigKeys.DATABASE_CONNECTION_ENCODING, encoding);
		
		p.store(new FileOutputStream(SystemGlobals.getValue(ConfigKeys.DATABASE_DRIVER_CONFIG)), null);
		
		Connection conn = null;
		
		try {
			SimpleConnection s = new SimpleConnection();
			s.init();
			
			conn = s.getConnection();
		}
		catch (Exception e) {
			return null;
		}
		
		return conn;
	}
	
	/** 
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception
	{
		this.welcome();
	}
	
	/** 
	 * @see net.jforum.Command#process()
	 */
	public Template process() throws Exception 
	{
		this.setTemplateName("default/empty.htm");
		return super.process();
	}
}
