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
 * Created on Jan 13, 2005 6:00:40 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.cache;

/**
 * @author Rafael Steil
 * @version $Id: Cacheable.java,v 1.1 2005/01/13 23:30:12 rafaelsteil Exp $
 */
public interface Cacheable
{
	/**
	 * Reloads some object. 
	 * This method may be used when the cache engine used
	 * throws some exception regarding key in an invalid state
	 * or missing, so you have a change to try to fetch the 
	 * data from somewhere.
	 * 
	 * @param fqn The fully qualified name used in the cache engine. 
	 * This value may be <code>null</code> if not used by some engines,
	 * although is highly recommended its use.
	 * @param key The wanted key
	 * @return The object instance, if found, or <code>null</code> otherwise
	 */
	public Object reload(String fqn, String key);
	
	/**
	 * Sets the cache engine instance.
	 * @param engine The instance of the cache engine
	 */
	public void setCacheEngine(CacheEngine engine);
}
