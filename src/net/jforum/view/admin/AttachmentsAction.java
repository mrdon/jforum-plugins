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
 * Created on Jan 17, 2005 2:46:30 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.admin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.jforum.ActionServletRequest;
import net.jforum.Command;
import net.jforum.entities.AttachmentExtension;
import net.jforum.entities.AttachmentExtensionGroup;
import net.jforum.entities.QuotaLimit;
import net.jforum.model.AttachmentModel;
import net.jforum.model.DataAccessDriver;
import net.jforum.util.TreeGroup;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: AttachmentsAction.java,v 1.6 2005/03/12 19:04:18 rafaelsteil Exp $
 */
public class AttachmentsAction extends Command
{
	public void configurations() throws Exception
	{
		this.context.put("icon", SystemGlobals.getValue(ConfigKeys.ATTACHMENTS_ICON));
		this.context.put("createThumb", SystemGlobals.getBoolValue(ConfigKeys.ATTACHMENTS_IMAGES_CREATE_THUMB));
		this.context.put("thumbH", SystemGlobals.getValue(ConfigKeys.ATTACHMENTS_IMAGES_MIN_THUMB_H));
		this.context.put("thumbW", SystemGlobals.getValue(ConfigKeys.ATTACHMENTS_IMAGES_MIN_THUMB_W));
		this.context.put("maxPost", SystemGlobals.getValue(ConfigKeys.ATTACHMENTS_MAX_POST));

		this.context.put("moduleAction", "attachments_config.htm");
	}
	
	public void configurationsSave() throws Exception
	{
		ConfigAction ca = new ConfigAction(this.request, this.response, this.conn, this.context);
		ca.updateData(ca.getConfig());
		
		this.configurations();
	}
	
	public void quotaLimit() throws Exception
	{
		AttachmentModel am = DataAccessDriver.getInstance().newAttachmentModel();
		
		this.context.put("quotas", am.selectQuotaLimit());
		this.context.put("moduleAction", "quota_limit.htm");
		this.context.put("groups", new TreeGroup().getNodes());
		this.context.put("selectedList", new ArrayList());
		this.context.put("groupQuotas", am.selectGroupsQuotaLimits());
	}
	
	public void quotaLimitSave() throws Exception
	{
		QuotaLimit ql = new QuotaLimit();
		ql.setDescription(this.request.getParameter("quota_description"));
		ql.setSize(this.request.getIntParameter("max_filesize"));
		ql.setType(this.request.getIntParameter("type"));
		
		DataAccessDriver.getInstance().newAttachmentModel().addQuotaLimit(ql);
		this.quotaLimit();
	}
	
	public void quotaLimitUpdate() throws Exception
	{
		AttachmentModel am = DataAccessDriver.getInstance().newAttachmentModel();
		
		// First check if we should delete some entry
		String[] delete = this.request.getParameterValues("delete");
		List deleteList = new ArrayList();
		if (delete != null) {
			deleteList = Arrays.asList(delete);
			am.removeQuotaLimit(delete);
		}
		
		// Now update the remaining
		int total = this.request.getIntParameter("total_records");
		for (int i = 0; i < total; i++) {
			if (deleteList.contains(this.request.getParameter("id_" + i))) {
				continue;
			}
			
			QuotaLimit ql = new QuotaLimit();
			ql.setId(this.request.getIntParameter("id_" + i));
			ql.setDescription(this.request.getParameter("quota_desc_" + i));
			ql.setSize(this.request.getIntParameter("max_filesize_" + i));
			ql.setType(this.request.getIntParameter("type_" + i));
			
			am.updateQuotaLimit(ql);
		}
		
		this.quotaLimit();
	}
	
	public void extensionGroups() throws Exception
	{
		this.context.put("moduleAction", "extension_groups.htm");
		this.context.put("groups", DataAccessDriver.getInstance().newAttachmentModel().selectExtensionGroups());
	}
	
	public void extensionGroupsSave() throws Exception
	{
		AttachmentExtensionGroup g = new AttachmentExtensionGroup();		
		g.setAllow(this.request.getParameter("allow") != null);
		g.setDownloadMode(this.request.getIntParameter("download_mode"));
		g.setName(this.request.getParameter("name"));
		g.setUploadIcon(this.request.getParameter("upload_icon"));
		
		DataAccessDriver.getInstance().newAttachmentModel().addExtensionGroup(g);
		this.extensionGroups();
	}
	
	public void extensionGroupsUpdate() throws Exception
	{
		AttachmentModel am = DataAccessDriver.getInstance().newAttachmentModel();
		
		// Check if there are records to remove
		String[] delete = this.request.getParameterValues("delete");
		List deleteList = new ArrayList();
		if (delete != null) {
			deleteList = Arrays.asList(delete);
			am.removeExtensionGroups(delete);
		}
		
		// Update
		int total = this.request.getIntParameter("total_records");
		for (int i = 0; i < total; i++) {
			if (deleteList.contains(this.request.getParameter("id_" + i))) {
				continue;
			}
			
			AttachmentExtensionGroup g = new AttachmentExtensionGroup();
			g.setId(this.request.getIntParameter("id_" + i));
			g.setAllow(this.request.getParameter("allow_" + i) != null);
			g.setDownloadMode(this.request.getIntParameter("download_mode_" + i));
			g.setName(this.request.getParameter("name_" + i));
			g.setUploadIcon(this.request.getParameter("upload_icon_" + i));
			
			am.updateExtensionGroup(g);
		}
		
		this.extensionGroups();
	}
	
	public void extensions() throws Exception
	{
		AttachmentModel am = DataAccessDriver.getInstance().newAttachmentModel();
		
		this.context.put("moduleAction", "extensions.htm");
		this.context.put("extensions", am.selectExtensions());
		this.context.put("groups", am.selectExtensionGroups());
	}
	
	public void extensionsSave() throws Exception
	{
		AttachmentExtension e = new AttachmentExtension();
		e.setAllow(this.request.getParameter("allow") != null);
		e.setComment(this.request.getParameter("comment"));
		e.setExtension(this.request.getParameter("extension"));
		e.setUploadIcon(this.request.getParameter("upload_icon"));
		e.setExtensionGroupId(this.request.getIntParameter("extension_group"));
		
		if (e.getExtension().startsWith(".")) {
			e.setExtension(e.getExtension().substring(1));
		}
		
		DataAccessDriver.getInstance().newAttachmentModel().addExtension(e);
		this.extensions();
	}
	
	public void extensionsUpdate() throws Exception
	{
		AttachmentModel am = DataAccessDriver.getInstance().newAttachmentModel();
		
		// Check for records to delete
		String[] delete = this.request.getParameterValues("delete");
		List deleteList = new ArrayList();
		if (delete != null) {
			deleteList = Arrays.asList(delete);
			am.removeExtensions(delete);
		}
		
		int total = this.request.getIntParameter("total_records");
		for (int i = 0; i < total; i++) {
			if (deleteList.contains(this.request.getParameter("id_" + i))) {
				continue;
			}
			
			AttachmentExtension e = new AttachmentExtension();
			e.setAllow(this.request.getParameter("allow_" + i) != null);
			e.setComment(this.request.getParameter("comment_" + i));
			e.setExtension(this.request.getParameter("extension_" + i));
			e.setExtensionGroupId(this.request.getIntParameter("extension_group_" + i));
			e.setId(this.request.getIntParameter("id_" + i));
			e.setUploadIcon(this.request.getParameter("upload_icon_" + i));
			
			am.updateExtension(e);
		}
		
		this.extensions();
	}
	
	public void quotaGroupsSave() throws Exception
	{
		int total = this.request.getIntParameter("total_groups");
		AttachmentModel am = DataAccessDriver.getInstance().newAttachmentModel();
		am.cleanGroupQuota();
		
		for (int i = 0; i < total; i++) {
			String l = this.request.getParameter("limit_" + i);
			if (l == null || l.equals("")) {
				continue;
			}
			
			int limitId = Integer.parseInt(l);
			int groupId = this.request.getIntParameter("group_" + i);
			
			if (groupId > 0) {
				am.setGroupQuota(groupId, limitId);
			}
		}
		
		this.quotaLimit();
	}
	
	
	/**
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception
	{
		this.configurations();
	}

	/**
	 * @see net.jforum.Command#process(net.jforum.ActionServletRequest, javax.servlet.http.HttpServletResponse, java.sql.Connection, freemarker.template.SimpleHash)
	 */
	public Template process(ActionServletRequest request, HttpServletResponse response, Connection conn,
			SimpleHash context) throws Exception
	{
		if (AdminAction.isAdmin()) {
			super.process(request, response, conn, context);
		}

		return AdminAction.adminBaseTemplate();
	}
}
