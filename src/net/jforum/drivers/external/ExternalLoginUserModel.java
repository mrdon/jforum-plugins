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
 * Created on Aug 2, 2004 by pieter
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.external;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.jforum.entities.Group;
import net.jforum.entities.User;
import net.jforum.model.UserModel;
import net.jforum.util.MD5;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

public class ExternalLoginUserModel implements UserModel {
	private UserModel delegateUserModel;
	private LoginServer loginServer;
	
	public ExternalLoginUserModel(UserModel delegateUserModel) throws SQLException {
		this.delegateUserModel = delegateUserModel;
		loginServer = new JdbcLoginServer();
	}

	public int addNew(User user) throws Exception {
		return delegateUserModel.addNew(user);
	}
	
	public void addToGroup(int userId, int[] groupId) throws Exception {
		delegateUserModel.addToGroup(userId, groupId);
	}
	
	public void decrementPosts(int userId) throws Exception {
		delegateUserModel.decrementPosts(userId);
	}
	
	public void delete(int userId) throws Exception {
		delegateUserModel.delete(userId);
	}
	
	public List findByName(String input, boolean exactMath) throws Exception {
		return delegateUserModel.findByName(input, exactMath);
	}
	
	public HashMap getLastUserInfo() throws Exception {
		return delegateUserModel.getLastUserInfo();
	}
	
	public int getTotalUsers() throws Exception {
		return delegateUserModel.getTotalUsers();
	}
	
	public String getUsernameByEmail(String email) throws Exception {
		return delegateUserModel.getUsernameByEmail(email);
	}
	
	public void incrementPosts(int userId) throws Exception {
		delegateUserModel.incrementPosts(userId);
	}
	
	public boolean isUsernameRegistered(String username) throws Exception {
		return delegateUserModel.isUsernameRegistered(username);
	}
	
	public void removeFromGroup(int userId, int[] groupId) throws Exception {
		delegateUserModel.removeFromGroup(userId, groupId);
	}
	
	public void saveNewPassword(String password, String email) throws Exception {
		delegateUserModel.saveNewPassword(password, email);
	}
	
	public ArrayList selectAll() throws Exception {
		return delegateUserModel.selectAll();
	}
	
	public ArrayList selectAll(int startFrom, int count) throws Exception {
		return delegateUserModel.selectAll(startFrom, count);
	}
	
	public User selectById(int userId) throws Exception {
		return delegateUserModel.selectById(userId);
	}
	
	public User selectByName(String username) throws Exception {
		return delegateUserModel.selectByName(username);
	}
	
	public void setActive(int userId, boolean active) throws Exception {
		delegateUserModel.setActive(userId, active);
	}
	
	public void setRanking(int userId, int rankingId) throws Exception {
		delegateUserModel.setRanking(userId, rankingId);
	}
	
	public void undelete(int userId) throws Exception {
		delegateUserModel.undelete(userId);
	}
	
	public void update(User user) throws Exception {
		delegateUserModel.update(user);
	}
	
	public User validateLogin(String username, String password) throws Exception {
		System.err.println("*** validating login: " + username  + ", " + password);
		int userId = loginServer.validateLogin(username, password);
		if (userId < 0) {
			// The external login server failed to verify the existence of the user
			return null;
		}
		System.err.println("*** userId=" + userId);
		
		// Get the groups the user should be part of
		int[] groups = loginServer.getGroups(userId);
		System.err.print("*** " + groups.length + " groups: ");
		for (int i=0; i<groups.length; i++) {
			System.err.print(groups[i] + ",");
		}
		System.err.println();
		
		String cryptedPassword = MD5.crypt(password);
		
		User user = selectById(userId);
		
		if (user.getId() == 0) {
			// We need to add a new user, because none exist with the specified user id
			System.err.println("*** adding new user");
			autoAddUser(userId, username, cryptedPassword, groups);
			user = null;
		} else {
			System.err.println("*** user found: " + user.getUsername() + ", password=" + user.getPassword() + ", uid=" + user.getId());
			// Name and/or password might have changed, so update them in the database
			checkNamePasswordChange(user, username, cryptedPassword);
			
			// Make sure the user is still in the right groups
			checkGroupChanges(user, groups);
		}
		
		// Continue with the validation sequence. Note that the delegate validator might still
		// reject the user based on some other criteria than the name/password combo!
		User validUser = delegateUserModel.validateLogin(username, password);
		
		// Perform some sanity checks
		System.err.println("*** validUser: " + validUser.getUsername() + ", password=" + validUser.getPassword() + ", uid=" + validUser.getId());
		if (validUser != null && (validUser.getId() != userId || !validUser.getUsername().equals(username) || !validUser.getPassword().equals(cryptedPassword))) {
			throw new AssertionError("validUser does not match");
		}
		
		return validUser;
	}
	
	private void autoAddUser(int userId, String username, String cryptedPassword, int[] groups) throws Exception {
		User user;
		user = new User();
		user.setUsername(username);
		user.setPassword(cryptedPassword);
		user.setEmail("");
		user.setId(userId);
		addNewWithId(user);
		addToGroup(userId, groups);
	}

	private void checkNamePasswordChange(User user, String username, String cryptedPassword) throws Exception {
		String oldName = user.getUsername();
		String oldPassword = user.getPassword();
		
		boolean userChanged = false;
		
		if (!username.equals(oldName)) {
			if (SystemGlobals.getBoolValue(ConfigKeys.EXTERNAL_UNIQUE_NAMES)) {
				updateName(username, new HashSet());
			}
			user.setUsername(username);
			userChanged = true;
		}

		if (!cryptedPassword.equals(oldPassword)) {
			user.setPassword(cryptedPassword);
			userChanged = true;
		}
		
		if (userChanged) {
			update(user);
		}
	}
	
	private void updateName(String username, Set names) throws Exception {
		User user = selectByName(username);
		if (user != null) {
			String newName = loginServer.getName(user.getId());
			if (newName.equals(username)) {
				throw new RuntimeException("duplicate username in external login server: " + username);
			}
			if (names.contains(newName)) {
				throw new RuntimeException("cyclic duplicate username in external login server: " + newName);
			}
			names.add(newName);
			updateName(newName, names);
			user.setUsername(newName);
			System.err.println("**** " + username + " has been changed into " + newName);
			update(user);
		}
	}

	private void checkGroupChanges(User user, int[] groups) throws Exception {
		int userId = user.getId();
		List oldGroups = user.getGroupsList();
		for (Iterator iter = oldGroups.iterator(); iter.hasNext(); ) {
			int oldGroup = ((Group)iter.next()).getId();
			boolean found = false;
			for (int i=0; !found && i<groups.length; i++) {
				if (groups[i] == oldGroup) {
					groups[i] = -1;
					found = true;
				}
			}
			
			// User is no longer in this group, so remove him/her
			if (!found) {
				removeFromGroup(userId, new int[] { oldGroup });
			}
		}
		
		// Add user to any new groups
		for (int i=0; i<groups.length; i++) {
			if (groups[i] >= 0) {
				//System.err.println("*** adding user " + user.getId() + " to group: " + groups[i]);
				addToGroup(userId, new int[] { groups[i] });
			}
		}
	}

	public boolean validateLostPasswordHash(String email, String hash) throws Exception {
		return delegateUserModel.validateLostPasswordHash(email, hash);
	}
	
	public void writeLostPasswordHash(String email, String hash) throws Exception {
		delegateUserModel.writeLostPasswordHash(email, hash);
	}

	public void addNewWithId(User user) throws Exception {
		delegateUserModel.addNewWithId(user);
	}
}
