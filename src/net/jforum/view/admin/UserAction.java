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
 * This file creation date: Apr 19, 2003 / 9:13:16 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.admin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.jforum.ActionServletRequest;
import net.jforum.Command;
import net.jforum.entities.Group;
import net.jforum.entities.User;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.GroupModel;
import net.jforum.model.UserModel;
import net.jforum.model.security.UserSecurityModel;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.PermissionControl;
import net.jforum.security.XMLPermissionControl;
import net.jforum.util.I18n;
import net.jforum.util.TreeGroup;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.view.forum.common.UserCommon;
import net.jforum.view.forum.common.ViewCommon;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: UserAction.java,v 1.14 2004/12/31 16:28:13 rafaelsteil Exp $
 */
public class UserAction extends Command 
{
	// Listing
	public void list() throws Exception
	{
		int start = this.preparePagination(DataAccessDriver.getInstance().newUserModel().getTotalUsers());
		int usersPerPage = SystemGlobals.getIntValue(ConfigKeys.USERS_PER_PAGE);

		this.context.put("users", DataAccessDriver.getInstance().newUserModel().selectAll(start ,usersPerPage));
		this.context.put("moduleAction", "user_list.htm");
	}
	
	private int preparePagination(int totalUsers)
	{
		String s = this.request.getParameter("start");
		int start = ViewCommon.getStartPage();
		int usersPerPage = SystemGlobals.getIntValue(ConfigKeys.USERS_PER_PAGE);
		
		this.context.put("totalPages", new Double(Math.ceil( (double)totalUsers / usersPerPage )));
		this.context.put("recordsPerPage", new Integer(usersPerPage));
		this.context.put("totalRecords", new Integer(totalUsers));
		this.context.put("thisPage", new Double(Math.ceil( (double)(start + 1) / usersPerPage )));
		this.context.put("start", new Integer(start));
		
		return start;
	}
	
	public void search() throws Exception
	{
		List users = new ArrayList();
		String search = this.request.getParameter("username");
		
		if (search != null) {
			users = DataAccessDriver.getInstance().newUserModel().findByName(search, false);
		}
		
		this.context.put("moduleAction", "user_list.htm");
		this.context.put("users", users);
		this.context.put("search", search);
		
		// "start" is added to avoid search error. "start" may be in use when pagination is introduced for search 
		this.context.put("start", new Integer(1));
	}
	
	// Permissions
	public void permissions() throws Exception
	{
		int id = Integer.parseInt(this.request.getParameter("id"));
		
		User user = DataAccessDriver.getInstance().newUserModel().selectById(id);
		
		UserSecurityModel umodel = DataAccessDriver.getInstance().newUserSecurityModel();
		PermissionControl pc = new PermissionControl();
		pc.setSecurityModel(umodel);
		pc.setRoles(umodel.loadRoles(user));
		
		List sections = new XMLPermissionControl(pc).loadConfigurations(
				SystemGlobals.getValue(ConfigKeys.CONFIG_DIR) + "/permissions.xml");
		this.context.put("sections", sections);
		this.context.put("user", user);
		this.context.put("moduleAction", "user_security_form.htm");
	}
	
	// Permissions Save
	public void permissionsSave() throws Exception
	{
		int id = Integer.parseInt(this.request.getParameter("id"));
		User user = DataAccessDriver.getInstance().newUserModel().selectById(id);
		
		UserSecurityModel umodel = DataAccessDriver.getInstance().newUserSecurityModel();
		PermissionControl pc = new PermissionControl();
		pc.setSecurityModel(umodel);

		new PermissionProcessHelper(pc, user.getId()).processData();
		
		// Reload it
		pc.setRoles(umodel.loadRoles(user));

		// Update Security Repository
		SecurityRepository.remove(user.getId());
		SecurityRepository.add(user.getId(), pc);
		
		this.list();
	}
	
	public void edit() throws Exception
	{
		int userId = Integer.parseInt(this.request.getParameter("id"));	
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		User u = um.selectById(userId);
		
		this.context.put("u", u);
		this.context.put("action", "editSave");		
		this.context.put("moduleAction", "user_form.htm");
		this.context.put("admin", true);
	}
	
	public void editSave() throws Exception
	{
		int userId = Integer.parseInt(this.request.getParameter("user_id"));
		UserCommon.saveUser(userId);

		this.list();
	}

	// Delete
	public void delete() throws Exception
	{
		String ids[] = this.request.getParameterValues("user_id");
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				
				int user = Integer.parseInt(ids[i]);
				if (um.isDeleted(user)){
					um.undelete(user);
				} 
				else {
					um.delete(user);
				}
			}
		}
		
		this.list();
	}
	
	// Groups
	public void groups() throws Exception
	{
		int userId = this.request.getIntParameter("id");
		
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		GroupModel gm = DataAccessDriver.getInstance().newGroupModel();
		
		User u = um.selectById(userId);
		
		List selectedList = new ArrayList();
		for (Iterator iter = u.getGroupsList().iterator(); iter.hasNext(); ) {
			selectedList.add(new Integer(((Group)iter.next()).getId()));
		}
		
		this.context.put("selectedList", selectedList);
		this.context.put("groups", new TreeGroup().getNodes());
		this.context.put("user", u);
		this.context.put("userId", new Integer(userId));
		this.context.put("moduleAction", "user_groups.htm");
		this.context.put("groupFor", I18n.getMessage("User.GroupsFor", new String[] { u.getUsername() }));
	}
	
	// Groups Save
	public void groupsSave() throws Exception
	{
		int userId = Integer.parseInt(this.request.getParameter("user_id"));
		
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		GroupModel gm = DataAccessDriver.getInstance().newGroupModel();
		
		// Remove the old groups
		List allGroupsList = gm.selectAll();
		int[] allGroups = new int[allGroupsList.size()];
		
		int counter = 0;
		for (Iterator iter = allGroupsList.iterator(); iter.hasNext(); counter++) {
			Group g = (Group)iter.next();
			
			allGroups[counter] = g.getId();
		}
		
		um.removeFromGroup(userId, allGroups);
		
		// Associate the user to the selected groups
		String[] selectedGroups = this.request.getParameterValues("groups");
		int[] newGroups = new int[selectedGroups.length];
		
		for (int i = 0; i < selectedGroups.length; i++) {
			newGroups[i] = Integer.parseInt(selectedGroups[i]);
		}
		
		um.addToGroup(userId, newGroups);
		
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
