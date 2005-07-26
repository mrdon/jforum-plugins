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
 * Created on 12/11/2004 18:04:12
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jforum.SessionFacade;
import net.jforum.dao.DataAccessDriver;
import net.jforum.entities.Category;
import net.jforum.entities.Forum;
import net.jforum.entities.Topic;
import net.jforum.entities.UserSession;
import net.jforum.repository.ForumRepository;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 */
public class ForumCommon {
	/**
	 * Check if some forum has unread messages.
	 * 
	 * @param forum
	 *            The forum to search for unread messages
	 * @param tracking
	 *            Tracking of the topics read by the user
	 * @param lastVisit
	 *            The last visit time of the current usre
	 * 
	 * @return The same <code>Forum</code> instance passed as argument, which
	 *         then a call to "getUnread()" will return the "read" status for
	 *         this forum
	 */
	public static Forum checkUnreadPosts(Forum forum, Map tracking,
			long lastVisit) throws Exception {
		List unreadTopics = DataAccessDriver.getInstance().newForumDAO()
				.checkUnreadTopics(forum.getId(), lastVisit);

		if (unreadTopics.size() == 0) {
			return forum;
		}

		for (Iterator iter = unreadTopics.iterator(); iter.hasNext();) {
			Topic t = (Topic) iter.next();

			Integer topicId = new Integer(t.getId());
			if (tracking != null && tracking.containsKey(topicId)) {
				long readTime = ((Long) tracking.get(topicId)).longValue();
				if (t.getTime().compareTo(new Date(readTime)) > 0) {
					forum.setUnread(true);
				}
			} else {
				forum.setUnread(true);
			}

			if (forum.getUnread()) {
				break;
			}
		}

		return forum;
	}

	/**
	 * Gets all forums available to the user.
	 * 
	 * @param us
	 *            An <code>UserSession</code> instance with user information
	 * @param anonymousUserId
	 *            The id which represents the anonymous user
	 * @param tracking
	 *            <code>Map</code> instance with information about the topics
	 *            read by the user
	 * @param checkUnreadPosts
	 *            <code>true</code> if is to search for unread topics inside
	 *            the forums, or <code>false</code> if this action is not
	 *            needed.
	 * @return A <code>List</code> instance where each record is an instance
	 *         of a <code>Category</code> object
	 * @throws Exception
	 */
	public static List getAllCategoriesAndForums(UserSession us,
			int anonymousUserId, Map tracking, boolean checkUnreadPosts)
			throws Exception {
		long lastVisit = 0;
		int userId = anonymousUserId;

		if (us != null) {
			lastVisit = us.getLastVisit().getTime();
			userId = us.getUserId();
		}

		// Do not check for unread posts if the user is not logged in
		checkUnreadPosts = checkUnreadPosts
				&& (us.getUserId() != anonymousUserId);

		List categories = ForumRepository.getAllCategories(userId);

		if (!checkUnreadPosts) {
			return categories;
		}

		List returnCategories = new ArrayList();
		for (Iterator iter = categories.iterator(); iter.hasNext();) {
			Category c = new Category((Category) iter.next());

			for (Iterator tmpIterator = c.getForums().iterator(); tmpIterator
					.hasNext();) {
				Forum f = (Forum) tmpIterator.next();

				f = ForumCommon.checkUnreadPosts(f, tracking, lastVisit);
			}

			returnCategories.add(c);
		}

		return returnCategories;
	}

	/**
	 * @see #getAllCategoriesAndForums(UserSession, int, Map, boolean)
	 */
	public static List getAllCategoriesAndForums(boolean checkUnreadPosts)
			throws Exception {
		return getAllCategoriesAndForums(SessionFacade.getUserSession(),
				SystemGlobals.getIntValue(ConfigKeys.ANONYMOUS_USER_ID),
				(HashMap) SessionFacade
						.getAttribute(ConfigKeys.TOPICS_TRACKING),
				checkUnreadPosts);
	}

	/**
	 * @see #getAllCategoriesAndForums(boolean)
	 */
	public static List getAllCategoriesAndForums() throws Exception {
		UserSession us = SessionFacade.getUserSession();
		boolean checkUnread = (us != null && us.getUserId() != SystemGlobals
				.getIntValue(ConfigKeys.ANONYMOUS_USER_ID));
		return getAllCategoriesAndForums(checkUnread);
	}
}
