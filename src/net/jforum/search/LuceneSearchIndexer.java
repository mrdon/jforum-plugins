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
 * Created on 18/07/2007 17:18:41
 * 
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.search;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.jforum.entities.Post;
import net.jforum.exceptions.ForumException;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.util.search.SearchManager;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;

/**
 * @author Rafael Steil
 * @version $Id: LuceneSearchIndexer.java,v 1.12 2007/07/23 23:28:33 rafaelsteil Exp $
 */
public class LuceneSearchIndexer implements SearchManager
{
	private static final Logger logger = Logger.getLogger(LuceneSearchIndexer.class);
	private static final Object MUTEX = new Object();
	
	private LuceneSettings settings;
	private List newDocumentAddedList = new ArrayList();
	
	public void setSettings(LuceneSettings settings)
	{
		this.settings = settings;
	}
	
	public void watchNewDocuDocumentAdded(NewDocumentAdded newDoc)
	{
		this.newDocumentAddedList.add(newDoc);
	}

	/**
	 * @see net.jforum.util.search.SearchManager#init()
	 */
	public void init()
	{
		try {
			Analyzer analyzer = (Analyzer)Class.forName(SystemGlobals.getValue(
				ConfigKeys.LUCENE_ANALYZER)).newInstance();
			
			this.setSettings(new LuceneSettings(analyzer));
			this.settings.useFSDirectory(SystemGlobals.getValue(ConfigKeys.LUCENE_INDEX_WRITE_PATH));
		}
		catch (Exception e) {
			throw new ForumException(e);
		}
	}
	
	/**
	 * @see net.jforum.util.search.SearchManager#index(net.jforum.entities.Post)
	 */
	public void index(Post post)
	{
		synchronized (MUTEX) {
			IndexWriter writer = null;
			
			try {
				writer = new IndexWriter(this.settings.directory(), this.settings.analyzer());
				
				Document document = this.createDocument(post);
				writer.addDocument(document);
				
				this.optimize(writer);
				
				if (logger.isDebugEnabled()) {
					logger.debug("Indexed " + document);
				}
			}
			catch (Exception e) {
				logger.error(e.toString(), e);
			}
			finally {
				if (writer != null) {
					try {
						writer.flush();
						writer.close();
						
						this.notifyNewDocumentAdded();
					}
					catch (Exception e) {}
				}
			}
		}
	}
	
	private void optimize(IndexWriter writer) throws Exception
	{
		if (writer.docCount() % 100 == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("Optimizing indexes. Current number of documents is " + writer.docCount());
			}
			
			writer.optimize();
			
			if (logger.isDebugEnabled()) {
				logger.debug("Indexes optimized");
			}
		}
	}
	
	private Document createDocument(Post p)
	{
		Document d = new Document();
		
		d.add(new Field(SearchFields.Keyword.POST_ID, String.valueOf(p.getId()), Store.YES, Index.UN_TOKENIZED));
		d.add(new Field(SearchFields.Keyword.FORUM_ID, String.valueOf(p.getForumId()), Store.YES, Index.UN_TOKENIZED));
		d.add(new Field(SearchFields.Keyword.TOPIC_ID, String.valueOf(p.getTopicId()), Store.YES, Index.UN_TOKENIZED));
		d.add(new Field(SearchFields.Keyword.USER_ID, String.valueOf(p.getUserId()), Store.YES, Index.UN_TOKENIZED));
		
		d.add(new Field(SearchFields.Indexed.DATE, p.getTime().toString(), Store.NO, Index.TOKENIZED));
		
		// We add the subject and message text together because, when searching, we only care about the 
		// matches, not where it was performed. The real subject and contents will be fetched from the database
		d.add(new Field(SearchFields.Indexed.CONTENTS, p.getSubject() + " " + p.getText(), Store.NO, Index.TOKENIZED));
		d.add(new Field(SearchFields.Indexed.USERNAME, p.getPostUsername(), Store.NO, Index.TOKENIZED));
		
		return d;
	}
	
	private void notifyNewDocumentAdded()
	{
		for (Iterator iter = this.newDocumentAddedList.iterator(); iter.hasNext(); ) {
			((NewDocumentAdded)iter.next()).newDocumentAdded();
		}
	}

	/**
	 * Does nothing
	 */
	public void setConnection(Connection conn) { }
}
