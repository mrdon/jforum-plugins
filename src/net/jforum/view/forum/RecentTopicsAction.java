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
 * Created on Oct 19, 2004
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import net.jforum.Command;
import net.jforum.entities.Topic;
import net.jforum.entities.Forum;
import net.jforum.JForum;
import net.jforum.repository.ForumRepository;
import net.jforum.repository.TopicRepository;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * Display a list of recent Topics
 * 
 * @author James Yong
 * @version $Id: RecentTopicsAction.java,v 1.2 2004/11/02 19:02:50 rafaelsteil Exp $
 */
public class RecentTopicsAction extends Command 
{
	public void list() throws Exception
	{
		int postsPerPage = SystemGlobals.getIntValue(ConfigKeys.POST_PER_PAGE);
		List tmpTopics = TopicRepository.getRecentTopics();
		
		List forums = new ArrayList(postsPerPage);
		Iterator iter = tmpTopics.iterator();
		while (iter.hasNext()) 
		{
			Topic t = (Topic)iter.next();

			// Get name of forum that the topic refers to
			Forum f = ForumRepository.getForum(t.getForumId());
			forums.add(f);
		}
		
		JForum.getContext().put("postsPerPage", new Integer(postsPerPage));
		JForum.getContext().put("topics", TopicsCommon.prepareTopics(tmpTopics));
		JForum.getContext().put("forums", forums);
		JForum.getContext().put("moduleAction", "recent_thread.htm");
		
		TopicsCommon.topicListingBase();
	}
}
