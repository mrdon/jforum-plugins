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
 * This file creation date: 20/05/2004 - 21:05:45
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import java.util.List;

import net.jforum.Command;
import net.jforum.SessionFacade;
import net.jforum.entities.Post;
import net.jforum.entities.PrivateMessage;
import net.jforum.entities.PrivateMessageType;
import net.jforum.entities.User;
import net.jforum.entities.UserSession;
import net.jforum.model.DataAccessDriver;
import net.jforum.repository.SmiliesRepository;
import net.jforum.util.I18n;
import net.jforum.util.concurrent.executor.QueuedExecutor;
import net.jforum.util.mail.EmailSenderTask;
import net.jforum.util.mail.PrivateMessageSpammer;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.view.forum.common.PostCommon;
import net.jforum.view.forum.common.ViewCommon;

/**
 * @author Rafael Steil
 * @version $Id: PrivateMessageAction.java,v 1.14 2005/02/18 19:01:18 rafaelsteil Exp $
 */
public class PrivateMessageAction extends Command
{
	private String templateName;
	
	public void inbox() throws Exception
	{
		if (!SessionFacade.isLogged()) {
			ViewCommon.contextToLogin();
			return;
		}
		
		User user = new User();
		user.setId(SessionFacade.getUserSession().getUserId());
		
		List pmList = DataAccessDriver.getInstance().newPrivateMessageModel().selectFromInbox(user);

		this.context.put("inbox", true);
		this.context.put("pmList", pmList);
		this.context.put("moduleAction", "pm_list.htm");
		this.putTypes();		
	}
	
	public void sentbox() throws Exception
	{
		if (!SessionFacade.isLogged()) {
			ViewCommon.contextToLogin();
			return;
		}
		
		User user = new User();
		user.setId(SessionFacade.getUserSession().getUserId());
		
		List pmList = DataAccessDriver.getInstance().newPrivateMessageModel().selectFromSent(user);

		this.context.put("sentbox", true);
		this.context.put("pmList", pmList);
		this.context.put("moduleAction", "pm_list.htm");
		this.putTypes();
	}
	
	private void putTypes()
	{
		this.context.put("NEW", new Integer(PrivateMessageType.NEW));
		this.context.put("READ", new Integer(PrivateMessageType.READ));
		this.context.put("UNREAD", new Integer(PrivateMessageType.UNREAD));
	}
	
	public void send() throws Exception
	{
		if (!SessionFacade.isLogged()) {
			ViewCommon.contextToLogin();
			return;
		}
		
		User user = DataAccessDriver.getInstance().newUserModel().selectById(
						SessionFacade.getUserSession().getUserId());

		this.sendFormCommon(user);
	}
	public void sendTo() throws Exception
	{
		if (!SessionFacade.isLogged()) {
			ViewCommon.contextToLogin();
			return;
		}
		
		User user = DataAccessDriver.getInstance().newUserModel().selectById(
				SessionFacade.getUserSession().getUserId());

		int userId = this.request.getIntParameter("user_id");
		if (userId > 0){
			User user1 = DataAccessDriver.getInstance().newUserModel().selectById(userId);
			this.context.put("pmRecipient", user1);
			this.context.put("toUserId", String.valueOf(user1.getId()));
			this.context.put("toUsername", user1.getUsername());
			this.context.put("toUserEmail", user1.getEmail());
		}

		this.sendFormCommon(user);
	}
	
	private void sendFormCommon(User user)
	{
		this.context.put("user", user);
		this.context.put("moduleName", "pm");
		this.context.put("action", "sendSave");
		this.context.put("moduleAction", "post_form.htm");
		this.context.put("htmlAllowed", true);
		this.context.put("attachmentsEnabled", false);
		this.context.put("maxAttachments", SystemGlobals.getValue(ConfigKeys.ATTACHMENTS_MAX_POST));
	}
	
	public void sendSave() throws Exception
	{
		if (!SessionFacade.isLogged()) {
			ViewCommon.contextToLogin();
			return;
		}
		
		String sId = this.request.getParameter("toUserId");
		String toUsername = this.request.getParameter("toUsername");
		String userEmail = this.request.getParameter("toUserEmail");
		
		int toUserId = -1;
		if (sId == null || sId.equals("")) {
			List l = DataAccessDriver.getInstance().newUserModel().findByName(toUsername, true);
			
			if (l.size() > 0) {
				User u = (User)l.get(0);
				toUserId = u.getId();
				userEmail = u.getEmail();
			}
		}
		else {
			toUserId = Integer.parseInt(sId);
		}
		
		// We failed to get the user id?
		if (toUserId == -1) {
			this.context.put("moduleAction", "message.htm");
			this.context.put("message", I18n.getMessage("PrivateMessage.userIdNotFound"));
			return;
		}
		
		PrivateMessage pm = new PrivateMessage();
		pm.setPost(PostCommon.fillPostFromRequest());
		
		User fromUser = new User();
		fromUser.setId(SessionFacade.getUserSession().getUserId());
		pm.setFromUser(fromUser);
		
		User toUser = new User();
		toUser.setId(toUserId);
		toUser.setUsername(toUsername);
		toUser.setEmail(userEmail);
		pm.setToUser(toUser);
		
		boolean preview = (this.request.getParameter("preview") != null);
		if (!preview) {
			DataAccessDriver.getInstance().newPrivateMessageModel().send(pm);
			
			this.context.put("moduleAction", "message.htm");
			this.context.put("message", I18n.getMessage("PrivateMessage.messageSent", 
							new String[] { this.request.getContextPath() +"/pm/inbox"
											+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION)}));
			
			// If the target user if in the forum, then increments its 
			// private messate count
			String sid = SessionFacade.isUserInSession(toUserId);
			if (sid != null) {
				UserSession us = SessionFacade.getUserSession(sid);
				us.setPrivateMessages(us.getPrivateMessages() + 1);
			}
			
			if (userEmail != null && userEmail.trim().length() > 0) {
				if (SystemGlobals.getBoolValue(ConfigKeys.MAIL_NOTIFY_ANSWERS)) {
					try {
						QueuedExecutor.getInstance().execute(new EmailSenderTask(new PrivateMessageSpammer(toUser)));
					}
					catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}
		else {
			this.context.put("preview", true);
			this.context.put("post", pm.getPost());
			
			Post postPreview = new Post(pm.getPost());
			this.context.put("postPreview", PostCommon.preparePostForDisplay(postPreview));
			this.context.put("pm", pm);

			this.send();
		}
	}
	
	public void findUser() throws Exception
	{
		boolean showResult = false;
		String username = this.request.getParameter("username");

		if (username != null && !username.equals("")) {
			List namesList = DataAccessDriver.getInstance().newUserModel().findByName(username, false);
			this.context.put("namesList", namesList);
			showResult = true;
		}
		
		this.context.put("username", username);
		this.context.put("showResult", showResult);
		this.setTemplateName(SystemGlobals.getValue(ConfigKeys.TEMPLATE_NAME) + "/pm_finduser.htm");
	}
	
	public void read() throws Exception
	{
		if (!SessionFacade.isLogged()) {
			ViewCommon.contextToLogin();
			return;
		}
		
		int id = this.request.getIntParameter("id");
		
		PrivateMessage pm = new PrivateMessage();
		pm.setId(id);
		
		pm = DataAccessDriver.getInstance().newPrivateMessageModel().selectById(pm);
		
		// Don't allow the read of messages that don't belongs
		// to the current user
		UserSession us = SessionFacade.getUserSession();
		int userId = us.getUserId();
		if (pm.getToUser().getId() == userId || pm.getFromUser().getId() == userId) {
			pm.getPost().setText(PostCommon.preparePostForDisplay(pm.getPost()).getText());
			
			// Update the message status, if needed
			if (pm.getType() == PrivateMessageType.NEW) {
				pm.setType(PrivateMessageType.READ);
				DataAccessDriver.getInstance().newPrivateMessageModel().updateType(pm);
				us.setPrivateMessages(us.getPrivateMessages() - 1);
			}
			
			User u = pm.getFromUser();
			u.setSignature(PostCommon.processText(u.getSignature()));
            u.setSignature(PostCommon.processSmilies(u.getSignature(), 
            		SmiliesRepository.getSmilies()));
            
			this.context.put("pm", pm);
			this.context.put("moduleAction", "pm_read_message.htm");
		}
		else {
			this.context.put("moduleAction", "message.htm");
			this.context.put("message", I18n.getMessage("PrivateMessage.readDenied"));
		}
	}
	
	public void delete() throws Exception
	{
		if (!SessionFacade.isLogged()) {
			ViewCommon.contextToLogin();
			return;
		}
		
		String ids[] = this.request.getParameterValues("id");
		
		if (ids != null && ids.length > 0) {
			PrivateMessage[] pm = new PrivateMessage[ids.length];
			User u = new User();
			u.setId(SessionFacade.getUserSession().getUserId());
	
			for (int i = 0; i < ids.length; i++) {
				pm[i] = new PrivateMessage();
				pm[i].setFromUser(u);
				pm[i].setId(Integer.parseInt(ids[i]));
			}
			
			DataAccessDriver.getInstance().newPrivateMessageModel().delete(pm);
		}
		
		this.context.put("moduleAction", "message.htm");
		this.context.put("message", I18n.getMessage("PrivateMessage.deleteDone", 
						new String[] { this.request.getContextPath() + "/pm/inbox"
										+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION) }));
	}
	
	public void reply() throws Exception
	{
		if (!SessionFacade.isLogged()) {
			ViewCommon.contextToLogin();
			return;
		}
		
		int id = this.request.getIntParameter("id");
		
		PrivateMessage pm = new PrivateMessage();
		pm.setId(id);
		pm = DataAccessDriver.getInstance().newPrivateMessageModel().selectById(pm);
		
		pm.getPost().setSubject(I18n.getMessage("PrivateMessage.replyPrefix") + pm.getPost().getSubject());
		
		this.context.put("moduleAction", "post_form.htm");
		this.context.put("htmlAllowed", true);
		this.context.put("action", "sendSave");
		this.context.put("pm", pm);
		this.context.put("pmReply", true);
		this.context.put("user", DataAccessDriver.getInstance().newUserModel().selectById(
						SessionFacade.getUserSession().getUserId()));
	}
	
	public void quote() throws Exception
	{
		if (!SessionFacade.isLogged()) {
			ViewCommon.contextToLogin();
			return;
		}
		
		int id = this.request.getIntParameter("id");
		
		PrivateMessage pm = new PrivateMessage();
		pm.setId(id);
		pm = DataAccessDriver.getInstance().newPrivateMessageModel().selectById(pm);
		
		pm.getPost().setSubject(I18n.getMessage("PrivateMessage.replyPrefix") + pm.getPost().getSubject());
		
		this.sendFormCommon(DataAccessDriver.getInstance().newUserModel().selectById(
						SessionFacade.getUserSession().getUserId()));
		
		this.context.put("quote", "true");
		this.context.put("quoteUser", pm.getFromUser().getUsername());
		this.context.put("post", pm.getPost());
		this.context.put("pm", pm);
	}
	
	/** 
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception
	{
		this.inbox();
	}
}
