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
 * This file creation date: 08/01/2004 / 22:11:13
 * net.jforum.security.RoleCollection.java
 */
package net.jforum.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * @author Rafael Steil
 */
public class RoleCollection extends LinkedHashSet 
{
	/* 
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(Object o) 
	{
		if (!(o instanceof Role)) {
			throw new IllegalArgumentException("Object passed as argument is not a Role type");
		}

		return super.add(o);
	}

	/* 
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection c) 
	{
		boolean status = true;
		for (Iterator iter = c.iterator(); iter.hasNext(); ) {
			status = this.add(iter.next());
		}
		
		return status;
	}
	
	/**
	 * Gets a role.
	 * 
	 * @param name The role's name
	 * @return <code>Role</code> object if a role with a name equals to the name passed
	 * as argument is found, or <code>null</code> otherwise.
	 */
	public Role get(String name)
	{
		for (Iterator iter = this.iterator(); iter.hasNext(); ) {
			Role role = (Role)iter.next();
			
			if (role.getName().equals(name)) {
				return role;
			}
		}
		
		return null;
	}

}
