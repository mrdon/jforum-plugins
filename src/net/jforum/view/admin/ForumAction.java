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

import java.util.ArrayList;
import java.util.Iterator;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.entities.Forum;
import net.jforum.model.CategoryModel;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.ForumModel;
import net.jforum.model.GroupModel;
import net.jforum.model.security.GroupSecurityModel;
import net.jforum.repository.ForumRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.PermissionControl;
import net.jforum.security.Role;
import net.jforum.security.RoleValue;
import net.jforum.security.RoleValueCollection;
import net.jforum.security.SecurityConstants;
import net.jforum.util.TreeGroup;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: ForumAction.java,v 1.3 2004/11/17 02:16:24 rafaelsteil Exp $
 */
public class ForumAction extends Command 
{
	// Listing
	public void list() throws Exception
	{
		JForum.getContext().put("categories", ForumRepository.getAllCategories(true));
		JForum.getContext().put("moduleAction", "forum_list.htm");
	}
	
	// One more, one more
	public void insert() throws Exception
	{
		CategoryModel cm = DataAccessDriver.getInstance().newCategoryModel();
		
		JForum.getContext().put("groups", new TreeGroup().getNodes());
		JForum.getContext().put("selectedList", new ArrayList());
		JForum.getContext().put("moduleAction", "forum_form.htm");
		JForum.getContext().put("categories",cm.selectAll());
		JForum.getContext().put("action", "insertSave");		
	}
	
	// Edit
	public void edit() throws Exception
	{
		CategoryModel cm = DataAccessDriver.getInstance().newCategoryModel();
		
		JForum.getContext().put("forum", DataAccessDriver.getInstance().newForumModel().selectById(Integer.parseInt(JForum.getRequest().getParameter("forum_id"))));
		JForum.getContext().put("categories", cm.selectAll());
		JForum.getContext().put("moduleAction", "forum_form.htm");
		JForum.getContext().put("action", "editSave");
	}
	
	//  Save information
	public void editSave() throws Exception
	{
		Forum f = new Forum();
		f.setDescription(JForum.getRequest().getParameter("description"));
		f.setId(Integer.parseInt(JForum.getRequest().getParameter("forum_id")));
		f.setIdCategories(Integer.parseInt(JForum.getRequest().getParameter("categories_id")));
		f.setModerated(JForum.getRequest().getParameter("moderated") != null && JForum.getRequest().getParameter("moderated").equals("true"));
		f.setName(JForum.getRequest().getParameter("forum_name"));
			
		DataAccessDriver.getInstance().newForumModel().update(f);
		
		ForumRepository.reloadForum(f.getId());
		
		this.list();
	}
	
	public void up() throws Exception
	{
		int forumId = Integer.parseInt(JForum.getRequest().getParameter("forum_id"));
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		fm.setOrderUp(Integer.parseInt(JForum.getRequest().getParameter("forum_id")));

		ForumRepository.reloadForum(forumId);
		this.list();
	}
	
	public void down() throws Exception
	{
		int forumId = Integer.parseInt(JForum.getRequest().getParameter("forum_id"));
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		fm.setOrderDown(forumId);
		
		ForumRepository.reloadForum(forumId);
		this.list();
	}
	
	// Delete
	public void delete() throws Exception
	{
		String ids[] = JForum.getRequest().getParameterValues("forum_id");
		
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				fm.delete(Integer.parseInt(ids[i]));
				
				Forum f = new Forum();
				f.setId(Integer.parseInt(ids[i]));
				ForumRepository.removeForum(f);
			}
		}
		
		this.list();
	}
	
	// A new one
	public void insertSave() throws Exception
	{
		Forum f = new Forum();
		f.setDescription(JForum.getRequest().getParameter("description"));
		f.setIdCategories(Integer.parseInt(JForum.getRequest().getParameter("categories_id")));
		f.setModerated(JForum.getRequest().getParameter("moderated") != null && JForum.getRequest().getParameter("moderated").equals("true"));
		f.setName(JForum.getRequest().getParameter("forum_name"));	
			
		int forumId = DataAccessDriver.getInstance().newForumModel().addNew(f);
		f.setId(forumId);
		
		ForumRepository.addForum(f);
		
		String[] groups = JForum.getRequest().getParameterValues("groups");
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
				
				Iterator iter = gm.selectUsersIds(groupId).iterator();
				while (iter.hasNext()) {
					SecurityRepository.remove(Integer.parseInt(iter.next().toString()));
				}
			}
		}
		
		this.list();
	}
	
	/** 
	 * @see net.jforum.Command#process()
	 */
	public Template process() throws Exception 
	{	
		if (AdminAction.isAdmin()) {
			super.process();
		}

		return AdminAction.adminBaseTemplate();
	}

}
