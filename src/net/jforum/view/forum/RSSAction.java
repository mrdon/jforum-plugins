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
 * Created on 13/10/2004 23:47:06
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.entities.Topic;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.PostModel;
import net.jforum.model.TopicModel;
import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.util.rss.ForumRSS;
import net.jforum.util.rss.RSSAware;
import net.jforum.util.rss.TopicRSS;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: RSSAction.java,v 1.2 2004/10/20 03:19:46 rafaelsteil Exp $
 */
public class RSSAction extends Command 
{
	/**
	 * RSS for all forums.
	 * Show rss syndication containing information about
	 * all available forums
	 * @throws Exception
	 */
	public void forums() throws Exception
	{
		LinkedHashMap forums = ForumAction.getAllForums();
		String title = I18n.getMessage("RSS.Forums.title");
		String description = I18n.getMessage("RSS.Forums.description");
		
		RSSAware rss = new ForumRSS(title, description, forums);
		JForum.getContext().put("rssContents", rss.createRSS());
	}
	
	/**
	 * RSS for all N first topics for some given forum
	 * @throws Exception
	 */
	public void topics() throws Exception
	{
		int forumId = Integer.parseInt(JForum.getRequest().getParameter("forum_id")); 
		if (!TopicsCommon.isTopicAccessible(forumId)) {
			return;
		}
		
		List topics = TopicsCommon.topicsByForum(forumId, 0);
		RSSAware rss = new TopicRSS(I18n.getMessage("RSS.ForumTopics.title"),
				I18n.getMessage("RSS.ForumTopics.descripton"),
				forumId, 
				topics);
		JForum.getContext().put("rssContents", rss.createRSS());
	}
	
	/**
	 * RSS for all N first posts for some given topic
	 * @throws Exception
	 */
	public void topic() throws Exception
	{
		int topicId = Integer.parseInt(JForum.getRequest().getParameter("topic_id"));

		PostModel pm = DataAccessDriver.getInstance().newPostModel();
		TopicModel tm = DataAccessDriver.getInstance().newTopicModel();
		
		Topic topic = tm.selectById(topicId);
		
		if (!TopicsCommon.isTopicAccessible(topic.getForumId()) || topic.getId() == 0) {
			return;
		}
		
		tm.incrementTotalViews(topic.getId());
		
		int count = SystemGlobals.getIntValue(ConfigKeys.POST_PER_PAGE);
		ArrayList posts = pm.selectAllByTopicByLimit(topicId, 0, count);
		
		String title = I18n.getMessage("RSS.Topic.title");
		String description = I18n.getMessage("RSS.Topic.description");

		RSSAware rss = new TopicRSS(title, description, topic.getForumId(), posts);
		JForum.getContext().put("rssContents", rss.createRSS());
	}
	
	/** 
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception 
	{
		this.forums();
	}
	
	/** 
	 * @see net.jforum.Command#process()
	 */
	public Template process() throws Exception 
	{
		super.setTemplateName(SystemGlobals.getValue(ConfigKeys.TEMPLATE_NAME) + "/rss.htm");
		return super.process();
	}

}
