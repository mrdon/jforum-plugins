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

import javax.servlet.http.HttpServletResponse;

import net.jforum.ActionServletRequest;
import net.jforum.Command;
import net.jforum.model.DataAccessDriver;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: AttachmentsAction.java,v 1.1 2005/01/17 18:52:32 rafaelsteil Exp $
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
	
	public void quota() throws Exception
	{
		this.context.put("quotas", DataAccessDriver.getInstance().newAttachmentModel().selectQuotaLimit());
		this.context.put("moduleAction", "quota.htm");
	}
	
	public void quotaSave() throws Exception
	{
		
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
