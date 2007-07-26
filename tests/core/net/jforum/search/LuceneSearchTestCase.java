/*
 * Created on 18/07/2007 14:03:15
 */
package net.jforum.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.jforum.dao.SearchArgs;
import net.jforum.entities.Post;

/**
 * @author Rafael Steil
 * @version $Id: LuceneSearchTestCase.java,v 1.18 2007/07/26 16:08:33 rafaelsteil Exp $
 */
public class LuceneSearchTestCase extends TestCase
{
	private static boolean logInitialized;
	
	private LuceneSearch search;
	private LuceneSettings settings;
	private LuceneSearchIndexer indexer;
	
	public void testFilterInacessibleForums()
	{
		// TODO
		Assert.assertTrue(false);
	}
	
	public void testFivePostsInTwoForumsSearchOneForumAndTwoValidTermsAndOneInvalidTermExpectThreeResults()
	{
		List l = this.createThreePosts();
		((Post)l.get(0)).setForumId(1);
		((Post)l.get(1)).setForumId(2);
		((Post)l.get(2)).setForumId(1);
		
		this.indexer.index((Post)l.get(0));
		this.indexer.index((Post)l.get(1));
		this.indexer.index((Post)l.get(2));
		
		// Post 4
		Post p = this.newPost();
		p.setText("It introduces you to searching, sorting, filtering and highlighting [...]");
		p.setForumId(1);
		this.indexer.index(p);
		
		// Post 5
		p = this.newPost();
		p.setText("How to integrate lucene into your applications");
		p.setForumId(2);
		l.add(p);
		
		this.indexer.index(p);
		
		// Search
		SearchArgs args = new SearchArgs();
		args.setForumId(1);
		args.setKeywords("open lucene xpto authoritative");
		
		List results = this.search.search(args);
		
		Assert.assertEquals(3, results.size());
	}
	
	public void testORExpressionUsingThreePostsSearchTwoTermsExpectThreeResults()
	{
		List l = this.createThreePosts();
		
		this.indexer.index((Post)l.get(0));
		this.indexer.index((Post)l.get(1));
		this.indexer.index((Post)l.get(2));
		
		// Search
		SearchArgs args = new SearchArgs();
		args.setKeywords("open lucene");
		
		List results = this.search.search(args);
		
		Assert.assertEquals(3, results.size());
	}

	private List createThreePosts()
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
		
		return l;
	}
	
	public void testANDExpressionUsingTwoPostsWithOneCommonWordSearchTwoTermsExpectOneResult()
	{
		// 1
		Post p = this.newPost();
		p.setText("a regular text with some magic word");
		this.indexer.index(p);
		
		// 2
		p = this.newPost();
		p.setText("say shazan to see the magic happen");
		this.indexer.index(p);
		
		// Search
		SearchArgs args = new SearchArgs();
		args.matchAllKeywords();
		args.setKeywords("magic regular");
		
		List results = this.search.search(args);
		
		Assert.assertEquals(1, results.size());
	}
	
	public void testThreePostsSearchContentsExpectOneResult()
	{
		// 1
		Post p = this.newPost();
		p.setSubject("java");
		this.indexer.index(p);
		
		// 2
		p = this.newPost();
		p.setSubject("something else");
		this.indexer.index(p);
		
		// 3
		p = this.newPost();
		p.setSubject("debug");
		this.indexer.index(p);
		
		// Search
		SearchArgs args = new SearchArgs();
		args.setKeywords("java");
		
		List results = this.search.search(args);
		
		Assert.assertEquals(1, results.size());
	}
	
	public void testTwoDifferentForumsSearchOneExpectOneResult()
	{
		Post p1 = this.newPost();
		p1.setForumId(1);
		this.indexer.index(p1);
		
		Post p2 = this.newPost();
		p2.setForumId(2);
		this.indexer.index(p2);
		
		SearchArgs args = new SearchArgs();
		args.setForumId(1);
		
		List results = this.search.search(args);
		
		Assert.assertEquals(1, results.size());
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
		
		this.settings = new LuceneSettings(new StandardAnalyzer(), 1);
		this.settings.useRAMDirectory();
		
		this.indexer = new LuceneSearchIndexer();
		this.indexer.setSettings(this.settings);
		
		this.search = new LuceneSearch();
		this.search.setSettings(this.settings);
		
		this.indexer.watchNewDocuDocumentAdded(this.search);
	}
}
