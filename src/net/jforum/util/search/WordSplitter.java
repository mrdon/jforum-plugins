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
 * Creation date: Ago 27, 2005 / 12:33:17
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Ieong
 * @version $Id: WordSplitter.java,v 1.1 2005/08/27 22:58:14 rafaelsteil Exp $
 */
public class WordSplitter
{
	public static String[] split(String s)
	{
		List wordlist = new ArrayList();
		
		char[] chars = s.toLowerCase().toCharArray();
		int startpos = 0;
		
		for (int i = 0; i < chars.length; i++) {
			System.out.println(chars[i] + " is: " + Character.isLetterOrDigit(chars[i]));
			if (!Character.isLowerCase(chars[i])) {
				// Skip words less than 3 chars
				if (i - startpos + 1 > 3) {
					wordlist.add(new String(chars, startpos, i - startpos));
				}
				
				startpos = i + 1;

				if (chars[i] > 255) {
					wordlist.add(new String(chars, i, 1));
				}
			}
		}
		
		if (chars.length - startpos + 1 > 3) {
			wordlist.add(new String(chars, startpos, chars.length - startpos));
		}

		return (String[]) wordlist.toArray(new String[0]);
	}

}