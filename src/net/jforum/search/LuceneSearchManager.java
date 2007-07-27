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
 * Created on 24/07/2007 12:23:05
 * 
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.search;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;

import net.jforum.dao.SearchArgs;
import net.jforum.entities.Post;
import net.jforum.exceptions.ForumException;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: LuceneSearchManager.java,v 1.6 2007/07/27 13:55:48 rafaelsteil Exp $
 */
public class LuceneSearchManager implements SearchManager
{
	private LuceneSearch search;
	private LuceneSettings settings;
	private LuceneSearchIndexer indexer;
	
	/**
	 * @see net.jforum.search.SearchManager#init()
	 */
	public void init()
	{
		this.indexer = new LuceneSearchIndexer();
		this.search = new LuceneSearch();
		
		try {
			Analyzer analyzer = (Analyzer)Class.forName(SystemGlobals.getValue(
				ConfigKeys.LUCENE_ANALYZER)).newInstance();
			
			this.settings = new LuceneSettings(analyzer, 
				SystemGlobals.getIntValue(ConfigKeys.LUCENE_HIGHLIGHTER_FRAGMENTS));
			this.settings.useFSDirectory(SystemGlobals.getValue(ConfigKeys.LUCENE_INDEX_WRITE_PATH));
			
			this.indexer.setSettings(this.settings);
			this.search.setSettings(this.settings);
			
			this.indexer.watchNewDocuDocumentAdded(this.search);
		}
		
		catch (Exception e) {
			throw new ForumException(e);
		}
	}
	
	/**
	 * @see net.jforum.search.SearchManager#create(net.jforum.entities.Post)
	 */
	public void create(Post post)
	{
		this.indexer.create(post);
	}
	
	/**
	 * @see net.jforum.search.SearchManager#update(net.jforum.entities.Post)
	 */
	public void update(Post post)
	{
		this.indexer.update(post);
	}

	/**
	 * @see net.jforum.search.SearchManager#search(net.jforum.dao.SearchArgs)
	 */
	public List search(SearchArgs args)
	{
		return this.search.search(args);
	}
}
