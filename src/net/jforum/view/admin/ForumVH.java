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
 * This file creation date: Mar 28, 2003 / 8:21:56 PM
 * net.jforum.view.admin.ForumVH.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.admin;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.entities.Forum;
import net.jforum.model.CategoryModel;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.ForumModel;
import net.jforum.repository.ForumRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 */
public class ForumVH extends Command 
{
	// Listing
	public void list() throws Exception
	{
		JForum.getContext().put("forums", DataAccessDriver.getInstance().newForumModel().selectAll());
		JForum.getContext().put("moduleAction", "forum_list.htm");
	}
	
	// One more, one more
	public void insert() throws Exception
	{
		CategoryModel cm = DataAccessDriver.getInstance().newCategoryModel();
		
		JForum.getContext().put("moduleAction", "forum_form.htm");
		JForum.getContext().put("categories",cm.selectAll());
		JForum.getContext().put("action", "insertSave");		
	}
	
	// Edit
	public void edit() throws Exception
	{
		CategoryModel cm = DataAccessDriver.getInstance().newCategoryModel();
		
		JForum.getContext().put("forum", DataAccessDriver.getInstance().newForumModel().selectById(Integer.parseInt(JForum.getRequest().getParameter("forum_id"))));
		JForum.getContext().put("categories", cm.selectAll());
		JForum.getContext().put("moduleAction", "forum_form.htm");
		JForum.getContext().put("action", "editSave");
	}
	
	//  Save information
	public void editSave() throws Exception
	{
		Forum f = new Forum();
		f.setDescription(JForum.getRequest().getParameter("description"));
		f.setId(Integer.parseInt(JForum.getRequest().getParameter("forum_id")));
		f.setIdCategories(Integer.parseInt(JForum.getRequest().getParameter("categories_id")));
		f.setModerated(JForum.getRequest().getParameter("moderated") != null && JForum.getRequest().getParameter("moderated").equals("true"));
		f.setName(JForum.getRequest().getParameter("forum_name"));
			
		DataAccessDriver.getInstance().newForumModel().update(f);
		
		ForumRepository.reloadForum(f.getId());
		
		// RSS
		//new ForumRSS().createRSS();
			
		this.list();
	}
	
	// Delete
	public void delete() throws Exception
	{
		String ids[] = JForum.getRequest().getParameterValues("forum_id");
		
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				fm.delete(Integer.parseInt(ids[i]));
				
				Forum f = new Forum();
				f.setId(Integer.parseInt(ids[i]));
				ForumRepository.removeForum(f);
			}
		}
		
		// RSS
		//new ForumRSS().createRSS();

		this.list();
	}
	
	// A new one
	public void insertSave() throws Exception
	{
		Forum f = new Forum();
		f.setDescription(JForum.getRequest().getParameter("description"));
		f.setIdCategories(Integer.parseInt(JForum.getRequest().getParameter("categories_id")));
		f.setModerated(JForum.getRequest().getParameter("moderated") != null && JForum.getRequest().getParameter("moderated").equals("true"));
		f.setName(JForum.getRequest().getParameter("forum_name"));	
			
		int forumId = DataAccessDriver.getInstance().newForumModel().addNew(f);
		f.setId(forumId);
		
		ForumRepository.addForum(f);
		
		// RSS
		//new ForumRSS().createRSS();
		
		this.list();
	}
	
	/* 
	 * @see net.jforum.Command#process()
	 */
	public Template process() throws Exception 
	{	
		if (AdminVH.isAdmin()) {
			super.process();
		}

		return Configuration.getDefaultConfiguration().getTemplate("admin/index_main.htm");
	}

}
