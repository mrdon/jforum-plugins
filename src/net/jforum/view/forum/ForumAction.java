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
import java.util.Map;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.SessionFacade;
import net.jforum.entities.Category;
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
/**
 * @author Rafael Steil
 * @version $Id: ForumAction.java,v 1.17 2004/11/13 03:14:02 rafaelsteil Exp $
 */
public class ForumAction extends Command 
{
	/**
	 * Gets all forums available to the user.
	 * 
	 * @return <code>LinkedHashMap</code> with the records found. The <i>key</i> is
	 * an object of type <code>Category</code>, which represets the forum's category. 
	 * The <i>value</i> is an <code>ArrayList</code> filled by <code>Forum</code> objects.
	 * 
	 * @see #getAllForums(boolean)
	 * @throws Exception
	 */
	public static List getAllCategoriesAndForums() throws Exception
	{
		return ForumAction.getAllCategoriesAndForums(false);
	}

	/**
	 * Gets all forums available to the user.
	 * 
	 * @param checkUnreadPosts <code>true</code> if is to search for unread topics inside the forums, 
	 * or <code>false</code> if this action is not needed. 
	 * 
	 * @return <code>LinkedHashMap</code> with the records found. The <i>key</i> is
	 * an object of type <code>Category</code>, which represets the forum's category. 
	 * The <i>value</i> is an <code>ArrayList</code> filled by <code>Forum</code> objects.
	 * @see #getAllForums()
	 * @throws Exception
	 */
	public static List getAllCategoriesAndForums(boolean checkUnreadPosts) throws Exception
	{
		long lastVisit = 0;
		
		UserSession us = SessionFacade.getUserSession();
		if (us != null) {
			lastVisit = us.getLastVisit().getTime();
		}
		
		// Do not check for unread posts if the user is not logged in
		checkUnreadPosts = checkUnreadPosts 
			&& (us.getUserId() != SystemGlobals.getIntValue(ConfigKeys.ANONYMOUS_USER_ID));

		Map tracking = null;
		if (checkUnreadPosts) {
			tracking = (HashMap)SessionFacade.getAttribute(ConfigKeys.TOPICS_TRACKING);
		}
		
		List returnCategories = new ArrayList();
		List categories = ForumRepository.getAllCategories();
		for (Iterator iter = categories.iterator(); iter.hasNext(); ) {
			Category c = (Category)iter.next();
			
			for (Iterator tmpIterator = c.getForums(); tmpIterator.hasNext(); ) {
				Forum f = (Forum)tmpIterator.next();
				if (!ForumRepository.isForumAccessible(c.getId(), c.getId())) {
					c.removeForum(f.getId());
					continue;
				}
				
				f = new Forum(f);
				
				if (checkUnreadPosts) {
					f = ForumCommon.checkUnreadPosts(f, ForumRepository.getLastPostInfo(f.getId()), 
							tracking, lastVisit);
				}
			}
			
			returnCategories.add(c);
		}
		
		return returnCategories;
	}
	
	public void list() throws Exception
	{
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		
		JForum.getContext().put("allCategories", ForumAction.getAllCategoriesAndForums(true));
		JForum.getContext().put("topicsPerPage",  new Integer(SystemGlobals.getIntValue(ConfigKeys.TOPICS_PER_PAGE)));
		JForum.getContext().put("moduleAction", "forum_list.htm");
		JForum.getContext().put("rssEnabled", SystemGlobals.getBoolValue(ConfigKeys.RSS_ENABLED));
		
		JForum.getContext().put("totalMessages", I18n.getMessage("ForumListing.totalMessagesInfo", 
						new Object[] {new Integer( ForumRepository.getTotalMessages() )}));
		
		JForum.getContext().put("totalUsers", I18n.getMessage("ForumListing.registeredUsers", new Object[] {new Integer(um.getTotalUsers())}));

		User lastUser = um.getLastUserInfo();		
		JForum.getContext().put("lastUserId", new Integer(lastUser.getId()));
		JForum.getContext().put("lastUserName", lastUser.getUsername());
		
		SimpleDateFormat df = new SimpleDateFormat(SystemGlobals.getValue(ConfigKeys.DATE_TIME_FORMAT));
		GregorianCalendar gc = new GregorianCalendar();
		JForum.getContext().put("now", df.format(gc.getTime()));
		
		JForum.getContext().put("lastVisit", df.format(SessionFacade.getUserSession().getLastVisit()));
		JForum.getContext().put("fir", new ForumRepository());
		
		// Online Users
		JForum.getContext().put("totalOnlineUsers", new Integer(SessionFacade.size()));
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
			String lang = JForum.getRequest().getParameter("lang");
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
		
		JForum.getContext().put("userSessions", onlineUsersList);
		JForum.getContext().put("usersOnline", I18n.getMessage("ForumListing.numberOfUsersOnline", 
			new Object[] {
					   new Integer(SessionFacade.size()),
					   new Integer(registered),
					   new Integer(guest)
			}));
	}
	
	public void moderation() throws Exception
	{
		JForum.getContext().put("openModeration", true);
		this.show();
	}

	
	public void show() throws Exception
	{
		int forumId = Integer.parseInt(JForum.getRequest().getParameter("forum_id"));
		
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

		JForum.getContext().put("topics", TopicsCommon.prepareTopics(tmpTopics));
		JForum.getContext().put("allCategories", ForumAction.getAllCategoriesAndForums());
		JForum.getContext().put("forum", forum);
		JForum.getContext().put("moduleAction", "forum_show.htm");
		JForum.getContext().put("rssEnabled", SystemGlobals.getBoolValue(ConfigKeys.RSS_ENABLED));
		JForum.getContext().put("pageTitle", SystemGlobals.getValue(ConfigKeys.FORUM_NAME) + " - " + forum.getName());
		
		// Pagination
		JForum.getContext().put("totalPages", new Double(Math.ceil( (double)totalTopics / (double)topicsPerPage )));
		JForum.getContext().put("recordsPerPage", new Integer(topicsPerPage));
		JForum.getContext().put("totalRecords", new Integer(totalTopics));
		JForum.getContext().put("thisPage", new Double(Math.ceil( (double)(start+1) / (double)topicsPerPage )));
		JForum.getContext().put("start", new Integer(start));
		JForum.getContext().put("postsPerPage", new Integer(postsPerPage));
		JForum.getContext().put("readonly", !SecurityRepository.canAccess(SecurityConstants.PERM_READ_ONLY_FORUMS, 
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
		String path = JForum.getRequest().getContextPath() +"/forums/"+ action +"/";
		String thisPage = JForum.getRequest().getParameter("start");
		
		if (thisPage != null && !thisPage.equals("0")) {
			path += thisPage +"/";
		}
		
		String forumId = JForum.getRequest().getParameter("forum_id");
		if (forumId == null) {
			forumId = JForum.getRequest().getParameter("persistData"); 
		}

		path += forumId + SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION);
		
		return path;
	}
	
	// Mark all topics as read
	public void readAll() throws Exception
	{
		SearchData sd = new SearchData();
		sd.setTime(SessionFacade.getUserSession().getLastVisit());
		
		String forumId = JForum.getRequest().getParameter("forum_id");
		if (forumId != null) {
			sd.setForumId(Integer.parseInt(forumId));
		}
		
		List allTopics = DataAccessDriver.getInstance().newSearchModel().search(sd);
		for (Iterator iter = allTopics.iterator(); iter.hasNext(); ) {
			Topic t = (Topic)iter.next();
			
			((HashMap)SessionFacade.getAttribute("topics_tracking")).put(new Integer(t.getId()), 
					new Long(t.getLastPostTimeInMillis().getTime()));
		}
		
		if (forumId != null) {
			JForum.setRedirect(this.makeRedirect("show"));
		}
		else {
			JForum.setRedirect(JForum.getRequest().getContextPath() +"/forums/list"
					+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION));
		}
	}
	
	// Messages since last visit
	public void newMessages() throws Exception
	{
		JForum.getRequest().addParameter("post_time", Long.toString(SessionFacade.getUserSession().getLastVisit().getTime()));
		JForum.getRequest().addParameter("clean", "true");
		JForum.getRequest().addParameter("sort_by", "t.topic_time");
		JForum.getRequest().addParameter("sort_dir", "DESC");
		new SearchAction().search();
	}
}
