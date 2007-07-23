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
import net.jforum.dao.SearchArgs;
import net.jforum.entities.Post;

/**
 * @author Rafael Steil
 * @version $Id: LuceneSearchTestCase.java,v 1.13 2007/07/23 16:56:14 rafaelsteil Exp $
 */
public class LuceneSearchTestCase extends TestCase
{
	private static boolean logInitialized;
	
	private LuceneSearchIndexer indexer;
	private LuceneSearch search;
	
	public void testORExpressionUsingThreePostsSearchTwoTermsExpectThreeResults()
	{
		List l = new ArrayList();
		
		// 1
		Post p = this.newPost();
		p.setText("lucene is a gem in the open source world");
		l.add(p);
		
		// 2
		p = this.newPost();
		p.setText("lucene in action is the authoritative guide to lucene");
		l.add(p);
		
		// 3
		p = this.newPost();
		p.setText("Powers search in surprising places [...] open to everyone");
		l.add(p);
		
		this.indexer.insertSearchWords(l);
		
		// Search
		SearchArgs args = new SearchArgs();
		args.setKeywords("open lucene");
		
		List results = this.search.search(args);
		
		Assert.assertEquals(3, results.size());
	}
	
	public void testANDExpressionUsingTwoPostsWithOneCommonWordSearchTwoTermsExpectOneResult()
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
		SearchArgs args = new SearchArgs();
		args.matchAllKeywords();
		args.setKeywords("magic regular");
		
		List results = this.search.search(args);
		
		Assert.assertEquals(1, results.size());
	}
	
	public void testThreePostsSearchContentsExpectOneResult()
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
		SearchArgs args = new SearchArgs();
		args.setKeywords("java");
		
		List results = this.search.search(args);
		
		Assert.assertEquals(1, results.size());
	}
	
	public void testTwoDifferentForumsSearchOneExpectOneResult()
	{
		List l = new ArrayList();
		
		Post p1 = this.newPost();
		p1.setForumId(1);
		
		l.add(p1);
		
		Post p2 = this.newPost();
		p2.setForumId(2);
		
		l.add(p2);
		
		this.indexer.insertSearchWords(l);
		
		SearchArgs args = new SearchArgs();
		args.setForumId(1);
		
		List results = this.search.search(args);
		
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
