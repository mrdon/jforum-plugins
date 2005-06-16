/*
 * Copyright (c) Rafael Steil
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
 * This file creation date: Mar 3, 2003 / 11:43:35 AM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jforum.exceptions.ExceptionWriter;
import net.jforum.exceptions.ForumStartupException;
import net.jforum.repository.ModulesRepository;
import net.jforum.repository.RankingRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.repository.SmiliesRepository;
import net.jforum.util.MD5;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.log4j.Logger;

import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * Front Controller.
 * 
 * @author Rafael Steil
 * @version $Id: JForum.java,v 1.71 2005/06/16 01:24:56 rafaelsteil Exp $
 */
public class JForum extends JForumBaseServlet 
{
	private static boolean isDatabaseUp;
	private static Logger logger = Logger.getLogger(JForum.class);
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		
		// Start database
		isDatabaseUp = ForumStartup.startDatabase();
		
		// Configure ThreadLocal
		DataHolder dh = new DataHolder();
		Connection conn;
		
		try {
			conn = DBConnection.getImplementation().getConnection();
		}
		catch (Exception e) {
			throw new ForumStartupException("Error while starting jforum", e);
		}
		
		dh.setConnection(conn);
		JForum.setThreadLocalData(dh);
		
		// Init general forum stuff
		ForumStartup.startForumRepository();
		RankingRepository.loadRanks();
		SmiliesRepository.loadSmilies();
		
		// Finalize
		if (conn != null) {
			try {
				DBConnection.getImplementation().releaseConnection(conn);
			}
			catch (Exception e) {}
		}
		
		JForum.setThreadLocalData(null);
	}
	
	public void service(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException
	{
		Writer out = null;
		Connection conn = null;
		
		boolean autoCommitStatus = true;
		boolean useTransactions = SystemGlobals.getBoolValue(ConfigKeys.DATABASE_USE_TRANSACTIONS);
		
		try {
			// Initializes thread local data
			DataHolder dataHolder = new DataHolder();
			localData.set(dataHolder);

			String encoding = SystemGlobals.getValue(ConfigKeys.ENCODING);

			// Request
			ActionServletRequest request = new ActionServletRequest(req);

			dataHolder.setResponse(response);
			dataHolder.setRequest(request);
			
			if (!isDatabaseUp) {
				ForumStartup.startDatabase();
			}
			
			if (isDatabaseUp) {
				conn = DBConnection.getImplementation().getConnection();
				dataHolder.setConnection(conn);
				
				if (useTransactions) {
					autoCommitStatus = conn.getAutoCommit();
					conn.setAutoCommit(false);
				}
			}
			
			localData.set(dataHolder);
			
			// Setup stuff
			SimpleHash context = JForum.getContext();
			
			ControllerUtils utils = new ControllerUtils();
			utils.refreshSession();
			utils.prepareTemplateContext(context);
			
			boolean logged = "1".equals(SessionFacade.getAttribute("logged"));
			
			context.put("logged", logged);
			
			// Process security data
			SecurityRepository.load(SessionFacade.getUserSession().getUserId());

			String module = request.getModule();
			
			// Gets the module class name
			String moduleClass = ModulesRepository.getModuleClass(module);
			
			context.put("moduleName", module);
			context.put("action", request.getAction());
			
			context.put("securityHash", MD5.crypt(request.getSession().getId()));
			context.put("session", SessionFacade.getUserSession());
		
			if (moduleClass != null) {
				// Here we go, baby
				Command c = (Command)Class.forName(moduleClass).newInstance();
				Template template = c.process(request, response, conn, context);

				DataHolder dh = (DataHolder)localData.get();
				
				if (dh.getRedirectTo() == null) {
					String contentType = dh.getContentType();
					
					if (contentType == null) {
						contentType = "text/html; charset=" + encoding;
					}
					
					response.setContentType(contentType);
					
					// Binary content are expected to be fully 
					// handled in the action, including outputstream
					// manipulation
					if (!dh.isBinaryContent()) {
						out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), encoding));
						template.process(JForum.getContext(), out);
						out.flush();
					}
				}
			}
			else {
				logger.error("Cannot find the module class name for "
						+ "[module=" + module + ", "
						+ "action=" + request.getAction() + "]");
			}
			
			if (useTransactions) {
				if (!JForum.cancelCommit()) {
					conn.commit();
				}
				else {
					conn.rollback();
				}
			}
		}
		catch (Exception e) {
			if (useTransactions && conn != null) {
				try {
					conn.rollback();
				}
				catch (Exception dbe) {
					dbe.printStackTrace();
				}
			}
			
			if (e.toString().indexOf("ClientAbortException") == -1) {
				response.setContentType("text/html");
				if (out != null) {
					new ExceptionWriter().handleExceptionData(e, out);
				}
				else {
					new ExceptionWriter().handleExceptionData(e, new BufferedWriter(new OutputStreamWriter(response.getOutputStream())));
				}
			}
		}
		finally {
			try {
				if (conn != null) {
					if (useTransactions) {
						conn.setAutoCommit(autoCommitStatus);
					}
				
					DBConnection.getImplementation().releaseConnection(conn);
				}
				
				if (out != null) {
					out.close();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			DataHolder dh = (DataHolder)localData.get();
			
			if (dh != null) {
				String redirectTo = dh.getRedirectTo();
				
				if (redirectTo != null) {
					response.sendRedirect(redirectTo);
				}
			}
			
			localData.set(null);
		}		
	}
	
	/** 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	public void destroy() {
		super.destroy();
		System.out.println("Destroying JForum...");
		
		try {
			DBConnection.getImplementation().realReleaseAllConnections();
		}
		catch (Exception e) {}
	}
}
