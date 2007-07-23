/*
 * Created on 18/07/2007 14:03:15
 */
package net.jforum.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.jforum.dao.SearchData;
import net.jforum.entities.Post;

/**
 * @author Rafael Steil
 * @version $Id: LuceneSearchTestCase.java,v 1.12 2007/07/23 16:28:02 rafaelsteil Exp $
 */
public class LuceneSearchTestCase extends TestCase
{
	private static boolean logInitialized;
	
	private LuceneSearchIndexer indexer;
	private LuceneSearch search;
	
	public void testIndexTwoPostsWithOneCommonWordSearchCommonWordExpectOneResult()
	{
		List l = new ArrayList();
		
		// 1
		Post p = this.newPost();
		p.setText("a regular text with some magic word");
		l.add(p);
		
		// 2
		p = this.newPost();
		p.setText("say shazan to see the magic happen");
		l.add(p);
		
		this.indexer.insertSearchWords(l);
		
		// Search
		SearchData sd = new SearchData();
		sd.setKeywords("magic regular");
		
		List results = this.search.search(sd);
		
		Assert.assertEquals(1, results.size());
	}
	
	public void testIndexThreePostsSearchContentsExpectOneResult()
	{
		List l = new ArrayList();
		
		// 1
		Post p = this.newPost();
		p.setSubject("java");
		l.add(p);
		
		// 2
		p = this.newPost();
		p.setSubject("something else");
		l.add(p);
		
		// 3
		p = this.newPost();
		p.setSubject("debug");
		l.add(p);
		
		this.indexer.insertSearchWords(l);
		
		// Search
		SearchData sd = new SearchData();
		sd.setKeywords("java");
		
		List results = this.search.search(sd);
		
		Assert.assertEquals(1, results.size());
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
		if (!logInitialized) {
			DOMConfigurator.configure(this.getClass().getResource("/log4j.xml"));
			logInitialized = true;
		}
		
		this.indexer = new LuceneSearchIndexer();
		this.indexer.useRAMDirectory();
		
		this.search = new LuceneSearch();
		this.search.setDirectory(this.indexer.directoryImplementation());
		
		this.indexer.watchNewDocuDocumentAdded(this.search);
	}
}
