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

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import net.jforum.dao.SearchIndexerDAO;
import net.jforum.entities.Post;
import net.jforum.exceptions.ForumException;

/**
 * @author Rafael Steil
 * @version $Id: LuceneSearchIndexer.java,v 1.1 2007/07/18 21:02:25 rafaelsteil Exp $
 */
public class LuceneSearchIndexer implements SearchIndexerDAO
{
	private static final Logger logger = Logger.getLogger(LuceneSearchIndexer.class);
	private static final Object MUTEX = new Object();
	
	private Directory directory;
	private Analyzer analyzer;
	private List newDocumentAddedList = new ArrayList();
	
	public void useRAMDirectory()
	{
		this.directory = new RAMDirectory();
	}
	
	public void useFSDirectory(String indexWritePath)
	{
		try {
			File indexDirectory = new File(indexWritePath);
			
			if (!indexDirectory.exists()) {
				this.createIndexDirectory(indexDirectory);
			}
			
			this.directory = FSDirectory.getDirectory(indexDirectory);
		}
		catch (IOException e) {
			throw new ForumException(e);
		}
	}
	
	public void watchNewDocuDocumentAdded(NewDocumentAdded newDoc)
	{
		this.newDocumentAddedList.add(newDoc);
	}
	
	private void createIndexDirectory(File directory) throws IOException 
	{
		logger.info("Creating new index search directory");
		
		FSDirectory fsDir = FSDirectory.getDirectory(directory);
		IndexWriter writer = new IndexWriter(fsDir, this.analyzer, true);
		writer.close();
		
		logger.info("Search index directory created");
	}
	
	public void setAnalyzer(Analyzer analyzer)
	{
		this.analyzer = analyzer;
	}
	
	/**
	 * @see net.jforum.dao.SearchIndexerDAO#insertSearchWords(java.util.List)
	 */
	public void insertSearchWords(List posts)
	{
		synchronized (MUTEX) {
			try {
				IndexWriter writer = new IndexWriter(this.directory, this.analyzer, false);
				
				for (Iterator iter = posts.iterator(); iter.hasNext(); ) {
					Post post = (Post)iter.next();
					
					this.write(writer, this.createDocument(post));
					this.optimize(writer);
					
					if (logger.isDebugEnabled()) {
						logger.debug("Indexing post #" + post.getId());
					}
				}
				
				writer.flush();
				writer.close();
				
				this.notifyNewDocumentAdded();
			}
			catch (Exception e) {
				logger.error(e.toString(), e);
			}
		}
	}
	
	private void write(IndexWriter writer, Document document) throws Exception
	{
		try {
			writer.addDocument(document);
		}
		catch (Exception e) {
			logger.error(e.toString(), e);
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
		
		return d;
	}
	
	private void notifyNewDocumentAdded()
	{
		for (Iterator iter = this.newDocumentAddedList.iterator(); iter.hasNext(); ) {
			((NewDocumentAdded)iter.next()).newDocument();
		}
	}

	/**
	 * @see net.jforum.dao.SearchIndexerDAO#insertSearchWords(net.jforum.entities.Post)
	 */
	public void insertSearchWords(Post post)
	{
		List l = new ArrayList();
		l.add(post);
		this.insertSearchWords(l);
	}

	/**
	 * Does nothing
	 */
	public void setConnection(Connection conn) { }
}
