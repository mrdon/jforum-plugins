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
 * This file creation date: 31/01/2004 - 20:53:44
 * net.jforum.util.rss.TopicRSS.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: TopicRSS.java,v 1.3 2004/06/01 19:47:28 pieter2 Exp $
 */
package net.jforum.util.rss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import net.jforum.JForum;
import net.jforum.entities.Topic;
import net.jforum.model.DataAccessDriver;
import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 */
public class TopicRSS extends RSSable 
{
	private static Collection queueElementsList = Collections.synchronizedCollection(new LinkedList());

	/* 
	 * @see net.jforum.util.rss.RSSable#prepareRSSDocument()
	 */
	protected RSSDocument prepareRSSDocument() throws Exception 
	{
		ArrayList topics = DataAccessDriver.getInstance().newTopicModel().selectLastN(SystemGlobals.getIntValue(ConfigKeys.RSS_TOPICS));
		String ctx = JForum.getRequest().getContextPath();
		
		RSSDocument rssDocumnet = new RSSDocument();
		
		RSSChannel channel = new RSSChannel();
		channel.setChannelDescription(I18n.getMessage("TopicRSS.description"));
		channel.setChannelTitle(I18n.getMessage("TopicRSS.title"));
		channel.setChannelLink(SystemGlobals.getValue(ConfigKeys.FORUM_LINK));
		
		queueElementsList.clear();
		
		for (Iterator iter = topics.iterator(); iter.hasNext(); ) {
			Topic t = (Topic)iter.next();
			
			RSSItem item = new RSSItem();
			item.setDescription(t.getTitle());
			item.setTitle(t.getTitle());
			
			item.setLink("http://"+ JForum.getRequest().getServerName() +":"
					+ JForum.getRequest().getServerPort()
					+ (ctx.equals("") ? "" : "/"+ ctx)
					+ "/posts/list/"+ t.getId() +"."
					+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION));
			
			queueElementsList.add(Integer.toString(t.getId()));
			channel.addItem(item);
		}
		
		rssDocumnet.addChannel(channel);

		return rssDocumnet;
	}
	
	/* 
	 * @see net.jforum.util.rss.RSSable#getFilename()
	 */
	public String getFilename() throws Exception 
	{
		return SystemGlobals.getValue(ConfigKeys.RSS_FILENAME_TOPIC);
	}

	/* 
	 * @see net.jforum.util.rss.RSSElementQueue#objectExists(java.lang.Object)
	 */
	public boolean objectExists(Object o) 
	{
		if (o instanceof Topic) {
			return (TopicRSS.queueElementsList.contains(Integer.toString(((Topic)o).getId())));
		}
		
		return false;
	}
}
