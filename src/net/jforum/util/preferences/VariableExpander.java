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
 * Created on May 31, 2004 by pieter
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.preferences;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pieter
 * @version $Id: VariableExpander.java,v 1.4 2005/07/26 03:05:12 rafaelsteil Exp $
 */
public class VariableExpander {
	private static final int MAX_REPLACEMENTS = 15;
	
	private VariableStore variables;
	private String pre;
	private String post;
	
	private Map cache;
	
	public VariableExpander(VariableStore variables, String pre, String post) {
		this.variables = variables;
		this.pre = pre;
		this.post = post;
		cache = new HashMap();
	}
	
	public void clearCache() {
		cache.clear();
	}
	
	public String expandVariables(String source) {
		String result = (String) cache.get(source);
		if (result != null) {
			return result;
		}
		
		boolean found = true;
		int count = 0;
		
		while (found && count++ < MAX_REPLACEMENTS) {
			found = false;
			int start = source.lastIndexOf(pre);
			if (start != -1) {
				int end = source.indexOf(post, start);
				if (end != -1) {
					found = true;
					String prefix = source.substring(0, start);
					String postfix = source.substring(end + post.length());
					String name = source.substring(start + pre.length(), end);
					name = expandVariables(name);
					int assign = name.indexOf('=');
					String defaultValue = null;
					if (assign >= 0) {
						defaultValue = name.substring(assign+1);
						name = name.substring(0, assign);
					}
					String value = variables.getVariableValue(name);
					if (value == null) {
						value = defaultValue;
						if (value == null) {
							throw new RuntimeException("variable not defined: " + name);
						}
					}
					source = prefix + value + postfix;
				}
			}
		}
		
		return source;
	}
	
}
