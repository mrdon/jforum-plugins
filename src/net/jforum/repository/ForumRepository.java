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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.jforum.SessionFacade;
import net.jforum.entities.Category;
import net.jforum.entities.Forum;
import net.jforum.entities.LastPostInfo;
import net.jforum.exceptions.CategoryNotFoundException;
import net.jforum.exceptions.LoadForumException;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.ForumModel;
import net.jforum.security.PermissionControl;
import net.jforum.security.SecurityConstants;

/**
 * Repository for the forums of the System.
 * This repository acts like a cache system, to avoid repetitive and unnecessary SQL queries
 * every time we need some info about the forums. Using the repository, we put process information
 * needed just once, and then use the cache when data is requested. 
 * 
 * @author Rafael Steil
 * @version  $Id: ForumRepository.java,v 1.12 2004/11/13 03:14:05 rafaelsteil Exp $
 */
public class ForumRepository 
{
	private static Map forumCategoryRelation = new HashMap();
	private static Map categoriesMap = new LinkedHashMap();
	private static int totalTopics = -1;
	private static int totalMessages = 0;
	
	static {
		try {
			ForumRepository.loadForums();
		}
		catch (Exception e) {
			throw new LoadForumException("Error while trying to load forums information: " + e);
		}
	}
	
	/**
	 * Checks if the current user has access rights to the forum.
	 * 
	 * @param categoryId The id of the category to which the forum belongs to
	 * @param forumId The forum id to check
	 * @return <code>true</code> if access is allowed, and <code>false</code> in all
	 * other cases. 
	 */
	public static boolean isForumAccessible(int categoryId, int forumId)
	{
		Category c = (Category)categoriesMap.get(new Integer(categoryId));
		Forum f = c.getForum(forumId);
		
		int userId = SessionFacade.getUserSession().getUserId();
		PermissionControl pc = SecurityRepository.get(userId);
		
		return pc.canAccess(SecurityConstants.PERM_FORUM, Integer.toString(f.getId()));
	}

	/**
	 * Gets a <code>Category</code> by its id.
	 * 
	 * @param categoryId The id of the category to retrieve.
	 * @return The <code>Category</code> instance if found, or <code>null</code> 
	 * otherwise.
	 */
	public static Category getCategory(int categoryId)
	{
		return (Category)categoriesMap.get(new Integer(categoryId));
	}	
	
	/**
	 * Gets all categories from the cache. 
	 * 
	 * @param ignorePermissions If <code>true</code>, all categories are returned, even if the
	 * user doesn't have access to someone. If <code>false</code>, the permission control is applied.
	 * 
	 * @return <code>List</code> with the categories. Each entry is a <code>Category</code> object.
	 */
	public static List getAllCategories(boolean ignorePermissions)
	{
		if (ignorePermissions) {
			return new ArrayList(categoriesMap.values());
		}
		
		List l = new ArrayList();
		int userId = SessionFacade.getUserSession().getUserId();
		PermissionControl pc = SecurityRepository.get(userId);
		
		Iterator iter = categoriesMap.values().iterator();
		while (iter.hasNext()) {
			Category c = (Category)iter.next();
			
			if (pc.canAccess(SecurityConstants.PERM_CATEGORY, Integer.toString(c.getId()))) {
				l.add(new Category(c));
			}
		}
		
		return l;
	}
	
	/**
	 * @see #getAllCategories(boolean)
	 */
	public static List getAllCategories()
	{
		return getAllCategories(false);
	}

	/**
	 * Updates some category
	 * @param c The category to update. The method will search for a category
	 * with the same id and update its data.
	 * @throws <code>CategoryNotFoundException</code> if the category is not found in the cache. 
	 */
	public static void reloadCategory(Category c)
	{
		Category currentCategory = getCategory(c.getId());
		
		if (currentCategory == null) {
			throw new CategoryNotFoundException("Category #" + c.getId() + " was not found in the cache");
		}
		
		currentCategory.setName(c.getName());
		currentCategory.setOrder(c.getOrder());
	}
	
	/**
	 * Remove a category from the cache
	 * @param c The category to remove. The instance should have the 
	 * category id at least
	 */
	public static void removeCategory(Category c)
	{
		categoriesMap.remove(new Integer(c.getId()));
	}
	
	/**
	 * Adds a new category to the cache.
	 * @param c The category instance to insert in the cache.
	 */
	public static void addCategory(Category c)
	{
		categoriesMap.put(new Integer(c.getId()), c);
	}
	
	/**
	 * Gets a specific forum from the cache.	 
	 * 
	 * @param forumId The forum's ID to get
	 * @return <code>net.jforum.Forum</code> object instance
	 */
	public static Forum getForum(int forumId)
	{
		int categoryId = ((Integer)forumCategoryRelation.get(new Integer(forumId))).intValue();
		return getCategory(categoryId).getForum(forumId);
	}
	
	/**
	 * Adds a new forum to the cache repository.	 
	 * 
	 * @param forum The forum to add
	 */
	public static void addForum(Forum forum)
	{
		Integer categoryId = new Integer(forum.getCategoryId());
		((Category)categoriesMap.get(categoryId)).addForum(forum);
		forumCategoryRelation.put(new Integer(forum.getId()), categoryId);
	}
	
	/**
	 * Removes a forum from the cache.
	 * 
	 * @param forum The forum instance to remove.
	 */
	public static synchronized void removeForum(Forum forum)
	{
		Integer id = new Integer(forum.getId());
		forumCategoryRelation.remove(id);
		getCategory(forum.getCategoryId()).removeForum(forum.getId());
	}
	
	/**
	 * Reloads a forum.
	 * 
	 * @param forumId The id of the forum to reload its information
	 * @throws Exception
	 */
	public static synchronized void reloadForum(int forumId) throws Exception
	{
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		
		Forum f = fm.selectById(forumId);
		if (forumCategoryRelation.containsKey(new Integer(forumId))) {
			Category c = getCategory(f.getCategoryId());
			c.addForum(f);
			forumCategoryRelation.put(new Integer(f.getId()), new Integer(c.getId()));
			f.setLastPostInfo(null);
			ForumRepository.getLastPostInfo(forumId);
		}
		
		getTotalMessages(true);
	}
	
	/**
	 * Gets information about the last message posted in some forum.
	 * 
	 * @param forumId The forum's id to retrieve information
	 * @return
	 * @throws Exception
	 */
	public static LastPostInfo getLastPostInfo(int forumId) throws Exception
	{
		Forum forum = getForum(forumId);
		LastPostInfo lpi = forum.getLastPostInfo();
		
		if (lpi == null || !forum.getLastPostInfo().hasInfo()) {
			lpi = DataAccessDriver.getInstance().newForumModel().getLastPostInfo(forumId);
			forum.setLastPostInfo(lpi);
		}
		
		return lpi;
	}
	
	/**
	 * Gets the number of topics in some forum.
	 * 
	 * @param forumId The forum's id to retrieve the number of topics
	 * @param fromDb If <code>true</code>, a query to the database will be made 
	 * to get the number of topics. If <code>false</code>, the cached information
	 * will be returned
	 * @return The number of topics
	 * @throws Exception
	 * @see #getTotalTopics(int)
	 */
	public static int getTotalTopics(int forumId, boolean fromDb) throws Exception
	{
		if (fromDb || ForumRepository.totalTopics == -1) {
			ForumRepository.totalTopics = DataAccessDriver.getInstance().newForumModel().getTotalTopics(forumId);
		}
		
		return ForumRepository.totalTopics;
	}
	
	/**
	 * Gets the number of topics in some forum.
	 * @param forumId The forum's id to retrieve the number of topics
	 * @return The number of topics
	 * @throws Exception
	 * @see #getTotalTopics(int, boolean)
	 */
	public static int getTotalTopics(int forumId) throws Exception
	{
		return ForumRepository.getTotalTopics(forumId, false); 
	}
	
	/**
	 * Gets the number of messages in the entire board.
	 * @return 
	 * @throws Exception
	 * @see #getTotalMessages(boolean)
	 */
	public static int getTotalMessages() throws Exception
	{
		return getTotalMessages(false);
	}

	/**
	 * Gets the number of messags in the entire board.
	 * 
	 * @param fromDb If <code>true</code>, a query to the database will
	 * be made, to retrieve the desired information. If <code>false</code>, the
	 * data will be fetched from the cache.
	 * @return The number of messages posted in the board.
	 * @throws Exception
	 * @see #getTotalMessages()
	 */
	public static int getTotalMessages(boolean fromDb) throws Exception
	{
		if (fromDb || ForumRepository.totalMessages == 0) {
			ForumRepository.totalMessages = DataAccessDriver.getInstance().newForumModel().getTotalMessages();
		}
		
		return ForumRepository.totalMessages;
	}
	
	/**
	 * Loads all forums.
	 * 
	 * @throws Exception
	 */
	private static void loadForums() throws Exception
	{
		loadCategories();

		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		List l = fm.selectAll();

		for (Iterator iter = l.iterator(); iter.hasNext(); ) {
			Forum f = (Forum)iter.next();
			Category c = getCategory(f.getCategoryId());
			
			if (c == null) {
				throw new CategoryNotFoundException("Category for forum #" + f.getId() + " not found");
			}
			
			c.addForum(f);
			forumCategoryRelation.put(new Integer(f.getId()), new Integer(c.getId()));
		}
	}

	/**
	 * Loads all categories.
	 * 
	 * @throws Exception
	 */
	private static void loadCategories() throws Exception
	{
		List categories = DataAccessDriver.getInstance().newCategoryModel().selectAll();
		for (Iterator iter = categories.iterator(); iter.hasNext(); ) {
			Category c = (Category)iter.next();
			categoriesMap.put(new Integer(c.getId()), c);
		}
	}
}
