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
 * Created on Jan 18, 2005 3:08:48 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum.common;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import net.jforum.ActionServletRequest;
import net.jforum.SessionFacade;
import net.jforum.entities.Attachment;
import net.jforum.entities.AttachmentExtension;
import net.jforum.entities.AttachmentInfo;
import net.jforum.model.AttachmentModel;
import net.jforum.model.DataAccessDriver;
import net.jforum.util.MD5;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

/**
 * @author Rafael Steil
 * @version $Id: AttachmentCommon.java,v 1.2 2005/01/19 19:25:55 rafaelsteil Exp $
 */
public class AttachmentCommon
{
	private static Logger logger = Logger.getLogger(AttachmentCommon.class);
	
	private ActionServletRequest request;
	private AttachmentModel am;
	
	public AttachmentCommon(ActionServletRequest request)
	{
		this.request = request;
		this.am = DataAccessDriver.getInstance().newAttachmentModel();
	}
	
	public void processAttachments(int postId) throws Exception
	{
		String t = this.request.getParameter("total_files");
		if (t == null || "".equals(t)) {
			return;
		}
		
		int total = Integer.parseInt(t);
		if (total < 1) {
			return;
		}
		
		if (total > SystemGlobals.getIntValue(ConfigKeys.ATTACHMENTS_MAX_POST)) {
			total = SystemGlobals.getIntValue(ConfigKeys.ATTACHMENTS_MAX_POST);
		}
		
		for (int i = 0; i < total; i++) {
			FileItem item = (FileItem)this.request.getObjectParameter("file_" + i);
			if (item == null) {
				continue;
			}

			if (item.getName().indexOf('\000') > -1) {
				logger.warn("Possible bad attachment (null char): " + item.getName()
						+ " - user_id: " + SessionFacade.getUserSession().getUserId());
				continue;
			}
			
			UploadUtils uploadUtils = new UploadUtils(item, this.request);
			
			Attachment a = new Attachment();
			a.setPostId(postId);
			a.setUserId(SessionFacade.getUserSession().getUserId());
			
			AttachmentInfo info = new AttachmentInfo();
			info.setFilesize(item.getSize());
			info.setComment(this.request.getParameter("comment_" + i));
			info.setMimetype(item.getContentType());
			info.setRealFilename(item.getName());
			info.setUploadTimeInMillis(System.currentTimeMillis());
			
			AttachmentExtension ext = this.am.selectExtension(uploadUtils.getExtension().toLowerCase());
			if (ext.isUnknown()) {
				ext.setExtension(uploadUtils.getExtension());
			}
			
			info.setExtension(ext);
			String savePath = this.makeStoreFilename(info);
			info.setPhysicalFilename(savePath);
			
			a.setInfo(info);
			
			uploadUtils.saveUploadedFile(SystemGlobals.getValue(ConfigKeys.ATTACHMENTS_STORE_DIR)
					+ "/" + savePath);
			
			this.am.addAttachment(a);
		}
	}
	
	private String makeStoreFilename(AttachmentInfo a)
	{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH + 1);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		String dir = "" + year + "/" + month + "/" + day + "/";
		new File(SystemGlobals.getValue(ConfigKeys.ATTACHMENTS_STORE_DIR) + "/" + dir).mkdirs();
		
		return dir
			+ MD5.crypt(a.getRealFilename() + a.getUploadTime()) 
			+ "_" + SessionFacade.getUserSession().getUserId()
			+ "." + a.getExtension().getExtension();
	}
	
	public List getAttachments(int postId) throws Exception
	{
		return this.am.selectAttachments(postId);
	}
}
