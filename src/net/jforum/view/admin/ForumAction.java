/*
 * Copyright (c) 2003, 2004 Rafael Steil
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
 * This file creation date: Mar 28, 2003 / 8:21:56 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.admin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.jforum.ActionServletRequest;
import net.jforum.Command;
import net.jforum.entities.Category;
import net.jforum.entities.Forum;
import net.jforum.model.CategoryModel;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.ForumModel;
import net.jforum.model.GroupModel;
import net.jforum.model.TopicModel;
import net.jforum.model.security.GroupSecurityModel;
import net.jforum.repository.ForumRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.PermissionControl;
import net.jforum.security.Role;
import net.jforum.security.RoleValue;
import net.jforum.security.RoleValueCollection;
import net.jforum.security.SecurityConstants;
import net.jforum.util.TreeGroup;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: ForumAction.java,v 1.9 2004/12/27 00:30:51 rafaelsteil Exp $
 */
public class ForumAction extends Command 
{
	// Listing
	public void list() throws Exception
	{
		this.context.put("categories", DataAccessDriver.getInstance().newCategoryModel().selectAll());
		this.context.put("repository", new ForumRepository());
		this.context.put("moduleAction", "forum_list.htm");
	}
	
	// One more, one more
	public void insert() throws Exception
	{
		CategoryModel cm = DataAccessDriver.getInstance().newCategoryModel();
		
		this.context.put("groups", new TreeGroup().getNodes());
		this.context.put("selectedList", new ArrayList());
		this.context.put("moduleAction", "forum_form.htm");
		this.context.put("categories",cm.selectAll());
		this.context.put("action", "insertSave");		
	}
	
	// Edit
	public void edit() throws Exception
	{
		CategoryModel cm = DataAccessDriver.getInstance().newCategoryModel();
		
		this.context.put("forum", DataAccessDriver.getInstance().newForumModel().selectById(Integer.parseInt(this.request.getParameter("forum_id"))));
		this.context.put("categories", cm.selectAll());
		this.context.put("moduleAction", "forum_form.htm");
		this.context.put("action", "editSave");
	}
	
	//  Save information
	public void editSave() throws Exception
	{
		Forum f = new Forum();
		f.setDescription(this.request.getParameter("description"));
		f.setId(Integer.parseInt(this.request.getParameter("forum_id")));
		f.setIdCategories(Integer.parseInt(this.request.getParameter("categories_id")));
		f.setModerated(this.request.getParameter("moderated") != null && this.request.getParameter("moderated").equals("true"));
		f.setName(this.request.getParameter("forum_name"));
			
		DataAccessDriver.getInstance().newForumModel().update(f);
		
		ForumRepository.reloadForum(f.getId());
		
		this.list();
	}
	
	public void up() throws Exception
	{
		this.processOrdering(true);
	}
	
	public void down() throws Exception
	{
		this.processOrdering(false);
	}
	
	private void processOrdering(boolean up) throws Exception
	{
		Forum toChange = new Forum(ForumRepository.getForum(Integer.parseInt(
				this.request.getParameter("forum_id"))));
		
		Category category = ForumRepository.getCategory(toChange.getCategoryId());
		List forums = new ArrayList(category.getForums());
		int index = forums.indexOf(toChange);
		
		if (index == -1 || (up && index == 0) || (!up && index + 1 == forums.size())) {
			this.list();
			return;
		}
		
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		
		if (up) {
			// Get the forum which comes *before* the forum we're changing
			Forum otherForum = new Forum((Forum)forums.get(index - 1));
			fm.setOrderUp(toChange, otherForum);
		}
		else {
			// Get the forum which comes *after* the forum we're changing
			Forum otherForum = new Forum((Forum)forums.get(index + 1));
			fm.setOrderDown(toChange, otherForum);
		}
		
		category.changeForumOrder(toChange);
		this.list();
	}
	
	// Delete
	public void delete() throws Exception
	{
		String ids[] = this.request.getParameterValues("forum_id");
		
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		TopicModel tm = DataAccessDriver.getInstance().newTopicModel();
		
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				int forumId = Integer.parseInt(ids[i]);

				tm.deleteByForum(forumId);
				fm.delete(forumId);
				
				Forum f = new Forum();
				f.setId(forumId);
				ForumRepository.removeForum(f);
			}
		}
		
		this.list();
	}
	
	// A new one
	public void insertSave() throws Exception
	{
		Forum f = new Forum();
		f.setDescription(this.request.getParameter("description"));
		f.setIdCategories(Integer.parseInt(this.request.getParameter("categories_id")));
		f.setModerated(this.request.getParameter("moderated") != null && this.request.getParameter("moderated").equals("true"));
		f.setName(this.request.getParameter("forum_name"));	
			
		int forumId = DataAccessDriver.getInstance().newForumModel().addNew(f);
		f.setId(forumId);
		
		ForumRepository.addForum(f);
		
		String[] groups = this.request.getParameterValues("groups");
		if (groups != null) {
			GroupModel gm = DataAccessDriver.getInstance().newGroupModel();
			GroupSecurityModel gmodel = DataAccessDriver.getInstance().newGroupSecurityModel();
			PermissionControl pc = new PermissionControl();
			pc.setSecurityModel(gmodel);

			Role role = new Role();
			role.setName(SecurityConstants.PERM_FORUM);

			for (int i = 0; i < groups.length; i++) {
				int groupId = Integer.parseInt(groups[i]);
				RoleValueCollection roleValues = new RoleValueCollection();
				
				RoleValue rv = new RoleValue();
				rv.setType(PermissionControl.ROLE_ALLOW);
				rv.setValue(Integer.toString(forumId));
				
				roleValues.add(rv);
				
				pc.addRole(groupId, role, roleValues);
			}
		}
		
		SecurityRepository.clean();
		this.list();
	}
	
	/** 
	 * @see net.jforum.Command#process()
	 */
	public Template process(ActionServletRequest request, 
			HttpServletResponse response, 
			Connection conn, SimpleHash context) throws Exception 
	{	
		if (AdminAction.isAdmin()) {
			super.process(request, response, conn, context);
		}

		return AdminAction.adminBaseTemplate();
	}

}
