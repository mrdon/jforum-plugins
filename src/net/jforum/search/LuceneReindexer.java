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
 * Created on 06/08/2007 15:20:23
 * 
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.search;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.jforum.JForumExecutionContext;
import net.jforum.dao.DataAccessDriver;
import net.jforum.dao.LuceneDAO;
import net.jforum.entities.Post;
import net.jforum.exceptions.ForumException;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.lucene.search.IndexSearcher;

/**
 * @author Rafael Steil
 * @version $Id: LuceneReindexer.java,v 1.1 2007/08/06 21:31:05 rafaelsteil Exp $
 */
public class LuceneReindexer
{
	private LuceneSettings settings;
	private LuceneReindexArgs args;
	private boolean recreate;
	
	public LuceneReindexer(LuceneSettings settings, LuceneReindexArgs args, boolean recreate)
	{
		this.args = args;
		this.recreate = recreate;
		this.settings = settings;
	}
	
	public void startProcess()
	{
		this.reindex();
	}
	
	public void startBackgroundProcess()
	{
		try {
			if (recreate) {
				this.settings.createIndexDirectory(SystemGlobals.getValue(ConfigKeys.LUCENE_INDEX_WRITE_PATH));
			}
		}
		catch (IOException e) {
			throw new ForumException(e);
		}
		
		Runnable indexingJob = new Runnable() {		
			public void run() {
				reindex();
			}
		};
		
		SystemGlobals.setValue(ConfigKeys.LUCENE_CURRENTLY_INDEXING, "1");
		
		Thread thread = new Thread(indexingJob);
		thread.start();
	}

	private void reindex()
	{
		LuceneDAO dao = DataAccessDriver.getInstance().newLuceneDAO();
		
		IndexSearcher searcher = null;
		
		int startPosition = 0;
		int howMany = 20;
		boolean hasMorePosts = true;
		
		try {
			if (!recreate) {
				searcher = new IndexSearcher(this.settings.directory());
			}
			
			LuceneSearch luceneSearch = ((LuceneManager)SearchFacade.manager()).luceneSearch();
			LuceneIndexer luceneIndexer = ((LuceneManager)SearchFacade.manager()).luceneIndexer();
			
			while (hasMorePosts) {
				try {
					JForumExecutionContext ex = JForumExecutionContext.get();
					JForumExecutionContext.set(ex);
					
					List l = dao.getPostsToIndex(args, startPosition, howMany);
					
					for (Iterator iter = l.iterator(); iter.hasNext(); ) {
						if ("0".equals(SystemGlobals.getValue(ConfigKeys.LUCENE_CURRENTLY_INDEXING))) {
							hasMorePosts = false;
							break;
						}
						
						Post post = (Post)iter.next();
						
						if (!recreate && args.avoidDuplicatedRecords()) {
							if (luceneSearch.isPostIndexed(post.getId())) {
								continue;
							}
						}
						
						luceneIndexer.batchCreate(post);
					}
					
					startPosition += howMany;
					hasMorePosts = hasMorePosts && l.size() > 0;
				}
				finally {
					JForumExecutionContext.finish();
				}
			}
		}
		catch (IOException e) {
			throw new ForumException(e);
		}
		finally {
			SystemGlobals.setValue(ConfigKeys.LUCENE_CURRENTLY_INDEXING, "0");
			
			if (searcher != null) {
				try { searcher.close(); }
				catch (Exception e) {}
			}
		}
	}
}
