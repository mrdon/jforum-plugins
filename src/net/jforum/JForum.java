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
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

import net.jforum.entities.User;
import net.jforum.entities.UserSession;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.UserSessionModel;
import net.jforum.repository.BBCodeRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.util.I18n;
import net.jforum.util.MD5;
import net.jforum.util.bbcode.BBCodeHandler;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * Front Controller.
 * 
 * @author Rafael Steil
 * @version $Id: JForum.java,v 1.14 2004/06/02 03:56:07 rafaelsteil Exp $
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
		modulesMapping.load(new FileInputStream(baseDir +"/config/modulesMapping.properties"));
	}
	
	private void startDatabase() throws Exception
	{
		ConnectionPool.init(SystemGlobals.getApplicationResourceDir() +"/config/database.properties");
	}
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
				
		try {
			JForum.debug = config.getInitParameter("development").equals("true"); 
			
			// Load system default values
            // TODO: allow defaultsFile and installation to be overridden by the init parameters
            SystemGlobals.initGlobals(config.getServletContext().getRealPath(""), null, null);
            SystemGlobals.setTransientValue(ConfigKeys.SERVLET_NAME, config.getServletName());
						
			// Start the connection pool
			this.startDatabase();
			
			// Configure the template engine
			Configuration templateCfg = new Configuration();
			templateCfg.setDirectoryForTemplateLoading(new File(SystemGlobals.getApplicationPath() +"/templates"));
			templateCfg.setTemplateUpdateDelay(0);
			
			JForum.loadModulesMapping(SystemGlobals.getApplicationResourceDir());
			
			if (!JForum.debug) {
				this.loadConfigStuff();
				templateCfg.setTemplateUpdateDelay(3600);
			}
			
			Configuration.setDefaultConfiguration(templateCfg);
		}
		catch (Exception e) {
			new ForumException(e);	
		}
	}
	
	private void loadConfigStuff() throws Exception
	{
		SystemGlobals.loadQueries(SystemGlobals.getApplicationResourceDir() +"/config/"+ SystemGlobals.getValue(ConfigKeys.GENERIC_SQL_QUERIES));
		SystemGlobals.loadQueries(SystemGlobals.getApplicationResourceDir() +"/config/"+ SystemGlobals.getValue(ConfigKeys.SQL_FILE));
		
		I18n.load();
		this.loadUrlPatterns();
		
		// BB Code
		BBCodeRepository.setBBCollection(new BBCodeHandler().parse());
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
	public static void addCookie(String name, String value)
	{
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(3600 * 24 * 365);

		JForum.getResponse().addCookie(cookie);
	}
	
	private void setAnonymousUserSession(UserSession userSession)
	{
		userSession.setStartTime(System.currentTimeMillis());
		userSession.setLastVisit(System.currentTimeMillis());
		userSession.setUserId(SystemGlobals.getIntValue(ConfigKeys.ANONYMOUS_USER_ID));
		userSession.setSessionId(JForum.getRequest().getSession().getId());
	}
	
	private void checkCookies() throws Exception
	{
		if (SessionFacade.getUserSession() == null) {
			UserSession userSession = new UserSession();
			userSession.setSessionId(JForum.getRequest().getSession().getId());

			String cookieName = SystemGlobals.getValue(ConfigKeys.COOKIE_NAME_USER);
			Cookie cookie = JForum.getCookie(cookieName);
			
			// If we don't have any cookie yet, then we should set it with the default values
			if (cookie == null || cookie.getValue().equals(SystemGlobals.getValue(ConfigKeys.ANONYMOUS_USER_ID))) {
				this.setAnonymousUserSession(userSession);
			}
			else {
				String uid = cookie.getValue();
				if (uid != null && !uid.equals("")) {
					int userId = Integer.parseInt(uid);
					userSession.setUserId(userId);

					User user = DataAccessDriver.getInstance().newUserModel().selectById(userId);
					UserSessionModel sm = DataAccessDriver.getInstance().newUserSessionModel();

					UserSession tmpUs = sm.selectById(userSession, JForum.getConnection());
					if (tmpUs == null) {
						userSession.setStartTime(System.currentTimeMillis());
						userSession.setLastVisit(System.currentTimeMillis());
						sm.add(userSession, JForum.getConnection());
					}
					else {
						// Update last visit and session start time
						userSession.setLastVisit(tmpUs.getStartTime() + tmpUs.getSessionTime());
						userSession.setStartTime(System.currentTimeMillis());
					}
					
					userSession.setUsername(user.getUsername());
				}
				else {
					this.setAnonymousUserSession(userSession);
				}
			}
			
			cookie = JForum.getCookie(SystemGlobals.getValue(ConfigKeys.COOKIE_AUTO_LOGIN));
			if (cookie != null && cookie.getValue().equals("1")) {
				userSession.setAutoLogin(true);
				SessionFacade.setAttribute("logged", "1");
			}
			else {
				userSession.setUserId(SystemGlobals.getIntValue(ConfigKeys.ANONYMOUS_USER_ID));
			}
			
			SessionFacade.add(userSession);
			SessionFacade.setAttribute("topics_tracking", new HashMap());
		}
		else {
			SessionFacade.getUserSession().updateSessionTime();
		}
	}
	
	private void loadUrlPatterns() throws IOException
	{
		Properties p = new Properties();
		p.load(new FileInputStream(SystemGlobals.getApplicationResourceDir() + "/config/urlPattern.properties"));
		
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
				this.loadConfigStuff();
			}
			
			String encoding = SystemGlobals.getValue(ConfigKeys.ENCODING);
			req.setCharacterEncoding(encoding);
			
			// Ensure the database is up and running
			if (!ConnectionPool.isDatabaseUp()) {
				System.out.println("Database not loaded. Trying again...");
				this.startDatabase();
			}
			
			// Context
			JForum.getContext().put("servletName", SystemGlobals.getValue(ConfigKeys.SERVLET_NAME));
			JForum.getContext().put("contextPath", req.getContextPath());
			JForum.getContext().put("serverName", req.getServerName());
			JForum.getContext().put("templateName", "default");
			JForum.getContext().put("serverPort", Integer.toString(req.getServerPort()));
			JForum.getContext().put("I18n", I18n.getInstance());
			JForum.getContext().put("version", SystemGlobals.getValue(ConfigKeys.VERSION));
			JForum.getContext().put("homeLink",SystemGlobals.getValue(ConfigKeys.FORUM_LINK));
			JForum.getContext().put("pageTitle",SystemGlobals.getValue(ConfigKeys.FORUM_PAGE_TITLE));
			JForum.getContext().put("metaKeywords",SystemGlobals.getValue(ConfigKeys.FORUM_PAGE_METATAG_KEYWORDS));
			JForum.getContext().put("metaDescription",SystemGlobals.getValue(ConfigKeys.FORUM_PAGE_METATAG_DESCRIPTION));
			JForum.getContext().put("encoding", encoding);

			// Request
			ActionServletRequest request = new ActionServletRequest(req);
			request.setCharacterEncoding(encoding);

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
					response.setContentType("text/html; charset="+ encoding);

					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), encoding));
					
					template.process(JForum.getContext(), out);
					out.flush();
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
		
		String redirectTo = ((DataHolder)localData.get()).getRedirectTo();
		if (redirectTo != null) {
			JForum.getResponse().sendRedirect(redirectTo);
		}
		
		// hhmmm.. is it enough to clear the thread local object?  
		localData.set(null);
	}	
}
