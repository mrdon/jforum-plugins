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
 * This file creation date: 27/08/2004 - 18:12:26
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jforum.exceptions.ExceptionWriter;
import net.jforum.repository.ModulesRepository;
import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: InstallServlet.java,v 1.12 2005/02/17 19:05:50 rafaelsteil Exp $
 */
public class InstallServlet extends JForumCommonServlet
{
	/** 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}
	
	/** 
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	public void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException
	{
		DataHolder dataHolder = new DataHolder();
		localData.set(dataHolder);
		
		String encoding = SystemGlobals.getValue(ConfigKeys.ENCODING);
		req.setCharacterEncoding(encoding);
		
		// Context
		InstallServlet.getContext().put("contextPath", req.getContextPath());
		InstallServlet.getContext().put("serverName", req.getServerName());
		InstallServlet.getContext().put("templateName", "default");
		InstallServlet.getContext().put("serverPort", Integer.toString(req.getServerPort()));
		InstallServlet.getContext().put("I18n", I18n.getInstance());
		InstallServlet.getContext().put("encoding", encoding);
		InstallServlet.getContext().put("extension", SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION));
		
		// Request
		ActionServletRequest request = new ActionServletRequest(req);
		request.setCharacterEncoding(encoding);

		dataHolder.setResponse(response);
		dataHolder.setRequest(request);

		// Assigns the information to user's thread 
		localData.set(dataHolder);
		
		if (SystemGlobals.getBoolValue(ConfigKeys.INSTALLED)) {
			InstallServlet.setRedirect(InstallServlet.getRequest().getContextPath() 
					+ "/forums/list.page");
		}
		else {		
			// Module and Action
			String moduleClass = ModulesRepository.getModuleClass(request.getModule());
			
			InstallServlet.getContext().put("moduleName", request.getModule());
			InstallServlet.getContext().put("action", InstallServlet.getRequest().getAction());
			
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), encoding));
			
			try {
				if (moduleClass != null) {
					// Here we go, baby
					Command c = (Command)Class.forName(moduleClass).newInstance();
					Template template = c.process(request, response, null, InstallServlet.getContext());
		
					if (((DataHolder)localData.get()).getRedirectTo() == null) {
						response.setContentType("text/html; charset=" + encoding);
		
						template.process(InstallServlet.getContext(), out);
						out.flush();
					}
				}
			}
			catch (Exception e) {
				response.setContentType("text/html");
				if (out != null) {
					new ExceptionWriter().handleExceptionData(e, out);
				}
				else {
					new ExceptionWriter().handleExceptionData(e, new BufferedWriter(new OutputStreamWriter(response.getOutputStream())));
				}
			}
		}
		
		String redirectTo = ((DataHolder)localData.get()).getRedirectTo();
		if (redirectTo != null) {
			InstallServlet.getResponse().sendRedirect(redirectTo);
		}
		
		localData.set(null);
	}
}
