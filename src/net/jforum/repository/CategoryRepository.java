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
 * This file creation date: Apr 24, 2003 / 8:35:17 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.jforum.entities.Category;
import net.jforum.ForumException;
import net.jforum.SessionFacade;
import net.jforum.model.DataAccessDriver;
import net.jforum.security.PermissionControl;
import net.jforum.security.SecurityConstants;

/**
 * Repository for the categories.
 * Yes, it's just a cache.
 * 
 * @author Rafael Steil
 * @version $Id: CategoryRepository.java,v 1.3 2004/11/05 03:29:48 rafaelsteil Exp $
 */
public class CategoryRepository 
{
	private static List categoriesList = new ArrayList();
	
	static {
		try {
			CategoryRepository.loadCategories();
		}
		catch (Exception e) {
			new ForumException(e);
		}
	}
	
	private CategoryRepository() {}

	/**
	 * Gets a category
	 * 
	 * @param categoryId The ID of the category to get
	 * @return The <code>Category</code> object
	 */
	public static Category getCategory(int categoryId)
	{
		Category c = new Category();
		
		Iterator iter = CategoryRepository.getAllCategories().iterator();
		while (iter.hasNext()) {
			c = (Category)iter.next();
			
			if (c.getId() == categoryId) {
				return c;
			}
		}
		
		return c;
	}
	
	/**
	 * Gets all categories from the cache. 
	 * 
	 * @return <code>ArrayList</code> with the categories. Each entry is a <code>Category</code> object.
	 */
	public static List getAllCategories()
	{
		return CategoryRepository.getAllCategories(false);
	}
	
	/**
	 * Gets all categories from the cache. 
	 * 
	 * @param ignorePermissions If <code>true</code>, all categories are returned, even if the
	 * user doesn't have access to someone. If <code>false</code>, the permission control is applied.
	 * 
	 * @return <code>ArrayList</code> with the categories. Each entry is a <code>Category</code> object.
	 */
	public static List getAllCategories(boolean ignorePermissions)
	{
		if (ignorePermissions) {
			return CategoryRepository.categoriesList;
		}
		
		ArrayList l = new ArrayList();
		int userId = SessionFacade.getUserSession().getUserId();
		PermissionControl pc = SecurityRepository.get(userId);
		
		Iterator iter = CategoryRepository.categoriesList.iterator();
		while (iter.hasNext()) {
			Category c = (Category)iter.next();
			
			if (pc.canAccess(SecurityConstants.PERM_CATEGORY, Integer.toString(c.getId()))) {
				l.add(c);
			}
		}
		
		return l;
	}

	public static synchronized void loadCategories() throws Exception
	{		
		CategoryRepository.categoriesList = DataAccessDriver.getInstance().newCategoryModel().selectAll();
	}
}
