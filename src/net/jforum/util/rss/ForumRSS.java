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
 * This file creation date: 31/01/2004 - 19:22:42
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.rss;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import net.jforum.JForum;
import net.jforum.entities.Category;
import net.jforum.entities.Forum;
import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.view.forum.ForumAction;

/**
 * @author Rafael Steil
 * @version $Id: ForumRSS.java,v 1.6 2004/10/14 02:23:37 rafaelsteil Exp $
 */
public class ForumRSS extends RSSAware 
{
	private static Collection queueElementsList = Collections.synchronizedCollection(new LinkedList());
	
	/* 
	 * @see net.jforum.util.rss.RSSable#prepareRSSDocument()
	 */
	protected RSSDocument prepareRSSDocument() throws Exception 
	{
		LinkedHashMap forums = ForumAction.getAllForums();
		RSSDocument rssDocumnet = new RSSDocument();
		
		String ctx = JForum.getRequest().getContextPath();
		ArrayList tmpList = new ArrayList();
		
		for (Iterator iter = forums.entrySet().iterator(); iter.hasNext(); ) {
			Map.Entry entry = (Map.Entry)iter.next();
			Category category = (Category)entry.getKey();
						
			RSSChannel channel = new RSSChannel();
			channel.setChannelTitle(category.getName());
			channel.setChannelDescription(I18n.getMessage("ForumRSS.description"));
			channel.setChannelLink(SystemGlobals.getValue(ConfigKeys.FORUM_LINK));
			
			ArrayList forumsList = (ArrayList)entry.getValue();
			for (Iterator fIter = forumsList.iterator(); fIter.hasNext(); ) {
				Forum forum = (Forum)iter.next();
				
				RSSItem item = new RSSItem();
				item.setTitle(forum.getName());
				item.setDescription(forum.getDescription());

				item.setLink("http://" + JForum.getRequest().getServerName()
						+ (JForum.getRequest().getServerPort() != 80 
								? ":" + JForum.getRequest().getServerPort() 
								: "")
						+ (ctx.equals("") ? "" : "/" + ctx)
						+ "/forums/list/" + forum.getId()
						+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION));
				
				tmpList.add(Integer.toString(forum.getId()));
				channel.addItem(item);
			}
			
			rssDocumnet.addChannel(channel);
		}
		
		synchronized (queueElementsList) {
			queueElementsList.clear();
			queueElementsList = new ArrayList(tmpList);
		}
		
		return rssDocumnet;
	}

	/* 
	 * @see net.jforum.util.rss.RSSable#getFilename()
	 */
	public String getFilename() throws Exception 
	{
		return SystemGlobals.getValue(ConfigKeys.RSS_FILENAME_FORUM);
	}

	/* 
	 * @see net.jforum.util.rss.RSSElementQueue#objectExists(java.lang.Object)
	 */
	public boolean objectExists(Object o) 
	{
		if (o instanceof Forum) {
			return (ForumRSS.queueElementsList.contains(Integer.toString(((Forum)o).getId())));
		}
		
		return false;
	}
}
