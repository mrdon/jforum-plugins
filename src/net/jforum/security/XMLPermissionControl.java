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
 * This file creation date: 21/09/2003 / 16:36:44
 * net.jforum.XMLPermissionControl.java
 */
package net.jforum.security;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.jforum.ForumException;
import net.jforum.JForum;
import net.jforum.util.FormSelectedData;
import net.jforum.util.SystemGlobals;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

 /**
 * Manipulates XML permission control file definition 
 * 
 * @author Rafael Steil
 */
public class XMLPermissionControl extends DefaultHandler 
{
	private PermissionSection section;
	private List listSections;
	private String permissionName;
	private String permissionId;
	private String permissionType;
	private ArrayList permissionData;
	private PermissionControl pc;
	
	private boolean alreadySelected;
	
	public XMLPermissionControl(PermissionControl pc)
	{
		this.listSections = new ArrayList();
		this.permissionData = new ArrayList();
		this.pc = pc;
	}

	/**
	 * @return <code>List</code> object containing <code>Section</code> objects. Each
	 * <code>Section</code>  contains many <code>PermissionItem</code> objects, 
	 * which represent the permission elements of some section. For its turn, the
	 * <code>PermissionItem</code> objects have many <code>FormSelectedData</code>
	 * objects, which are the ones responsible to store field values, and which values
	 * are checked and which not. 
	 */
	public List loadConfigurations(String xmlFile)
		throws Exception 
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		
		SAXParser parser = factory.newSAXParser();
		InputSource input = new InputSource(xmlFile);
		
		parser.parse(input, this);
				
		return this.listSections;
	}

	/**
	 * @see org.xml.sax.ContentHandler#endElement(String, String, String)
	 */
	public void endElement(String namespaceURI, String localName, String tag)
		throws SAXException 
	{
		if (tag.equals("section")) {
			this.listSections.add(this.section);
		}
		else if (tag.equals("permission")) {
			this.section.addPermission(new PermissionItem(this.permissionName, this.permissionId, this.permissionType, this.permissionData));

			this.permissionData = new ArrayList();
		}
	}

	/**
	 * @see org.xml.sax.ErrorHandler#error(SAXParseException)
	 */
	public void error(SAXParseException exception) throws SAXException 
	{
		throw exception;
	}

	/**
	 * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
	 */
	public void startElement(
		String namespaceURI,
		String localName,
		String tag,
		Attributes atts)
		throws SAXException 
	{
		if (tag.equals("section")) {
			this.section = new PermissionSection(atts.getValue("title"), atts.getValue("id"));
		}
		else if (tag.equals("permission")) {
			this.permissionName = atts.getValue("title");
			this.permissionId = atts.getValue("id");
			this.permissionType = atts.getValue("type");
			this.alreadySelected = false;
		}
		else if (tag.equals("sql")) {
			ResultSet rs = null;
			PreparedStatement p = null;
			
			try {
				p = JForum.getConnection().prepareStatement(SystemGlobals.getSql(atts.getValue("queryName")));
				rs = p.executeQuery();
				
				String valueField = atts.getValue("valueField");
				String captionField = atts.getValue("captionField");
			
				// user/group values array
				RoleValueCollection roleValues = new RoleValueCollection();
				Role role = this.pc.getRole(this.permissionId);
				
				if (role != null) {
					roleValues = role.getValues();
				}
				
				while (rs.next()) {
					String value = rs.getString(valueField);
					String caption = rs.getString(captionField);
					
					RoleValue rv = roleValues.get(value);

					this.permissionData.add(
						new FormSelectedData(
							caption, 
							value,
							rv != null && rv.getType() == PermissionControl.ROLE_DENY
						)
					);
				}
			}
			catch (Exception e) {
				new ForumException(e);
			}
			finally {
				try {
					if (rs != null) {
						rs.close();
						p.close();
					}
				}
				catch (Exception e) {
					new ForumException(e);
				}
			}
		}
		else if (tag.equals("option")) {
			boolean selected = false;
			
			if (this.permissionType.equals("single")) {
				if (this.pc.canAccess(this.permissionId) && atts.getValue("value").equals("allow") && !this.alreadySelected) {
					selected = true;
					this.alreadySelected = true;
				}
			}
			else {
				// TODO: Implement this shit
				throw new UnsupportedOperationException("'option' tag with 'multiple' attribute support not yet implemented");
			}
			
			this.permissionData.add(new FormSelectedData(atts.getValue("description"), atts.getValue("value"), selected));
		}
	}

}