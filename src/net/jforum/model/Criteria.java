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
 * This file creating date: Feb 23, 2003 / 12:02:43 PM
 * net.jforum.model.Criteria.java
 * The JForum Project
 * http://www.jforum.net
 */

package net.jforum.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Rafael Steil
 */
public class Criteria 
{
	/**
	 * ArrayList containing all expressions
	 */
	private ArrayList expression;
	
	/**
	 * Row number to start fetching. 
	 */
	private int startFrom;
	
	/**
	 * Max number of rows to fetch
	 */
	private int count;
	
	/**
	 * Field names to add to the SELECT clause
	 */
	private String[] selectFields;
	
	/**
	 * The table ( or tables ) name to retrieve the resutls
	 */
	private ArrayList fromTables;

	/**
	 * Default constructor
	 */
	public Criteria()
	{
		this.expression = new ArrayList();
	}
	
	/**
	* Super class for common expressions.
	* This is the base class for some SQL expressions operations,
	* like <code>AND</code> and <code>OR</code>. It provides common
	* methods to add a new {@link CriteriaExpression} expression,
	* like a simple way to retrieve the expression as well.
	**/
	public static class Expression
	{
		/**
		 * ArrayList containing all expressions.
		 */
		private ArrayList expression;

		public Expression()
		{
			this.expression = new ArrayList();
		}
		
		/**
		 * Adds a new expression.
		 * 
		 * @param e <code>CriteriaExpression</code> object reference with a valid expression
		 */
		public void add(CriteriaExpression e)
		{
			this.expression.add(e);
		}
		
		/**
		 * Adds a new expression.
		 * 
		 * @param e <code>Expression</code> object reference with a valid expression
		 */
		public void add(Expression e)
		{
			this.expression.add(e);
		}

		/**
		 * Returns the expression for this object.
		 * 
		 * @return ArrayList with the expression
		 */
		public ArrayList getExpression()
		{
			return this.expression;
		}		
	}
	
	/**
	 * Inner Class to represent an "AND" expression.
	 * Use this class when you want to enclose some groups
	 * of {@link CriteriaExpression} togheter. 
	 * <p>
	 * Normal SQL statements are like <br>
	 * <code>SELECT field1, field2 FROM tableX WHERE aaa = 1 AND bbb = 2 OR ccc = 3</code><br>
	 * This class provides a simple way to group fields. For example, into situations where some
	 * field must match some criteria and other two values may be "a" or 'b", you could create an
	 * <code>ANDExpression</code> reference to do the job. For example:<br>
	 * <pre>
	 * <code>
	 * ..
	 * ANDExpression e = new ANDExpression();
	 * e.add(new CriteriaExpression("bbb", "2", CriteriaConstans.EQUAL, CriteriaConstants.NULL));
	 * e.add(new CriteriaExpression("ccc", "3", CriteriaConstans.EQUAL, CriteriaConstants.OR));
	 * myCriteriaObject.addFilter(e);
	 * ..
	 * </code>
	 * </blockquote><br>
	 * and considering that you previously have created a valid <code>Criteria</code> object, the result
	 * will be<br><code>SELECT field1, field2 FROM tableX WHERE aaa = 1 AND (bbb = 2 OR ccc = 3)</code>
	 * <p>
	 * <b>Note:</b> this is just a marker class
	 */
	public static class ANDExpression extends Expression { }


	/**
	 * Inner Class to represent an "OR" expression.
	 * Use this class when you want to enclose some groups
	 * of {@link CriteriaExpression} togheter. <br>
	 * Normal SQL statements are like<br>
	 * <code>SELECT field1, field2 FROM tableX WHERE aaa = 1 OR bbb = 2 OR ccc = 3</code>
	 * <br>
	 * This class provides a simple way to group fields. For example, into situations where some
	 * field must match some criteria and other two values may be "a" or 'b", you could create an
	 * <code>ANDExpression</code> reference to do the job. For example:<br>
	 * <blockquote>
	 * <code>
	 * ..
	 * ORExpression e = new ORExpression();
	 * e.add(new CriteriaExpression("bbb", "2", CriteriaConstans.EQUAL, CriteriaConstants.NULL));
	 * e.add(new CriteriaExpression("ccc", "3", CriteriaConstans.EQUAL, CriteriaConstants.OR));
	 * myCriteriaObject.addFilter(e);
	 * ..
	 * </code>
	 * </blockquote><br>
	 * and considering that you previously have created a valid <code>Criteria</code> object, the result
	 * will be<br><code>SELECT field1, field2 FROM tableX WHERE aaa = 1 OR (bbb = 2 OR ccc = 3)</code>
	 * <p>
	 * <b>Note:</b> this is just a marker class
	 */
	public static class ORExpression extends Expression { }

	/**
	 * Inner class to represent JOIN statements.
	 * This class stores the table names and its respectives columns
	 * to make a valid JOIN ( actually a LEFT JOIN ). 
	 */
	public static class JOINExpression
	{
		/**
		 * Stores the table and columns name for both parts of the join.
		 * The positions description are:<br>
		 * [0.0] - table 1
		 * [0.1] - column name 1 
		 * [1.0] - table 2
		 * [1.1] - column name 2		 
		 */
		private String fields[][];

		/**
		 * Adds the values for the JOIN expression.
		 * 
		 * @param table1 The name of first table of the join ( the one more to the left )
		 * @param column1 The column name of the first table
		 * @param table2 The name of second table of the join
		 * @param column2 The column name of the second table
		 */
		public void add(String table1, String column1, String table2, String column2)
		{
			this.fields[0][0] = table1;
			this.fields[0][1] = column1; 
			this.fields[1][0] = table2;
			this.fields[1][1] = column2;			
		}

		/**
		 * Return the values that represents the JOIN expresion
		 * 
		 * @return Double array of Strings with the values. The positions description are:<br>
		 * <code>
		 * [0.0] - table 1
		 * [0.1] - column name 1 
		 * [1.0] - table 2
		 * [1.1] - column name 2
		 * </code>		  
		 */
		public String[][] getFields()
		{
			return this.fields;
		}
	}
	
	/**
	 * Adds a new WHERE expression to the SQL criteria.
	 * This method is exactly equal to <code>addFilter</code>. It 
	 * just have a different name to make code interpretation more easy
	 * ( or less complicated hehehe ).
	 * 
	 * @param e <code>CriteriaExpression</code> object with the "rules"
	 * #see addFilter(CriteriaExpression)
	 */
	public void addWhere(CriteriaExpression e) 
	{
		this.addFilter(e);
	}
	
	/**
	 * Adds a filter to the SQL String. 
	 * A filter is jsut an expression that must match some pre-defined
	 * rules. For example, to represent <code>AND name = "kxy"</code>,
	 * you could use:<br>
	 *  <blockquote>
	 * <code>
	 * criteriaObj.addFilter(new CriteriaExpression("name", "kxy", CriteriaConstants.EQUAL, CriteriaConstans.AND));
	 * </code>
	 * </blockquote>
	 * 
	 * @param row e <code>CriteriaExpression</code> object with the "rules"
	 * @see CriteriaConstans, CriteriaExpression, #addWhere, #addFilter(ANDExpression), #addFilter(ORExpression)
	 */
	public void addFilter(CriteriaExpression e) 
	{
		this.expression.add(e);
	}
	
	/**
	 * Adds a filter to the SQL String.
	 * For more information, read the documentation for <code>addFilter(CriteriaExpression)</code>
	 * method and {@link ANDExpression} class.
	 * 
	 * @param e <code>ANDExpression</code> objet with the "rules"
	 * @see #addFilter(CriteriaExpression), #addFilter(ORExpression), #addWhere
	 */
	public void addFilter(Expression e) 
	{
		this.expression.add(e);
	}

	/**
	 * Adds a filter to the SQL String.
	 * For more information, read the documentation for <code>addFilter(CriteriaExpression)</code>
	 * method and {@link JOINExpression} class.
	 * 
	 * @param e <code>JOINExpression</code> objet with the "rules"
	 * @see #addFilter(CriteriaExpression), #addFilter(ORExpression), #addWhere
	 */
	public void addFilter(JOINExpression e)
	{
		this.expression.add(e);
	}

	/**
	 * Sets the maximum number of rows to retrieve
	 * 
	 * @param count The number of rows
	 * @see #setLimit(int, int)
	 */
	public void setLimit(int count)
	{
		this.count = count;
	}

	/**
	 * Sets the maximum number of rows to retrieve and the start position
	 * 
	 * @param startFrom The row number to start fetching
	 * @param count The number of row to fetch
	 * @see #setLimit(int)
	 */
	public void setLimit(int startFrom, int count)
	{
		this.startFrom = startFrom;
		this.count = count;
	}
	
	/**
	 * Setst the table ( or tables ) name to retrieve the results.
	 * 
	 * @param from Array of String with the tables names. Each position must containing exactly one table name
	 */
	public void setFromTables(ArrayList from)
	{
		this.fromTables = from;
	}
	
	/**
	 * Sets the order of the results that will be retrieved.
	 * 
	 * @param fieldName Name ( or names ) of the fields to order when retrieving/executing the SQL statement
	 * @see #setGroupBy
	 */
	public void setOrderBy(String fieldName)
	{
	}

	/**
	 * Sets the order of the results that will be retrieved.
	 * 
	 * @param fieldName Name ( or names ) of the fields to order when retrieving/executing the SQL statement
	 * @see #setOrderBy
	 */
	public void setGroupBy(String fieldName)
	{
	}

	/**
	 * 
	 * @param f
	 */
	public void addSelectFields(String f[])
	{
		this.selectFields = f;
	}

	/**
	 * Generates the SQL String based on the filters added
	 * 
	 * @return String with the SQL statement created 
	 */
	public String generateSql()
	{
		Iterator iter = this.expression.iterator();

		StringBuffer sql = new StringBuffer();		
		sql.append("SELECT ");
		
		// Adds the fields to retrieve to the SQL statement	
		for (int i = 0; i < this.selectFields.length - 1; i++) {
			sql.append(this.selectFields[i] +", ");
		}
		sql.append(this.selectFields[this.selectFields.length - 1]);
		
		// Adds the table names
		sql.append(" FROM ");
		Iterator iterator = this.fromTables.iterator();
		while (iterator.hasNext()) {
			sql.append(iterator.next().toString()).append(", ");
		}
		
		int len = sql.length();
		sql.delete(len - 2, len);
				
		sql.append(" WHERE ");
		
		makeExpression(sql, (CriteriaExpression)iter.next());
		
		while (iter.hasNext()) {
			this.processExpression(sql, iter.next());
		}

		return sql.toString();
	}
	
	private void processExpression(StringBuffer sql, Object o)
	{
		// ** CriteriaExpression **
		if (o instanceof CriteriaExpression) {
			makeExpression(sql, (CriteriaExpression)o);
		}
		// ** ANDExpression **
		else if (o instanceof ANDExpression) {
			ArrayList l = ((ANDExpression)o).getExpression();

			sql.append(" AND (");
			
			Iterator tmpIter = l.iterator();
			while (tmpIter.hasNext()) {
				this.processExpression(sql, tmpIter.next()); 
			}

			// Remove a possible remaider 'AND'
			if (sql.toString().endsWith("AND ")) {
				int slen = sql.length();
				sql.delete(slen - 4, slen);
			}
			sql.append(") ");
		}
		// ** ORExpression **
		else if (o instanceof ORExpression) {
			ArrayList l = ((ORExpression)o).getExpression();

			sql.append(" OR (");
			
			Iterator tmpIter = l.iterator();
			while (tmpIter.hasNext()) {
				this.processExpression(sql, tmpIter.next());
			}

			// Remove a possible remaider 'OR'
			if (sql.toString().endsWith("OR ")) {
				int slen = sql.length();
				sql.delete(slen - 3, slen);
			}
			sql.append(") ");
		}
		// ** JOINExpression **
		else if (o instanceof JOINExpression) {
			String join[][] = ((JOINExpression)o).getFields();

			sql.append(" LEFT JOIN ");
			sql.append(join[0][0]);
			sql.append(" ON ");
			sql.append(join[0][0]);
			sql.append("."+ join[0][1]);
			sql.append(" = ");
			sql.append(join[1][0] +"."+ join[1][1]);

			// TODO
			// fazer essa porcaria direito
		}
	}
	
	private void makeExpression(StringBuffer sql, CriteriaExpression e)
	{
		sql.append(e.getName()).append(" ") // field name
			.append(CriteriaConstants.symbol[e.getCondition()]) // comparison 
			.append(" ")
			.append(getValueByCondition(e)) // the value to compare
			.append(" ")
			.append(CriteriaConstants.symbol[e.getType()]) // and, or...
			.append(" ");
	}
	
	private String getValueByCondition(CriteriaExpression e)
	{
		if (e.getCondition() == CriteriaConstants.LIKE || e.getCondition() == CriteriaConstants.NOT_LIKE) {
			return "'%"+ e.getValue() +"%'";
		}
		else {
			return (e.getEscapeValue() ? "'"+ e.getValue() +"'" : e.getValue());
		}
	}
}
