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
 * Created on 13/10/2004 23:47:06
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import freemarker.template.Template;
import net.jforum.Command;
import net.jforum.JForum;

/**
 * @author Rafael Steil
 * @version $Id: RSSAction.java,v 1.1 2004/10/17 05:26:56 rafaelsteil Exp $
 */
public class RSSAction extends Command 
{
	/**
	 * RSS for a specific forum
	 * @throws Exception
	 */
	public void forum() throws Exception
	{
		int forumId = Integer.parseInt(JForum.getRequest().getParameter("forum_id"));
	}
	
	/**
	 * RSS for all forums.
	 * Show rss syndication containing information about
	 * all available forums
	 * @throws Exception
	 */
	public void forums() throws Exception
	{
		
	}
	
	/**
	 * RSS for all N first topics for some given forum
	 * @throws Exception
	 */
	public void topics() throws Exception
	{
		int forumId = Integer.parseInt(JForum.getRequest().getParameter("forum_id")); 
	}
	
	/**
	 * RSS for all N first posts for some given topic
	 * @throws Exception
	 */
	public void topic() throws Exception
	{
		int topicId = Integer.parseInt(JForum.getRequest().getParameter("topic_id")); 
	}
	
	/** 
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception {
		this.forums();
	}
	
	/** 
	 * @see net.jforum.Command#process()
	 */
	public Template process() throws Exception {
		return super.process();
	}

}
