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
 * This file creation date: 07/02/2005 - 10:29:14
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.jforum.model.DataAccessDriver;
import net.jforum.model.PostModel;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.log4j.Logger;

/**
 * Repository for the post in the top n topics for each forum.
 * 
 * @author Sean Mitchell
 * @author Rafael Steil
 * @version $Id: PostRepository.java,v 1.2 2005/02/16 13:46:25 rafaelsteil Exp $
 */
public class PostRepository
{
	private static Logger logger = Logger.getLogger(PostRepository.class);
	private static final int CACHE_SIZE = SystemGlobals.getIntValue(ConfigKeys.POSTS_CACHE_SIZE);
  
	private static Map cache = new LinkedHashMap(CACHE_SIZE + 1) {
		protected boolean removeEldestEntry(Map.Entry eldest) {
			return this.size() > CACHE_SIZE;
		}
	};
		
	public static List selectAllByTopicByLimit(int topicId, int start, int count) throws Exception 
	{
		Integer tid = new Integer(topicId);
		
		List topics = (List)cache.get(tid);
		if (topics == null || topics.size() == 0) {
			synchronized (cache) {
				if (!cache.containsKey(tid)) {
					PostModel pm = DataAccessDriver.getInstance().newPostModel();
					topics = pm.selectAllByTopic(topicId);
			
					cache.put(tid, topics);
				}
			}
		}
		
		return topics.subList(start, start + count);
   }
	
	public static void clearCache(int topicId)
	{
		Integer tid = new Integer(topicId);
		if (cache.containsKey(tid)) {
			synchronized (cache) {
				cache.remove(tid);
			}
		}
	}
}

