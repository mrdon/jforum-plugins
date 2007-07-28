/*
 * Copyright (c) JForum Team
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
 * This file creation date: 25/02/2004 - 19:16:25
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.search;

import java.util.Date;

/**
 * @author Rafael Steil
 * @version $Id: SearchArgs.java,v 1.1 2007/07/28 20:07:17 rafaelsteil Exp $
 */
public class SearchArgs 
{
	private String keywords = "";
	private String author;
	private String orderBy = "ASC";
	private String orderByField;
	
	private boolean matchAllKeywords;
	private boolean searchStarted;
	
	private int forumId;
	
	private Date time;
	
	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
	}
	
	public void matchAllKeywords()
	{
		this.matchAllKeywords = true;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public void setForumId(int forumId)
	{
		this.forumId = forumId;
	}
	
	public void setOrderByField(String f)
	{
		this.orderByField = f;
	}
	
	public void setSearchStarted(boolean started)
	{
		this.searchStarted = started;
	}
	
	public void setOrderBy(String orderBy)
	{
		this.orderBy = (orderBy == null ? "ASC" : orderBy);
	}
	
	public void setTime(Date time) 
	{
		this.time = time;
	}
	
	public String[] getKeywords()
	{
		if (this.keywords == null || this.keywords.trim().length() == 0) {
			return new String[] {};
		}

		return this.keywords.trim().split(" ");
	}
	
	public boolean shouldMatchAllKeywords()
	{
		return this.matchAllKeywords;
	}
	
	public String getAuthor()
	{
		return this.author;
	}
	
	public int getForumId()
	{
		return this.forumId;
	}
	
	public String getOrderBy()
	{
		if (!"ASC".equals(this.orderBy) && !"DESC".equals(this.orderBy)) {
			return "ASC";
		}

		return this.orderBy;
	}
	
	public String getOrderByField()
	{
		return this.orderByField;
	}
	
	public Date getTime()
	{
		return this.time;
	}
	
	public boolean getSearchStarted()
	{
		return this.searchStarted;
	}
}
