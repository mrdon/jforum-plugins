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
 * The JForum Project
 * http://www.jforum.net 
 */
package net.jforum.model;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.jforum.entities.User;

/**
  * Model interface for {@link net.jforum.User}.
 * This interface defines methods which are expected to be
 * implementd by a specific data access driver. The intention is
 * to provide all functionality needed to update, insert, delete and
 * select some specific data.
 * 
 * @author Rafael Steil
 * @version $Id: UserModel.java,v 1.10 2004/09/25 01:58:42 jamesyong Exp $
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
	 * Gets a specific <code>User</code>.
	 * 
	 * @param username The User name to search
	 * @return <code>User</code>object containing all the information
	 * @throws Exception
	 * @see #selectAll
	 */
	public User selectByName(String username) throws Exception;
	
	/**
	 * Gets all users
	 * 
	 * @return <code>ArrayList</code> with the users. Each entry is an <code>User</code> object
	 * @throws Exception
	 */
	public ArrayList selectAll() throws Exception;
	
	/**
	 * Finds an user by matching an input string. 
	 * 
	 * @param input The username to search. May be part of the username. 
	 * The method will match all users who have the input string as 
	 * part of their usernames.
	 * @param exactMath Set to <code>true</code> to get the user data related to 
	 * the username passed as argument, and set it to <code>false</code> to 
	 * search all users who match the criteria. 
	 * @return <code>List</code> with the found users. Each entry is an 
	 * <code>User</code> object, where only the <i>id</i> and <i>username</i>
	 * members are filled.
	 * @throws Exception exact
	 */
	public List findByName(String input, boolean exactMath) throws Exception;
	
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
	 * Adds a new user with a predefined user id
	 * 
	 * (added by Pieter for external login support)
	 * @param user Reference to a valid and configured <code>User</code> object, with the user id already set
	 * @throws Exception
	 */
	public void addNewWithId(User user) throws Exception ;
	
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
	
	/**
	 * whether the user is locked or not.
	 * 
	 * @return boolean
	 * @throws Exception
	 */	
	public boolean isDeleted(int user_id) throws Exception;
	
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
	 * @return The user object if the provided information was corret, <code>null</code> if the information was invalid 
	 * @throws Exception
	 */
	public User validateLogin(String username, String password) throws NoSuchAlgorithmException, Exception;
	
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
	
	/**
	 * Stores the "lost password" security hash, that was generated
	 * when the user asked the system to get a reminder of his password. 
	 * This hash is used to ensure the information supplied.  
	 * 
	 * @param email The user email
	 * @param hash The hash to store.
	 * @throws Exception
	 */
	public void writeLostPasswordHash(String email, String hash) throws Exception;
	
	/**
	 * Validate the provided security hash against the data stored in our system.
	 * 
	 * @param email The user email
	 * @param hash The supplied security hash
	 * @return <code>true</code> if the data matches ok, of <code>false</code> if it is invalid
	 * @throws Exception
	 */
	public boolean validateLostPasswordHash(String email, String hash) throws Exception;
	
	/**
	 * Writes a new password for the user. 
	 * 
	 * @param password The new password
	 * @param email The user email
	 * @throws Exception
	 */
	public void saveNewPassword(String password, String email) throws Exception;
	
	/**
	 * Gets the username related to the email
	 * 
	 * @param email The email to search for the username
	 * @return The username, if found, or an empty <code>String</code>
	 * @throws Exception
	 */
	public String getUsernameByEmail(String email) throws Exception;
	
	/**
	 * Validate if the activated key matches the one in the database
	 * 
	 * @param userId Which user to validate the activation key?
	 * @param hash The activation key
	 * @return <code>true</code> if the data matches ok, of <code>false</code> if it is invalid
	 * @throws Exception
	 */
	public boolean validateActivationKeyHash(int userId , String hash) throws Exception;

	/**
	 * Set user account to active
	 * 
	 * @param userId Which user account to set active?
	 * @throws Exception
	 */	
	public void writeUserActive(int userId) throws Exception;
}
