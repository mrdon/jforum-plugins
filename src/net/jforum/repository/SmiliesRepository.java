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
 * This file creation date: 13/01/2004 / 20:23:52
 * net.jforum.repository.SmiliesRepository.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: SmiliesRepository.java,v 1.2 2004/04/21 23:57:33 rafaelsteil Exp $
 */
package net.jforum.repository;

import java.util.ArrayList;

import net.jforum.model.DataAccessDriver;

/**
 * @author Rafael Steil
 */
public class SmiliesRepository 
{
	private static ArrayList smiliesList = new ArrayList();
	
	static {
		try {
			SmiliesRepository.loadSmilies();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private SmiliesRepository() {}
	
	public static void loadSmilies() throws Exception
	{
		SmiliesRepository.smiliesList.clear();
		SmiliesRepository.smiliesList = DataAccessDriver.getInstance().newSmilieModel().selectAll();
	}
	
	public static ArrayList getSmilies()
	{
		return SmiliesRepository.smiliesList;
	}
}
