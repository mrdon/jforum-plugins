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
 * Created on 30/05/2004 15:10:57
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.generic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.jforum.entities.UserSession;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: UserSessionModel.java,v 1.2 2004/06/01 19:47:17 pieter2 Exp $
 */
public class UserSessionModel implements net.jforum.model.UserSessionModel
{
	/** 
	 * @see net.jforum.model.UserSessionModel#add(net.jforum.entities.UserSession, java.sql.Connection)
	 */
	public void add(UserSession us, Connection conn) throws Exception
	{
		this.add(us, conn, false);
	}
	
	private void add(UserSession us, Connection conn, boolean checked) throws Exception
	{
		if (!checked && this.selectById(us, conn) != null) {
			return;
		}
		
		PreparedStatement p = conn.prepareStatement(SystemGlobals.getSql("UserSessionModel.add"));
		p.setString(1, us.getSessionId());
		p.setInt(2, us.getUserId());
		p.setString(3, Long.toString(us.getStartTime()));
		
		p.executeUpdate();
		p.close();
	}

	/** 
	 * @see net.jforum.model.UserSessionModel#update(net.jforum.entities.UserSession, java.sql.Connection)
	 */
	public void update(UserSession us, Connection conn) throws Exception
	{
		if (this.selectById(us, conn) == null) {
			this.add(us, conn, true);
			return;
		}
		
		PreparedStatement p = conn.prepareStatement(SystemGlobals.getSql("UserSessionModel.update"));
		p.setString(1, Long.toString(us.getStartTime()));
		p.setString(2, Long.toString(us.getSessionTime()));
		p.setString(3, us.getSessionId());
		p.setInt(4, us.getUserId());
		
		p.executeUpdate();
		p.close();
	}

	/** 
	 * @see net.jforum.model.UserSessionModel#selectById(net.jforum.entities.UserSession, java.sql.Connection)
	 */
	public UserSession selectById(UserSession us, Connection conn) throws Exception
	{
		PreparedStatement p = conn.prepareStatement(SystemGlobals.getSql("UserSessionModel.selectById"));
		p.setInt(1, us.getUserId());
		
		ResultSet rs = p.executeQuery();
		boolean found = false;
		
		UserSession returnUs = new UserSession(us);
		
		if (rs.next()) {
			returnUs.setSessionTime(rs.getLong("session_time"));
			returnUs.setStartTime(rs.getLong("session_start"));
			found = true;
		}
		
		rs.close();
		p.close();
		
		return (found ? returnUs : null);
	}

}
