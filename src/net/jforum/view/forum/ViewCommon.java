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
 * This file creation date: 02/04/2004 - 20:31:35
 * net.jforum.view.forum.ViewCommon.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import net.jforum.JForum;
import net.jforum.entities.Topic;
import net.jforum.entities.User;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.UserModel;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.SecurityConstants;
import net.jforum.util.MD5;
import net.jforum.util.SystemGlobals;

/**
 * @author Rafael Steil
 */
public final class ViewCommon
{
	/**
	 * Common properties to be used when showing topic data
	 */
	public static void topicListingBase()
	{
		// Topic Types
		JForum.getContext().put("TOPIC_ANNOUNCE", new Integer(Topic.TYPE_ANNOUNCE));
		JForum.getContext().put("TOPIC_STICKY", new Integer(Topic.TYPE_STICKY));
		JForum.getContext().put("TOPIC_NORMAL", new Integer(Topic.TYPE_NORMAL));

		// Topic Status
		JForum.getContext().put("STATUS_LOCKED", new Integer(Topic.STATUS_LOCKED));
		JForum.getContext().put("STATUS_UNLOCKED", new Integer(Topic.STATUS_UNLOCKED));
		
		// Moderation
		JForum.getContext().put("moderator", SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION));
		JForum.getContext().put("can_remove_posts", SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_POST_REMOVE));
		JForum.getContext().put("can_move_topics", SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_TOPIC_MOVE));
		JForum.getContext().put("can_lockUnlock_topics", SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_TOPIC_LOCK_UNLOCK));
	}
	
	/**
	 * Updates the user information
	 * 
	 * @param userId The user id we are saving
	 * @throws Exception
	 */
	public static void saveUser(int userId) throws Exception
	{
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		User u = um.selectById(userId);
		
		String username = JForum.getRequest().getParameter("username");
		if (username != null) {
			u.setUsername(username);
		}
		
		u.setId(userId);
		u.setEmail(JForum.getRequest().getParameter("email"));
		u.setIcq(JForum.getRequest().getParameter("icq"));
		u.setAim(JForum.getRequest().getParameter("aim"));
		u.setMsnm(JForum.getRequest().getParameter("msn"));
		u.setYim(JForum.getRequest().getParameter("yim"));
		u.setWebSite(JForum.getRequest().getParameter("website"));
		u.setFrom(JForum.getRequest().getParameter("location"));
		u.setOccupation(JForum.getRequest().getParameter("occupation"));
		u.setInterests(JForum.getRequest().getParameter("interests"));
		u.setSignature(JForum.getRequest().getParameter("signature"));
		u.setViewEmailEnabled(JForum.getRequest().getParameter("viewemail").equals("1"));
		u.setViewOnlineEnabled(JForum.getRequest().getParameter("hideonline").equals("0"));
		u.setNotifyPrivateMessagesEnabled(JForum.getRequest().getParameter("notifypm").equals("1"));
		u.setNotifyOnMessagesEnabled(JForum.getRequest().getParameter("notifyreply").equals("1"));
		u.setAttachSignatureEnabled(JForum.getRequest().getParameter("attachsig").equals("1"));
		u.setHtmlEnabled(JForum.getRequest().getParameter("allowhtml").equals("1"));
		
		if (JForum.getRequest().getParameter("new_password") != null && JForum.getRequest().getParameter("new_password").length() > 0) {
			u.setPassword(MD5.crypt(JForum.getRequest().getParameter("new_password")));
		}
		
		if (JForum.getRequest().getParameter("avatardel") != null) {
			File f = new File(SystemGlobals.getApplicationPath() +"images/avatar/"+ u.getAvatar());
			f.delete();
			
			u.setAvatar(null);
		}

		if (JForum.getRequest().getObjectParameter("avatar") != null) {
			String fileName = MD5.crypt(Integer.toString(u.getId()));
			
			// Gets file extension
			String extension = JForum.getRequest().getParameter("avatarName");
			extension = extension.substring(extension.lastIndexOf('.'));
		
			// Gets the content and write it to disk
			BufferedInputStream inputStream = new BufferedInputStream((InputStream)JForum.getRequest().getObjectParameter("avatar"));
			FileOutputStream outputStream = new FileOutputStream(SystemGlobals.getApplicationPath() +"/images/avatar/"+ fileName + extension);
			
			int c = 0;
			byte[] b = new byte[1024];
			while ((c = inputStream.read(b)) != -1) {
				outputStream.write(b);
			}
			
			outputStream.flush();
			outputStream.close();
			inputStream.close();

			u.setAvatar(fileName + extension);
		}
		
		um.update(u); 
	}
}
