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
 * Created on 24/05/2004 / 12:04:11
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.hsqldb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.jforum.JForum;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: PostModel.java,v 1.1 2004/09/22 13:51:18 marcwick Exp $
 */
public class PostModel extends net.jforum.drivers.postgresql.PostModel {

	/**
	 * @see net.jforum.model.PostModel#selectAllByTopicByLimit(int, int, int)
	 */
	public ArrayList selectAllByTopicByLimit(int topicId, int startFrom,
			int count) throws Exception {
		ArrayList l = new ArrayList();

		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("PostModel.selectAllByTopicByLimit"));
		p.setInt(1, startFrom);
		p.setInt(2, count);
		p.setInt(3, topicId);

		ResultSet rs = p.executeQuery();

		while (rs.next()) {
			l.add(super.makePost(rs));
		}

		rs.close();
		p.close();

		return l;
	}
}