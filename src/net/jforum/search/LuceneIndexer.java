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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.jforum.entities.Post;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

/**
 * @author Rafael Steil
 * @version $Id: LuceneIndexer.java,v 1.2 2007/07/27 15:57:06 rafaelsteil Exp $
 */
public class LuceneIndexer
{
	private static final Logger logger = Logger.getLogger(LuceneIndexer.class);
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
	
	public synchronized void create(Post post)
	{
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
	
	public synchronized void update(Post post)
	{
		IndexReader reader = null;
		
		try {
			reader = IndexReader.open(this.settings.directory());
			reader.deleteDocuments(new Term(SearchFields.Keyword.POST_ID, String.valueOf(post.getId())));
			
			this.create(post);
		}
		catch (IOException e) {
			logger.error(e.toString(), e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e) {}
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
		d.add(new Field(SearchFields.Keyword.DATE, this.settings.formatDateTime(p.getTime()), Store.YES, Index.UN_TOKENIZED));
		
		// We add the subject and message text together because, when searching, we only care about the 
		// matches, not where it was performed. The real subject and contents will be fetched from the database
		d.add(new Field(SearchFields.Indexed.CONTENTS, p.getSubject() + " " + p.getText(), Store.NO, Index.TOKENIZED));
		
		return d;
	}
	
	private void notifyNewDocumentAdded()
	{
		for (Iterator iter = this.newDocumentAddedList.iterator(); iter.hasNext(); ) {
			((NewDocumentAdded)iter.next()).newDocumentAdded();
		}
	}
}
