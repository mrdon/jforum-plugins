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
 * This file creation date: 20/05/2004 - 15:24:07
 * net.jforum.entities.PrivateMessage.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.entities;

/**
 * @author Rafael Steil
 * @version $Id: PrivateMessage.java,v 1.1 2004/05/21 00:24:11 rafaelsteil Exp $
 */
public class PrivateMessage 
{
	private int id;
	private int type;
	private String subject;
	private User fromUser;
	private User toUser;
	private long date;
	private boolean enableBbCode;
	private boolean enableHtml;
	private boolean enableSmilies;
	private boolean attachSignature;
	private String text;
	
	/**
	 * @return Returns the attachSignature.
	 */
	public boolean isAttachSignature()
	{
		return attachSignature;
	}
	
	/**
	 * @param attachSignature The attachSignature to set.
	 */
	public void setAttachSignature(boolean attachSignature)
	{
		this.attachSignature = attachSignature;
	}
	
	/**
	 * @return Returns the date.
	 */
	public long getDate()
	{
		return date;
	}
	
	/**
	 * @param date The date to set.
	 */
	public void setDate(long date)
	{
		this.date = date;
	}
	
	/**
	 * @return Returns the enableBbCode.
	 */
	public boolean isEnableBbCode()
	{
		return enableBbCode;
	}
	
	/**
	 * @param enableBbCode The enableBbCode to set.
	 */
	public void setEnableBbCode(boolean enableBbCode)
	{
		this.enableBbCode = enableBbCode;
	}
	
	/**
	 * @return Returns the enableHtml.
	 */
	public boolean isEnableHtml()
	{
		return enableHtml;
	}
	
	/**
	 * @param enableHtml The enableHtml to set.
	 */
	public void setEnableHtml(boolean enableHtml)
	{
		this.enableHtml = enableHtml;
	}
	
	/**
	 * @return Returns the enableSmilies.
	 */
	public boolean isEnableSmilies()
	{
		return enableSmilies;
	}
	
	/**
	 * @param enableSmilies The enableSmilies to set.
	 */
	public void setEnableSmilies(boolean enableSmilies)
	{
		this.enableSmilies = enableSmilies;
	}
	
	/**
	 * @return Returns the fromUser.
	 */
	public User getFromUser()
	{
		return fromUser;
	}
	
	/**
	 * @param fromUser The fromUser to set.
	 */
	public void setFromUser(User fromUser)
	{
		this.fromUser = fromUser;
	}
	
	/**
	 * @return Returns the subject.
	 */
	public String getSubject()
	{
		return subject;
	}
	
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	/**
	 * @return Returns the text.
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * @param text The text to set.
	 */
	public void setText(String text)
	{
		this.text = text;
	}
	
	/**
	 * @return Returns the toUser.
	 */
	public User getToUser()
	{
		return toUser;
	}
	
	/**
	 * @param toUser The toUser to set.
	 */
	public void setToUser(User toUser)
	{
		this.toUser = toUser;
	}
	
	/**
	 * @return Returns the type.
	 */
	public int getType()
	{
		return type;
	}
	
	/**
	 * @param type The type to set.
	 */
	public void setType(int type)
	{
		this.type = type;
	}
	
	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id = id;
	}
}
