/*
 * Copyright (c) 2003, 2004 Rafael Steil
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, 
 * with or without modification, are permitted provided 
 * that the following conditions are met:
 * 
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import net.jforum.Command;
import net.jforum.DBConnection;
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
 * @version $Id: InstallAction.java,v 1.7 2004/10/30 03:30:09 rafaelsteil Exp $
 */
public class InstallAction extends Command
{
	private static Logger logger = Logger.getLogger(InstallAction.class);
	
	public void welcome() throws Exception
	{
		InstallServlet.getContext().put("language", this.getFromSession("language"));
		InstallServlet.getContext().put("database", this.getFromSession("database"));
		InstallServlet.getContext().put("dbhost", this.getFromSession("dbHost"));
		InstallServlet.getContext().put("dbuser", this.getFromSession("dbUser"));
		InstallServlet.getContext().put("dbname", this.getFromSession("dbName"));
		InstallServlet.getContext().put("dbpasswd", this.getFromSession("dbPassword"));
		InstallServlet.getContext().put("dbencoding", this.getFromSession("dbEncoding"));
		InstallServlet.getContext().put("use_pool", this.getFromSession("usePool"));
		InstallServlet.getContext().put("forum_link", this.getFromSession("forumLink"));
		
		InstallServlet.getContext().put("moduleAction", "install.htm");
	}
	
	private String getFromSession(String key)
	{
		return (String)InstallServlet.getRequest().getSession().getAttribute(key);
	}
	
	private void error()
	{
		InstallServlet.getContext().put("moduleAction", "install_error.htm");
	}
	
	public void doInstall() throws Exception
	{
		Connection conn = null;
		
		if (!this.checkForWritableDir()) {
			return;
		}
		
		if (!"passed".equals(this.getFromSession("configureDatabase"))) {
			logger.info("Going to configure the database...");
			conn = this.configureDatabase();
			if (conn == null) {
				InstallServlet.getContext().put("message", I18n.getMessage("Install.databaseError"));
				this.error();
				return;
			}
		}
		
		logger.info("Database configuration ok");

		// Database Configuration is ok
		this.addToSessionAndContext("configureDatabase", "passed");
		
		DBConnection simpleConnection = new SimpleConnection();
		if (conn == null) {
			conn = simpleConnection.getConnection();
		}
		
		if (!"passed".equals(this.getFromSession("createTables")) && !this.createTables(conn)) {
			InstallServlet.getContext().put("message", I18n.getMessage("Install.createTablesError"));
			simpleConnection.releaseConnection(conn);
			this.error();
			return;
		}
		
		// Create tables is ok
		this.addToSessionAndContext("createTables", "passed");
		logger.info("Table creation is ok");
		
		if (!"passed".equals(this.getFromSession("importTablesData")) && !this.importTablesData(conn)) {
			InstallServlet.getContext().put("message", I18n.getMessage("Install.importTablesDataError"));
			simpleConnection.releaseConnection(conn);
			this.error();
			return;
		}
		
		// Dump is ok
		this.addToSessionAndContext("importTablesData", "passed");
		
		this.configureSystemGlobals();
		
		if (!this.updateAdminPassword()) {
			InstallServlet.getContext().put("message", I18n.getMessage("Install.updateAdminError"));
			simpleConnection.releaseConnection(conn);
			this.error();
			return;
		}

		InstallServlet.setRedirect(InstallServlet.getRequest().getContextPath() + "/install/install"
				+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION)
				+ "?module=install&action=finished");
	}
	
	public void finished() throws Exception
	{
		InstallServlet.getContext().put("clickHere", I18n.getMessage("Install.clickHere", 
				new String[] { this.getFromSession("forumLink") }));
		InstallServlet.getContext().put("moduleAction", "install_finished.htm");
	}
	
	private void configureSystemGlobals() throws Exception
	{
		SystemGlobals.setValue(ConfigKeys.USER_HASH_SEQUENCE, MD5.crypt(this.getFromSession("dbPassword")
				+ System.currentTimeMillis()));

		SystemGlobals.setValue(ConfigKeys.FORUM_LINK, this.getFromSession("forumLink"));
		
		SystemGlobals.setValue(ConfigKeys.I18N_DEFAULT, this.getFromSession("language"));
		SystemGlobals.setValue(ConfigKeys.INSTALLED, "true");
		SystemGlobals.saveInstallation();
	}
	
	private boolean importTablesData(Connection conn) throws Exception
	{
		boolean status = true;
		boolean autoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		
		String dbType = this.getFromSession("database");
		
		List statements = this.readFromDat(SystemGlobals.getApplicationPath() + "/install/" + dbType +"_dump.dat");
		for (Iterator iter = statements.iterator(); iter.hasNext();) {
			String query = (String)iter.next();
			
			Statement s = conn.createStatement();
			
			try {
				s.executeUpdate(query);
			}
			catch (SQLException ex) {
				status = false;
				conn.rollback();
				logger.error("Error importing data for " + query + ": " + ex);
				InstallServlet.getContext().put("exceptionMessage", ex.getMessage() + "\n" + query);
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
		logger.info("Going to create tables...");

		String dbType = this.getFromSession("database");
		boolean status = true;
		boolean autoCommit = conn.getAutoCommit();
		
		List statements = this.readFromDat(SystemGlobals.getApplicationPath() + "/install/" + dbType + "_structure.dat");
		
		conn.setAutoCommit(false);
		for (Iterator iter = statements.iterator(); iter.hasNext(); ) {
			String query = (String)iter.next();
			
			Statement s = conn.createStatement();
			
			try {
				s.executeUpdate(query);
			}
			catch (SQLException ex) {
				status = false;
				conn.rollback();
				
				logger.error("Error executing query: " + query + ": " + ex);
				InstallServlet.getContext().put("exceptionMessage", ex.getMessage() + "\n" + query);
				
				break;
			}
			finally {
				s.close();
			}
		}
		
		conn.setAutoCommit(autoCommit);
		return status;
	}
	
	private boolean checkForWritableDir()
	{
		File f = new File(SystemGlobals.getValue(ConfigKeys.CONFIG_DIR));
		if (!f.canWrite()) {
			InstallServlet.getContext().put("message", I18n.getMessage("Install.noWebInfWritePermission"));
			this.error();
			return false;
		}
		
		return true;
	}
	
	private Connection configureDatabase() throws Exception
	{
		String username = this.getFromSession("dbUser");
		String password = this.getFromSession("dbPassword");
		String dbName = this.getFromSession("dbName");
		String host = this.getFromSession("dbHost");
		String type = this.getFromSession("database");
		String encoding = this.getFromSession("dbEncoding");

		String implementation = "yes".equals(this.getFromSession("usePool")) 
			? "net.jforum.PooledConnection"
			: "net.jforum.SimpleConnection";
		
		Properties p = new Properties();
		p.load(new FileInputStream(SystemGlobals.getValue(ConfigKeys.DATABASE_DRIVER_CONFIG)));
		
		p.setProperty(ConfigKeys.DATABASE_CONNECTION_HOST, host);
		p.setProperty(ConfigKeys.DATABASE_CONNECTION_USERNAME, username);
		p.setProperty(ConfigKeys.DATABASE_CONNECTION_PASSWORD, password);
		p.setProperty(ConfigKeys.DATABASE_CONNECTION_DBNAME, dbName);
		p.setProperty(ConfigKeys.DATABASE_CONNECTION_ENCODING, encoding);
		
		p.store(new FileOutputStream(SystemGlobals.getValue(ConfigKeys.DATABASE_DRIVER_CONFIG)), null);
		
		this.restartSystemGlobals();
		
		Connection conn = null;
		
		try {
			DBConnection s = new SimpleConnection();
			s.init();
			
			conn = s.getConnection();
		}
		catch (Exception e) {
			logger.warn("Error while trying to get a connection: " + e);
			InstallServlet.getContext().put("exceptionMessage", e.getMessage());
			return null;
		}
		
		return conn;
	}
	
	private boolean updateAdminPassword() throws Exception
	{
		logger.info("Going to update the administrator's password");
		
		boolean status = false;
		
		Connection conn  = null;
		try {
			conn = DBConnection.getImplementation().getConnection();
			PreparedStatement p = conn.prepareStatement("UPDATE jforum_users SET user_password = ? WHERE username = 'Admin'");
			p.setString(1, MD5.crypt(this.getFromSession("adminPassword")));
			p.executeUpdate();
			p.close();
			DBConnection.getImplementation().releaseConnection(conn);
			
			status = true;
		}
		catch (Exception e) {
			logger.warn("Error while trying to update the administrator's password: " + e);
			InstallServlet.getContext().put("exceptionMessage", e.getMessage());
		}
		finally {
			if (conn != null) {
				try { DBConnection.getImplementation().releaseConnection(conn); }
				catch (Exception ex) {};
			}
		}
		
		return status;
	}
	
	private void restartSystemGlobals() throws Exception
	{
		String appPath = SystemGlobals.getApplicationPath();
		SystemGlobals.initGlobals(appPath, appPath + "/WEB-INF/config/SystemGlobals.properties", null);
        SystemGlobals.loadAdditionalDefaults(SystemGlobals.getValue(ConfigKeys.DATABASE_DRIVER_CONFIG));
	}
	
	public void checkInformation() throws Exception
	{
		String language = InstallServlet.getRequest().getParameter("language");
		String database = InstallServlet.getRequest().getParameter("database");
		String dbHost = InstallServlet.getRequest().getParameter("dbhost");
		String dbUser = InstallServlet.getRequest().getParameter("dbuser");
		String dbName = InstallServlet.getRequest().getParameter("dbname");
		String dbPassword = InstallServlet.getRequest().getParameter("dbpasswd");
		String dbEncoding = InstallServlet.getRequest().getParameter("dbencoding");
		String dbEncodingOther = InstallServlet.getRequest().getParameter("dbencoding_other");
		String usePool = InstallServlet.getRequest().getParameter("use_pool");
		String forumLink = InstallServlet.getRequest().getParameter("forum_link");
		String adminPassword = InstallServlet.getRequest().getParameter("admin_pass1");
		
		dbHost = this.notNullDefault(dbHost, "localhost");
		dbEncodingOther = this.notNullDefault(dbEncodingOther, "UTF-8");
		dbEncoding = this.notNullDefault(dbEncoding, dbEncodingOther);
		forumLink = this.notNullDefault(forumLink, "http://localhost");
		dbName = this.notNullDefault(dbName, "jforum");
		
		if ("hsqldb".equals(database)) {
			dbUser = this.notNullDefault(dbUser, "sa");
		}
		
		this.addToSessionAndContext("language", language);
		this.addToSessionAndContext("database", database);
		this.addToSessionAndContext("dbHost", dbHost);
		this.addToSessionAndContext("dbUser", dbUser);
		this.addToSessionAndContext("dbName", dbName);
		this.addToSessionAndContext("dbPassword", dbPassword);
		this.addToSessionAndContext("dbEncoding", dbEncoding);
		this.addToSessionAndContext("usePool", usePool);
		this.addToSessionAndContext("forumLink", forumLink);
		this.addToSessionAndContext("adminPassword", adminPassword);
		
		this.addToSessionAndContext("configureDatabase", null);
		this.addToSessionAndContext("createTables", null);
		this.addToSessionAndContext("importTablesData", null);
		
		InstallServlet.getContext().put("moduleAction", "install_check_info.htm");
	}
	
	private List readFromDat(String filename) throws Exception
	{
		List l = new ArrayList();
		
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream in = new ObjectInputStream(fis);
		l = (ArrayList)in.readObject();
		in.close();
		
		return l;
	}
	
	private void addToSessionAndContext(String key, String value)
	{
		InstallServlet.getRequest().getSession().setAttribute(key, value);
		InstallServlet.getContext().put(key, value);
	}
	
	private String notNullDefault(String value, String useDefault)
	{
		if (value == null || value.trim().equals("")) {
			return useDefault;
		}
		
		return value;
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
