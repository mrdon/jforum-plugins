/*
 * Copyright (c) 2003, 2004 Rafael Steil
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
 * This file creation date: Apr 24, 2003 / 10:15:07 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.SessionFacade;
import net.jforum.entities.Forum;
import net.jforum.entities.Topic;
import net.jforum.entities.User;
import net.jforum.entities.UserSession;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.ForumModel;
import net.jforum.model.SearchData;
import net.jforum.model.UserModel;
import net.jforum.repository.ForumRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.SecurityConstants;
import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.view.forum.common.ForumCommon;
import net.jforum.view.forum.common.TopicsCommon;
import net.jforum.view.forum.common.ViewCommon;
/**
 * @author Rafael Steil
 * @version $Id: ForumAction.java,v 1.23 2004/12/29 14:48:12 rafaelsteil Exp $
 */
public class ForumAction extends Command 
{
	public void list() throws Exception
	{
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		
		this.context.put("allCategories", ForumCommon.getAllCategoriesAndForums(true));
		this.context.put("topicsPerPage",  new Integer(SystemGlobals.getIntValue(ConfigKeys.TOPICS_PER_PAGE)));
		this.context.put("moduleAction", "forum_list.htm");
		this.context.put("rssEnabled", SystemGlobals.getBoolValue(ConfigKeys.RSS_ENABLED));
		
		this.context.put("totalMessages", I18n.getMessage("ForumListing.totalMessagesInfo", 
						new Object[] {new Integer( ForumRepository.getTotalMessages() )}));
		
		this.context.put("totalUsers", I18n.getMessage("ForumListing.registeredUsers", new Object[] {new Integer(um.getTotalUsers())}));

		User lastUser = um.getLastUserInfo();		
		this.context.put("lastUserId", new Integer(lastUser.getId()));
		this.context.put("lastUserName", lastUser.getUsername());
		
		SimpleDateFormat df = new SimpleDateFormat(SystemGlobals.getValue(ConfigKeys.DATE_TIME_FORMAT));
		GregorianCalendar gc = new GregorianCalendar();
		this.context.put("now", df.format(gc.getTime()));
		
		this.context.put("lastVisit", df.format(SessionFacade.getUserSession().getLastVisit()));
		this.context.put("fir", new ForumRepository());
		
		// Online Users
		this.context.put("totalOnlineUsers", new Integer(SessionFacade.size()));
		int guest = 0;
		int registered = 0;
		int aid = SystemGlobals.getIntValue(ConfigKeys.ANONYMOUS_USER_ID);
	
		List userSessions = SessionFacade.getAllSessions();
		List onlineUsersList = new ArrayList();
		for (Iterator iter = userSessions.iterator(); iter.hasNext(); ) {
			UserSession us = (UserSession)iter.next();
			
			if (us.getUserId() == aid) {
				guest++;
			}
			else {
				registered++;
				onlineUsersList.add(us);
			}
		}
		
		// Check for an optional language parameter
		UserSession currentUser = SessionFacade.getUserSession();
		if (currentUser.getUserId() == aid) {
			String lang = this.request.getParameter("lang");
			if (lang != null && I18n.languageExists(lang)) {
				currentUser.setLang(lang);
			}
		}

		// If there are only guest users, then just register
		// a single one. In any other situation, we do not
		// show the "guest" username
		if (onlineUsersList.size() == 0) {
			UserSession us = new UserSession();
			us.setUserId(aid);
			us.setUsername(I18n.getMessage("Guest"));
			
			onlineUsersList.add(us);
		}
		
		this.context.put("userSessions", onlineUsersList);
		this.context.put("usersOnline", I18n.getMessage("ForumListing.numberOfUsersOnline", 
			new Object[] {
					   new Integer(SessionFacade.size()),
					   new Integer(registered),
					   new Integer(guest)
			}));
	}
	
	public void moderation() throws Exception
	{
		this.context.put("openModeration", true);
		this.show();
	}

	public void show() throws Exception
	{
		int forumId = Integer.parseInt(this.request.getParameter("forum_id"));
		
		// The user can access this forum?
		if (!SecurityRepository.canAccess(SecurityConstants.PERM_FORUM, Integer.toString(forumId))) {
			new ModerationHelper().denied(I18n.getMessage("ForumListing.denied"));
			return;
		}
		
		int start = ViewCommon.getStartPage();
		
		int topicsPerPage = SystemGlobals.getIntValue(ConfigKeys.TOPICS_PER_PAGE);
		List tmpTopics = TopicsCommon.topicsByForum(forumId, start);
		
		int postsPerPage = SystemGlobals.getIntValue(ConfigKeys.POST_PER_PAGE);
		int totalTopics = ForumRepository.getTotalTopics(forumId, true);
		
		Forum forum = ForumRepository.getForum(forumId);

		this.context.put("topics", TopicsCommon.prepareTopics(tmpTopics));
		this.context.put("allCategories", ForumCommon.getAllCategoriesAndForums());
		this.context.put("forum", forum);
		this.context.put("moduleAction", "forum_show.htm");
		this.context.put("rssEnabled", SystemGlobals.getBoolValue(ConfigKeys.RSS_ENABLED));
		this.context.put("pageTitle", SystemGlobals.getValue(ConfigKeys.FORUM_NAME) + " - " + forum.getName());
		
		// Pagination
		this.context.put("totalPages", new Double(Math.ceil( (double)totalTopics / (double)topicsPerPage )));
		this.context.put("recordsPerPage", new Integer(topicsPerPage));
		this.context.put("totalRecords", new Integer(totalTopics));
		this.context.put("thisPage", new Double(Math.ceil( (double)(start+1) / (double)topicsPerPage )));
		this.context.put("start", new Integer(start));
		this.context.put("postsPerPage", new Integer(postsPerPage));
		this.context.put("readonly", !SecurityRepository.canAccess(SecurityConstants.PERM_READ_ONLY_FORUMS, 
				Integer.toString(forumId)));

		TopicsCommon.topicListingBase();
	}
	
	public void doModeration() throws Exception
	{
		new ModerationHelper().doModeration(this.makeRedirect("moderationDone"));
	}
	
	public void moveTopic() throws Exception
	{
		new ModerationHelper().moveTopicsSave(this.makeRedirect("show"));
	}
	
	public void moderationDone() throws Exception
	{
		new ModerationHelper().moderationDone(this.makeRedirect("show"));
	}
	
	// Make an URL to some action
	private String makeRedirect(String action)
	{
		String path = this.request.getContextPath() +"/forums/"+ action +"/";
		String thisPage = this.request.getParameter("start");
		
		if (thisPage != null && !thisPage.equals("0")) {
			path += thisPage +"/";
		}
		
		String forumId = this.request.getParameter("forum_id");
		if (forumId == null) {
			forumId = this.request.getParameter("persistData"); 
		}

		path += forumId + SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION);
		
		return path;
	}
	
	// Mark all topics as read
	public void readAll() throws Exception
	{
		SearchData sd = new SearchData();
		sd.setTime(SessionFacade.getUserSession().getLastVisit());
		
		String forumId = this.request.getParameter("forum_id");
		if (forumId != null) {
			sd.setForumId(Integer.parseInt(forumId));
		}
		
		List allTopics = DataAccessDriver.getInstance().newSearchModel().search(sd);
		for (Iterator iter = allTopics.iterator(); iter.hasNext(); ) {
			Topic t = (Topic)iter.next();
			
			((HashMap)SessionFacade.getAttribute(ConfigKeys.TOPICS_TRACKING)).put(new Integer(t.getId()), 
					new Long(t.getLastPostTimeInMillis().getTime()));
		}
		
		if (forumId != null) {
			JForum.setRedirect(this.makeRedirect("show"));
		}
		else {
			JForum.setRedirect(this.request.getContextPath() + "/forums/list"
					+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION));
		}
	}
	
	// Messages since last visit
	public void newMessages() throws Exception
	{
		this.request.addParameter("post_time", Long.toString(SessionFacade.getUserSession().getLastVisit().getTime()));
		this.request.addParameter("clean", "true");
		this.request.addParameter("sort_by", "t.topic_time");
		this.request.addParameter("sort_dir", "DESC");
		new SearchAction(this.request, this.response, this.conn, this.context).search();
	}
	
	public void pingSession()
	{
		this.setTemplateName("pingSession.htm");
	}
}
