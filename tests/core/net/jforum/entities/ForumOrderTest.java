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
 * Created on 17/11/2004 22:03:01
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.entities;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * @author Rafael Steil
 * @version $Id: ForumOrderTest.java,v 1.1 2004/11/18 01:31:46 rafaelsteil Exp $
 */
public class ForumOrderTest extends TestCase 
{
	private Category category;

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception 
	{
		this.category = new Category();
		
		Forum f = new Forum();
		f.setName("Forum 2");
		f.setOrder(2);
		this.category.addForum(f);
		
		f = new Forum();
		f.setName("Forum 5");
		f.setOrder(5);
		this.category.addForum(f);
		
		f = new Forum();
		f.setName("Forum 1");
		f.setOrder(1);
		this.category.addForum(f);
		
		f = new Forum();
		f.setName("Forum 3");
		f.setOrder(3);
		this.category.addForum(f);
		
		f = new Forum();
		f.setName("Forum 4");
		f.setOrder(4);
		this.category.addForum(f);
	}
	
	public void testForumOrder()
	{
		String[] expectedNames = { "Forum 1", "Forum 2", "Forum 3", "Forum 4", "Forum 5" };
		int i = 0; 
		for (Iterator iter = this.category.getForums().iterator(); iter.hasNext(); ) {
			Forum f = (Forum)iter.next();
			assertEquals(expectedNames[i++], f.getName());
		}
	}

}
