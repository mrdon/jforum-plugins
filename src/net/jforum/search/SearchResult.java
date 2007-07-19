/*
 * Copyright (c) JForum Team
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
 * Created on 18/07/2007 21:11:46
 * 
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.search;

import java.util.Date;

import net.jforum.entities.Forum;
import net.jforum.entities.User;

/**
 * @author Rafael Steil
 * @version $Id: SearchResult.java,v 1.1 2007/07/19 00:17:35 rafaelsteil Exp $
 */
public class SearchResult
{
	private String subject;
	private User user;
	private Forum forum;
	private Date date;
	private String contents;
	
	public SearchResult(String subject, String contents, 
		User user, Forum forum, Date date)
	{
		this.subject = subject;
		this.contents = contents;
		this.user = user;
		this.forum = forum;
		this.date = date;
	}
	
	/**
	 * @return the subject
	 */
	public String getSubject()
	{
		return this.subject;
	}
	
	/**
	 * @return the user
	 */
	public User getUser()
	{
		return this.user;
	}
	
	/**
	 * @return the forum
	 */
	public Forum getForum()
	{
		return this.forum;
	}
	
	/**
	 * @return the date
	 */
	public Date getDate()
	{
		return this.date;
	}

	/**
	 * @return the contents
	 */
	public String getContents()
	{
		return this.contents;
	}
}
