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
 * Created on Jan 13, 2005 7:47:58 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.cache;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.log4j.Logger;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * @author Rafael Steil
 * @version $Id: OSCacheEngine.java,v 1.1 2005/01/13 23:30:11 rafaelsteil Exp $
 */
public class OSCacheEngine implements CacheEngine
{
	private Logger logger = Logger.getLogger(OSCacheEngine.class);
	
	private GeneralCacheAdministrator cacheAdmin;
	private Cache cache;

	/**
	 * @see net.jforum.cache.CacheEngine#init()
	 */
	public void init()
	{
		try {
			Properties p = new Properties();
			p.load(new FileInputStream(SystemGlobals.getValue(ConfigKeys.OSCACHE_PROPERTIES)));
			
			this.cacheAdmin = new GeneralCacheAdministrator(p);
			this.cache = cacheAdmin.getCache();	
		}
		catch (IOException e) {
			this.logger.error("Error while trying to configure OSCache: " + e);
			return;
		}
	}

	/**
	 * @see net.jforum.cache.CacheEngine#add(java.lang.Object, java.lang.Object)
	 */
	public void add(String key, Object value)
	{
		this.cache.putInCache(key, value);
		this.cache.flushEntry(key);
		
		this.logger.info("Adding " + key + " to the cache. " + value);
	}

	/**
	 * @see net.jforum.cache.CacheEngine#add(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void add(String fqn, String key, Object value)
	{
		this.add(key, value);
	}

	/**
	 * @see net.jforum.cache.CacheEngine#get(net.jforum.cache.Cacheable, java.lang.String, java.lang.String)
	 */
	public Object get(Cacheable cacheable, String fqn, String key) throws InexistentCacheEntryException
	{
		return this.get(cacheable, key);
	}

	/**
	 * @see net.jforum.cache.CacheEngine#get(net.jforum.cache.Cacheable, java.lang.String)
	 */
	public Object get(Cacheable cacheable, String key) throws InexistentCacheEntryException
	{
		try {
			return this.cache.getFromCache(key);
		}
		catch (NeedsRefreshException e) {
			this.logger.info("Cache entry for " + key + " not found. Trying to reload");
			return cacheable.reload(CacheEngine.DUMMY_FQN, key); 
		}
	}

	/**
	 * @see net.jforum.cache.CacheEngine#remove(java.lang.String, java.lang.Object)
	 */
	public void remove(String fqn, String key)
	{
		this.cache.flushEntry(key);
		this.logger.info("Removing " + key + " from the cache");
	}

	/**
	 * @see net.jforum.cache.CacheEngine#remove(java.lang.Object)
	 */
	public void remove(String key)
	{
		this.remove(null, key);
	}

}
