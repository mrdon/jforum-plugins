/*
 * Created on 08/12/2004 23:20:44
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.entities;

import net.jforum.TestCaseUtils;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.PermissionControl;

/**
 * Common methods for testing categories.
 * 
 * @author Rafael Steil
 * @version $Id: CategoryTestCommon.java,v 1.5 2006/04/29 14:14:28 rafaelsteil Exp $
 */
public class CategoryTestCommon 
{
	/**
	 * Creates a <code>net.jforum.entities.Category</code> instance 
	 * and populates it with some forums. 
	 * The roles are set to "allow all" to an user_id equals to "1"
	 * 
	 * @return The category instance with all forums and roles set
	 * @throws Exception
	 */
	public Category createCategoryAndForums() throws Exception
	{
		Category category = new Category();
		
		int allow = PermissionControl.ROLE_ALLOW;
		int super_user = 1;
		
		int[] categoryIds = { 1 };
		int[] categoryRights = { allow };
		
		int[] forumIds = { 1, 2, 3, 4, 5 };
		int[] forumRights = { allow, allow, allow, allow, allow };
		
		SecurityRepository.add(super_user, TestCaseUtils.createForumCategoryPermissionControl(categoryIds, categoryRights, 
				forumIds, forumRights));

		TestCaseUtils.loadEnvironment();
		TestCaseUtils.createThreadLocalData(super_user);
		
		category = new Category();
		category.setId(1);
		
		Forum f = new Forum();
		f.setId(2);
		f.setIdCategories(1);
		f.setName("Forum 2");
		f.setOrder(2);
		category.addForum(f);
		
		f = new Forum();
		f.setId(5);
		f.setIdCategories(1);
		f.setName("Forum 5");
		f.setOrder(5);
		category.addForum(f);
		
		f = new Forum();
		f.setId(1);
		f.setIdCategories(1);
		f.setName("Forum 1");
		f.setOrder(1);
		category.addForum(f);
		
		f = new Forum();
		f.setId(3);
		f.setIdCategories(1);
		f.setName("Forum 3");
		f.setOrder(3);
		category.addForum(f);
		
		f = new Forum();
		f.setId(4);
		f.setIdCategories(1);
		f.setName("Forum 4");
		f.setOrder(4);
		category.addForum(f);
		
		return category;
	}
}
