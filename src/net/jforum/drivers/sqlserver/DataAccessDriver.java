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
 * Created on 29/05/2004 00:12:37
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.sqlserver;

import net.jforum.drivers.sqlserver.CategoryModel;
import net.jforum.drivers.sqlserver.ForumModel;
import net.jforum.drivers.sqlserver.PostModel;
import net.jforum.drivers.sqlserver.PrivateMessageModel;
import net.jforum.drivers.sqlserver.SearchModel;
import net.jforum.drivers.sqlserver.SmilieModel;
import net.jforum.drivers.sqlserver.TopicModel;
import net.jforum.drivers.sqlserver.UserModel;
import net.jforum.drivers.sqlserver.security.GroupSecurityModel;
import net.jforum.drivers.sqlserver.security.UserSecurityModel;

/**
 * @author Andre de Andrade da Silva - andre.de.andrade@gmail.com
 * @version $Id: DataAccessDriver.java,v 1.3 2005/02/17 19:14:08 franklin_samir Exp $
 */
public class DataAccessDriver extends net.jforum.drivers.generic.DataAccessDriver
{
	private static PostModel postModel = new PostModel();
	private static TopicModel topicModel = new TopicModel();
	private static ForumModel forumModel = new ForumModel();
	private static SearchModel searchModel = new SearchModel();
	private static SmilieModel smilieModel = new SmilieModel();
	private static UserModel userModel = new UserModel();
	private static GroupSecurityModel groupSecurityModel = new GroupSecurityModel();
	private static UserSecurityModel userSecurityModel = new UserSecurityModel();
	private static PrivateMessageModel pmModel = new PrivateMessageModel();
	private static CategoryModel categoryModel = new CategoryModel();
	private static KarmaModel karmaModel = new KarmaModel();

	/** 
	 * @see net.jforum.model.DataAccessDriver#newPostModel()
	 */
	public net.jforum.model.PostModel newPostModel()
	{
		return postModel;
	}

	/** 
	 * @see net.jforum.model.DataAccessDriver#newTopicModel()
	 */
	public net.jforum.model.TopicModel newTopicModel()
	{
		return topicModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newForumModel()
	 */
	public net.jforum.model.ForumModel newForumModel()
	{
	    return forumModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newSearchModel()
	 */
	public net.jforum.model.SearchModel newSearchModel()
	{
		return searchModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newSmilieModel()
	 */
	public net.jforum.model.SmilieModel newSmilieModel()
	{
		return smilieModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newUserModel()
	 */
	public net.jforum.model.UserModel newUserModel()
	{
		return userModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newGroupSecurityModel()
	 */
	public net.jforum.model.security.GroupSecurityModel newGroupSecurityModel()
	{
		return groupSecurityModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newUserSecurityModel()
	 */
	public net.jforum.model.security.UserSecurityModel newUserSecurityModel()
	{
		return userSecurityModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newCategoryModel()
	 */
	public net.jforum.model.CategoryModel newCategoryModel()
	{
		return categoryModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newPrivateMessageModel()
	 */
	public net.jforum.model.PrivateMessageModel newPrivateMessageModel()
	{
		return pmModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newKarmaModel()
	 */
	public net.jforum.model.KarmaModel newKarmaModel()
	{
		return karmaModel;
	}

}
