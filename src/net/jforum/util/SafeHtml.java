/*
 * Copyright (c) 2003, 2004 Rafael Steil
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
 * This file creation date: 27/09/2004 23:59:10
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.Html;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeIterator;

/**
 * Process text with html and remove possible
 * malicious tags and attributes.
 * 
 * @author Rafael Steil
 * @version $Id: SafeHtml.java,v 1.1 2004/09/28 04:03:28 rafaelsteil Exp $
 */
public class SafeHtml 
{
	private static final Logger logger = Logger.getLogger(SafeHtml.class);
	private StringBuffer sb = new StringBuffer(512);
	
	private String processAllNodes(String contents) throws Exception
	{
		Parser p = new Parser(new Lexer(contents));
		this.displayChildren(p.elements());
		
		return this.sb.toString();
	}
	
	private Node displayNode(Node node) throws Exception
	{
		if (node == null) {
			return null;
		}

		if (!(node instanceof TagNode)) {
			return node;
		}

		TagNode tag = (TagNode)node;

		Vector attributes = tag.getAttributesEx();
		Vector newAttributes = new Vector();
		for (Iterator iter = attributes.iterator(); iter.hasNext(); ) {
			Attribute a = (Attribute)iter.next();
			if (a.getName() != null && a.getName().toLowerCase().startsWith("on")) {
				continue;
			}

			newAttributes.add(a);
		}

		if (tag instanceof ScriptTag) {
			ScriptTag script = (ScriptTag)tag;
			node = new TextNode("&lt;script" + (script.getLanguage() != null ? " language=" + script.getLanguage() : "") 
					+ "&gt;" 
					+ script.getScriptCode() 
					+ "&lt;/script&gt;");
		}
		else {
			tag.setAttributesEx(newAttributes);
			
			if (tag.getChildren() != null) {
				this.displayChildren(tag.getChildren().elements());
			}
		}

		return node;
	}
	
	private void displayChildren(NodeIterator elements) throws Exception
	{
		while (elements.hasMoreNodes()) {
			Node node = elements.nextNode();
			node = this.displayNode(node);
			
			if (!this.ignoreTagRendering(node))
				this.sb.append(node.toHtml());
		}
	}
	
	private boolean ignoreTagRendering(Node node)
	{
		return ((node instanceof BodyTag) || (node instanceof Html));
	}
	
	
	public static String makeSafe(String contents)
	{
		if (contents == null || contents.trim().length() == 0) {
			return contents;
		}
		
		try {
			contents = new SafeHtml().processAllNodes(contents);
		}
		catch (Exception e) {
			logger.warn("Problems while parsing the html: " + e);
			; // we don't care
		}
		
		return contents;
	}
}
