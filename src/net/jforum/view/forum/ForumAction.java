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
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.SessionFacade;
import net.jforum.entities.Category;
import net.jforum.entities.Forum;
import net.jforum.entities.Topic;
import net.jforum.entities.UserSession;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.ForumModel;
import net.jforum.model.SearchData;
import net.jforum.model.TopicModel;
import net.jforum.model.UserModel;
import net.jforum.repository.CategoryRepository;
import net.jforum.repository.ForumRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.repository.TopicRepository;
import net.jforum.security.SecurityConstants;
import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
/**
 * @author Rafael Steil
 * @version $Id: ForumAction.java,v 1.3 2004/09/04 17:29:36 rafaelsteil Exp $
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
	public static LinkedHashMap getAllForums() throws Exception
	{
		return ForumAction.getAllForums(false);
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
	public static LinkedHashMap getAllForums(boolean checkUnreadPosts) throws Exception
	{
		LinkedHashMap allForumsMap = new LinkedHashMap();
		ArrayList forums = ForumRepository.getAllForums();
		long lastVisit = SessionFacade.getUserSession().getLastVisit();
		
		Collections.sort(forums, new ForumOrderComparator());
		
		Iterator iter = CategoryRepository.getAllCategories().iterator();
		while (iter.hasNext()) {
			Category c = (Category)iter.next();
			ArrayList tmpList = new ArrayList();
			
			for (Iterator tmpIterator = forums.iterator(); tmpIterator.hasNext();) {
				Forum f = new Forum((Forum)tmpIterator.next());
				
				if (checkUnreadPosts) {
					HashMap lpi = ForumRepository.getLastPostInfo(f.getId());
					if (lpi.containsKey("postTimeMillis")) {
						Integer topicId = ((Integer)lpi.get("topicId"));
						boolean contains = ((HashMap)SessionFacade.getAttribute("topics_tracking")).containsKey(topicId);
						long lpiTime = ((Long)lpi.get("postTimeMillis")).longValue();
						
						if (contains) {
							long readTime = ((Long)((HashMap)SessionFacade.getAttribute("topics_tracking")).get(topicId)).longValue();
							
							if (lpiTime > readTime) {
								f.setUnread(true);
							}
						}
						else if (lpiTime > lastVisit) {
							f.setUnread(true);
						}
					}
				}

				if (f.getCategoryId() == c.getId()) {
					tmpList.add(f);				
				}
			}
			
			allForumsMap.put(c, tmpList);
		}
		
		return allForumsMap;
	}
	
	public void list() throws Exception
	{
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		
		JForum.getContext().put("allForums", ForumAction.getAllForums(true));
		JForum.getContext().put("topicsPerPage",  new Integer(SystemGlobals.getIntValue(ConfigKeys.TOPICS_PER_PAGE)));
		JForum.getContext().put("moduleAction", "forum_list.htm");
		
		JForum.getContext().put("totalMessages", I18n.getMessage("ForumListing.totalMessagesInfo", 
						new Object[] {new Integer( ForumRepository.getTotalMessages() )}));
		
		JForum.getContext().put("totalUsers", I18n.getMessage("ForumListing.registeredUsers", new Object[] {new Integer(um.getTotalUsers())}));

		HashMap m = um.getLastUserInfo();		
		JForum.getContext().put("lastUserId", m.get("userId"));
		JForum.getContext().put("lastUserName", m.get("userName"));		
		
		SimpleDateFormat df = new SimpleDateFormat(SystemGlobals.getValue(ConfigKeys.DATE_TIME_FORMAT));
		GregorianCalendar gc = new GregorianCalendar();
		JForum.getContext().put("now", df.format(gc.getTime()));
		
		gc = new GregorianCalendar();
		gc.setTimeInMillis(SessionFacade.getUserSession().getLastVisit());
		JForum.getContext().put("lastVisit", df.format(gc.getTime()));
		
		JForum.getContext().put("fir", new ForumRepository());
		
		// Online Users
		JForum.getContext().put("totalOnlineUsers", new Integer(SessionFacade.size()));
		int guest = 0;
		int registered = 0;
		int aid = SystemGlobals.getIntValue(ConfigKeys.ANONYMOUS_USER_ID);
	
		ArrayList userSessions = SessionFacade.getAllSessions();
		ArrayList onlineUsersList = new ArrayList();
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
		
		String s = JForum.getRequest().getParameter("start");
		int start = 0;
		
		if (s == null || s.equals("")) {
			start = 0;
		}
		else {
			start = Integer.parseInt(s);
			
			if (start < 0) {
				start = 0;
			}
		}
		
		TopicModel tm = DataAccessDriver.getInstance().newTopicModel();
		int topicsPerPage = SystemGlobals.getIntValue(ConfigKeys.TOPICS_PER_PAGE);
		ArrayList tmpTopics = null;
		
		// Try to get the first's page topics from the cache
		if (start == 0) {
			tmpTopics = TopicRepository.getTopics(forumId);

			if (tmpTopics.size() == 0) {
				tmpTopics = tm.selectAllByForumByLimit(forumId, start, topicsPerPage);
				TopicRepository.addAll(forumId, tmpTopics);
			}
		}
		else {
			tmpTopics = tm.selectAllByForumByLimit(forumId, start, topicsPerPage);
		}
		
		int postsPerPage = SystemGlobals.getIntValue(ConfigKeys.POST_PER_PAGE);
		int totalTopics = ForumRepository.getTotalTopics(forumId, true);

		JForum.getContext().put("topics", this.prepareTopics(tmpTopics));
		JForum.getContext().put("allForums", ForumAction.getAllForums());
		JForum.getContext().put("forum", ForumRepository.getForum(forumId));
		JForum.getContext().put("moduleAction", "forum_show.htm");
		
		// Pagination
		JForum.getContext().put("totalPages", new Double(Math.floor(totalTopics / topicsPerPage)));
		JForum.getContext().put("recordsPerPage", new Integer(topicsPerPage));
		JForum.getContext().put("totalRecords", new Integer(totalTopics));
		JForum.getContext().put("thisPage", new Integer(start));
		JForum.getContext().put("postsPerPage", new Integer(postsPerPage));
		JForum.getContext().put("readonly", !SecurityRepository.canAccess(SecurityConstants.PERM_READ_ONLY_FORUMS, 
				Integer.toString(forumId)));

		ViewCommon.topicListingBase();
	}
	
	public ArrayList prepareTopics(List topics)
	{
		long lastVisit = SessionFacade.getUserSession().getLastVisit();

		int postsPerPage = SystemGlobals.getIntValue(ConfigKeys.POST_PER_PAGE);
		HashMap topicsTracking = (HashMap)SessionFacade.getAttribute("topics_tracking");
		ArrayList newTopics = new ArrayList(topics.size());
		
		Iterator iter = topics.iterator();
		while (iter.hasNext()) {
			boolean read = false;
			Topic t = (Topic)iter.next();

			if (t.getLastPostTimeInMillis() > lastVisit) {
				if (topicsTracking.containsKey(new Integer(t.getId()))) {
					read = (t.getLastPostTimeInMillis() == ((Long)topicsTracking.get(new Integer(t.getId()))).longValue());
				}
			}
			else {
				read = true;
			}
			
			if (t.getTotalReplies() + 1 > postsPerPage) {
				t.setPaginate(true);
				t.setTotalPages(new Double(Math.floor(t.getTotalReplies() / postsPerPage)));
			}
			else {
				t.setPaginate(false);
				t.setTotalPages(new Double(0));
			}
			
			t.setRead(read);
			newTopics.add(t);
		}
		
		return newTopics;
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

		path += forumId +".page";
		
		return path;
	}
	
	// Mark all topics as read
	public void readAll() throws Exception
	{
		SearchData sd = new SearchData();
		sd.setTime(Long.toString(SessionFacade.getUserSession().getLastVisit()));
		
		String forumId = JForum.getRequest().getParameter("forum_id");
		if (forumId != null) {
			sd.setForumId(Integer.parseInt(forumId));
		}
		
		ArrayList allTopics = DataAccessDriver.getInstance().newSearchModel().search(sd);
		for (Iterator iter = allTopics.iterator(); iter.hasNext(); ) {
			Topic t = (Topic)iter.next();
			
			((HashMap)SessionFacade.getAttribute("topics_tracking")).put(new Integer(t.getId()), new Long(t.getLastPostTimeInMillis()));
		}
		
		if (forumId != null) {
			JForum.setRedirect(this.makeRedirect("show"));
		}
		else {
			JForum.setRedirect(JForum.getRequest().getContextPath() +"/forums/list.page");
		}
	}
	
	// Messages since last visit
	public void newMessages() throws Exception
	{
		JForum.getRequest().addParameter("post_time", Long.toString(SessionFacade.getUserSession().getLastVisit()));
		JForum.getRequest().addParameter("clean", "true");
		new SearchAction().search();
	}
}
