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
 * This file creating date: Feb 23, 2003 / 12:40:13 PM
 * net.jforum.entities.Topic.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: Topic.java,v 1.2 2004/04/21 23:57:31 rafaelsteil Exp $
 */
package net.jforum.entities;

/**
 * Represents every topic in the forum.
 * 
 * @author Rafael Steil
 */
public class Topic 
{
	public static int TYPE_NORMAL = 0;
	public static int TYPE_STICKY = 1;
	public static int TYPE_ANNOUNCE = 2;

	public static int STATUS_UNLOCKED = 0;
	public static int STATUS_LOCKED = 1;
	
	/**
	 * The topic ID
	 */
	private int id;
	
	/**
	 * The id of the forum which the topic is associated
	 */
	private int forumId;
	
	private boolean read = true;
	
	/**
	 * The topic title
	 */
	private String title;
	
	/**
	 * The time which the topic was posted
	 */
	private long time;
	
	private long lastPostTimeInMillis;
	
	/**
	 * Total vews of the topcis
	 */
	private int totalViews;
	
	/**
	 * Total number of answers
	 */
	private int totalReplies;
	
	/**
	 * 
	 */
	private int status;
	
	/**
	 * 
	 */
	private boolean vote;
	
	/**
	 * 
	 */
	private int type;
	
	/**
	 * The id of the first post
	 */
	private int firstPostId;
	
	private String firstPostTime;
	
	/**
	 * The id of the last post
	 */
	private int lastPostId;	
	
	private String lastPostTime;
	
	/**
	 * Indicate if the is a moderated topic or not
	 */
	private boolean moderated;
	
	private boolean paginate;
	
	private Double totalPages;

	/**
	 * User who created the topic
	 */
	private User postedBy;
	
	/**
	 * User who posted the last message in the topic 
	 */
	private User lastPostBy;
	
	/**
	 * Default Constructor
	 */
	public Topic() {}
	
		
	/**
	 * Returns the ID of the firts topic
	 * 
	 * @return int value with the ID
	 */
	public int getFirstPostId() {
		return this.firstPostId;
	}

	/**
	 * Returns the ID of the topic
	 * 
	 * @return int value with the ID
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Returns the ID of the forum this topic belongs to
	 * 
	 * @return int value with the ID
	 */
	public int getForumId() {
		return this.forumId;
	}

	/**
	 * Teturns the ID of the last post in the topic
	 * 
	 * @return int value with the ID
	 */
	public int getLastPostId() {
		return this.lastPostId;
	}

	/**
	 * Returns the status 
	 * 
	 * @return int value with the status
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * Returns the time the topic was posted
	 * 
	 * @return int value representing the time
	 */
	public long getTime() {
		return this.time;
	}
	
	public void setFirstPostTime(String d) {
		this.firstPostTime = d;
	}
	
	public void setLastPostTime(String d) {
		this.lastPostTime = d;
	}

	/**
	 * Returns the title of the topci
	 * 
	 * @return String with the topic title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns the total number of replies
	 * 
	 * @return int value with the total
	 */
	public int getTotalReplies() {
		return this.totalReplies;
	}

	/**
	 * Returns the total number of views
	 * 
	 * @return int value with the total number of views
	 */
	public int getTotalViews() {
		return this.totalViews;
	}
	
	public User getPostedBy() {
		return this.postedBy;
	}
	
	public User getLastPostBy() {
		return this.lastPostBy;
	}

	/**
	 * Returns the type
	 * 
	 * @return int value representing the type
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Is a votation topic?
	 * 
	 * @return boolean value
	 */
	public boolean isVote() {
		return this.vote;
	}

	/**
	 * Sets the id of the firts post in the topic
	 * 
	 * @param firstPostId The post id 
	 */
	public void setFirstPostId(int firstPostId) {
		this.firstPostId = firstPostId;
	}

	/**
	 * Sets the id to the topic
	 * 
	 * @param id The id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the id of the forum associeted with this topic
	 * 
	 * @param idForum The id of the forum to set
	 */
	public void setForumId(int idForum) {
		this.forumId = idForum;
	}

	/**
	 * Sets the id of the last post in the topic
	 * 
	 * @param lastPostId The post id
	 */
	public void setLastPostId(int lastPostId) {
		this.lastPostId = lastPostId;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status The status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Sets the time.
	 * 
	 * @param time The time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title The title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the totalReplies.
	 * 
	 * @param totalReplies The totalReplies to set
	 */
	public void setTotalReplies(int totalReplies) {
		this.totalReplies = totalReplies;
	}

	/**
	 * Sets the totalViews.
	 * 
	 * @param totalViews The totalViews to set
	 */
	public void setTotalViews(int totalViews) {
		this.totalViews = totalViews;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type The type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Sets the vote.
	 * 
	 * @param vote The vote to set
	 */
	public void setVote(boolean vote) {
		this.vote = vote;
	}
	/**
	 * @return
	 */
	public boolean isModerated() {
		return this.moderated;
	}

	/**
	 * @param b
	 */
	public void setModerated(boolean b) {
		this.moderated = b;
	}
	
	public void setPostedBy(User u) {
		this.postedBy = u;
	}
	
	public void setLastPostBy(User u) {
		this.lastPostBy = u;
	}
	
	public String getFirstPostTime() {
		return this.firstPostTime;
	}
	
	public String getLastPostTime() {
		return this.lastPostTime;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	
	public boolean getRead() {
		return this.read;
	}
	
	public void setLastPostTimeInMillis(long t) {
		this.lastPostTimeInMillis = t;
	}
	
	public long getLastPostTimeInMillis() {
		return this.lastPostTimeInMillis;
	}
	
	public void setPaginate(boolean paginate) {
		this.paginate = paginate;
	}
	
	public boolean getPaginate() {
		return this.paginate;
	}
	
	public void setTotalPages(Double total) {
		this.totalPages = total;
	}
	
	public Double getTotalPages() {
		return this.totalPages;
	}
}
