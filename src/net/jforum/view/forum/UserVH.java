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
 * This file creation date: May 12, 2003 / 8:31:25 PM
 * net.jforum.view.forum.UserVH.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.SessionFacade;
import net.jforum.entities.User;
import net.jforum.entities.UserSession;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.UserModel;
import net.jforum.repository.SecurityRepository;
import net.jforum.util.I18n;
import net.jforum.util.MD5;
import net.jforum.util.SystemGlobals;

/**
 * @author Rafael Steil
 */
public class UserVH extends Command 
{
	public void edit() throws Exception
	{
		int tmpId = SessionFacade.getUserSession().getUserId();
		if (SessionFacade.getAttribute("logged") != null
			&& SessionFacade.getAttribute("logged").equals("1")
			&& tmpId == Integer.parseInt(JForum.getRequest().getParameter("user_id"))) {

			int userId = Integer.parseInt(JForum.getRequest().getParameter("user_id"));	
			UserModel um = DataAccessDriver.getInstance().newUserModel();
			User u = um.selectById(userId);
			
			JForum.getContext().put("u", u);
			JForum.getContext().put("action", "editSave");		
			JForum.getContext().put("moduleAction", "user_form.htm");
		}
		else {
			this.profile();
		}
	}
	
	public void editSave() throws Exception
	{	
		int userId = Integer.parseInt(JForum.getRequest().getParameter("user_id"));
		ViewCommon.saveUser(userId);

		JForum.setRedirect(JForum.getRequest().getContextPath() +"/user/edit/"+ userId +".page");
	}
	
	public void insert()
	{
		JForum.getContext().put("action", "insertSave");		
		JForum.getContext().put("moduleAction", "user_new.htm");
	}
	
	public void insertSave() throws Exception
	{
		User u = new User();
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		
		String username = JForum.getRequest().getParameter("username");
		String password = JForum.getRequest().getParameter("password");
		
		boolean error = false;
		
		if (username == null || username.equals("") || password == null || password.equals("")) {
			JForum.getContext().put("error", I18n.getMessage("UsernamePasswordCannotBeNull"));
			error = true;
		}
		
		if (um.isUsernameRegistered(JForum.getRequest().getParameter("username"))) {
			JForum.getContext().put("error", I18n.getMessage("UsernameExists"));
			error = true;
		}
		
		if (error) {
			this.insert();
			
			return;
		}
		
		u.setUsername(username);
		u.setPassword(MD5.crypt(password));
		u.setEmail(JForum.getRequest().getParameter("email"));
		
		int userId = um.addNew(u);
		
		SessionFacade.setAttribute("logged", "1");
		
		UserSession userSession = new UserSession();
		userSession.setAutoLogin(true);
		userSession.setUserId(userId);
		userSession.setUsername(u.getUsername());
		userSession.setLastVisit(System.currentTimeMillis());
		userSession.setStartTime(System.currentTimeMillis());
		
		SessionFacade.add(userSession);
		JForum.addSerializedCookie(SystemGlobals.getValue("cookieName").toString(), userSession);
		
		// Finalizing.. show to user the congrats page
		JForum.setRedirect(JForum.getRequest().getContextPath() +"/user/registrationComplete.page");
	}
	
	public void registrationComplete() throws Exception
	{
		int userId = SessionFacade.getUserSession().getUserId();
		
		String profilePage = JForum.getRequest().getContextPath() +"/user/edit/"+ userId +".page";
		String homePage = JForum.getRequest().getContextPath() +"/forums/list.page";
		
		String message = I18n.getMessage("User.RegistrationCompleteMessage", new Object[] { profilePage, homePage });
		JForum.getContext().put("message", message);
		JForum.getContext().put("moduleAction", "registration_complete.htm");
	}
	
	public void validateLogin() throws Exception
	{
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		
		boolean validInfo = false;

		if (JForum.getRequest().getParameter("password").length() > 0) { 
			int userId = um.validateLogin(JForum.getRequest().getParameter("username"), JForum.getRequest().getParameter("password"));
			
			if (userId > 0) {
				JForum.setRedirect(JForum.getRequest().getContextPath() +"/forums/list.page");
				
				SessionFacade.setAttribute("logged", "1");
				
				UserSession userSession = SessionFacade.getUserSession();
				userSession.setUserId(userId);
				userSession.setUsername(JForum.getRequest().getParameter("username"));
				
				// Autologin
				if (JForum.getRequest().getParameter("autologin") != null) {
					userSession.setAutoLogin(true);
				}
				
				SessionFacade.add(userSession);
				JForum.addSerializedCookie(SystemGlobals.getValue("cookieName").toString(), userSession);
				
				SecurityRepository.load(userId, true);
				validInfo = true;
			}
		}

		// Invalid login
		if (validInfo == false) {			
			JForum.getContext().put("invalidLogin", "1");
			JForum.getContext().put("moduleAction", "forum_login.htm");
			
			if (JForum.getRequest().getParameter("returnPath") != null) {
				JForum.getContext().put("returnPath", JForum.getRequest().getParameter("returnPath"));
			}
		}
		else if (JForum.getRequest().getParameter("returnPath") != null) {
			JForum.setRedirect(JForum.getRequest().getParameter("returnPath"));
		}
	}
	
	public void profile() throws Exception
	{
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		
		User u = um.selectById(Integer.parseInt(JForum.getRequest().getParameter("user_id")));
		 
		JForum.getContext().put("moduleAction", "user_profile.htm");
		JForum.getContext().put("u", u);
	}
	
	public void logout() throws Exception
	{
		JForum.setRedirect(JForum.getRequest().getContextPath() +"/forums/list.page");
		
		// Disable auto login
		UserSession userSession = SessionFacade.getUserSession();
		userSession.setAutoLogin(false);
		userSession.setUserId(Integer.parseInt(SystemGlobals.getValue("anonymousUserId").toString()));

		JForum.addSerializedCookie(SystemGlobals.getValue("cookieName").toString(), userSession);
		
		SessionFacade.setAttribute("logged", "0");
		SessionFacade.add(userSession);
	}
	
	public void login() throws Exception
	{
		if (JForum.getRequest().getParameter("returnPath") != null) {
			JForum.getContext().put("returnPath", JForum.getRequest().getParameter("returnPath"));
		}

		JForum.getContext().put("moduleAction", "forum_login.htm");
	}
	
	public void list() throws Exception
	{
		this.login();
	}
}
