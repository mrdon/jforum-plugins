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
import net.jforum.entities.Post;
import net.jforum.entities.Topic;

/**
 * @author Rafael Steil
 * @version $Id: SearchResult.java,v 1.3 2007/07/25 17:44:33 rafaelsteil Exp $
 */
public class SearchResult
{
	private Date date;
	private Forum forum;
	private Topic topic;
	private Post post;
	private String subject;
	private String contents;
	
	/**
	 * @return the subject
	 */
	public String getSubject()
	{
		return this.subject;
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

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * @param forum the forum to set
	 */
	public void setForum(Forum forum)
	{
		this.forum = forum;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents)
	{
		this.contents = contents;
	}

	/**
	 * @return the topic
	 */
	public Topic getTopic()
	{
		return this.topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(Topic topic)
	{
		this.topic = topic;
	}

	/**
	 * @return the post
	 */
	public Post getPost()
	{
		return this.post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(Post post)
	{
		this.post = post;
	}
}
