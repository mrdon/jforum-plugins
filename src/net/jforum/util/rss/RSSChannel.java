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
 * This file creation date: 31/01/2004 - 20:24:12
 * net.jforum.util.rss.RSSChannel.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.rss;

import java.util.ArrayList;

/**
 * @author Rafael Steil
 * 
 * Represents a &lt;channel&gt; in the RSS Document
 */
public class RSSChannel 
{
	private String channelTitle;
	private String channelLink;
	private String channelDescription;
	
	private ArrayList itemsList = new ArrayList();
	
	public void setChannelTitle(String channelTitle)
	{
		this.channelTitle = channelTitle;
	}
	
	public void setChannelLink(String channelLink)
	{
		this.channelLink = channelLink;
	}
	
	public void setChannelDescription(String channelDescription)
	{
		this.channelDescription = channelDescription;
	}
	
	public String getChannelTitle()
	{
		return this.channelTitle;
	}
	
	public String getChannelLink()
	{
		return this.channelLink;
	}
	
	public String getChannelDescription()
	{
		return this.channelDescription;
	}
	
	public void addItem(RSSItem item)
	{
		this.itemsList.add(item);
	}
	
	public ArrayList getItems()
	{
		return this.itemsList;
	}
}
