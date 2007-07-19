/*
 * Created on 18/07/2007 14:03:15
 */
package net.jforum.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.jforum.entities.Post;

/**
 * @author Rafael Steil
 * @version $Id: LuceneSearchTestCase.java,v 1.3 2007/07/19 00:17:36 rafaelsteil Exp $
 */
public class LuceneSearchTestCase extends TestCase
{
	private LuceneSearchIndexer indexer;
	
	public void testIndexTwoDifferentForumsSearchOneExpectOneResult()
	{
		List l = new ArrayList();
		
		Post p1 = new Post();
		p1.setTime(new Date());
		p1.setForumId(1);
		
		l.add(p1);
		
		Post p2 = new Post();
		p2.setTime(new Date());
		p2.setForumId(2);
		
		l.add(p2);
		
		this.indexer.insertSearchWords(l);
		
		
	}
	
	public void testWatchNewDocumentAddedExpectOneNotification()
	{
		class HitTest {
			boolean value;
		}
		
		final HitTest hitTest = new HitTest();
		
		this.indexer.watchNewDocuDocumentAdded(new NewDocumentAdded() {
			public void newDocument() {
				hitTest.value = true;
			}
		});
		
		this.indexer.insertSearchWords(new ArrayList());
		
		Assert.assertTrue(hitTest.value);
	}
	
	protected void setUp() throws Exception
	{
		this.indexer = new LuceneSearchIndexer();
		this.indexer.useRAMDirectory();
	}
}
