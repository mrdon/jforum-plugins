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
 * Created on Aug 2, 2004 by pieter
 *
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.external;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.jforum.util.preferences.SystemGlobals;

public class JdbcLoginServer implements LoginServer {
	private static final String CONF_CONNECTION = "loginServer.database.connection.string";
	
	private static final String CONF_QUERY_VALIDATE_LOGIN = "loginServer.database.query.validateLogin";
	private static final String CONF_QUERY_SELECT_NAME = "loginServer.database.query.selectName";
	private static final String CONF_QUERY_SELECT_GROUPS = "loginServer.database.query.selectGroups";

	// Because we do not expect hundreds of logins each second, we only need
	// a single connection instead of a full-fledged connection pool
	private Connection connection;
	
	private PreparedStatement validateLoginStatement;
	private PreparedStatement selectNameStatement;
	private PreparedStatement selectGroupsStatement;
	
	public JdbcLoginServer() throws SQLException {
		connection = DriverManager.getConnection(SystemGlobals.getValue(CONF_CONNECTION));
		
		validateLoginStatement = connection.prepareStatement(SystemGlobals.getValue(CONF_QUERY_VALIDATE_LOGIN));
		selectNameStatement = connection.prepareStatement(SystemGlobals.getValue(CONF_QUERY_SELECT_NAME));
		selectGroupsStatement = connection.prepareStatement(SystemGlobals.getValue(CONF_QUERY_SELECT_GROUPS));
	}
	
	synchronized public int validateLogin(String name, String password) throws SQLException {
		validateLoginStatement.setString(1, name);
		validateLoginStatement.setString(2, password);
		
		ResultSet rs = validateLoginStatement.executeQuery();
		
		int uid = -1;
		if (rs.next()) {
			uid = rs.getInt(1);
		}
		
		rs.close();
		
		return uid;
	}

	public String getName(int userId) throws SQLException {
		selectNameStatement.setInt(1, userId);
		
		ResultSet rs = selectNameStatement.executeQuery();
		
		String name = null;
		if (rs.next()) {
			name = rs.getString(1);
		}
		
		rs.close();
		
		return name;
	}

	public int[] getGroups(int userId) throws SQLException {
		selectGroupsStatement.setInt(1, userId);
		
		ResultSet rs = selectGroupsStatement.executeQuery();
		
		List list = new ArrayList();
		while (rs.next()) {
			list.add(new Integer(rs.getInt(1)));
		}
		
		rs.close();
		
		int count = list.size();
		int[] groups = new int[count];
		for (int i=0; i<count; i++) {
			groups[i] = ((Integer) list.get(i)).intValue();
		}
		return groups;
	}

}
