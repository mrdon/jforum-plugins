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
 * This file creating date: Feb 17, 2003 / 10:47:29 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.entities;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a category in the System.
 * 
 * @author Rafael Steil
 * @version $Id: Category.java,v 1.4 2004/11/13 03:14:01 rafaelsteil Exp $
 */
public class Category 
{
	private int id;
	private int order;
	private String name;
	private Map forums = new LinkedHashMap();
		
	public Category() {}
	
	public Category(Category c) {
		this.name = c.getName();
		this.id = c.getId();
		this.order = c.getOrder();
		
		for (Iterator iter = c.getForums(); iter.hasNext(); ) {
			this.addForum(new Forum((Forum)iter.next()));
		}
	}
	
	/**
	 * @return int
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return int
	 */
	public int getOrder() {
		return this.order;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the order.
	 * @param order The order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	
	/**
	 * Adds a forum to this category
	 * 
	 * @param forum
	 */
	public void addForum(Forum forum) {
		this.forums.put(new Integer(forum.getId()), forum);
	}
	
	/**
	 * Removes a forum from the list.
	 * @param forumId
	 */
	public void removeForum(int forumId) {
		this.forums.remove(new Integer(forumId));
	}
	
	/**
	 * Gets a forum.
	 * 
	 * @param forumId The id of the forum to retrieve
	 * @return The <code>Forum</code> instance if found, or <code>null</code>
	 * otherwhise.
	 */
	public Forum getForum(int forumId)
	{
		return (Forum)this.forums.get(new Integer(forumId));
	}
	
	/**
	 * Gets all forums from this category
	 * @return 
	 */
	public Iterator getForums() {
		return this.forums.values().iterator();
	}

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return this.id;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) 
	{
		return ((o instanceof Category) && (((Category)o).getId() == this.id));
	}

}
