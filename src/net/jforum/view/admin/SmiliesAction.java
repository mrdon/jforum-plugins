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
 * This file creation date: 13/01/2004 / 18:45:31
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.admin;

import java.io.File;
import java.sql.Connection;

import javax.servlet.http.HttpServletResponse;

import net.jforum.ActionServletRequest;
import net.jforum.Command;
import net.jforum.entities.Smilie;
import net.jforum.model.DataAccessDriver;
import net.jforum.repository.SmiliesRepository;
import net.jforum.util.MD5;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.view.forum.common.UploadUtils;

import org.apache.commons.fileupload.FileItem;

import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 * @version $Id: SmiliesAction.java,v 1.6 2005/01/18 20:59:47 rafaelsteil Exp $
 */
public class SmiliesAction extends Command 
{
	private String processUpload() throws Exception
	{
		String imgName = "";
		
		if (this.request.getObjectParameter("smilie_img") != null) {
			FileItem item = (FileItem)this.request.getObjectParameter("smilie_img");
			UploadUtils uploadUtils = new UploadUtils(item, this.request);

			imgName = MD5.crypt(item.getName());
			
			uploadUtils.saveUploadedFile(SystemGlobals.getApplicationPath() 
					+ "/"
					+ SystemGlobals.getValue(ConfigKeys.SMILIE_IMAGE_DIR) 
					+ "/"
					+ imgName + "." + uploadUtils.getExtension());
			
			imgName += "." + uploadUtils.getExtension();
		}
		
		return imgName;
	}
	
	public void insert()
	{
		this.context.put("moduleAction", "smilie_form.htm");
		this.context.put("action", "insertSave");
	}
		
	public void insertSave() throws Exception
	{
		Smilie s = new Smilie();
		s.setCode(this.request.getParameter("code"));
		
		String imgName = this.processUpload();
		s.setUrl(SystemGlobals.getValue(ConfigKeys.SMILIE_IMAGE_PATTERN).replaceAll("#IMAGE#", imgName));
		
		s.setDiskName(imgName);
		
		DataAccessDriver.getInstance().newSmilieModel().addNew(s);
		
		SmiliesRepository.loadSmilies();
		this.list();
	}
	
	public void edit() throws Exception
	{
		int id = 1;
		
		if (this.request.getParameter("id") != null) {
			id = this.request.getIntParameter("id");
		}
		
		this.context.put("moduleAction", "smilie_form.htm");
		this.context.put("smilie", DataAccessDriver.getInstance().newSmilieModel().selectById(id));
		this.context.put("action", "editSave");
	}
	
	public void editSave() throws Exception
	{
		Smilie s = DataAccessDriver.getInstance().newSmilieModel().selectById(this.request.getIntParameter("id"));
		s.setCode(this.request.getParameter("code"));
		
		if (this.request.getObjectParameter("smilie_img") != null) {
			String imgName = this.processUpload();
			s.setUrl(SystemGlobals.getValue(ConfigKeys.SMILIE_IMAGE_PATTERN).replaceAll("#IMAGE#", imgName));
			s.setDiskName(imgName);
		}

		DataAccessDriver.getInstance().newSmilieModel().update(s);
		
		SmiliesRepository.loadSmilies();
		this.list();
	}
	
	public void delete() throws Exception
	{
		if (this.request.getParameter("id") != null) {
			int id = this.request.getIntParameter("id");
			Smilie s = DataAccessDriver.getInstance().newSmilieModel().selectById(id);
			
			DataAccessDriver.getInstance().newSmilieModel().delete(id);
			
			File f = new File(SystemGlobals.getApplicationPath() +"/"+ SystemGlobals.getValue(ConfigKeys.SMILIE_IMAGE_DIR) +"/"+ s.getDiskName());
			if (f.exists()) {
				f.delete();
			}
		}
		
		SmiliesRepository.loadSmilies();
		this.list();
	}

	/** 
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception 
	{
		this.context.put("smilies", SmiliesRepository.getSmilies());
		this.context.put("moduleAction", "smilie_list.htm");
	}

	/**
	 * @see net.jforum.Command#process()
	 */
	public Template process(ActionServletRequest request, 
			HttpServletResponse response, 
			Connection conn, SimpleHash context) throws Exception 
	{
		if (AdminAction.isAdmin()) {
			super.process(request, response, conn, context);
		}
		
		return AdminAction.adminBaseTemplate();
	}
}
