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
 * This file creation date: 19/03/2004 - 18:41:30
 * net.jforum.model.security.UserSecurityModel.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: UserSecurityModel.java,v 1.2 2004/04/21 23:57:26 rafaelsteil Exp $
 */
package net.jforum.model.security;

import net.jforum.entities.User;
import net.jforum.security.RoleCollection;

/**
 * @author Rafael Steil
 */
public interface UserSecurityModel extends SecurityModel 
{
	public void deleteUserRoleByGroup(int groupId, String roleName) throws Exception;
	
	public RoleCollection loadRoles(User user) throws Exception;

	/**
	 * Deletes a specific role value of all users who belongs to the group passed as parameter.
	 * 
	 * @param groupId The group id
	 * @param roleName The role name
	 * @param roleValues An array containing all values to be deleted
	 * @throws Exception
	 */
	public void deleteUserRoleValuesByGroup(int groupId, String roleName, String[] roleValues) throws Exception;
	
	/**
	 * Deletes a specific role value of all users who belongs to the group passed as parameter.
	 * 
	 * @param groupId The group id
	 * @param roleName The role name
	 * @param roleValue The role value
	 * @throws Exception
	 */
	public void deleteUserRoleValuesByGroup(int groupId, String roleName, String roleValue) throws Exception;
	
	/**
	 * Deletes all role values of all users who belongs to the group passed as parameter.
	 * 
	 * @param groupId The group id
	 * @param roleName The role name
	 * @throws Exception
	 */
	public void deleteUserRoleValuesByGroup(int groupId, String roleName) throws Exception;
}
