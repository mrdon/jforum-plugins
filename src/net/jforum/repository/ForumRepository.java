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
import java.util.Set;
import java.util.TreeSet;

import net.jforum.SessionFacade;
import net.jforum.entities.Category;
import net.jforum.entities.Forum;
import net.jforum.entities.LastPostInfo;
import net.jforum.exceptions.CategoryNotFoundException;
import net.jforum.model.CategoryModel;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.ForumModel;
import net.jforum.security.PermissionControl;
import net.jforum.security.SecurityConstants;
import net.jforum.util.CategoryOrderComparator;

/**
 * Repository for the forums of the System.
 * This repository acts like a cache system, to avoid repetitive and unnecessary SQL queries
 * every time we need some info about the forums. 
 * To start the repository, call the method <code>start(ForumModel, CategoryModel)</code>
 * 
 * @author Rafael Steil
 * @version  $Id: ForumRepository.java,v 1.23 2004/12/18 15:00:49 rafaelsteil Exp $
 */
public class ForumRepository 
{
	private static Map forumCategoryRelation = new HashMap();
	private static Map categoriesMap = new HashMap();
	private static Set categoriesSet = new TreeSet(new CategoryOrderComparator());
	private static int totalTopics = -1;
	private static int totalMessages = 0;

	private static ForumRepository instance;
	
	/**
	 * Starts the repository.
	 * 
	 * @param fm The <code>ForumModel</code> instance which will be 
	 * used to retrieve information about the forums.
	 * @param cm The <code>CategoryModel</code> instance which will 
	 * be used to retrieve information about the categories.
	 * @throws Exception
	 */
	public synchronized static void start(ForumModel fm, CategoryModel cm) throws Exception
	{
		instance = new ForumRepository();
		
		instance.loadCategories(cm);
		instance.loadForums(fm);
	}
	
	/**
	 * Gets a category by its id.
	 * A call to @link #getCategory(int, int) is made, using the
	 * return of <code>SessionFacade.getUserSession().getUserId()</code>
	 * as argument for the "userId" parameter.
	 * 
	 * @param categoryId The id of the category to check
	 * @return <code>null</code> if the category is either not
	 * found or access is denied.
	 * @see #getCategory(int, int)
	 */
	public static Category getCategory(int categoryId)
	{
		return getCategory(SessionFacade.getUserSession().getUserId(), categoryId);
	}

	/**
	 * Gets a category by its id.
	 *  
	 * @param userId The user id who is requesting the category
	 * @param categoryId The id of the category to get
	 * @return <code>null</code> if the category is either not
	 * found or access is denied.
	 * @see #getCategory(int)
	 */
	public static Category getCategory(int userId, int categoryId)
	{
		if (!isCategoryAccessible(userId, categoryId)) {
			return null;
		}
		
		return (Category)categoriesMap.get(new Integer(categoryId));
	}
	
	/**
	 * Check is some category is accessible.
	 * 
	 * @param userId The user's id who is trying to get the category
	 * @param categoryId The category's id to check for access rights
	 * @return <code>true</code> if access to the category is allowed.
	 */
	public static boolean isCategoryAccessible(int userId, int categoryId)
	{
		return isCategoryAccessible(SecurityRepository.get(userId), categoryId);
	}
	
	/**
	 * Check is some category is accessible.
	 * 
	 * @param pc The <code>PermissionControl</code> instance containing
	 * all security info related to the user.
	 * @param categoryId the category's id to check for access rights
	 * @return <code>true</code> if access to the category is allowed.
	 */
	public static boolean isCategoryAccessible(PermissionControl pc, int categoryId)
	{
		return pc.canAccess(SecurityConstants.PERM_CATEGORY, Integer.toString(categoryId));
	}
	
	/**
	 * Gets all categories from the cache. 
	 * 
	 * @return <code>List</code> with the categories. Each entry is a <code>Category</code> object.
	 */
	public static List getAllCategories(int userId)
	{
		PermissionControl pc = SecurityRepository.get(userId);
		List l = new ArrayList();
		
		Iterator iter = categoriesSet.iterator();
		while (iter.hasNext()) {
			Category c = (Category)iter.next();
			
			if (isCategoryAccessible(pc, c.getId())) {
				l.add(c);
			}
		}
		
		return l;
	}

	/**
	 * Get all categories.
	 * A call to @link #getAllCategories(int) is made, passing
	 * the return of <code>SessionFacade.getUserSession().getUserId()</code> 
	 * as the value for the "userId" argument.
	 * 
	 * @return <code>List</code> with the categories. Each entry is a <code>Category</code> object.
	 * @see #getAllCategories(int)
	 */
	public static List getAllCategories()
	{
		return getAllCategories(SessionFacade.getUserSession().getUserId());
	}
	
	private static Category findCategoryByOrder(int order)
	{
		for (Iterator iter = categoriesSet.iterator(); iter.hasNext(); ) {
			Category c = (Category)iter.next();
			if (c.getOrder() == order) {
				return c;
			}
		}
		
		return null;
	}

	/**
	 * Updates some category.
	 * This method only updated the "name" and "order" fields. 
	 *  
	 * @param c The category to update. The method will search for a category
	 * with the same id and update its data.
	 */
	public synchronized static void reloadCategory(Category c)
	{
		Category current = (Category)categoriesMap.get(new Integer(c.getId()));
		Category currentAtOrder = findCategoryByOrder(c.getOrder());
		
		Set tmpSet = new TreeSet(new CategoryOrderComparator());
		tmpSet.addAll(categoriesSet);
		
		if (currentAtOrder != null) {
			tmpSet.remove(currentAtOrder);
		}
		
		tmpSet.add(c);
		
		if (currentAtOrder != null) {
			tmpSet.remove(current);
			currentAtOrder.setOrder(current.getOrder());
			tmpSet.add(currentAtOrder);
		}
		
		categoriesSet = tmpSet;
	}
	
	/**
	 * Remove a category from the cache
	 * @param c The category to remove. The instance should have the 
	 * category id at least
	 */
	public synchronized static void removeCategory(Category c)
	{
		categoriesMap.remove(new Integer(c.getId()));
		categoriesSet.remove(c);
		
		for (Iterator iter = forumCategoryRelation.values().iterator(); iter.hasNext(); ) {
			int id = ((Integer)iter.next()).intValue();
			if (id == c.getId()) {
				iter.remove();
			}
		}
	}
	
	/**
	 * Adds a new category to the cache.
	 * @param c The category instance to insert in the cache.
	 */
	public synchronized static void addCategory(Category c)
	{
		categoriesMap.put(new Integer(c.getId()), c);
		categoriesSet.add(c);
	}
	
	/**
	 * Gets a specific forum from the cache.	 
	 * 
	 * @param forumId The forum's ID to get
	 * @return <code>net.jforum.Forum</code> object instance or <code>null</code>
	 * if the forum was not found or is not accessible to the user.
	 */
	public static Forum getForum(int forumId)
	{
		int categoryId = ((Integer)forumCategoryRelation.get(new Integer(forumId))).intValue();
		return ((Category)categoriesMap.get(new Integer(categoryId))).getForum(forumId);
	}
	
	public static boolean isForumAccessible(int forumId)
	{
		return isForumAccessible(SessionFacade.getUserSession().getUserId(), forumId);
	}
	
	public static boolean isForumAccessible(int userId, int forumId)
	{
		int categoryId = ((Integer)forumCategoryRelation.get(new Integer(forumId))).intValue();
		return isForumAccessible(userId, categoryId, forumId);
	}
	
	public static boolean isForumAccessible(int userId, int categoryId, int forumId)
	{
		return ((Category)categoriesMap.get(new Integer(categoryId))).getForum(userId, forumId) != null;
	}
	
	/**
	 * Adds a new forum to the cache repository.	 
	 * 
	 * @param forum The forum to add
	 */
	public synchronized static void addForum(Forum forum)
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
	public synchronized static void removeForum(Forum forum)
	{
		Integer id = new Integer(forum.getId());
		forumCategoryRelation.remove(id);
		getCategory(forum.getCategoryId()).removeForum(forum.getId());
	}
	
	/**
	 * Reloads a forum.
	 * The forum should already be in the cache and <b>SHOULD NOT</b>
	 * have its order changed. If the forum's order was changed, 
	 * then you <b>MUST CALL</b> @link Category#changeForumOrder(Forum) <b>BEFORE</b>
	 * calling this method.
	 * 
	 * @param forum The forum to reload its information
	 * @throws Exception
	 */
	public static synchronized void reloadForum(int forumId) throws Exception
	{
		Forum f = DataAccessDriver.getInstance().newForumModel().selectById(forumId);
		
		if (forumCategoryRelation.containsKey(new Integer(f.getId()))) {
			Category c = (Category)categoriesMap.get(new Integer(f.getCategoryId()));
			
			f.setLastPostInfo(null);
			f.setLastPostInfo(ForumRepository.getLastPostInfo(f));
			c.reloadForum(f);
		}
		
		getTotalMessages(true);
	}
	
	/**
	 * Gets information about the last message posted in some forum.
	 * @param forum The forum to retrieve information
	 * @return 
	 */
	public static LastPostInfo getLastPostInfo(Forum forum) throws Exception
	{
		LastPostInfo lpi = forum.getLastPostInfo();
		
		if (lpi == null || !forum.getLastPostInfo().hasInfo()) {
			lpi = DataAccessDriver.getInstance().newForumModel().getLastPostInfo(forum.getId());
			forum.setLastPostInfo(lpi);
		}
		
		return lpi;
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
		return getLastPostInfo(getForum(forumId));
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
	 * @throws Exception
	 */
	private void loadForums(ForumModel fm) throws Exception
	{
		forumCategoryRelation = new HashMap();
		List l = fm.selectAll();

		for (Iterator iter = l.iterator(); iter.hasNext(); ) {
			Forum f = (Forum)iter.next();
			Category c = (Category)categoriesMap.get(new Integer(f.getCategoryId()));
			
			if (c == null) {
				throw new CategoryNotFoundException("Category for forum #" + f.getId() + " not found");
			}
			
			c.addForum(f);
			forumCategoryRelation.put(new Integer(f.getId()), new Integer(c.getId()));
		}
	}

	/**
	 * Loads all categories.
	 * @throws Exception 
	 */
	private void loadCategories(CategoryModel cm) throws Exception
	{
		List categories = cm.selectAll();
		categoriesMap = new LinkedHashMap();
		categoriesSet = new TreeSet(new CategoryOrderComparator());
		for (Iterator iter = categories.iterator(); iter.hasNext(); ) {
			Category c = (Category)iter.next();
			categoriesMap.put(new Integer(c.getId()), c);
			categoriesSet.add(c);
		}
	}
}
