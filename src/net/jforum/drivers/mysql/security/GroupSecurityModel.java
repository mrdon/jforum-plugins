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
 * This file creation date: 19/03/2004 - 18:44:56
 * net.jforum.drivers.mysql.security.GroupSecurityModel.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.mysql.security;

import java.sql.PreparedStatement;

import net.jforum.JForum;
import net.jforum.security.Role;
import net.jforum.security.RoleCollection;
import net.jforum.security.RoleValueCollection;
import net.jforum.util.SystemGlobals;

/**
 * @author Rafael Steil
 */
public class GroupSecurityModel implements net.jforum.model.security.GroupSecurityModel 
{
	/* 
	 * @see net.jforum.model.security.SecurityModel#deleteAllRoles(int)
	 */
	public void deleteAllRoles(int id) throws Exception 
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("PermissionControl.deleteAllGroupRoles"));
		p.setInt(1, id);
		p.executeUpdate();
		p.close();		
	}

	/* 
	 * @see net.jforum.model.security.SecurityModel#deleteRole(int, java.lang.String)
	 */
	public void deleteRole(int id, String roleName) throws Exception 
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("PermissionControl.deleteGroupRole"));
		p.setString(1, roleName);
		p.setInt(2, id);
		p.executeUpdate();
		p.close();	
	}

	/* 
	 * @see net.jforum.model.security.SecurityModel#addRole(int, net.jforum.security.Role)
	 */
	public void addRole(int id, Role role) throws Exception 
	{
		this.addRole(id, role, null);
	}

	/* 
	 * @see net.jforum.model.security.SecurityModel#addRole(int, net.jforum.security.Role, net.jforum.security.RoleValueCollection)
	 */
	public void addRole(int id, Role role, RoleValueCollection roleValues) throws Exception 
	{
		SecurityCommon.executeAddRole(SystemGlobals.getSql("PermissionControl.addGroupRole"), id, role, roleValues);
	}

	/* 
	 * @see net.jforum.model.security.SecurityModel#loadRoles(int)
	 */
	public RoleCollection loadRoles(int id) throws Exception 
	{
		return SecurityCommon.processLoadRoles(SystemGlobals.getSql("PermissionControl.loadGroupRoles"), id);
	}

}
