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
 * This file creation date: Apr 23, 2003 / 10:46:05 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.jforum.entities.Forum;
import net.jforum.ForumException;
import net.jforum.SessionFacade;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.ForumModel;
import net.jforum.security.PermissionControl;
import net.jforum.security.SecurityConstants;

/**
 * Repository for the forums of the System.
 * This repository acts like a cache system, to avoid repetitive and unnecessary SQL queries
 * every time we need some info about the forums. Using the repository, we put process information
 * needed just once, and then use the cache when data is requested.<br> 
 * 
 * @author Rafael Steil
 * @version  $Id: ForumRepository.java,v 1.6 2004/10/14 02:23:38 rafaelsteil Exp $
 */
public class ForumRepository 
{
	private static HashMap forumsMap = new HashMap();
	private static HashMap lastPostInfoMap = new HashMap();
	private static int totalTopics = -1;
	private static int totalMessages = 0;
	
	static {
		try {
			ForumRepository.loadForums();
		}
		catch (Exception e) {
			new ForumException(e);
		}
	}
	
	/**
	 * Gets all forums from the cache
	 * 
	 * @param ignoreSecurity If <code>true</code>, all contents are returned, even if the
	 * user doesn't have access to someone. If <code>false</code>, the permission control is applied.
	 * @return <code>ArrayList</code> containing all forums. Each entry is a <code>net.jforum.Forum</code> object
	 * @see #getForum(int)
	 */
	public static ArrayList getAllForums(boolean ignoreSecurity)
	{
		ArrayList l = new ArrayList();
		Iterator iter = ForumRepository.forumsMap.values().iterator();
		
		if (!ignoreSecurity) {
			int userId = SessionFacade.getUserSession().getUserId();
			PermissionControl pc = SecurityRepository.get(userId);

			while (iter.hasNext()) {
				Forum f = (Forum)iter.next();

				if (pc.canAccess(SecurityConstants.PERM_FORUM, Integer.toString(f.getId()))) {
					l.add(f);
				}
			}
		} else {
			while (iter.hasNext()) {
				Forum f = (Forum)iter.next();
			    l.add(f);
			}
		}
		
		return l;
	}
	
	/**
	 * Gets all forums from the cache
	 * 
	 * @return <code>ArrayList</code> containing all forums. Each entry is a <code>net.jforum.Forum</code> object
	 * @see #getForum(int)
	 */
	public static ArrayList getAllForums()
	{
		return ForumRepository.getAllForums(false);
	}
	
	private static void loadForums() throws Exception
	{
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		 
		List l = fm.selectAll();
		for (Iterator iter = l.iterator(); iter.hasNext(); ) {
			Forum f = (Forum)iter.next();
			
			forumsMap.put(new Integer(f.getId()), f);
		}
	}
	
	/**
	 * Gets a specific forum from the cache.	 
	 * 
	 * @param forumId The forum's ID to get
	 * @return <code>net.jforum.Forum</code> object instance
	 */
	public static Forum getForum(int forumId)
	{
		Forum f = (Forum)forumsMap.get(new Integer(forumId));
		if (f != null) {
			return f;
		}
		
		return new Forum();
	}
	
	/**
	 * Adds a new forum to the cache repository.	 
	 * 
	 * @param forum The forum to add
	 */
	public static void addForum(Forum forum)
	{
		forumsMap.put(new Integer(forum.getId()), forum);
	}
	
	public static synchronized void removeForum(Forum forum)
	{
		ForumRepository.lastPostInfoMap.remove(Integer.toString(forum.getId()));
		ForumRepository.forumsMap.remove(new Integer(forum.getId()));
	}
	
	public static synchronized void reloadForum(int forumId) throws Exception
	{
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		
		Forum f = fm.selectById(forumId);
		if (forumsMap.containsKey(new Integer(forumId))) {
			ForumRepository.forumsMap.put(new Integer(forumId), f);
			ForumRepository.lastPostInfoMap.remove(Integer.toString(forumId));
			ForumRepository.getLastPostInfo(forumId);
		}
		
		getTotalMessages(true);
	}
	
	public static HashMap getLastPostInfo(int forumId) throws Exception
	{
		if (ForumRepository.lastPostInfoMap.containsKey(Integer.toString(forumId)) == false) {
			ForumRepository.lastPostInfoMap.put(Integer.toString(forumId), DataAccessDriver.getInstance().newForumModel().getLastPostInfo(forumId));
		}
		
		return ((HashMap)ForumRepository.lastPostInfoMap.get(Integer.toString(forumId)));
	}
	
	public static int getTotalTopics(int forumId, boolean fromDb) throws Exception
	{
		if (fromDb || ForumRepository.totalTopics == -1) {
			ForumRepository.totalTopics = DataAccessDriver.getInstance().newForumModel().getTotalTopics(forumId);
		}
		
		return ForumRepository.totalTopics;
	}
	
	public static int getTotalMessages() throws Exception
	{
		return getTotalMessages(false);
	}
	
	public static int getTotalMessages(boolean fromDb) throws Exception
	{
		if (fromDb || ForumRepository.totalMessages == 0) {
			ForumRepository.totalMessages = DataAccessDriver.getInstance().newForumModel().getTotalMessages();
		}
		
		return ForumRepository.totalMessages;
	}
	
	public static int getTotalTopics(int forumId) throws Exception
	{
		return ForumRepository.getTotalTopics(forumId, false); 
	}
}
