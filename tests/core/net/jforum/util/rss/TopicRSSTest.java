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
 * Created on 16/10/2004 17:52:09
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.rss;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import net.jforum.TestCaseUtils;
import net.jforum.entities.Topic;
import net.jforum.entities.User;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import com.sun.syndication.io.impl.DateParser;

/**
 * @author Rafael Steil
 * @version $Id: TopicRSSTest.java,v 1.1 2004/10/17 05:26:56 rafaelsteil Exp $
 */
public class TopicRSSTest extends TestCase 
{
	private static boolean isUp;
	private List topics;
	private int forumId = 1;
	
	private String title;
	private String description;
	
	private Date time1;
	private Date time2;

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception 
	{
		if (!isUp) {
			TestCaseUtils.loadEnvironment();
			isUp = true;
		}
		
		this.title = "title for topics rss";
		this.description = "description for topics rss";
		
		this.time1 = new Date(System.currentTimeMillis());
		this.time2 = new Date(System.currentTimeMillis());
		
		this.topics = new ArrayList();
		this.createTopics();
	}
	
	private void createTopics()
	{
		for (int i = 0; i < 2; i++) {
			Topic t = new Topic();
			t.setId(i);
			t.setTime(i == 0 ? this.time1 : this.time2);
			t.setForumId(this.forumId);
			
			User u = new User();
			u.setUsername("username for topic " + i);
			t.setPostedBy(u);
			t.setTitle("topic title " + i);
			
			this.topics.add(t);
		}
	}

	public void testCreate() throws Exception
	{
		RSSAware rss = new TopicRSS(this.title, this.description, this.forumId, this.topics);
		String contents = rss.createRSS().replaceAll("\r", "").replaceAll("\n", "");
		assertEquals(this.expectedResult(), contents);
	}
	
	private String expectedResult()
	{
		StringBuffer sb = new StringBuffer();
		
		String forumLink = SystemGlobals.getValue(ConfigKeys.FORUM_LINK);
		if (!forumLink.endsWith("/")) {
			forumLink += "/";
		}
		
		sb.append("<?xml version=\"1.0\" encoding=\"" 
				+ SystemGlobals.getValue(ConfigKeys.ENCODING) + "\"?>");
		sb.append("<rss version=\"2.0\">");
		sb.append("  <channel>");
		sb.append("    <title>title for topics rss</title>");
		sb.append("    <link>" + forumLink + "forums/show/1.page</link>");
		sb.append("    <description>description for topics rss</description>");
		sb.append("    <item>");
		sb.append("      <title>topic title 0</title>");
		sb.append("      <link>" + forumLink + "posts/list/0.page</link>");
		sb.append("      <description>topic title 0</description>");
		sb.append("      <pubDate>" + DateParser.formatRFC822(this.time1) + "</pubDate>");
		sb.append("      <author>username for topic 0</author>");
		sb.append("      <guid>" + forumLink + "posts/list/0.page</guid>");
		sb.append("    </item>");
		sb.append("    <item>");
		sb.append("      <title>topic title 1</title>");
		sb.append("      <link>" + forumLink + "posts/list/1.page</link>");
		sb.append("      <description>topic title 1</description>");
		sb.append("      <pubDate>" + DateParser.formatRFC822(this.time2) + "</pubDate>");
		sb.append("      <author>username for topic 1</author>");
		sb.append("      <guid>" + forumLink + "posts/list/1.page</guid>");
		sb.append("    </item>");		
		sb.append("  </channel>");
		sb.append("</rss>");
		
		return sb.toString();
	}
}
