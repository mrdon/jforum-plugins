/*
 * Created on 18/07/2007 14:03:15
 */
package net.jforum.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.jforum.dao.SearchData;
import net.jforum.entities.Post;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 * @author Rafael Steil
 * @version $Id: LuceneSearchTestCase.java,v 1.7 2007/07/19 03:53:01 rafaelsteil Exp $
 */
public class LuceneSearchTestCase extends TestCase
{
	private LuceneSearchIndexer indexer;
	private LuceneSearch search;
	
	public void testX() throws Exception
	{
		Directory dir = new RAMDirectory();
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer());
		
		Document d = new Document();
		d.add(new Field("forum.id", "1", Store.YES, Index.UN_TOKENIZED));
		
		writer.addDocument(d);
		
		writer.close();
		
		IndexSearcher search = new IndexSearcher(dir);
		Hits hits = search.search(new TermQuery(new Term("forum.id", "1")));
		
		Assert.assertEquals(1, hits.length());
	}
	
	public void testIndexTwoDifferentForumsSearchOneExpectOneResult()
	{
		List l = new ArrayList();
		
		Post p1 = this.newPost();
		p1.setForumId(1);
		
		l.add(p1);
		
		Post p2 = this.newPost();
		p2.setForumId(2);
		
		l.add(p2);
		
		this.indexer.insertSearchWords(l);
		
		SearchData sd = new SearchData();
		sd.setForumId(1);
		
		List results = this.search.search(sd);
		
		Assert.assertEquals(1, results.size());
	}
	
	public void testWatchNewDocumentAddedExpectOneNotification()
	{
		class HitTest {
			boolean value;
		}
		
		final HitTest hitTest = new HitTest();
		
		this.indexer.watchNewDocuDocumentAdded(new NewDocumentAdded() {
			public void newDocumentAdded() {
				hitTest.value = true;
			}
		});
		
		this.indexer.insertSearchWords(new ArrayList());
		
		Assert.assertTrue(hitTest.value);
	}
	
	private Post newPost() 
	{
		Post p = new Post();
		
		p.setText("");
		p.setTime(new Date());
		p.setSubject("");
		p.setPostUsername("");
		
		return p;
	}
	
	protected void setUp() throws Exception
	{
		this.indexer = new LuceneSearchIndexer();
		this.indexer.useRAMDirectory();
		
		this.search = new LuceneSearch();
		this.search.setDirectory(this.indexer.directoryImplementation());
		
		this.indexer.watchNewDocuDocumentAdded(this.search);
	}
}
