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
 * This file creation date: 02/04/2004 - 20:31:35
 * net.jforum.view.forum.ViewCommon.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: ViewCommon.java,v 1.11 2004/09/22 23:21:55 rafaelsteil Exp $
 */
package net.jforum.view.forum;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import net.jforum.JForum;
import net.jforum.SessionFacade;
import net.jforum.entities.Topic;
import net.jforum.entities.User;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.UserModel;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.SecurityConstants;
import net.jforum.util.I18n;
import net.jforum.util.MD5;
import net.jforum.util.image.ImageUtils;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

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
	public static List saveUser(int userId) throws Exception
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
		u.setLang(JForum.getRequest().getParameter("language"));
		
		String website = JForum.getRequest().getParameter("website");
		if (website != null && !website.toLowerCase().startsWith("http://")) {
			website = "http://" + website;
		}

		u.setWebSite(website);
		
		if (JForum.getRequest().getParameter("new_password") != null && JForum.getRequest().getParameter("new_password").length() > 0) {
			u.setPassword(MD5.crypt(JForum.getRequest().getParameter("new_password")));
		}
		
		if (JForum.getRequest().getParameter("avatardel") != null) {
			File f = new File(SystemGlobals.getApplicationPath() +"images/avatar/"+ u.getAvatar());
			f.delete();
			
			u.setAvatar(null);
		}

		List warns = new ArrayList();
		if (JForum.getRequest().getObjectParameter("avatar") != null) {
			try {
				handleAvatar(u);
			}
			catch (Exception e) {
				warns.add(I18n.getMessage("User.avatarUploadError"));
			}
		}
		
		um.update(u);
		
		SessionFacade.getUserSession().setLang(u.getLang());
		
		return warns;
	}

	/**
	 * @param u
	 * @throws NoSuchAlgorithmException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void handleAvatar(User u) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		String fileName = MD5.crypt(Integer.toString(u.getId()));
		
		// Gets file extension
		String extension = JForum.getRequest().getParameter("avatarName");
		extension = extension.substring(extension.lastIndexOf('.') + 1).toLowerCase();
		int type = -1;
		
		if (extension.equals("jpg") || extension.equals("jpeg")) {
			type = ImageUtils.IMAGE_JPEG;
		}
		else if (extension.equals("gif") || extension.equals("png")) {
			type = ImageUtils.IMAGE_PNG;
		}
		
		if (type != -1) {
			String avatarTmpFileName = SystemGlobals.getApplicationPath() +"/images/avatar/"+ fileName +"_tmp." + extension;
			
			// We cannot handle gifs
			if (extension.toLowerCase().equals("gif")) {
				extension = "png";
			}

			String avatarFinalFileName = SystemGlobals.getApplicationPath() +"/images/avatar/"+ fileName +"."+ extension;

			// Read the avatar and stores it into a temporary file
			BufferedInputStream inputStream = new BufferedInputStream((InputStream)JForum.getRequest().getObjectParameter("avatar"));
			FileOutputStream outputStream = new FileOutputStream(avatarTmpFileName);
			
			int c = 0;
			byte[] b = new byte[1024];
			while ((c = inputStream.read(b)) != -1) {
				outputStream.write(b);
			}
			
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			
			// OK, time to check and process the avatar size
			int maxWidth = SystemGlobals.getIntValue(ConfigKeys.AVATAR_MAX_WIDTH);
			int maxHeight = SystemGlobals.getIntValue(ConfigKeys.AVATAR_MAX_HEIGHT);

			BufferedImage image = ImageUtils.resizeImage(avatarTmpFileName, type, maxWidth, maxHeight);
			ImageUtils.saveImage(image, avatarFinalFileName, type);

			u.setAvatar(fileName +"."+ extension);
			
			// Delete de temporary file
			new File(avatarTmpFileName).delete();
		}
	}
	
	public static void contextToLogin() 
	{
		JForum.getContext().put("moduleAction", "forum_login.htm");
		String uri = JForum.getRequest().getRequestURI();
		String query = JForum.getRequest().getQueryString();
		String path = query == null ? uri : uri + "?" + query;

		JForum.getContext().put("returnPath", path);
	}
}
