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
 * This file creation date: 31/01/2004 - 19:07:55
 * net.jforum.util.rss.RSSable.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.rss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.jforum.util.SystemGlobals;

/**
 * @author Rafael Steil
 * 
 * A RSS representation of some content. 
 */
public abstract class RSSable
{
	protected static final String RSS_VERSION = "2.0";
	
	protected static final String ROOT_ELEMENT = "rss";
	protected static final String VERSION_ATTRIBUTE = "version";
	protected static final String CHANNEL_ELEMENT = "channel";
	protected static final String TITLE_ELEMENT = "title";
	protected static final String LINK_ELEMENT = "link";
	protected static final String DESCRIPTION_ELEMENT = "description";
	protected static final String ITEM_ELEMENT = "item";
	
	private static Object mutex = new Object();
	
	/**
	 * Creates the RSS data.
	 * Use this method to process and create a new RSS
	 * file. 
	 * 
	 * @throws Exception
	 * @see #getFilename()
	 * @see #getRSSContents()
	 */
	public void createRSS() throws Exception
	{
		RSSDocument rssDocument = this.prepareRSSDocument();
		
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = docBuilder.newDocument();
		
		Element rootElement = document.createElement(ROOT_ELEMENT);
		
		// The RSS' version
		rootElement.setAttribute(VERSION_ATTRIBUTE, RSS_VERSION);
		
		for (Iterator iter = rssDocument.getChannels().iterator(); iter.hasNext(); ) {
			RSSChannel channel = (RSSChannel)iter.next();
			
			// Channel
			Element channelElement = document.createElement(CHANNEL_ELEMENT);
			
			// Channel title
			Element channelTitleElement = document.createElement(TITLE_ELEMENT);
			channelTitleElement.appendChild(document.createTextNode(channel.getChannelTitle()));
			
			// Channel Description
			Element channelDescriptionElement = document.createElement(DESCRIPTION_ELEMENT);
			channelDescriptionElement.appendChild(document.createTextNode(channel.getChannelDescription()));
			
			// Channel Link
			Element channelLinkElement = document.createElement(LINK_ELEMENT);
			channelLinkElement.appendChild(document.createTextNode(channel.getChannelLink()));
			
			channelElement.appendChild(channelTitleElement);
			channelElement.appendChild(channelLinkElement);
			channelElement.appendChild(channelDescriptionElement);
			
			// Items
			for (Iterator itemIter = channel.getItems().iterator(); itemIter.hasNext(); ) {
				RSSItem item = (RSSItem)itemIter.next();
				
				// Item element
				Element itemElement = document.createElement(ITEM_ELEMENT);
				
				// Title
				Element itemTitleElement = document.createElement(TITLE_ELEMENT);
				itemTitleElement.appendChild(document.createTextNode(item.getTitle()));
				
				// Link
				Element itemLinkElement = document.createElement(LINK_ELEMENT);
				itemLinkElement.appendChild(document.createTextNode(item.getLink()));
				
				// Description
				Element itemDescriptionElement = document.createElement(DESCRIPTION_ELEMENT);
				itemDescriptionElement.appendChild(document.createTextNode(item.getDescription()));
				
				// Add the item to the channel
				itemElement.appendChild(itemTitleElement);
				itemElement.appendChild(itemLinkElement);
				itemElement.appendChild(itemDescriptionElement);
				
				channelElement.appendChild(itemElement);
			}
			
			// Add to root
			rootElement.appendChild(channelElement);
		}
		
		document.appendChild(rootElement);
		document.getDocumentElement().normalize();
		
		synchronized (mutex) {
			DOMSource domSource = new DOMSource(document);
			StreamResult sr = new StreamResult(new File(SystemGlobals.getApplicationPath() + this.getFilename()));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(domSource, sr);
		}
	}
	
	protected abstract RSSDocument prepareRSSDocument() throws Exception;
	
	/**
	 * Gets the RSS filename.
	 * The filename is relative to webiste document root. 
	 * 
	 * @return <code>String</code> with the file name
	 * @throws Exception
	 */
	public abstract String getFilename() throws Exception;
	
	/**
	 * Checks if some element exists in the queue
	 * 
	 * @param o The object to check
	 * @return <code>true</code> if the object exists, or <code>false</code> it not.
	 */
	public abstract boolean objectExists(Object o);
	
	/**
	 * Gets the RSS file contents. 
	 *  
	 * @return A <code>String</code> containing the RSS data.
	 * @throws Exception
	 */
	public String getRSSContents() throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader(SystemGlobals.getApplicationPath() + this.getFilename()));
		StringBuffer sb = new StringBuffer(256);

		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		
		reader.close();
		return sb.toString();
	}
}
