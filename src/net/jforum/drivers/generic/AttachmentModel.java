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
 * Created on Jan 17, 2005 4:36:30 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.generic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.jforum.JForum;
import net.jforum.entities.QuotaLimit;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: AttachmentModel.java,v 1.1 2005/01/17 18:59:14 rafaelsteil Exp $
 */
public class AttachmentModel implements net.jforum.model.AttachmentModel
{
	/**
	 * @see net.jforum.model.AttachmentModel#addQuotaLimit(net.jforum.entities.QuotaLimit)
	 */
	public void addQuotaLimit(QuotaLimit limit) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.addQuotaLimit"));
		p.setString(1, limit.getDescription());
		p.setInt(2, limit.getSize());
		p.setInt(3, limit.getType());
		p.executeUpdate();
		p.close();
	}

	/**
	 * @see net.jforum.model.AttachmentModel#updateQuotaLimit(net.jforum.entities.QuotaLimit)
	 */
	public void updateQuotaLimit(QuotaLimit limit) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.updateQuotaLimit"));
		p.setString(1, limit.getDescription());
		p.setInt(2, limit.getSize());
		p.setInt(3, limit.getType());
		p.setInt(4, limit.getId());
		p.executeUpdate();
		p.close();
	}

	/**
	 * @see net.jforum.model.AttachmentModel#removeQuotaLimit(int)
	 */
	public void removeQuotaLimit(int id) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.removeQuotaLimit"));
		p.setInt(1, id);
		p.executeUpdate();
		p.close();
	}
	
	/**
	 * @see net.jforum.model.AttachmentModel#selectQuotaLimit()
	 */
	public List selectQuotaLimit() throws Exception
	{
		List l = new ArrayList();
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.selectQuotaLimit"));
		
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			l.add(this.getQuotaLimit(rs));
		}
		
		rs.close();
		p.close();
		
		return l;
	}
	
	protected QuotaLimit getQuotaLimit(ResultSet rs) throws Exception
	{
		QuotaLimit ql = new QuotaLimit();
		ql.setDescription(rs.getString("quota_desc"));
		ql.setId(rs.getInt("quota_limit_id"));
		ql.setSize(rs.getInt("quota_limit"));
		ql.setType(rs.getInt("quota_type"));
		
		return ql;
	}
}
