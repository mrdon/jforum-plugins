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
 * This file creation date: Mar 10, 2003 / 8:49:51 PM
 * net.jforum.view.admin.CategoryVH.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: CategoryVH.java,v 1.2 2004/04/21 23:57:28 rafaelsteil Exp $
 */
package net.jforum.view.admin;

import java.util.ArrayList;

import net.jforum.entities.Category;
import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.model.CategoryModel;
import net.jforum.model.DataAccessDriver;
import net.jforum.repository.CategoryRepository;
import net.jforum.util.I18n;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * ViewHelper for category administration.
 * 
 * @author Rafael Steil
 */
public class CategoryVH extends Command 
{
	private CategoryModel cm;
	
	// Listing
	public void list() throws Exception
	{
		JForum.getContext().put("categories", this.cm.selectAll());
		JForum.getContext().put("moduleAction", "category_list.htm");
	}
	
	// One more, one more
	public void insert() throws Exception
	{
		JForum.getContext().put("moduleAction", "category_form.htm");
		JForum.getContext().put("action", "insertSave");
	}
	
	// Edit
	public void edit() throws Exception
	{
		JForum.getContext().put("category", this.cm.selectById(Integer.parseInt(JForum.getRequest().getParameter("category_id"))));
		JForum.getContext().put("moduleAction", "category_form.htm");
		JForum.getContext().put("action", "editSave");
	}
	
	//  Save information
	public void editSave() throws Exception
	{
		Category c = new Category();
		c.setName(JForum.getRequest().getParameter("category_name"));
		c.setId(Integer.parseInt(JForum.getRequest().getParameter("categories_id")));
			
		this.cm.update(c);
		CategoryRepository.loadCategories();
			
		this.list();
	}
	
	// Delete
	public void delete() throws Exception
	{
		String ids[] = JForum.getRequest().getParameterValues("categories_id");
		ArrayList errors = new ArrayList();
		
		if (ids != null) {						
			for (int i = 0; i < ids.length; i++){
				if (this.cm.canDelete(Integer.parseInt(ids[i]))) {
					this.cm.delete(Integer.parseInt(ids[i]));
					
				}
				else {
					errors.add(I18n.getMessage(I18n.CANNOT_DELETE_CATEGORY, new Object[] {new Integer(ids[i])}));
				}
			}
		}

		if (errors.size() > 0) {
			JForum.getContext().put("errorMessage", errors);
		}
		
		CategoryRepository.loadCategories();	
		this.list();
	}
	
	// A new one
	public void insertSave() throws Exception
	{
		Category c = new Category();
		c.setName(JForum.getRequest().getParameter("category_name"));
			
		this.cm.addNew(c);
		CategoryRepository.loadCategories();
			
		this.list();
	}
	
	/* 
	 * @see net.jforum.Command#process()
	 */
	public Template process() throws Exception 
	{
		if (AdminVH.isAdmin()) {
			this.cm = DataAccessDriver.getInstance().newCategoryModel();
			super.process();
		}

		return Configuration.getDefaultConfiguration().getTemplate("admin/index_main.htm");
	}

}
