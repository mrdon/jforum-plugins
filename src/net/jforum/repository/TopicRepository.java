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
 * This file creation date: 05/04/2004 - 20:11:44
 * net.jforum.repository.TopicRepository.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: TopicRepository.java,v 1.2 2004/04/21 23:57:32 rafaelsteil Exp $
 */
package net.jforum.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import net.jforum.entities.Topic;
import net.jforum.util.SystemGlobals;

/**
 * Repository for the last n topics for each forum.
 * 
 * @author Rafael Steil
 */
public class TopicRepository
{
	private static LinkedHashMap allTopicsMap = new LinkedHashMap();
	private static int maxItems = Integer.parseInt((String)SystemGlobals.getValue("topicsPerPage"));
	
	private TopicRepository() {}

	/**
	 * Add topics to the cache
	 * 
	 * @param forumId The forum id to which the topics are related
	 * @param topics The topics to add
	 */
	public synchronized static void addAll(int forumId, ArrayList topics)
	{
		allTopicsMap.put(new Integer(forumId), new LinkedList(topics));
	}
	
	/**
	 * Clears the cache
	 * 
	 * @param forumId The forum id to clear the cache
	 */
	public synchronized static void clearCache(int forumId)
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
	public synchronized static ArrayList getTopics(int forumid)
	{
		LinkedList returnList = (LinkedList)allTopicsMap.get(new Integer(forumid));
		if (returnList == null) {
			return new ArrayList();
		}
		
		return new ArrayList(returnList);
	}
}
