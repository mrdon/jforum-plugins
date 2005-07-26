/*
 * Copyright (c) 2003, 2004 Rafael Steil
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
 * Created on Dec 26, 2004 19:53
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.security;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author Rafael Steil
 * @version $Id: MergeTest.java,v 1.2 2005/07/26 02:46:09 diegopires Exp $
 */
public class MergeTest extends TestCase {
	private RoleCollection userRoles;

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		this.userRoles = new RoleCollection();
	}

	public void testForumMergeWithGroup1() {
		List groupRoles = new ArrayList();
		groupRoles.add(this.createGroup1ForumRoles());

		UserSecurityHelper.mergeUserGroupRoles(this.userRoles, groupRoles);
		PermissionControl pc = new PermissionControl();
		pc.setRoles(this.userRoles);

		this.assertAllForumsTrue(pc);

		assertFalse(pc.canAccess(SecurityConstants.PERM_FORUM, "y"));
	}

	public void testForumMergeWithGroup2() {
		List groupRoles = new ArrayList();
		groupRoles.add(this.createGroup2ForumRoles());

		UserSecurityHelper.mergeUserGroupRoles(this.userRoles, groupRoles);
		PermissionControl pc = new PermissionControl();
		pc.setRoles(this.userRoles);

		assertFalse(pc.canAccess(SecurityConstants.PERM_FORUM, "a"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "b"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "c"));
		assertFalse(pc.canAccess(SecurityConstants.PERM_FORUM, "d"));
		assertFalse(pc.canAccess(SecurityConstants.PERM_FORUM, "e"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "f"));
	}

	/*
	 * Merge the roles of group 2 and 3 and check if it worked correctly
	 */
	public void testForumMergeMixed() {
		List groupRoles = new ArrayList();
		groupRoles.add(this.createGroup2ForumRoles());
		groupRoles.add(this.createGroup3ForumRoles());

		UserSecurityHelper.mergeUserGroupRoles(this.userRoles, groupRoles);

		PermissionControl pc = new PermissionControl();
		pc.setRoles(this.userRoles);

		this.assertAllForumsTrue(pc);
	}

	public void testForumMergeUserGroup1() {
		this.userRoles = this.createUserRestrictiveForumRoles();
		List groupRoles = new ArrayList();
		groupRoles.add(this.createGroup1ForumRoles());

		UserSecurityHelper.mergeUserGroupRoles(this.userRoles, groupRoles);
		PermissionControl pc = new PermissionControl();
		pc.setRoles(this.userRoles);

		this.assertUserForums(pc);
	}

	private void assertUserForums(PermissionControl pc) {
		assertFalse(pc.canAccess(SecurityConstants.PERM_FORUM, "a"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "b"));
		assertFalse(pc.canAccess(SecurityConstants.PERM_FORUM, "c"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "d"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "e"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "f"));
	}

	private void assertAllForumsTrue(PermissionControl pc) {
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "a"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "b"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "c"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "d"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "e"));
		assertTrue(pc.canAccess(SecurityConstants.PERM_FORUM, "f"));
	}

	private RoleCollection createUserRestrictiveForumRoles() {
		RoleCollection rc = new RoleCollection();

		int deny = PermissionControl.ROLE_DENY;
		rc.add(this.createRole(SecurityConstants.PERM_FORUM, 5, new String[] {
				"a", "c" }, new int[] { deny, deny }));

		return rc;
	}

	private RoleCollection createGroup1ForumRoles() {
		RoleCollection rc = new RoleCollection();

		int allow = PermissionControl.ROLE_ALLOW;
		rc.add(this.createRole(SecurityConstants.PERM_FORUM, 1, new String[] {
				"a", "b", "c", "d", "e", "f" }, new int[] { allow, allow,
				allow, allow, allow, allow }));

		return rc;
	}

	private RoleCollection createGroup2ForumRoles() {
		RoleCollection rc = new RoleCollection();

		int allow = PermissionControl.ROLE_ALLOW;
		rc.add(this.createRole(SecurityConstants.PERM_FORUM, 2, new String[] {
				"b", "c", "f" }, new int[] { allow, allow, allow }));

		return rc;
	}

	private RoleCollection createGroup3ForumRoles() {
		RoleCollection rc = new RoleCollection();

		int allow = PermissionControl.ROLE_ALLOW;
		rc.add(this.createRole(SecurityConstants.PERM_FORUM, 3, new String[] {
				"a", "d", "e" }, new int[] { allow, allow, allow }));

		return rc;
	}

	private Role createRole(String roleName, int id, String[] roleValues,
			int[] access) {
		Role role = new Role();
		role.setName(roleName);
		role.setId(id);

		if (roleValues != null) {
			for (int i = 0; i < roleValues.length; i++) {
				RoleValue rv = new RoleValue();
				rv.setRoleId(id);
				rv.setType(access[i]);
				rv.setValue(roleValues[i]);

				role.getValues().add(rv);
			}
		} else {
			role.setType(access[0]);
		}

		return role;
	}
}
