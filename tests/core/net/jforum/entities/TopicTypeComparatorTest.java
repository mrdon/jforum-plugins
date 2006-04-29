/*
 * Created on 29/04/2006 10:48:18
 */
package net.jforum.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Rafael Steil
 * @version $Id: TopicTypeComparatorTest.java,v 1.1 2006/04/29 14:14:28 rafaelsteil Exp $
 */
public class TopicTypeComparatorTest extends TestCase
{
	public void testPassRandomOrderExpectResultInCorrectOrder()
	{
		List l = new ArrayList();
		
		l.add(this.createTopic(Topic.TYPE_NORMAL, 1, "Regular day 1"));
		l.add(this.createTopic(Topic.TYPE_STICKY, 1, "Sticky day 1"));
		l.add(this.createTopic(Topic.TYPE_STICKY, 2, "Sticky day 2"));
		l.add(this.createTopic(Topic.TYPE_NORMAL, 3, "Regular day 3"));
		l.add(this.createTopic(Topic.TYPE_ANNOUNCE, 5, "Announce day 5"));
		l.add(this.createTopic(Topic.TYPE_ANNOUNCE, 4, "Announce day 4"));
		l.add(this.createTopic(Topic.TYPE_NORMAL, 6, "Regular day 6"));
		l.add(this.createTopic(Topic.TYPE_STICKY, 7, "Sticky day 7"));
		
		Collections.sort(l, new TopicTypeComparator());
		
		Assert.assertEquals("Announce day 5", this.extractTitle(l, 0));
		Assert.assertEquals("Announce day 4", this.extractTitle(l, 1));
		Assert.assertEquals("Sticky day 7", this.extractTitle(l, 2));
		Assert.assertEquals("Sticky day 2", this.extractTitle(l, 3));
		Assert.assertEquals("Sticky day 1", this.extractTitle(l, 4));
		Assert.assertEquals("Regular day 6", this.extractTitle(l, 5));
		Assert.assertEquals("Regular day 3", this.extractTitle(l, 6));
		Assert.assertEquals("Regular day 1", this.extractTitle(l, 7));
	}
	
	private String extractTitle(List l, int index)
	{
		return ((Topic)l.get(index)).getTitle();
	}
	
	private Topic createTopic(int type, int day, String title)
	{
		Topic t = new Topic();
		
		t.setTitle(title);
		t.setType(type);
		t.setTime(new GregorianCalendar(2006, 4, day).getTime());
		
		return t;
	}
}
