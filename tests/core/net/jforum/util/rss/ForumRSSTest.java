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
 * Created on 16/10/2004 14:40:44
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.rss;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import junit.framework.TestCase;
import net.jforum.TestCaseUtils;
import net.jforum.entities.Category;
import net.jforum.entities.Forum;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: ForumRSSTest.java,v 1.1 2004/10/17 05:26:56 rafaelsteil Exp $
 */
public class ForumRSSTest extends TestCase 
{
	private LinkedHashMap forums;
	private String description;
	private String title;
	
	private static boolean isUp;
	
	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception 
	{
		if (!isUp) {
			TestCaseUtils.loadEnvironment();
			isUp = true;
		}

		this.description = "forums rss description";
		this.title = "title for forums rss";
		this.forums = new LinkedHashMap();
		this.createForums();
	}
	
	private void createForums()
	{
		for (int i = 0; i < 3; i++) {
			Category c = new Category();
			c.setName("Category " + i);
			
			ArrayList catForums = new ArrayList();
			
			for (int j = 0; j < 2; j++) {
				Forum f = new Forum();
				f.setDescription("Forum description [cat=" + i + ", forum=" + j + "]");
				f.setName("Forum name [cat=" + i + ", forum=" + j + "]");
				f.setId(j);
				
				catForums.add(f);
			}
			
			this.forums.put(c, catForums);
		}
	}

	public void testCreate() throws Exception
	{
		RSSAware rss = new ForumRSS(this.title, this.description, this.forums);
		String contents = rss.createRSS().replaceAll("\r", "").replaceAll("\n", "");
		assertEquals(this.extepctedResult(), contents);
	}
	
	private String extepctedResult()
	{
		String forumLink = SystemGlobals.getValue(ConfigKeys.FORUM_LINK);
		if (!forumLink.endsWith("/")) {
			forumLink += "/";
		}
		
		StringBuffer sb = new StringBuffer(512);
		sb.append("<?xml version=\"1.0\" encoding=\"" 
				+ SystemGlobals.getValue(ConfigKeys.ENCODING) + "\"?>");
		sb.append("<rss version=\"" + RSSAware.RSS_VERSION.substring(4) + "\">");
		sb.append("  <channel>");
		sb.append("    <title>" + this.title + "</title>");
		sb.append("    <link>" + forumLink + "</link>");
		sb.append("    <description>" + this.description + "</description>");
		sb.append("    <item>");
		sb.append("      <title>Forum name [cat=2, forum=0]</title>");
		sb.append("      <link>" + forumLink + "forums/list/0.page</link>");
		sb.append("      <description>Forum description [cat=2, forum=0]</description>");
		sb.append("      <category>Category 0</category>");
		sb.append("      <guid>" + forumLink + "forums/list/0.page</guid>");
		sb.append("    </item>");
		sb.append("    <item>");
		sb.append("      <title>Forum name [cat=2, forum=1]</title>");
		sb.append("      <link>" + forumLink + "forums/list/1.page</link>");
		sb.append("      <description>Forum description [cat=2, forum=1]</description>");
		sb.append("      <category>Category 0</category>");
		sb.append("      <guid>" + forumLink + "forums/list/1.page</guid>");
		sb.append("    </item>");
		sb.append("  </channel>");
		sb.append("</rss>");
		return sb.toString();
	}
}
