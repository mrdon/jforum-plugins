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
 * This file creating date: Feb 19, 2003 / 8:56:56 PM
 * net.jforum.model.UserModel.java
 * The JForum Project
 * http://www.jforum.net 
 */
package net.jforum.model;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import net.jforum.entities.User;

/**
  * Model interface for {@link net.jforum.User}.
 * This interface defines methods which are expected to be
 * implementd by a specific data access driver. The intention is
 * to provide all functionality needed to update, insert, delete and
 * select some specific data.
 * 
 * @author Rafael Steil
 */
public interface UserModel 
{
	/**
	 * Gets a specific <code>User</code>.
	 * 
	 * @param userId The User ID to search
	 * @return <code>User</code>object containing all the information
	 * @throws Exception
	 * @see #selectAll
	 */
	public User selectById(int userId) throws Exception;
	
	/**
	 * Gets all users
	 * 
	 * @return <code>ArrayList</code> with the users. Each entry is an <code>User</code> object
	 * @throws Exception
	 */
	public ArrayList selectAll() throws Exception;
	
	/**
	 * Gets all users
	 * 
	 * @param startFrom Index to start fetching from
	 * @param count Number of records to retrieve
	 * @return <code>ArrayList</code> with the users. Each entry is an <code>User</code> object
	 * @throws Exception
	 */
	public ArrayList selectAll(int startFrom, int count) throws Exception;
	

	/**
	 * Deletes an user.
	 * 
	 * @param userId The user ID to delete
	 * @throws Exception
	 * @see #undelete(int)
	 */
	public void delete(int userId) throws Exception;
	
	/**
	 * Undeletes an user.
	 * The system allows user undeletation because when you 
	 * call {@link #delete(int)} the user isn't fisically deleted of the
	 * database, but just marked as deleted. This is done to ensure
	 * data integrity.
	 * 
	 * @param userId The user ID to undelete
	 * @throws Exception
	 * @see #delete(int)
	 */
	public void undelete(int userId) throws Exception;
	
	/**
	 * Updates a user.
	 * 
	 * @param user Reference to a <code>User</code> object to update
	 * @throws Exception
	 * @see #update(int)
	 */
	public void update(User user) throws Exception;
	
	/**
	 * Adds a new User.
	 * 
	 * @param user Reference to a valid and configured <code>User</code> object
	 * @return The new user id
	 * @throws Exception
	 */
	public int addNew(User user) throws Exception;
	
	/**
	 * Set the active status.
	 * An user with the active status equals to false cannot be considered
	 * a "oficial", "fully registered" user until its status is set to true. This is
	 * interesting when you want to request user confirmation about registrations,
	 * for example
	 * 
	 * @param userId The user ID to change the status
	 * @param active <code>true</code> or <code>false</code>
	 * @throws Exception
	 */
	public void setActive(int userId, boolean active) throws Exception;
	
	/**
	 * Sets the ranking.
	 * 
	 * @param userId The user ID
	 * @throws Exception
	 */
	public void setRanking(int userId, int rankingId) throws Exception;
	
	/**
	 * Increments the number of posts of the user.
	 * 
	 * @param userId The user ID to increment the number of posts
	 * @throws Exception
	 */
	public void incrementPosts(int userId) throws Exception;
	
	/**
	 * Decrement the number of posts of some user.
	 * 
	 * @param userId The user ID do decrement the number of posts.
	 * @throws Exception
	 */
	public void decrementPosts(int userId) throws Exception;
	
	/**
	 * Gest some piece of information of the last user registered
	 * 
	 * @return <code>HashMap</code> containing the information. The map
	 * has two keys:<br>
	 * <li><b>userName</b>: The username
	 * <li><b>userId</b>: The user's ID 
	 * @throws Exception
	 */
	public HashMap getLastUserInfo() throws Exception;
	
	/**
	 * Gets the total number of users
	 * 
	 * @return The total number of users
	 * @throws Exception
	 */
	public int getTotalUsers() throws Exception;
	
	/***
	 * Checks the existence of some username.
	 * This method is used to ensure that will not be two equal usernames in the database.
	 * 
	 * @param username The username to verify
	 * @return <code>true</code> or <code>false</code>, if the user was found or not, respectively
	 * @throws Exception
	 */
	public boolean isUsernameRegistered(String username) throws Exception;
	
	/**
	 * Validates the user login.
	 * 
	 * @param username The username
	 * @param password The password
	 * @return The user id if the provided information was corret, <code>0</code> if the information was invalid 
	 * @throws Exception
	 */
	public int validateLogin(String username, String password) throws NoSuchAlgorithmException, Exception;
	
	/**
	 * Associate the user to the group
	 * 
	 * @param userId The user id 
	 * @param groupId The group id to associate to
	 * @throws Exception
	 */
	public void addToGroup(int userId, int[] groupId) throws Exception;
	
	/**
	 * Remove the user from the group
	 * 
	 * @param userId The user id
	 * @param groupId The group id to remove the user from
	 */
	public void removeFromGroup(int userId, int[] groupId) throws Exception;
}
