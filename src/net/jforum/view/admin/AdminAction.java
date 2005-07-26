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
 * This file creation date: 17/01/2004 / 19:34:01
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.admin;

import javax.servlet.http.HttpServletResponse;

import net.jforum.ActionServletRequest;
import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.SessionFacade;
import net.jforum.entities.UserSession;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.PermissionControl;
import net.jforum.security.SecurityConstants;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.util.preferences.TemplateKeys;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: AdminAction.java,v 1.9 2005/07/26 02:45:41 diegopires Exp $
 */
public class AdminAction extends Command {

	/**
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception {
		this.login();
	}

	public void login() throws Exception {
		String logged = (String) SessionFacade.getAttribute("logged");
		UserSession us = SessionFacade.getUserSession();

		PermissionControl pc = SecurityRepository.get(us.getUserId());

		if (logged == null || logged.toString().equals("0") || pc == null
				|| !pc.canAccess(SecurityConstants.PERM_ADMINISTRATION)) {
			String returnPath = this.request.getContextPath()
					+ "/admBase/login"
					+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION);

			JForum.setRedirect(this.request.getContextPath() + "/jforum"
					+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION)
					+ "?module=user&action=login&returnPath=" + returnPath);
		} else {
			this.setTemplateName(TemplateKeys.ADMIN_INDEX);
		}
	}

	public void menu() throws Exception {
		this.setTemplateName(TemplateKeys.ADMIN_MENU);
	}

	public void main() throws Exception {
		this.setTemplateName(TemplateKeys.ADMIN_MAIN);
	}

	public boolean checkAdmin() {
		int userId = SessionFacade.getUserSession().getUserId();
		if (SecurityRepository.get(userId).canAccess(
				SecurityConstants.PERM_ADMINISTRATION)) {
			return true;
		}

		JForum.setRedirect(JForum.getRequest().getContextPath()
				+ "/admBase/login"
				+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION));

		super.ignoreAction();

		return false;
	}

	public Template process(ActionServletRequest request,
			HttpServletResponse response, SimpleHash context) throws Exception {
		return super.process(request, response, context);
	}
}
