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
 * Created on 17/10/2004 23:54:47
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jforum.JForum;
import net.jforum.SessionFacade;
import net.jforum.entities.Topic;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.TopicModel;
import net.jforum.repository.SecurityRepository;
import net.jforum.repository.TopicRepository;
import net.jforum.security.SecurityConstants;
import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * General utilities methods for topic manipulation.
 * 
 * @author Rafael Steil
 * @version $Id: TopicsCommon.java,v 1.4 2004/11/06 19:04:41 rafaelsteil Exp $
 */
public class TopicsCommon 
{
	/**
	 * List all first 'n' topics of a given forum.
	 * This method returns no more than <code>ConfigKeys.TOPICS_PER_PAGE</code>
	 * topics for the forum. 
	 * 
	 * @param forumId The forum id to which the topics belongs to
	 * @param start The start fetching index
	 * @return <code>java.util.List</code> containing the topics found.
	 * @throws Exception
	 */
	public static List topicsByForum(int forumId, int start) throws Exception
	{
		TopicModel tm = DataAccessDriver.getInstance().newTopicModel();
		int topicsPerPage = SystemGlobals.getIntValue(ConfigKeys.TOPICS_PER_PAGE);
		List topics = null;
		
		// Try to get the first's page of topics from the cache
		if (start == 0 && SystemGlobals.getBoolValue(ConfigKeys.TOPIC_CACHE_ENABLED)) {
			topics = TopicRepository.getTopics(forumId);

			if (topics.size() == 0) {
				topics = tm.selectAllByForumByLimit(forumId, start, topicsPerPage);
				TopicRepository.addAll(forumId, topics);
			}
		}
		else {
			topics = tm.selectAllByForumByLimit(forumId, start, topicsPerPage);
		}
		
		return topics;
	}
	
	/**
	 * Prepare the topics for listing.
	 * This method does some preparation for a set ot <code>net.jforum.entities.Topic</code>
	 * instances for the current user, like verification if the user already
	 * read the topic, if pagination is a need and so on.
	 * 
	 * @param topics The topics to process
	 * @return The post-processed topics.
	 */
	public static List prepareTopics(List topics)
	{
		long lastVisit = SessionFacade.getUserSession().getLastVisit().getTime();
		int hotBegin = SystemGlobals.getIntValue(ConfigKeys.HOT_TOPIC_BEGIN);

		int postsPerPage = SystemGlobals.getIntValue(ConfigKeys.POST_PER_PAGE);
		Map topicsTracking = (HashMap)SessionFacade.getAttribute("topics_tracking");
		List newTopics = new ArrayList(topics.size());
		
		Iterator iter = topics.iterator();
		while (iter.hasNext()) {
			boolean read = false;
			Topic t = (Topic)iter.next();

			if (t.getLastPostTimeInMillis().getTime() > lastVisit) {
				if (topicsTracking.containsKey(new Integer(t.getId()))) {
					read = (t.getLastPostTimeInMillis().getTime() == ((Long)topicsTracking.get(new Integer(t.getId()))).longValue());
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
			
			// Check if this is a hot topic
			t.setHot(t.getTotalReplies() >= hotBegin);
			
			t.setRead(read);
			newTopics.add(t);
		}
		
		return newTopics;
	}

	/**
	 * Common properties to be used when showing topic data
	 */
	public static void topicListingBase()
	{
		// Topic Types
		JForum.getContext().put("TOPIC_ANNOUNCE", new Integer(Topic.TYPE_ANNOUNCE));
		JForum.getContext().put("TOPIC_STICKY", new Integer(Topic.TYPE_STICKY));
		JForum.getContext().put("TOPIC_NORMAL", new Integer(Topic.TYPE_NORMAL));
	
		// Topic Status
		JForum.getContext().put("STATUS_LOCKED", new Integer(Topic.STATUS_LOCKED));
		JForum.getContext().put("STATUS_UNLOCKED", new Integer(Topic.STATUS_UNLOCKED));
		
		// Moderation
		JForum.getContext().put("moderator", SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION));
		JForum.getContext().put("can_remove_posts", SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_POST_REMOVE));
		JForum.getContext().put("can_move_topics", SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_TOPIC_MOVE));
		JForum.getContext().put("can_lockUnlock_topics", SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_TOPIC_LOCK_UNLOCK));
	}
	
	/**
	 * Checks if the user is allowed to view the topic
	 * 
	 * @param forumId The forum id to which the topics belongs to
	 * @return <code>true</code> if the topic is accessible, <code>false</code> otherwise
	 * @throws Exception
	 */
	public static boolean isTopicAccessible(int forumId) throws Exception 
	{
		if (!SecurityRepository.canAccess(SecurityConstants.PERM_FORUM, Integer.toString(forumId))) {
			new ModerationHelper().denied(I18n.getMessage("PostShow.denied"));
			return false;
		}

		return true;
	}
}
