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
 * This file creation date: 17/01/2004 / 19:34:01
 * net.jforum.view.admin.AdminVH.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: AdminVH.java,v 1.4 2004/08/30 13:40:54 rafaelsteil Exp $
 */
package net.jforum.view.admin;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.SessionFacade;
import net.jforum.entities.UserSession;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.PermissionControl;
import net.jforum.security.SecurityConstants;

/**
 * @author Rafael Steil
 */
public class AdminVH extends Command {

	/* 
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception 
	{
		this.login();
	}
	
	public void login() throws Exception
	{
		String logged = (String)SessionFacade.getAttribute("logged");
		UserSession us = SessionFacade.getUserSession();
		
		PermissionControl pc = SecurityRepository.get(us.getUserId());
		
		if (logged == null || logged.toString().equals("0") || pc == null || !pc.canAccess(SecurityConstants.PERM_ADMINISTRATION)) {
			String returnPath =  JForum.getRequest().getContextPath() +"/admBase/login.page";
			JForum.setRedirect(JForum.getRequest().getContextPath() +"/jforum.page?module=user&action=login&returnPath="+ returnPath);
		}
		else {
			JForum.getContext().put("moduleAction", "admin_index.htm");
		}
	}
	
	public void menu() throws Exception
	{
		JForum.getContext().put("moduleAction", "menu.htm");
	}
	
	public void main() throws Exception
	{
		JForum.getContext().put("moduleAction", "welcome.htm");
	}
	
	public static boolean isAdmin()
	{
		int userId = SessionFacade.getUserSession().getUserId();
		if (SecurityRepository.get(userId).canAccess(SecurityConstants.PERM_ADMINISTRATION)) {
			return true;
		}
		else {
			JForum.setRedirect(JForum.getRequest().getContextPath() +"/admBase/login.page");

			return false;
		}
	}
	
	/*
	 * @see net.jforum.Command#process()
	 */
	public Template process() throws Exception 
	{
		super.process();
		
		return Configuration.getDefaultConfiguration().getTemplate("admin/empty.htm");
	}
}
