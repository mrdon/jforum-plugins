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
 * This file creation date: 31/01/2004 - 20:53:44
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.rss;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.jforum.entities.Topic;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * @author Rafael Steil
 * @version $Id: TopicRSS.java,v 1.6 2004/10/17 05:26:55 rafaelsteil Exp $
 */
public class TopicRSS implements RSSAware {
	private List topics;

	private String title;

	private String description;

	private int forumId;

	public TopicRSS(String title, String description, int forumId, List topics) {
		this.topics = topics;
		this.title = title;
		this.description = description;
		this.forumId = forumId;
	}

	/**
	 * @see net.jforum.util.rss.RSSAware#createRSS()
	 */
	public String createRSS() throws Exception 
	{
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType(RSSAware.RSS_VERSION);
		feed.setDescription(this.description);
		feed.setTitle(this.title);
		feed.setEncoding(SystemGlobals.getValue(ConfigKeys.ENCODING));
		
		String forumLink = SystemGlobals.getValue(ConfigKeys.FORUM_LINK);
		if (!forumLink.endsWith("/")) {
			forumLink += "/";
		}
		
		feed.setLink(forumLink + "forums/show/" + forumId 
				+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION));

		List entries = new ArrayList();
		for (Iterator iter = topics.iterator(); iter.hasNext(); ) {
			Topic t = (Topic)iter.next();
		
			SyndEntry entry = new SyndEntryImpl();
			entry.setAuthor(t.getPostedBy().getUsername());
			
			entry.setPublishedDate(t.getTime());

			entry.setLink(forumLink + "posts/list/" + t.getId()
					+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION));
			entry.setTitle(t.getTitle());
			
			SyndContent content = new SyndContentImpl();
			content.setType("text/html");
			content.setValue(t.getTitle());
			
			entry.setDescription(content);
			
			entries.add(entry);
		}
		
		feed.setEntries(entries);
		
		StringWriter writer = new StringWriter();
		SyndFeedOutput out = new SyndFeedOutput();
		out.output(feed, writer);
		
		return writer.toString();
	}
}