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
 * This file creation date: 19/03/2004 - 18:56:49
 * net.jforum.security.UserSecurityHelper.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.security;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Rafael Steil
 */
public class UserSecurityHelper 
{
	public static void mergeUserGroupRoles(RoleCollection userRoles, ArrayList groupsRolesList)
	{
		for (Iterator iter = groupsRolesList.iterator(); iter.hasNext(); ) {
			RoleCollection rc = (RoleCollection)iter.next();
			
			for (Iterator rcIter = rc.iterator(); rcIter.hasNext(); ) {
				Role role = (Role)rcIter.next();
				Role userRole = userRoles.get(role.getName());
				
				if (userRole == null) {
					userRoles.add(role);
				}
				else {
					// Merge them
					for (Iterator vIter = role.getValues().iterator(); vIter.hasNext(); ) {
						RoleValue gRv = (RoleValue)vIter.next();
						RoleValue uRv = userRole.getValues().get(gRv.getValue()); 
						
						if (uRv == null) {
							userRole.getValues().add(gRv);
						} 
					}
				}
			}
		}
	}
}
