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
package net.jforum.dao.generic;

import net.jforum.dao.generic.security.GenericGroupSecurityDAO;
import net.jforum.dao.generic.security.GenericUserSecurityDAO;

/**
 * @author Rafael Steil
 * @version $Id: DataAccessDriver.java,v 1.3 2005/04/01 14:34:43 samuelyung Exp $
 */
public class DataAccessDriver extends net.jforum.dao.DataAccessDriver 
{
	private static GroupModel groupModel = new GroupModel();
	private static PostModel postModel = new PostModel();
	private static GenericRankingDAO rankingModel = new GenericRankingDAO();
	private static GenericTopicModelDAO topicModel = new GenericTopicModelDAO();
	private static GenericUserDAO userModel = new GenericUserDAO();
	private static GenericTreeGroupDAO treeGroupModel = new GenericTreeGroupDAO();
	private static GenericSmilieDAO smilieModel = new GenericSmilieDAO();
	private static GenericSearchDAO searchModel = new GenericSearchDAO();
	private static GenericUserSecurityDAO userSecurityModel = new GenericUserSecurityDAO();
	private static GenericGroupSecurityDAO groupSecurityModel = new GenericGroupSecurityDAO();
	private static PrivateMessageModel privateMessageModel = new PrivateMessageModel();
	private static GenericUserSessionDAO userSessionModel = new GenericUserSessionDAO();
	private static KarmaModel karmaModel = new KarmaModel();
	private static GenericBookmarkDAO bookmarkModel = new GenericBookmarkDAO();
	private static GenericAttachmentDAO attachmentModel = new GenericAttachmentDAO();
	private static ModerationModel moderationModel = new ModerationModel();
	private static GenericForumDAO forumModel = new GenericForumDAO();
	private static GenericCategoryDAO categoryModel = new GenericCategoryDAO();
	private static GenericConfigDAO configModel = new GenericConfigDAO();
	private static GenericScheduledSearchIndexerDAO ssim = new GenericScheduledSearchIndexerDAO();
	private static GenericBannerDAO bannerDao = new GenericBannerDAO();

	/**
	 * @see net.jforum.dao.DataAccessDriver#getForumModel()
	 */
	public net.jforum.dao.ForumDAO newForumDAO() 
	{
		return forumModel;	
	}

	/**
	 * @see net.jforum.dao.DataAccessDriver#getGroupModel()
	 */
	public net.jforum.dao.GroupDAO newGroupDAO() 
	{
		return groupModel;
	}

	/**
	 * @see net.jforum.dao.DataAccessDriver#getPostModel()
	 */
	public net.jforum.dao.PostDAO newPostDAO() 
	{
		return postModel;
	}

	/**
	 * @see net.jforum.dao.DataAccessDriver#getRankingModel()
	 */
	public net.jforum.dao.RankingDAO newRankingDAO() 
	{	
		return rankingModel;
	}

	/**
	 * @see net.jforum.dao.DataAccessDriver#getTopicModel()
	 */
	public net.jforum.dao.TopicDAO newTopicDAO() 
	{
		return topicModel;
	}

	/**
	 * @see net.jforum.dao.DataAccessDriver#getUserModel()
	 */
	public net.jforum.dao.UserDAO newUserDAO() 
	{
		return userModel;
	}

	/**
	 * @see net.jforum.dao.DataAccessDriver#newCategoryDAO()
	 */
	public net.jforum.dao.CategoryDAO newCategoryDAO() 
	{
		return categoryModel;
	}

	/**
	 * @see net.jforum.dao.DataAccessDriver#newTreeGroupDAO()
	 */
	public net.jforum.dao.TreeGroupDAO newTreeGroupDAO() 
	{
		return treeGroupModel;
	}
	
	/** 
	 * @see net.jforum.dao.DataAccessDriver#newSmilieDAO()
	 */
	public net.jforum.dao.SmilieDAO newSmilieDAO() 
	{
		return smilieModel;
	}
	
	/** 
	 * @see net.jforum.dao.DataAccessDriver#newSearchDAO()
	 */
	public net.jforum.dao.SearchDAO newSearchDAO() 
	{
		return searchModel;
	}
	
	/** 
	 * @see net.jforum.dao.DataAccessDriver#newSearchIndexerDAO()
	 */
	public net.jforum.dao.SearchIndexerDAO newSearchIndexerDAO() 
	{
		return new GenericSearchIndexerDAO();
	}
	
	/** 
	 * @see net.jforum.dao.DataAccessDriver#newGroupSecurityDAO()
	 */
	public net.jforum.dao.security.GroupSecurityDAO newGroupSecurityDAO() 
	{
		return groupSecurityModel;
	}

	/** 
	 * @see net.jforum.dao.DataAccessDriver#newUserSecurityDAO()
	 */
	public net.jforum.dao.security.UserSecurityDAO newUserSecurityDAO() 
	{
		return userSecurityModel;
	}

	/** 
	 * @see net.jforum.dao.DataAccessDriver#newUserSecurityDAO()
	 */
	public net.jforum.dao.PrivateMessageDAO newPrivateMessageDAO() 
	{
		return privateMessageModel;
	}
	
	/** 
	 * @see net.jforum.dao.DataAccessDriver#newUserSessionDAO()
	 */
	public net.jforum.dao.UserSessionDAO newUserSessionDAO()
	{
		return userSessionModel;
	}
	
	/** 
	 * @see net.jforum.dao.DataAccessDriver#newConfigDAO()
	 */
	public net.jforum.dao.ConfigDAO newConfigDAO()
	{
		return configModel;
	}
	
	/** 
	 * @see net.jforum.dao.DataAccessDriver#newKarmaDAO()
	 */
	public net.jforum.dao.KarmaDAO newKarmaDAO()
	{
		return karmaModel;
	}
	
	/** 
	 * @see net.jforum.dao.DataAccessDriver#newBookmarkDAO()
	 */
	public net.jforum.dao.BookmarkDAO newBookmarkDAO()
	{
		return bookmarkModel;
	}
	
	/** 
	 * @see net.jforum.dao.DataAccessDriver#newAttachmentDAO()
	 */
	public net.jforum.dao.AttachmentDAO newAttachmentDAO()
	{
		return attachmentModel;
	}
	
	/** 
	 * @see net.jforum.dao.DataAccessDriver#newModerationDAO()
	 */
	public net.jforum.dao.ModerationDAO newModerationDAO()
	{
		return moderationModel;
	}
	
	/**
	 * @see net.jforum.dao.DataAccessDriver#newScheduledSearchIndexerDAO()
	 */
	public net.jforum.dao.ScheduledSearchIndexerDAO newScheduledSearchIndexerDAO()
	{
		return ssim;
	}

	public net.jforum.dao.BannerDAO newBannerDAO()
	{
		return bannerDao;
	}
}
