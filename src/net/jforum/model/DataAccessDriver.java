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
 * This file creation date: Mar 3, 2003 / 1:37:05 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.model;

import java.sql.Connection;

import net.jforum.exceptions.ForumException;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * The class that every driver class must implement.
 * JForum implementation provides a simple and extremely
 * configurable way to use diferent database engines without
 * any modification to the core source code. 
 * <br>
 * For example, if you want to use the Database "XYZ" as
 * backend, all you need to do is to implement <code>DataAccessDriver</code>,
 * all *Model classes and a specific file with the SQL queries. 
 * <br>
 * The default implementation was written to support MySQL, so if you want a base code to
 * analise, look at <code>net.jforum.drivers.generic</code> package.
 * 
 * @author Rafael Steil
 * @version $Id: DataAccessDriver.java,v 1.8 2004/11/21 17:13:46 rafaelsteil Exp $
 */
public abstract class DataAccessDriver 
{
	private static DataAccessDriver driver;
	
	protected DataAccessDriver() {}
	
	/**
	 * Gets a driver implementation instance. 
	 * You MUST use this method when you want a instance
	 * of a valid <code>DataAccessDriver</code>. Never access
	 * the driver implementation directly.  
	 * 
	 * @return <code>DataAccessDriver</code> instance
	 */
	public final static DataAccessDriver getInstance()
	{
		if (driver == null) {
			try {
				driver = (DataAccessDriver)Class.forName(SystemGlobals.getValue(ConfigKeys.DAO_DRIVER)).newInstance();
			}
			catch (Exception e) {
				throw new ForumException(e);
			}			
		}
		
		return driver;
	}
	 
	/**
	 * Gets a {@link net.jforum.model.ForumModel} instance. 
	 * 
	 * @param conn The connection instance to pass to the class
	 * @return <code>net.jforum.model.ForumModel</code> instance
	 */
	public abstract net.jforum.model.ForumModel newForumModel(Connection conn);
	
	/**
	 * Gets a {@link net.jforum.model.ForumModel} instance. 
	 * 
	 * @return <code>net.jforum.model.ForumModel</code> instance
	 */
	public abstract net.jforum.model.ForumModel newForumModel();
	
	/**
	 * Gets a {@link net.jforum.model.GroupModel} instance
	 * 
	 * @return <code>net.jforum.model.GroupModel</code> instance.
	 */
	public abstract  net.jforum.model.GroupModel newGroupModel();
	
	/**
	 * Gets a {@link net.jforum.model.PostModel} instance.
	 * 
	 * @return <code>net.jforum.model.PostModel</code> instance.
	 */
	public abstract  net.jforum.model.PostModel newPostModel();
	
	/**
	 * Gets a {@link net.jforum.model.RankingModel} instance.
	 * 
	 * @return <code>net.jforum.model.RankingModel</code> instance
	 */
	public abstract  net.jforum.model.RankingModel newRankingModel();
	
	/**
	 * Gets a {@link net.jforum.model.TopicModel} instance.
	 * 
	 * @return <code>net.jforum.model.TopicModel</code> instance.
	 */
	public abstract  net.jforum.model.TopicModel newTopicModel();
	
	/**
	 * Gets an {@link net.jforum.model.UserModel} instance.
	 * 
	 * @return <code>net.jforum.model.UserModel</code> instance.
	 */
	public abstract  net.jforum.model.UserModel newUserModel();
	
	/**
	 * Gets an {@link net.jforum.model.CategoryModel} instance.
	 * 
	 * @return <code>net.jforum.model.CategoryModel</code> instance.
	 */
	public abstract  net.jforum.model.CategoryModel newCategoryModel();
	
	/**
	 * Gets an {@link net.jforum.model.CategoryModel} instance.
	 * 
	 * @param conn The connection instance to pass to the class
	 * @return <code>net.jforum.model.CategoryModel</code> instance.
	 */
	public abstract  net.jforum.model.CategoryModel newCategoryModel(Connection conn);
	
	/**
	 * Gets an {@link net.jforum.model.TreeGroupModel} instance
	 * 
	 * @return <code>net.jforum.model.TreeGroupModel</code> instance.
	 */
	public abstract net.jforum.model.TreeGroupModel newTreeGroupModel();

	/**
	 * Gets a {@link net.jforum.model.SmilieModel} instance
	 * 
	 * @return <code>net.jforum.model.SmilieModel</code> instance.
	 */
	public abstract net.jforum.model.SmilieModel newSmilieModel();
	
	/**
	 * Gets a {@link net.jforum.model.SearchModel} instance
	 * 
	 * @return <code>net.jforum.model.SearchModel</code> instance
	 */
	public abstract net.jforum.model.SearchModel newSearchModel();
	
	/**
	 * Gets a {@link net.jforum.model.security.UserSecurityModel} instance
	 * 
	 * @return <code>net.jforum.model.security.UserSecurityModel</code> instance
	 */
	public abstract net.jforum.model.security.UserSecurityModel newUserSecurityModel();
	
	/**
	 * Gets a {@link net.jforum.model.security.GroupSecurityModel} instance
	 * 
	 * @return <code>net.jforum.model.security.GroupSecurityModel</code> instance
	 */
	public abstract net.jforum.model.security.GroupSecurityModel newGroupSecurityModel();

	/**
	 * Gets a {@link net.jforum.model.security.PrivateMessageModel} instance
	 * 
	 * @return <code>link net.jforum.model.security.PrivateMessageModel</code> instance
	 */
	public abstract net.jforum.model.PrivateMessageModel newPrivateMessageModel();
	
	/**
	 * Gets a {@link net.jforum.model.UserSessionModel} instance
	 * 
	 * @return <code>link net.jforum.model.UserSessionModel</code> instance
	 */
	public abstract net.jforum.model.UserSessionModel newUserSessionModel();
}
