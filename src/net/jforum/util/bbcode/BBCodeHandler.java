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
 * This file creation date: 03/08/2003 / 05:28:03
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.bbcode;

 import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Rafael Steil
 * @version $Id: BBCodeHandler.java,v 1.8 2004/12/29 17:18:44 rafaelsteil Exp $
 */
public class BBCodeHandler extends DefaultHandler
{
	private Map bbMap = new LinkedHashMap();
	private boolean matchOpen = false;
	private String tagName = "";
	private StringBuffer sb;	
	private BBCode bb;
	
	public BBCodeHandler() { }
	
	public BBCodeHandler parse() throws Exception
	{
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		InputSource input = new InputSource(SystemGlobals.getValue(ConfigKeys.CONFIG_DIR) 
				+ "/bb_config.xml");
		BBCodeHandler bbParser = new BBCodeHandler();
		parser.parse(input, bbParser);
		
		return bbParser;  
	}
	
	public void addBb(BBCode bb)
	{
		this.bbMap.put(bb.getTagName(), bb);
	}
	
	public Collection getBbList()
	{
		return this.bbMap.values();
	}
	
	public BBCode findByName(String tagName)
	{
		return (BBCode)this.bbMap.get(tagName);
	}
	
	public void startElement(String uri, String localName, String tag, Attributes attrs)
	{
		if (tag.equals("match")) {
			this.matchOpen = true;
			this.sb = new StringBuffer();
			this.bb = new BBCode();
			
			String tagName = attrs.getValue("name");
			if (tagName != null) {
				this.bb.setTagName(tagName);
			}
			
			// Shall we remove the infamous quotes?
			String removeQuotes = attrs.getValue("removeQuotes");
			if (removeQuotes != null && removeQuotes.equals("true")) {
				this.bb.enableRemoveQuotes();
			}
		}
	
		this.tagName = tag;
	}

	public void endElement(String uri, String localName, String tag)
	{	
		if (tag.equals("match")) {
			this.matchOpen = false;
			this.addBb(this.bb);
		}
		else if (this.tagName.equals("replace")) {
			this.bb.setReplace(this.sb.toString().trim());
			this.sb.delete(0, this.sb.length());
		}
		else if (this.tagName.equals("regex")) {
			this.bb.setRegex(this.sb.toString().trim());
			this.sb.delete(0, this.sb.length());
		}
	
		this.tagName = "";
	}

	public void characters(char ch[], int start, int length)
	{
		if (this.tagName.equals("replace") || this.tagName.equals("regex"))
			this.sb.append(ch, start, length);
	}

	public void error(SAXParseException exception) throws SAXException 
	{
		throw exception;
	}
}