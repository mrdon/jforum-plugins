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
 * This file creating date: Feb 17, 2003 / 10:47:29 PM
 * net.jforum.entities.Categories.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: Category.java,v 1.2 2004/04/21 23:57:31 rafaelsteil Exp $
 */
package net.jforum.entities;

/**
 * Represents a category in the System.
 * 
 * @author Rafael Steil
 */
public class Category 
{
	private int id;
	private int order;
	private String name;
		
	/**
	 * 
	 * @see java.lang.Object#Object()
	 */
	public Category() {}
	
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
	 

	/* 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return this.id;
	}

	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) 
	{
		return ((obj instanceof Category) && (((Category)obj).getId() == this.id));
	}

}
