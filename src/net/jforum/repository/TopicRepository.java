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
 * This file creation date: 05/04/2004 - 20:11:44
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import net.jforum.entities.Topic;
import net.jforum.exceptions.ForumException;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.TopicModel;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * Repository for the last n topics for each forum.
 * 
 * @author Rafael Steil
 * @author James Yong
 * @version $Id: TopicRepository.java,v 1.10 2004/11/21 17:13:47 rafaelsteil Exp $
 */
public class TopicRepository
{
	private static LinkedHashMap allTopicsMap = new LinkedHashMap();
	private static int maxItems = SystemGlobals.getIntValue(ConfigKeys.TOPICS_PER_PAGE);
	private static LinkedList recentTopicsList = new LinkedList();
	
	static {
		if (SystemGlobals.getBoolValue(ConfigKeys.TOPIC_CACHE_ENABLED)) {
			try {
				TopicRepository.loadMostRecentTopics();
			}
			catch (Exception e) {
				throw new ForumException(e);
			}
		}
	}

	/**
	 * Add topic to the FIFO stack
	 * 
	 * @param topic The topic to add to stack
	 */
	public synchronized static void pushTopic(Topic topic)
	{
		if (SystemGlobals.getBoolValue(ConfigKeys.TOPIC_CACHE_ENABLED)) {
			int limit = SystemGlobals.getIntValue(ConfigKeys.RECENT_TOPICS);
	
			recentTopicsList.remove(topic);
			recentTopicsList.addFirst(topic);
			
			while (recentTopicsList.size() > limit)
			{
				recentTopicsList.removeLast();
			}
		}
	}

	/**
	 * Remove topic to the FIFO stack
	 * 
	 * @param topic The topic to remove from stack
	 */
	public synchronized static void popTopic(Topic topic)
	{
		if (SystemGlobals.getBoolValue(ConfigKeys.TOPIC_CACHE_ENABLED)) {
			recentTopicsList.remove(topic);
		}
	}	

	/**
	 * Get all cached recent topics. 
	 * 
	 */	
	public static List getRecentTopics() throws Exception
	{
		if (recentTopicsList == null || recentTopicsList.size() == 0
				|| !SystemGlobals.getBoolValue(ConfigKeys.TOPIC_CACHE_ENABLED)) {
			loadMostRecentTopics();
		}
		
		return new ArrayList(recentTopicsList);
	}	

	/**
	 * Add recent topics to the cache
	 */
	public static void loadMostRecentTopics() throws Exception
	{
		TopicModel tm = DataAccessDriver.getInstance().newTopicModel();
		recentTopicsList = new LinkedList();
		
		int limit = SystemGlobals.getIntValue(ConfigKeys.RECENT_TOPICS);
		List l = tm.selectRecentTopics(limit);
		for (Iterator iter = l.iterator(); iter.hasNext(); ) {
			Topic t = (Topic)iter.next();
			
			recentTopicsList.addLast(t);
		}		
	}
	/**
	 * Add topics to the cache
	 * 
	 * @param forumId The forum id to which the topics are related
	 * @param topics The topics to add
	 */
	public synchronized static void addAll(int forumId, List topics)
	{
		allTopicsMap.put(new Integer(forumId), new LinkedList(topics));
	}
	
	/**
	 * Clears the cache
	 * 
	 * @param forumId The forum id to clear the cache
	 */
	public synchronized static void clearCache(int forumId) throws Exception
	{
		allTopicsMap.put(new Integer(forumId), new LinkedList());
	}
	
	/**
	 * Adds a new topic to the cache
	 * 
	 * @param topic The topic to add
	 */
	public synchronized static void addTopic(Topic topic)
	{
		if (SystemGlobals.getBoolValue(ConfigKeys.TOPIC_CACHE_ENABLED)) {
			Integer forumId = new Integer(topic.getForumId());
			LinkedList list = (LinkedList)allTopicsMap.get(forumId);
			
			if (list == null) {
				list = new LinkedList();
				list.add(topic);
				allTopicsMap.put(forumId, list);
			}
			else {
				if (list.size() + 1 > maxItems) {
					list.removeLast();
				}
				
				list.addFirst(topic);
			}
		}
	}
	
	/**
	 * Updates a cached topic
	 * 
	 * @param topic The topic to update
	 */
	public synchronized static void updateTopic(Topic topic)
	{
		int index = ((LinkedList)allTopicsMap.get(new Integer(topic.getForumId()))).indexOf(topic);
		if (index > -1) {
			((LinkedList)allTopicsMap.get(new Integer(topic.getForumId()))).set(index, topic);
		}
	}
	
	/**
	 * Checks if a topic is cached
	 * 
	 * @param topic The topic to verify
	 * @return <code>true</code> if the topic is cached, or <code>false</code> if not.
	 */
	public static boolean isTopicCached(Topic topic)
	{
		return ((LinkedList)allTopicsMap.get(new Integer(topic.getForumId()))).contains(topic);
	}
	
	/**
	 * Get all cached topics related to a forum. 
	 * 
	 * @param forumid The forum id 
	 * @return <code>ArrayList</code> with the topics.
	 */
	public static List getTopics(int forumid)
	{
		if (SystemGlobals.getBoolValue(ConfigKeys.TOPIC_CACHE_ENABLED)) {
			LinkedList returnList = (LinkedList)allTopicsMap.get(new Integer(forumid));
			if (returnList == null) {
				return new ArrayList();
			}
			
			return new ArrayList(returnList);
		}
		
		return new ArrayList();
	}
}
