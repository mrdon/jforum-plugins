/*
 * Copyright (c) 2003, Rafael Steil
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
 * This file creation date: Mar 3, 2003 / 10:55:19 AM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum;

import java.sql.Connection;

import javax.servlet.http.HttpServletResponse;

import net.jforum.exceptions.TemplateNotFoundException;
import net.jforum.repository.Tpl;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.util.preferences.TemplateKeys;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * <code>Command</code> Pattern implementation.
 * All View Helper classes, which are intead to configure and processs
 * presentation actions must extend this class. 
 * 
 * @author Rafael Steil
 * @version $Id: Command.java,v 1.8 2005/03/15 18:24:12 rafaelsteil Exp $
 */
public abstract class Command 
{
	private String templateName;
	private boolean ignoreAction;
	
	protected Connection conn;
	protected ActionServletRequest request;
	protected HttpServletResponse response;
	protected SimpleHash context;
	
	protected void setTemplateName(String templateName)
	{
		this.templateName = Tpl.name(templateName);
	}
	
	protected void ignoreAction()
	{
		this.ignoreAction = true;
	}
	
	/**
	 * Base method for listings. 
	 * May be used as general listing or as helper
	 * to another specialized type of listing. Subclasses
	 * must implement it to the cases where some invalid
	 * action is called ( which means that the exception will
	 * be caught and the general listing will be used )
	 * 
	 * @throws Exception  
	 */
	public abstract void list() throws Exception;
	
	/**
	 * Process and manipulate a requisition.
	 * @param context TODO
	 * 
	 * @throws Exception
	 * @return <code>Template</code> reference
	 */
	public Template process(ActionServletRequest request, 
			HttpServletResponse response,
			Connection conn, 
			SimpleHash context) throws Exception 
	{
		this.request = request;
		this.response = response;
		this.conn = conn;
		this.context = context;
		
		String action = JForum.getRequest().getAction();

		if (!this.ignoreAction) {
			try {
				Class.forName(this.getClass().getName()).getMethod(action, null).invoke(this, null);
			}
			catch (NoSuchMethodException e) {		
				this.list();		
			}
			catch (Exception e) {
				throw e;
			}
		}
		
		if (JForum.getRedirect() != null) {
			this.setTemplateName(TemplateKeys.EMPTY);
		}
		else if (this.templateName == null) {
			throw new TemplateNotFoundException("Template for action " + action + " is not defined");
		}
		
		return Configuration.getDefaultConfiguration().getTemplate(SystemGlobals.getValue(ConfigKeys.TEMPLATE_DIR)
				+ "/" 
				+ this.templateName);
	}
}
