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
 * This file creation date: Mar 3, 2003 / 2:19:47 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.generic;

import net.jforum.drivers.generic.security.GroupSecurityModel;
import net.jforum.drivers.generic.security.UserSecurityModel;

/**
 * @author Rafael Steil
 * @version $Id: DataAccessDriver.java,v 1.9 2005/02/03 12:37:39 rafaelsteil Exp $
 */
public class DataAccessDriver extends net.jforum.model.DataAccessDriver 
{
	private static GroupModel groupModel = new GroupModel();
	private static PostModel postModel = new PostModel();
	private static RankingModel rankingModel = new RankingModel();
	private static TopicModel topicModel = new TopicModel();
	private static UserModel userModel = new UserModel();
	private static TreeGroupModel treeGroupModel = new TreeGroupModel();
	private static SmilieModel smilieModel = new SmilieModel();
	private static SearchModel searchModel = new SearchModel();
	private static UserSecurityModel userSecurityModel = new UserSecurityModel();
	private static GroupSecurityModel groupSecurityModel = new GroupSecurityModel();
	private static PrivateMessageModel privateMessageModel = new PrivateMessageModel();
	private static UserSessionModel userSessionModel = new UserSessionModel();
	private static KarmaModel karmaModel = new KarmaModel();
	private static BookmarkModel bookmarkModel = new BookmarkModel();
	private static AttachmentModel attachmentModel = new AttachmentModel();
	private static ModerationModel moderationModel = new ModerationModel();
	private static ForumModel forumModel = new ForumModel();
	private static CategoryModel categoryModel = new CategoryModel();
	private static ConfigModel configModel = new ConfigModel();
	
	/**
	 * @see net.jforum.model.DataAccessDriver#getForumModel()
	 */
	public net.jforum.model.ForumModel newForumModel() 
	{
		return forumModel;	
	}

	/**
	 * @see net.jforum.model.DataAccessDriver#getGroupModel()
	 */
	public net.jforum.model.GroupModel newGroupModel() 
	{
		return groupModel;
	}

	/**
	 * @see net.jforum.model.DataAccessDriver#getPostModel()
	 */
	public net.jforum.model.PostModel newPostModel() 
	{
		return postModel;
	}

	/**
	 * @see net.jforum.model.DataAccessDriver#getRankingModel()
	 */
	public net.jforum.model.RankingModel newRankingModel() 
	{	
		return rankingModel;
	}

	/**
	 * @see net.jforum.model.DataAccessDriver#getTopicModel()
	 */
	public net.jforum.model.TopicModel newTopicModel() 
	{
		return topicModel;
	}

	/**
	 * @see net.jforum.model.DataAccessDriver#getUserModel()
	 */
	public net.jforum.model.UserModel newUserModel() 
	{
		return userModel;
	}

	/**
	 * @see net.jforum.model.DataAccessDriver#newCategoryModel()
	 */
	public net.jforum.model.CategoryModel newCategoryModel() 
	{
		return categoryModel;
	}

	/**
	 * @see net.jforum.model.DataAccessDriver#newTreeGroupModel()
	 */
	public net.jforum.model.TreeGroupModel newTreeGroupModel() 
	{
		return treeGroupModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newSmilieModel()
	 */
	public net.jforum.model.SmilieModel newSmilieModel() 
	{
		return smilieModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newSearchModel()
	 */
	public net.jforum.model.SearchModel newSearchModel() 
	{
		return searchModel;
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
	 * @see net.jforum.model.DataAccessDriver#newUserSecurityModel()
	 */
	public net.jforum.model.PrivateMessageModel newPrivateMessageModel() 
	{
		return privateMessageModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newUserSessionModel()
	 */
	public net.jforum.model.UserSessionModel newUserSessionModel()
	{
		return userSessionModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newConfigModel()
	 */
	public net.jforum.model.ConfigModel newConfigModel()
	{
		return configModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newKarmaModel()
	 */
	public net.jforum.model.KarmaModel newKarmaModel()
	{
		return karmaModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newBookmarkModel()
	 */
	public net.jforum.model.BookmarkModel newBookmarkModel()
	{
		return bookmarkModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newAttachmentModel()
	 */
	public net.jforum.model.AttachmentModel newAttachmentModel()
	{
		return attachmentModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newModerationModel()
	 */
	public net.jforum.model.ModerationModel newModerationModel()
	{
		return moderationModel;
	}
}
