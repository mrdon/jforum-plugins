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
import net.jforum.entities.AttachmentExtension;
import net.jforum.entities.AttachmentExtensionGroup;
import net.jforum.entities.QuotaLimit;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: AttachmentModel.java,v 1.3 2005/01/17 23:19:04 rafaelsteil Exp $
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
		this.removeQuotaLimit(new String[] { Integer.toString(id) });
	}
	
	/**
	 * @see net.jforum.model.AttachmentModel#removeQuotaLimit(java.lang.String[])
	 */
	public void removeQuotaLimit(String[] ids) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.removeQuotaLimit"));
		
		for (int i = 0; i < ids.length; i++) {
			p.setInt(1, Integer.parseInt(ids[i]));
			p.executeUpdate();
		}
		
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
	
	/**
	 * @see net.jforum.model.AttachmentModel#addExtensionGroup(net.jforum.entities.AttachmentExtensionGroup)
	 */
	public void addExtensionGroup(AttachmentExtensionGroup g) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.addExtensionGroup"));
		p.setString(1, g.getName());
		p.setInt(2, g.isAllow() ? 1 : 0);
		p.setString(3, g.getUploadIcon());
		p.setInt(4, g.getDownloadMode());
		p.executeUpdate();
		p.close();
	}
	
	/**
	 * @see net.jforum.model.AttachmentModel#removeExtensionGroups(java.lang.String[])
	 */
	public void removeExtensionGroups(String[] ids) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.removeExtensionGroups"));
		
		for (int i = 0; i < ids.length; i++) {
			p.setInt(1, Integer.parseInt(ids[i]));
			p.executeUpdate();
		}
		
		p.close();
	}
	
	/**
	 * @see net.jforum.model.AttachmentModel#selectExtensionGroups()
	 */
	public List selectExtensionGroups() throws Exception
	{
		List l = new ArrayList();
		
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.selectExtensionGroups"));
		
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			l.add(this.getExtensionGroup(rs));
		}
		
		rs.close();
		p.close();
		
		return l;
	}
	
	/**
	 * @see net.jforum.model.AttachmentModel#updateExtensionGroup(net.jforum.entities.AttachmentExtensionGroup)
	 */
	public void updateExtensionGroup(AttachmentExtensionGroup g) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.updateExtensionGroups"));
		p.setString(1, g.getName());
		p.setInt(2, g.isAllow() ? 1 : 0);
		p.setString(3, g.getUploadIcon());
		p.setInt(4, g.getDownloadMode());
		p.setInt(5, g.getId());
		p.executeUpdate();
		p.close();
	}
	
	protected AttachmentExtensionGroup getExtensionGroup(ResultSet rs) throws Exception
	{
		AttachmentExtensionGroup g = new AttachmentExtensionGroup();
		g.setId(rs.getInt("extension_group_id"));
		g.setName(rs.getString("name"));
		g.setUploadIcon(rs.getString("upload_icon"));
		g.setAllow(rs.getInt("allow") == 1);
		g.setDownloadMode(rs.getInt("download_mode"));
		
		return g;
	}
	
	/**
	 * @see net.jforum.model.AttachmentModel#addExtension(net.jforum.entities.AttachmentExtension)
	 */
	public void addExtension(AttachmentExtension e) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.addExtension"));
		p.setInt(1, e.getExtensionGroupId());
		p.setString(2, e.getComment());
		p.setString(3, e.getUploadIcon());
		p.setString(4, e.getExtension());
		p.setInt(5, e.isAllow() ? 1 : 0);
		p.executeUpdate();
		p.close();
	}
	
	/**
	 * @see net.jforum.model.AttachmentModel#removeExtensions(java.lang.String[])
	 */
	public void removeExtensions(String[] ids) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.removeExtension"));
		for (int i = 0; i < ids.length; i++) {
			p.setInt(1, Integer.parseInt(ids[i]));
			p.executeUpdate();
		}
		p.close();
	}
	
	/**
	 * @see net.jforum.model.AttachmentModel#selectExtensions()
	 */
	public List selectExtensions() throws Exception
	{
		List l = new ArrayList();
		
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.selectExtensions"));
		
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			l.add(this.getExtension(rs));
		}
		
		rs.close();
		p.close();
		
		return l;
	}
	
	/**
	 * @see net.jforum.model.AttachmentModel#updateExtension(net.jforum.entities.AttachmentExtension)
	 */
	public void updateExtension(AttachmentExtension e) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("AttachmentModel.updateExtension"));
		p.setInt(1, e.getExtensionGroupId());
		p.setString(2, e.getComment());
		p.setString(3, e.getUploadIcon());
		p.setString(4, e.getExtension());
		p.setInt(5, e.isAllow() ? 1 : 0);
		p.setInt(6, e.getId());
		p.executeUpdate();
		p.close();
	}
	
	protected AttachmentExtension getExtension(ResultSet rs) throws Exception
	{
		AttachmentExtension e = new AttachmentExtension();
		e.setAllow(rs.getInt("allow") == 1);
		e.setComment(rs.getString("comment"));
		e.setExtension(rs.getString("extension"));
		e.setExtensionGroupId(rs.getInt("extension_group_id"));
		e.setId(rs.getInt("extension_id"));
		e.setUploadIcon(rs.getString("upload_icon"));
		
		return e;
	}
}
