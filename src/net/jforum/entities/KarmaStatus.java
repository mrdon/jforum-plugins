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
 * Created on Jan 11, 2005 11:05:57 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.entities;

/**
 * @author Rafael Steil
 * @version $Id: KarmaStatus.java,v 1.1 2005/01/13 23:30:09 rafaelsteil Exp $
 */
public class KarmaStatus
{
	private int userId;
	private int totalPositive;
	private int totalNegative;
	private double karmaPoints;
	
	public KarmaStatus() {}
	
	/**
	 * Creates a new KarmaStatus instance.
	 * #calculeKarma() is called by this constructor.
	 * 
	 * @param userId The id of the user related to this 
	 * karma status
	 * @param totalPositive Number of positive votes
	 * @param totalNegative Number of negative votess
	 */
	public KarmaStatus(int userId, int totalPositive, int totalNegative)
	{
		this.userId = userId;
		this.totalPositive = totalPositive;
		this.totalNegative = totalNegative;
		
		this.calculeKarma();
	}
	
	public void calculeKarma()
	{
		// TODO: define karma status logic
	}
	
	/**
	 * @return Returns the karmaPoints.
	 */
	public double getKarmaPoints()
	{
		return this.karmaPoints;
	}
	
	/**
	 * @return Returns the totalNegative.
	 */
	public int getTotalNegative()
	{
		return this.totalNegative;
	}
	
	/**
	 * @param totalNegative The totalNegative to set.
	 */
	public void setTotalNegative(int totalNegative)
	{
		this.totalNegative = totalNegative;
	}
	
	/**
	 * @return Returns the totalPositive.
	 */
	public int getTotalPositive()
	{
		return this.totalPositive;
	}
	
	/**
	 * @param totalPositive The totalPositive to set.
	 */
	public void setTotalPositive(int totalPositive)
	{
		this.totalPositive = totalPositive;
	}
	
	/**
	 * @return Returns the userId.
	 */
	public int getUserId()
	{
		return this.userId;
	}
	
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
}
