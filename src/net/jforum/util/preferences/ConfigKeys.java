/*
 * Created on May 29, 2004 by pieter
 *
 */
package net.jforum.util.preferences;

/**
 * @author pieter
 *
 * Encapsulate all configuration keys in constants. This is more typesafe and provides
 * a nice overview of all configuration keys. Last but not least this lets us autocomplete
 * configuration keys under eclipse ;-)
 */

public class ConfigKeys {
    
	// Configuration values for the keys in this section are set by the web-application itself
    public static final String APPLICATION_PATH = "application.path";
    public static final String INSTALLATION = "installation";

    // The installation config file contains keys that are installation specific and differ from
    // the default config.
    public static final String INSTALLATION_CONFIG = "installation.config";

    // The remaining keys are set in default.config and can be overriden in the installation config 
    public static final String RESOURCE_DIR = "resource.dir";
    public static final String CONFIG_DIR = "config.dir";
    public static final String DATABASE_PROPERTIES = "database.properties";
    public static final String DATABASE_DRIVER_PROPERTIES = "database.driver.properties";
    public static final String GENERIC_SQL_QUERIES = "generic.sql.queries";
    public static final String SQL_FILE = "sql.file";
    
    public static final String ENCODING = "encoding";
    public static final String SERVLET_NAME = "servlet.name";	// transient!
    public static final String SERVLET_EXTENSION = "servlet.extension";
    public static final String COOKIE_NAME_DATA = "cookie.name.data";
    public static final String COOKIE_NAME_USER = "cookie.name.user";
    public static final String ANONYMOUS_USER_ID = "anonymous.userId";
    public static final String DEFAULT_USER_GROUP = "defaultUserGroup";
    
    public static final String VERSION = "version";
    
    public static final String FORUM_LINK = "forum.link";
    public static final String FORUM_PAGE_TITLE = "forum.page.title";
    public static final String FORUM_PAGE_METATAG_KEYWORDS = "forum.page.metatag.keywords";
    public static final String FORUM_PAGE_METATAG_DESCRIPTION = "forum.page.metatag.description";
    
    public static final String TMP_DIR = "tmp.dir";
    public static final String CACHE_DIR = "cache.dir";
    
    public static final String DAO_DRIVER = "dao.driver";
    
    public static final String DATE_TIME_FORMAT = "dateTime.format";
    
    public static final String TOPICS_PER_PAGE = "topicsPerPage";
    public static final String POST_PER_PAGE = "postPerPage";
    
    public static final String RSS_FILENAME_FORUM = "rss.filename.forum";
    public static final String RSS_FILENAME_TOPIC = "rss.filename.topic";
    public static final String RSS_TOPICS = "rss.topics";
    
    public static final String I18N_DEFAULT = "i18n.default";
    
    public static final String MAIL_LOST_PASSWORD_MESSAGE_FILE = "mail.lostPassword.messageFile";
    public static final String MAIL_NOTIFY_ANSWERS = "mail.notify.answers";
    public static final String MAIL_SENDER = "mail.sender";
    public static final String MAIL_CHARSET = "mail.charset";
    public static final String MAIL_NEW_ANSWER_MESSAGE_FILE = "mail.newAnswer.messageFile";
    public static final String MAIL_NEW_ANSWER_SUBJECT = "mail.newAnswer.subject";
    public static final String MAIL_MESSSAGE_FORMAT = "mail.messageFormat";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_USERNAME = "mail.smtp.username";
    public static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";

    public static final String SMILIE_IMAGE_DIR = "smilie.image.dir";
    public static final String SMILIE_IMAGE_PATTERN = "smilie.image.pattern";
    
    public static final String AVATAR_MAX_WIDTH = "avatar.maxWidth";
    public static final String AVATAR_MAX_HEIGHT = "avatar.maxHeight";
    
    private ConfigKeys() {
    }
}
