package net.jforum.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import net.jforum.SessionFacade;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.wordgenerator.WordGenerator;
import com.octo.captcha.component.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.image.ImageCaptchaFactory;
import javax.imageio.ImageIO;

public class Captcha extends ListImageCaptchaEngine {

	private static Captcha classInstance = new Captcha();
	
    private ArrayList backgroundGeneratorList;
    private ArrayList textPasterList;
    private ArrayList fontGeneratorList;
    
    private static final String charsInUsed = "123456789ABCDEFGHJLKMNPRSTWXYZ";

    /**
     * Gets the singleton
     * 
     * @return Instance of Captcha class
     */
    public static Captcha getInstance() {
        return classInstance;
    }
    
	protected void buildInitialFactories() {
		
		backgroundGeneratorList = new ArrayList();
		textPasterList = new ArrayList();
		fontGeneratorList = new ArrayList();
		
		backgroundGeneratorList.add(new GradientBackgroundGenerator(new Integer(180), new Integer(80), Color.PINK, Color.YELLOW));
		backgroundGeneratorList.add(new FunkyBackgroundGenerator(new Integer(180), new Integer(80)));
		
		textPasterList.add(new RandomTextPaster(new Integer(4), new Integer(5), Color.RED));
		textPasterList.add(new RandomTextPaster(new Integer(4), new Integer(5), Color.GREEN));
		textPasterList.add(new RandomTextPaster(new Integer(4), new Integer(5), Color.BLUE));

		fontGeneratorList.add(new TwistedAndShearedRandomFontGenerator(new Integer(30), new Integer(40)));

		//Create a random word generator
        WordGenerator words = new RandomWordGenerator(charsInUsed);

        for (Iterator fontIter = fontGeneratorList.iterator(); fontIter.hasNext(); ) 
        {
            FontGenerator fontGeny = (FontGenerator)fontIter.next();
            
            for (Iterator backIter = backgroundGeneratorList.iterator(); backIter.hasNext(); ) 
            {
                BackgroundGenerator bkgdGeny = (BackgroundGenerator)backIter.next();
                
                for (Iterator textIter = textPasterList.iterator(); textIter.hasNext(); ) 
                {
                    TextPaster textPaster = (TextPaster)textIter.next();

                    WordToImage word2image = new ComposedWordToImage(fontGeny, bkgdGeny, textPaster);
                    //Creates a ImageCaptcha Factory
                    ImageCaptchaFactory factory = new GimpyFactory(words, word2image);
                    //Add a factory to the gimpy list (A Gimpy is a ImagCaptcha)
                    addFactory(factory);
                }
            }
        }		
		
	}
	
    public void writeCaptchaImage(HttpServletResponse response){

    	BufferedImage image = SessionFacade.getUserSession().getCaptchaImage();
    	if (image == null) {
    		return;
    	}

    	OutputStream outputStream = null;
    	try {
    		outputStream = response.getOutputStream();
   			response.setContentType("image/png");
    		ImageIO.write(image, "png", outputStream);
    		outputStream.close();
    		outputStream = null;
    	} catch (IOException ex) {
    		//handles error here
     	} finally {
    		if (outputStream != null) {
    			try {
    				outputStream.close();
    			} catch (IOException ex) { }
    		}
    	}
    }
}
