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
 * This file creation date: Mar 28, 2003 / 8:09:08 PM
 * net.jforum.view.admin.RankingVH.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: RankingVH.java,v 1.2 2004/04/21 23:57:29 rafaelsteil Exp $
 */
package net.jforum.view.admin;


import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.entities.Ranking;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.RankingModel;
import net.jforum.repository.RankingRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 */
public class RankingVH extends Command 
{
	// List
	public void list() throws Exception
	{
		JForum.getContext().put("ranks", DataAccessDriver.getInstance().newRankingModel().selectAll());
		JForum.getContext().put("moduleAction", "ranking_list.htm");
	}
	
	// One more, one more
	public void insert() throws Exception
	{
		JForum.getContext().put("moduleAction", "ranking_form.htm");
		JForum.getContext().put("action", "insertSave");
	}
	
	// Edit
	public void edit() throws Exception
	{
		JForum.getContext().put("rank", DataAccessDriver.getInstance().newRankingModel().selectById(Integer.parseInt(JForum.getRequest().getParameter("ranking_id"))));
		JForum.getContext().put("moduleAction", "ranking_form.htm");
		JForum.getContext().put("action", "editSave");
	}
	
	//  Save information
	public void editSave() throws Exception
	{
		Ranking r = new Ranking();
		r.setTitle(JForum.getRequest().getParameter("rank_title"));
		r.setMin(Integer.parseInt(JForum.getRequest().getParameter("rank_min")));
		r.setId(Integer.parseInt(JForum.getRequest().getParameter("rank_id")));
		
		// TODO: needs to add support to images 
		
		DataAccessDriver.getInstance().newRankingModel().update(r);
		RankingRepository.loadRanks();	
		this.list();
	}
	
	// Delete
	public void delete() throws Exception
	{
		String ids[] = JForum.getRequest().getParameterValues("rank_id");
		
		RankingModel rm = DataAccessDriver.getInstance().newRankingModel();
		
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				rm.delete(Integer.parseInt(ids[i]));
			}
		}
			
		this.list();
	}
	
	// A new one
	public void insertSave() throws Exception
	{
		Ranking r = new Ranking();
		r.setTitle(JForum.getRequest().getParameter("rank_title"));
		r.setMin(Integer.parseInt(JForum.getRequest().getParameter("rank_min")));
		
		// TODO: need to add support to images
		DataAccessDriver.getInstance().newRankingModel().addNew(r);
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
