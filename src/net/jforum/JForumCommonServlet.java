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
 * This file creation date: 27/08/2004 - 18:22:10
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import net.jforum.repository.BBCodeRepository;
import net.jforum.util.FileMonitor;
import net.jforum.util.I18n;
import net.jforum.util.bbcode.BBCodeHandler;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.QueriesFileListener;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.util.preferences.SystemGlobalsListener;

import org.apache.log4j.xml.DOMConfigurator;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;

/**
 * @author Rafael Steil
 * @version $Id: JForumCommonServlet.java,v 1.8 2004/10/10 16:46:26 rafaelsteil Exp $
 */
public class JForumCommonServlet extends HttpServlet {
    private static Properties modulesMapping;

    protected boolean debug;

    // Thread local implementation
    protected static ThreadLocal localData = new ThreadLocal() {
        public Object initialValue() {
            return new DataHolder();
        }
    };

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            debug = "true".equals(config.getInitParameter("development"));

            DOMConfigurator.configure(config.getServletContext().getRealPath("")
                    + "/WEB-INF/log4j.xml");

            // Load system default values
            // TODO: allow defaultsFile and installation to be overridden by the init parameters
            String appPath = config.getServletContext().getRealPath("");
            SystemGlobals.initGlobals(appPath,
                    appPath + "/WEB-INF/config/SystemGlobals.properties", null);
            SystemGlobals.loadAdditionalDefaults(SystemGlobals
                    .getValue(ConfigKeys.DATABASE_DRIVER_CONFIG));

            if (new File(SystemGlobals.getValue(ConfigKeys.INSTALLATION_CONFIG)).exists()) {
                SystemGlobals.loadAdditionalDefaults(SystemGlobals
                        .getValue(ConfigKeys.INSTALLATION_CONFIG));
            }

            // Configure the template engine
            Configuration templateCfg = new Configuration();
            templateCfg.setDirectoryForTemplateLoading(new File(SystemGlobals.getApplicationPath()
                    + "/templates"));
            templateCfg.setTemplateUpdateDelay(0);

            this.loadModulesMapping(SystemGlobals.getApplicationResourceDir());

            SystemGlobals.loadQueries(SystemGlobals.getValue(ConfigKeys.SQL_QUERIES_GENERIC));
            SystemGlobals.loadQueries(SystemGlobals.getValue(ConfigKeys.SQL_QUERIES_DRIVER));

            this.loadConfigStuff();

            if (!this.debug) {
                templateCfg.setTemplateUpdateDelay(3600);
            }

            int fileChangesDelay = SystemGlobals.getIntValue(ConfigKeys.FILECHANGES_DELAY);

            if (fileChangesDelay > 0) {
                // Queries
                FileMonitor.getInstance().addFileChangeListener(new QueriesFileListener(),
                        SystemGlobals.getValue(ConfigKeys.SQL_QUERIES_GENERIC), fileChangesDelay);

                FileMonitor.getInstance().addFileChangeListener(new QueriesFileListener(),
                        SystemGlobals.getValue(ConfigKeys.SQL_QUERIES_DRIVER), fileChangesDelay);

                // System Properties
                FileMonitor.getInstance().addFileChangeListener(new SystemGlobalsListener(),
                        SystemGlobals.getValue(ConfigKeys.DEFAULT_CONFIG), fileChangesDelay);

                FileMonitor.getInstance().addFileChangeListener(new SystemGlobalsListener(),
                        SystemGlobals.getValue(ConfigKeys.INSTALLATION_CONFIG), fileChangesDelay);
            }
            Configuration.setDefaultConfiguration(templateCfg);
        } catch (Exception e) {
            new ForumException(e);
        }
    }

    /**
     * Loads modules mapping file
     */
    protected void loadModulesMapping(String baseDir) throws IOException {
        modulesMapping = new Properties();
        modulesMapping.load(new FileInputStream(baseDir + "/config/modulesMapping.properties"));
    }

    protected void loadUrlPatterns() throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream(SystemGlobals.getApplicationResourceDir()
                + "/config/urlPattern.properties"));

        Iterator iter = p.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            ActionServletRequest.addUrlPattern(entry.getKey().toString(), entry.getValue()
                    .toString());
        }
    }

    protected void loadConfigStuff() throws Exception {
        this.loadUrlPatterns();
        I18n.load();

        // BB Code
        BBCodeRepository.setBBCollection(new BBCodeHandler().parse());
    }

    protected String getModuleClass(String moduleName) {
        return modulesMapping.getProperty(moduleName);
    }

    /**
     * Gets a cookie by its name.
     * 
     * @param name
     *            The cookie name to retrieve
     * @return The <code>Cookie</code> object if found, or <code>null</code> oterwhise
     */
    public static Cookie getCookie(String name) {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];

                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }

        return null;
    }

    /**
     * Add or update a cookie. This method adds a cookie, serializing its value using XML.
     * 
     * @param name
     *            The cookie name.
     * @param value
     *            The cookie value
     */
    public static void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(3600 * 24 * 365);
        cookie.setPath("/");

        getResponse().addCookie(cookie);
    }
    
    public static void setThreadLocalData(DataHolder dataHolder)
    {
    	localData.set(dataHolder);
    }

    /**
     * Request information data holder. Stores information/data like the user request and response,
     * his database connection and any other kind of data needed.
     */
    public static class DataHolder {
        /**
         * Database connection
         */
        private Connection conn;

        /**
         * The request
         */
        private ActionServletRequest request;

        /**
         * The response
         */
        private HttpServletResponse response;

        /**
         * The template engine context. All is put here.
         */
        private SimpleHash context = new SimpleHash(ObjectWrapper.BEANS_WRAPPER);

        /**
         * If some redirect is needed, the url is here
         */
        private String redirectTo;

        // Setters
        public void setConnection(Connection conn) {
            this.conn = conn;
        }

        public void setRequest(ActionServletRequest request) {
            this.request = request;
        }

        public void setResponse(HttpServletResponse response) {
            this.response = response;
        }

        public void setContext(SimpleHash context) {
            this.context = context;
        }

        public void setRedirectTo(String redirectTo) {
            this.redirectTo = redirectTo;
        }

        // Getters
        public Connection getConnection() {
            return this.conn;
        }

        public ActionServletRequest getRequest() {
            return this.request;
        }

        public HttpServletResponse getResponse() {
            return this.response;
        }

        public SimpleHash getContext() {
            return this.context;
        }

        public String getRedirectTo() {
            return this.redirectTo;
        }
    }

    /**
     * Gets the current thread's connection
     * 
     * @return
     */
    public static Connection getConnection() {
        return ((DataHolder) localData.get()).getConnection();
    }

    /**
     * Gets the current thread's request
     * 
     * @return
     */
    public static ActionServletRequest getRequest() {
        return ((DataHolder) localData.get()).getRequest();
    }

    /**
     * Gets the current thread's response
     * 
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ((DataHolder) localData.get()).getResponse();
    }

    /**
     * Gets the current thread's template context
     * 
     * @return
     */
    public static SimpleHash getContext() {
        return ((DataHolder) localData.get()).getContext();
    }

    /**
     * Gets the current thread's <code>DataHolder</code> instance
     * 
     * @return
     */
    public static void setRedirect(String redirect) {
        ((DataHolder) localData.get()).setRedirectTo(redirect);
    }
}