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
 * This file creation date: Mar 6, 2003 / 11:09:34 PM
 * net.jforum.drivers.mysql.CategoryModel.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: CategoryModel.java,v 1.2 2004/04/21 23:57:20 rafaelsteil Exp $
 */
package net.jforum.drivers.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.jforum.JForum;
import net.jforum.entities.Category;
import net.jforum.util.SystemGlobals;

/**
 * @author Rafael Steil
 */
public class CategoryModel implements net.jforum.model.CategoryModel 
{
	/*
	 * @see net.jforum.model.CategoryModel#selectById(int)
	 */
	public Category selectById(int categoryId) throws Exception 
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("CategoryModel.selectById"));
		p.setInt(1, categoryId);
		
		ResultSet rs = p.executeQuery();
		
		Category c = new Category();
		if (rs.next()) {
			c.setId(categoryId);
			c.setName(rs.getString("title"));
			c.setOrder(rs.getInt("display_order"));
		}
		
		return c;
	}

	/* 
	 * @see net.jforum.model.CategoryModel#selectAll()
	 */
	public ArrayList selectAll() throws Exception 
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("CategoryModel.selectAll"));
		ArrayList l = new ArrayList();
		
		ResultSet rs = p.executeQuery();		
		while (rs.next()) {
			Category c = new Category();
			
			c.setId(rs.getInt("categories_id"));
			c.setName(rs.getString("title"));
			c.setOrder(rs.getInt("display_order"));	
			
			l.add(c);		
		}
			
		return l;
	}

	/* 
	 * @see net.jforum.model.CategoryModel#canDelete(int)
	 */
	public boolean canDelete(int categoryId) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("CategoryModel.canDelete"));
		p.setInt(1, categoryId);
		
		ResultSet rs = p.executeQuery();
		if (!rs.next() || rs.getInt("total") < 1) {		
			return true;
		}
		
		return false;
	}

	/*
	 * @see net.jforum.model.CategoryModel#delete(int)
	 */
	public void delete(int categoryId) throws Exception 
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("CategoryModel.delete"));
		p.setInt(1, categoryId);
		
		p.executeUpdate();
	}

	/*
	 * @see net.jforum.model.CategoryModel#update(net.jforum.Category)
	 */
	public void update(Category category) throws Exception 
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("CategoryModel.update"));		
		p.setString(1, category.getName());
		p.setInt(2, category.getId());
		
		p.executeUpdate();
	}

	/* 
	 * @see net.jforum.model.CategoryModel#addNew(net.jforum.Category)
	 */
	public void addNew(Category category) throws Exception 
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("CategoryModel.addNew"));		
		p.setString(1, category.getName());
		
		p.executeUpdate();		
	}

}
