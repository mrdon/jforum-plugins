/*
 * Copyright (c) 2003, Rafael Steil
 * All rights reserved.

 * Redistribution and use in source and binary forms, 
 * with or without modification, are permitted provided 
 * that the following conditions are met:

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
 * This file creation date: Mar 3, 2003 / 11:07:02 AM
 * net.jforum.view.admin.GroupVH.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.PermissionControl;
import net.jforum.security.XMLPermissionControl;
import net.jforum.util.SystemGlobals;
import net.jforum.util.TreeGroup;
import net.jforum.entities.Group;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.GroupModel;
import net.jforum.model.security.GroupSecurityModel;
import net.jforum.util.I18n;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * ViewHelper class for group administration.
 * 
 * @author Rafael Steil
 */
public class GroupVH extends Command 
{
	// Listing
	public void list() throws Exception
	{
		JForum.getContext().put("groups", new TreeGroup().getNodes());
		JForum.getContext().put("moduleAction", "group_list.htm");	
	}
	
	// Insert
	public void insert() throws Exception
	{
		JForum.getContext().put("groups", new TreeGroup().getNodes());
		JForum.getContext().put("action", "insertSave");
		JForum.getContext().put("selectedList", new ArrayList());
		JForum.getContext().put("moduleAction", "group_form.htm");
	}
	
	// Save information for an existing group
	public void editSave() throws Exception
	{
		int groupId = Integer.parseInt(JForum.getRequest().getParameter("group_id"));
			
		Group g = new Group();
		g.setDescription(JForum.getRequest().getParameter("group_description"));
		g.setId(groupId);
		g.setParentId(Integer.parseInt(JForum.getRequest().getParameter("parent_id")));
		g.setName(JForum.getRequest().getParameter("group_name"));

		DataAccessDriver.getInstance().newGroupModel().update(g);
			
		this.list();
	}
	
	// Edit a group
	public void edit() throws Exception
	{
		int groupId = Integer.parseInt(JForum.getRequest().getParameter("group_id"));
		GroupModel gm = DataAccessDriver.getInstance().newGroupModel();
					
		JForum.getContext().put("group", gm.selectById(groupId));
		JForum.getContext().put("groups", new TreeGroup().getNodes());
		JForum.getContext().put("selectedList", new ArrayList());
		JForum.getContext().put("moduleAction", "group_form.htm");
		JForum.getContext().put("action", "editSave");	
	}
	
	// Deletes a group
	public void delete() throws Exception
	{		
		String groupId[] = JForum.getRequest().getParameterValues("group_id");
		if (groupId == null) {
			this.list();
			
			return;
		}
		
		ArrayList errors = new ArrayList();
		GroupModel gm = DataAccessDriver.getInstance().newGroupModel();
			
		for (int i = 0; i < groupId.length; i++) {
			int id = Integer.parseInt(groupId[i]);
			if (gm.canDelete(id)) {
				gm.delete(id);
			}
			else {
				errors.add(I18n.getMessage(I18n.CANNOT_DELETE_GROUP, new Object[] {new Integer(id)}));
			}
		}
		
		if (errors.size() > 0) {
			JForum.getContext().put("errorMessage", errors);
		}
			
		this.list();
	}
	
	// Saves a new group
	public void insertSave() throws Exception
	{
		GroupModel gm = DataAccessDriver.getInstance().newGroupModel();
		
		Group g = new Group();
		g.setDescription(JForum.getRequest().getParameter("group_description"));
		g.setParentId(Integer.parseInt(JForum.getRequest().getParameter("parent_id")));
		g.setName(JForum.getRequest().getParameter("group_name"));
			
		gm.addNew(g);			
			
		this.list();
	}
	
	// Permissions
	public void permissions() throws Exception
	{
		int id = Integer.parseInt(JForum.getRequest().getParameter("group_id"));
		
		GroupSecurityModel gmodel = DataAccessDriver.getInstance().newGroupSecurityModel();

		PermissionControl pc = new PermissionControl();
		pc.setSecurityModel(gmodel);
		pc.setRoles(gmodel.loadRoles(id));
		
		List sections = new XMLPermissionControl(pc).loadConfigurations(SystemGlobals.getApplicationResourceDir() +"config/permissions.xml");
		
		GroupModel gm = DataAccessDriver.getInstance().newGroupModel();

		JForum.getContext().put("sections", sections);
		JForum.getContext().put("group", gm.selectById(id));
		JForum.getContext().put("moduleAction", "group_security_form.htm");
	}
	
	public void permissionsSave() throws Exception
	{
		int id = Integer.parseInt(JForum.getRequest().getParameter("id"));
		
		GroupSecurityModel gmodel = DataAccessDriver.getInstance().newGroupSecurityModel();
		
		PermissionControl pc = new PermissionControl();
		pc.setSecurityModel(gmodel);
		
		new PermissionProcessHelper(pc, id).processData();
		
		GroupModel gm = DataAccessDriver.getInstance().newGroupModel();

		Iterator iter = gm.selectUsersIds(id).iterator();
		while (iter.hasNext()) {
			SecurityRepository.remove(Integer.parseInt(iter.next().toString()));
		}
		
		this.list();
	}
	
	/*
	 * @see net.jforum.Command#process()
	 */
	public Template process() throws Exception 
	{
		if (AdminVH.isAdmin()) {
			super.process();
		}
		
		return Configuration.getDefaultConfiguration().getTemplate("admin/index_main.htm");
	}

}
