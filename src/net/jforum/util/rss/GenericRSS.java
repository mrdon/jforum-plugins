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

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * @author Rafael Steil
 * @version $Id: GenericRSS.java,v 1.1 2004/10/21 03:26:04 rafaelsteil Exp $
 */
public class GenericRSS implements RSSAware 
{
	private RSS rss;
	
	protected void setRSS(RSS rss) 
	{
		this.rss = rss;
	}

	/**
	 * @see net.jforum.util.rss.RSSAware#createRSS()
	 */
	public String createRSS() throws Exception 
	{
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType(RSSAware.RSS_VERSION);
		feed.setDescription(rss.getDescription());
		feed.setTitle(rss.getTitle());
		feed.setEncoding(rss.getEncoding());
		feed.setLink(rss.getLink());

		List entries = new ArrayList();
		for (Iterator iter = rss.getItens().iterator(); iter.hasNext(); ) {
			RSSItem item = (RSSItem)iter.next();
		
			SyndEntry entry = new SyndEntryImpl();
			entry.setAuthor(item.getAuthor());
			
			entry.setPublishedDate(item.getPublishDate());

			entry.setLink(item.getLink());
			entry.setTitle(item.getTitle());
			
			SyndContent content = new SyndContentImpl();
			content.setType(item.getContentType());
			content.setValue(item.getDescription());
			
			entry.setDescription(content);
			
			// Check for categories related to this item
			if (item.getCategories().size() > 0) {
				List categories = new ArrayList();
				
				for (Iterator catIter = item.getCategories().iterator(); catIter.hasNext(); ) {
					SyndCategory category = new SyndCategoryImpl();
					category.setName((String)catIter.next());
					
					categories.add(category);
				}
				
				entry.setCategories(categories);				
			}
			
			entries.add(entry);
		}
		
		feed.setEntries(entries);
		
		StringWriter writer = new StringWriter();
		SyndFeedOutput out = new SyndFeedOutput();
		out.output(feed, writer);
		
		return writer.toString();
	}
}