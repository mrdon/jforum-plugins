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
 * This file creation date: Mar 6, 2003 / 11:09:34 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.generic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.jforum.JForum;
import net.jforum.entities.Category;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: CategoryModel.java,v 1.7 2004/12/05 21:51:24 rafaelsteil Exp $
 */
public class CategoryModel extends AutoKeys implements net.jforum.model.CategoryModel 
{
	private Connection conn;
	
	public CategoryModel()
	{
		this.conn = JForum.getConnection();
	}
	
	public CategoryModel(Connection conn)
	{
		this.conn = conn;
	}
	
	/**
	 * @see net.jforum.model.CategoryModel#selectById(int)
	 */
	public Category selectById(int categoryId) throws Exception 
	{
		PreparedStatement p = this.conn.prepareStatement(SystemGlobals.getSql("CategoryModel.selectById"));
		p.setInt(1, categoryId);
		
		ResultSet rs = p.executeQuery();
		
		Category c = new Category();
		if (rs.next()) {
			c.setId(categoryId);
			c.setName(rs.getString("title"));
			c.setOrder(rs.getInt("display_order"));
		}
		
		rs.close();
		p.close();
		
		return c;
	}

	/** 
	 * @see net.jforum.model.CategoryModel#selectAll()
	 */
	public List selectAll() throws Exception 
	{
		PreparedStatement p = this.conn.prepareStatement(SystemGlobals.getSql("CategoryModel.selectAll"));
		List l = new ArrayList();
		
		ResultSet rs = p.executeQuery();		
		while (rs.next()) {
			Category c = new Category();
			
			c.setId(rs.getInt("categories_id"));
			c.setName(rs.getString("title"));
			c.setOrder(rs.getInt("display_order"));	
			
			l.add(c);		
		}
		
		rs.close();
		p.close();
			
		return l;
	}

	/** 
	 * @see net.jforum.model.CategoryModel#canDelete(int)
	 */
	public boolean canDelete(int categoryId) throws Exception
	{
		PreparedStatement p = this.conn.prepareStatement(SystemGlobals.getSql("CategoryModel.canDelete"));
		p.setInt(1, categoryId);
		
		ResultSet rs = p.executeQuery();
		if (!rs.next() || rs.getInt("total") < 1) {		
			return true;
		}
		
		rs.close();
		p.close();
		
		return false;
	}

	/**
	 * @see net.jforum.model.CategoryModel#delete(int)
	 */
	public void delete(int categoryId) throws Exception 
	{
		PreparedStatement p = this.conn.prepareStatement(SystemGlobals.getSql("CategoryModel.delete"));
		p.setInt(1, categoryId);
		p.executeUpdate();
		
		p.close();
	}

	/**
	 * @see net.jforum.model.CategoryModel#update(net.jforum.Category)
	 */
	public void update(Category category) throws Exception 
	{
		PreparedStatement p = this.conn.prepareStatement(SystemGlobals.getSql("CategoryModel.update"));		
		p.setString(1, category.getName());
		p.setInt(2, category.getId());
		p.executeUpdate();
		p.close();
	}

	/** 
	 * @see net.jforum.model.CategoryModel#addNew(net.jforum.Category)
	 */
	public int addNew(Category category) throws Exception 
	{
		PreparedStatement p = this.getStatementForAutoKeys("CategoryModel.addNew");
		p.setString(1, category.getName());
		
		int id = this.executeAutoKeysQuery(p);
		
		p.close();
		
		return id;
	}
	
	/**
	 * @see net.jforum.model.CategoryModel#setOrderUp(Category, Category)
	 */
	public void setOrderUp(Category category, Category relatedCategory) throws Exception 
	{
		this.setOrder(category, relatedCategory, true);
	}
	
	/**
	 * @see net.jforum.model.CategoryModel#setOrderDown(Category, Category)
	 */
	public void setOrderDown(Category category, Category relatedCategory) throws Exception 
	{
		this.setOrder(category, relatedCategory, false);
	}
	
	private void setOrder(Category category, Category otherCategory, boolean up) throws Exception
	{
		int tmpOrder = otherCategory.getOrder();
		otherCategory.setOrder(category.getOrder());
		category.setOrder(tmpOrder);

		// *********
		PreparedStatement p = this.conn.prepareStatement(SystemGlobals.getSql("CategoryModel.setOrderById"));
		p.setInt(1, otherCategory.getOrder());
		p.setInt(2, otherCategory.getId());
		p.executeUpdate();
		p.close();

		// *********
		p = this.conn.prepareStatement(SystemGlobals.getSql("CategoryModel.setOrderById"));
		p.setInt(1, category.getOrder());
		p.setInt(2, category.getId());
		p.executeUpdate();
		p.close();
	}
}
