/*
 * Created on 08/12/2004 23:18:22
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.entities;

import java.util.Iterator;

import junit.framework.TestCase;
import net.jforum.ConfigLoader;
import net.jforum.TestCaseUtils;

/**
 * Test some general <code>net.jforum.entities.Category</code> methods.
 * This is not some best practice test case, since it relies on the
 * ThreadLocal settings and system roles. But, anyway, it helps
 * to test :)
 * 
 * @author Rafael Steil
 * @version $Id: CategoryTest.java,v 1.6 2006/04/29 14:14:27 rafaelsteil Exp $
 */
public class CategoryTest extends TestCase 
{
	private Category category;

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception 
	{
		TestCaseUtils.loadEnvironment();
		ConfigLoader.startCacheEngine();
		
		this.category = new CategoryTestCommon().createCategoryAndForums();
	}
	
	public void testReloadForum()
	{
		Forum f1 = this.category.getForum(1);
		Forum f2 = this.category.getForum(4);
		
		// Now, change its order and names
		f1.setName("Forum 1 changed");
		f1.setOrder(4);
		
		f2.setName("Forum 4 changed");
		f2.setOrder(1);
		
		// Updates and check if the order changed as well
		this.category.reloadForum(f1);
		
		String[] expectedNames = { "Forum 4 changed", "Forum 2", "Forum 3", "Forum 1 changed", "Forum 5" };
		this.checkExpectedNames(expectedNames);
	}
	
	public void testRemoveForum()
	{
		this.category.removeForum(3);
		this.checkExpectedNames(new String[]{ "Forum 1", "Forum 2", "Forum 4", "Forum 5" });
	}
	
	private void checkExpectedNames(String[] expectedNames)
	{
		int i = 0; 
		for (Iterator iter = this.category.getForums().iterator(); iter.hasNext(); ) {
			Forum f = (Forum)iter.next();
			assertEquals(expectedNames[i++], f.getName());
		}
	}
}
