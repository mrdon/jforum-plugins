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
 * 
 * $Id: SmiliesAction.java,v 1.2 2004/10/03 16:53:45 rafaelsteil Exp $
 */
package net.jforum.view.admin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.entities.Smilie;
import net.jforum.model.DataAccessDriver;
import net.jforum.repository.SmiliesRepository;
import net.jforum.util.MD5;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 */
public class SmiliesAction extends Command 
{
	private String processUpload() throws Exception
	{
		String imgName = "";
		
		if (JForum.getRequest().getObjectParameter("smilie_img") != null) {
			imgName = JForum.getRequest().getParameter("smilie_imgName");
			String extension = extension = imgName.substring(imgName.lastIndexOf('.'));
			
			imgName = MD5.crypt(JForum.getRequest().getParameter("smilie_imgName"));
			
			BufferedInputStream inputStream = new BufferedInputStream((InputStream)JForum.getRequest().getObjectParameter("smilie_img"));
			FileOutputStream outputStream = new FileOutputStream(SystemGlobals.getApplicationPath() +"/"+ SystemGlobals.getValue(ConfigKeys.SMILIE_IMAGE_DIR) +"/"+ imgName + extension);
			
			int c = 0;
			byte[] b = new byte[1024];
			while ((c = inputStream.read(b)) != -1) {
				outputStream.write(b);
			}
			
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			
			imgName += extension;
		}
		
		return imgName;
	}
	
	public void insert()
	{
		JForum.getContext().put("moduleAction", "smilie_form.htm");
		JForum.getContext().put("action", "insertSave");
	}
		
	public void insertSave() throws Exception
	{
		Smilie s = new Smilie();
		s.setCode(JForum.getRequest().getParameter("code"));
		
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
		
		if (JForum.getRequest().getParameter("id") != null) {
			id = Integer.parseInt(JForum.getRequest().getParameter("id"));
		}
		
		JForum.getContext().put("moduleAction", "smilie_form.htm");
		JForum.getContext().put("smilie", DataAccessDriver.getInstance().newSmilieModel().selectById(id));
		JForum.getContext().put("action", "editSave");
	}
	
	public void editSave() throws Exception
	{
		Smilie s = DataAccessDriver.getInstance().newSmilieModel().selectById(Integer.parseInt(JForum.getRequest().getParameter("id")));
		s.setCode(JForum.getRequest().getParameter("code"));
		
		if (JForum.getRequest().getObjectParameter("smilie_img") != null) {
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
		if (JForum.getRequest().getParameter("id") != null) {
			int id = Integer.parseInt(JForum.getRequest().getParameter("id"));
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

	/* 
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception 
	{
		JForum.getContext().put("smilies", SmiliesRepository.getSmilies());
		JForum.getContext().put("moduleAction", "smilie_list.htm");
	}

	/*
	 * @see net.jforum.Command#process()
	 */
	public Template process() throws Exception 
	{
		if (AdminAction.isAdmin()) {
			super.process();
		}
		
		return AdminAction.adminBaseTemplate();
	}
}
