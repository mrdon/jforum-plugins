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
 * This file creating date: Feb 23, 2003 / 9:01:09 PM
 * net.jforum.util.CriteriaExpression.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.model;

/**
 * Represents a column relacted expression.
 * This class provides a simple way to pass column names, values
 * and conditions to some SQL expression. Every column from some table,
 * its comparison character and the type ( AND, OR etc ) are 
 * represented with this class. 
 * <br><br>
 * For example, lets suppose you want to add to your SQL string a simple
 * check expression: "if the age is equal to 18 or equal to 21", for example. In normal SQL 
 * code, it goes like this:
 * <blockquote>SELECT fieldA FROM tableX WHERE age = 18 OR age = 21</blockquote>
 * So far all fine. To represent "WHERE age = 18 OR age = 21" expression, you use
 * <code>CriteriaExpression</code> togheter with {@link CriteriaConstants}:
 * <blockquote>
 * <code>
 *..
 * CriteriaExpression ce = new CriteriaExpression("age", "18", CriteriaConstants.EQUAL, CriteriaConstans.NULL);
 * CriteriaExpression ce2 = new CriteriaExpression("age", "21", CriteriaConstants.EQUAL, CriteriaConstans.AND);
 * ..
 * </code>
 * </blockquote>
 * <br>
 * Into normal situation you wont need to create variables to every expressions,
 * being enough just create the object "on the fly" when passing it to some
 * {@link Criteria} objct reference.  
 * 
 * 
 * @author Rafael Steil
 */
public class CriteriaExpression 
{
	/**
	 * The Row name
	 */
	private String name;
	
	/**
	 * The Row value
	 */
	private String value;

	/**
	 * The condition for the SQL comparison ( &lt;&gt;, =, &l;=, &gt; etc )
	 */
	private int condition;
	
	/**
	 * The expression type: AND, OR etc
	 */
	private int type;

	/**
	 * If <code>true</code>, the value passed will be put into quotes. The opposite to <code>false</code>
	 */
	private boolean escapeValue;

	/**
	 * Creates a new <code>CriteriaRow</code> objet.
	 * 
	 * @param name The column  name
	 * @param value The column value
	 * @param condition The comparison expression
	 * @param type The expression type
	 */
	public CriteriaExpression(String name, String value, int condition, int type)
	{
		this(name, value, condition, type, true);
	}
	
	/**
	 * Creates a new <code>CriteriaRow</code> objet.
	 * 
	 * @param name The column  name
	 * @param value The column value
	 * @param condition The comparison expression
	 * @param type The expression type
	 * @param escapeValue Put quotes - or not - in the value
	 */
	public CriteriaExpression(String name, String value, int condition, int type, boolean escapeValue)
	{
		this.name = name;
		this.value = value;
		this.condition = condition;
		this.type = type;
		this.escapeValue = escapeValue;
	}

	/**
	 * Returns the column name 
	 * 
	 * @return String with the column name	 
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Returns the column value
	 * 
	 * @return String with the column value
	 */
	public String getValue()
	{
		return this.value;
	}

	/**
	 * Returns the expression type
	 * 
	 * @return int value representing the expression. To know the expression
	 * value ( the character value ), check {@link CriteriaConstants}
	 */
	public int getType()
	{
		return this.type;
	}

	/**
	 * Returns the comparison condition.
	 * 
	 * @return int value representing the condition. 
	 */
	public int getCondition()
	{
		return this.condition;
	}
	
	/**
	 * Returns the escape value status;
	 * 
	 * @return The escape value
	 */
	public boolean getEscapeValue()
	{
		return this.escapeValue;
	}
}
