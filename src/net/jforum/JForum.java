/*
 * Copyright (c) 2003, Rafael Steil
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
 * This file creation date: Mar 3, 2003 / 11:43:35 AM
 * net.jforum.JForum.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: JForum.java,v 1.4 2004/04/24 19:54:25 rafaelsteil Exp $
 */
package net.jforum;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

import net.jforum.entities.UserSession;
import net.jforum.repository.BBCodeRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.util.bbcode.BBCodeHandler;
import net.jforum.util.MD5;
import net.jforum.util.I18n;
import net.jforum.util.SystemGlobals;

/**
 * Front Controller.
 * 
 * @author Rafael Steil
 */
public class JForum extends HttpServlet 
{
	/**
	 * Request information data holder.
	 * Stores information/data like the user request and
	 * response, his database connection and any other
	 * kind of data needed.
	 */
	private static class DataHolder
	{
		/**
		 * Database connection
		 */
		private Connection conn;
		
		/**
		 * The request
		 */
		private ActionServletRequest request;
		
		/**
		 * The response
		 */
		private HttpServletResponse response;
		
		/**
		 * The template engine context. All is put here.
		 */
		private SimpleHash context = new SimpleHash(ObjectWrapper.BEANS_WRAPPER);
		
		/**
		 * If some redirect is needed, the url is here
		 */
		private String redirectTo;
		
		// Setters
		public void setConnection(Connection conn)
		{
			this.conn = conn;
		}
		
		public void setRequest(ActionServletRequest request)
		{
			this.request = request;
		}
		
		public void setResponse(HttpServletResponse response)
		{
			this.response = response;
		}
		
		public void setContext(SimpleHash context)
		{
			this.context = context;
		}
		
		public void setRedirectTo(String redirectTo)
		{
			this.redirectTo = redirectTo;
		}
		
		// Getters
		public Connection getConnection()
		{
			return this.conn;
		}
		
		public ActionServletRequest getRequest()
		{
			return this.request;
		}
		
		public HttpServletResponse getResponse()
		{
			return this.response;
		}
		
		public SimpleHash getContext()
		{
			return this.context;
		}
		
		public String getRedirectTo()
		{
			return this.redirectTo;
		}
	}
	
	// Thread local implementation
	private static ThreadLocal localData = new ThreadLocal() {
		public Object initialValue() {
			return new DataHolder();
		}
	};
	
	/**
	 * Gets the current thread's connection  
	 * 
	 * @return
	 */	
	public static Connection getConnection()
	{
		return ((DataHolder)localData.get()).getConnection();
	}
	
	/**
	 * Gets the current thread's request
	 * 
	 * @return
	 */
	public static ActionServletRequest getRequest()
	{
		return ((DataHolder)localData.get()).getRequest();
	}
	
	/**
	 * Gets the current thread's response
	 * 
	 * @return
	 */
	public static HttpServletResponse getResponse()
	{
		return ((DataHolder)localData.get()).getResponse();
	}
	
	/**
	 * Gets the current thread's template context
	 * 
	 * @return
	 */
	public static SimpleHash getContext()
	{
		return ((DataHolder)localData.get()).getContext();
	}
	
	/**
	 * Gets the current thread's <code>DataHolder</code> instance
	 * 
	 * @return
	 */
	public static void setRedirect(String redirect)
	{
		((DataHolder)localData.get()).setRedirectTo(redirect);
	}
	
	private static Properties modulesMapping;
	private static boolean debug;
	
	/**
	 * Loads modules mapping file
	 * */
	private static void loadModulesMapping(String baseDir) throws IOException
	{
		modulesMapping = new Properties();
		modulesMapping.load(new FileInputStream(baseDir +"config/modulesMapping.properties"));
	}
	
	private void startDatabase() throws Exception
	{
		ConnectionPool.init(SystemGlobals.getApplicationResourceDir() +"config/database.properties");
	}
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
				
		try {
			JForum.debug = config.getInitParameter("development").equals("true"); 
			
			// Load system default values
			SystemGlobals.setApplicationPath(config.getServletContext().getRealPath("") +"/");
			SystemGlobals.setApplicationResourceDir(SystemGlobals.getApplicationPath() +"WEB-INF/");						
						
			SystemGlobals.setValue("servletName", config.getServletName());
		
			// Start the connection pool
			this.startDatabase();
			
			// Configure the template engine
			Configuration templateCfg = new Configuration();
			templateCfg.setDirectoryForTemplateLoading(new File(SystemGlobals.getApplicationPath() +"templates"));
			templateCfg.setTemplateUpdateDelay(0);
			
			JForum.loadModulesMapping(SystemGlobals.getApplicationResourceDir());
			this.loadUrlPatterns();
			
			if (!JForum.debug) {
				SystemGlobals.loadDefaults(SystemGlobals.getApplicationResourceDir() +"config/SystemGlobals.properties");
				SystemGlobals.loadQueries(SystemGlobals.getApplicationResourceDir() +"config/"+ SystemGlobals.getValue("sql.file"));
				
				I18n.load();
				
				// BB Code
				BBCodeRepository.setBBCollection(new BBCodeHandler().parse());
				
				templateCfg.setTemplateUpdateDelay(3600);
			}
			
			Configuration.setDefaultConfiguration(templateCfg);
		}
		catch (Exception e) {
			new ForumException(e);	
		}
	}

	/**
	 * Gets a cookie by its name.
	 * 
	 * @param name The cookie name to retrieve
	 * @return The <code>Cookie</code> object if found, or <code>null</code> oterwhise
	 */
	public static Cookie getCookie(String name)
	{
		Cookie[] cookies = JForum.getRequest().getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Add or update a cookie.
	 * This method adds a cookie, serializing its value using XML.
	 * 
	 * @param name The cookie name.
	 * @param value The cookie value
	 */
	public static void addSerializedCookie(String name, Object value)
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(out);
		encoder.writeObject(value);
		encoder.close();
		
		String s = out.toString().replaceAll("\r", "").replaceAll("\n", "");
		Cookie cookie = new Cookie(SystemGlobals.getValue("cookieName").toString(), s);
		cookie.setMaxAge(3600 * 24 * 365);
		
		JForum.getResponse().addCookie(cookie);
	}
	
	private void checkCookies() throws IOException
	{
		// If userData is not, then must probably the user is entering right
		// now in the website
		if (SessionFacade.getUserSession() == null) {
			UserSession userSession = new UserSession();

			Cookie cookie = JForum.getCookie(SystemGlobals.getValue("cookieName").toString());
			
			// If we don't have any cookie yet, then we should set it with the default values
			if (cookie == null) {
				userSession.setStartTime(System.currentTimeMillis());
				userSession.setLastVisit(System.currentTimeMillis());
				userSession.setUserId(Integer.parseInt(SystemGlobals.getValue("anonymousUserId").toString()));
				userSession.setSessionId(JForum.getRequest().getSession().getId());
				
				JForum.addSerializedCookie(SystemGlobals.getValue("cookieName").toString(), userSession);
			}
			else {
				// Ok, we have a cookie. Time to get it from the oven
				ByteArrayInputStream in = new ByteArrayInputStream(cookie.getValue().getBytes());
				XMLDecoder decoder = new XMLDecoder(in);
				
				userSession = (UserSession)decoder.readObject();
				
				// Update last visit and session start time
				userSession.setLastVisit(userSession.getStartTime() + userSession.getSessionTime());
				userSession.setStartTime(System.currentTimeMillis());
				
				decoder.close();
			}
			
			if (userSession.getAutoLogin()) {
				SessionFacade.setAttribute("logged", "1");
			}
			else {
				userSession.setUserId(Integer.parseInt(SystemGlobals.getValue("anonymousUserId").toString()));
			}
			
			userSession.setSessionId(JForum.getRequest().getSession().getId());
			SessionFacade.add(userSession);
			SessionFacade.setAttribute("topics_tracking", new HashMap());
		}
		else {
			UserSession userSession = SessionFacade.getUserSession();
			userSession.updateSessionTime();

			JForum.addSerializedCookie(SystemGlobals.getValue("cookieName").toString(), userSession);
		}
	}
	
	private void loadUrlPatterns() throws IOException
	{
		Properties p = new Properties();
		p.load(new FileInputStream(SystemGlobals.getApplicationResourceDir() + "config/urlPattern.properties"));
		
		Iterator iter = p.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			
			ActionServletRequest.addUrlPattern(entry.getKey().toString(), entry.getValue().toString());
		}
	}
	
	public void service(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException
	{
		try {
			// Initializes thread local data
			DataHolder dataHolder = new DataHolder();
			localData.set(dataHolder);

			if (JForum.debug) {
				SystemGlobals.loadDefaults(SystemGlobals.getApplicationResourceDir() +"config/SystemGlobals.properties");
				SystemGlobals.loadQueries(SystemGlobals.getApplicationResourceDir() +"config/"+ SystemGlobals.getValue("sql.file"));
				this.loadUrlPatterns();
				I18n.load();
				BBCodeRepository.setBBCollection(new BBCodeHandler().parse());
			}
			
			// Ensure the database is up and running
			if (!ConnectionPool.isDatabaseUp()) {
				System.out.println("Database not loaded. Trying again...");
				this.startDatabase();
			}
			
			// Context
			JForum.getContext().put("servletName", SystemGlobals.getValue("servletName"));
			JForum.getContext().put("contextPath", req.getContextPath());
			JForum.getContext().put("serverName", req.getServerName());
			JForum.getContext().put("templateName", "default");
			JForum.getContext().put("serverPort", Integer.toString(req.getServerPort()));
			JForum.getContext().put("I18n", I18n.getInstance());
			JForum.getContext().put("version", SystemGlobals.getValue("version"));
            JForum.getContext().put("homeLink",SystemGlobals.getValue("forumLink"));
            JForum.getContext().put("pageTitle",SystemGlobals.getValue("forum.page.title"));
            JForum.getContext().put("metaKeywords",SystemGlobals.getValue("forum.page.metatag.keywords"));
            JForum.getContext().put("metaDescription",SystemGlobals.getValue("forum.page.metatag.description"));

            // Request
			ActionServletRequest request = new ActionServletRequest(req);

			dataHolder.setResponse(response);
			dataHolder.setConnection(ConnectionPool.getPool().getConnection());
			dataHolder.setRequest(request);

			// Assigns the information to user's thread 
			localData.set(dataHolder);

			// Verify cookies
			this.checkCookies();
			
			boolean logged = false;
			if (SessionFacade.getAttribute("logged") != null && SessionFacade.getAttribute("logged").equals("1")) {
				logged = true;
			}

			JForum.getContext().put("logged", logged);

			// Process security data
			SecurityRepository.load(SessionFacade.getUserSession().getUserId());
			
			// Gets the module class name
			String moduleClass = JForum.modulesMapping.getProperty(request.getModule());
			
			JForum.getContext().put("moduleName", request.getModule());
			JForum.getContext().put("action", JForum.getRequest().getAction());
			
			JForum.getContext().put("securityHash", MD5.crypt(JForum.getRequest().getSession().getId()));
			JForum.getContext().put("session", SessionFacade.getUserSession());
			
			if (moduleClass != null) {
				// Here we go, baby
				Command c = (Command)Class.forName(moduleClass).newInstance();
				Template template = c.process();

				if (((DataHolder)localData.get()).getRedirectTo() == null) {
					// TODO: Add support to gzip content				
					response.setContentType("text/html");
					template.process(JForum.getContext(), response.getWriter());
				}
			}
		}
		catch (Exception e) {
				new ForumException(e, response.getWriter());
		}
		finally {
			try {
				if (JForum.getConnection() != null) {
					ConnectionPool.getPool().releaseConnection(JForum.getConnection());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (((DataHolder)localData.get()).getRedirectTo() != null) {
			JForum.getResponse().sendRedirect(((DataHolder)localData.get()).getRedirectTo());
		}
		
		// hhmmm.. is it enough to clear the thread local object?  
		localData.set(null);
	}	
}
