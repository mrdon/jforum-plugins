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
 * This file creation date: Nov 13, 2004 / 17:17:09
 * The JForum Project
 * http://www.jforum.net
 */

package net.jforum.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import net.jforum.JForum;
import net.jforum.SessionFacade;

import org.apache.log4j.Logger;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * @author James Yong
 * @version $Id: Captcha.java,v 1.3 2004/12/30 00:07:59 rafaelsteil Exp $
 */
public class Captcha extends ListImageCaptchaEngine
{
	private static final Logger logger = Logger.getLogger(Captcha.class);
	
	private static Captcha classInstance = new Captcha();
	private List backgroundGeneratorList;
	private List textPasterList;
	private List fontGeneratorList;

	private static final String charsInUsed = "123456789ABCDEFGHJLKMNPRSTWXYZ";

	/**
	 * Gets the singleton
	 * 
	 * @return Instance of Captcha class
	 */
	public static Captcha getInstance()
	{
		return classInstance;
	}

	protected void buildInitialFactories()
	{
		this.backgroundGeneratorList = new ArrayList();
		this.textPasterList = new ArrayList();
		this.fontGeneratorList = new ArrayList();

		this.backgroundGeneratorList.add(new GradientBackgroundGenerator(new Integer(220), 
				new Integer(50), Color.BLACK, Color.GRAY));
		this.backgroundGeneratorList.add(new FunkyBackgroundGenerator(new Integer(220), new Integer(50)));

		this.textPasterList.add(new RandomTextPaster(new Integer(6), new Integer(8), Color.RED));
		this.textPasterList.add(new RandomTextPaster(new Integer(6), new Integer(8), Color.ORANGE));
		this.textPasterList.add(new RandomTextPaster(new Integer(6), new Integer(8), Color.BLUE));
		this.textPasterList.add(new RandomTextPaster(new Integer(6), new Integer(8), Color.WHITE));

		this.fontGeneratorList.add(new TwistedAndShearedRandomFontGenerator(new Integer(30), new Integer(40)));

		// Create a random word generator
		WordGenerator words = new RandomWordGenerator(charsInUsed);

		for (Iterator fontIter = this.fontGeneratorList.iterator(); fontIter.hasNext();) {
			FontGenerator fontGeny = (FontGenerator) fontIter.next();

			for (Iterator backIter = this.backgroundGeneratorList.iterator(); backIter.hasNext();) {
				BackgroundGenerator bkgdGeny = (BackgroundGenerator) backIter.next();

				for (Iterator textIter = this.textPasterList.iterator(); textIter.hasNext();) {
					TextPaster textPaster = (TextPaster) textIter.next();

					WordToImage word2image = new ComposedWordToImage(fontGeny, bkgdGeny, textPaster);
					
					// Creates a ImageCaptcha Factory
					ImageCaptchaFactory factory = new GimpyFactory(words, word2image);
					
					// Add a factory to the gimpy list (A Gimpy is a ImagCaptcha)
					addFactory(factory);
				}
			}
		}

	}

	public void writeCaptchaImage()
	{
		BufferedImage image = SessionFacade.getUserSession().getCaptchaImage();
		if (image == null) {
			return;
		}

		OutputStream outputStream = null;
		try {
			outputStream = JForum.getResponse().getOutputStream();
			JForum.setContentType("image/png");
			ImageIO.write(image, "png", outputStream);
			outputStream.close();
			outputStream = null;
		}
		catch (IOException ex) {
			logger.error(ex);
		}
		finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				}
				catch (IOException ex) {
				}
			}
		}
	}
}
