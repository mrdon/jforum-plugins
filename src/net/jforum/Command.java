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
 * This file creation date: Mar 3, 2003 / 10:55:19 AM
 * net.jforum.Command.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * <code>Command</code> Pattern implementation.
 * All View Helper classes, which are intead to configure and processs
 * presentation actions must extend this class. 
 * 
 * @author Rafael Steil
 */
public abstract class Command 
{
	/**
	 * Base method for listings. 
	 * May be used as general listing or as helper
	 * to another specialized type of listing. Subclasses
	 * must implement it to the cases where some invalid
	 * action is called ( which means that the exception will
	 * be caught and the general listing will be used )
	 * 
	 * @throws Exception  
	 */
	public abstract void list() throws Exception;
	
	/**
	 * Process and manipulate a requisition.
	 * 
	 * @throws Exception
	 * @return <code>Template</code> reference
	 */
	public Template process() throws Exception 
	{
		try{
			Class.forName(this.getClass().getName()).getMethod(JForum.getRequest().getAction(), null).invoke(this, null);
		}
		catch (NoSuchMethodException e) {		
			list();		
		}
		catch (Exception e) {
			throw e;
		}
		
		return Configuration.getDefaultConfiguration().getTemplate("default/forum_base.htm");
	}
}
