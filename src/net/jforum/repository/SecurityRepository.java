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
 * This file creation date: 18/11/2003 / 23:09:15
 * net.jforum.repository.SecurityRepository.java
 */
package net.jforum.repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.jforum.SessionFacade;
import net.jforum.entities.User;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.UserModel;
import net.jforum.model.security.UserSecurityModel;
import net.jforum.security.PermissionControl;

/**
 * @author Rafael Steil
 */
public class SecurityRepository 
{
	private static int MAX_USERS = 50;
	
	private static Map securityInfoMap = new LinkedHashMap(MAX_USERS) {
		protected boolean removeEldestEntry(Entry eldest) {
			return this.size() > MAX_USERS;
		}
	};
	
	private SecurityRepository() { }
	
	/***
	 * Load user's roles. 
	 * 
	 * @param userId The user's id
	 * @param force If <code>true</code>, forces a reload. If <code>false</code>, the call
	 * will be ignored if the roles are already loaded.
	 * 
	 * @see SecurityRepository#load(int)
	 * @see SecurityRepository#load(User)
	 * @see SecurityRepository#load(User, boolean)
	 * @throws Exception
	 */
	public static void load(int userId, boolean force) throws Exception
	{
		if (force || SecurityRepository.securityInfoMap.containsKey(new Integer(userId)) == false) {
			UserModel um = DataAccessDriver.getInstance().newUserModel();
			
			SecurityRepository.load(um.selectById(userId), force);
		}
	}

	/**
	 * Load user's roles.
	 * 
	 * @param userId The users's id
	 * 
	 * @see SecurityRepository#load(int, boolean)
	 * @see SecurityRepository#load(User)
	 * @see SecurityRepository#load(User, boolean)
	 * @throws Exception
	 */
	public static void load(int userId) throws Exception
	{
		SecurityRepository.load(userId, false);
	}
	
	/**
	 * Load user's roles.
	 * 
	 * @param user The <code>User</code> to load.
	 * 
	 * @see SecurityRepository#load(int)
	 * @see SecurityRepository#load(int, boolean), 
	 * @see SecurityRepository#load(User, boolean)
	 * @throws Exception
	 */
	public static void load(User user) throws Exception
	{
		SecurityRepository.load(user, false);
	}

	/**
	 * Load user's roles.
	 * 
	 * @param user The <code>User</code> to load
	 * @param force If <code>true</code>, forces a reload. If <code>false</code>, the call
	 * will be ignored if the roles are already loaded.
	 * 
	 * @see SecurityRepository#load(int)
	 * @see SecurityRepository#load(int, boolean)
	 * @see SecurityRepository#load(User)
	 * @throws Exception
	 */
	public static void load(User user, boolean force) throws Exception
	{
		if (force || SecurityRepository.securityInfoMap.containsKey(new Integer(user.getId())) == false) {
			UserSecurityModel umodel = DataAccessDriver.getInstance().newUserSecurityModel();
			PermissionControl pc = new PermissionControl();
			
			pc.setSecurityModel(umodel);
			pc.setRoles(umodel.loadRoles(user));
			
			SecurityRepository.add(user.getId(), pc);
		}
	}
	
	/**
	 * Check if the logged user has access to the role. 
	 * This method gets user's id from its session.
	 * 
	 * @param roleName The role name to verity
	 * @return <code>true</code> if the user has access to the role, <code>false</code> if access is denied
	 */
	public static boolean canAccess(String roleName)
	{
		return canAccess(SessionFacade.getUserSession().getUserId(), roleName);
	}
	
	public static boolean canAccess(int userId, String roleName)
	{
		if (SecurityRepository.get(userId).canAccess(roleName)) {
			return true;
		}
		
		return false;
	}

	/**
	 * Check if the logged user has access to the role. 
	 * This method gets user's id from its session.
	 * 
	 * @param roleName The role name to verify
	 * @param value The value relacted to the role to verify for access
	 * @return <code>true</code> if the user has access to the role, <code>false</code> if access is denied
	 */
	public static boolean canAccess(String roleName, String value)
	{
		return canAccess(SessionFacade.getUserSession().getUserId(), roleName, value);
	}
	
	public static boolean canAccess(int userId, String roleName, String value)
	{
		if (SecurityRepository.get(userId).canAccess(roleName, value)) {
			return true;
		}
		
		return false;
	}

	public static PermissionControl get(int userId)
	{
		return (PermissionControl)SecurityRepository.securityInfoMap.get(new Integer(userId));
	}

	public static void add(int userId, PermissionControl pc)
	{
		SecurityRepository.securityInfoMap.put(new Integer(userId), pc);
	}
	
	public static void remove(int userId)
	{
		SecurityRepository.securityInfoMap.remove(new Integer(userId));
	}
}
