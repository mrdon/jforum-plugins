/*
 * Copyright (c) 2005 Rafael Steil
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
 * Created on Feb 20, 2005 12:00:02 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.admin;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import net.jforum.ActionServletRequest;
import net.jforum.Command;
import net.jforum.SessionFacade;
import net.jforum.model.DataAccessDriver;
import net.jforum.repository.BBCodeRepository;
import net.jforum.repository.ForumRepository;
import net.jforum.repository.ModulesRepository;
import net.jforum.repository.PostRepository;
import net.jforum.repository.RankingRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.repository.SmiliesRepository;
import net.jforum.repository.TopicRepository;
import net.jforum.util.bbcode.BBCodeHandler;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: CacheAction.java,v 1.2 2005/02/21 20:32:13 rafaelsteil Exp $
 */
public class CacheAction extends Command
{
	/**
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception
	{
		this.context.put("moduleAction", "cache_list.htm");
		
		this.context.put("bb", new BBCodeRepository());
		this.context.put("modules", new ModulesRepository());
		this.context.put("ranking", new RankingRepository());
		this.context.put("smilies", new SmiliesRepository());
		this.context.put("security", new SecurityRepository());
		this.context.put("forum", new ForumRepository());
		this.context.put("topic", new TopicRepository());
		this.context.put("session", new SessionFacade());
		this.context.put("posts", new PostRepository());
	}
	
	public void bbReload() throws Exception
	{
		BBCodeRepository.setBBCollection(new BBCodeHandler().parse());
		this.list();
	}
	
	public void sessionClear() throws Exception
	{
		SessionFacade.clear();
		this.list();
	}
	
	public void modulesReload() throws Exception
	{
		ModulesRepository.init(SystemGlobals.getValue(ConfigKeys.CONFIG_DIR));
		this.list();
	}
	
	public void smiliesReload() throws Exception
	{
		SmiliesRepository.loadSmilies();
		this.list();
	}
	
	public void rankingReload() throws Exception
	{
		RankingRepository.loadRanks();
		this.list();
	}
	
	public void postsMoreInfo() throws Exception
	{
		if (!SystemGlobals.getBoolValue(ConfigKeys.POSTS_CACHE_ENABLED)) {
			this.list();
			return;
		}
		
		Collection topics = PostRepository.cachedTopics();
		
		this.context.put("topics", DataAccessDriver.getInstance().newTopicModel().selectTopicTitlesByIds(topics));
		this.context.put("repository", new PostRepository());
		this.context.put("moduleAction", "post_repository_info.htm");
	}
	
	public void postsClear() throws Exception
	{
		int topicId = this.request.getIntParameter("topic_id");
		PostRepository.clearCache(topicId);
		this.postsMoreInfo();
	}

	/**
	 * @see net.jforum.Command#process()
	 */
	public Template process(ActionServletRequest request, 
			HttpServletResponse response, 
			Connection conn, SimpleHash context) throws Exception 
	{
		if (AdminAction.isAdmin()) {
			super.process(request, response, conn, context);
		}
		
		return AdminAction.adminBaseTemplate();
	}
}
