/*
 * Copyright (c) 2003, Rafael Steil
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
 * This file creating date: Feb 23, 2003 / 2:43:40 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.model;

import java.util.List;

import net.jforum.entities.Forum;
import net.jforum.entities.LastPostInfo;

/**
* Model interface for {@link net.jforum.Forum}.
 * This interface defines methods which are expected to be
 * implementd by a specific data access driver. The intention is
 * to provide all functionality needed to update, insert, delete and
 * select some specific data.
 * 
 * @author Rafael Steil
 * @version $Id: ForumModel.java,v 1.4 2004/11/12 20:46:39 rafaelsteil Exp $
 */
public interface ForumModel 
{
	/**
	 * Gets a specific <code>Forum</code>.
	 * 
	 * @param forumId The ForumID to search
	 * @return <code>Forum</code>object containing all the information
	 * @throws Exception
	 * @see #selectAll
	 */
	public Forum selectById(int forumId) throws Exception;
	
	/**
	 * Selects all forums data from the database.
	 * 
	 * @return ArrayList with the forums found 
	 * @throws Exception
	 * @see #selectById
	 */
	public List selectAll() throws Exception;
	
	/**
	 * Sets the forum's order one level up.
	 * When you call this method on a specific forum, the forum that 
	 * is one level up will be sent down one level, and the forum which
	 * you are sending up wil take the order position of the forum which
	 * was sent down.
	 * 
	 * @param forumId
	 * @throws Exception
	 */
	public void setOrderUp(int forumId) throws Exception;
	
	/**
	 * Sets the forum's order one level down.
	 * For more information, take a look at {@link #setOrderUp} method. 
	 * The only different between both is that this method sends the 
	 * forum order down.
	 * 
	 * @param forumId
	 * @throws Exception
	 */
	public void setOrderDown(int forumId) throws Exception;
	
	/**
	 * Delete a forum.
	 * 
	 * @param forumId The forum ID to delete
	 * @throws Exception
	 * @see #canDelete(int)
	 */
	public void delete(int forumId) throws Exception;
		
	/**
	 * Updates a Forum.
	 * 
	 * @param forum Reference to a <code>Forum</code> object to update
	 * @throws Exception
	 * @see #update(int)
	 */
	public void update(Forum forum) throws Exception;
	
	/**
	 * Adds a new Forum.
	 * 
	 * @param forum Reference to a valid and configured <code>Forum</code> object
	 * @return The forum's ID
	 * @throws Exception
	 */
	public int addNew(Forum forum) throws Exception;
	
	/**
	 * Sets the last topic of a forum
	 * 
	 * @param forumId The forum ID to update
	 * @param postId Last post ID
	 * @throws Exception
	 */
	public void setLastPost(int forumId, int postId) throws Exception;

	/**
	 * Increments the total number of topics of a forum
	 * 
	 * @param forumId The forum ID to update
	 * @param count Increment a total of <code>count</code> elements
	 * @throws Exception
	 */
	public void incrementTotalTopics(int forumId, int count) throws Exception;
	
	/**
	 * Decrements the total number of topics of a forum
	 * 
	 * @param forumId The forum ID to update
	 * @param count Decrement a total of <code>count</code> elements 
	 * @throws Exception
	 */
	public void decrementTotalTopics(int forumId, int count) throws Exception;

	public LastPostInfo getLastPostInfo(int forumId) throws Exception;
	
	/**
	 * Gets the total number of messages of a forum
	 * 
	 * @param forumId The forum ID
	 * @throws Exception
	 */
	public int getTotalMessages() throws Exception; 
	
	/**
	 * Gets the total number os topics of some forum
	 * 
	 * @return Total of topics
	 * @throws Exception
	 */
	public int getTotalTopics(int forumId) throws Exception;

	
	/**
	 * Gets the last post id associated to the forum
	 * 
	 * @param forumId The forum id 
	 * @throws Exception
	 */
	public int getMaxPostId(int forumId) throws Exception;
	
	/**
	 * Move the topics to a new forum
	 * 
	 * @param topics The topics id array
	 * @param fromForumId The original forum id
	 * @param toForumId The destination forum id
	 * @throws Exception
	 */
	public void moveTopics(String[] topics, int fromForumId, int toForumId) throws Exception;
}
