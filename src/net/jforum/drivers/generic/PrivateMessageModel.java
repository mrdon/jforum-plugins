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
 * This file creation date: 20/05/2004 - 15:51:10
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.generic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.jforum.JForum;
import net.jforum.entities.Post;
import net.jforum.entities.PrivateMessage;
import net.jforum.entities.PrivateMessageType;
import net.jforum.entities.User;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: PrivateMessageModel.java,v 1.6 2004/10/19 14:32:13 marcwick Exp $
 */
public class PrivateMessageModel extends AutoKeys implements net.jforum.model.PrivateMessageModel
{
	/** 
	 * @see net.jforum.model.PrivateMessageModel#send(net.jforum.entities.PrivateMessage)
	 */
	public void send(PrivateMessage pm) throws Exception
	{
		// We should store 2 copies: one for the sendee's sent box
		// and another for the target user's inbox.
		
		PreparedStatement text = JForum.getConnection().prepareStatement(SystemGlobals.getSql("PrivateMessagesModel.addText"));

		// Sendee's sent box
		PreparedStatement p = this.getStatementForAutoKeys("PrivateMessageModel.add");
		p.setInt(1, PrivateMessageType.SENT);
		p.setString(2, pm.getPost().getSubject());
		p.setInt(3, pm.getFromUser().getId());
		p.setInt(4, pm.getToUser().getId());
		p.setTimestamp(5, new Timestamp(pm.getPost().getTime().getTime()));
		p.setString(6, pm.getPost().isBbCodeEnabled() ? "1" : "0");
		p.setString(7, pm.getPost().isHtmlEnabled() ? "1" : "0");
		p.setString(8, pm.getPost().isSmiliesEnabled() ? "1" : "0");
		p.setString(9, pm.getPost().isSignatureEnabled() ? "1" : "0");
		
		text.setInt(1, this.executeAutoKeysQuery(p));
		text.setString(2, pm.getPost().getText());
		text.executeUpdate();
		
		// Target user's inbox
		p.setInt(1, PrivateMessageType.NEW);
		
		text.setInt(1, this.executeAutoKeysQuery(p));
		text.executeUpdate();
		
		text.close();
		p.close();
	}
	
	/** 
	 * @see net.jforum.model.PrivateMessageModel#delete(net.jforum.entities.PrivateMessage[])
	 */
	public void delete(PrivateMessage[] pm) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("PrivateMessageModel.delete"));
		p.setInt(2, pm[0].getFromUser().getId());
		p.setInt(3, pm[0].getFromUser().getId());
		
		PreparedStatement delete = JForum.getConnection().prepareStatement(SystemGlobals.getSql("PrivateMessagesModel.deleteText"));
		
		for (int i = 0; i < pm.length; i++) {
			p.setInt(1, pm[i].getId());
			p.executeUpdate();
			
			if (p.getUpdateCount() > 0) {
				delete.setInt(1, pm[i].getId());
				delete.executeUpdate();
			}
		}
		
		delete.close();
		p.close();
	}

	/**
	 * @see net.jforum.model.PrivateMessageModel#selectFromInbox(net.jforum.entities.User)
	 */
	public List selectFromInbox(User user) throws Exception
	{
		String query = SystemGlobals.getSql("PrivateMessageModel.baseListing");
		query = query.replaceAll("#FILTER#", SystemGlobals.getSql("PrivateMessageModel.inbox"));
		
		PreparedStatement p = JForum.getConnection().prepareStatement(query);
		p.setInt(1, user.getId());
		
		List pmList = new ArrayList();

		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			PrivateMessage pm = this.getPm(rs, false);
			
			User fromUser = new User();
			fromUser.setId(rs.getInt("user_id"));
			fromUser.setUsername(rs.getString("username"));
			
			pm.setFromUser(fromUser);
			
			pmList.add(pm);
		}
		
		rs.close();
		p.close();
		
		return pmList;
	}

	/** 
	 * @see net.jforum.model.PrivateMessageModel#selectFromSent(net.jforum.entities.User)
	 */
	public List selectFromSent(User user) throws Exception
	{
		String query = SystemGlobals.getSql("PrivateMessageModel.baseListing");
		query = query.replaceAll("#FILTER#", SystemGlobals.getSql("PrivateMessageModel.sent"));
		
		PreparedStatement p = JForum.getConnection().prepareStatement(query);
		p.setInt(1, user.getId());
		
		List pmList = new ArrayList();

		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			PrivateMessage pm = this.getPm(rs, false);
			
			User toUser = new User();
			toUser.setId(rs.getInt("user_id"));
			toUser.setUsername(rs.getString("username"));
			
			pm.setToUser(toUser);
			
			pmList.add(pm);
		}
		
		rs.close();
		p.close();
		
		return pmList;
	}
	
	private PrivateMessage getPm(ResultSet rs) throws Exception
	{
		return this.getPm(rs, true);
	}
	
	private PrivateMessage getPm(ResultSet rs, boolean full) throws Exception
	{
		PrivateMessage pm = new PrivateMessage();
		Post p = new Post();

		pm.setId(rs.getInt("privmsgs_id"));
		pm.setType(rs.getInt("privmsgs_type"));
		p.setTime(rs.getTimestamp("privmsgs_date"));
		p.setSubject(rs.getString("privmsgs_subject"));
		
		SimpleDateFormat df = new SimpleDateFormat(SystemGlobals.getValue(ConfigKeys.DATE_TIME_FORMAT));
		pm.setFormatedDate(df.format(p.getTime()));
		
		if (full) {
			UserModel um = new UserModel();
			pm.setFromUser(um.selectById(rs.getInt("privmsgs_from_userid")));
			pm.setToUser(um.selectById(rs.getInt("privmsgs_to_userid")));
			
			p.setBbCodeEnabled(rs.getInt("privmsgs_enable_bbcode") == 1);
			p.setSignatureEnabled(rs.getInt("privmsgs_attach_sig") == 1);
			p.setHtmlEnabled(rs.getInt("privmsgs_enable_html") == 1);
			p.setSmiliesEnabled(rs.getInt("privmsgs_enable_smilies") == 1);
			p.setText(rs.getString("privmsgs_text"));
		}
		
		pm.setPost(p);
		
		return pm;
	}

	/** 
	 * @see net.jforum.model.PrivateMessageModel#selectById(net.jforum.entities.PrivateMessage)
	 */
	public PrivateMessage selectById(PrivateMessage pm) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("PrivateMessageModel.selectById"));
		p.setInt(1, pm.getId());
		
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			pm = this.getPm(rs);
		}
		
		rs.close();
		p.close();
		
		return pm;
	}

	/** 
	 * @see net.jforum.model.PrivateMessageModel#updateType(net.jforum.entities.PrivateMessage)
	 */
	public void updateType(PrivateMessage pm) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("PrivateMessageModel.updateType"));
		p.setInt(1,pm.getType());
		p.setInt(2, pm.getId());
		p.executeUpdate();
		p.close();
	}
}
