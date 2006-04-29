/*
 * Created on 17/11/2004 22:03:01
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.entities;

import java.util.Iterator;

import junit.framework.TestCase;
import net.jforum.ConfigLoader;
import net.jforum.TestCaseUtils;
import net.jforum.repository.ForumRepository;
import net.jforum.security.PermissionControl;

/**
 * @author Rafael Steil
 * @version $Id: ForumOrderTest.java,v 1.8 2006/04/29 14:14:28 rafaelsteil Exp $
 */
public class ForumOrderTest extends TestCase 
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
	
	public void testForumOrder()
	{
		String[] expectedNames = { "Forum 1", "Forum 2", "Forum 3", "Forum 4", "Forum 5" };
		this.checkExpectedNames(expectedNames);
	}
	
	public void testReload()
	{
		Forum f1 = new Forum(this.category.getForum(1));
		f1.setOrder(3);
		this.category.changeForumOrder(f1);
		this.checkExpectedNames(new String[] { "Forum 3", "Forum 2", "Forum 1", "Forum 4", "Forum 5" });
		
		assertEquals(5, this.category.getForums().size());
		
		Forum f5 = new Forum(this.category.getForum(5));
		f5.setOrder(2);
		this.category.changeForumOrder(f5);
		this.checkExpectedNames(new String[] { "Forum 3", "Forum 5", "Forum 1", "Forum 4", "Forum 2" });
		
		assertEquals(5, this.category.getForums().size());
		
		f1 = new Forum(this.category.getForum(1));
		f1.setOrder(1);
		this.category.changeForumOrder(f1);
		this.checkExpectedNames(new String[] { "Forum 1", "Forum 5", "Forum 3", "Forum 4", "Forum 2" });
	}
	
	public void testReloadUsingRepository()
	{
		this.configureRepository();
		
		Forum f = new Forum(ForumRepository.getForum(3));
		f.setOrder(1);
		ForumRepository.getCategory(this.pc(), f.getCategoryId()).changeForumOrder(f);
		this.checkExpectedNames(new String[] { "Forum 3", "Forum 2", "Forum 1", "Forum 4", "Forum 5" });
		assertEquals(5, ForumRepository.getCategory(this.pc(), f.getCategoryId()).getForums().size());
		
		f = new Forum(ForumRepository.getForum(4));
		f.setOrder(2);
		ForumRepository.getCategory(this.pc(), f.getCategoryId()).changeForumOrder(f);
		this.checkExpectedNames(new String[] { "Forum 3", "Forum 4", "Forum 1", "Forum 2", "Forum 5" });
		assertEquals(5, ForumRepository.getCategory(this.pc(), f.getCategoryId()).getForums().size());
	}
	
	private void checkExpectedNames(String[] expectedNames)
	{
		int i = 0; 
		for (Iterator iter = this.category.getForums().iterator(); iter.hasNext(); ) {
			Forum f = (Forum)iter.next();
			assertEquals(expectedNames[i++], f.getName());
		}
	}
	
	private void configureRepository()
	{
		ForumRepository.addCategory(this.category);
	}
	
	private PermissionControl pc()
	{
		return new PermissionControl() {
			public boolean canAccess(String roleName, String roleValue) {
				return true;
			}
		};
	}
}
