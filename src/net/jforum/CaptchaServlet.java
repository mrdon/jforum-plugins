package net.jforum;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jforum.util.Captcha;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author James Yong
 *
 */
public class CaptchaServlet extends JForumCommonServlet
{
	/** 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}
	
	public void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("write captcha image");
		// Initializes thread local data
		DataHolder dataHolder = new DataHolder();
		localData.set(dataHolder);

		String encoding = SystemGlobals.getValue(ConfigKeys.ENCODING);
		req.setCharacterEncoding(encoding);

		// Request
		ActionServletRequest request = new ActionServletRequest(req);
		request.setCharacterEncoding(encoding);
		
		dataHolder.setResponse(response);
		dataHolder.setRequest(request);

		// Assigns the information to user's thread 
		localData.set(dataHolder);

		
		if (SessionFacade.getUserSession() != null) {
			Captcha.getInstance().writeCaptchaImage(response);
		}
		
		localData.set(null);
	}
}
