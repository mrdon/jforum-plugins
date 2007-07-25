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
 * Created on 23/07/2007 15:14:27
 * 
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.admin;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.index.IndexReader;

import freemarker.template.SimpleHash;
import freemarker.template.Template;

import net.jforum.context.RequestContext;
import net.jforum.context.ResponseContext;
import net.jforum.dao.DataAccessDriver;
import net.jforum.dao.LuceneDAO;
import net.jforum.entities.Post;
import net.jforum.repository.ForumRepository;
import net.jforum.search.LuceneSearchIndexer;
import net.jforum.search.LuceneSettings;
import net.jforum.search.SearchFacade;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.util.preferences.TemplateKeys;

/**
 * @author Rafael Steil
 * @version $Id: LuceneStatsAction.java,v 1.5 2007/07/25 19:53:07 rafaelsteil Exp $
 */
public class LuceneStatsAction extends AdminCommand
{
	/**
	 * @see net.jforum.Command#list()
	 */
	public void list()
	{
		this.setTemplateName(TemplateKeys.SEARCH_STATS_ADMIN_LIST);
	}
	
	public void createIndexDirectory() throws Exception
	{
		this.settings().createIndexDirectory(
			new File(SystemGlobals.getValue(ConfigKeys.LUCENE_INDEX_WRITE_PATH)));
		this.reindexMain();
	}
	
	public void reconstructIndexFromScratch()
	{
		LuceneDAO dao = DataAccessDriver.getInstance().newLuceneDAO();
		int start = 0;
		int howMany = 20;
		boolean hasMorePosts = true;
		
		while (hasMorePosts) {
			List l = dao.getPostsToIndex(start, howMany);
			
			for (Iterator iter = l.iterator(); iter.hasNext(); ) {
				Post p = (Post)iter.next();
				SearchFacade.index(p);
			}
			
			start += howMany;
			hasMorePosts = l.size() > 0;
		}
		
		this.reindexMain();
	}
	
	public void reindexMain()
	{
		File indexDir = new File(SystemGlobals.getValue(ConfigKeys.LUCENE_INDEX_WRITE_PATH));
		
		this.setTemplateName(TemplateKeys.SEARCH_STATS_REINDEX);
		
		this.context.put("indexExists", IndexReader.indexExists(indexDir));
		this.context.put("indexLocation", indexDir.getAbsolutePath());
		this.context.put("totalMessages", new Integer(ForumRepository.getTotalMessages()));
	}
	
	public void luceneNotEnabled()
	{
		this.setTemplateName(TemplateKeys.SEARCH_STATS_NOT_ENABLED);
	}
	
	public Template process(RequestContext request, ResponseContext response, SimpleHash context)
	{
		if (!this.isSearchEngineLucene()) {
			this.ignoreAction();
			this.luceneNotEnabled();
		}
		
		return super.process(request, response, context);
	}
	
	private boolean isSearchEngineLucene()
	{
		return LuceneSearchIndexer.class.getName()
			.equals(SystemGlobals.getValue(ConfigKeys.SEARCH_INDEXER_IMPLEMENTATION))
			|| this.settings() == null;
	}
	
	private LuceneSettings settings()
	{
		return (LuceneSettings)SystemGlobals.getObjectValue(ConfigKeys.LUCENE_SETTINGS);
	}
}
