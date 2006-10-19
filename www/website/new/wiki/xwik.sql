-- MySQL dump 9.11
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	4.0.20-standard

--
-- Table structure for table `xwikiattachment`
--

CREATE TABLE xwikiattachment (
  XWA_ID bigint(20) NOT NULL default '0',
  XWA_DOC_ID bigint(20) default NULL,
  XWA_FILENAME varchar(255) NOT NULL default '',
  XWA_SIZE int(11) default NULL,
  XWA_DATE datetime NOT NULL default '0000-00-00 00:00:00',
  XWA_AUTHOR varchar(255) default NULL,
  XWA_VERSION varchar(255) NOT NULL default '',
  XWA_COMMENT varchar(255) default NULL,
  PRIMARY KEY  (XWA_ID)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikiattachment`
--


--
-- Table structure for table `xwikiattachment_archive`
--

CREATE TABLE xwikiattachment_archive (
  XWA_ID bigint(20) NOT NULL default '0',
  XWA_ARCHIVE mediumblob,
  PRIMARY KEY  (XWA_ID)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikiattachment_archive`
--


--
-- Table structure for table `xwikiattachment_content`
--

CREATE TABLE xwikiattachment_content (
  XWA_ID bigint(20) NOT NULL default '0',
  XWA_CONTENT mediumblob NOT NULL,
  PRIMARY KEY  (XWA_ID)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikiattachment_content`
--


--
-- Table structure for table `xwikibooleanclasses`
--

CREATE TABLE xwikibooleanclasses (
  XWN_ID int(11) NOT NULL default '0',
  XWN_NAME varchar(255) NOT NULL default '',
  XWN_DISPLAYTYPE varchar(20) default NULL,
  PRIMARY KEY  (XWN_ID,XWN_NAME),
  KEY XWN_ID (XWN_ID,XWN_NAME),
  KEY XWN_ID_2 (XWN_ID,XWN_NAME),
  KEY XWN_ID_3 (XWN_ID,XWN_NAME),
  KEY XWN_ID_4 (XWN_ID,XWN_NAME),
  KEY XWN_ID_5 (XWN_ID,XWN_NAME),
  KEY XWN_ID_6 (XWN_ID,XWN_NAME),
  KEY XWN_ID_7 (XWN_ID,XWN_NAME),
  KEY XWN_ID_8 (XWN_ID,XWN_NAME),
  KEY XWN_ID_9 (XWN_ID,XWN_NAME),
  KEY XWN_ID_10 (XWN_ID,XWN_NAME),
  KEY XWN_ID_11 (XWN_ID,XWN_NAME),
  KEY XWN_ID_12 (XWN_ID,XWN_NAME),
  KEY XWN_ID_13 (XWN_ID,XWN_NAME),
  KEY XWN_ID_14 (XWN_ID,XWN_NAME),
  KEY XWN_ID_15 (XWN_ID,XWN_NAME),
  KEY XWN_ID_16 (XWN_ID,XWN_NAME),
  KEY XWN_ID_17 (XWN_ID,XWN_NAME),
  KEY XWN_ID_18 (XWN_ID,XWN_NAME),
  KEY XWN_ID_19 (XWN_ID,XWN_NAME),
  KEY XWN_ID_20 (XWN_ID,XWN_NAME),
  KEY XWN_ID_21 (XWN_ID,XWN_NAME),
  KEY XWN_ID_22 (XWN_ID,XWN_NAME),
  KEY XWN_ID_23 (XWN_ID,XWN_NAME),
  KEY XWN_ID_24 (XWN_ID,XWN_NAME),
  KEY XWN_ID_25 (XWN_ID,XWN_NAME),
  KEY XWN_ID_26 (XWN_ID,XWN_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikibooleanclasses`
--

INSERT INTO xwikibooleanclasses VALUES (495778886,'active','yesno');

--
-- Table structure for table `xwikiclasses`
--

CREATE TABLE xwikiclasses (
  XWO_ID int(11) NOT NULL default '0',
  XWO_NAME varchar(255) NOT NULL default '',
  PRIMARY KEY  (XWO_ID)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikiclasses`
--

INSERT INTO xwikiclasses VALUES (104408758,'XWiki.XWikiPreferences');
INSERT INTO xwikiclasses VALUES (495778886,'XWiki.XWikiUsers');
INSERT INTO xwikiclasses VALUES (2082812758,'XWiki.XWikiGroups');
INSERT INTO xwikiclasses VALUES (-1905796263,'XWiki.XWikiRights');
INSERT INTO xwikiclasses VALUES (981637980,'XWiki.XWikiGlobalRights');
INSERT INTO xwikiclasses VALUES (-980492554,'XWiki.XWikiComments');
INSERT INTO xwikiclasses VALUES (493697236,'XWiki.XWikiSkins');

--
-- Table structure for table `xwikiclassesprop`
--

CREATE TABLE xwikiclassesprop (
  XWP_ID int(11) NOT NULL default '0',
  XWP_NAME varchar(255) NOT NULL default '',
  XWP_PRETTYNAME varchar(255) default NULL,
  XWP_CLASSTYPE varchar(255) default NULL,
  XWP_UNMODIFIABLE tinyint(1) default NULL,
  XWP_NUMBER int(11) default NULL,
  PRIMARY KEY  (XWP_ID,XWP_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikiclassesprop`
--

INSERT INTO xwikiclassesprop VALUES (104408758,'admin_email','Admin eMail','com.xpn.xwiki.objects.classes.StringClass',0,14);
INSERT INTO xwikiclassesprop VALUES (104408758,'confirmation_email_content','Confirmation eMail Content','com.xpn.xwiki.objects.classes.TextAreaClass',0,16);
INSERT INTO xwikiclassesprop VALUES (104408758,'skin','Skin','com.xpn.xwiki.objects.classes.StringClass',0,2);
INSERT INTO xwikiclassesprop VALUES (104408758,'title','Title','com.xpn.xwiki.objects.classes.StringClass',0,10);
INSERT INTO xwikiclassesprop VALUES (104408758,'language','Language','com.xpn.xwiki.objects.classes.StringClass',0,1);
INSERT INTO xwikiclassesprop VALUES (104408758,'meta','HTTP Meta Info','com.xpn.xwiki.objects.classes.TextAreaClass',0,9);
INSERT INTO xwikiclassesprop VALUES (104408758,'version','Version','com.xpn.xwiki.objects.classes.StringClass',0,12);
INSERT INTO xwikiclassesprop VALUES (104408758,'editbox_width','Editbox Width','com.xpn.xwiki.objects.classes.NumberClass',0,3);
INSERT INTO xwikiclassesprop VALUES (104408758,'webbgcolor','Web Background Color','com.xpn.xwiki.objects.classes.StringClass',0,2);
INSERT INTO xwikiclassesprop VALUES (104408758,'webcopyright','Web Copyright','com.xpn.xwiki.objects.classes.StringClass',0,5);
INSERT INTO xwikiclassesprop VALUES (104408758,'smtp_server','SMTP Server','com.xpn.xwiki.objects.classes.StringClass',0,13);
INSERT INTO xwikiclassesprop VALUES (104408758,'editbox_height','Editbox Height','com.xpn.xwiki.objects.classes.NumberClass',0,4);
INSERT INTO xwikiclassesprop VALUES (104408758,'plugins','Plugins','com.xpn.xwiki.objects.classes.StringClass',0,6);
INSERT INTO xwikiclassesprop VALUES (104408758,'validation_email_content','Validation eMail Content','com.xpn.xwiki.objects.classes.TextAreaClass',0,15);
INSERT INTO xwikiclassesprop VALUES (104408758,'menu','Menu','com.xpn.xwiki.objects.classes.TextAreaClass',0,8);
INSERT INTO xwikiclassesprop VALUES (104408758,'authenticate_edit','Authenticate On Edit','com.xpn.xwiki.objects.classes.StringClass',0,7);
INSERT INTO xwikiclassesprop VALUES (495778886,'password','Password','com.xpn.xwiki.objects.classes.PasswordClass',0,1);
INSERT INTO xwikiclassesprop VALUES (495778886,'active','Active','com.xpn.xwiki.objects.classes.BooleanClass',0,1);
INSERT INTO xwikiclassesprop VALUES (495778886,'email','e-Mail','com.xpn.xwiki.objects.classes.StringClass',0,2);
INSERT INTO xwikiclassesprop VALUES (495778886,'comment','Comment','com.xpn.xwiki.objects.classes.TextAreaClass',0,3);
INSERT INTO xwikiclassesprop VALUES (495778886,'default_language','Default Language','com.xpn.xwiki.objects.classes.StringClass',0,8);
INSERT INTO xwikiclassesprop VALUES (495778886,'validkey','Validation Key','com.xpn.xwiki.objects.classes.StringClass',0,7);
INSERT INTO xwikiclassesprop VALUES (495778886,'last_name','Last Name','com.xpn.xwiki.objects.classes.StringClass',0,5);
INSERT INTO xwikiclassesprop VALUES (495778886,'fullname','Full Name','com.xpn.xwiki.objects.classes.StringClass',0,6);
INSERT INTO xwikiclassesprop VALUES (495778886,'first_name','First Name','com.xpn.xwiki.objects.classes.StringClass',0,4);
INSERT INTO xwikiclassesprop VALUES (2082812758,'member','Member','com.xpn.xwiki.objects.classes.StringClass',0,1);
INSERT INTO xwikiclassesprop VALUES (-1905796263,'groups','Groups','com.xpn.xwiki.objects.classes.StringClass',0,1);
INSERT INTO xwikiclassesprop VALUES (-1905796263,'users','Users','com.xpn.xwiki.objects.classes.StringClass',0,3);
INSERT INTO xwikiclassesprop VALUES (-1905796263,'levels','Access Levels','com.xpn.xwiki.objects.classes.StringClass',0,2);
INSERT INTO xwikiclassesprop VALUES (-1905796263,'allow','Allow/Deny','com.xpn.xwiki.objects.classes.NumberClass',0,4);
INSERT INTO xwikiclassesprop VALUES (981637980,'groups','Groups','com.xpn.xwiki.objects.classes.StringClass',0,1);
INSERT INTO xwikiclassesprop VALUES (981637980,'users','Users','com.xpn.xwiki.objects.classes.StringClass',0,3);
INSERT INTO xwikiclassesprop VALUES (981637980,'levels','Access Levels','com.xpn.xwiki.objects.classes.StringClass',0,2);
INSERT INTO xwikiclassesprop VALUES (981637980,'allow','Allow/Deny','com.xpn.xwiki.objects.classes.NumberClass',0,4);
INSERT INTO xwikiclassesprop VALUES (-980492554,'highlight','Highlighted Text','com.xpn.xwiki.objects.classes.TextAreaClass',0,2);
INSERT INTO xwikiclassesprop VALUES (-980492554,'comment','Comment','com.xpn.xwiki.objects.classes.TextAreaClass',0,4);
INSERT INTO xwikiclassesprop VALUES (-980492554,'date','Date','com.xpn.xwiki.objects.classes.DateClass',0,3);
INSERT INTO xwikiclassesprop VALUES (-980492554,'author','Author','com.xpn.xwiki.objects.classes.StringClass',0,1);
INSERT INTO xwikiclassesprop VALUES (493697236,'header.vm','Header','com.xpn.xwiki.objects.classes.TextAreaClass',0,3);
INSERT INTO xwikiclassesprop VALUES (493697236,'footer.vm','Footer','com.xpn.xwiki.objects.classes.TextAreaClass',0,4);
INSERT INTO xwikiclassesprop VALUES (493697236,'name','Name','com.xpn.xwiki.objects.classes.StringClass',0,1);
INSERT INTO xwikiclassesprop VALUES (493697236,'style.css','style.css','com.xpn.xwiki.objects.classes.TextAreaClass',0,2);

--
-- Table structure for table `xwikidateclasses`
--

CREATE TABLE xwikidateclasses (
  XWS_ID int(11) NOT NULL default '0',
  XWS_NAME varchar(255) NOT NULL default '',
  XWS_SIZE int(11) default NULL,
  XWS_EMPTY_IS_TODAY int(11) default NULL,
  XWS_DATE_FORMAT varchar(255) default NULL,
  PRIMARY KEY  (XWS_ID,XWS_NAME),
  KEY XWS_ID (XWS_ID,XWS_NAME),
  KEY XWS_ID_2 (XWS_ID,XWS_NAME),
  KEY XWS_ID_3 (XWS_ID,XWS_NAME),
  KEY XWS_ID_4 (XWS_ID,XWS_NAME),
  KEY XWS_ID_5 (XWS_ID,XWS_NAME),
  KEY XWS_ID_6 (XWS_ID,XWS_NAME),
  KEY XWS_ID_7 (XWS_ID,XWS_NAME),
  KEY XWS_ID_8 (XWS_ID,XWS_NAME),
  KEY XWS_ID_9 (XWS_ID,XWS_NAME),
  KEY XWS_ID_10 (XWS_ID,XWS_NAME),
  KEY XWS_ID_11 (XWS_ID,XWS_NAME),
  KEY XWS_ID_12 (XWS_ID,XWS_NAME),
  KEY XWS_ID_13 (XWS_ID,XWS_NAME),
  KEY XWS_ID_14 (XWS_ID,XWS_NAME),
  KEY XWS_ID_15 (XWS_ID,XWS_NAME),
  KEY XWS_ID_16 (XWS_ID,XWS_NAME),
  KEY XWS_ID_17 (XWS_ID,XWS_NAME),
  KEY XWS_ID_18 (XWS_ID,XWS_NAME),
  KEY XWS_ID_19 (XWS_ID,XWS_NAME),
  KEY XWS_ID_20 (XWS_ID,XWS_NAME),
  KEY XWS_ID_21 (XWS_ID,XWS_NAME),
  KEY XWS_ID_22 (XWS_ID,XWS_NAME),
  KEY XWS_ID_23 (XWS_ID,XWS_NAME),
  KEY XWS_ID_24 (XWS_ID,XWS_NAME),
  KEY XWS_ID_25 (XWS_ID,XWS_NAME),
  KEY XWS_ID_26 (XWS_ID,XWS_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikidateclasses`
--

INSERT INTO xwikidateclasses VALUES (-980492554,'date',20,1,'dd/MM/yyyy HH:mm:ss');

--
-- Table structure for table `xwikidates`
--

CREATE TABLE xwikidates (
  XWS_ID int(11) NOT NULL default '0',
  XWS_NAME varchar(255) NOT NULL default '',
  XWS_VALUE datetime default NULL,
  PRIMARY KEY  (XWS_ID,XWS_NAME),
  KEY XWS_ID (XWS_ID,XWS_NAME),
  KEY XWS_ID_2 (XWS_ID,XWS_NAME),
  KEY XWS_ID_3 (XWS_ID,XWS_NAME),
  KEY XWS_ID_4 (XWS_ID,XWS_NAME),
  KEY XWS_ID_5 (XWS_ID,XWS_NAME),
  KEY XWS_ID_6 (XWS_ID,XWS_NAME),
  KEY XWS_ID_7 (XWS_ID,XWS_NAME),
  KEY XWS_ID_8 (XWS_ID,XWS_NAME),
  KEY XWS_ID_9 (XWS_ID,XWS_NAME),
  KEY XWS_ID_10 (XWS_ID,XWS_NAME),
  KEY XWS_ID_11 (XWS_ID,XWS_NAME),
  KEY XWS_ID_12 (XWS_ID,XWS_NAME),
  KEY XWS_ID_13 (XWS_ID,XWS_NAME),
  KEY XWS_ID_14 (XWS_ID,XWS_NAME),
  KEY XWS_ID_15 (XWS_ID,XWS_NAME),
  KEY XWS_ID_16 (XWS_ID,XWS_NAME),
  KEY XWS_ID_17 (XWS_ID,XWS_NAME),
  KEY XWS_ID_18 (XWS_ID,XWS_NAME),
  KEY XWS_ID_19 (XWS_ID,XWS_NAME),
  KEY XWS_ID_20 (XWS_ID,XWS_NAME),
  KEY XWS_ID_21 (XWS_ID,XWS_NAME),
  KEY XWS_ID_22 (XWS_ID,XWS_NAME),
  KEY XWS_ID_23 (XWS_ID,XWS_NAME),
  KEY XWS_ID_24 (XWS_ID,XWS_NAME),
  KEY XWS_ID_25 (XWS_ID,XWS_NAME),
  KEY XWS_ID_26 (XWS_ID,XWS_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikidates`
--


--
-- Table structure for table `xwikidblistclasses`
--

CREATE TABLE xwikidblistclasses (
  XWL_ID int(11) NOT NULL default '0',
  XWL_NAME varchar(255) NOT NULL default '',
  XWL_DISPLAYTYPE varchar(20) default NULL,
  XWL_MULTISELECT tinyint(1) default NULL,
  XWL_SIZE int(11) default NULL,
  XWL_RELATIONAL tinyint(1) default NULL,
  XWL_SQL text,
  PRIMARY KEY  (XWL_ID,XWL_NAME),
  KEY XWL_ID (XWL_ID,XWL_NAME),
  KEY XWL_ID_2 (XWL_ID,XWL_NAME),
  KEY XWL_ID_3 (XWL_ID,XWL_NAME),
  KEY XWL_ID_4 (XWL_ID,XWL_NAME),
  KEY XWL_ID_5 (XWL_ID,XWL_NAME),
  KEY XWL_ID_6 (XWL_ID,XWL_NAME),
  KEY XWL_ID_7 (XWL_ID,XWL_NAME),
  KEY XWL_ID_8 (XWL_ID,XWL_NAME),
  KEY XWL_ID_9 (XWL_ID,XWL_NAME),
  KEY XWL_ID_10 (XWL_ID,XWL_NAME),
  KEY XWL_ID_11 (XWL_ID,XWL_NAME),
  KEY XWL_ID_12 (XWL_ID,XWL_NAME),
  KEY XWL_ID_13 (XWL_ID,XWL_NAME),
  KEY XWL_ID_14 (XWL_ID,XWL_NAME),
  KEY XWL_ID_15 (XWL_ID,XWL_NAME),
  KEY XWL_ID_16 (XWL_ID,XWL_NAME),
  KEY XWL_ID_17 (XWL_ID,XWL_NAME),
  KEY XWL_ID_18 (XWL_ID,XWL_NAME),
  KEY XWL_ID_19 (XWL_ID,XWL_NAME),
  KEY XWL_ID_20 (XWL_ID,XWL_NAME),
  KEY XWL_ID_21 (XWL_ID,XWL_NAME),
  KEY XWL_ID_22 (XWL_ID,XWL_NAME),
  KEY XWL_ID_23 (XWL_ID,XWL_NAME),
  KEY XWL_ID_24 (XWL_ID,XWL_NAME),
  KEY XWL_ID_25 (XWL_ID,XWL_NAME),
  KEY XWL_ID_26 (XWL_ID,XWL_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikidblistclasses`
--


--
-- Table structure for table `xwikidoc`
--

CREATE TABLE xwikidoc (
  XWD_ID bigint(20) NOT NULL default '0',
  XWD_NAME varchar(255) NOT NULL default '',
  XWD_LANGUAGE varchar(5) default NULL,
  XWD_DEFAULT_LANGUAGE varchar(5) default NULL,
  XWD_TRANSLATION int(11) NOT NULL default '0',
  XWD_DATE datetime NOT NULL default '0000-00-00 00:00:00',
  XWD_CREATION_DATE datetime default NULL,
  XWD_AUTHOR varchar(255) default NULL,
  XWD_CREATOR varchar(255) default NULL,
  XWD_WEB varchar(255) NOT NULL default '',
  XWD_CONTENT mediumtext NOT NULL,
  XWD_ARCHIVE mediumtext NOT NULL,
  XWD_VERSION varchar(255) NOT NULL default '',
  XWD_PARENT text,
  PRIMARY KEY  (XWD_ID)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikidoc`
--

INSERT INTO xwikidoc VALUES (1807025832,'WebHome','fr','',1,'2004-08-20 13:31:59','2004-08-20 13:18:22','XWiki.Admin','XWiki.Admin','Main','1 Bienvenue sur votre Wiki\r\n\r\nCe site est votre site. Avec votre Wiki vous pouvez:\r\n\r\n   * Créer un site professionnel ou personnel\r\n   * Partager de la connaissance sur un Intranet\r\n   * Developper des applications dynamiques\r\n\r\nPour plus d\'informations allez voir la [documentation>Doc.WebHome]. Pour des demonstrations de fonctions avancés allez voir [la zone de developpement de XWiki>http://www.xwiki.org/xwiki/bin/view/Dev/WebHome]\r\n','head	1.4;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.4\ndate	2004.08.20.13.31.59;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.13.31.12;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.13.18.22;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.4\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>WebHome</name>\n<language>fr</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1093000702000</creationDate>\n<date>1093001519046</date>\n<version>1.4</version>\n<content>1 Bienvenue sur votre Wiki\n\nCe site est votre site. Avec votre Wiki vous pouvez:\n\n   * Créer un site professionnel ou personnel\n   * Partager de la connaissance sur un Intranet\n   * Developper des applications dynamiques\n\nPour plus d\'informations allez voir la [documentation&gt;Doc.WebHome]. Pour des demonstrations de fonctions avancés allez voir [la zone de developpement de XWiki&gt;http://www.xwiki.org/xwiki/bin/view/Dev/WebHome]\n</content>\n</xwikidoc>\n@\n\n\n1.3\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1093001472993</date>\n<version>1.3</version>\nd18 3\na20 3\n   * Créer un site professionel ou personal\n   * Share content on an Intranet\n   * Build dynamic web applications\n@\n\n\n1.2\nlog\n@Main.WebHome\n@\ntext\n@d11 3\na13 3\n<creationDate>1093000702671</creationDate>\n<date>1093000702672</date>\n<version>1.2</version>\nd18 3\na20 3\n   * Créer votre site personnel\n   * Partager du contenu avec vos amis\n   * Créer des zones d\'expressions libres avec les internautes\nd22 1\na22 1\nPour plus d\'informations allez voir la [documentation&gt;Doc.WebHome]\n@\n','1.4','');
INSERT INTO xwikidoc VALUES (1807025797,'WebHome','en','',1,'2004-08-20 12:06:47','2004-08-20 11:33:01','XWiki.Admin','','Main','1 Welcome on your Wiki\r\n\r\nThis web site is your web site. With your wiki you can:\r\n\r\n   * Create your personal web site\r\n   * Share content with your friends\r\n   * Create free content zones with all Internet users\r\n\r\nFor more information, check out the [documentation>Doc.WebHome]\r\n','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.06.47;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.01;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>WebHome</name>\n<language>en</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994381574</creationDate>\n<date>1092996407026</date>\n<version>1.3</version>\n<content>1 Welcome on your Wiki\n\nThis web site is your web site. With your wiki you can:\n\n   * Create your personal web site\n   * Share content with your friends\n   * Create free content zones with all Internet users\n\nFor more information, check out the [documentation&gt;Doc.WebHome]\n</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@Main.WebHome\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994381574</date>\n<version>1.2</version>\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (104408758,'XWikiPreferences','','fr',0,'2004-08-20 12:18:50','2004-08-20 11:33:01','XWiki.Admin','','XWiki','#includeForm(\"XWiki.XWikiPreferencesTemplate\")','head	1.7;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.7\ndate	2004.08.20.12.18.50;	author tomcat;	state Exp;\nbranches;\nnext	1.6;\n\n1.6\ndate	2004.08.20.12.07.36;	author tomcat;	state Exp;\nbranches;\nnext	1.5;\n\n1.5\ndate	2004.08.20.11.56.13;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.11.40.22;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.11.33.03;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.01;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.7\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiPreferences</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.WebHome</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994381769</creationDate>\n<date>1092997130759</date>\n<version>1.7</version>\n<class>\n<name>XWiki.XWikiPreferences</name>\n<admin_email>\n<prettyName>Admin eMail</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>admin_email</name>\n<number>14</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</admin_email>\n<confirmation_email_content>\n<prettyName>Confirmation eMail Content</prettyName>\n<rows>10</rows>\n<unmodifiable>0</unmodifiable>\n<size>72</size>\n<name>confirmation_email_content</name>\n<number>16</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</confirmation_email_content>\n<skin>\n<prettyName>Skin</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>20</size>\n<name>skin</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</skin>\n<title>\n<prettyName>Title</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>title</name>\n<number>10</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</title>\n<language>\n<prettyName>Language</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>language</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</language>\n<meta>\n<prettyName>HTTP Meta Info</prettyName>\n<rows>5</rows>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>meta</name>\n<number>9</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</meta>\n<version>\n<prettyName>Version</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>version</name>\n<number>12</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</version>\n<editbox_width>\n<numberType>long</numberType>\n<prettyName>Editbox Width</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>editbox_width</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</editbox_width>\n<webbgcolor>\n<prettyName>Web Background Color</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>webbgcolor</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</webbgcolor>\n<webcopyright>\n<prettyName>Web Copyright</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>60</size>\n<name>webcopyright</name>\n<number>5</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</webcopyright>\n<smtp_server>\n<prettyName>SMTP Server</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>smtp_server</name>\n<number>13</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</smtp_server>\n<editbox_height>\n<numberType>long</numberType>\n<prettyName>Editbox Height</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>editbox_height</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</editbox_height>\n<plugins>\n<prettyName>Plugins</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>plugins</name>\n<number>6</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</plugins>\n<validation_email_content>\n<prettyName>Validation eMail Content</prettyName>\n<rows>10</rows>\n<unmodifiable>0</unmodifiable>\n<size>72</size>\n<name>validation_email_content</name>\n<number>15</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</validation_email_content>\n<menu>\n<prettyName>Menu</prettyName>\n<rows>8</rows>\n<unmodifiable>0</unmodifiable>\n<size>60</size>\n<name>menu</name>\n<number>8</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</menu>\n<authenticate_edit>\n<prettyName>Authenticate On Edit</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>authenticate_edit</name>\n<number>7</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</authenticate_edit>\n</class>\n<object>\n<class>\n<name>XWiki.XWikiPreferences</name>\n<admin_email>\n<prettyName>Admin eMail</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>admin_email</name>\n<number>14</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</admin_email>\n<confirmation_email_content>\n<prettyName>Confirmation eMail Content</prettyName>\n<rows>10</rows>\n<unmodifiable>0</unmodifiable>\n<size>72</size>\n<name>confirmation_email_content</name>\n<number>16</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</confirmation_email_content>\n<skin>\n<prettyName>Skin</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>20</size>\n<name>skin</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</skin>\n<title>\n<prettyName>Title</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>title</name>\n<number>10</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</title>\n<language>\n<prettyName>Language</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>language</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</language>\n<meta>\n<prettyName>HTTP Meta Info</prettyName>\n<rows>5</rows>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>meta</name>\n<number>9</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</meta>\n<version>\n<prettyName>Version</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>version</name>\n<number>12</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</version>\n<editbox_width>\n<numberType>long</numberType>\n<prettyName>Editbox Width</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>editbox_width</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</editbox_width>\n<webbgcolor>\n<prettyName>Web Background Color</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>webbgcolor</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</webbgcolor>\n<webcopyright>\n<prettyName>Web Copyright</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>60</size>\n<name>webcopyright</name>\n<number>5</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</webcopyright>\n<smtp_server>\n<prettyName>SMTP Server</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>smtp_server</name>\n<number>13</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</smtp_server>\n<editbox_height>\n<numberType>long</numberType>\n<prettyName>Editbox Height</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>editbox_height</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</editbox_height>\n<plugins>\n<prettyName>Plugins</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>plugins</name>\n<number>6</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</plugins>\n<validation_email_content>\n<prettyName>Validation eMail Content</prettyName>\n<rows>10</rows>\n<unmodifiable>0</unmodifiable>\n<size>72</size>\n<name>validation_email_content</name>\n<number>15</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</validation_email_content>\n<menu>\n<prettyName>Menu</prettyName>\n<rows>8</rows>\n<unmodifiable>0</unmodifiable>\n<size>60</size>\n<name>menu</name>\n<number>8</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</menu>\n<authenticate_edit>\n<prettyName>Authenticate On Edit</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>authenticate_edit</name>\n<number>7</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</authenticate_edit>\n</class>\n<name>XWiki.XWikiPreferences</name>\n<number>0</number>\n<className>XWiki.XWikiPreferences</className>\n<property>\n<admin_email>webmaster@@xwiki.com</admin_email>\n</property>\n<property>\n<confirmation_email_content></confirmation_email_content>\n</property>\n<property>\n<skin>default</skin>\n</property>\n<property>\n<title>Wiki - $doc.web - $doc.name</title>\n</property>\n<property>\n<language>fr</language>\n</property>\n<property>\n<meta>&lt;meta name=\"revisit-after\" content=\"7 days\" /&gt;\n&lt;meta name=\"description\" content=\"Wiki personel\" /&gt;\n&lt;meta name=\"keywords\" content=\"wiki\" /&gt;\n&lt;meta name=\"distribution\" content=\"GLOBAL\" /&gt;\n&lt;meta name=\"rating\" content=\"General\" /&gt;\n&lt;meta name=\"copyright\" content=\"Copyright (c) 2004 Auteurs\" /&gt;\n&lt;meta name=\"author\" content=\"\" /&gt;\n&lt;meta http-equiv=\"reply-to\" content=\"\" /&gt;\n&lt;meta name=\"language\" content=\"fr\" /&gt;\n&lt;meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" /&gt;</meta>\n</property>\n<property>\n<version></version>\n</property>\n<property>\n<editbox_width>80</editbox_width>\n</property>\n<property>\n<webcopyright>Copyright 2004 (c) Auteurs des pages</webcopyright>\n</property>\n<property>\n<webbgcolor></webbgcolor>\n</property>\n<property>\n<smtp_server>127.0.0.1</smtp_server>\n</property>\n<property>\n<editbox_height>20</editbox_height>\n</property>\n<property>\n<plugins></plugins>\n</property>\n<property>\n<menu>&lt;a href=\"../../view/Main/WebHome\"&gt;Wiki&lt;/a&gt;&lt;span&gt; | &lt;/span&gt; \n&lt;a href=\"../../view/Main/WhatsNew\"&gt;Quoi de Neuf&lt;/a&gt;&lt;span&gt; | &lt;/span&gt; \n&lt;a href=\"../../view/Main/WebSearch\"&gt;Recherche&lt;/a&gt;&lt;span&gt; | &lt;/span&gt; \n&lt;a href=\"../../view/Admin/WebHome\"&gt;Admin&lt;/a&gt;&lt;span&gt; | &lt;/span&gt; \n&lt;a href=\"../../view/Doc/WebHome\"&gt;Doc&lt;/a&gt;&lt;span&gt; | &lt;/span&gt;\n&lt;a href=\"../../view/Sandbox/WebHome\"&gt;Test&lt;/a&gt; \n</menu>\n</property>\n<property>\n<validation_email_content></validation_email_content>\n</property>\n<property>\n<authenticate_edit>yes</authenticate_edit>\n</property>\n</object>\n<object>\n<class>\n<name>XWiki.XWikiPreferences</name>\n<admin_email>\n<prettyName>Admin eMail</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>admin_email</name>\n<number>14</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</admin_email>\n<confirmation_email_content>\n<prettyName>Confirmation eMail Content</prettyName>\n<rows>10</rows>\n<unmodifiable>0</unmodifiable>\n<size>72</size>\n<name>confirmation_email_content</name>\n<number>16</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</confirmation_email_content>\n<skin>\n<prettyName>Skin</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>20</size>\n<name>skin</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</skin>\n<title>\n<prettyName>Title</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>title</name>\n<number>10</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</title>\n<language>\n<prettyName>Language</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>language</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</language>\n<meta>\n<prettyName>HTTP Meta Info</prettyName>\n<rows>5</rows>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>meta</name>\n<number>9</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</meta>\n<version>\n<prettyName>Version</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>version</name>\n<number>12</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</version>\n<editbox_width>\n<numberType>long</numberType>\n<prettyName>Editbox Width</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>editbox_width</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</editbox_width>\n<webbgcolor>\n<prettyName>Web Background Color</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>webbgcolor</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</webbgcolor>\n<webcopyright>\n<prettyName>Web Copyright</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>60</size>\n<name>webcopyright</name>\n<number>5</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</webcopyright>\n<smtp_server>\n<prettyName>SMTP Server</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>smtp_server</name>\n<number>13</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</smtp_server>\n<editbox_height>\n<numberType>long</numberType>\n<prettyName>Editbox Height</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>editbox_height</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</editbox_height>\n<plugins>\n<prettyName>Plugins</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>plugins</name>\n<number>6</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</plugins>\n<validation_email_content>\n<prettyName>Validation eMail Content</prettyName>\n<rows>10</rows>\n<unmodifiable>0</unmodifiable>\n<size>72</size>\n<name>validation_email_content</name>\n<number>15</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</validation_email_content>\n<menu>\n<prettyName>Menu</prettyName>\n<rows>8</rows>\n<unmodifiable>0</unmodifiable>\n<size>60</size>\n<name>menu</name>\n<number>8</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</menu>\n<authenticate_edit>\n<prettyName>Authenticate On Edit</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>authenticate_edit</name>\n<number>7</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</authenticate_edit>\n</class>\n<name>XWiki.XWikiPreferences</name>\n<number>1</number>\n<className>XWiki.XWikiPreferences</className>\n<property>\n<admin_email></admin_email>\n</property>\n<property>\n<confirmation_email_content></confirmation_email_content>\n</property>\n<property>\n<skin>default</skin>\n</property>\n<property>\n<title>Wiki - $doc.web - $doc.name</title>\n</property>\n<property>\n<language>en</language>\n</property>\n<property>\n<meta>&lt;meta name=\"revisit-after\" content=\"7 days\" /&gt;\n&lt;meta name=\"description\" content=\"Wiki\" /&gt;\n&lt;meta name=\"keywords\" content=\"wiki\" /&gt;\n&lt;meta name=\"distribution\" content=\"GLOBAL\" /&gt;\n&lt;meta name=\"rating\" content=\"General\" /&gt;\n&lt;meta name=\"copyright\" content=\"Copyright (c) 2004 Contributing Authors\" /&gt;\n&lt;meta name=\"author\" content=\"\" /&gt;\n&lt;meta http-equiv=\"reply-to\" content=\"\" /&gt;\n&lt;meta name=\"language\" content=\"en\" /&gt;\n&lt;meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" /&gt;</meta>\n</property>\n<property>\n<version></version>\n</property>\n<property>\n<editbox_width></editbox_width>\n</property>\n<property>\n<webcopyright>Copyright (c) 2004 Contributing Authors</webcopyright>\n</property>\n<property>\n<webbgcolor></webbgcolor>\n</property>\n<property>\n<smtp_server></smtp_server>\n</property>\n<property>\n<editbox_height></editbox_height>\n</property>\n<property>\n<plugins></plugins>\n</property>\n<property>\n<menu>&lt;a href=\"../../view/Main/WebHome\"&gt;Wiki&lt;/a&gt;&lt;span&gt; | &lt;/span&gt; \n&lt;a href=\"../../view/Main/WhatsNew\"&gt;Whats New&lt;/a&gt;&lt;span&gt; | &lt;/span&gt; \n&lt;a href=\"../../view/Main/WebSearch\"&gt;Search&lt;/a&gt;&lt;span&gt; | &lt;/span&gt; \n&lt;a href=\"../../view/Admin/WebHome\"&gt;Admin&lt;/a&gt;&lt;span&gt; | &lt;/span&gt; \n&lt;a href=\"../../view/Doc/WebHome\"&gt;Doc&lt;/a&gt;&lt;span&gt; | &lt;/span&gt;\n&lt;a href=\"../../view/Sandbox/WebHome\"&gt;Test&lt;/a&gt; </menu>\n</property>\n<property>\n<validation_email_content></validation_email_content>\n</property>\n<property>\n<authenticate_edit>yes</authenticate_edit>\n</property>\n</object>\n<object>\n<class>\n<name>XWiki.XWikiGlobalRights</name>\n<groups>\n<prettyName>Groups</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>groups</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</groups>\n<users>\n<prettyName>Users</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>users</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</users>\n<levels>\n<prettyName>Access Levels</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>levels</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</levels>\n<allow>\n<numberType>integer</numberType>\n<prettyName>Allow/Deny</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>2</size>\n<name>allow</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</allow>\n</class>\n<name>XWiki.XWikiPreferences</name>\n<number>0</number>\n<className>XWiki.XWikiGlobalRights</className>\n<property>\n<users>XWiki.Admin</users>\n</property>\n<property>\n<levels>admin, edit</levels>\n</property>\n<property>\n<allow>1</allow>\n</property>\n<property>\n<groups>XWiki.AdminGroup</groups>\n</property>\n</object>\n<content>#includeForm(\"XWiki.XWikiPreferencesTemplate\")</content>\n</xwikidoc>\n@\n\n\n1.6\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092996456571</date>\n<version>1.6</version>\nd610 1\na610 1\n<groups></groups>\n@\n\n\n1.5\nlog\n@@\ntext\n@d10 1\na10 1\n<author>XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092995773899</date>\n<version>1.5</version>\n@\n\n\n1.4\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092994822232</date>\n<version>1.4</version>\nd601 1\na601 1\n<users>XWiki.LudovicDubost</users>\nd609 3\n@\n\n\n1.3\nlog\n@@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994383171</date>\n<version>1.3</version>\nd610 1\na610 1\n<content>#includeForm(\"xwiki:XWiki.XWikiPreferencesTemplate\")</content>\n@\n\n\n1.2\nlog\n@XWiki.XWikiPreferences\n@\ntext\n@d12 2\na13 2\n<date>1092994381771</date>\n<version>1.2</version>\nd560 50\n@\n','1.7','XWiki.WebHome');
INSERT INTO xwikidoc VALUES (495778886,'XWikiUsers','','en',0,'2004-08-20 12:09:04','2004-08-20 11:33:02','XWiki.Admin','','XWiki',' 1 XWiki Users\r\n\r\n   * [RegisterNewUser]\r\n\r\n1 Current XWiki Users\r\n\r\n#set ($sql = \", BaseObject as obj where obj.name=CONCAT(XWD_WEB,\'.\',XWD_NAME) and obj.className=\'XWiki.XWikiUsers\'\")\r\n#foreach ($item in $xwiki.searchDocuments($sql))\r\n   * [$item]\r\n#end\r\n\r\n1 Admin\r\n\r\n   * [XWikiUserTemplate]\r\n   * [XWikiUserSheet]','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.09.04;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiUsers</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.WebHome</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382274</creationDate>\n<date>1092996544900</date>\n<version>1.3</version>\n<class>\n<name>XWiki.XWikiUsers</name>\n<password>\n<prettyName>Password</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>password</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.PasswordClass</classType>\n</password>\n<active>\n<prettyName>Active</prettyName>\n<displayType>yesno</displayType>\n<unmodifiable>0</unmodifiable>\n<name>active</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.BooleanClass</classType>\n</active>\n<email>\n<prettyName>e-Mail</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>email</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</email>\n<comment>\n<prettyName>Comment</prettyName>\n<rows>5</rows>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>comment</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</comment>\n<default_language>\n<prettyName>Default Language</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>default_language</name>\n<number>8</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</default_language>\n<validkey>\n<prettyName>Validation Key</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>validkey</name>\n<number>7</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</validkey>\n<last_name>\n<prettyName>Last Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>last_name</name>\n<number>5</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</last_name>\n<fullname>\n<prettyName>Full Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>fullname</name>\n<number>6</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</fullname>\n<first_name>\n<prettyName>First Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>first_name</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</first_name>\n</class>\n<content> 1 XWiki Users\n\n   * [RegisterNewUser]\n\n1 Current XWiki Users\n\n#set ($sql = \", BaseObject as obj where obj.name=CONCAT(XWD_WEB,\'.\',XWD_NAME) and obj.className=\'XWiki.XWikiUsers\'\")\n#foreach ($item in $xwiki.searchDocuments($sql))\n   * [$item]\n#end\n\n1 Admin\n\n   * [XWikiUserTemplate]\n   * [XWikiUserSheet]</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.XWikiUsers\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382275</date>\n<version>1.2</version>\n@\n','1.3','XWiki.WebHome');
INSERT INTO xwikidoc VALUES (-643679104,'XWikiUsers','fr','',1,'2004-08-20 12:09:14','2004-08-20 11:33:02','XWiki.Admin','','XWiki',' 1 Utilisateurs\r\n\r\n   * [RegisterNewUser]\r\n\r\n1 Utilisateurs actuels\r\n\r\n#set ($sql = \", BaseObject as obj where obj.name=CONCAT(XWD_WEB,\'.\',XWD_NAME) and obj.className=\'XWiki.XWikiUsers\'\")\r\n#foreach ($item in $xwiki.searchDocuments($sql))\r\n   * [$item]\r\n#end\r\n\r\n1 Admin\r\n\r\n   * [XWikiUserTemplate]\r\n   * [XWikiUserSheet]','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.09.14;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiUsers</name>\n<language>fr</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382347</creationDate>\n<date>1092996554403</date>\n<version>1.3</version>\n<class>\n<name>XWiki.XWikiUsers</name>\n<password>\n<prettyName>Password</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>password</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.PasswordClass</classType>\n</password>\n<active>\n<prettyName>Active</prettyName>\n<displayType>yesno</displayType>\n<unmodifiable>0</unmodifiable>\n<name>active</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.BooleanClass</classType>\n</active>\n<email>\n<prettyName>e-Mail</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>email</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</email>\n<comment>\n<prettyName>Comment</prettyName>\n<rows>5</rows>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>comment</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</comment>\n<default_language>\n<prettyName>Default Language</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>default_language</name>\n<number>8</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</default_language>\n<validkey>\n<prettyName>Validation Key</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>validkey</name>\n<number>7</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</validkey>\n<last_name>\n<prettyName>Last Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>last_name</name>\n<number>5</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</last_name>\n<fullname>\n<prettyName>Full Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>fullname</name>\n<number>6</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</fullname>\n<first_name>\n<prettyName>First Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>first_name</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</first_name>\n</class>\n<content> 1 Utilisateurs\n\n   * [RegisterNewUser]\n\n1 Utilisateurs actuels\n\n#set ($sql = \", BaseObject as obj where obj.name=CONCAT(XWD_WEB,\'.\',XWD_NAME) and obj.className=\'XWiki.XWikiUsers\'\")\n#foreach ($item in $xwiki.searchDocuments($sql))\n   * [$item]\n#end\n\n1 Admin\n\n   * [XWikiUserTemplate]\n   * [XWikiUserSheet]</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.XWikiUsers\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382348</date>\n<version>1.2</version>\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (2082812758,'XWikiGroups','','fr',0,'2004-08-20 12:13:13','2004-08-20 11:33:02','XWiki.Admin','','XWiki','1 Create a new group:\r\n\r\n<form>\r\n<input type=\"text\" name=\"name\" value=\"\">\r\n<input type=\"button\" value=\"Create Group\" onClick=\"go(this.form.name.value)\">\r\n</form>\r\n\r\n<script>\r\nfunction go(value) {\r\nlocation = \"/xwiki/bin/inline/XWiki/\" + value + \"?parent=XWiki.XWikiGroups&template=XWiki.XWikiGroupTemplate\";\r\n} \r\n</script>\r\n\r\n1 Current XWiki Groups\r\n\r\n#set ($sql = \", BaseObject as obj where obj.name=CONCAT(XWD_WEB,\'.\',XWD_NAME) and obj.className=\'XWiki.XWikiGroups\'\")\r\n#foreach ($item in $xwiki.searchDocuments($sql))\r\n   * [$item]\r\n#end\r\n\r\n1 Admin\r\n\r\n   * [XWikiGroupTemplate]\r\n   * [XWikiGroupSheet]','head	1.5;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.5\ndate	2004.08.20.12.13.13;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.12.12.40;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.12.10.06;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.5\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiGroups</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.WebHome</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382415</creationDate>\n<date>1092996793605</date>\n<version>1.5</version>\n<class>\n<name>XWiki.XWikiGroups</name>\n<member>\n<prettyName>Member</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>member</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</member>\n</class>\n<content>1 Create a new group:\n\n&lt;form&gt;\n&lt;input type=\"text\" name=\"name\" value=\"\"&gt;\n&lt;input type=\"button\" value=\"Create Group\" onClick=\"go(this.form.name.value)\"&gt;\n&lt;/form&gt;\n\n&lt;script&gt;\nfunction go(value) {\nlocation = \"/xwiki/bin/inline/XWiki/\" + value + \"?parent=XWiki.XWikiGroups&amp;template=XWiki.XWikiGroupTemplate\";\n} \n&lt;/script&gt;\n\n1 Current XWiki Groups\n\n#set ($sql = \", BaseObject as obj where obj.name=CONCAT(XWD_WEB,\'.\',XWD_NAME) and obj.className=\'XWiki.XWikiGroups\'\")\n#foreach ($item in $xwiki.searchDocuments($sql))\n   * [$item]\n#end\n\n1 Admin\n\n   * [XWikiGroupTemplate]\n   * [XWikiGroupSheet]</content>\n</xwikidoc>\n@\n\n\n1.4\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092996760260</date>\n<version>1.4</version>\nd34 1\na34 1\nlocation = \"/xwiki/bin/edit/XWiki/\" + value + \"?parent=XWiki.XWikiGroups&amp;template=XWiki.XWikiGroupTemplate\";\n@\n\n\n1.3\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092996606887</date>\n<version>1.3</version>\nd47 2\na48 1\n   * [XWikiGroupTemplate]</content>\n@\n\n\n1.2\nlog\n@XWiki.XWikiGroups\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382416</date>\n<version>1.2</version>\nd25 23\na47 2\n<content>\n</content>\n@\n','1.5','XWiki.WebHome');
INSERT INTO xwikidoc VALUES (-1905796263,'XWikiRights','','fr',0,'2004-08-20 12:19:18','2004-08-20 11:33:02','XWiki.Admin','','XWiki','\n','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.19.18;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiRights</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.WebHome</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382444</creationDate>\n<date>1092997158347</date>\n<version>1.3</version>\n<class>\n<name>XWiki.XWikiRights</name>\n<groups>\n<prettyName>Groups</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>groups</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</groups>\n<users>\n<prettyName>Users</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>users</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</users>\n<levels>\n<prettyName>Access Levels</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>levels</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</levels>\n<allow>\n<numberType>integer</numberType>\n<prettyName>Allow/Deny</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>2</size>\n<name>allow</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</allow>\n</class>\n<content>\n</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.XWikiRights\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382445</date>\n<version>1.2</version>\n@\n','1.3','XWiki.WebHome');
INSERT INTO xwikidoc VALUES (981637980,'XWikiGlobalRights','','fr',0,'2004-08-20 12:19:39','2004-08-20 11:33:02','XWiki.Admin','','XWiki','\n','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.19.39;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiGlobalRights</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.WebHome</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382483</creationDate>\n<date>1092997179231</date>\n<version>1.3</version>\n<class>\n<name>XWiki.XWikiGlobalRights</name>\n<groups>\n<prettyName>Groups</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>groups</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</groups>\n<users>\n<prettyName>Users</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>users</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</users>\n<levels>\n<prettyName>Access Levels</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>levels</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</levels>\n<allow>\n<numberType>integer</numberType>\n<prettyName>Allow/Deny</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>2</size>\n<name>allow</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</allow>\n</class>\n<content>\n</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.XWikiGlobalRights\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382484</date>\n<version>1.2</version>\n@\n','1.3','XWiki.WebHome');
INSERT INTO xwikidoc VALUES (2045153351,'AllDocs','','fr',0,'2004-08-20 12:23:42','2004-08-20 11:33:02','XWiki.Admin','','Main','1 Tous les documents de ce Wiki\r\n\r\n#set ($sql = \"\")\r\n#foreach ($item in $xwiki.searchDocuments($sql))\r\n#set($bentrydoc = $xwiki.getDocument($item))\r\n   * [${bentrydoc.web}.$bentrydoc.name] by [$bentrydoc.author] on \r\n#end','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.23.42;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>AllDocs</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent>Main.WebHome</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382519</creationDate>\n<date>1092997422457</date>\n<version>1.3</version>\n<content>1 Tous les documents de ce Wiki\n\n#set ($sql = \"\")\n#foreach ($item in $xwiki.searchDocuments($sql))\n#set($bentrydoc = $xwiki.getDocument($item))\n   * [${bentrydoc.web}.$bentrydoc.name] by [$bentrydoc.author] on \n#end</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@Main.AllDocs\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382519</date>\n<version>1.2</version>\n@\n','1.3','Main.WebHome');
INSERT INTO xwikidoc VALUES (-1242522436,'AllDocs','en','',1,'2004-08-20 12:23:52','2004-08-20 11:33:02','XWiki.Admin','','Main','1 All documents of this wiki\r\n\r\n#set ($sql = \"\")\r\n#foreach ($item in $xwiki.searchDocuments($sql))\r\n#set($bentrydoc = $xwiki.getDocument($item))\r\n   * [${bentrydoc.web}.$bentrydoc.name] by [$bentrydoc.author] on $formatter.formatLongDateTime($bentrydoc.date)\r\n#end','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.23.52;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>AllDocs</name>\n<language>en</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382537</creationDate>\n<date>1092997432242</date>\n<version>1.3</version>\n<content>1 All documents of this wiki\n\n#set ($sql = \"\")\n#foreach ($item in $xwiki.searchDocuments($sql))\n#set($bentrydoc = $xwiki.getDocument($item))\n   * [${bentrydoc.web}.$bentrydoc.name] by [$bentrydoc.author] on $formatter.formatLongDateTime($bentrydoc.date)\n#end</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@Main.AllDocs\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382538</date>\n<version>1.2</version>\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (-419313613,'WebRss','','fr',0,'2004-08-20 12:29:47','2004-08-20 11:33:02','XWiki.Admin','','Main','$response.setContentType(\"text/xml\")\r\n#set ($sql = \"where 1=1 order by doc.date desc\")\r\n#set ($list = $xwiki.searchDocuments($sql , 20 , 0))\r\n#set ($baseurl =  \"http://${request.serverName}\")\r\n{pre}\r\n#set($rsvc = $xwiki.xWiki.getRightService())\r\n$response.setContentType(\"text/xml\")\r\n<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://purl.org/rss/1.0/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:wiki=\"http://purl.org/rss/1.0/modules/wiki/\" >\r\n<channel rdf:about=\"$baseurl\">\r\n<title>$request.serverName</title>\r\n<link>$baseurl</link>\r\n<description>\r\n$request.serverName\r\n</description>\r\n<image rdf:resource=\"${baseurl}/xwiki/skins/default/logo.gif\" />\r\n<dc:language>$doc.defaultLanguage</dc:language>\r\n<dc:rights>$xwiki.webCopyright</dc:rights>\r\n<dc:publisher>$doc.author</dc:publisher>\r\n<dc:creator>$doc.author</dc:creator>\r\n<items>\r\n<rdf:Seq>\r\n#foreach ($item in $list)\r\n#if ($rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\r\n#set ($currentdoc = $xwiki.getDocument($item))\r\n<rdf:li rdf:resource=\"${baseurl}/xwiki/bin/view/${currentdoc.web}/${currentdoc.name}\" />\r\n#end\r\n#end\r\n</rdf:Seq>\r\n</items>\r\n</channel>\r\n<image rdf:about=\"${baseurl}/xwiki/skins/default/logo.gif\">\r\n  <title>XWiki Logo</title>\r\n  <link>${baseurl}</link>\r\n  <url>${baseurl}/xwiki/skins/default/logo.gif</url>\r\n</image>\r\n#foreach ($item in $list)\r\n#if ($rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\r\n#set ($currentdoc = $xwiki.getDocument($item))\r\n#set ($url = \"${baseurl}/xwiki/bin/view/${currentdoc.web}/${currentdoc.name}\")\r\n#if ($currentdoc.content.length() < 255)\r\n#set ($length = $currentdoc.content.length())\r\n#else\r\n#set ($length = 255)\r\n#end\r\n#set ($desc = $xwiki.getXMLEncoded($currentdoc.content.substring($length)))\r\n<item rdf:about=\"$url\">\r\n<title>${currentdoc.web}.${currentdoc.name}</title>\r\n<link>$url</link>\r\n<description>\r\n${currentdoc.web}.${currentdoc.name}\r\n</description>\r\n<dc:date>$currentdoc.date</dc:date>\r\n<dc:contributor>\r\n<rdf:Description link=\"\">\r\n<rdf:value>$currentdoc.author</rdf:value>\r\n</rdf:Description>\r\n</dc:contributor>\r\n</item>\r\n#end\r\n#end\r\n</rdf:RDF>\r\n{/pre}','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.29.47;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>WebRss</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent>Main.WebHome</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382555</creationDate>\n<date>1092997787541</date>\n<version>1.3</version>\n<content>$response.setContentType(\"text/xml\")\n#set ($sql = \"where 1=1 order by doc.date desc\")\n#set ($list = $xwiki.searchDocuments($sql , 20 , 0))\n#set ($baseurl =  \"http://${request.serverName}\")\n{pre}\n#set($rsvc = $xwiki.xWiki.getRightService())\n$response.setContentType(\"text/xml\")\n&lt;rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://purl.org/rss/1.0/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:wiki=\"http://purl.org/rss/1.0/modules/wiki/\" &gt;\n&lt;channel rdf:about=\"$baseurl\"&gt;\n&lt;title&gt;$request.serverName&lt;/title&gt;\n&lt;link&gt;$baseurl&lt;/link&gt;\n&lt;description&gt;\n$request.serverName\n&lt;/description&gt;\n&lt;image rdf:resource=\"${baseurl}/xwiki/skins/default/logo.gif\" /&gt;\n&lt;dc:language&gt;$doc.defaultLanguage&lt;/dc:language&gt;\n&lt;dc:rights&gt;$xwiki.webCopyright&lt;/dc:rights&gt;\n&lt;dc:publisher&gt;$doc.author&lt;/dc:publisher&gt;\n&lt;dc:creator&gt;$doc.author&lt;/dc:creator&gt;\n&lt;items&gt;\n&lt;rdf:Seq&gt;\n#foreach ($item in $list)\n#if ($rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\n#set ($currentdoc = $xwiki.getDocument($item))\n&lt;rdf:li rdf:resource=\"${baseurl}/xwiki/bin/view/${currentdoc.web}/${currentdoc.name}\" /&gt;\n#end\n#end\n&lt;/rdf:Seq&gt;\n&lt;/items&gt;\n&lt;/channel&gt;\n&lt;image rdf:about=\"${baseurl}/xwiki/skins/default/logo.gif\"&gt;\n  &lt;title&gt;XWiki Logo&lt;/title&gt;\n  &lt;link&gt;${baseurl}&lt;/link&gt;\n  &lt;url&gt;${baseurl}/xwiki/skins/default/logo.gif&lt;/url&gt;\n&lt;/image&gt;\n#foreach ($item in $list)\n#if ($rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\n#set ($currentdoc = $xwiki.getDocument($item))\n#set ($url = \"${baseurl}/xwiki/bin/view/${currentdoc.web}/${currentdoc.name}\")\n#if ($currentdoc.content.length() &lt; 255)\n#set ($length = $currentdoc.content.length())\n#else\n#set ($length = 255)\n#end\n#set ($desc = $xwiki.getXMLEncoded($currentdoc.content.substring($length)))\n&lt;item rdf:about=\"$url\"&gt;\n&lt;title&gt;${currentdoc.web}.${currentdoc.name}&lt;/title&gt;\n&lt;link&gt;$url&lt;/link&gt;\n&lt;description&gt;\n${currentdoc.web}.${currentdoc.name}\n&lt;/description&gt;\n&lt;dc:date&gt;$currentdoc.date&lt;/dc:date&gt;\n&lt;dc:contributor&gt;\n&lt;rdf:Description link=\"\"&gt;\n&lt;rdf:value&gt;$currentdoc.author&lt;/rdf:value&gt;\n&lt;/rdf:Description&gt;\n&lt;/dc:contributor&gt;\n&lt;/item&gt;\n#end\n#end\n&lt;/rdf:RDF&gt;\n{/pre}</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@Main.WebRss\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 64\na75 3\n<date>1092994382556</date>\n<version>1.2</version>\n<content>#includeForm(\"xwiki:Main.WebRss\")</content>\n@\n','1.3','Main.WebHome');
INSERT INTO xwikidoc VALUES (-863174266,'WhatsNew','','en',0,'2004-08-20 13:07:31','2004-08-20 11:33:02','XWiki.Admin','','Main','#set($diff = $request.get(\"diff\"))\r\n#if(!$diff)\r\n#set($diff = \"0\")\r\n#end\r\n#set($rsvc= $xwiki.xWiki.getRightService())\r\n1 What\'s New\r\n\r\nThe following documents have been recently created or modified:\r\n\r\n#if($diff == \"0\")\r\n[Click here to view the changes>$doc.name?diff=1]\r\n#end\r\n\r\n#set ($sql = \"where doc.name <> \'WhatsNew\' order by doc.date desc\")\r\n#set ($list = $xwiki.searchDocuments($sql , 20 , 0))\r\n#foreach ($item in $list)\r\n#if (!$rsvc || $rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\r\n#set($bentrydoc = $xwiki.getDocument($item))\r\n   * [${bentrydoc.web}.$bentrydoc.name] by [$bentrydoc.author] on $formatter.formatLongDateTime($bentrydoc.date)\r\n\r\n\r\n\r\n#if ($diff == \"1\")\r\n#set($deltas= $bentrydoc.getLastChanges())\r\n#foreach($delta in $deltas)\r\n#set($chunk = $delta.revised)\r\n#if ($chunk.size()>0)\r\n<div style=\"border-left: 8px solid #00FF00\">\r\n#if (($type == \"source\")||($type == \"xml\"))\r\n<pre>\r\n$xwiki.renderChunk($chunk, true, $tdoc)\r\n</pre>\r\n#else\r\n$xwiki.renderChunk($chunk, $tdoc)\r\n#end\r\n</div>\r\n#end\r\n\r\n#set($chunk = $delta.original)\r\n#if ($chunk.size()>0)\r\n<div style=\"border-left: 8px solid #FF0000\">\r\n#if (($type == \"source\")||($type == \"xml\"))\r\n<pre>\r\n$xwiki.renderChunk($chunk, true, $tdoc)\r\n</pre>\r\n#else\r\n$xwiki.renderChunk($chunk, $tdoc)\r\n#end\r\n</div>\r\n#end\r\n#end\r\n#end\r\n\r\n#end\r\n#end\r\n','head	1.6;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.6\ndate	2004.08.20.13.07.31;	author tomcat;	state Exp;\nbranches;\nnext	1.5;\n\n1.5\ndate	2004.08.20.13.06.45;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.13.05.08;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.13.04.56;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.6\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>WhatsNew</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent>Main.WebHome</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382576</creationDate>\n<date>1093000051335</date>\n<version>1.6</version>\n<content>#set($diff = $request.get(\"diff\"))\n#if(!$diff)\n#set($diff = \"0\")\n#end\n#set($rsvc= $xwiki.xWiki.getRightService())\n1 What\'s New\n\nThe following documents have been recently created or modified:\n\n#if($diff == \"0\")\n[Click here to view the changes&gt;$doc.name?diff=1]\n#end\n\n#set ($sql = \"where doc.name &lt;&gt; \'WhatsNew\' order by doc.date desc\")\n#set ($list = $xwiki.searchDocuments($sql , 20 , 0))\n#foreach ($item in $list)\n#if (!$rsvc || $rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\n#set($bentrydoc = $xwiki.getDocument($item))\n   * [${bentrydoc.web}.$bentrydoc.name] by [$bentrydoc.author] on $formatter.formatLongDateTime($bentrydoc.date)\n\n\n\n#if ($diff == \"1\")\n#set($deltas= $bentrydoc.getLastChanges())\n#foreach($delta in $deltas)\n#set($chunk = $delta.revised)\n#if ($chunk.size()&gt;0)\n&lt;div style=\"border-left: 8px solid #00FF00\"&gt;\n#if (($type == \"source\")||($type == \"xml\"))\n&lt;pre&gt;\n$xwiki.renderChunk($chunk, true, $tdoc)\n&lt;/pre&gt;\n#else\n$xwiki.renderChunk($chunk, $tdoc)\n#end\n&lt;/div&gt;\n#end\n\n#set($chunk = $delta.original)\n#if ($chunk.size()&gt;0)\n&lt;div style=\"border-left: 8px solid #FF0000\"&gt;\n#if (($type == \"source\")||($type == \"xml\"))\n&lt;pre&gt;\n$xwiki.renderChunk($chunk, true, $tdoc)\n&lt;/pre&gt;\n#else\n$xwiki.renderChunk($chunk, $tdoc)\n#end\n&lt;/div&gt;\n#end\n#end\n#end\n\n#end\n#end\n</content>\n</xwikidoc>\n@\n\n\n1.5\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1093000005908</date>\n<version>1.5</version>\nd19 1\na19 1\n1 Quoi de Neuf\nd21 1\na21 1\nLes documents suivants ont été créés ou modifiés:\nd24 1\na24 1\n[Cliquez ici pour voir les différences&gt;$doc.name?diff=1]\nd32 1\na32 1\n   * [${bentrydoc.web}.$bentrydoc.name] par [$bentrydoc.author] le $formatter.formatLongDateTime($bentrydoc.date)\nd34 2\nd68 2\na69 1\n#end</content>\n@\n\n\n1.4\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999908937</date>\n<version>1.4</version>\nd30 1\na30 1\n#if ($rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\n@\n\n\n1.3\nlog\n@@\ntext\n@d7 1\na7 1\n<defaultLanguage>fr</defaultLanguage>\nd12 2\na13 2\n<date>1092999896145</date>\n<version>1.3</version>\n@\n\n\n1.2\nlog\n@Main.WhatsNew\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 55\na66 3\n<date>1092994382576</date>\n<version>1.2</version>\n<content>#includeForm(\"xwiki:Main.WhatsNew\")</content>\n@\n','1.6','Main.WebHome');
INSERT INTO xwikidoc VALUES (-152317460,'WebHome','','en',0,'2004-08-20 13:20:06','2004-08-20 11:33:02','XWiki.Admin','','Sandbox','1 Test Zone\r\n\r\nThis zone is usefull to practice the Wiki Syntax\r\n\r\n   * [TestTopic1], [TestTopic2], [TestTopic3], [TestTopic4], [TestTopic5] \r\n   * [TestTopic6], [TestTopic7], [TestTopic8], [TestTopic9], [TestTopic10]\r\n   * [TestTopic11], [TestTopic12], [TestTopic13], [TestTopic14], [TestTopic15]\r\n\r\n','head	1.5;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.5\ndate	2004.08.20.13.20.06;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.13.19.22;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.12.26.51;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.5\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Sandbox</web>\n<name>WebHome</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent>Main.WebHome</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382000</creationDate>\n<date>1093000806757</date>\n<version>1.5</version>\n<content>1 Test Zone\n\nThis zone is usefull to practice the Wiki Syntax\n\n   * [TestTopic1], [TestTopic2], [TestTopic3], [TestTopic4], [TestTopic5] \n   * [TestTopic6], [TestTopic7], [TestTopic8], [TestTopic9], [TestTopic10]\n   * [TestTopic11], [TestTopic12], [TestTopic13], [TestTopic14], [TestTopic15]\n\n</content>\n</xwikidoc>\n@\n\n\n1.4\nlog\n@@\ntext\n@d12 3\na14 3\n<date>1093000762913</date>\n<version>1.4</version>\n<content>1 Zone d\'entrainement\nd16 1\na16 1\nCette zone est utile pour s\'entrainer à la syntaxe XWiki:\n@\n\n\n1.3\nlog\n@@\ntext\n@d7 1\na7 1\n<defaultLanguage>fr</defaultLanguage>\nd11 3\na13 3\n<creationDate>1092994382596</creationDate>\n<date>1092997611669</date>\n<version>1.3</version>\n@\n\n\n1.2\nlog\n@Sandbox.WebHome\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382596</date>\n<version>1.2</version>\n@\n','1.5','Main.WebHome');
INSERT INTO xwikidoc VALUES (2091039991,'WebHome','en','',1,'2004-08-20 12:26:42','2004-08-20 11:33:02','XWiki.Admin','','Sandbox','1 Training zone\r\n\r\nThis zone allows you to test creating wiki pages\r\n\r\n   * [TestTopic1], [TestTopic2], [TestTopic3], [TestTopic4], [TestTopic5] \r\n   * [TestTopic6], [TestTopic7], [TestTopic8], [TestTopic9], [TestTopic10]\r\n   * [TestTopic11], [TestTopic12], [TestTopic13], [TestTopic14], [TestTopic15]\r\n\r\n','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.26.42;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Sandbox</web>\n<name>WebHome</name>\n<language>en</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382616</creationDate>\n<date>1092997602599</date>\n<version>1.3</version>\n<content>1 Training zone\n\nThis zone allows you to test creating wiki pages\n\n   * [TestTopic1], [TestTopic2], [TestTopic3], [TestTopic4], [TestTopic5] \n   * [TestTopic6], [TestTopic7], [TestTopic8], [TestTopic9], [TestTopic10]\n   * [TestTopic11], [TestTopic12], [TestTopic13], [TestTopic14], [TestTopic15]\n\n</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@Sandbox.WebHome\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382617</date>\n<version>1.2</version>\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (80521547,'WebPreferences','','en',0,'2004-08-20 12:27:21','2004-08-20 11:33:02','XWiki.Admin','','Sandbox','#includeForm(\"XWiki.XWikiPreferencesTemplate\")','head	1.4;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.4\ndate	2004.08.20.12.27.21;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.12.27.13;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.4\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Sandbox</web>\n<name>WebPreferences</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382659</creationDate>\n<date>1092997641817</date>\n<version>1.4</version>\n<object>\n<class>\n<name>XWiki.XWikiRights</name>\n<groups>\n<prettyName>Groups</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>groups</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</groups>\n<users>\n<prettyName>Users</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>users</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</users>\n<levels>\n<prettyName>Access Levels</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>levels</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</levels>\n<allow>\n<numberType>integer</numberType>\n<prettyName>Allow/Deny</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>2</size>\n<name>allow</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</allow>\n</class>\n<name>Sandbox.WebPreferences</name>\n<number>0</number>\n<className>XWiki.XWikiRights</className>\n<property>\n<groups></groups>\n</property>\n<property>\n<users>Main.XWikiGuest</users>\n</property>\n<property>\n<levels>edit</levels>\n</property>\n<property>\n<allow>1</allow>\n</property>\n</object>\n<object>\n<class>\n<name>XWiki.XWikiPreferences</name>\n<admin_email>\n<prettyName>Admin eMail</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>admin_email</name>\n<number>14</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</admin_email>\n<confirmation_email_content>\n<prettyName>Confirmation eMail Content</prettyName>\n<rows>10</rows>\n<unmodifiable>0</unmodifiable>\n<size>72</size>\n<name>confirmation_email_content</name>\n<number>16</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</confirmation_email_content>\n<skin>\n<prettyName>Skin</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>20</size>\n<name>skin</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</skin>\n<title>\n<prettyName>Title</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>title</name>\n<number>10</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</title>\n<language>\n<prettyName>Language</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>language</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</language>\n<meta>\n<prettyName>HTTP Meta Info</prettyName>\n<rows>5</rows>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>meta</name>\n<number>9</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</meta>\n<version>\n<prettyName>Version</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>version</name>\n<number>12</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</version>\n<editbox_width>\n<numberType>long</numberType>\n<prettyName>Editbox Width</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>editbox_width</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</editbox_width>\n<webbgcolor>\n<prettyName>Web Background Color</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>webbgcolor</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</webbgcolor>\n<webcopyright>\n<prettyName>Web Copyright</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>60</size>\n<name>webcopyright</name>\n<number>5</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</webcopyright>\n<smtp_server>\n<prettyName>SMTP Server</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>smtp_server</name>\n<number>13</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</smtp_server>\n<editbox_height>\n<numberType>long</numberType>\n<prettyName>Editbox Height</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>editbox_height</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</editbox_height>\n<plugins>\n<prettyName>Plugins</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>plugins</name>\n<number>6</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</plugins>\n<validation_email_content>\n<prettyName>Validation eMail Content</prettyName>\n<rows>10</rows>\n<unmodifiable>0</unmodifiable>\n<size>72</size>\n<name>validation_email_content</name>\n<number>15</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</validation_email_content>\n<menu>\n<prettyName>Menu</prettyName>\n<rows>8</rows>\n<unmodifiable>0</unmodifiable>\n<size>60</size>\n<name>menu</name>\n<number>8</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</menu>\n<authenticate_edit>\n<prettyName>Authenticate On Edit</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>authenticate_edit</name>\n<number>7</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</authenticate_edit>\n</class>\n<name>Sandbox.WebPreferences</name>\n<number>0</number>\n<className>XWiki.XWikiPreferences</className>\n<property>\n<editbox_width></editbox_width>\n</property>\n<property>\n<skin></skin>\n</property>\n<property>\n<webcopyright></webcopyright>\n</property>\n<property>\n<webbgcolor></webbgcolor>\n</property>\n<property>\n<title></title>\n</property>\n<property>\n<editbox_height></editbox_height>\n</property>\n<property>\n<plugins></plugins>\n</property>\n<property>\n<menu>&lt;a href=\"WebHome\"&gt;Test&lt;/a&gt;\n</menu>\n</property>\n<property>\n<meta></meta>\n</property>\n<property>\n<authenticate_edit></authenticate_edit>\n</property>\n</object>\n<content>#includeForm(\"XWiki.XWikiPreferencesTemplate\")</content>\n</xwikidoc>\n@\n\n\n1.3\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092997633359</date>\n<version>1.3</version>\nd240 1\na240 1\n<content>#includeForm(\"XWiki.XWikiPreferences\")</content>\n@\n\n\n1.2\nlog\n@Sandbox.WebPreferences\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382660</date>\n<version>1.2</version>\n@\n','1.4','');
INSERT INTO xwikidoc VALUES (631474013,'WebHome','','en',0,'2004-08-20 12:08:18','2004-08-20 11:33:02','XWiki.Admin','','Doc','1 Welcome on your Wiki\r\n\r\nThis web site is your web site. With your wiki you can:\r\n\r\n   * Create a professional and personal Web Site\r\n   * Share and organize the company knowledge on an Intranet\r\n   * Create dynamic Intranet applications\r\n\r\n1.1 Links to the documentation\r\n\r\nYou will find the documentation [here>http://www.xwiki.com/xwiki/bin/view/Doc/WebHome].\r\n\r\n1.1 Support\r\n\r\n<span class=\"xwikiwarning\">The XWiki service is currently in *test*. Don’t hesitate to contact the [technical support>http://www.xwiki.org/xwiki/bin/view/Support/WebHome] to tell us about any problems or even with your comments about the service.</span>\r\n\r\n\r\n1.1 Where to start\r\n\r\nYou can start by modifying this page by clicking on the \"Edit\" button in the action bar on the right (for this you need to be logged-in). Before editing a page, check out the documentation about the [XWiki syntax>Doc.XWikiSyntax]\r\n\r\n1.1 Searching\r\n\r\nIf you search for documents in your wiki, use the [search page>Main.WebSearch] or get the list of [all documents>Main.AllDocs].','head	1.4;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.4\ndate	2004.08.20.12.08.18;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.11.38.42;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.4\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Doc</web>\n<name>WebHome</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382757</creationDate>\n<date>1092996498921</date>\n<version>1.4</version>\n<content>1 Welcome on your Wiki\n\nThis web site is your web site. With your wiki you can:\n\n   * Create a professional and personal Web Site\n   * Share and organize the company knowledge on an Intranet\n   * Create dynamic Intranet applications\n\n1.1 Links to the documentation\n\nYou will find the documentation [here&gt;http://www.xwiki.com/xwiki/bin/view/Doc/WebHome].\n\n1.1 Support\n\n&lt;span class=\"xwikiwarning\"&gt;The XWiki service is currently in *test*. Don’t hesitate to contact the [technical support&gt;http://www.xwiki.org/xwiki/bin/view/Support/WebHome] to tell us about any problems or even with your comments about the service.&lt;/span&gt;\n\n\n1.1 Where to start\n\nYou can start by modifying this page by clicking on the \"Edit\" button in the action bar on the right (for this you need to be logged-in). Before editing a page, check out the documentation about the [XWiki syntax&gt;Doc.XWikiSyntax]\n\n1.1 Searching\n\nIf you search for documents in your wiki, use the [search page&gt;Main.WebSearch] or get the list of [all documents&gt;Main.AllDocs].</content>\n</xwikidoc>\n@\n\n\n1.3\nlog\n@@\ntext\n@d10 1\na10 1\n<author>XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994722321</date>\n<version>1.3</version>\n@\n\n\n1.2\nlog\n@Doc.WebHome\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382757</date>\n<version>1.2</version>\nd18 3\na20 3\n   * Create your personal web site\n   * Share content with your friends\n   * Create free content zones with all Internet users\nd24 1\na24 1\nYou will find the documentation [here&gt;xwiki:Doc.WebHome].\nd28 1\na28 1\n&lt;span class=\"xwikiwarning\"&gt;The XWiki service is currently in *test*. Don’t hesitate to contact the [technical support&gt;xwiki:Support.WebHome] to tell us about any problems or even with your comments about the service.&lt;/span&gt;\nd33 1\na33 1\nYou can start by modifying this page by clicking on the \"Edit\" button in the action bar on the right (for this you need to be logged-in). Before editing a page, check out the documentation about the [XWiki syntax&gt;xwiki:Doc.XWikiSyntax]\n@\n','1.4','');
INSERT INTO xwikidoc VALUES (285623817,'WebHome','fr','',1,'2004-08-20 13:37:59','2004-08-20 11:33:02','XWiki.Admin','','Doc','1 Bienvenue sur votre Wiki\r\n\r\nCe site est votre site. Avec votre Wiki vous pouvez:\r\n\r\n   * Créer un site professionnel ou personnel\r\n   * Partager et Organiser la connaissance de l\'entreprise\r\n   * Créer des applications Intranet\r\n\r\n1.1 Liens vers la documentation\r\n\r\nL\'aide et la documentation sont aussi accessibles par le bouton \"Aide\" en haut et à droite de chaque page. Allez voir la [documentation générale>http://www.xwiki.com/xwiki/bin/view/Doc/WebHome].\r\n\r\n1.1 Support\r\n\r\nN\'hésitez pas à contacter le [support>http://www.xwiki.org/xwiki/bin/view/Support/WebHome] pour nous faire part des problèmes et de vos remarques sur ce logiciel.\r\n\r\n\r\n1.1 Que faire\r\n\r\nVous pouvez modifier cette page en cliquant sur le bouton \"Edit\" dans la barre d\'outil (pour cela vous devez tout d\'abord être identifié), mais avant de commencer à modifier une page, veuillez consulter le document sur [la syntaxe XWiki>Doc.XWikiSyntax]\r\n\r\n1.1 Recherche\r\n\r\nSi vous cherchez un document utilisez [la page de recherche>Main.WebSearch] ou listez [tous les documents>Main.AllDocs].','head	1.9;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.9\ndate	2004.08.20.13.37.59;	author tomcat;	state Exp;\nbranches;\nnext	1.8;\n\n1.8\ndate	2004.08.20.12.08.07;	author tomcat;	state Exp;\nbranches;\nnext	1.7;\n\n1.7\ndate	2004.08.20.11.36.49;	author tomcat;	state Exp;\nbranches;\nnext	1.6;\n\n1.6\ndate	2004.08.20.11.35.42;	author tomcat;	state Exp;\nbranches;\nnext	1.5;\n\n1.5\ndate	2004.08.20.11.35.19;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.11.34.59;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.11.34.43;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.9\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Doc</web>\n<name>WebHome</name>\n<language>fr</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382000</creationDate>\n<date>1093001879137</date>\n<version>1.9</version>\n<content>1 Bienvenue sur votre Wiki\n\nCe site est votre site. Avec votre Wiki vous pouvez:\n\n   * Créer un site professionnel ou personnel\n   * Partager et Organiser la connaissance de l\'entreprise\n   * Créer des applications Intranet\n\n1.1 Liens vers la documentation\n\nL\'aide et la documentation sont aussi accessibles par le bouton \"Aide\" en haut et à droite de chaque page. Allez voir la [documentation générale&gt;http://www.xwiki.com/xwiki/bin/view/Doc/WebHome].\n\n1.1 Support\n\nN\'hésitez pas à contacter le [support&gt;http://www.xwiki.org/xwiki/bin/view/Support/WebHome] pour nous faire part des problèmes et de vos remarques sur ce logiciel.\n\n\n1.1 Que faire\n\nVous pouvez modifier cette page en cliquant sur le bouton \"Edit\" dans la barre d\'outil (pour cela vous devez tout d\'abord être identifié), mais avant de commencer à modifier une page, veuillez consulter le document sur [la syntaxe XWiki&gt;Doc.XWikiSyntax]\n\n1.1 Recherche\n\nSi vous cherchez un document utilisez [la page de recherche&gt;Main.WebSearch] ou listez [tous les documents&gt;Main.AllDocs].</content>\n</xwikidoc>\n@\n\n\n1.8\nlog\n@@\ntext\n@d11 3\na13 3\n<creationDate>1092994382777</creationDate>\n<date>1092996487914</date>\n<version>1.8</version>\nd37 1\na37 1\nSi vous cherchez un document utilisez [la page de recherche&gt;WebSearch] ou listez [tous les documents&gt;AllDocs].</content>\n@\n\n\n1.7\nlog\n@@\ntext\n@d10 1\na10 1\n<author>XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994609539</date>\n<version>1.7</version>\n@\n\n\n1.6\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092994542740</date>\n<version>1.6</version>\nd18 3\na20 3\n   * Créer votre site personnel\n   * Partager du contenu avec vos amis\n   * Créer des zones d\'expressions libres avec les internautes\n@\n\n\n1.5\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092994519564</date>\n<version>1.5</version>\nd28 1\na28 1\nLe service xwiki est actuellement en *test*. Soyez indulgent avec les bogues et le manque de documentation. N\'hésitez pas à contacter le [support&gt;http://www.xwiki.org/xwiki/bin/view/Support/WebHome] pour nous faire part des problèmes et de vos remarques sur le service.\n@\n\n\n1.4\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092994499554</date>\n<version>1.4</version>\nd28 1\na28 1\nLe service xwiki est actuellement en *test*. Soyez indulgent avec les bogues et le manque de documentation. N\'hésitez pas à contacter le [support&gt;xwiki:Support.WebHome] pour nous faire part des problèmes et de vos remarques sur le service.\n@\n\n\n1.3\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092994483431</date>\n<version>1.3</version>\nd33 1\na33 1\nVous pouvez modifier cette page en cliquant sur le bouton \"Edit\" dans la barre d\'outil (pour cela vous devez tout d\'abord être identifié), mais avant de commencer à modifier une page, veuillez consulter le document sur [la syntaxe XWiki&gt;http://www.xwiki.com/xwiki/bin/view/Doc/XWikiSyntax]\n@\n\n\n1.2\nlog\n@Doc.WebHome\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382778</date>\n<version>1.2</version>\nd24 1\na24 1\nL\'aide et la documentation sont aussi accessibles par le bouton \"Aide\" en haut et à droite de chaque page. Allez voir la [documentation générale&gt;xwiki:Doc.WebHome].\nd33 1\na33 1\nVous pouvez modifier cette page en cliquant sur le bouton \"Edit\" dans la barre d\'outil (pour cela vous devez tout d\'abord être identifié), mais avant de commencer à modifier une page, veuillez consulter le document sur [la syntaxe XWiki&gt;xwiki:Doc.XWikiSyntax]\n@\n','1.9','');
INSERT INTO xwikidoc VALUES (1479340930,'RegisterNewUser','','en',0,'2004-08-20 12:27:48','2004-08-20 11:33:02','XWiki.Admin','','XWiki','#set ($register = $request.getParameter(\"register\"))\r\n#if ($register)\r\nRegistering user *$request.getParameter(\"register_first_name\") $request.getParameter(\"register_last_name\")*\r\n\r\n#set( $reg= $xwiki.createUser(false))\r\n#if ($reg>0)\r\nThe user $request.getParameter(\"xwikiname\") has been registered properly.\r\n#else\r\nAn error occured during the registration process. Please contact the [support>Support.WebHome].\r\n#end\r\n#else\r\n#set($reg=0)\r\n#end\r\n\r\n#if ($reg<=0)\r\nWelcome on the XWiki registration page. This page will allow you to create an account on this wiki which can allow you to create content on this wiki (provided that the administrator gives you the corresponding rights).\r\n\r\n1.1 Registration\r\n\r\n<form id=\"register\" name=\"register\" action=\"\" method=\"get\">\r\n<script type=\"text/javascript\">\r\n#includeTopic(\"xwiki:XWiki.RegisterJS\")\r\n<p>\r\n<input type=\"hidden\" name=\"template\" value=\"XWiki.XWikiUserTemplate\" />\r\n<input type=\"hidden\" name=\"register\" value=\"1\">\r\n#set( $class = $xwiki.getDocument(\"XWiki.XWikiUsers\").xWikiClass)\r\n#set( $obj = $class.newObject() )\r\n</p>\r\n<table class=\"block\" cellspacing=\"5px\">\r\n <tbody>\r\n <tr>\r\n#set($prop = $class.first_name)\r\n   <td>First Name\r\n   </td>\r\n     <td>$doc.displayEdit($prop, \"register_\",  $obj)\r\n     </td>\r\n#set($prop = $class.last_name)\r\n   <td>Last Name\r\n   </td>\r\n     <td>$doc.displayEdit($prop, \"register_\",  $obj)\r\n     </td>\r\n </tr>\r\n<tr>\r\n#set($prop = $class.email)\r\n   <td>e-Mail Address \r\n   </td>\r\n     <td>$doc.displayEdit($prop, \"register_\",  $obj)\r\n     </td>\r\n </tr>\r\n<tr>\r\n</tr>\r\n<td></td>\r\n <tr>\r\n#set($prop = $class.password)\r\n   <td>Password\r\n   </td>\r\n     <td>$doc.displayEdit($prop, \"register_\",  $obj)\r\n     </td>\r\n   <td>Password (again)\r\n   </td>\r\n     <td>$doc.displayEdit($prop, \"register2_\",  $obj)\r\n     </td>\r\n </tr>\r\n<tr><td></td></tr>\r\n <tr>\r\n   <td>Wiki Name (this is also your login):</td>\r\n   <td>\r\n    <input name=\"xwikiname\" type=\"text\" size=\"20\" onfocus=\" prepareName(document.forms.register);\" /> \r\n   </td>\r\n </tr>\r\n </tbody>\r\n </table>\r\n<center>\r\n<input type=\"submit\" value=\"Register this account\">\r\n</center>\r\n</form>\r\n#end','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.27.48;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>RegisterNewUser</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.XWikiUsers</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382794</creationDate>\n<date>1092997668098</date>\n<version>1.3</version>\n<content>#set ($register = $request.getParameter(\"register\"))\n#if ($register)\nRegistering user *$request.getParameter(\"register_first_name\") $request.getParameter(\"register_last_name\")*\n\n#set( $reg= $xwiki.createUser(false))\n#if ($reg&gt;0)\nThe user $request.getParameter(\"xwikiname\") has been registered properly.\n#else\nAn error occured during the registration process. Please contact the [support&gt;Support.WebHome].\n#end\n#else\n#set($reg=0)\n#end\n\n#if ($reg&lt;=0)\nWelcome on the XWiki registration page. This page will allow you to create an account on this wiki which can allow you to create content on this wiki (provided that the administrator gives you the corresponding rights).\n\n1.1 Registration\n\n&lt;form id=\"register\" name=\"register\" action=\"\" method=\"get\"&gt;\n&lt;script type=\"text/javascript\"&gt;\n#includeTopic(\"xwiki:XWiki.RegisterJS\")\n&lt;p&gt;\n&lt;input type=\"hidden\" name=\"template\" value=\"XWiki.XWikiUserTemplate\" /&gt;\n&lt;input type=\"hidden\" name=\"register\" value=\"1\"&gt;\n#set( $class = $xwiki.getDocument(\"XWiki.XWikiUsers\").xWikiClass)\n#set( $obj = $class.newObject() )\n&lt;/p&gt;\n&lt;table class=\"block\" cellspacing=\"5px\"&gt;\n &lt;tbody&gt;\n &lt;tr&gt;\n#set($prop = $class.first_name)\n   &lt;td&gt;First Name\n   &lt;/td&gt;\n     &lt;td&gt;$doc.displayEdit($prop, \"register_\",  $obj)\n     &lt;/td&gt;\n#set($prop = $class.last_name)\n   &lt;td&gt;Last Name\n   &lt;/td&gt;\n     &lt;td&gt;$doc.displayEdit($prop, \"register_\",  $obj)\n     &lt;/td&gt;\n &lt;/tr&gt;\n&lt;tr&gt;\n#set($prop = $class.email)\n   &lt;td&gt;e-Mail Address \n   &lt;/td&gt;\n     &lt;td&gt;$doc.displayEdit($prop, \"register_\",  $obj)\n     &lt;/td&gt;\n &lt;/tr&gt;\n&lt;tr&gt;\n&lt;/tr&gt;\n&lt;td&gt;&lt;/td&gt;\n &lt;tr&gt;\n#set($prop = $class.password)\n   &lt;td&gt;Password\n   &lt;/td&gt;\n     &lt;td&gt;$doc.displayEdit($prop, \"register_\",  $obj)\n     &lt;/td&gt;\n   &lt;td&gt;Password (again)\n   &lt;/td&gt;\n     &lt;td&gt;$doc.displayEdit($prop, \"register2_\",  $obj)\n     &lt;/td&gt;\n &lt;/tr&gt;\n&lt;tr&gt;&lt;td&gt;&lt;/td&gt;&lt;/tr&gt;\n &lt;tr&gt;\n   &lt;td&gt;Wiki Name (this is also your login):&lt;/td&gt;\n   &lt;td&gt;\n    &lt;input name=\"xwikiname\" type=\"text\" size=\"20\" onfocus=\" prepareName(document.forms.register);\" /&gt; \n   &lt;/td&gt;\n &lt;/tr&gt;\n &lt;/tbody&gt;\n &lt;/table&gt;\n&lt;center&gt;\n&lt;input type=\"submit\" value=\"Register this account\"&gt;\n&lt;/center&gt;\n&lt;/form&gt;\n#end</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.RegisterNewUser\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382795</date>\n<version>1.2</version>\n@\n','1.3','XWiki.XWikiUsers');
INSERT INTO xwikidoc VALUES (386280388,'RegisterNewUser','fr','',1,'2004-08-20 12:28:21','2004-08-20 11:33:02','XWiki.Admin','','XWiki','#set ($register = $request.getParameter(\"register\"))\r\n#if ($register)\r\nTentative d\'inscription de *$request.getParameter(\"register_first_name\") $request.getParameter(\"register_last_name\")*\r\n\r\n#set( $reg= $xwiki.createUser(false))\r\n#if ($reg>0)\r\nL\'utilisateur [$request.getParameter(\"xwikiname\")] a été correctement enregistré.\r\n#else\r\nUne erreur est survenue pendant l\'enregistrement.\r\n#end\r\n#else\r\n#set($reg=0)\r\n#end\r\n\r\n#if ($reg<=0)\r\nBienvenue sur le formulaire d\'inscription à XWiki. Ceci vous permettra d\'éditer les pages de ce wiki (si l\'administrateur du wiki vous donne les droits).\r\n\r\n1.1 Inscription\r\n\r\n<form id=\"register\" name=\"register\" action=\"\" method=\"get\">\r\n#includeTopic(\"xwiki:XWiki.RegisterJS\")\r\n<p>\r\n<input type=\"hidden\" name=\"template\" value=\"XWiki.XWikiUserTemplate\" />\r\n<input type=\"hidden\" name=\"register\" value=\"1\">\r\n#set( $class = $xwiki.getDocument(\"XWiki.XWikiUsers\").xWikiClass)\r\n#set( $serverclass = $xwiki.getDocument(\"XWiki.XWikiServerClass\").xWikiClass)\r\n#set( $obj = $class.newObject() )\r\n#set( $serverobj = $class.newObject() )\r\n</p>\r\n<table class=\"block\" cellspacing=\"5px\">\r\n <tbody>\r\n <tr>\r\n#set($prop = $class.first_name)\r\n   <td>Prénom\r\n   </td>\r\n     <td>$doc.displayEdit($prop, \"register_\",  $obj)\r\n     </td>\r\n#set($prop = $class.last_name)\r\n   <td>Nom \r\n   </td>\r\n     <td>$doc.displayEdit($prop, \"register_\",  $obj)\r\n     </td>\r\n </tr>\r\n<tr>\r\n#set($prop = $class.email)\r\n   <td>Adresse e-Mail \r\n   </td>\r\n     <td>$doc.displayEdit($prop, \"register_\",  $obj)\r\n     </td>\r\n </tr>\r\n<tr>\r\n</tr>\r\n<td></td>\r\n <tr>\r\n#set($prop = $class.password)\r\n   <td>Mot de passe\r\n   </td>\r\n     <td>$doc.displayEdit($prop, \"register_\",  $obj)\r\n     </td>\r\n   <td>Répétition du mot de passe\r\n   </td>\r\n     <td>$doc.displayEdit($prop, \"register2_\",  $obj)\r\n     </td>\r\n </tr>\r\n<tr><td></td></tr>\r\n <tr>\r\n   <td>Nom Wiki (il s\'agit aussi de votre login):</td>\r\n   <td>\r\n    <input name=\"xwikiname\" type=\"text\" size=\"20\" onfocus=\" prepareName(document.forms.register);\" /> \r\n   </td>\r\n </tr>\r\n </tbody>\r\n </table>\r\n<center>\r\n<input type=\"submit\" value=\"Je m\'inscris\">\r\n</center>\r\n</form>\r\n#end\r\n\r\n','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.28.21;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>RegisterNewUser</name>\n<language>fr</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382817</creationDate>\n<date>1092997701237</date>\n<version>1.3</version>\n<content>#set ($register = $request.getParameter(\"register\"))\n#if ($register)\nTentative d\'inscription de *$request.getParameter(\"register_first_name\") $request.getParameter(\"register_last_name\")*\n\n#set( $reg= $xwiki.createUser(false))\n#if ($reg&gt;0)\nL\'utilisateur [$request.getParameter(\"xwikiname\")] a été correctement enregistré.\n#else\nUne erreur est survenue pendant l\'enregistrement.\n#end\n#else\n#set($reg=0)\n#end\n\n#if ($reg&lt;=0)\nBienvenue sur le formulaire d\'inscription à XWiki. Ceci vous permettra d\'éditer les pages de ce wiki (si l\'administrateur du wiki vous donne les droits).\n\n1.1 Inscription\n\n&lt;form id=\"register\" name=\"register\" action=\"\" method=\"get\"&gt;\n#includeTopic(\"xwiki:XWiki.RegisterJS\")\n&lt;p&gt;\n&lt;input type=\"hidden\" name=\"template\" value=\"XWiki.XWikiUserTemplate\" /&gt;\n&lt;input type=\"hidden\" name=\"register\" value=\"1\"&gt;\n#set( $class = $xwiki.getDocument(\"XWiki.XWikiUsers\").xWikiClass)\n#set( $serverclass = $xwiki.getDocument(\"XWiki.XWikiServerClass\").xWikiClass)\n#set( $obj = $class.newObject() )\n#set( $serverobj = $class.newObject() )\n&lt;/p&gt;\n&lt;table class=\"block\" cellspacing=\"5px\"&gt;\n &lt;tbody&gt;\n &lt;tr&gt;\n#set($prop = $class.first_name)\n   &lt;td&gt;Prénom\n   &lt;/td&gt;\n     &lt;td&gt;$doc.displayEdit($prop, \"register_\",  $obj)\n     &lt;/td&gt;\n#set($prop = $class.last_name)\n   &lt;td&gt;Nom \n   &lt;/td&gt;\n     &lt;td&gt;$doc.displayEdit($prop, \"register_\",  $obj)\n     &lt;/td&gt;\n &lt;/tr&gt;\n&lt;tr&gt;\n#set($prop = $class.email)\n   &lt;td&gt;Adresse e-Mail \n   &lt;/td&gt;\n     &lt;td&gt;$doc.displayEdit($prop, \"register_\",  $obj)\n     &lt;/td&gt;\n &lt;/tr&gt;\n&lt;tr&gt;\n&lt;/tr&gt;\n&lt;td&gt;&lt;/td&gt;\n &lt;tr&gt;\n#set($prop = $class.password)\n   &lt;td&gt;Mot de passe\n   &lt;/td&gt;\n     &lt;td&gt;$doc.displayEdit($prop, \"register_\",  $obj)\n     &lt;/td&gt;\n   &lt;td&gt;Répétition du mot de passe\n   &lt;/td&gt;\n     &lt;td&gt;$doc.displayEdit($prop, \"register2_\",  $obj)\n     &lt;/td&gt;\n &lt;/tr&gt;\n&lt;tr&gt;&lt;td&gt;&lt;/td&gt;&lt;/tr&gt;\n &lt;tr&gt;\n   &lt;td&gt;Nom Wiki (il s\'agit aussi de votre login):&lt;/td&gt;\n   &lt;td&gt;\n    &lt;input name=\"xwikiname\" type=\"text\" size=\"20\" onfocus=\" prepareName(document.forms.register);\" /&gt; \n   &lt;/td&gt;\n &lt;/tr&gt;\n &lt;/tbody&gt;\n &lt;/table&gt;\n&lt;center&gt;\n&lt;input type=\"submit\" value=\"Je m\'inscris\"&gt;\n&lt;/center&gt;\n&lt;/form&gt;\n#end\n\n</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.RegisterNewUser\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382818</date>\n<version>1.2</version>\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (1079582975,'WebSearch','fr','',1,'2004-08-20 13:15:00','2004-08-20 13:11:04','XWiki.Admin','XWiki.Admin','Main','1 Recherche sur ce wiki\r\n\r\n#if ($request.getParameter(\"text\"))\r\n#set ($text = $request.getParameter(\"text\") )\r\n#else\r\n#set($text = \"\")\r\n#end\r\n\r\n<center>\r\n<form action=\"WebSearch\">\r\n<input type=\"text\" name=\"text\" value=\"$!text\">&nbsp;<input type=\"submit\" name=\"search\" value=\"Search\">\r\n</form>\r\n</center>\r\n\r\n#set ($sql = \"where doc.content like \'%$text%\' order by doc.date desc\")\r\n#set ($start = 0)\r\n#set ($nb = 100)\r\n<!-- \r\nSql: $sql\r\nSql: $start\r\nSql: $nb\r\n-->\r\n#set ($list = $xwiki.searchDocuments($sql , $nb, $start))\r\n#foreach ($item in $list)\r\n#set($bentrydoc = $xwiki.getDocument($item))\r\n   * [${bentrydoc.web}.$bentrydoc.name] par [$bentrydoc.author] le $formatter.formatLongDateTime($bentrydoc.date)\r\n#end','head	1.7;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.7\ndate	2004.08.20.13.15.00;	author tomcat;	state Exp;\nbranches;\nnext	1.6;\n\n1.6\ndate	2004.08.20.13.14.48;	author tomcat;	state Exp;\nbranches;\nnext	1.5;\n\n1.5\ndate	2004.08.20.13.14.41;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.13.12.58;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.13.12.58;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.13.11.04;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.7\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>WebSearch</name>\n<language>fr</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1093000264535</creationDate>\n<date>1093000500449</date>\n<version>1.7</version>\n<content>1 Recherche sur ce wiki\n\n#if ($request.getParameter(\"text\"))\n#set ($text = $request.getParameter(\"text\") )\n#else\n#set($text = \"\")\n#end\n\n&lt;center&gt;\n&lt;form action=\"WebSearch\"&gt;\n&lt;input type=\"text\" name=\"text\" value=\"$!text\"&gt;&amp;nbsp;&lt;input type=\"submit\" name=\"search\" value=\"Search\"&gt;\n&lt;/form&gt;\n&lt;/center&gt;\n\n#set ($sql = \"where doc.content like \'%$text%\' order by doc.date desc\")\n#set ($start = 0)\n#set ($nb = 100)\n&lt;!-- \nSql: $sql\nSql: $start\nSql: $nb\n--&gt;\n#set ($list = $xwiki.searchDocuments($sql , $nb, $start))\n#foreach ($item in $list)\n#set($bentrydoc = $xwiki.getDocument($item))\n   * [${bentrydoc.web}.$bentrydoc.name] par [$bentrydoc.author] le $formatter.formatLongDateTime($bentrydoc.date)\n#end</content>\n</xwikidoc>\n@\n\n\n1.6\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1093000488933</date>\n<version>1.6</version>\nd28 1\na28 1\n#set ($sql = \"where doc.content like \'%$text%\' and doc.author&lt;&gt;\'XWiki.Admn\' order by doc.date desc\")\n@\n\n\n1.5\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1093000481536</date>\n<version>1.5</version>\nd28 1\na28 1\n#set ($sql = \"where doc.content like \'%$text%\' and doc.author&lt;&gt;\'XWiki.Admin\' order by doc.date desc\")\n@\n\n\n1.4\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1093000378717</date>\n<version>1.4</version>\nd28 1\na28 1\n#set ($sql = \"where doc.content like \'%$text%\' order by doc.date desc\")\n@\n\n\n1.3\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1093000378103</date>\n<version>1.3</version>\n@\n\n\n1.2\nlog\n@Main.WebSearch\n@\ntext\n@d12 2\na13 2\n<date>1093000264536</date>\n<version>1.2</version>\nd30 1\na30 1\n#set ($nb = 50)\n@\n','1.7','');
INSERT INTO xwikidoc VALUES (1079582940,'WebSearch','en','',1,'2004-08-20 13:08:15','2004-08-20 11:33:02','XWiki.Admin','','Main','1 Search on this Wiki\r\n\r\n#if ($request.getParameter(\"text\"))\r\n#set ($text = $request.getParameter(\"text\") )\r\n#else\r\n#set($text = \"\")\r\n#end\r\n\r\n<center>\r\n<form action=\"WebSearch\">\r\n<input type=\"text\" name=\"text\" value=\"$!text\">&nbsp;<input type=\"submit\" name=\"search\" value=\"Search\">\r\n</form>\r\n</center>\r\n\r\n#set ($sql = \"where doc.content like \'%$text%\' order by doc.date desc\")\r\n#set ($start = 0)\r\n#set ($nb = 50)\r\n<!-- \r\nSql: $sql\r\nSql: $start\r\nSql: $nb\r\n-->\r\n#set ($list = $xwiki.searchDocuments($sql , $nb, $start))\r\n#foreach ($item in $list)\r\n#set($bentrydoc = $xwiki.getDocument($item))\r\n   * [${bentrydoc.web}.$bentrydoc.name] by [$bentrydoc.author] on $formatter.formatLongDateTime($bentrydoc.date)\r\n#end','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.13.08.15;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>WebSearch</name>\n<language>en</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382853</creationDate>\n<date>1093000095296</date>\n<version>1.3</version>\n<content>1 Search on this Wiki\n\n#if ($request.getParameter(\"text\"))\n#set ($text = $request.getParameter(\"text\") )\n#else\n#set($text = \"\")\n#end\n\n&lt;center&gt;\n&lt;form action=\"WebSearch\"&gt;\n&lt;input type=\"text\" name=\"text\" value=\"$!text\"&gt;&amp;nbsp;&lt;input type=\"submit\" name=\"search\" value=\"Search\"&gt;\n&lt;/form&gt;\n&lt;/center&gt;\n\n#set ($sql = \"where doc.content like \'%$text%\' order by doc.date desc\")\n#set ($start = 0)\n#set ($nb = 50)\n&lt;!-- \nSql: $sql\nSql: $start\nSql: $nb\n--&gt;\n#set ($list = $xwiki.searchDocuments($sql , $nb, $start))\n#foreach ($item in $list)\n#set($bentrydoc = $xwiki.getDocument($item))\n   * [${bentrydoc.web}.$bentrydoc.name] by [$bentrydoc.author] on $formatter.formatLongDateTime($bentrydoc.date)\n#end</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@Main.WebSearch\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 29\na40 3\n<date>1092994382854</date>\n<version>1.2</version>\n<content>#includeForm(\"xwiki:Main.WebSearch\")</content>\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (565801557,'XWikiSyntax','','en',0,'2004-08-20 13:03:24','2004-08-20 11:33:02','XWiki.Admin','','Doc','1 The Wiki Syntax\r\n\r\nXWiki allows to create internet pages without using HTML thanks to the Wiki Syntax. This Wiki Syntax is a simple way to modify the aspect of a document as well as create links between documents.\r\n\r\nThis document explains the wiki syntax. \r\n\r\n{pre}\r\n<table class=\"wiki-table\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\" width=\"80%\">\r\n<tr><th width=\"25%\">Commande:</th>\r\n<th width=\"10%\">Exemple:</th>\r\n<th width=\"10%\">Resultat:</th>\r\n\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Titles</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\n1 Titre 1\r\n\r\n1.1 Titre 2\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<h3 class=\"heading-1\">Titre 1</h3>\r\n<h3 class=\"heading-1-1\">Titre 2</h3>\r\n</td>\r\n\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Paragraphs</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\nHere is a paragraph.\r\nThis text continues the same paragraph.\r\n\r\nThis paragraph is a new one.\r\n\r\nThis paragraph is another new one.\r\n\r\n</pre>\r\n</td>\r\n<td>\r\nHere is a paragraph.\r\nThis text continues the same paragraph.<p class=\"paragraph\"/>This paragraph is a new one.<p class=\"paragraph\"/>This paragraph is another new one.\r\n</td>\r\n</tr>\r\n\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Lists</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\n* List item\r\n* List item\r\n\r\n   * Recursive list\r\n      * Recursive list\r\n      * Recursive list\r\n      * Recursive list\r\n   * Recursive list\r\n      * Recursive list\r\n\r\n   \r\n1. Enumerated list\r\n1. Enumerated list\r\n\r\na. Alphabetical enumerated list\r\na. Alphabetical enumerated list\r\n\r\nA. Uppercase alphabetical enumerated list\r\nA. Uppercase alphabetical enumerated list\r\n\r\ni. Roman enumerated list\r\ni. Roman enumerated list\r\n\r\nI. Uppercase roman enumerated list\r\nI. Uppercase roman enumerated list\r\n\r\ng. Greek enumerated list\r\ng. Greek enumerated list\r\n\r\nh. Hirigana (jap.) enumerated list\r\nh. Hirigana (jap.) enumerated list\r\n\r\nk. Katakana (jap.) enumerated list\r\nk. Katakana (jap.) enumerated list\r\n\r\n \r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<ul class=\"star\">\r\n<li>List item</li>\r\n<li>List item</li>\r\n<li>Recursive list</li>\r\n\r\n<li>Recursive list</li>\r\n<li>Recursive list</li>\r\n<li>Recursive list</li>\r\n<li>Recursive list</li>\r\n<li>Recursive list</li>\r\n</ul>\r\n<ol>\r\n<li>Enumerated list</li>\r\n<li>Enumerated list</li>\r\n</ol>\r\n\r\n<ol class=\"alpha\">\r\n<li>Alphabetical enumerated list</li>\r\n<li>Alphabetical enumerated list</li>\r\n</ol>\r\n<ol class=\"ALPHA\">\r\n<li>Uppercase alphabetical enumerated list</li>\r\n<li>Uppercase alphabetical enumerated list</li>\r\n</ol>\r\n<ol class=\"roman\">\r\n<li>Roman enumerated list</li>\r\n<li>Roman enumerated list</li>\r\n\r\n</ol>\r\n<ol class=\"ROMAN\">\r\n<li>Uppercase roman enumerated list</li>\r\n<li>Uppercase roman enumerated list</li>\r\n</ol>\r\n<ol class=\"greek\">\r\n<li>Greek enumerated list</li>\r\n<li>Greek enumerated list</li>\r\n</ol>\r\n<ol class=\"hiragana\">\r\n<li>Hirigana (jap.) enumerated list</li>\r\n<li>Hirigana (jap.) enumerated list</li>\r\n\r\n</ol>\r\n<ol class=\"katakana\">\r\n<li>Katakana (jap.) enumerated list</li>\r\n<li>Katakana (jap.) enumerated list</li>\r\n</ol> \r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Bold</b><br/>\r\nThere are two ways to makr text as bold.\r\n</td>\r\n<td>\r\n<pre>\r\n\r\nHere is a text with a *bold* word.\r\nHere is a text with a __bold__ word.\r\n\r\n\r\n</pre>\r\n</td>\r\n<td>\r\nHere is a text with a <strong class=\"strong\">bold</strong> word.\r\nHere is a text with a <b class=\"bold\">bold</b> word.<p class=\"paragraph\"/></td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Italics</b><br/><p class=\"paragraph\"/></td>\r\n\r\n<td>\r\n<pre>\r\n\r\n~~Here is a text in italics~~\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<i class=\"italic\">Here is a text in italics</i>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Striked Text</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\n--Here is a striked text--\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<strike class=\"strike\">Here is a striked text</strike>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Wiki Links</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\nWiki Links:\r\n\r\n* [WebHome]\r\n* [Web Home]\r\n* [web home>WebHome]\r\n* [Main.WebHome]\r\n* [web home|Main.WebHome]\r\n\r\n\r\n\r\n</pre>\r\n</td>\r\n<td>\r\nWiki Links:\r\n<ul class=\"star\">\r\n<li><span class=\"wikilink\"><a href=\"/xwiki/bin/view/Doc/WebHome\">WebHome</a></span></li>\r\n<li><span class=\"wikilink\"><a href=\"/xwiki/bin/view/Doc/WebHome\">Web Home</a></span></li>\r\n<li><span class=\"wikilink\"><a href=\"/xwiki/bin/view/Doc/WebHome\">web home</a></span></li>\r\n<li><span class=\"wikilink\"><a href=\"/xwiki/bin/view/Main/WebHome\">Main.WebHome</a></span></li>\r\n<li><span class=\"wikilink\"><a href=\"/xwiki/bin/view/Main/WebHome\">web home</a></span></li>\r\n</ul></td>\r\n</tr>\r\n\r\n<tr>\r\n<td>\r\n<b class=\"bold\">External Links</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\n* http://www.xwiki.org\r\n* [http://www.xwiki.org]\r\n* [lien xwiki.org>http://www.xwiki.org]\r\n* {link:lien xwiki.org|http://www.xwiki.org}\r\n\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<ul class=\"star\">\r\n<li><span class=\"nobr\"><a href=\"http://www.xwiki.org\">&#104;ttp://www.xwiki.org</a></span></li>\r\n<li><span class=\"wikiexternallink\"><a href=\"http://www.xwiki.org\">&#104;ttp://www.xwiki.org</a></span></li>\r\n<li><span class=\"wikiexternallink\"><a href=\"http://www.xwiki.org\">&#108;ien xwiki.org</a></span></li>\r\n\r\n<li><span class=\"nobr\"><a href=\"http://www.xwiki.org\">lien xwiki.org</a></span></li>\r\n</ul></td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Quotes</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\n{quote:http://www.xwiki.org}\r\nHere is a text with a quote\r\n{quote}\r\n\r\n{quote:http://www.xwiki.org}\r\n~~Here is a text with a quote~~\r\n{quote}\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<blockquote class=\"quote\">\r\nHere is a text with a quote\r\n\r\n<a href=\"http://www.xwiki.org\">Source</a></blockquote><p class=\"paragraph\"/><blockquote class=\"quote\">\r\n<i class=\"italic\">Here is a text with a quote</i>\r\n<a href=\"http://www.xwiki.org\">Source</a></blockquote>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Tables</b><br/>\r\nThe syntax for tables allows to easily create content in table format. It even allows to make some calculation on the content !\r\n</td>\r\n<td>\r\n<pre>\r\n\r\n{table}\r\nTitle 1 | Title 2\r\nWord 1 | Word 2\r\n{table}\r\n\r\n{table}\r\nCategory | Sales (K€)\r\nCategory 1 | 100\r\nCategory 2 | 50\r\nCategory 3 | 50\r\nTotal | =sum(B2:B4)\r\n{table}\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<table class=\"wiki-table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><th>Title 1</th><th>Title 2</th></tr><tr class=\"table-odd\"><td>Word 1</td><td>Word 2</td></tr></table><p class=\"paragraph\"/><table class=\"wiki-table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><th>Category</th><th>Sales (K€)</th></tr><tr class=\"table-odd\"><td>Category 1</td><td>100</td></tr><tr class=\"table-even\"><td>Category 2</td><td>50</td></tr><tr class=\"table-odd\"><td>Category 3</td><td>50</td></tr><tr class=\"table-even\"><td>Total</td><td>200</td></tr></table><p class=\"paragraph\"/>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Document Includes</b><br/>\r\nUsing XWiki you can include documents inside other documents. This can be usefull to create complex documents using multiple document an then convert them in PDF. This also allows to create forms to present object data.\r\n</td>\r\n<td>\r\n#&#105;ncludeTopic(\"Doc.TestInclude\")\r\n<br />\r\n#&#105;ncludeForm(\"Doc.TestInclude\")\r\n</td>\r\n<td>\r\n#includeTopic(\"Doc.TestInclude\")\r\n<br />\r\n#includeForm(\"Doc.TestInclude\")\r\n</td>\r\n</tr>\r\n</table>','head	1.16;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.16\ndate	2004.08.20.13.03.24;	author tomcat;	state Exp;\nbranches;\nnext	1.15;\n\n1.15\ndate	2004.08.20.13.03.12;	author tomcat;	state Exp;\nbranches;\nnext	1.14;\n\n1.14\ndate	2004.08.20.13.03.02;	author tomcat;	state Exp;\nbranches;\nnext	1.13;\n\n1.13\ndate	2004.08.20.13.02.47;	author tomcat;	state Exp;\nbranches;\nnext	1.12;\n\n1.12\ndate	2004.08.20.13.02.08;	author tomcat;	state Exp;\nbranches;\nnext	1.11;\n\n1.11\ndate	2004.08.20.13.01.41;	author tomcat;	state Exp;\nbranches;\nnext	1.10;\n\n1.10\ndate	2004.08.20.13.00.54;	author tomcat;	state Exp;\nbranches;\nnext	1.9;\n\n1.9\ndate	2004.08.20.13.00.08;	author tomcat;	state Exp;\nbranches;\nnext	1.8;\n\n1.8\ndate	2004.08.20.12.57.59;	author tomcat;	state Exp;\nbranches;\nnext	1.7;\n\n1.7\ndate	2004.08.20.12.57.08;	author tomcat;	state Exp;\nbranches;\nnext	1.6;\n\n1.6\ndate	2004.08.20.12.56.50;	author tomcat;	state Exp;\nbranches;\nnext	1.5;\n\n1.5\ndate	2004.08.20.12.41.36;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.12.41.15;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.12.39.41;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.16\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Doc</web>\n<name>XWikiSyntax</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent>Doc.WebHome</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382884</creationDate>\n<date>1092999804749</date>\n<version>1.16</version>\n<content>1 The Wiki Syntax\n\nXWiki allows to create internet pages without using HTML thanks to the Wiki Syntax. This Wiki Syntax is a simple way to modify the aspect of a document as well as create links between documents.\n\nThis document explains the wiki syntax. \n\n{pre}\n&lt;table class=\"wiki-table\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\" width=\"80%\"&gt;\n&lt;tr&gt;&lt;th width=\"25%\"&gt;Commande:&lt;/th&gt;\n&lt;th width=\"10%\"&gt;Exemple:&lt;/th&gt;\n&lt;th width=\"10%\"&gt;Resultat:&lt;/th&gt;\n\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Titles&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n1 Titre 1\n\n1.1 Titre 2\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;h3 class=\"heading-1\"&gt;Titre 1&lt;/h3&gt;\n&lt;h3 class=\"heading-1-1\"&gt;Titre 2&lt;/h3&gt;\n&lt;/td&gt;\n\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Paragraphs&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\nHere is a paragraph.\nThis text continues the same paragraph.\n\nThis paragraph is a new one.\n\nThis paragraph is another new one.\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\nHere is a paragraph.\nThis text continues the same paragraph.&lt;p class=\"paragraph\"/&gt;This paragraph is a new one.&lt;p class=\"paragraph\"/&gt;This paragraph is another new one.\n&lt;/td&gt;\n&lt;/tr&gt;\n\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Lists&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n* List item\n* List item\n\n   * Recursive list\n      * Recursive list\n      * Recursive list\n      * Recursive list\n   * Recursive list\n      * Recursive list\n\n   \n1. Enumerated list\n1. Enumerated list\n\na. Alphabetical enumerated list\na. Alphabetical enumerated list\n\nA. Uppercase alphabetical enumerated list\nA. Uppercase alphabetical enumerated list\n\ni. Roman enumerated list\ni. Roman enumerated list\n\nI. Uppercase roman enumerated list\nI. Uppercase roman enumerated list\n\ng. Greek enumerated list\ng. Greek enumerated list\n\nh. Hirigana (jap.) enumerated list\nh. Hirigana (jap.) enumerated list\n\nk. Katakana (jap.) enumerated list\nk. Katakana (jap.) enumerated list\n\n \n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;ul class=\"star\"&gt;\n&lt;li&gt;List item&lt;/li&gt;\n&lt;li&gt;List item&lt;/li&gt;\n&lt;li&gt;Recursive list&lt;/li&gt;\n\n&lt;li&gt;Recursive list&lt;/li&gt;\n&lt;li&gt;Recursive list&lt;/li&gt;\n&lt;li&gt;Recursive list&lt;/li&gt;\n&lt;li&gt;Recursive list&lt;/li&gt;\n&lt;li&gt;Recursive list&lt;/li&gt;\n&lt;/ul&gt;\n&lt;ol&gt;\n&lt;li&gt;Enumerated list&lt;/li&gt;\n&lt;li&gt;Enumerated list&lt;/li&gt;\n&lt;/ol&gt;\n\n&lt;ol class=\"alpha\"&gt;\n&lt;li&gt;Alphabetical enumerated list&lt;/li&gt;\n&lt;li&gt;Alphabetical enumerated list&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"ALPHA\"&gt;\n&lt;li&gt;Uppercase alphabetical enumerated list&lt;/li&gt;\n&lt;li&gt;Uppercase alphabetical enumerated list&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"roman\"&gt;\n&lt;li&gt;Roman enumerated list&lt;/li&gt;\n&lt;li&gt;Roman enumerated list&lt;/li&gt;\n\n&lt;/ol&gt;\n&lt;ol class=\"ROMAN\"&gt;\n&lt;li&gt;Uppercase roman enumerated list&lt;/li&gt;\n&lt;li&gt;Uppercase roman enumerated list&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"greek\"&gt;\n&lt;li&gt;Greek enumerated list&lt;/li&gt;\n&lt;li&gt;Greek enumerated list&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"hiragana\"&gt;\n&lt;li&gt;Hirigana (jap.) enumerated list&lt;/li&gt;\n&lt;li&gt;Hirigana (jap.) enumerated list&lt;/li&gt;\n\n&lt;/ol&gt;\n&lt;ol class=\"katakana\"&gt;\n&lt;li&gt;Katakana (jap.) enumerated list&lt;/li&gt;\n&lt;li&gt;Katakana (jap.) enumerated list&lt;/li&gt;\n&lt;/ol&gt; \n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Bold&lt;/b&gt;&lt;br/&gt;\nThere are two ways to makr text as bold.\n&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\nHere is a text with a *bold* word.\nHere is a text with a __bold__ word.\n\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\nHere is a text with a &lt;strong class=\"strong\"&gt;bold&lt;/strong&gt; word.\nHere is a text with a &lt;b class=\"bold\"&gt;bold&lt;/b&gt; word.&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Italics&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n\n&lt;td&gt;\n&lt;pre&gt;\n\n~~Here is a text in italics~~\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;i class=\"italic\"&gt;Here is a text in italics&lt;/i&gt;\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Striked Text&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n--Here is a striked text--\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;strike class=\"strike\"&gt;Here is a striked text&lt;/strike&gt;\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Wiki Links&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\nWiki Links:\n\n* [WebHome]\n* [Web Home]\n* [web home&gt;WebHome]\n* [Main.WebHome]\n* [web home|Main.WebHome]\n\n\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\nWiki Links:\n&lt;ul class=\"star\"&gt;\n&lt;li&gt;&lt;span class=\"wikilink\"&gt;&lt;a href=\"/xwiki/bin/view/Doc/WebHome\"&gt;WebHome&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikilink\"&gt;&lt;a href=\"/xwiki/bin/view/Doc/WebHome\"&gt;Web Home&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikilink\"&gt;&lt;a href=\"/xwiki/bin/view/Doc/WebHome\"&gt;web home&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikilink\"&gt;&lt;a href=\"/xwiki/bin/view/Main/WebHome\"&gt;Main.WebHome&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikilink\"&gt;&lt;a href=\"/xwiki/bin/view/Main/WebHome\"&gt;web home&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;/ul&gt;&lt;/td&gt;\n&lt;/tr&gt;\n\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;External Links&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n* http://www.xwiki.org\n* [http://www.xwiki.org]\n* [lien xwiki.org&gt;http://www.xwiki.org]\n* {link:lien xwiki.org|http://www.xwiki.org}\n\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;ul class=\"star\"&gt;\n&lt;li&gt;&lt;span class=\"nobr\"&gt;&lt;a href=\"http://www.xwiki.org\"&gt;&amp;#104;ttp://www.xwiki.org&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikiexternallink\"&gt;&lt;a href=\"http://www.xwiki.org\"&gt;&amp;#104;ttp://www.xwiki.org&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikiexternallink\"&gt;&lt;a href=\"http://www.xwiki.org\"&gt;&amp;#108;ien xwiki.org&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n\n&lt;li&gt;&lt;span class=\"nobr\"&gt;&lt;a href=\"http://www.xwiki.org\"&gt;lien xwiki.org&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;/ul&gt;&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Quotes&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n{quote:http://www.xwiki.org}\nHere is a text with a quote\n{quote}\n\n{quote:http://www.xwiki.org}\n~~Here is a text with a quote~~\n{quote}\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;blockquote class=\"quote\"&gt;\nHere is a text with a quote\n\n&lt;a href=\"http://www.xwiki.org\"&gt;Source&lt;/a&gt;&lt;/blockquote&gt;&lt;p class=\"paragraph\"/&gt;&lt;blockquote class=\"quote\"&gt;\n&lt;i class=\"italic\"&gt;Here is a text with a quote&lt;/i&gt;\n&lt;a href=\"http://www.xwiki.org\"&gt;Source&lt;/a&gt;&lt;/blockquote&gt;\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Tables&lt;/b&gt;&lt;br/&gt;\nThe syntax for tables allows to easily create content in table format. It even allows to make some calculation on the content !\n&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n{table}\nTitle 1 | Title 2\nWord 1 | Word 2\n{table}\n\n{table}\nCategory | Sales (K€)\nCategory 1 | 100\nCategory 2 | 50\nCategory 3 | 50\nTotal | =sum(B2:B4)\n{table}\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;table class=\"wiki-table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"&gt;&lt;tr&gt;&lt;th&gt;Title 1&lt;/th&gt;&lt;th&gt;Title 2&lt;/th&gt;&lt;/tr&gt;&lt;tr class=\"table-odd\"&gt;&lt;td&gt;Word 1&lt;/td&gt;&lt;td&gt;Word 2&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;p class=\"paragraph\"/&gt;&lt;table class=\"wiki-table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"&gt;&lt;tr&gt;&lt;th&gt;Category&lt;/th&gt;&lt;th&gt;Sales (K€)&lt;/th&gt;&lt;/tr&gt;&lt;tr class=\"table-odd\"&gt;&lt;td&gt;Category 1&lt;/td&gt;&lt;td&gt;100&lt;/td&gt;&lt;/tr&gt;&lt;tr class=\"table-even\"&gt;&lt;td&gt;Category 2&lt;/td&gt;&lt;td&gt;50&lt;/td&gt;&lt;/tr&gt;&lt;tr class=\"table-odd\"&gt;&lt;td&gt;Category 3&lt;/td&gt;&lt;td&gt;50&lt;/td&gt;&lt;/tr&gt;&lt;tr class=\"table-even\"&gt;&lt;td&gt;Total&lt;/td&gt;&lt;td&gt;200&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;p class=\"paragraph\"/&gt;\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Document Includes&lt;/b&gt;&lt;br/&gt;\nUsing XWiki you can include documents inside other documents. This can be usefull to create complex documents using multiple document an then convert them in PDF. This also allows to create forms to present object data.\n&lt;/td&gt;\n&lt;td&gt;\n#&amp;#105;ncludeTopic(\"Doc.TestInclude\")\n&lt;br /&gt;\n#&amp;#105;ncludeForm(\"Doc.TestInclude\")\n&lt;/td&gt;\n&lt;td&gt;\n#includeTopic(\"Doc.TestInclude\")\n&lt;br /&gt;\n#includeForm(\"Doc.TestInclude\")\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;/table&gt;</content>\n</xwikidoc>\n@\n\n\n1.15\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999792355</date>\n<version>1.15</version>\nd327 1\na327 3\n&lt;/table&gt;\n{/pre}\n&lt;br /&gt;</content>\n@\n\n\n1.14\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999782804</date>\n<version>1.14</version>\nd328 1\na328 1\n{pre}\n@\n\n\n1.13\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999767835</date>\n<version>1.13</version>\nd327 3\na329 1\n&lt;/table&gt;</content>\n@\n\n\n1.12\nlog\n@@\ntext\n@d12 9\na20 3\n<date>1092999728551</date>\n<version>1.12</version>\n<content>{pre}\n@\n\n\n1.11\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999701036</date>\n<version>1.11</version>\nd321 1\na321 2\n&lt;/table&gt;\n{/pre}</content>\n@\n\n\n1.10\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999654292</date>\n<version>1.10</version>\na298 2\n\n\na302 1\n\nd312 1\n@\n\n\n1.9\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999607998</date>\n<version>1.9</version>\na309 28\n&lt;b class=\"bold\"&gt;No wiki rendering but HTML rendering&lt;/b&gt;&lt;br/&gt;\nThis allows to show wiki syntax in a wiki page.\n&lt;/td&gt;\n&lt;td&gt;\nNo *bold rendering* in wiki syntax.\n&lt;/td&gt;\n&lt;td&gt;\nNo *bold rendering* in wiki syntax.\n&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;No Wiki rendering and no HTML rendering&lt;/b&gt;&lt;br/&gt;\nThis allows to show Wiki and HTML content in a Wiki page.\n&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\nNo &lt;b&gt;bold rendering&lt;/b&gt; in HTML.\nNo *bold rendering in* wiki syntax.\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;div class=\"code\"&gt;&lt;pre&gt;No &lt;span class=\"xml&amp;#45;tag\"&gt;&amp;#60;b&amp;#62;&lt;/span&gt;bold rendering&lt;span class=\"xml&amp;#45;tag\"&gt;&amp;#60;/b&amp;#62;&lt;/span&gt; in HTML.\nNo &amp;#42;bold rendering in&amp;#42; wiki syntax.&lt;/pre&gt;&lt;/div&gt;\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\na313 1\n{code}\na315 2\n{code}\n&lt;/pre&gt;\n@\n\n\n1.8\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999479215</date>\n<version>1.8</version>\na313 3\n&lt;pre&gt;\n\n\na314 2\n&lt;p class=\"paragraph\"/&gt;{/pre}\n&lt;/pre&gt;\na316 2\n\n{/pre}\na326 2\n\n{code:xml}\na328 2\n{code}\n\na329 1\n\na338 1\n\n@\n\n\n1.7\nlog\n@@\ntext\n@d12 4\na15 3\n<date>1092999428312</date>\n<version>1.7</version>\n<content>&lt;table class=\"wiki-table\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\" width=\"80%\"&gt;\nd367 2\na368 2\n&lt;/table&gt;&lt;p class=\"paragraph\"/&gt;&lt;br /&gt;\n&lt;br /&gt;</content>\n@\n\n\n1.6\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999410947</date>\n<version>1.6</version>\nd355 2\na356 2\n#&amp;#105;includeTopic(\"Doc.TestInclude\")\n#&amp;#105;includeForm(\"Doc.TestInclude\")\n@\n\n\n1.5\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092998496588</date>\n<version>1.5</version>\nd355 2\na356 2\n#!includeTopic(\"Doc.TestInclude\")\n#!includeForm(\"Doc.TestInclude\")\nd361 3\na363 1\n#includeTopic(\"Doc.TestInclude\")&lt;p class=\"paragraph\"/&gt;#includeForm(\"Doc.TestInclude\")\n@\n\n\n1.4\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092998475135</date>\n<version>1.4</version>\nd355 2\na356 2\n#includeTopic(\"Doc.TestInclude\")\n#includeForm(\"Doc.TestInclude\")\n@\n\n\n1.3\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092998381043</date>\n<version>1.3</version>\nd354 1\na354 2\n&lt;pre&gt;\n\na355 1\n\nd357 1\na357 1\n\n@\n\n\n1.2\nlog\n@Doc.XWikiSyntax\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 356\na367 3\n<date>1092994382885</date>\n<version>1.2</version>\n<content>#includeTopic(\"xwiki:Doc.XWikiSyntax\")</content>\n@\n','1.16','Doc.WebHome');
INSERT INTO xwikidoc VALUES (1492600722,'XWikiUserSheet','','en',0,'2004-08-20 13:11:46','2004-08-20 11:33:02','XWiki.Admin','','XWiki','1 User $doc.name\r\n\r\n#foreach($class in $doc.xWikiClasses)\r\n#set( $nb = $doc.getObjectNumbers(\"${class.name}\"))\r\n#if ($nb==1)\r\n#set( $obj = $doc.getObject(\"${class.name}\",0))\r\n<table border=\"1\" cellspacing=\"0\" cellpadding=\"2\">\r\n#foreach($prop in $class.properties)\r\n<tr><td> *${prop.prettyName}* </td>\r\n<td>$doc.display($prop.getName(), $obj)</td>\r\n#end\r\n</tr>\r\n</table>\r\n#else\r\n$doc.displayForm(\"${class.name}\")\r\n#end\r\n<br />\r\n<br />\r\n#end\r\n','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.13.11.46;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiUserSheet</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.XWikiUsers</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382903</creationDate>\n<date>1093000306442</date>\n<version>1.3</version>\n<content>1 User $doc.name\n\n#foreach($class in $doc.xWikiClasses)\n#set( $nb = $doc.getObjectNumbers(\"${class.name}\"))\n#if ($nb==1)\n#set( $obj = $doc.getObject(\"${class.name}\",0))\n&lt;table border=\"1\" cellspacing=\"0\" cellpadding=\"2\"&gt;\n#foreach($prop in $class.properties)\n&lt;tr&gt;&lt;td&gt; *${prop.prettyName}* &lt;/td&gt;\n&lt;td&gt;$doc.display($prop.getName(), $obj)&lt;/td&gt;\n#end\n&lt;/tr&gt;\n&lt;/table&gt;\n#else\n$doc.displayForm(\"${class.name}\")\n#end\n&lt;br /&gt;\n&lt;br /&gt;\n#end\n</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.XWikiUserSheet\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382904</date>\n<version>1.2</version>\n@\n','1.3','XWiki.XWikiUsers');
INSERT INTO xwikidoc VALUES (271752628,'XWikiUserSheet','fr','',1,'2004-08-20 13:11:36','2004-08-20 11:33:02','XWiki.Admin','','XWiki','1 Utilisateur $doc.name\r\n\r\n#foreach($class in $doc.xWikiClasses)\r\n#set( $nb = $doc.getObjectNumbers(\"${class.name}\"))\r\n#if ($nb==1)\r\n#set( $obj = $doc.getObject(\"${class.name}\",0))\r\n<table border=\"1\" cellspacing=\"0\" cellpadding=\"2\">\r\n#foreach($prop in $class.properties)\r\n<tr><td> *${prop.prettyName}* </td>\r\n<td>$doc.display($prop.getName(), $obj)</td>\r\n#end\r\n</tr>\r\n</table>\r\n#else\r\n$doc.displayForm(\"${class.name}\")\r\n#end\r\n<br />\r\n<br />\r\n#end\r\n','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.13.11.36;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiUserSheet</name>\n<language>fr</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382922</creationDate>\n<date>1093000296071</date>\n<version>1.3</version>\n<content>1 Utilisateur $doc.name\n\n#foreach($class in $doc.xWikiClasses)\n#set( $nb = $doc.getObjectNumbers(\"${class.name}\"))\n#if ($nb==1)\n#set( $obj = $doc.getObject(\"${class.name}\",0))\n&lt;table border=\"1\" cellspacing=\"0\" cellpadding=\"2\"&gt;\n#foreach($prop in $class.properties)\n&lt;tr&gt;&lt;td&gt; *${prop.prettyName}* &lt;/td&gt;\n&lt;td&gt;$doc.display($prop.getName(), $obj)&lt;/td&gt;\n#end\n&lt;/tr&gt;\n&lt;/table&gt;\n#else\n$doc.displayForm(\"${class.name}\")\n#end\n&lt;br /&gt;\n&lt;br /&gt;\n#end\n</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.XWikiUserSheet\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382923</date>\n<version>1.2</version>\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (-408956569,'XWikiUserTemplate','','en',0,'2004-08-20 13:12:01','2004-08-20 11:33:02','XWiki.Admin','','XWiki','#includeForm(\"XWiki.XWikiUserSheet\")','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.13.12.01;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiUserTemplate</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.XWikiUsers</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382937</creationDate>\n<date>1093000321039</date>\n<version>1.3</version>\n<content>#includeForm(\"XWiki.XWikiUserSheet\")</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.XWikiUserTemplate\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382938</date>\n<version>1.2</version>\n@\n','1.3','XWiki.XWikiUsers');
INSERT INTO xwikidoc VALUES (874874484,'WebHome','','en',0,'2004-09-15 01:36:27','2004-08-20 11:33:02','xwiki:XWiki.LudovicDubost','','Admin','* [XWiki.XWikiUsers]\r\n* [XWiki.XWikiGroups]\r\n* [RSS>Main.WebRss?xpage=rdf]','head	1.5;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.5\ndate	2004.09.15.01.36.27;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.13.19.04;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.13.12.28;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.5\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Admin</web>\n<name>WebHome</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>xwiki:XWiki.LudovicDubost</author>\n<creationDate>1092994382000</creationDate>\n<date>1095204987631</date>\n<version>1.5</version>\n<content>* [XWiki.XWikiUsers]\n* [XWiki.XWikiGroups]\n* [RSS&gt;Main.WebRss?xpage=rdf]</content>\n</xwikidoc>\n@\n\n\n1.4\nlog\n@@\ntext\n@d10 1\na10 1\n<author>XWiki.Admin</author>\nd12 2\na13 2\n<date>1093000744333</date>\n<version>1.4</version>\nd16 1\na16 1\n* [RSS&gt;Main.WebRss]</content>\n@\n\n\n1.3\nlog\n@@\ntext\n@d7 1\na7 1\n<defaultLanguage>fr</defaultLanguage>\nd11 3\na13 3\n<creationDate>1092994382956</creationDate>\n<date>1093000348008</date>\n<version>1.3</version>\n@\n\n\n1.2\nlog\n@Admin.WebHome\n@\ntext\n@d10 1\na10 1\n<author>xwiki:XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994382957</date>\n<version>1.2</version>\nd15 1\n@\n','1.5','');
INSERT INTO xwikidoc VALUES (-980492554,'XWikiComments','','fr',0,'2004-08-20 13:14:08','2004-08-20 11:33:02','XWiki.Admin','','XWiki','\n','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.13.14.08;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.02;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiComments</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994382985</creationDate>\n<date>1093000448893</date>\n<version>1.3</version>\n<class>\n<name>XWiki.XWikiComments</name>\n<highlight>\n<prettyName>Highlighted Text</prettyName>\n<rows>2</rows>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>highlight</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</highlight>\n<comment>\n<prettyName>Comment</prettyName>\n<rows>5</rows>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>comment</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</comment>\n<date>\n<prettyName>Date</prettyName>\n<emptyIsToday>1</emptyIsToday>\n<dateFormat>dd/MM/yyyy HH:mm:ss</dateFormat>\n<unmodifiable>0</unmodifiable>\n<size>20</size>\n<name>date</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.DateClass</classType>\n</date>\n<author>\n<prettyName>Author</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>author</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</author>\n</class>\n<content>\n</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.XWikiComments\n@\ntext\n@d7 1\na7 1\n<defaultLanguage></defaultLanguage>\nd10 1\na10 1\n<author></author>\nd12 2\na13 2\n<date>1092994382986</date>\n<version>1.2</version>\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (493697236,'XWikiSkins','','fr',0,'2004-08-20 13:14:03','2004-08-20 11:33:03','XWiki.Admin','','XWiki','\n','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.13.14.03;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.33.03;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiSkins</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994383025</creationDate>\n<date>1093000443777</date>\n<version>1.3</version>\n<class>\n<name>XWiki.XWikiSkins</name>\n<header.vm>\n<prettyName>Header</prettyName>\n<rows>15</rows>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>header.vm</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</header.vm>\n<footer.vm>\n<prettyName>Footer</prettyName>\n<rows>15</rows>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>footer.vm</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</footer.vm>\n<name>\n<prettyName>Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>name</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</name>\n<style.css>\n<prettyName>style.css</prettyName>\n<rows>15</rows>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>style.css</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</style.css>\n</class>\n<content>\n</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.XWikiSkins\n@\ntext\n@d7 1\na7 1\n<defaultLanguage></defaultLanguage>\nd10 1\na10 1\n<author></author>\nd12 2\na13 2\n<date>1092994383026</date>\n<version>1.2</version>\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (1287555159,'XWikiGroupTemplate','','fr',0,'2004-08-20 12:13:51','2004-08-20 12:10:07','XWiki.Admin','XWiki.Admin','XWiki','#includeForm(\"XWiki.XWikiGroupSheet\")','head	1.5;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.5\ndate	2004.08.20.12.13.51;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.12.13.47;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.12.13.42;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.12.11.16;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.5\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiGroupTemplate</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.XWikiGroups</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092996607827</creationDate>\n<date>1092996831217</date>\n<version>1.5</version>\n<object>\n<class>\n<name>XWiki.XWikiGroups</name>\n<member>\n<prettyName>Member</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>member</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</member>\n</class>\n<name>XWiki.XWikiGroupTemplate</name>\n<number>0</number>\n<className>XWiki.XWikiGroups</className>\n<property>\n<member></member>\n</property>\n</object>\n<object>\n<class>\n<name>XWiki.XWikiGroups</name>\n<member>\n<prettyName>Member</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>member</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</member>\n</class>\n<name>XWiki.XWikiGroupTemplate</name>\n<number>1</number>\n<className>XWiki.XWikiGroups</className>\n<property>\n<member></member>\n</property>\n</object>\n<content>#includeForm(\"XWiki.XWikiGroupSheet\")</content>\n</xwikidoc>\n@\n\n\n1.4\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092996827937</date>\n<version>1.4</version>\nd29 3\nd48 3\n@\n\n\n1.3\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092996822376</date>\n<version>1.3</version>\nd30 16\n@\n\n\n1.2\nlog\n@XWiki.XWikiGroupTemplate\n@\ntext\n@d12 18\na29 2\n<date>1092996676551</date>\n<version>1.2</version>\n@\n','1.5','XWiki.XWikiGroups');
INSERT INTO xwikidoc VALUES (-1283623262,'XWikiGroupSheet','','fr',0,'2004-08-20 12:17:34','2004-08-20 12:11:17','XWiki.Admin','XWiki.Admin','XWiki','1 Group $doc.name\r\n\r\nGroup Members: \r\n\r\n#if ($context.action==\"inline\")\r\n$doc.displayForm(\"XWiki.XWikiGroups\")\r\n#else\r\n#foreach($objlist in $doc.xWikiObjects)\r\n  #foreach($obj in $objlist)\r\n#set($propclass = $obj.xWikiClass.member)   \r\n#if ($propclass)\r\n   * [${doc.displayView($propclass, \"\", $obj)}]\r\n#end\r\n #end\r\n#end\r\n#end\r\n\r\nAdd members to this group by editing the \'member\' field.','head	1.7;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.7\ndate	2004.08.20.12.17.34;	author tomcat;	state Exp;\nbranches;\nnext	1.6;\n\n1.6\ndate	2004.08.20.12.16.00;	author tomcat;	state Exp;\nbranches;\nnext	1.5;\n\n1.5\ndate	2004.08.20.12.15.42;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.12.14.56;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.12.11.45;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.12.11.28;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.7\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiGroupSheet</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.XWikiGroups</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092996677401</creationDate>\n<date>1092997054034</date>\n<version>1.7</version>\n<content>1 Group $doc.name\n\nGroup Members: \n\n#if ($context.action==\"inline\")\n$doc.displayForm(\"XWiki.XWikiGroups\")\n#else\n#foreach($objlist in $doc.xWikiObjects)\n  #foreach($obj in $objlist)\n#set($propclass = $obj.xWikiClass.member)   \n#if ($propclass)\n   * [${doc.displayView($propclass, \"\", $obj)}]\n#end\n #end\n#end\n#end\n\nAdd members to this group by editing the \'member\' field.</content>\n</xwikidoc>\n@\n\n\n1.6\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092996960239</date>\n<version>1.6</version>\nd18 3\na24 3\n#if ($context.action==\"inline\")\n   * ${doc.displayView($propclass, \"\", $obj)}\n#else\na26 1\n#end\nd29 1\n@\n\n\n1.5\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092996942204</date>\n<version>1.5</version>\na15 2\n$context.action\n\nd23 2\na25 2\n#else\n   * ${doc.displayView($propclass, \"\", $obj)}\n@\n\n\n1.4\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092996896808</date>\n<version>1.4</version>\nd16 2\n@\n\n\n1.3\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092996705693</date>\n<version>1.3</version>\nd22 1\nd24 2\nd27 1\n@\n\n\n1.2\nlog\n@XWiki.XWikiGroupSheet\n@\ntext\n@d9 1\na9 1\n<parent></parent>\nd12 2\na13 2\n<date>1092996688608</date>\n<version>1.2</version>\n@\n','1.7','XWiki.XWikiGroups');
INSERT INTO xwikidoc VALUES (57622864,'XWikiPreferencesTemplate','','fr',0,'2004-08-20 12:07:44','2004-08-20 11:40:23','XWiki.Admin','XWiki.LudovicDubost','XWiki','#set($object = $doc.getObject(\"XWiki.XWikiPreferences\",0))\r\n1 Preferences\r\n\r\n1.1 Presentation\r\n\r\n1.1.1 Ces paramètres peuvent être modifiés pour chaque utilisateur\r\n\r\n   * Skin: $doc.display(\"skin\", $object)\r\n   * Style par défaut: $doc.display(\"stylesheet\", $object)\r\n   * Styles alternatifs: $doc.display(\"stylesheets\", $object)\r\n\r\n1.1.1 Ces paramètres peuvent être modifiés pour chaque web\r\n\r\n   * Copyright:  $doc.display(\"webcopyright\", $object)\r\n   * Titre:  $doc.display(\"title\", $object) \r\n   * Version:  $doc.display(\"version\", $object) \r\n\r\n1.1.1 Barre de Menu\r\n\r\nLa barre de menu peut contenir de l\'HTML:\r\n\r\n$doc.display(\"menu\", $object)\r\n\r\n1.1 Avancé\r\n\r\n#if ($request.requestURI.indexOf(\"inline\")!=-1)\r\n   * Barre d\'Actions: $doc.display(\"pagemenu\", $object)\r\n#else\r\n   * Barre d\'Actions:\r\n\r\n{code:xml}\r\n$doc.display(\"pagemenu\", $object)\r\n{code}\r\n\r\n#end\r\n#if ($request.requestURI.indexOf(\"inline\")!=-1)\r\n   * Meta-Données HTTP: $doc.display(\"meta\", $object)\r\n#else\r\n   * Meta-Données HTTP:\r\n\r\n{code:xml}\r\n$doc.display(\"meta\", $object)\r\n{code}\r\n\r\n#end\r\n\r\n#if ($request.requestURI.indexOf(\"inline\")!=-1)\r\n   * Style: $doc.display(\"style\", $object)\r\n#else\r\n   * Style: \r\n\r\n{code:xml}\r\n$doc.display(\"style\", $object)\r\n{code}\r\n\r\n#end\r\n\r\n1.1 Enregistrement\r\n\r\n   * Serveur SMTP: $doc.display(\"smtp_server\", $object)\r\n   * e-Mail de l\'Administrateur: $doc.display(\"admin_email\", $object)\r\n#if ($request.requestURI.indexOf(\"inline\")!=-1)\r\n   * e-Mail de validation: $doc.display(\"validation_email_content\", $object)\r\n#else\r\n   * e-Mail de validation:\r\n\r\n{code:pre}\r\n$doc.display(\"validation_email_content\", $object)\r\n{code}\r\n\r\n#end\r\n#if ($request.requestURI.indexOf(\"inline\")!=-1)\r\n   * e-Mail de confirmation: $doc.display(\"confirmation_email_content\", $object)\r\n#else\r\n   * e-Mail de confirmation:\r\n\r\n{code:pre}\r\n$doc.display(\"confirmation_email_content\", $object)\r\n{code}\r\n#end','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.12.07.44;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.11.40.45;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiPreferencesTemplate</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092994823899</creationDate>\n<date>1092996464572</date>\n<version>1.3</version>\n<content>#set($object = $doc.getObject(\"XWiki.XWikiPreferences\",0))\n1 Preferences\n\n1.1 Presentation\n\n1.1.1 Ces paramètres peuvent être modifiés pour chaque utilisateur\n\n   * Skin: $doc.display(\"skin\", $object)\n   * Style par défaut: $doc.display(\"stylesheet\", $object)\n   * Styles alternatifs: $doc.display(\"stylesheets\", $object)\n\n1.1.1 Ces paramètres peuvent être modifiés pour chaque web\n\n   * Copyright:  $doc.display(\"webcopyright\", $object)\n   * Titre:  $doc.display(\"title\", $object) \n   * Version:  $doc.display(\"version\", $object) \n\n1.1.1 Barre de Menu\n\nLa barre de menu peut contenir de l\'HTML:\n\n$doc.display(\"menu\", $object)\n\n1.1 Avancé\n\n#if ($request.requestURI.indexOf(\"inline\")!=-1)\n   * Barre d\'Actions: $doc.display(\"pagemenu\", $object)\n#else\n   * Barre d\'Actions:\n\n{code:xml}\n$doc.display(\"pagemenu\", $object)\n{code}\n\n#end\n#if ($request.requestURI.indexOf(\"inline\")!=-1)\n   * Meta-Données HTTP: $doc.display(\"meta\", $object)\n#else\n   * Meta-Données HTTP:\n\n{code:xml}\n$doc.display(\"meta\", $object)\n{code}\n\n#end\n\n#if ($request.requestURI.indexOf(\"inline\")!=-1)\n   * Style: $doc.display(\"style\", $object)\n#else\n   * Style: \n\n{code:xml}\n$doc.display(\"style\", $object)\n{code}\n\n#end\n\n1.1 Enregistrement\n\n   * Serveur SMTP: $doc.display(\"smtp_server\", $object)\n   * e-Mail de l\'Administrateur: $doc.display(\"admin_email\", $object)\n#if ($request.requestURI.indexOf(\"inline\")!=-1)\n   * e-Mail de validation: $doc.display(\"validation_email_content\", $object)\n#else\n   * e-Mail de validation:\n\n{code:pre}\n$doc.display(\"validation_email_content\", $object)\n{code}\n\n#end\n#if ($request.requestURI.indexOf(\"inline\")!=-1)\n   * e-Mail de confirmation: $doc.display(\"confirmation_email_content\", $object)\n#else\n   * e-Mail de confirmation:\n\n{code:pre}\n$doc.display(\"confirmation_email_content\", $object)\n{code}\n#end</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@XWiki.XWikiPreferencesTemplate\n@\ntext\n@d10 1\na10 1\n<author>XWiki.LudovicDubost</author>\nd12 2\na13 2\n<date>1092994845810</date>\n<version>1.2</version>\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (1936882153,'Admin','','fr',0,'2004-08-20 13:13:33','2004-08-20 11:55:30','XWiki.Admin','','XWiki','#includeForm(\"XWiki.XWikiUserSheet\")','head	1.2;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.2\ndate	2004.08.20.13.13.33;	author tomcat;	state Exp;\nbranches;\nnext	1.1;\n\n1.1\ndate	2004.08.20.11.55.30;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.2\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>Admin</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.XWikiUsers</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092995730462</creationDate>\n<date>1093000413747</date>\n<version>1.2</version>\n<object>\n<class>\n<name>XWiki.XWikiRights</name>\n<groups>\n<prettyName>Groups</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>groups</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</groups>\n<users>\n<prettyName>Users</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>users</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</users>\n<levels>\n<prettyName>Access Levels</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>levels</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</levels>\n<allow>\n<numberType>integer</numberType>\n<prettyName>Allow/Deny</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>2</size>\n<name>allow</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</allow>\n</class>\n<name>XWiki.Admin</name>\n<number>0</number>\n<className>XWiki.XWikiRights</className>\n<property>\n<groups>XWiki.XWikiAdminGroup</groups>\n</property>\n<property>\n<levels>view, edit</levels>\n</property>\n<property>\n<allow>1</allow>\n</property>\n</object>\n<object>\n<class>\n<name>XWiki.XWikiRights</name>\n<groups>\n<prettyName>Groups</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>groups</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</groups>\n<users>\n<prettyName>Users</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>users</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</users>\n<levels>\n<prettyName>Access Levels</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>levels</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</levels>\n<allow>\n<numberType>integer</numberType>\n<prettyName>Allow/Deny</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>2</size>\n<name>allow</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</allow>\n</class>\n<name>XWiki.Admin</name>\n<number>1</number>\n<className>XWiki.XWikiRights</className>\n<property>\n<users>XWiki.Admin</users>\n</property>\n<property>\n<levels>view</levels>\n</property>\n<property>\n<allow>1</allow>\n</property>\n</object>\n<object>\n<class>\n<name>XWiki.XWikiUsers</name>\n<password>\n<prettyName>Password</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>password</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.PasswordClass</classType>\n</password>\n<active>\n<prettyName>Active</prettyName>\n<displayType>yesno</displayType>\n<unmodifiable>0</unmodifiable>\n<name>active</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.BooleanClass</classType>\n</active>\n<email>\n<prettyName>e-Mail</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>email</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</email>\n<comment>\n<prettyName>Comment</prettyName>\n<rows>5</rows>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>comment</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</comment>\n<default_language>\n<prettyName>Default Language</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>default_language</name>\n<number>8</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</default_language>\n<validkey>\n<prettyName>Validation Key</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>validkey</name>\n<number>7</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</validkey>\n<last_name>\n<prettyName>Last Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>last_name</name>\n<number>5</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</last_name>\n<fullname>\n<prettyName>Full Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>fullname</name>\n<number>6</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</fullname>\n<first_name>\n<prettyName>First Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>first_name</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</first_name>\n</class>\n<name>XWiki.Admin</name>\n<number>0</number>\n<className>XWiki.XWikiUsers</className>\n<property>\n<password>admin</password>\n</property>\n<property>\n<active>1</active>\n</property>\n<property>\n<email></email>\n</property>\n<property>\n<last_name>Admin</last_name>\n</property>\n<property>\n<first_name>Admin</first_name>\n</property>\n</object>\n<content>#includeForm(\"XWiki.XWikiUserSheet\")</content>\n</xwikidoc>\n@\n\n\n1.1\nlog\n@XWiki.Admin\n@\ntext\n@d7 1\na7 1\n<defaultLanguage></defaultLanguage>\nd10 1\na10 1\n<author></author>\nd12 2\na13 2\n<date>1092995730465</date>\n<version>1.1</version>\n@\n','1.2','XWiki.XWikiUsers');
INSERT INTO xwikidoc VALUES (1397975648,'XWikiAllGroup','','fr',0,'2004-08-20 13:13:52','2004-08-20 11:55:30','XWiki.Admin','','XWiki','#includeForm(\"XWiki.XWikiGroupTemplate\")','head	1.2;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.2\ndate	2004.08.20.13.13.52;	author tomcat;	state Exp;\nbranches;\nnext	1.1;\n\n1.1\ndate	2004.08.20.11.55.30;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.2\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiAllGroup</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092995730551</creationDate>\n<date>1093000432642</date>\n<version>1.2</version>\n<object>\n<class>\n<name>XWiki.XWikiGroups</name>\n<member>\n<prettyName>Member</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>member</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</member>\n</class>\n<name>XWiki.XWikiAllGroup</name>\n<number>0</number>\n<className>XWiki.XWikiGroups</className>\n<property>\n<member>XWiki.Admin</member>\n</property>\n</object>\n<object>\n<class>\n<name>XWiki.XWikiGroups</name>\n<member>\n<prettyName>Member</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>member</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</member>\n</class>\n<name>XWiki.XWikiAllGroup</name>\n<number>1</number>\n<className>XWiki.XWikiGroups</className>\n<property>\n<member>XWiki.User1</member>\n</property>\n</object>\n<content>#includeForm(\"XWiki.XWikiGroupTemplate\")</content>\n</xwikidoc>\n@\n\n\n1.1\nlog\n@XWiki.XWikiAllGroup\n@\ntext\n@d7 1\na7 1\n<defaultLanguage></defaultLanguage>\nd10 1\na10 1\n<author></author>\nd12 2\na13 2\n<date>1092995730551</date>\n<version>1.1</version>\nd33 20\na52 2\n<content>\n</content>\n@\n','1.2','');
INSERT INTO xwikidoc VALUES (1955791968,'User1','','fr',0,'2004-08-20 13:13:15','2004-08-20 11:55:56','XWiki.Admin','','XWiki','#includeForm(\"XWiki.XWikiUserSheet\")','head	1.2;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.2\ndate	2004.08.20.13.13.15;	author tomcat;	state Exp;\nbranches;\nnext	1.1;\n\n1.1\ndate	2004.08.20.11.55.56;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.2\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>User1</name>\n<language></language>\n<defaultLanguage>fr</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.XWikiUsers</parent>\n<author>XWiki.Admin</author>\n<creationDate>1092995756702</creationDate>\n<date>1093000395081</date>\n<version>1.2</version>\n<object>\n<class>\n<name>XWiki.XWikiRights</name>\n<groups>\n<prettyName>Groups</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>groups</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</groups>\n<users>\n<prettyName>Users</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>users</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</users>\n<levels>\n<prettyName>Access Levels</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>levels</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</levels>\n<allow>\n<numberType>integer</numberType>\n<prettyName>Allow/Deny</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>2</size>\n<name>allow</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</allow>\n</class>\n<name>XWiki.User1</name>\n<number>0</number>\n<className>XWiki.XWikiRights</className>\n<property>\n<groups>XWiki.XWikiAdminGroup</groups>\n</property>\n<property>\n<levels>view, edit</levels>\n</property>\n<property>\n<allow>1</allow>\n</property>\n</object>\n<object>\n<class>\n<name>XWiki.XWikiRights</name>\n<groups>\n<prettyName>Groups</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>groups</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</groups>\n<users>\n<prettyName>Users</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>users</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</users>\n<levels>\n<prettyName>Access Levels</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>80</size>\n<name>levels</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</levels>\n<allow>\n<numberType>integer</numberType>\n<prettyName>Allow/Deny</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>2</size>\n<name>allow</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.NumberClass</classType>\n</allow>\n</class>\n<name>XWiki.User1</name>\n<number>1</number>\n<className>XWiki.XWikiRights</className>\n<property>\n<users>XWiki.User1</users>\n</property>\n<property>\n<levels>view</levels>\n</property>\n<property>\n<allow>1</allow>\n</property>\n</object>\n<object>\n<class>\n<name>XWiki.XWikiUsers</name>\n<password>\n<prettyName>Password</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>password</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.PasswordClass</classType>\n</password>\n<active>\n<prettyName>Active</prettyName>\n<displayType>yesno</displayType>\n<unmodifiable>0</unmodifiable>\n<name>active</name>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.BooleanClass</classType>\n</active>\n<email>\n<prettyName>e-Mail</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>email</name>\n<number>2</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</email>\n<comment>\n<prettyName>Comment</prettyName>\n<rows>5</rows>\n<unmodifiable>0</unmodifiable>\n<size>40</size>\n<name>comment</name>\n<number>3</number>\n<classType>com.xpn.xwiki.objects.classes.TextAreaClass</classType>\n</comment>\n<default_language>\n<prettyName>Default Language</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>5</size>\n<name>default_language</name>\n<number>8</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</default_language>\n<validkey>\n<prettyName>Validation Key</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>10</size>\n<name>validkey</name>\n<number>7</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</validkey>\n<last_name>\n<prettyName>Last Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>last_name</name>\n<number>5</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</last_name>\n<fullname>\n<prettyName>Full Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>fullname</name>\n<number>6</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</fullname>\n<first_name>\n<prettyName>First Name</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<name>first_name</name>\n<number>4</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</first_name>\n</class>\n<name>XWiki.User1</name>\n<number>0</number>\n<className>XWiki.XWikiUsers</className>\n<property>\n<password>user1</password>\n</property>\n<property>\n<active>1</active>\n</property>\n<property>\n<email></email>\n</property>\n<property>\n<last_name></last_name>\n</property>\n<property>\n<first_name>User1</first_name>\n</property>\n</object>\n<content>#includeForm(\"XWiki.XWikiUserSheet\")</content>\n</xwikidoc>\n@\n\n\n1.1\nlog\n@XWiki.User1\n@\ntext\n@d7 1\na7 1\n<defaultLanguage></defaultLanguage>\nd10 1\na10 1\n<author></author>\nd12 2\na13 2\n<date>1092995756705</date>\n<version>1.1</version>\n@\n','1.2','XWiki.XWikiUsers');
INSERT INTO xwikidoc VALUES (-114121954,'WebHome','','en',0,'2004-08-20 13:30:05','2004-08-20 13:17:45','XWiki.Admin','XWiki.Admin','Main','1 Welcome on your Wiki\r\n\r\nThis web site is your web site. With your wiki you can:\r\n\r\n   * Create a professional or personal Web Site\r\n   * Share content on an Intranet\r\n   * Build dynamic intranet applications\r\n\r\nFor more information, check out the [documentation>Doc.WebHome]. For demos of advanced features, check out the [XWiki Dev Zone>http://www.xwiki.org/xwiki/bin/view/Dev/WebHome]\r\n','head	1.4;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.4\ndate	2004.08.20.13.30.05;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.13.29.17;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.13.18.06;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.4\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>WebHome</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1093000665000</creationDate>\n<date>1093001405690</date>\n<version>1.4</version>\n<content>1 Welcome on your Wiki\n\nThis web site is your web site. With your wiki you can:\n\n   * Create a professional or personal Web Site\n   * Share content on an Intranet\n   * Build dynamic intranet applications\n\nFor more information, check out the [documentation&gt;Doc.WebHome]. For demos of advanced features, check out the [XWiki Dev Zone&gt;http://www.xwiki.org/xwiki/bin/view/Dev/WebHome]\n</content>\n</xwikidoc>\n@\n\n\n1.3\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1093001357330</date>\n<version>1.3</version>\nd22 1\na22 1\nFor more information, check out the [documentation&gt;Doc.WebHome]\n@\n\n\n1.2\nlog\n@Main.WebHome\n@\ntext\n@d11 4\na14 5\n<creationDate>1093000665455</creationDate>\n<date>1093000686140</date>\n<version>1.2</version>\n<content>\n1 Welcome on your Wiki\nd18 3\na20 3\n   * Create your personal web site\n   * Share content with your friends\n   * Create free content zones with all Internet users\n@\n','1.4','');
INSERT INTO xwikidoc VALUES (-2006889136,'WebRss','en','',1,'2004-08-20 13:08:46','2004-08-20 12:25:13','XWiki.Admin','XWiki.Admin','Main','{pre}\r\n$response.setContentType(\"text/xml\")\r\n#set ($sql = \"where 1=1 order by doc.date desc\")\r\n#set ($list = $xwiki.searchDocuments($sql , 20 , 0))\r\n#set ($baseurl =  \"http://${request.serverName}\")\r\n#set($rsvc = $xwiki.xWiki.getRightService())\r\n$response.setContentType(\"text/xml\")\r\n<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://purl.org/rss/1.0/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:wiki=\"http://purl.org/rss/1.0/modules/wiki/\" >\r\n<channel rdf:about=\"$baseurl\">\r\n<title>$request.serverName</title>\r\n<link>$baseurl</link>\r\n<description>\r\n$request.serverName\r\n</description>\r\n<image rdf:resource=\"${baseurl}/xwiki/skins/default/logo.gif\" />\r\n<dc:language>$doc.defaultLanguage</dc:language>\r\n<dc:rights>$xwiki.webCopyright</dc:rights>\r\n<dc:publisher>$doc.author</dc:publisher>\r\n<dc:creator>$doc.author</dc:creator>\r\n<items>\r\n<rdf:Seq>\r\n#foreach ($item in $list)\r\n#if (!$rsvc || $rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\r\n#set ($currentdoc = $xwiki.getDocument($item))\r\n<rdf:li rdf:resource=\"${baseurl}/xwiki/bin/view/${currentdoc.web}/${currentdoc.name}\" />\r\n#end\r\n#end\r\n</rdf:Seq>\r\n</items>\r\n</channel>\r\n<image rdf:about=\"${baseurl}/xwiki/skins/default/logo.gif\">\r\n  <title>XWiki Logo</title>\r\n  <link>${baseurl}</link>\r\n  <url>${baseurl}/xwiki/skins/default/logo.gif</url>\r\n</image>\r\n#foreach ($item in $list)\r\n#if ($rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\r\n#set ($currentdoc = $xwiki.getDocument($item))\r\n#set ($url = \"${baseurl}/xwiki/bin/view/${currentdoc.web}/${currentdoc.name}\")\r\n#if ($currentdoc.content.length() < 255)\r\n#set ($length = $currentdoc.content.length())\r\n#else\r\n#set ($length = 255)\r\n#end\r\n#set ($desc = $xwiki.getXMLEncoded($currentdoc.content.substring($length)))\r\n<item rdf:about=\"$url\">\r\n<title>${currentdoc.web}.${currentdoc.name}</title>\r\n<link>$url</link>\r\n<description>\r\n${currentdoc.web}.${currentdoc.name}\r\n</description>\r\n<dc:date>$currentdoc.date</dc:date>\r\n<dc:contributor>\r\n<rdf:Description link=\"\">\r\n<rdf:value>$currentdoc.author</rdf:value>\r\n</rdf:Description>\r\n</dc:contributor>\r\n</item>\r\n#end\r\n#end\r\n</rdf:RDF>\r\n{/pre}','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.13.08.46;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.12.25.13;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>WebRss</name>\n<language>en</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092997513702</creationDate>\n<date>1093000126948</date>\n<version>1.3</version>\n<content>{pre}\n$response.setContentType(\"text/xml\")\n#set ($sql = \"where 1=1 order by doc.date desc\")\n#set ($list = $xwiki.searchDocuments($sql , 20 , 0))\n#set ($baseurl =  \"http://${request.serverName}\")\n#set($rsvc = $xwiki.xWiki.getRightService())\n$response.setContentType(\"text/xml\")\n&lt;rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://purl.org/rss/1.0/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:wiki=\"http://purl.org/rss/1.0/modules/wiki/\" &gt;\n&lt;channel rdf:about=\"$baseurl\"&gt;\n&lt;title&gt;$request.serverName&lt;/title&gt;\n&lt;link&gt;$baseurl&lt;/link&gt;\n&lt;description&gt;\n$request.serverName\n&lt;/description&gt;\n&lt;image rdf:resource=\"${baseurl}/xwiki/skins/default/logo.gif\" /&gt;\n&lt;dc:language&gt;$doc.defaultLanguage&lt;/dc:language&gt;\n&lt;dc:rights&gt;$xwiki.webCopyright&lt;/dc:rights&gt;\n&lt;dc:publisher&gt;$doc.author&lt;/dc:publisher&gt;\n&lt;dc:creator&gt;$doc.author&lt;/dc:creator&gt;\n&lt;items&gt;\n&lt;rdf:Seq&gt;\n#foreach ($item in $list)\n#if (!$rsvc || $rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\n#set ($currentdoc = $xwiki.getDocument($item))\n&lt;rdf:li rdf:resource=\"${baseurl}/xwiki/bin/view/${currentdoc.web}/${currentdoc.name}\" /&gt;\n#end\n#end\n&lt;/rdf:Seq&gt;\n&lt;/items&gt;\n&lt;/channel&gt;\n&lt;image rdf:about=\"${baseurl}/xwiki/skins/default/logo.gif\"&gt;\n  &lt;title&gt;XWiki Logo&lt;/title&gt;\n  &lt;link&gt;${baseurl}&lt;/link&gt;\n  &lt;url&gt;${baseurl}/xwiki/skins/default/logo.gif&lt;/url&gt;\n&lt;/image&gt;\n#foreach ($item in $list)\n#if ($rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\n#set ($currentdoc = $xwiki.getDocument($item))\n#set ($url = \"${baseurl}/xwiki/bin/view/${currentdoc.web}/${currentdoc.name}\")\n#if ($currentdoc.content.length() &lt; 255)\n#set ($length = $currentdoc.content.length())\n#else\n#set ($length = 255)\n#end\n#set ($desc = $xwiki.getXMLEncoded($currentdoc.content.substring($length)))\n&lt;item rdf:about=\"$url\"&gt;\n&lt;title&gt;${currentdoc.web}.${currentdoc.name}&lt;/title&gt;\n&lt;link&gt;$url&lt;/link&gt;\n&lt;description&gt;\n${currentdoc.web}.${currentdoc.name}\n&lt;/description&gt;\n&lt;dc:date&gt;$currentdoc.date&lt;/dc:date&gt;\n&lt;dc:contributor&gt;\n&lt;rdf:Description link=\"\"&gt;\n&lt;rdf:value&gt;$currentdoc.author&lt;/rdf:value&gt;\n&lt;/rdf:Description&gt;\n&lt;/dc:contributor&gt;\n&lt;/item&gt;\n#end\n#end\n&lt;/rdf:RDF&gt;\n{/pre}</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@Main.WebRss\n@\ntext\n@d12 2\na13 2\n<date>1092997513703</date>\n<version>1.2</version>\nd36 1\na36 1\n#if ($rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (1489155282,'RegisterJS','','en',0,'2004-08-20 12:28:03','2004-08-20 12:27:53','XWiki.Admin','XWiki.Admin','XWiki','{pre}\r\n<script type=\"text/javascript\">\r\n<!--\r\nfunction noaccent(chaine) {\r\n      temp = chaine.replace(/[àâä]/gi,\"a\")\r\n      temp = temp.replace(/[éèêë]/gi,\"e\")\r\n      temp = temp.replace(/[îï]/gi,\"i\")\r\n      temp = temp.replace(/[ôö]/gi,\"o\")\r\n      temp = temp.replace(/[ùûü]/gi,\"u\")\r\n      return temp;\r\n}\r\nfunction prepareName(form) {\r\n var fname = form.register_first_name.value;\r\n var lname = form.register_last_name.value;\r\n var cxwikiname = form.xwikiname;\r\n if (fname != \"\") {\r\n   fname = fname.substring(0,1).toUpperCase() + fname.substring(1);\r\n   fname.replace(/ /g,\"\");\r\n }\r\n if (lname != \"\") {\r\n   lname = lname.substring(0,1).toUpperCase() + lname.substring(1)\r\n   lname.replace(/ /g,\"\");\r\n }\r\n if (cxwikiname.value == \"\") {\r\n   cxwikiname.value  =  noaccent(fname + lname);\r\n }\r\n}\r\nfunction verifyName(form) {    \r\nvar iframe = document.getElementsByTagName(\"iframe\").verificationframe;\r\niframe.src = \'RegisterNewUserVerify?xpage=plain&xwikiname=\' + form.xwikiname.value; \r\ndocument.getElementById(\"verification\").style.display = \"block\";\r\n}\r\n// -->\r\n</script>\r\n{/pre}','head	1.2;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.2\ndate	2004.08.20.12.28.03;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.2\nlog\n@XWiki.RegisterJS\n@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>RegisterJS</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092997673454</creationDate>\n<date>1092997683639</date>\n<version>1.2</version>\n<content>{pre}\n&lt;script type=\"text/javascript\"&gt;\n&lt;!--\nfunction noaccent(chaine) {\n      temp = chaine.replace(/[àâä]/gi,\"a\")\n      temp = temp.replace(/[éèêë]/gi,\"e\")\n      temp = temp.replace(/[îï]/gi,\"i\")\n      temp = temp.replace(/[ôö]/gi,\"o\")\n      temp = temp.replace(/[ùûü]/gi,\"u\")\n      return temp;\n}\nfunction prepareName(form) {\n var fname = form.register_first_name.value;\n var lname = form.register_last_name.value;\n var cxwikiname = form.xwikiname;\n if (fname != \"\") {\n   fname = fname.substring(0,1).toUpperCase() + fname.substring(1);\n   fname.replace(/ /g,\"\");\n }\n if (lname != \"\") {\n   lname = lname.substring(0,1).toUpperCase() + lname.substring(1)\n   lname.replace(/ /g,\"\");\n }\n if (cxwikiname.value == \"\") {\n   cxwikiname.value  =  noaccent(fname + lname);\n }\n}\nfunction verifyName(form) {    \nvar iframe = document.getElementsByTagName(\"iframe\").verificationframe;\niframe.src = \'RegisterNewUserVerify?xpage=plain&amp;xwikiname=\' + form.xwikiname.value; \ndocument.getElementById(\"verification\").style.display = \"block\";\n}\n// --&gt;\n&lt;/script&gt;\n{/pre}</content>\n</xwikidoc>\n@\n','1.2','');
INSERT INTO xwikidoc VALUES (735463360,'TestInclude','','en',0,'2004-08-20 12:40:20','2004-08-20 12:39:41','XWiki.Admin','XWiki.Admin','Doc','Nom du document: $doc.name','head	1.2;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.2\ndate	2004.08.20.12.40.20;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.2\nlog\n@Doc.TestInclude\n@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Doc</web>\n<name>TestInclude</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092998381932</creationDate>\n<date>1092998420585</date>\n<version>1.2</version>\n<content>Nom du document: $doc.name</content>\n</xwikidoc>\n@\n','1.2','');
INSERT INTO xwikidoc VALUES (-1952393199,'XWikiSyntax','fr','',1,'2004-08-20 13:04:02','2004-08-20 12:51:11','XWiki.Admin','XWiki.Admin','Doc','1 La syntaxe XWiki\r\n\r\nXWiki permet de créer des pages Internet sans utiliser l’HTML à l’aide d’une syntaxe simple reproduisant en ~texte~ le plus possible l’aspect final du document.\r\n\r\nCe document explique la syntaxe qui permet de modifier l\'aspect du document ainsi que de faire des liens entre les documents.\r\n\r\n{pre}\r\n<table class=\"wiki-table\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\" width=\"80%\">\r\n<tr><th width=\"25%\">Commande:</th>\r\n<th width=\"10%\">Exemple:</th>\r\n<th width=\"10%\">Resultat:</th>\r\n\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Titres</b><br/>\r\nCela permet de créer des titres HTML avec plusieurs niveaux. De plus ces titres peuvent être utiliser pour créer automatiquement une table des matières\r\n</td>\r\n<td>\r\n<pre>\r\n\r\n1 Titre 1\r\n\r\n1.1 Titre 2\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<h3 class=\"heading-1\">Titre 1</h3>\r\n<h3 class=\"heading-1-1\">Titre 2</h3>\r\n\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Paragraphes</b><br/>\r\nPour passer d\'un paragraphe à un autre il faut sauter une ligne car passer à la ligne ne suffit pas.\r\n</td>\r\n<td>\r\n<pre>\r\n\r\nVoici le texte d\'un paragraphe.\r\nEt le texte du même paragraphe.\r\n\r\nVoici le texte d\'un paragraphe.\r\n\r\nEt le texte d\'un autre paragraphe.\r\n\r\n</pre>\r\n</td>\r\n<td>\r\nVoici le texte d\'un paragraphe.\r\nEt le texte du même paragraphe.<p class=\"paragraph\"/>Voici le texte d\'un paragraphe.<p class=\"paragraph\"/>Et le texte d\'un autre paragraphe.\r\n\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Listes</b><br/>\r\nIl y a beaucoup de sortes de listes.. Elles peuvent être non-numérotés et numérotés de différentes façon (apparement tout ne marche pas).<p class=\"paragraph\"/>Elle peuvent aussi être imbriquées.\r\n</td>\r\n<td>\r\n<pre>\r\n\r\n* Liste non numérotée\r\n* Liste non numérotée\r\n\r\n   * Liste imbriquée\r\n      * Liste imbriquée\r\n      * Liste imbriquée\r\n   * Liste imbriquée\r\n      * Liste imbriquée\r\n\r\n   \r\n1. Liste numérotée\r\n1. Liste numérotée\r\n\r\na. Liste num. alphabetiquement\r\na. Liste num. alphabetiquement\r\n\r\nA. Liste num. alphabetiquement et en maj.\r\nA. Liste num. alphabetiquement et en maj.\r\n\r\ni. Liste num. en caractères romains\r\ni. Liste num. en caractères romains\r\n\r\nI. Liste num. en caractères romains et en maj.\r\nI. Liste num. en caractères romains et en maj.\r\n\r\ng. Liste num. en caractères grècs\r\ng. Liste num. en caractères grècs\r\n\r\nh. Liste num. en caractères hirigana (jap.)\r\nh. Liste num. en caractères hirigana (jap.)\r\n\r\nk. Liste num. en caractères katakana (jap.)\r\n\r\n \r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<ul class=\"star\">\r\n\r\n<li>Liste non numérotée</li>\r\n<li>Liste non numérotée</li>\r\n<li>Liste imbriquée</li>\r\n<li>Liste imbriquée</li>\r\n<li>Liste imbriquée</li>\r\n<li>Liste imbriquée</li>\r\n<li>Liste imbriquée</li>\r\n</ul>\r\n<ol>\r\n<li>Liste numérotée</li>\r\n\r\n<li>Liste numérotée</li>\r\n</ol>\r\n<ol class=\"alpha\">\r\n<li>Liste num. alphabetiquement</li>\r\n<li>Liste num. alphabetiquement</li>\r\n</ol>\r\n<ol class=\"ALPHA\">\r\n<li>Liste num. alphabetiquement et en maj.</li>\r\n<li>Liste num. alphabetiquement et en maj.</li>\r\n</ol>\r\n<ol class=\"roman\">\r\n<li>Liste num. en caractères romains</li>\r\n\r\n<li>Liste num. en caractères romains</li>\r\n</ol>\r\n<ol class=\"ROMAN\">\r\n<li>Liste num. en caractères romains et en maj.</li>\r\n<li>Liste num. en caractères romains et en maj.</li>\r\n</ol>\r\n<ol class=\"greek\">\r\n<li>Liste num. en caractères grècs</li>\r\n<li>Liste num. en caractères grècs</li>\r\n</ol>\r\n<ol class=\"hiragana\">\r\n<li>Liste num. en caractères hirigana (jap.)</li>\r\n\r\n<li>Liste num. en caractères hirigana (jap.)</li>\r\n</ol>\r\n<ol class=\"katakana\">\r\n<li>Liste num. en caractères katakana (jap.)</li>\r\n</ol> \r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Gras</b><br/>\r\nIl y a deux façon de faire des charactères gras.\r\n</td>\r\n<td>\r\n<pre>\r\n\r\nVoici un texte avec un mot en *gras*\r\nVoici un texte avec un mot en __gras__ \r\n\r\n\r\n</pre>\r\n</td>\r\n<td>\r\nVoici un texte avec un mot en <strong class=\"strong\">gras</strong>\r\nVoici un texte avec un mot en <b class=\"bold\">gras</b><p class=\"paragraph\"/></td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Italique</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n\r\n<pre>\r\n\r\n~~Voici un texte entièrement en italique~~\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<i class=\"italic\">Voici un texte entièrement en italique</i>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Texte barré</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\n--Voici un texte barré--\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<strike class=\"strike\">Voici un texte barré</strike>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Liens Wiki</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\nLiens Wiki:\r\n\r\n* [WebHome]\r\n* [Web Home]\r\n* [web home>WebHome]\r\n* [Main.WebHome]\r\n* [web home|Main.WebHome]\r\n\r\n\r\n</pre>\r\n\r\n</td>\r\n<td>\r\nLiens Wiki:\r\n<ul class=\"star\">\r\n<li><span class=\"wikilink\"><a href=\"/xwiki/bin/view/Doc/WebHome\">WebHome</a></span></li>\r\n<li><span class=\"wikilink\"><a href=\"/xwiki/bin/view/Doc/WebHome\">Web Home</a></span></li>\r\n<li><span class=\"wikilink\"><a href=\"/xwiki/bin/view/Doc/WebHome\">web home</a></span></li>\r\n<li><span class=\"wikilink\"><a href=\"/xwiki/bin/view/Main/WebHome\">Main.WebHome</a></span></li>\r\n<li><span class=\"wikilink\"><a href=\"/xwiki/bin/view/Main/WebHome\">web home</a></span></li>\r\n</ul></td>\r\n</tr>\r\n<tr>\r\n\r\n<td>\r\n<b class=\"bold\">Liens externes</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\n* http://www.xwiki.org\r\n* [http://www.xwiki.org]\r\n* [lien xwiki.org>http://www.xwiki.org]\r\n* {link:lien xwiki.org|http://www.xwiki.org}\r\n\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<ul class=\"star\">\r\n<li><span class=\"nobr\"><a href=\"http://www.xwiki.org\">&#104;ttp://www.xwiki.org</a></span></li>\r\n<li><span class=\"wikiexternallink\"><a href=\"http://www.xwiki.org\">&#104;ttp://www.xwiki.org</a></span></li>\r\n<li><span class=\"wikiexternallink\"><a href=\"http://www.xwiki.org\">&#108;ien xwiki.org</a></span></li>\r\n\r\n<li><span class=\"nobr\"><a href=\"http://www.xwiki.org\">lien xwiki.org</a></span></li>\r\n</ul></td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Citations</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\n{quote:http://www.xwiki.org}\r\nVoici un texte avec une source\r\n{quote}\r\n\r\n{quote:http://www.xwiki.org}\r\n~~Voici un texte avec une source~~\r\n{quote}\r\n\r\n</pre>\r\n</td>\r\n<td>\r\n<blockquote class=\"quote\">\r\nVoici un texte avec une source\r\n\r\n<a href=\"http://www.xwiki.org\">Source</a></blockquote><p class=\"paragraph\"/><blockquote class=\"quote\">\r\n<i class=\"italic\">Voici un texte avec une source</i>\r\n<a href=\"http://www.xwiki.org\">Source</a></blockquote>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Tables</b><br/><p class=\"paragraph\"/></td>\r\n<td>\r\n<pre>\r\n\r\n{table}\r\nTitre 1 | Titre 2\r\nMot 1 | Mot 2\r\n{table}\r\n\r\n{table}\r\nCategories | Ventes (K€)\r\nCategorie 1 | 100\r\nCategorie 2 | 50\r\nCategorie 3 | 50\r\nTotal | =sum(B2:B4)\r\n{table}\r\n\r\n\r\n\r\n</pre>\r\n\r\n</td>\r\n<td>\r\n<table class=\"wiki-table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><th>Titre 1</th><th>Titre 2</th></tr><tr class=\"table-odd\"><td>Mot 1</td><td>Mot 2</td></tr></table><p class=\"paragraph\"/><table class=\"wiki-table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><th>Categories</th><th>Ventes (K€)</th></tr><tr class=\"table-odd\"><td>Categorie 1</td><td>100</td></tr><tr class=\"table-even\"><td>Categorie 2</td><td>50</td></tr><tr class=\"table-odd\"><td>Categorie 3</td><td>50</td></tr><tr class=\"table-even\"><td>Total</td><td>200</td></tr></table><p class=\"paragraph\"/>\r\n\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<b class=\"bold\">Inclusion de documents</b><br/>\r\nAvec XWiki il est possible d\'inclure un document dans un autre. Cela permet de faire des documents complexes à partir d\'autres document pour faire un conversion en PDF.\r\nCela permet aussi de créer des formulaires à utiliser pour representer les objets.\r\n\r\n</td>\r\n<td>\r\n#&#105;ncludeTopic(\"Doc.TestInclude\")\r\n<br />\r\n#&#105;ncludeForm(\"Doc.TestInclude\")\r\n</td>\r\n<td>\r\n#includeTopic(\"Doc.TestInclude\")\r\n<br />\r\n#includeForm(\"Doc.TestInclude\")\r\n</td>\r\n</tr>\r\n</table>\r\n','head	1.9;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.9\ndate	2004.08.20.13.04.02;	author tomcat;	state Exp;\nbranches;\nnext	1.8;\n\n1.8\ndate	2004.08.20.12.56.25;	author tomcat;	state Exp;\nbranches;\nnext	1.7;\n\n1.7\ndate	2004.08.20.12.54.50;	author tomcat;	state Exp;\nbranches;\nnext	1.6;\n\n1.6\ndate	2004.08.20.12.53.49;	author tomcat;	state Exp;\nbranches;\nnext	1.5;\n\n1.5\ndate	2004.08.20.12.53.04;	author tomcat;	state Exp;\nbranches;\nnext	1.4;\n\n1.4\ndate	2004.08.20.12.52.43;	author tomcat;	state Exp;\nbranches;\nnext	1.3;\n\n1.3\ndate	2004.08.20.12.52.18;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.12.51.11;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.9\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Doc</web>\n<name>XWikiSyntax</name>\n<language>fr</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092999071431</creationDate>\n<date>1092999842569</date>\n<version>1.9</version>\n<content>1 La syntaxe XWiki\n\nXWiki permet de créer des pages Internet sans utiliser l’HTML à l’aide d’une syntaxe simple reproduisant en ~texte~ le plus possible l’aspect final du document.\n\nCe document explique la syntaxe qui permet de modifier l\'aspect du document ainsi que de faire des liens entre les documents.\n\n{pre}\n&lt;table class=\"wiki-table\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\" width=\"80%\"&gt;\n&lt;tr&gt;&lt;th width=\"25%\"&gt;Commande:&lt;/th&gt;\n&lt;th width=\"10%\"&gt;Exemple:&lt;/th&gt;\n&lt;th width=\"10%\"&gt;Resultat:&lt;/th&gt;\n\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Titres&lt;/b&gt;&lt;br/&gt;\nCela permet de créer des titres HTML avec plusieurs niveaux. De plus ces titres peuvent être utiliser pour créer automatiquement une table des matières\n&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n1 Titre 1\n\n1.1 Titre 2\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;h3 class=\"heading-1\"&gt;Titre 1&lt;/h3&gt;\n&lt;h3 class=\"heading-1-1\"&gt;Titre 2&lt;/h3&gt;\n\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Paragraphes&lt;/b&gt;&lt;br/&gt;\nPour passer d\'un paragraphe à un autre il faut sauter une ligne car passer à la ligne ne suffit pas.\n&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\nVoici le texte d\'un paragraphe.\nEt le texte du même paragraphe.\n\nVoici le texte d\'un paragraphe.\n\nEt le texte d\'un autre paragraphe.\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\nVoici le texte d\'un paragraphe.\nEt le texte du même paragraphe.&lt;p class=\"paragraph\"/&gt;Voici le texte d\'un paragraphe.&lt;p class=\"paragraph\"/&gt;Et le texte d\'un autre paragraphe.\n\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Listes&lt;/b&gt;&lt;br/&gt;\nIl y a beaucoup de sortes de listes.. Elles peuvent être non-numérotés et numérotés de différentes façon (apparement tout ne marche pas).&lt;p class=\"paragraph\"/&gt;Elle peuvent aussi être imbriquées.\n&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n* Liste non numérotée\n* Liste non numérotée\n\n   * Liste imbriquée\n      * Liste imbriquée\n      * Liste imbriquée\n   * Liste imbriquée\n      * Liste imbriquée\n\n   \n1. Liste numérotée\n1. Liste numérotée\n\na. Liste num. alphabetiquement\na. Liste num. alphabetiquement\n\nA. Liste num. alphabetiquement et en maj.\nA. Liste num. alphabetiquement et en maj.\n\ni. Liste num. en caractères romains\ni. Liste num. en caractères romains\n\nI. Liste num. en caractères romains et en maj.\nI. Liste num. en caractères romains et en maj.\n\ng. Liste num. en caractères grècs\ng. Liste num. en caractères grècs\n\nh. Liste num. en caractères hirigana (jap.)\nh. Liste num. en caractères hirigana (jap.)\n\nk. Liste num. en caractères katakana (jap.)\n\n \n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;ul class=\"star\"&gt;\n\n&lt;li&gt;Liste non numérotée&lt;/li&gt;\n&lt;li&gt;Liste non numérotée&lt;/li&gt;\n&lt;li&gt;Liste imbriquée&lt;/li&gt;\n&lt;li&gt;Liste imbriquée&lt;/li&gt;\n&lt;li&gt;Liste imbriquée&lt;/li&gt;\n&lt;li&gt;Liste imbriquée&lt;/li&gt;\n&lt;li&gt;Liste imbriquée&lt;/li&gt;\n&lt;/ul&gt;\n&lt;ol&gt;\n&lt;li&gt;Liste numérotée&lt;/li&gt;\n\n&lt;li&gt;Liste numérotée&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"alpha\"&gt;\n&lt;li&gt;Liste num. alphabetiquement&lt;/li&gt;\n&lt;li&gt;Liste num. alphabetiquement&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"ALPHA\"&gt;\n&lt;li&gt;Liste num. alphabetiquement et en maj.&lt;/li&gt;\n&lt;li&gt;Liste num. alphabetiquement et en maj.&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"roman\"&gt;\n&lt;li&gt;Liste num. en caractères romains&lt;/li&gt;\n\n&lt;li&gt;Liste num. en caractères romains&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"ROMAN\"&gt;\n&lt;li&gt;Liste num. en caractères romains et en maj.&lt;/li&gt;\n&lt;li&gt;Liste num. en caractères romains et en maj.&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"greek\"&gt;\n&lt;li&gt;Liste num. en caractères grècs&lt;/li&gt;\n&lt;li&gt;Liste num. en caractères grècs&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"hiragana\"&gt;\n&lt;li&gt;Liste num. en caractères hirigana (jap.)&lt;/li&gt;\n\n&lt;li&gt;Liste num. en caractères hirigana (jap.)&lt;/li&gt;\n&lt;/ol&gt;\n&lt;ol class=\"katakana\"&gt;\n&lt;li&gt;Liste num. en caractères katakana (jap.)&lt;/li&gt;\n&lt;/ol&gt; \n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Gras&lt;/b&gt;&lt;br/&gt;\nIl y a deux façon de faire des charactères gras.\n&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\nVoici un texte avec un mot en *gras*\nVoici un texte avec un mot en __gras__ \n\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\nVoici un texte avec un mot en &lt;strong class=\"strong\"&gt;gras&lt;/strong&gt;\nVoici un texte avec un mot en &lt;b class=\"bold\"&gt;gras&lt;/b&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Italique&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n\n&lt;pre&gt;\n\n~~Voici un texte entièrement en italique~~\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;i class=\"italic\"&gt;Voici un texte entièrement en italique&lt;/i&gt;\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Texte barré&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n--Voici un texte barré--\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;strike class=\"strike\"&gt;Voici un texte barré&lt;/strike&gt;\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Liens Wiki&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\nLiens Wiki:\n\n* [WebHome]\n* [Web Home]\n* [web home&gt;WebHome]\n* [Main.WebHome]\n* [web home|Main.WebHome]\n\n\n&lt;/pre&gt;\n\n&lt;/td&gt;\n&lt;td&gt;\nLiens Wiki:\n&lt;ul class=\"star\"&gt;\n&lt;li&gt;&lt;span class=\"wikilink\"&gt;&lt;a href=\"/xwiki/bin/view/Doc/WebHome\"&gt;WebHome&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikilink\"&gt;&lt;a href=\"/xwiki/bin/view/Doc/WebHome\"&gt;Web Home&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikilink\"&gt;&lt;a href=\"/xwiki/bin/view/Doc/WebHome\"&gt;web home&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikilink\"&gt;&lt;a href=\"/xwiki/bin/view/Main/WebHome\"&gt;Main.WebHome&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikilink\"&gt;&lt;a href=\"/xwiki/bin/view/Main/WebHome\"&gt;web home&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;/ul&gt;&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Liens externes&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n* http://www.xwiki.org\n* [http://www.xwiki.org]\n* [lien xwiki.org&gt;http://www.xwiki.org]\n* {link:lien xwiki.org|http://www.xwiki.org}\n\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;ul class=\"star\"&gt;\n&lt;li&gt;&lt;span class=\"nobr\"&gt;&lt;a href=\"http://www.xwiki.org\"&gt;&amp;#104;ttp://www.xwiki.org&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikiexternallink\"&gt;&lt;a href=\"http://www.xwiki.org\"&gt;&amp;#104;ttp://www.xwiki.org&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;li&gt;&lt;span class=\"wikiexternallink\"&gt;&lt;a href=\"http://www.xwiki.org\"&gt;&amp;#108;ien xwiki.org&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n\n&lt;li&gt;&lt;span class=\"nobr\"&gt;&lt;a href=\"http://www.xwiki.org\"&gt;lien xwiki.org&lt;/a&gt;&lt;/span&gt;&lt;/li&gt;\n&lt;/ul&gt;&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Citations&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n{quote:http://www.xwiki.org}\nVoici un texte avec une source\n{quote}\n\n{quote:http://www.xwiki.org}\n~~Voici un texte avec une source~~\n{quote}\n\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n&lt;blockquote class=\"quote\"&gt;\nVoici un texte avec une source\n\n&lt;a href=\"http://www.xwiki.org\"&gt;Source&lt;/a&gt;&lt;/blockquote&gt;&lt;p class=\"paragraph\"/&gt;&lt;blockquote class=\"quote\"&gt;\n&lt;i class=\"italic\"&gt;Voici un texte avec une source&lt;/i&gt;\n&lt;a href=\"http://www.xwiki.org\"&gt;Source&lt;/a&gt;&lt;/blockquote&gt;\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Tables&lt;/b&gt;&lt;br/&gt;&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n{table}\nTitre 1 | Titre 2\nMot 1 | Mot 2\n{table}\n\n{table}\nCategories | Ventes (K€)\nCategorie 1 | 100\nCategorie 2 | 50\nCategorie 3 | 50\nTotal | =sum(B2:B4)\n{table}\n\n\n\n&lt;/pre&gt;\n\n&lt;/td&gt;\n&lt;td&gt;\n&lt;table class=\"wiki-table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"&gt;&lt;tr&gt;&lt;th&gt;Titre 1&lt;/th&gt;&lt;th&gt;Titre 2&lt;/th&gt;&lt;/tr&gt;&lt;tr class=\"table-odd\"&gt;&lt;td&gt;Mot 1&lt;/td&gt;&lt;td&gt;Mot 2&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;p class=\"paragraph\"/&gt;&lt;table class=\"wiki-table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"&gt;&lt;tr&gt;&lt;th&gt;Categories&lt;/th&gt;&lt;th&gt;Ventes (K€)&lt;/th&gt;&lt;/tr&gt;&lt;tr class=\"table-odd\"&gt;&lt;td&gt;Categorie 1&lt;/td&gt;&lt;td&gt;100&lt;/td&gt;&lt;/tr&gt;&lt;tr class=\"table-even\"&gt;&lt;td&gt;Categorie 2&lt;/td&gt;&lt;td&gt;50&lt;/td&gt;&lt;/tr&gt;&lt;tr class=\"table-odd\"&gt;&lt;td&gt;Categorie 3&lt;/td&gt;&lt;td&gt;50&lt;/td&gt;&lt;/tr&gt;&lt;tr class=\"table-even\"&gt;&lt;td&gt;Total&lt;/td&gt;&lt;td&gt;200&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;p class=\"paragraph\"/&gt;\n\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Inclusion de documents&lt;/b&gt;&lt;br/&gt;\nAvec XWiki il est possible d\'inclure un document dans un autre. Cela permet de faire des documents complexes à partir d\'autres document pour faire un conversion en PDF.\nCela permet aussi de créer des formulaires à utiliser pour representer les objets.\n\n&lt;/td&gt;\n&lt;td&gt;\n#&amp;#105;ncludeTopic(\"Doc.TestInclude\")\n&lt;br /&gt;\n#&amp;#105;ncludeForm(\"Doc.TestInclude\")\n&lt;/td&gt;\n&lt;td&gt;\n#includeTopic(\"Doc.TestInclude\")\n&lt;br /&gt;\n#includeForm(\"Doc.TestInclude\")\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;/table&gt;\n</content>\n</xwikidoc>\n@\n\n\n1.8\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999385237</date>\n<version>1.8</version>\nd20 1\na20 1\n\na316 44\n&lt;b class=\"bold\"&gt;Pas de rendu Wiki mais HTML&lt;/b&gt;&lt;br/&gt;\nCeci permet de montrer la syntax Wiki dans une page Wiki.\n&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n\nPas de rendu *Wiki*.\nDonc pas de gras.\n&lt;p class=\"paragraph\"/&gt;{/pre}\n&lt;/pre&gt;\n&lt;/td&gt;\n&lt;td&gt;\n\n{/pre}\nPas de rendu *Wiki*.\nDonc pas de gras.\n&lt;p class=\"paragraph\"/&gt;&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\n&lt;b class=\"bold\"&gt;Pas de rendu HTML et Wiki&lt;/b&gt;&lt;br/&gt;\nCela permet d\'afficher du contenu HTML et Wiki dans un page Wiki.\n&lt;/td&gt;\n&lt;td&gt;\n&lt;pre&gt;\n\n{code:xml}\nPas de rendu &lt;b&gt;Html&lt;/b&gt;.\nDonc pas de gras en HTML.\nNi de *gras* en wiki.\n{code}\n\n&lt;/pre&gt;\n&lt;/td&gt;\n\n&lt;td&gt;\n&lt;div class=\"code\"&gt;&lt;pre&gt;Pas de rendu &lt;span class=\"xml&amp;#45;tag\"&gt;&amp;#60;b&amp;#62;&lt;/span&gt;Html&lt;span class=\"xml&amp;#45;tag\"&gt;&amp;#60;/b&amp;#62;&lt;/span&gt;.\nDonc pas de gras en HTML.\nNi de &amp;#42;gras&amp;#42; en wiki.&lt;/pre&gt;&lt;/div&gt;\n&lt;/td&gt;\n&lt;/tr&gt;\n&lt;tr&gt;\n&lt;td&gt;\na322 1\n{code}\nd324 1\na325 1\n{code}\n@\n\n\n1.7\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999290493</date>\n<version>1.7</version>\nd368 2\na369 2\n&amp;#23;includeTopic(\"Doc.TestInclude\")\n&amp;#23;includeForm(\"Doc.TestInclude\")\n@\n\n\n1.6\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999229363</date>\n<version>1.6</version>\nd368 2\na369 2\n&amp;#;includeTopic(\"Doc.TestInclude\")\n&amp;#;includeForm(\"Doc.TestInclude\")\n@\n\n\n1.5\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999184572</date>\n<version>1.5</version>\nd368 2\na369 2\n##includeTopic(\"Doc.TestInclude\")\n##includeForm(\"Doc.TestInclude\")\n@\n\n\n1.4\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999163777</date>\n<version>1.4</version>\nd368 2\na369 2\n#!includeTopic(\"Doc.TestInclude\")\n#!includeForm(\"Doc.TestInclude\")\n@\n\n\n1.3\nlog\n@@\ntext\n@d12 2\na13 2\n<date>1092999138671</date>\n<version>1.3</version>\nd368 2\na369 2\n#includeTopic(\"Doc.TestInclude\")\n#includeForm(\"Doc.TestInclude\")\nd374 1\n@\n\n\n1.2\nlog\n@Doc.XWikiSyntax\n@\ntext\n@d12 10\na21 3\n<date>1092999071432</date>\n<version>1.2</version>\n<content>&lt;table class=\"wiki-table\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\" width=\"80%\"&gt;\nd367 1\na367 2\n&lt;pre&gt;\n\na368 1\n\nd370 1\na370 2\n\n&lt;/pre&gt;\nd373 2\na374 2\nNom du document: TestInclude\n&lt;p class=\"paragraph\"/&gt;Nom du document: XWikiSyntax&lt;p class=\"paragraph\"/&gt;\n@\n','1.9','');
INSERT INTO xwikidoc VALUES (-855298240,'WhatsNew','fr','',1,'2004-08-20 13:06:20','2004-08-20 13:05:15','XWiki.Admin','XWiki.Admin','Main','#set($diff = $request.get(\"diff\"))\r\n#if(!$diff)\r\n#set($diff = \"0\")\r\n#end\r\n#set($rsvc= $xwiki.xWiki.getRightService())\r\n1 Quoi de Neuf\r\n\r\nLes documents suivants ont été créés ou modifiés:\r\n\r\n#if($diff == \"0\")\r\n[Cliquez ici pour voir les différences>$doc.name?diff=1]\r\n#end\r\n\r\n#set ($sql = \"where doc.name <> \'WhatsNew\' order by doc.date desc\")\r\n#set ($list = $xwiki.searchDocuments($sql , 20 , 0))\r\n#foreach ($item in $list)\r\n#if (!$rsvc || $rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\r\n#set($bentrydoc = $xwiki.getDocument($item))\r\n   * [${bentrydoc.web}.$bentrydoc.name] par [$bentrydoc.author] le $formatter.formatLongDateTime($bentrydoc.date)\r\n\r\n#if ($diff == \"1\")\r\n#set($deltas= $bentrydoc.getLastChanges())\r\n#foreach($delta in $deltas)\r\n#set($chunk = $delta.revised)\r\n#if ($chunk.size()>0)\r\n<div style=\"border-left: 8px solid #00FF00\">\r\n#if (($type == \"source\")||($type == \"xml\"))\r\n<pre>\r\n$xwiki.renderChunk($chunk, true, $tdoc)\r\n</pre>\r\n#else\r\n$xwiki.renderChunk($chunk, $tdoc)\r\n#end\r\n</div>\r\n#end\r\n\r\n#set($chunk = $delta.original)\r\n#if ($chunk.size()>0)\r\n<div style=\"border-left: 8px solid #FF0000\">\r\n#if (($type == \"source\")||($type == \"xml\"))\r\n<pre>\r\n$xwiki.renderChunk($chunk, true, $tdoc)\r\n</pre>\r\n#else\r\n$xwiki.renderChunk($chunk, $tdoc)\r\n#end\r\n</div>\r\n#end\r\n#end\r\n#end\r\n\r\n#end\r\n#end','head	1.3;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.3\ndate	2004.08.20.13.06.20;	author tomcat;	state Exp;\nbranches;\nnext	1.2;\n\n1.2\ndate	2004.08.20.13.05.15;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.3\nlog\n@@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>WhatsNew</name>\n<language>fr</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1092999915104</creationDate>\n<date>1092999980113</date>\n<version>1.3</version>\n<content>#set($diff = $request.get(\"diff\"))\n#if(!$diff)\n#set($diff = \"0\")\n#end\n#set($rsvc= $xwiki.xWiki.getRightService())\n1 Quoi de Neuf\n\nLes documents suivants ont été créés ou modifiés:\n\n#if($diff == \"0\")\n[Cliquez ici pour voir les différences&gt;$doc.name?diff=1]\n#end\n\n#set ($sql = \"where doc.name &lt;&gt; \'WhatsNew\' order by doc.date desc\")\n#set ($list = $xwiki.searchDocuments($sql , 20 , 0))\n#foreach ($item in $list)\n#if (!$rsvc || $rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\n#set($bentrydoc = $xwiki.getDocument($item))\n   * [${bentrydoc.web}.$bentrydoc.name] par [$bentrydoc.author] le $formatter.formatLongDateTime($bentrydoc.date)\n\n#if ($diff == \"1\")\n#set($deltas= $bentrydoc.getLastChanges())\n#foreach($delta in $deltas)\n#set($chunk = $delta.revised)\n#if ($chunk.size()&gt;0)\n&lt;div style=\"border-left: 8px solid #00FF00\"&gt;\n#if (($type == \"source\")||($type == \"xml\"))\n&lt;pre&gt;\n$xwiki.renderChunk($chunk, true, $tdoc)\n&lt;/pre&gt;\n#else\n$xwiki.renderChunk($chunk, $tdoc)\n#end\n&lt;/div&gt;\n#end\n\n#set($chunk = $delta.original)\n#if ($chunk.size()&gt;0)\n&lt;div style=\"border-left: 8px solid #FF0000\"&gt;\n#if (($type == \"source\")||($type == \"xml\"))\n&lt;pre&gt;\n$xwiki.renderChunk($chunk, true, $tdoc)\n&lt;/pre&gt;\n#else\n$xwiki.renderChunk($chunk, $tdoc)\n#end\n&lt;/div&gt;\n#end\n#end\n#end\n\n#end\n#end</content>\n</xwikidoc>\n@\n\n\n1.2\nlog\n@Main.WhatsNew\n@\ntext\n@d12 2\na13 2\n<date>1092999915105</date>\n<version>1.2</version>\nd30 1\na30 1\n#if ($rsvc.hasAccessLevel(\"view\", $context.user, \"${context.database}:${item}\", true, $context.context))\n@\n','1.3','');
INSERT INTO xwikidoc VALUES (-1991671769,'WebSearch','','en',0,'2004-08-20 13:10:04','2004-08-20 13:09:56','XWiki.Admin','XWiki.Admin','Main','1 Search on this Wiki\r\n\r\n#if ($request.getParameter(\"text\"))\r\n#set ($text = $request.getParameter(\"text\") )\r\n#else\r\n#set($text = \"\")\r\n#end\r\n\r\n<center>\r\n<form action=\"WebSearch\">\r\n<input type=\"text\" name=\"text\" value=\"$!text\">&nbsp;<input type=\"submit\" name=\"search\" value=\"Search\">\r\n</form>\r\n</center>\r\n\r\n#set ($sql = \"where doc.content like \'%$text%\' order by doc.date desc\")\r\n#set ($start = 0)\r\n#set ($nb = 50)\r\n<!-- \r\nSql: $sql\r\nSql: $start\r\nSql: $nb\r\n-->\r\n#set ($list = $xwiki.searchDocuments($sql , $nb, $start))\r\n#foreach ($item in $list)\r\n#set($bentrydoc = $xwiki.getDocument($item))\r\n   * [${bentrydoc.web}.$bentrydoc.name] by [$bentrydoc.author] on $formatter.formatLongDateTime($bentrydoc.date)\r\n#end','head	1.2;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.2\ndate	2004.08.20.13.10.04;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.2\nlog\n@Main.WebSearch\n@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Main</web>\n<name>WebSearch</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1093000196008</creationDate>\n<date>1093000204834</date>\n<version>1.2</version>\n<content>1 Search on this Wiki\n\n#if ($request.getParameter(\"text\"))\n#set ($text = $request.getParameter(\"text\") )\n#else\n#set($text = \"\")\n#end\n\n&lt;center&gt;\n&lt;form action=\"WebSearch\"&gt;\n&lt;input type=\"text\" name=\"text\" value=\"$!text\"&gt;&amp;nbsp;&lt;input type=\"submit\" name=\"search\" value=\"Search\"&gt;\n&lt;/form&gt;\n&lt;/center&gt;\n\n#set ($sql = \"where doc.content like \'%$text%\' order by doc.date desc\")\n#set ($start = 0)\n#set ($nb = 50)\n&lt;!-- \nSql: $sql\nSql: $start\nSql: $nb\n--&gt;\n#set ($list = $xwiki.searchDocuments($sql , $nb, $start))\n#foreach ($item in $list)\n#set($bentrydoc = $xwiki.getDocument($item))\n   * [${bentrydoc.web}.$bentrydoc.name] by [$bentrydoc.author] on $formatter.formatLongDateTime($bentrydoc.date)\n#end</content>\n</xwikidoc>\n@\n','1.2','');
INSERT INTO xwikidoc VALUES (2091040026,'WebHome','fr','',1,'2004-08-20 13:19:38','2004-08-20 13:19:38','XWiki.Admin','XWiki.Admin','Sandbox','1 Zone d\'entrainement\r\n\r\nCette zone est utile pour s\'entrainer à la syntaxe XWiki:\r\n\r\n   * [TestTopic1], [TestTopic2], [TestTopic3], [TestTopic4], [TestTopic5] \r\n   * [TestTopic6], [TestTopic7], [TestTopic8], [TestTopic9], [TestTopic10]\r\n   * [TestTopic11], [TestTopic12], [TestTopic13], [TestTopic14], [TestTopic15]\r\n\r\n','head	1.2;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.2\ndate	2004.08.20.13.19.38;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.2\nlog\n@Sandbox.WebHome\n@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>Sandbox</web>\n<name>WebHome</name>\n<language>fr</language>\n<defaultLanguage></defaultLanguage>\n<translation>1</translation>\n<parent></parent>\n<author>XWiki.Admin</author>\n<creationDate>1093000778096</creationDate>\n<date>1093000778097</date>\n<version>1.2</version>\n<content>1 Zone d\'entrainement\n\nCette zone est utile pour s\'entrainer à la syntaxe XWiki:\n\n   * [TestTopic1], [TestTopic2], [TestTopic3], [TestTopic4], [TestTopic5] \n   * [TestTopic6], [TestTopic7], [TestTopic8], [TestTopic9], [TestTopic10]\n   * [TestTopic11], [TestTopic12], [TestTopic13], [TestTopic14], [TestTopic15]\n\n</content>\n</xwikidoc>\n@\n','1.2','');
INSERT INTO xwikidoc VALUES (-1888779022,'XWikiAdminGroup','','en',0,'2004-09-15 01:37:11','2004-09-15 01:37:03','xwiki:XWiki.LudovicDubost','xwiki:XWiki.LudovicDubost','XWiki','#includeForm(\"XWiki.XWikiGroupSheet\")','head	1.2;\naccess;\nsymbols;\nlocks; strict;\ncomment	@# @;\n\n\n1.2\ndate	2004.09.15.01.37.11;	author tomcat;	state Exp;\nbranches;\nnext	;\n\n\ndesc\n@@\n\n\n1.2\nlog\n@XWiki.XWikiAdminGroup\n@\ntext\n@<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n<xwikidoc>\n<web>XWiki</web>\n<name>XWikiAdminGroup</name>\n<language></language>\n<defaultLanguage>en</defaultLanguage>\n<translation>0</translation>\n<parent>XWiki.XWikiGroups</parent>\n<author>xwiki:XWiki.LudovicDubost</author>\n<creationDate>1095205023646</creationDate>\n<date>1095205031954</date>\n<version>1.2</version>\n<object>\n<class>\n<name>XWiki.XWikiGroups</name>\n<member>\n<name>member</name>\n<prettyName>Member</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</member>\n</class>\n<name>XWiki.XWikiAdminGroup</name>\n<number>0</number>\n<className>XWiki.XWikiGroups</className>\n<property>\n<member>XWiki.Admin</member>\n</property>\n</object>\n<object>\n<class>\n<name>XWiki.XWikiGroups</name>\n<member>\n<name>member</name>\n<prettyName>Member</prettyName>\n<unmodifiable>0</unmodifiable>\n<size>30</size>\n<number>1</number>\n<classType>com.xpn.xwiki.objects.classes.StringClass</classType>\n</member>\n</class>\n<name>XWiki.XWikiAdminGroup</name>\n<number>1</number>\n<className>XWiki.XWikiGroups</className>\n<property>\n<member></member>\n</property>\n</object>\n<content>#includeForm(\"XWiki.XWikiGroupSheet\")</content>\n</xwikidoc>\n@\n','1.2','XWiki.XWikiGroups');

--
-- Table structure for table `xwikidoubles`
--

CREATE TABLE xwikidoubles (
  XWD_ID int(11) NOT NULL default '0',
  XWD_NAME varchar(255) NOT NULL default '',
  XWD_VALUE double default NULL,
  PRIMARY KEY  (XWD_ID,XWD_NAME),
  KEY XWD_ID (XWD_ID,XWD_NAME),
  KEY XWD_ID_2 (XWD_ID,XWD_NAME),
  KEY XWD_ID_3 (XWD_ID,XWD_NAME),
  KEY XWD_ID_4 (XWD_ID,XWD_NAME),
  KEY XWD_ID_5 (XWD_ID,XWD_NAME),
  KEY XWD_ID_6 (XWD_ID,XWD_NAME),
  KEY XWD_ID_7 (XWD_ID,XWD_NAME),
  KEY XWD_ID_8 (XWD_ID,XWD_NAME),
  KEY XWD_ID_9 (XWD_ID,XWD_NAME),
  KEY XWD_ID_10 (XWD_ID,XWD_NAME),
  KEY XWD_ID_11 (XWD_ID,XWD_NAME),
  KEY XWD_ID_12 (XWD_ID,XWD_NAME),
  KEY XWD_ID_13 (XWD_ID,XWD_NAME),
  KEY XWD_ID_14 (XWD_ID,XWD_NAME),
  KEY XWD_ID_15 (XWD_ID,XWD_NAME),
  KEY XWD_ID_16 (XWD_ID,XWD_NAME),
  KEY XWD_ID_17 (XWD_ID,XWD_NAME),
  KEY XWD_ID_18 (XWD_ID,XWD_NAME),
  KEY XWD_ID_19 (XWD_ID,XWD_NAME),
  KEY XWD_ID_20 (XWD_ID,XWD_NAME),
  KEY XWD_ID_21 (XWD_ID,XWD_NAME),
  KEY XWD_ID_22 (XWD_ID,XWD_NAME),
  KEY XWD_ID_23 (XWD_ID,XWD_NAME),
  KEY XWD_ID_24 (XWD_ID,XWD_NAME),
  KEY XWD_ID_25 (XWD_ID,XWD_NAME),
  KEY XWD_ID_26 (XWD_ID,XWD_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikidoubles`
--


--
-- Table structure for table `xwikifloats`
--

CREATE TABLE xwikifloats (
  XWF_ID int(11) NOT NULL default '0',
  XWF_NAME varchar(255) NOT NULL default '',
  XWF_VALUE float default NULL,
  PRIMARY KEY  (XWF_ID,XWF_NAME),
  KEY XWF_ID (XWF_ID,XWF_NAME),
  KEY XWF_ID_2 (XWF_ID,XWF_NAME),
  KEY XWF_ID_3 (XWF_ID,XWF_NAME),
  KEY XWF_ID_4 (XWF_ID,XWF_NAME),
  KEY XWF_ID_5 (XWF_ID,XWF_NAME),
  KEY XWF_ID_6 (XWF_ID,XWF_NAME),
  KEY XWF_ID_7 (XWF_ID,XWF_NAME),
  KEY XWF_ID_8 (XWF_ID,XWF_NAME),
  KEY XWF_ID_9 (XWF_ID,XWF_NAME),
  KEY XWF_ID_10 (XWF_ID,XWF_NAME),
  KEY XWF_ID_11 (XWF_ID,XWF_NAME),
  KEY XWF_ID_12 (XWF_ID,XWF_NAME),
  KEY XWF_ID_13 (XWF_ID,XWF_NAME),
  KEY XWF_ID_14 (XWF_ID,XWF_NAME),
  KEY XWF_ID_15 (XWF_ID,XWF_NAME),
  KEY XWF_ID_16 (XWF_ID,XWF_NAME),
  KEY XWF_ID_17 (XWF_ID,XWF_NAME),
  KEY XWF_ID_18 (XWF_ID,XWF_NAME),
  KEY XWF_ID_19 (XWF_ID,XWF_NAME),
  KEY XWF_ID_20 (XWF_ID,XWF_NAME),
  KEY XWF_ID_21 (XWF_ID,XWF_NAME),
  KEY XWF_ID_22 (XWF_ID,XWF_NAME),
  KEY XWF_ID_23 (XWF_ID,XWF_NAME),
  KEY XWF_ID_24 (XWF_ID,XWF_NAME),
  KEY XWF_ID_25 (XWF_ID,XWF_NAME),
  KEY XWF_ID_26 (XWF_ID,XWF_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikifloats`
--


--
-- Table structure for table `xwikiintegers`
--

CREATE TABLE xwikiintegers (
  XWI_ID int(11) NOT NULL default '0',
  XWI_NAME varchar(255) NOT NULL default '',
  XWI_VALUE int(11) default NULL,
  PRIMARY KEY  (XWI_ID,XWI_NAME),
  KEY XWI_ID (XWI_ID,XWI_NAME),
  KEY XWI_ID_2 (XWI_ID,XWI_NAME),
  KEY XWI_ID_3 (XWI_ID,XWI_NAME),
  KEY XWI_ID_4 (XWI_ID,XWI_NAME),
  KEY XWI_ID_5 (XWI_ID,XWI_NAME),
  KEY XWI_ID_6 (XWI_ID,XWI_NAME),
  KEY XWI_ID_7 (XWI_ID,XWI_NAME),
  KEY XWI_ID_8 (XWI_ID,XWI_NAME),
  KEY XWI_ID_9 (XWI_ID,XWI_NAME),
  KEY XWI_ID_10 (XWI_ID,XWI_NAME),
  KEY XWI_ID_11 (XWI_ID,XWI_NAME),
  KEY XWI_ID_12 (XWI_ID,XWI_NAME),
  KEY XWI_ID_13 (XWI_ID,XWI_NAME),
  KEY XWI_ID_14 (XWI_ID,XWI_NAME),
  KEY XWI_ID_15 (XWI_ID,XWI_NAME),
  KEY XWI_ID_16 (XWI_ID,XWI_NAME),
  KEY XWI_ID_17 (XWI_ID,XWI_NAME),
  KEY XWI_ID_18 (XWI_ID,XWI_NAME),
  KEY XWI_ID_19 (XWI_ID,XWI_NAME),
  KEY XWI_ID_20 (XWI_ID,XWI_NAME),
  KEY XWI_ID_21 (XWI_ID,XWI_NAME),
  KEY XWI_ID_22 (XWI_ID,XWI_NAME),
  KEY XWI_ID_23 (XWI_ID,XWI_NAME),
  KEY XWI_ID_24 (XWI_ID,XWI_NAME),
  KEY XWI_ID_25 (XWI_ID,XWI_NAME),
  KEY XWI_ID_26 (XWI_ID,XWI_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikiintegers`
--

INSERT INTO xwikiintegers VALUES (538939758,'allow',1);
INSERT INTO xwikiintegers VALUES (-1468381210,'allow',1);
INSERT INTO xwikiintegers VALUES (165499024,'allow',1);
INSERT INTO xwikiintegers VALUES (130775106,'allow',1);
INSERT INTO xwikiintegers VALUES (562594863,'active',1);
INSERT INTO xwikiintegers VALUES (1886528249,'allow',1);
INSERT INTO xwikiintegers VALUES (477451371,'allow',1);
INSERT INTO xwikiintegers VALUES (-213172058,'active',1);

--
-- Table structure for table `xwikilargestrings`
--

CREATE TABLE xwikilargestrings (
  XWL_ID int(11) NOT NULL default '0',
  XWL_NAME varchar(255) NOT NULL default '',
  XWL_VALUE text,
  PRIMARY KEY  (XWL_ID,XWL_NAME),
  KEY XWL_ID (XWL_ID,XWL_NAME),
  KEY XWL_ID_2 (XWL_ID,XWL_NAME),
  KEY XWL_ID_3 (XWL_ID,XWL_NAME),
  KEY XWL_ID_4 (XWL_ID,XWL_NAME),
  KEY XWL_ID_5 (XWL_ID,XWL_NAME),
  KEY XWL_ID_6 (XWL_ID,XWL_NAME),
  KEY XWL_ID_7 (XWL_ID,XWL_NAME),
  KEY XWL_ID_8 (XWL_ID,XWL_NAME),
  KEY XWL_ID_9 (XWL_ID,XWL_NAME),
  KEY XWL_ID_10 (XWL_ID,XWL_NAME),
  KEY XWL_ID_11 (XWL_ID,XWL_NAME),
  KEY XWL_ID_12 (XWL_ID,XWL_NAME),
  KEY XWL_ID_13 (XWL_ID,XWL_NAME),
  KEY XWL_ID_14 (XWL_ID,XWL_NAME),
  KEY XWL_ID_15 (XWL_ID,XWL_NAME),
  KEY XWL_ID_16 (XWL_ID,XWL_NAME),
  KEY XWL_ID_17 (XWL_ID,XWL_NAME),
  KEY XWL_ID_18 (XWL_ID,XWL_NAME),
  KEY XWL_ID_19 (XWL_ID,XWL_NAME),
  KEY XWL_ID_20 (XWL_ID,XWL_NAME),
  KEY XWL_ID_21 (XWL_ID,XWL_NAME),
  KEY XWL_ID_22 (XWL_ID,XWL_NAME),
  KEY XWL_ID_23 (XWL_ID,XWL_NAME),
  KEY XWL_ID_24 (XWL_ID,XWL_NAME),
  KEY XWL_ID_25 (XWL_ID,XWL_NAME),
  KEY XWL_ID_26 (XWL_ID,XWL_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikilargestrings`
--

INSERT INTO xwikilargestrings VALUES (-944455444,'confirmation_email_content','');
INSERT INTO xwikilargestrings VALUES (-944455444,'meta','<meta name=\"revisit-after\" content=\"7 days\" />\r\n<meta name=\"description\" content=\"Wiki personel\" />\r\n<meta name=\"keywords\" content=\"wiki\" />\r\n<meta name=\"distribution\" content=\"GLOBAL\" />\r\n<meta name=\"rating\" content=\"General\" />\r\n<meta name=\"copyright\" content=\"Copyright (c) 2004 Auteurs\" />\r\n<meta name=\"author\" content=\"\" />\r\n<meta http-equiv=\"reply-to\" content=\"\" />\r\n<meta name=\"language\" content=\"fr\" />\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />');
INSERT INTO xwikilargestrings VALUES (-944455444,'menu','<a href=\"../../view/Main/WebHome\">Wiki</a><span> | </span> \r\n<a href=\"../../view/Main/WhatsNew\">Quoi de Neuf</a><span> | </span> \r\n<a href=\"../../view/Main/WebSearch\">Recherche</a><span> | </span> \r\n<a href=\"../../view/Admin/WebHome\">Admin</a><span> | </span> \r\n<a href=\"../../view/Doc/WebHome\">Doc</a><span> | </span>\r\n<a href=\"../../view/Sandbox/WebHome\">Test</a> \r\n');
INSERT INTO xwikilargestrings VALUES (-944455444,'validation_email_content','');
INSERT INTO xwikilargestrings VALUES (-1383579234,'confirmation_email_content','');
INSERT INTO xwikilargestrings VALUES (-1383579234,'meta','<meta name=\"revisit-after\" content=\"7 days\" />\r\n<meta name=\"description\" content=\"Wiki\" />\r\n<meta name=\"keywords\" content=\"wiki\" />\r\n<meta name=\"distribution\" content=\"GLOBAL\" />\r\n<meta name=\"rating\" content=\"General\" />\r\n<meta name=\"copyright\" content=\"Copyright (c) 2004 Contributing Authors\" />\r\n<meta name=\"author\" content=\"\" />\r\n<meta http-equiv=\"reply-to\" content=\"\" />\r\n<meta name=\"language\" content=\"en\" />\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />');
INSERT INTO xwikilargestrings VALUES (-1383579234,'menu','<a href=\"../../view/Main/WebHome\">Wiki</a><span> | </span> \r\n<a href=\"../../view/Main/WhatsNew\">Whats New</a><span> | </span> \r\n<a href=\"../../view/Main/WebSearch\">Search</a><span> | </span> \r\n<a href=\"../../view/Admin/WebHome\">Admin</a><span> | </span> \r\n<a href=\"../../view/Doc/WebHome\">Doc</a><span> | </span>\r\n<a href=\"../../view/Sandbox/WebHome\">Test</a> ');
INSERT INTO xwikilargestrings VALUES (-1383579234,'validation_email_content','');
INSERT INTO xwikilargestrings VALUES (-1531382335,'menu','<a href=\"WebHome\">Test</a>\r\n');
INSERT INTO xwikilargestrings VALUES (-1531382335,'meta','');

--
-- Table structure for table `xwikilistitems`
--

CREATE TABLE xwikilistitems (
  XWL_ID int(11) NOT NULL default '0',
  XWL_NAME varchar(255) NOT NULL default '',
  XWL_VALUE varchar(255) default NULL,
  XWL_NUMBER int(11) NOT NULL default '0',
  PRIMARY KEY  (XWL_ID,XWL_NAME,XWL_NUMBER),
  KEY XWL_ID (XWL_ID,XWL_NAME),
  KEY XWL_ID_2 (XWL_ID,XWL_NAME),
  KEY XWL_ID_3 (XWL_ID,XWL_NAME),
  KEY XWL_ID_4 (XWL_ID,XWL_NAME),
  KEY XWL_ID_5 (XWL_ID,XWL_NAME),
  KEY XWL_ID_6 (XWL_ID,XWL_NAME),
  KEY XWL_ID_7 (XWL_ID,XWL_NAME),
  KEY XWL_ID_8 (XWL_ID,XWL_NAME),
  KEY XWL_ID_9 (XWL_ID,XWL_NAME),
  KEY XWL_ID_10 (XWL_ID,XWL_NAME),
  KEY XWL_ID_11 (XWL_ID,XWL_NAME),
  KEY XWL_ID_12 (XWL_ID,XWL_NAME),
  KEY XWL_ID_13 (XWL_ID,XWL_NAME),
  KEY XWL_ID_14 (XWL_ID,XWL_NAME),
  KEY XWL_ID_15 (XWL_ID,XWL_NAME),
  KEY XWL_ID_16 (XWL_ID,XWL_NAME),
  KEY XWL_ID_17 (XWL_ID,XWL_NAME),
  KEY XWL_ID_18 (XWL_ID,XWL_NAME),
  KEY XWL_ID_19 (XWL_ID,XWL_NAME),
  KEY XWL_ID_20 (XWL_ID,XWL_NAME),
  KEY XWL_ID_21 (XWL_ID,XWL_NAME),
  KEY XWL_ID_22 (XWL_ID,XWL_NAME),
  KEY XWL_ID_23 (XWL_ID,XWL_NAME),
  KEY XWL_ID_24 (XWL_ID,XWL_NAME),
  KEY XWL_ID_25 (XWL_ID,XWL_NAME),
  KEY XWL_ID_26 (XWL_ID,XWL_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikilistitems`
--


--
-- Table structure for table `xwikilists`
--

CREATE TABLE xwikilists (
  XWL_ID int(11) NOT NULL default '0',
  XWL_NAME varchar(255) NOT NULL default '',
  PRIMARY KEY  (XWL_ID,XWL_NAME),
  KEY XWL_ID (XWL_ID,XWL_NAME),
  KEY XWL_ID_2 (XWL_ID,XWL_NAME),
  KEY XWL_ID_3 (XWL_ID,XWL_NAME),
  KEY XWL_ID_4 (XWL_ID,XWL_NAME),
  KEY XWL_ID_5 (XWL_ID,XWL_NAME),
  KEY XWL_ID_6 (XWL_ID,XWL_NAME),
  KEY XWL_ID_7 (XWL_ID,XWL_NAME),
  KEY XWL_ID_8 (XWL_ID,XWL_NAME),
  KEY XWL_ID_9 (XWL_ID,XWL_NAME),
  KEY XWL_ID_10 (XWL_ID,XWL_NAME),
  KEY XWL_ID_11 (XWL_ID,XWL_NAME),
  KEY XWL_ID_12 (XWL_ID,XWL_NAME),
  KEY XWL_ID_13 (XWL_ID,XWL_NAME),
  KEY XWL_ID_14 (XWL_ID,XWL_NAME),
  KEY XWL_ID_15 (XWL_ID,XWL_NAME),
  KEY XWL_ID_16 (XWL_ID,XWL_NAME),
  KEY XWL_ID_17 (XWL_ID,XWL_NAME),
  KEY XWL_ID_18 (XWL_ID,XWL_NAME),
  KEY XWL_ID_19 (XWL_ID,XWL_NAME),
  KEY XWL_ID_20 (XWL_ID,XWL_NAME),
  KEY XWL_ID_21 (XWL_ID,XWL_NAME),
  KEY XWL_ID_22 (XWL_ID,XWL_NAME),
  KEY XWL_ID_23 (XWL_ID,XWL_NAME),
  KEY XWL_ID_24 (XWL_ID,XWL_NAME),
  KEY XWL_ID_25 (XWL_ID,XWL_NAME),
  KEY XWL_ID_26 (XWL_ID,XWL_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikilists`
--


--
-- Table structure for table `xwikilongs`
--

CREATE TABLE xwikilongs (
  XWL_ID int(11) NOT NULL default '0',
  XWL_NAME varchar(255) NOT NULL default '',
  XWL_VALUE bigint(20) default NULL,
  PRIMARY KEY  (XWL_ID,XWL_NAME),
  KEY XWL_ID (XWL_ID,XWL_NAME),
  KEY XWL_ID_2 (XWL_ID,XWL_NAME),
  KEY XWL_ID_3 (XWL_ID,XWL_NAME),
  KEY XWL_ID_4 (XWL_ID,XWL_NAME),
  KEY XWL_ID_5 (XWL_ID,XWL_NAME),
  KEY XWL_ID_6 (XWL_ID,XWL_NAME),
  KEY XWL_ID_7 (XWL_ID,XWL_NAME),
  KEY XWL_ID_8 (XWL_ID,XWL_NAME),
  KEY XWL_ID_9 (XWL_ID,XWL_NAME),
  KEY XWL_ID_10 (XWL_ID,XWL_NAME),
  KEY XWL_ID_11 (XWL_ID,XWL_NAME),
  KEY XWL_ID_12 (XWL_ID,XWL_NAME),
  KEY XWL_ID_13 (XWL_ID,XWL_NAME),
  KEY XWL_ID_14 (XWL_ID,XWL_NAME),
  KEY XWL_ID_15 (XWL_ID,XWL_NAME),
  KEY XWL_ID_16 (XWL_ID,XWL_NAME),
  KEY XWL_ID_17 (XWL_ID,XWL_NAME),
  KEY XWL_ID_18 (XWL_ID,XWL_NAME),
  KEY XWL_ID_19 (XWL_ID,XWL_NAME),
  KEY XWL_ID_20 (XWL_ID,XWL_NAME),
  KEY XWL_ID_21 (XWL_ID,XWL_NAME),
  KEY XWL_ID_22 (XWL_ID,XWL_NAME),
  KEY XWL_ID_23 (XWL_ID,XWL_NAME),
  KEY XWL_ID_24 (XWL_ID,XWL_NAME),
  KEY XWL_ID_25 (XWL_ID,XWL_NAME),
  KEY XWL_ID_26 (XWL_ID,XWL_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikilongs`
--

INSERT INTO xwikilongs VALUES (-944455444,'editbox_width',80);
INSERT INTO xwikilongs VALUES (-944455444,'editbox_height',20);
INSERT INTO xwikilongs VALUES (-1383579234,'editbox_width',NULL);
INSERT INTO xwikilongs VALUES (-1383579234,'editbox_height',NULL);
INSERT INTO xwikilongs VALUES (-1531382335,'editbox_width',NULL);
INSERT INTO xwikilongs VALUES (-1531382335,'editbox_height',NULL);

--
-- Table structure for table `xwikinumberclasses`
--

CREATE TABLE xwikinumberclasses (
  XWN_ID int(11) NOT NULL default '0',
  XWN_NAME varchar(255) NOT NULL default '',
  XWN_SIZE int(11) default NULL,
  XWN_NUMBERTYPE varchar(20) default NULL,
  PRIMARY KEY  (XWN_ID,XWN_NAME),
  KEY XWN_ID (XWN_ID,XWN_NAME),
  KEY XWN_ID_2 (XWN_ID,XWN_NAME),
  KEY XWN_ID_3 (XWN_ID,XWN_NAME),
  KEY XWN_ID_4 (XWN_ID,XWN_NAME),
  KEY XWN_ID_5 (XWN_ID,XWN_NAME),
  KEY XWN_ID_6 (XWN_ID,XWN_NAME),
  KEY XWN_ID_7 (XWN_ID,XWN_NAME),
  KEY XWN_ID_8 (XWN_ID,XWN_NAME),
  KEY XWN_ID_9 (XWN_ID,XWN_NAME),
  KEY XWN_ID_10 (XWN_ID,XWN_NAME),
  KEY XWN_ID_11 (XWN_ID,XWN_NAME),
  KEY XWN_ID_12 (XWN_ID,XWN_NAME),
  KEY XWN_ID_13 (XWN_ID,XWN_NAME),
  KEY XWN_ID_14 (XWN_ID,XWN_NAME),
  KEY XWN_ID_15 (XWN_ID,XWN_NAME),
  KEY XWN_ID_16 (XWN_ID,XWN_NAME),
  KEY XWN_ID_17 (XWN_ID,XWN_NAME),
  KEY XWN_ID_18 (XWN_ID,XWN_NAME),
  KEY XWN_ID_19 (XWN_ID,XWN_NAME),
  KEY XWN_ID_20 (XWN_ID,XWN_NAME),
  KEY XWN_ID_21 (XWN_ID,XWN_NAME),
  KEY XWN_ID_22 (XWN_ID,XWN_NAME),
  KEY XWN_ID_23 (XWN_ID,XWN_NAME),
  KEY XWN_ID_24 (XWN_ID,XWN_NAME),
  KEY XWN_ID_25 (XWN_ID,XWN_NAME),
  KEY XWN_ID_26 (XWN_ID,XWN_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikinumberclasses`
--

INSERT INTO xwikinumberclasses VALUES (104408758,'editbox_width',5,'long');
INSERT INTO xwikinumberclasses VALUES (104408758,'editbox_height',5,'long');
INSERT INTO xwikinumberclasses VALUES (-1905796263,'allow',2,'integer');
INSERT INTO xwikinumberclasses VALUES (981637980,'allow',2,'integer');

--
-- Table structure for table `xwikiobjects`
--

CREATE TABLE xwikiobjects (
  XWO_ID int(11) NOT NULL default '0',
  XWO_NUMBER int(11) default NULL,
  XWO_NAME varchar(255) NOT NULL default '',
  XWO_CLASSNAME varchar(255) NOT NULL default '',
  PRIMARY KEY  (XWO_ID)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikiobjects`
--

INSERT INTO xwikiobjects VALUES (-944455444,0,'XWiki.XWikiPreferences','XWiki.XWikiPreferences');
INSERT INTO xwikiobjects VALUES (-1383579234,1,'XWiki.XWikiPreferences','XWiki.XWikiPreferences');
INSERT INTO xwikiobjects VALUES (538939758,0,'Sandbox.WebPreferences','XWiki.XWikiRights');
INSERT INTO xwikiobjects VALUES (-1531382335,0,'Sandbox.WebPreferences','XWiki.XWikiPreferences');
INSERT INTO xwikiobjects VALUES (-790920225,0,'XWiki.XWikiGroupTemplate','XWiki.XWikiGroups');
INSERT INTO xwikiobjects VALUES (-1468381210,0,'XWiki.XWikiPreferences','XWiki.XWikiGlobalRights');
INSERT INTO xwikiobjects VALUES (165499024,0,'XWiki.Admin','XWiki.XWikiRights');
INSERT INTO xwikiobjects VALUES (130775106,1,'XWiki.Admin','XWiki.XWikiRights');
INSERT INTO xwikiobjects VALUES (562594863,0,'XWiki.Admin','XWiki.XWikiUsers');
INSERT INTO xwikiobjects VALUES (-142093066,0,'XWiki.XWikiAllGroup','XWiki.XWikiGroups');
INSERT INTO xwikiobjects VALUES (1886528249,0,'XWiki.User1','XWiki.XWikiRights');
INSERT INTO xwikiobjects VALUES (477451371,1,'XWiki.User1','XWiki.XWikiRights');
INSERT INTO xwikiobjects VALUES (-213172058,0,'XWiki.User1','XWiki.XWikiUsers');
INSERT INTO xwikiobjects VALUES (887520040,1,'XWiki.XWikiAllGroup','XWiki.XWikiGroups');
INSERT INTO xwikiobjects VALUES (134878161,1,'XWiki.XWikiGroupTemplate','XWiki.XWikiGroups');
INSERT INTO xwikiobjects VALUES (1520038052,0,'XWiki.XWikiAdminGroup','XWiki.XWikiGroups');
INSERT INTO xwikiobjects VALUES (467690326,1,'XWiki.XWikiAdminGroup','XWiki.XWikiGroups');

--
-- Table structure for table `xwikiproperties`
--

CREATE TABLE xwikiproperties (
  XWP_ID int(11) NOT NULL default '0',
  XWP_NAME varchar(255) NOT NULL default '',
  XWP_CLASSTYPE varchar(255) default NULL,
  PRIMARY KEY  (XWP_ID,XWP_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikiproperties`
--

INSERT INTO xwikiproperties VALUES (-944455444,'admin_email','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'confirmation_email_content','com.xpn.xwiki.objects.LargeStringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'skin','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'title','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'language','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'meta','com.xpn.xwiki.objects.LargeStringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'version','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'editbox_width','com.xpn.xwiki.objects.LongProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'webcopyright','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'webbgcolor','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'smtp_server','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'editbox_height','com.xpn.xwiki.objects.LongProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'plugins','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'menu','com.xpn.xwiki.objects.LargeStringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'validation_email_content','com.xpn.xwiki.objects.LargeStringProperty');
INSERT INTO xwikiproperties VALUES (-944455444,'authenticate_edit','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'admin_email','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'confirmation_email_content','com.xpn.xwiki.objects.LargeStringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'skin','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'title','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'language','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'meta','com.xpn.xwiki.objects.LargeStringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'version','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'editbox_width','com.xpn.xwiki.objects.LongProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'webcopyright','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'webbgcolor','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'smtp_server','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'editbox_height','com.xpn.xwiki.objects.LongProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'plugins','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'menu','com.xpn.xwiki.objects.LargeStringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'validation_email_content','com.xpn.xwiki.objects.LargeStringProperty');
INSERT INTO xwikiproperties VALUES (-1383579234,'authenticate_edit','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (538939758,'groups','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (538939758,'users','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (538939758,'levels','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (538939758,'allow','com.xpn.xwiki.objects.IntegerProperty');
INSERT INTO xwikiproperties VALUES (-1531382335,'editbox_width','com.xpn.xwiki.objects.LongProperty');
INSERT INTO xwikiproperties VALUES (-1531382335,'skin','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1531382335,'webcopyright','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1531382335,'webbgcolor','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1531382335,'title','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1531382335,'editbox_height','com.xpn.xwiki.objects.LongProperty');
INSERT INTO xwikiproperties VALUES (-1531382335,'plugins','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1531382335,'menu','com.xpn.xwiki.objects.LargeStringProperty');
INSERT INTO xwikiproperties VALUES (-1531382335,'meta','com.xpn.xwiki.objects.LargeStringProperty');
INSERT INTO xwikiproperties VALUES (-1531382335,'authenticate_edit','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (467690326,'member','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (1520038052,'member','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (134878161,'member','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-790920225,'member','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1468381210,'users','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1468381210,'levels','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1468381210,'allow','com.xpn.xwiki.objects.IntegerProperty');
INSERT INTO xwikiproperties VALUES (165499024,'groups','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (165499024,'levels','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (165499024,'allow','com.xpn.xwiki.objects.IntegerProperty');
INSERT INTO xwikiproperties VALUES (130775106,'users','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (130775106,'levels','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (130775106,'allow','com.xpn.xwiki.objects.IntegerProperty');
INSERT INTO xwikiproperties VALUES (562594863,'password','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (562594863,'active','com.xpn.xwiki.objects.IntegerProperty');
INSERT INTO xwikiproperties VALUES (562594863,'email','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (562594863,'last_name','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (562594863,'first_name','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-142093066,'member','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (1886528249,'groups','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (1886528249,'levels','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (1886528249,'allow','com.xpn.xwiki.objects.IntegerProperty');
INSERT INTO xwikiproperties VALUES (477451371,'users','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (477451371,'levels','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (477451371,'allow','com.xpn.xwiki.objects.IntegerProperty');
INSERT INTO xwikiproperties VALUES (-213172058,'password','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-213172058,'active','com.xpn.xwiki.objects.IntegerProperty');
INSERT INTO xwikiproperties VALUES (-213172058,'email','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-213172058,'last_name','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-213172058,'first_name','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (887520040,'member','com.xpn.xwiki.objects.StringProperty');
INSERT INTO xwikiproperties VALUES (-1468381210,'groups','com.xpn.xwiki.objects.StringProperty');

--
-- Table structure for table `xwikislistclasses`
--

CREATE TABLE xwikislistclasses (
  XWL_ID int(11) NOT NULL default '0',
  XWL_NAME varchar(255) NOT NULL default '',
  XWL_DISPLAYTYPE varchar(20) default NULL,
  XWL_MULTISELECT tinyint(1) default NULL,
  XWL_SIZE int(11) default NULL,
  XWL_RELATIONAL tinyint(1) default NULL,
  XWL_VALUES text,
  PRIMARY KEY  (XWL_ID,XWL_NAME),
  KEY XWL_ID (XWL_ID,XWL_NAME),
  KEY XWL_ID_2 (XWL_ID,XWL_NAME),
  KEY XWL_ID_3 (XWL_ID,XWL_NAME),
  KEY XWL_ID_4 (XWL_ID,XWL_NAME),
  KEY XWL_ID_5 (XWL_ID,XWL_NAME),
  KEY XWL_ID_6 (XWL_ID,XWL_NAME),
  KEY XWL_ID_7 (XWL_ID,XWL_NAME),
  KEY XWL_ID_8 (XWL_ID,XWL_NAME),
  KEY XWL_ID_9 (XWL_ID,XWL_NAME),
  KEY XWL_ID_10 (XWL_ID,XWL_NAME),
  KEY XWL_ID_11 (XWL_ID,XWL_NAME),
  KEY XWL_ID_12 (XWL_ID,XWL_NAME),
  KEY XWL_ID_13 (XWL_ID,XWL_NAME),
  KEY XWL_ID_14 (XWL_ID,XWL_NAME),
  KEY XWL_ID_15 (XWL_ID,XWL_NAME),
  KEY XWL_ID_16 (XWL_ID,XWL_NAME),
  KEY XWL_ID_17 (XWL_ID,XWL_NAME),
  KEY XWL_ID_18 (XWL_ID,XWL_NAME),
  KEY XWL_ID_19 (XWL_ID,XWL_NAME),
  KEY XWL_ID_20 (XWL_ID,XWL_NAME),
  KEY XWL_ID_21 (XWL_ID,XWL_NAME),
  KEY XWL_ID_22 (XWL_ID,XWL_NAME),
  KEY XWL_ID_23 (XWL_ID,XWL_NAME),
  KEY XWL_ID_24 (XWL_ID,XWL_NAME),
  KEY XWL_ID_25 (XWL_ID,XWL_NAME),
  KEY XWL_ID_26 (XWL_ID,XWL_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikislistclasses`
--


--
-- Table structure for table `xwikistatsdoc`
--

CREATE TABLE xwikistatsdoc (
  XWS_ID int(11) NOT NULL default '0',
  XWS_NUMBER int(11) default NULL,
  XWS_NAME varchar(255) NOT NULL default '',
  XWS_CLASSNAME varchar(255) NOT NULL default '',
  XWS_ACTION varchar(255) NOT NULL default '',
  XWS_PAGE_VIEWS int(11) default NULL,
  XWS_UNIQUE_VISITORS int(11) default NULL,
  XWS_PERIOD int(11) default NULL,
  XWS_VISITS int(11) default NULL,
  PRIMARY KEY  (XWS_ID)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikistatsdoc`
--

INSERT INTO xwikistatsdoc VALUES (-809420173,1423260975,'Main.WebHome','internal','view',77,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1436236760,1423260975,'Main','internal','view',156,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1505599057,1423260975,'','internal','view',301,0,200408,21);
INSERT INTO xwikistatsdoc VALUES (538584337,1954198446,'Main.WebHome','internal','view',53,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1510726026,1954198446,'Main','internal','view',126,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1441363729,1954198446,'','internal','view',254,0,20040821,2);
INSERT INTO xwikistatsdoc VALUES (286365652,1423260975,'Doc.WebHome','internal','view',23,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (522947129,1423260975,'Doc','internal','view',57,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1634370162,1954198446,'Doc.WebHome','internal','view',15,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1870951639,1954198446,'Doc','internal','view',42,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-725034393,60375559,'Doc.WebHome','internal','save',9,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1055514100,60375559,'Doc','internal','save',32,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (147741732,60375559,'','internal','save',103,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-402087654,-2108628346,'Doc.WebHome','internal','save',9,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-250725419,-2108628346,'Doc','internal','save',32,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (570410685,-2108628346,'','internal','save',105,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-2097366994,60375559,'XWiki.XWikiPreferences','internal','save',5,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (640645372,60375559,'XWiki','internal','save',33,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1884936051,-2108628346,'XWiki.XWikiPreferences','internal','save',5,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1191553307,-2108628346,'XWiki','internal','save',34,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (29695963,1423260975,'XWiki.XWikiPreferences','internal','view',3,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1555372759,1423260975,'XWiki','internal','view',68,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1377700473,1954198446,'XWiki.XWikiPreferences','internal','view',3,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-207368249,1954198446,'XWiki','internal','view',68,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-381688620,60375559,'XWiki.XWikiPreferencesTemplate','internal','save',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-67061747,-2108628346,'XWiki.XWikiPreferencesTemplate','internal','save',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-470828287,1423260975,'XWiki.XWikiPreferencesTemplate','internal','view',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (877176223,1954198446,'XWiki.XWikiPreferencesTemplate','internal','view',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-2035562806,1423260975,'Main.WebSearch','internal','view',27,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-687558296,1954198446,'Main.WebSearch','internal','view',25,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-34095029,1423260975,'XWiki.XWikiUsers','internal','view',8,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1313909481,1954198446,'XWiki.XWikiUsers','internal','view',8,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (415016847,1423260975,'XWiki.RegisterNewUser','internal','view',8,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1763021357,1954198446,'XWiki.RegisterNewUser','internal','view',8,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (358548164,1423260975,'XWiki.WebHome','internal','view',3,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1706552674,1954198446,'XWiki.WebHome','internal','view',3,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (10302219,1423260975,'Main.WhatsNew','internal','view',33,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1358306729,1954198446,'Main.WhatsNew','internal','view',31,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1173264582,60375559,'Main.WebHome','internal','save',8,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1756606315,60375559,'Main','internal','save',28,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1142658085,-2108628346,'Main.WebHome','internal','save',8,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-996321386,-2108628346,'Main','internal','save',28,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-612460228,1423260975,'XWiki.LudovicDubost','internal','view',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (735544282,1954198446,'XWiki.LudovicDubost','internal','view',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1891847010,60375559,'XWiki.XWikiUsers','internal','save',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-611902205,-2108628346,'XWiki.XWikiUsers','internal','save',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1737199419,1423260975,'XWiki.XWikiGroups','internal','view',12,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1209763367,1954198446,'XWiki.XWikiGroups','internal','view',12,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-732463218,60375559,'XWiki.XWikiGroups','internal','save',3,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (440353811,-2108628346,'XWiki.XWikiGroups','internal','save',3,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1568976045,60375559,'XWiki.XWikiGroupTemplate','internal','save',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1896347628,-2108628346,'XWiki.XWikiGroupTemplate','internal','save',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1512173722,1423260975,'XWiki.XWikiGroupTemplate','internal','view',3,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1434789064,1954198446,'XWiki.XWikiGroupTemplate','internal','view',3,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1048789566,60375559,'XWiki.XWikiGroupSheet','internal','save',6,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1727363935,-2108628346,'XWiki.XWikiGroupSheet','internal','save',6,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1604710289,1423260975,'XWiki.XWikiGroupSheet','internal','view',6,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-256705779,1954198446,'XWiki.XWikiGroupSheet','internal','view',6,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (2058154488,60375559,'XWiki.XWiki.XWikiAdminGroup','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (477568386,60375559,'XWiki.XWiki','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-456289687,-2108628346,'XWiki.XWiki.XWikiAdminGroup','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (127689631,-2108628346,'XWiki.XWiki','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-833745115,1423260975,'XWiki.XWiki.XWikiAdminGroup','internal','view',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (641433647,1423260975,'XWiki.XWiki','internal','view',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (514259395,1954198446,'XWiki.XWiki.XWikiAdminGroup','internal','view',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1989438157,1954198446,'XWiki.XWiki','internal','view',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1167944280,1423260975,'XWiki.XWikiRights','internal','view',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1779018506,1954198446,'XWiki.XWikiRights','internal','view',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1483701739,60375559,'XWiki.XWikiRights','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1881618710,-2108628346,'XWiki.XWikiRights','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-152031883,1423260975,'XWiki.XWikiGlobalRights','internal','view',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1195972627,1954198446,'XWiki.XWikiGlobalRights','internal','view',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-622697400,60375559,'XWiki.XWikiGlobalRights','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1917797913,-2108628346,'XWiki.XWikiGlobalRights','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (546276010,1423260975,'Main.AllDocs','internal','view',6,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1894280520,1954198446,'Main.AllDocs','internal','view',4,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1800372925,60375559,'Main.AllDocs','internal','save',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-212787196,-2108628346,'Main.AllDocs','internal','save',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (2111081278,1423260975,'Main.WebRss','internal','view',13,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-835881508,1954198446,'Main.WebRss','internal','view',13,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (94740561,60375559,'Main.WebRss','internal','save',3,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (2019273712,-2108628346,'Main.WebRss','internal','save',3,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (371707109,1423260975,'Sandbox.WebHome','internal','view',11,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-689151926,1423260975,'Sandbox','internal','view',14,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1719711619,1954198446,'Sandbox.WebHome','internal','view',10,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (658852584,1954198446,'Sandbox','internal','view',13,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1252319304,60375559,'Sandbox.WebHome','internal','save',5,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1870182307,60375559,'Sandbox','internal','save',7,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1952387927,-2108628346,'Sandbox.WebHome','internal','save',5,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1034516892,-2108628346,'Sandbox','internal','save',7,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1549804326,1423260975,'Sandbox.WebPreferences','internal','view',3,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1397158460,1954198446,'Sandbox.WebPreferences','internal','view',3,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1702489543,60375559,'Sandbox.WebPreferences','internal','save',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (941618440,-2108628346,'Sandbox.WebPreferences','internal','save',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-639984414,60375559,'XWiki.RegisterNewUser','internal','save',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-834004929,-2108628346,'XWiki.RegisterNewUser','internal','save',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-554326638,60375559,'XWiki.RegisterJS','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1458669967,-2108628346,'XWiki.RegisterJS','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1127760959,1423260975,'XWiki.RegisterJS','internal','view',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1819201827,1954198446,'XWiki.RegisterJS','internal','view',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (2107054556,1423260975,'Doc.XWikiSyntax','internal','view',33,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-839908230,1954198446,'Doc.XWikiSyntax','internal','view',26,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1420345745,60375559,'Doc.XWikiSyntax','internal','save',22,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (786437138,-2108628346,'Doc.XWikiSyntax','internal','save',22,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (651725924,60375559,'Doc.TestInclude','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1173507709,-2108628346,'Doc.TestInclude','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (503103121,1423260975,'Doc.TestInclude','internal','view',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1851107631,1954198446,'Doc.TestInclude','internal','view',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1105209506,60375559,'Main.WhatsNew','internal','save',6,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-238165437,-2108628346,'Main.WhatsNew','internal','save',6,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-34713891,60375559,'Main.WebSearch','internal','save',9,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (540422116,-2108628346,'Main.WebSearch','internal','save',9,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-735626881,1423260975,'XWiki.XWikiUserSheet','internal','view',4,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (612377629,1954198446,'XWiki.XWikiUserSheet','internal','view',4,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1839262418,60375559,'XWiki.XWikiUserSheet','internal','save',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (499604559,-2108628346,'XWiki.XWikiUserSheet','internal','save',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1981846134,1423260975,'XWiki.XWikiUserTemplate','internal','view',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-633841624,1954198446,'XWiki.XWikiUserTemplate','internal','view',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1554209379,60375559,'XWiki.XWikiUserTemplate','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-2134425308,-2108628346,'XWiki.XWikiUserTemplate','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1292073821,1423260975,'Admin.WebHome','internal','view',5,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1253705410,1423260975,'Admin','internal','view',5,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1654888965,1954198446,'Admin.WebHome','internal','view',4,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1693257376,1954198446,'Admin','internal','view',4,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1908411440,60375559,'Admin.WebHome','internal','save',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1912853291,60375559,'Admin','internal','save',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1648466225,-2108628346,'Admin.WebHome','internal','save',3,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-7324948,-2108628346,'Admin','internal','save',3,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (1255800731,1423260975,'XWiki.XWikiComments','internal','view',3,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-1691162055,1954198446,'XWiki.XWikiComments','internal','view',3,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-497779215,1423260975,'XWiki.User1','internal','view',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (850225295,1954198446,'XWiki.User1','internal','view',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-354901052,60375559,'XWiki.User1','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-17274083,-2108628346,'XWiki.User1','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-844455480,1423260975,'XWiki.Admin','internal','view',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (503549030,1954198446,'XWiki.Admin','internal','view',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-2030151695,1423260975,'XWiki.XWikiAllGroup','internal','view',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-682147185,1954198446,'XWiki.XWikiAllGroup','internal','view',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1675956995,1423260975,'XWiki.XWikiSkins','internal','view',2,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (-327952485,1954198446,'XWiki.XWikiSkins','internal','view',2,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-2075930277,60375559,'XWiki.Admin','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1311917990,-2108628346,'XWiki.Admin','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-2077164092,60375559,'XWiki.XWikiAllGroup','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1643303197,-2108628346,'XWiki.XWikiAllGroup','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-2058918448,60375559,'XWiki.XWikiSkins','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1130425233,-2108628346,'XWiki.XWikiSkins','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (-1220114450,60375559,'XWiki.XWikiComments','internal','save',1,0,200408,0);
INSERT INTO xwikistatsdoc VALUES (1267945395,-2108628346,'XWiki.XWikiComments','internal','save',1,0,20040821,0);
INSERT INTO xwikistatsdoc VALUES (538584338,1954198447,'Main.WebHome','internal','view',3,0,20040822,0);
INSERT INTO xwikistatsdoc VALUES (-1510726025,1954198447,'Main','internal','view',3,0,20040822,0);
INSERT INTO xwikistatsdoc VALUES (-1441363728,1954198447,'','internal','view',4,0,20040822,3);
INSERT INTO xwikistatsdoc VALUES (1634370163,1954198447,'Doc.WebHome','internal','view',1,0,20040822,0);
INSERT INTO xwikistatsdoc VALUES (1870951640,1954198447,'Doc','internal','view',1,0,20040822,0);
INSERT INTO xwikistatsdoc VALUES (538584339,1954198448,'Main.WebHome','internal','view',1,0,20040823,0);
INSERT INTO xwikistatsdoc VALUES (-1510726024,1954198448,'Main','internal','view',1,0,20040823,0);
INSERT INTO xwikistatsdoc VALUES (-1441363727,1954198448,'','internal','view',1,0,20040823,1);
INSERT INTO xwikistatsdoc VALUES (538584340,1954198449,'Main.WebHome','internal','view',1,0,20040824,0);
INSERT INTO xwikistatsdoc VALUES (-1510726023,1954198449,'Main','internal','view',1,0,20040824,0);
INSERT INTO xwikistatsdoc VALUES (-1441363726,1954198449,'','internal','view',1,0,20040824,1);
INSERT INTO xwikistatsdoc VALUES (538584362,1954198450,'Main.WebHome','internal','view',2,0,20040825,0);
INSERT INTO xwikistatsdoc VALUES (-1510726001,1954198450,'Main','internal','view',2,0,20040825,0);
INSERT INTO xwikistatsdoc VALUES (-1441363704,1954198450,'','internal','view',2,0,20040825,2);
INSERT INTO xwikistatsdoc VALUES (538584363,1954198451,'Main.WebHome','internal','view',1,0,20040826,0);
INSERT INTO xwikistatsdoc VALUES (-1510726000,1954198451,'Main','internal','view',1,0,20040826,0);
INSERT INTO xwikistatsdoc VALUES (-1441363703,1954198451,'','internal','view',1,0,20040826,1);
INSERT INTO xwikistatsdoc VALUES (538584364,1954198452,'Main.WebHome','internal','view',2,0,20040827,0);
INSERT INTO xwikistatsdoc VALUES (-1510725999,1954198452,'Main','internal','view',2,0,20040827,0);
INSERT INTO xwikistatsdoc VALUES (-1441363702,1954198452,'','internal','view',2,0,20040827,2);
INSERT INTO xwikistatsdoc VALUES (538584365,1954198453,'Main.WebHome','internal','view',1,0,20040828,0);
INSERT INTO xwikistatsdoc VALUES (-1510725998,1954198453,'Main','internal','view',1,0,20040828,0);
INSERT INTO xwikistatsdoc VALUES (-1441363701,1954198453,'','internal','view',1,0,20040828,1);
INSERT INTO xwikistatsdoc VALUES (538584366,1954198454,'Main.WebHome','internal','view',3,0,20040829,0);
INSERT INTO xwikistatsdoc VALUES (-1510725997,1954198454,'Main','internal','view',3,0,20040829,0);
INSERT INTO xwikistatsdoc VALUES (-1441363700,1954198454,'','internal','view',3,0,20040829,3);
INSERT INTO xwikistatsdoc VALUES (538584430,1954198476,'Main.WebHome','internal','view',5,0,20040830,0);
INSERT INTO xwikistatsdoc VALUES (-1510725933,1954198476,'Main','internal','view',9,0,20040830,0);
INSERT INTO xwikistatsdoc VALUES (-1441363636,1954198476,'','internal','view',13,0,20040830,1);
INSERT INTO xwikistatsdoc VALUES (1634370255,1954198476,'Doc.WebHome','internal','view',2,0,20040830,0);
INSERT INTO xwikistatsdoc VALUES (1870951732,1954198476,'Doc','internal','view',2,0,20040830,0);
INSERT INTO xwikistatsdoc VALUES (-1654888872,1954198476,'Admin.WebHome','internal','view',1,0,20040830,0);
INSERT INTO xwikistatsdoc VALUES (-1693257283,1954198476,'Admin','internal','view',1,0,20040830,0);
INSERT INTO xwikistatsdoc VALUES (1358306822,1954198476,'Main.WhatsNew','internal','view',2,0,20040830,0);
INSERT INTO xwikistatsdoc VALUES (-687558203,1954198476,'Main.WebSearch','internal','view',2,0,20040830,0);
INSERT INTO xwikistatsdoc VALUES (1719711712,1954198476,'Sandbox.WebHome','internal','view',1,0,20040830,0);
INSERT INTO xwikistatsdoc VALUES (658852677,1954198476,'Sandbox','internal','view',1,0,20040830,0);
INSERT INTO xwikistatsdoc VALUES (1894280614,1954198477,'Main.AllDocs','internal','view',2,0,20040831,0);
INSERT INTO xwikistatsdoc VALUES (-1510725932,1954198477,'Main','internal','view',6,0,20040831,0);
INSERT INTO xwikistatsdoc VALUES (-1441363635,1954198477,'','internal','view',14,0,20040831,2);
INSERT INTO xwikistatsdoc VALUES (-839908136,1954198477,'Doc.XWikiSyntax','internal','view',5,0,20040831,0);
INSERT INTO xwikistatsdoc VALUES (1870951733,1954198477,'Doc','internal','view',8,0,20040831,0);
INSERT INTO xwikistatsdoc VALUES (538584431,1954198477,'Main.WebHome','internal','view',4,0,20040831,0);
INSERT INTO xwikistatsdoc VALUES (1634370256,1954198477,'Doc.WebHome','internal','view',3,0,20040831,0);
INSERT INTO xwikistatsdoc VALUES (1634370257,1954198478,'Doc.WebHome','internal','view',2,0,20040832,0);
INSERT INTO xwikistatsdoc VALUES (1870951734,1954198478,'Doc','internal','view',4,0,20040832,0);
INSERT INTO xwikistatsdoc VALUES (-1441363634,1954198478,'','internal','view',5,0,20040832,2);
INSERT INTO xwikistatsdoc VALUES (-839908135,1954198478,'Doc.XWikiSyntax','internal','view',2,0,20040832,0);
INSERT INTO xwikistatsdoc VALUES (538584432,1954198478,'Main.WebHome','internal','view',1,0,20040832,0);
INSERT INTO xwikistatsdoc VALUES (-1510725931,1954198478,'Main','internal','view',1,0,20040832,0);
INSERT INTO xwikistatsdoc VALUES (-809420172,1423260976,'Main.WebHome','internal','view',42,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (1436236761,1423260976,'Main','internal','view',57,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (1505599058,1423260976,'','internal','view',74,0,200409,31);
INSERT INTO xwikistatsdoc VALUES (538613167,1954199346,'Main.WebHome','internal','view',1,0,20040902,0);
INSERT INTO xwikistatsdoc VALUES (-1510697196,1954199346,'Main','internal','view',1,0,20040902,0);
INSERT INTO xwikistatsdoc VALUES (-1441334899,1954199346,'','internal','view',1,0,20040902,1);
INSERT INTO xwikistatsdoc VALUES (538613168,1954199347,'Main.WebHome','internal','view',3,0,20040903,0);
INSERT INTO xwikistatsdoc VALUES (-1510697195,1954199347,'Main','internal','view',4,0,20040903,0);
INSERT INTO xwikistatsdoc VALUES (-1441334898,1954199347,'','internal','view',5,0,20040903,3);
INSERT INTO xwikistatsdoc VALUES (286365653,1423260976,'Doc.WebHome','internal','view',3,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (522947130,1423260976,'Doc','internal','view',3,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (1634398993,1954199347,'Doc.WebHome','internal','view',1,0,20040903,0);
INSERT INTO xwikistatsdoc VALUES (1870980470,1954199347,'Doc','internal','view',1,0,20040903,0);
INSERT INTO xwikistatsdoc VALUES (546276011,1423260976,'Main.AllDocs','internal','view',2,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (1894309351,1954199347,'Main.AllDocs','internal','view',1,0,20040903,0);
INSERT INTO xwikistatsdoc VALUES (538613169,1954199348,'Main.WebHome','internal','view',1,0,20040904,0);
INSERT INTO xwikistatsdoc VALUES (-1510697194,1954199348,'Main','internal','view',1,0,20040904,0);
INSERT INTO xwikistatsdoc VALUES (-1441334897,1954199348,'','internal','view',1,0,20040904,1);
INSERT INTO xwikistatsdoc VALUES (538613170,1954199349,'Main.WebHome','internal','view',3,0,20040905,0);
INSERT INTO xwikistatsdoc VALUES (-1510697193,1954199349,'Main','internal','view',3,0,20040905,0);
INSERT INTO xwikistatsdoc VALUES (-1441334896,1954199349,'','internal','view',3,0,20040905,3);
INSERT INTO xwikistatsdoc VALUES (538613192,1954199350,'Main.WebHome','internal','view',1,0,20040906,0);
INSERT INTO xwikistatsdoc VALUES (-1510697171,1954199350,'Main','internal','view',1,0,20040906,0);
INSERT INTO xwikistatsdoc VALUES (-1441334874,1954199350,'','internal','view',1,0,20040906,1);
INSERT INTO xwikistatsdoc VALUES (538613193,1954199351,'Main.WebHome','internal','view',1,0,20040907,0);
INSERT INTO xwikistatsdoc VALUES (-1510697170,1954199351,'Main','internal','view',1,0,20040907,0);
INSERT INTO xwikistatsdoc VALUES (-1441334873,1954199351,'','internal','view',1,0,20040907,1);
INSERT INTO xwikistatsdoc VALUES (538613194,1954199352,'Main.WebHome','internal','view',2,0,20040908,0);
INSERT INTO xwikistatsdoc VALUES (-1510697169,1954199352,'Main','internal','view',2,0,20040908,0);
INSERT INTO xwikistatsdoc VALUES (-1441334872,1954199352,'','internal','view',2,0,20040908,2);
INSERT INTO xwikistatsdoc VALUES (538613195,1954199353,'Main.WebHome','internal','view',1,0,20040909,0);
INSERT INTO xwikistatsdoc VALUES (-1510697168,1954199353,'Main','internal','view',1,0,20040909,0);
INSERT INTO xwikistatsdoc VALUES (-1441334871,1954199353,'','internal','view',1,0,20040909,1);
INSERT INTO xwikistatsdoc VALUES (538613259,1954199375,'Main.WebHome','internal','view',10,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (-1510697104,1954199375,'Main','internal','view',13,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (-1441334807,1954199375,'','internal','view',18,0,20040910,5);
INSERT INTO xwikistatsdoc VALUES (-2035562805,1423260976,'Main.WebSearch','internal','view',5,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (-687529374,1954199375,'Main.WebSearch','internal','view',2,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (-1403684854,1423260976,'Demo.WebHome','internal','view',1,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (-302420113,1423260976,'Demo','internal','view',1,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (-55651423,1954199375,'Demo.WebHome','internal','view',1,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (1045613318,1954199375,'Demo','internal','view',1,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (1634399084,1954199375,'Doc.WebHome','internal','view',1,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (1870980561,1954199375,'Doc','internal','view',1,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (1894309442,1954199375,'Main.AllDocs','internal','view',1,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (1292073822,1423260976,'Admin.WebHome','internal','view',5,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (1253705411,1423260976,'Admin','internal','view',5,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (-1654860043,1954199375,'Admin.WebHome','internal','view',1,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (-1693228454,1954199375,'Admin','internal','view',1,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (-34095028,1423260976,'XWiki.XWikiUsers','internal','view',1,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (-1555372758,1423260976,'XWiki','internal','view',6,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (1313938403,1954199375,'XWiki.XWikiUsers','internal','view',1,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (-207339327,1954199375,'XWiki','internal','view',2,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (415016848,1423260976,'XWiki.RegisterNewUser','internal','view',1,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (1763050279,1954199375,'XWiki.RegisterNewUser','internal','view',1,0,20040910,0);
INSERT INTO xwikistatsdoc VALUES (538613260,1954199376,'Main.WebHome','internal','view',2,0,20040911,0);
INSERT INTO xwikistatsdoc VALUES (-1510697103,1954199376,'Main','internal','view',2,0,20040911,0);
INSERT INTO xwikistatsdoc VALUES (-1441334806,1954199376,'','internal','view',2,0,20040911,2);
INSERT INTO xwikistatsdoc VALUES (538613261,1954199377,'Main.WebHome','internal','view',3,0,20040912,0);
INSERT INTO xwikistatsdoc VALUES (-1510697102,1954199377,'Main','internal','view',7,0,20040912,0);
INSERT INTO xwikistatsdoc VALUES (-1441334805,1954199377,'','internal','view',9,0,20040912,3);
INSERT INTO xwikistatsdoc VALUES (-1654860041,1954199377,'Admin.WebHome','internal','view',1,0,20040912,0);
INSERT INTO xwikistatsdoc VALUES (-1693228452,1954199377,'Admin','internal','view',1,0,20040912,0);
INSERT INTO xwikistatsdoc VALUES (10302220,1423260976,'Main.WhatsNew','internal','view',7,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (1358335653,1954199377,'Main.WhatsNew','internal','view',2,0,20040912,0);
INSERT INTO xwikistatsdoc VALUES (-687529372,1954199377,'Main.WebSearch','internal','view',1,0,20040912,0);
INSERT INTO xwikistatsdoc VALUES (2111081279,1423260976,'Main.WebRss','internal','view',1,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (-835852584,1954199377,'Main.WebRss','internal','view',1,0,20040912,0);
INSERT INTO xwikistatsdoc VALUES (1634399086,1954199377,'Doc.WebHome','internal','view',1,0,20040912,0);
INSERT INTO xwikistatsdoc VALUES (1870980563,1954199377,'Doc','internal','view',1,0,20040912,0);
INSERT INTO xwikistatsdoc VALUES (1358335654,1954199378,'Main.WhatsNew','internal','view',2,0,20040913,0);
INSERT INTO xwikistatsdoc VALUES (-1510697101,1954199378,'Main','internal','view',4,0,20040913,0);
INSERT INTO xwikistatsdoc VALUES (-1441334804,1954199378,'','internal','view',4,0,20040913,2);
INSERT INTO xwikistatsdoc VALUES (538613262,1954199378,'Main.WebHome','internal','view',2,0,20040913,0);
INSERT INTO xwikistatsdoc VALUES (538613263,1954199379,'Main.WebHome','internal','view',3,0,20040914,0);
INSERT INTO xwikistatsdoc VALUES (-1510697100,1954199379,'Main','internal','view',3,0,20040914,0);
INSERT INTO xwikistatsdoc VALUES (-1441334803,1954199379,'','internal','view',3,0,20040914,2);
INSERT INTO xwikistatsdoc VALUES (538613285,1954199380,'Main.WebHome','internal','view',4,0,20040915,0);
INSERT INTO xwikistatsdoc VALUES (-1510697078,1954199380,'Main','internal','view',4,0,20040915,0);
INSERT INTO xwikistatsdoc VALUES (-1441334781,1954199380,'','internal','view',4,0,20040915,3);
INSERT INTO xwikistatsdoc VALUES (538613286,1954199381,'Main.WebHome','internal','view',5,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (-1510697077,1954199381,'Main','internal','view',10,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (-1441334780,1954199381,'','internal','view',19,0,20040916,1);
INSERT INTO xwikistatsdoc VALUES (1358335678,1954199381,'Main.WhatsNew','internal','view',3,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (-687529347,1954199381,'Main.WebSearch','internal','view',2,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (-1654860016,1954199381,'Admin.WebHome','internal','view',2,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (-1693228427,1954199381,'Admin','internal','view',3,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (1908411462,60375560,'Admin.WebHome','internal','save',1,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (-1912853269,60375560,'Admin','internal','save',1,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (147741754,60375560,'','internal','save',2,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (1737199420,1423260976,'XWiki.XWikiGroups','internal','view',3,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (-1209734418,1954199381,'XWiki.XWikiGroups','internal','view',3,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (-207339300,1954199381,'XWiki','internal','view',4,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (-833745114,1423260976,'XWiki.XWiki.XWikiAdminGroup','internal','view',2,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (641433648,1423260976,'XWiki.XWiki','internal','view',2,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (514288344,1954199381,'XWiki.XWiki.XWikiAdminGroup','internal','view',2,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (1989467106,1954199381,'XWiki.XWiki','internal','view',2,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (-415032952,60375560,'XWiki.XWikiAdminGroup','internal','save',1,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (640645394,60375560,'XWiki','internal','save',1,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (-2007483985,-2108627411,'XWiki.XWikiAdminGroup','internal','save',1,0,20040916,0);
INSERT INTO xwikistatsdoc VALUES (1844985888,1423260976,'XWiki.XWikiAdminGroup','internal','view',1,0,200409,0);
INSERT INTO xwikistatsdoc VALUES (-1101947950,1954199381,'XWiki.XWikiAdminGroup','internal','view',1,0,20040916,0);

--
-- Table structure for table `xwikistatsreferer`
--

CREATE TABLE xwikistatsreferer (
  XWR_ID int(11) NOT NULL default '0',
  XWR_NUMBER int(11) default NULL,
  XWR_NAME varchar(255) NOT NULL default '',
  XWR_CLASSNAME varchar(255) NOT NULL default '',
  XWR_REFERER varchar(255) NOT NULL default '',
  XWR_PAGE_VIEWS int(11) default NULL,
  XWR_PERIOD int(11) default NULL,
  PRIMARY KEY  (XWR_ID)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikistatsreferer`
--

INSERT INTO xwikistatsreferer VALUES (-1992207321,1007649701,'Main.WebHome','internal','http://www.xwiki.com/xwiki/bin/view/XWiki/CreateWikiStep2?wikiname=demo',1,200408);
INSERT INTO xwikistatsreferer VALUES (1050410125,1336057424,'Main.WebHome','internal','http://www.xwiki.com/xwiki/bin/view/Main/WikiList',2,200408);
INSERT INTO xwikistatsreferer VALUES (-1965794039,1109861032,'Main.WebHome','internal','http://www.wiki.fr/xwiki/bin/view/Main/WikiListByDocNb',1,200408);
INSERT INTO xwikistatsreferer VALUES (-1965794038,1109861033,'Main.WebHome','internal','http://www.wiki.fr/xwiki/bin/view/Main/WikiListByDocNb',1,200409);

--
-- Table structure for table `xwikistatsvisit`
--

CREATE TABLE xwikistatsvisit (
  XWV_ID int(11) NOT NULL default '0',
  XWV_NUMBER int(11) default NULL,
  XWV_NAME varchar(255) NOT NULL default '',
  XWV_CLASSNAME varchar(255) NOT NULL default '',
  XWV_IP varchar(32) NOT NULL default '',
  XWV_USER_AGENT varchar(255) NOT NULL default '',
  XWV_COOKIE varchar(255) NOT NULL default '',
  XWV_UNIQUE_ID varchar(255) NOT NULL default '',
  XWV_PAGE_VIEWS int(11) default NULL,
  XWV_PAGE_SAVES int(11) default NULL,
  XWV_DOWNLOADS int(11) default NULL,
  XWV_START_DATE datetime default NULL,
  XWV_END_DATE datetime default NULL,
  PRIMARY KEY  (XWV_ID)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikistatsvisit`
--

INSERT INTO xwikistatsvisit VALUES (-1431408462,-1834189639,'XWiki.LudovicDubost','internal','81.57.118.13','Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7) Gecko/20040707 Firefox/0.8','YTQJ9LTQNKITZKU6EOXDD7W2WNXEEOQO','YTQJ9LTQNKITZKU6EOXDD7W2WNXEEOQO',253,103,0,'2004-08-20 11:33:12','2004-08-20 13:38:52');
INSERT INTO xwikistatsvisit VALUES (-781537227,-1343248582,'xwiki:XWiki.FabriceMorisseau','internal','202.61.121.191','Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.7) Gecko/20040615 Firefox/0.8','37CG2XSAHVVCXPYDHZW9UUPBOTWMPGMV','37CG2XSAHVVCXPYDHZW9UUPBOTWMPGMV',1,0,0,'2004-08-20 16:18:15','2004-08-20 16:18:15');
INSERT INTO xwikistatsvisit VALUES (1749937363,-200213361,'XWiki.XWikiGuest','internal','64.68.82.159','Googlebot/2.1 (+http://www.google.com/bot.html)','ETG21CDYYSMXROCFDOEZUMFRTMIM4LLO','64.68.82.159Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-09-14 20:57:10','2004-09-14 20:57:10');
INSERT INTO xwikistatsvisit VALUES (1695769099,146792455,'XWiki.XWikiGuest','internal','64.68.82.169','Googlebot/2.1 (+http://www.google.com/bot.html)','XKG3GOIHLKWMXHPKE9OD2AXXN8WQYIGL','64.68.82.169Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-08-21 07:49:37','2004-08-21 07:49:37');
INSERT INTO xwikistatsvisit VALUES (1177064754,-802223052,'xwiki:XWiki.Allijeke','internal','83.194.42.249','Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7) Gecko/20040707 Firefox/0.9.2','GE5ZI1TVJY5IJ86T6XYVVCY4SGSV7PCC','GE5ZI1TVJY5IJ86T6XYVVCY4SGSV7PCC',1,0,0,'2004-08-24 11:20:40','2004-08-24 11:20:40');
INSERT INTO xwikistatsvisit VALUES (-402899160,343377379,'XWiki.XWikiGuest','internal','64.68.82.169','Googlebot/2.1 (+http://www.google.com/bot.html)','JUB5RXSOYXMPNIYBSUIIYGDMTJ3FXTM7','64.68.82.169Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-08-26 11:43:37','2004-08-26 11:43:37');
INSERT INTO xwikistatsvisit VALUES (1257073935,434469508,'XWiki.XWikiGuest','internal','64.68.82.30','Googlebot/2.1 (+http://www.google.com/bot.html)','QVPVUYWFHQSYFTPTAI2H3BH4SDBCQSJH','64.68.82.30Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-08-27 08:48:33','2004-08-27 08:48:33');
INSERT INTO xwikistatsvisit VALUES (1708172592,269640416,'XWiki.XWikiGuest','internal','64.68.82.189','Googlebot/2.1 (+http://www.google.com/bot.html)','VJ2UUQRKMVG4ONOOHPLSYPNAVSZSGV26','64.68.82.189Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-09-01 08:28:54','2004-09-01 08:28:54');
INSERT INTO xwikistatsvisit VALUES (-1267590067,261055617,'XWiki.XWikiGuest','internal','212.27.41.28','Pompos/1.3 http://dir.com/pompos.html','MGDSPHXBMKK0D63PCOXEWM9VZ4ZBXIXA','212.27.41.28Pompos/1.3 http://dir.com/pompos.html',1,0,0,'2004-08-28 11:39:16','2004-08-28 11:39:16');
INSERT INTO xwikistatsvisit VALUES (1769164463,1476516935,'XWiki.XWikiGuest','internal','212.27.41.28','Pompos/1.3 http://dir.com/pompos.html','IVBE9SXJINGDQX69B3DIHYLPFAV7AKTW','212.27.41.28Pompos/1.3 http://dir.com/pompos.html',13,0,0,'2004-08-29 03:16:02','2004-08-29 22:15:19');
INSERT INTO xwikistatsvisit VALUES (2146745611,676620875,'XWiki.XWikiGuest','internal','212.27.41.28','Pompos/1.3 http://dir.com/pompos.html','BKN4BOW5OGF5LTO5X8ZBO3B5QZE86UMB','212.27.41.28Pompos/1.3 http://dir.com/pompos.html',13,0,0,'2004-08-30 05:11:26','2004-08-30 21:17:19');
INSERT INTO xwikistatsvisit VALUES (916838725,1365409632,'XWiki.XWikiGuest','internal','64.68.82.201','Googlebot/2.1 (+http://www.google.com/bot.html)','6IACL4RG3TQR0QJSM5QNFPGUWB6EABNU','64.68.82.201Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-08-30 08:16:47','2004-08-30 08:16:47');
INSERT INTO xwikistatsvisit VALUES (1687279723,1311979107,'XWiki.XWikiGuest','internal','212.27.41.28','Pompos/1.3 http://dir.com/pompos.html','GGVW6BG8NMY0U5OXSSAOSGT7CCDJV8CJ','212.27.41.28Pompos/1.3 http://dir.com/pompos.html',4,0,0,'2004-08-31 02:34:59','2004-08-31 09:18:53');
INSERT INTO xwikistatsvisit VALUES (-240991657,1856923618,'XWiki.XWikiGuest','internal','64.68.82.197','Googlebot/2.1 (+http://www.google.com/bot.html)','A2I1B1BFDFNTEJWCYOXITUNXU66RHOMA','64.68.82.197Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-09-02 08:47:06','2004-09-02 08:47:06');
INSERT INTO xwikistatsvisit VALUES (-478652951,1623038073,'XWiki.XWikiGuest','internal','192.107.9.91','Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7) Gecko/20040803 Firefox/0.9.3','J45Y4WZCNFDDC1XKMIIVAZMNBPL2PX2J','J45Y4WZCNFDDC1XKMIIVAZMNBPL2PX2J',3,0,0,'2004-09-02 10:11:01','2004-09-02 10:11:22');
INSERT INTO xwikistatsvisit VALUES (135338252,878029985,'XWiki.XWikiGuest','internal','64.68.82.182','Googlebot/2.1 (+http://www.google.com/bot.html)','AQS62BSHQZG6IXLZQONNBS4ZGCSGUMZE','64.68.82.182Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-09-03 08:47:43','2004-09-03 08:47:43');
INSERT INTO xwikistatsvisit VALUES (-209235277,1607788288,'XWiki.XWikiGuest','internal','64.68.82.172','Googlebot/2.1 (+http://www.google.com/bot.html)','JVXMENYVGO8SHUUCES2FNNTZGYI9HHWD','64.68.82.172Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-09-07 08:31:25','2004-09-07 08:31:25');
INSERT INTO xwikistatsvisit VALUES (-1162373011,1787055260,'XWiki.XWikiGuest','internal','64.68.82.143','Googlebot/2.1 (+http://www.google.com/bot.html)','QVPHX60B1I9GEU6FPGBI9TMGCITERFFQ','64.68.82.143Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-09-05 09:15:05','2004-09-05 09:15:05');
INSERT INTO xwikistatsvisit VALUES (1392673888,-155207360,'xwiki:XWiki.LudovicDubost','internal','81.57.118.13','Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7) Gecko/20040707 Firefox/0.8','IB5UTHBKLKXRQSTMXFLNDO4PRIONHGTO','81.57.118.13Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7) Gecko/20040707 Firefox/0.8',19,2,0,'2004-09-15 01:35:11','2004-09-15 01:37:30');
INSERT INTO xwikistatsvisit VALUES (-1659640273,863375656,'XWiki.XWikiGuest','internal','64.68.82.141','Googlebot/2.1 (+http://www.google.com/bot.html)','ETBKR1BGVN7AGK4TDY0OBZMXLAP3FZAV','64.68.82.141Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-09-09 09:02:00','2004-09-09 09:02:00');
INSERT INTO xwikistatsvisit VALUES (-1783693147,257713864,'XWiki.XWikiGuest','internal','193.3.141.121','Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; Feedreader; .NET CLR 1.1.4322)','ZLX63XCY4UVYGM1BQYTPWW5FLPJDVASW','193.3.141.121Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; Feedreader; .NET CLR 1.1.4322)',2,0,0,'2004-09-09 14:26:24','2004-09-09 14:26:39');
INSERT INTO xwikistatsvisit VALUES (581992863,1718650953,'XWiki.XWikiGuest','internal','66.42.7.152','Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.6) Gecko/20040122 Debian/1.6-0.backports.org.1','5RCSLTIDBVZ9XBY2WKYMFC7KCWPCJ0VL','66.42.7.152Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.6) Gecko/20040122 Debian/1.6-0.backports.org.1',1,0,0,'2004-09-12 05:06:48','2004-09-12 05:06:48');
INSERT INTO xwikistatsvisit VALUES (-1083681390,1795417905,'XWiki.XWikiGuest','internal','64.68.82.168','Googlebot/2.1 (+http://www.google.com/bot.html)','GFTJDWKAC5OHCZ0ZAHFLOC097WBWQTFQ','64.68.82.168Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-09-10 11:59:33','2004-09-10 11:59:34');
INSERT INTO xwikistatsvisit VALUES (60879338,763061184,'XWiki.XWikiGuest','internal','64.68.82.47','Googlebot/2.1 (+http://www.google.com/bot.html)','QGY8SCZJKDNTPYARMHGCCQEEOORTAORR','64.68.82.47Googlebot/2.1 (+http://www.google.com/bot.html)',1,0,0,'2004-09-12 08:53:48','2004-09-12 08:53:48');

--
-- Table structure for table `xwikistringclasses`
--

CREATE TABLE xwikistringclasses (
  XWS_ID int(11) NOT NULL default '0',
  XWS_NAME varchar(255) NOT NULL default '',
  XWS_SIZE int(11) default NULL,
  XWS_ROWS int(11) default NULL,
  PRIMARY KEY  (XWS_ID,XWS_NAME),
  KEY XWS_ID (XWS_ID,XWS_NAME),
  KEY XWS_ID_2 (XWS_ID,XWS_NAME),
  KEY XWS_ID_3 (XWS_ID,XWS_NAME),
  KEY XWS_ID_4 (XWS_ID,XWS_NAME),
  KEY XWS_ID_5 (XWS_ID,XWS_NAME),
  KEY XWS_ID_6 (XWS_ID,XWS_NAME),
  KEY XWS_ID_7 (XWS_ID,XWS_NAME),
  KEY XWS_ID_8 (XWS_ID,XWS_NAME),
  KEY XWS_ID_9 (XWS_ID,XWS_NAME),
  KEY XWS_ID_10 (XWS_ID,XWS_NAME),
  KEY XWS_ID_11 (XWS_ID,XWS_NAME),
  KEY XWS_ID_12 (XWS_ID,XWS_NAME),
  KEY XWS_ID_13 (XWS_ID,XWS_NAME),
  KEY XWS_ID_14 (XWS_ID,XWS_NAME),
  KEY XWS_ID_15 (XWS_ID,XWS_NAME),
  KEY XWS_ID_16 (XWS_ID,XWS_NAME),
  KEY XWS_ID_17 (XWS_ID,XWS_NAME),
  KEY XWS_ID_18 (XWS_ID,XWS_NAME),
  KEY XWS_ID_19 (XWS_ID,XWS_NAME),
  KEY XWS_ID_20 (XWS_ID,XWS_NAME),
  KEY XWS_ID_21 (XWS_ID,XWS_NAME),
  KEY XWS_ID_22 (XWS_ID,XWS_NAME),
  KEY XWS_ID_23 (XWS_ID,XWS_NAME),
  KEY XWS_ID_24 (XWS_ID,XWS_NAME),
  KEY XWS_ID_25 (XWS_ID,XWS_NAME),
  KEY XWS_ID_26 (XWS_ID,XWS_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikistringclasses`
--

INSERT INTO xwikistringclasses VALUES (104408758,'admin_email',30,NULL);
INSERT INTO xwikistringclasses VALUES (104408758,'confirmation_email_content',72,10);
INSERT INTO xwikistringclasses VALUES (104408758,'skin',20,NULL);
INSERT INTO xwikistringclasses VALUES (104408758,'title',30,NULL);
INSERT INTO xwikistringclasses VALUES (104408758,'language',30,NULL);
INSERT INTO xwikistringclasses VALUES (104408758,'meta',40,5);
INSERT INTO xwikistringclasses VALUES (104408758,'version',30,NULL);
INSERT INTO xwikistringclasses VALUES (104408758,'webbgcolor',10,NULL);
INSERT INTO xwikistringclasses VALUES (104408758,'webcopyright',60,NULL);
INSERT INTO xwikistringclasses VALUES (104408758,'smtp_server',30,NULL);
INSERT INTO xwikistringclasses VALUES (104408758,'plugins',40,NULL);
INSERT INTO xwikistringclasses VALUES (104408758,'validation_email_content',72,10);
INSERT INTO xwikistringclasses VALUES (104408758,'menu',60,8);
INSERT INTO xwikistringclasses VALUES (104408758,'authenticate_edit',30,NULL);
INSERT INTO xwikistringclasses VALUES (495778886,'password',10,NULL);
INSERT INTO xwikistringclasses VALUES (495778886,'email',30,NULL);
INSERT INTO xwikistringclasses VALUES (495778886,'comment',40,5);
INSERT INTO xwikistringclasses VALUES (495778886,'default_language',5,NULL);
INSERT INTO xwikistringclasses VALUES (495778886,'validkey',10,NULL);
INSERT INTO xwikistringclasses VALUES (495778886,'last_name',30,NULL);
INSERT INTO xwikistringclasses VALUES (495778886,'fullname',30,NULL);
INSERT INTO xwikistringclasses VALUES (495778886,'first_name',30,NULL);
INSERT INTO xwikistringclasses VALUES (2082812758,'member',30,NULL);
INSERT INTO xwikistringclasses VALUES (-1905796263,'groups',80,NULL);
INSERT INTO xwikistringclasses VALUES (-1905796263,'users',80,NULL);
INSERT INTO xwikistringclasses VALUES (-1905796263,'levels',80,NULL);
INSERT INTO xwikistringclasses VALUES (981637980,'groups',80,NULL);
INSERT INTO xwikistringclasses VALUES (981637980,'users',80,NULL);
INSERT INTO xwikistringclasses VALUES (981637980,'levels',80,NULL);
INSERT INTO xwikistringclasses VALUES (-980492554,'highlight',40,2);
INSERT INTO xwikistringclasses VALUES (-980492554,'comment',40,5);
INSERT INTO xwikistringclasses VALUES (-980492554,'author',30,NULL);
INSERT INTO xwikistringclasses VALUES (493697236,'header.vm',80,15);
INSERT INTO xwikistringclasses VALUES (493697236,'footer.vm',80,15);
INSERT INTO xwikistringclasses VALUES (493697236,'name',30,NULL);
INSERT INTO xwikistringclasses VALUES (493697236,'style.css',80,15);

--
-- Table structure for table `xwikistrings`
--

CREATE TABLE xwikistrings (
  XWS_ID int(11) NOT NULL default '0',
  XWS_NAME varchar(255) NOT NULL default '',
  XWS_VALUE varchar(255) default NULL,
  PRIMARY KEY  (XWS_ID,XWS_NAME),
  KEY XWS_ID (XWS_ID,XWS_NAME),
  KEY XWS_ID_2 (XWS_ID,XWS_NAME),
  KEY XWS_ID_3 (XWS_ID,XWS_NAME),
  KEY XWS_ID_4 (XWS_ID,XWS_NAME),
  KEY XWS_ID_5 (XWS_ID,XWS_NAME),
  KEY XWS_ID_6 (XWS_ID,XWS_NAME),
  KEY XWS_ID_7 (XWS_ID,XWS_NAME),
  KEY XWS_ID_8 (XWS_ID,XWS_NAME),
  KEY XWS_ID_9 (XWS_ID,XWS_NAME),
  KEY XWS_ID_10 (XWS_ID,XWS_NAME),
  KEY XWS_ID_11 (XWS_ID,XWS_NAME),
  KEY XWS_ID_12 (XWS_ID,XWS_NAME),
  KEY XWS_ID_13 (XWS_ID,XWS_NAME),
  KEY XWS_ID_14 (XWS_ID,XWS_NAME),
  KEY XWS_ID_15 (XWS_ID,XWS_NAME),
  KEY XWS_ID_16 (XWS_ID,XWS_NAME),
  KEY XWS_ID_17 (XWS_ID,XWS_NAME),
  KEY XWS_ID_18 (XWS_ID,XWS_NAME),
  KEY XWS_ID_19 (XWS_ID,XWS_NAME),
  KEY XWS_ID_20 (XWS_ID,XWS_NAME),
  KEY XWS_ID_21 (XWS_ID,XWS_NAME),
  KEY XWS_ID_22 (XWS_ID,XWS_NAME),
  KEY XWS_ID_23 (XWS_ID,XWS_NAME),
  KEY XWS_ID_24 (XWS_ID,XWS_NAME),
  KEY XWS_ID_25 (XWS_ID,XWS_NAME),
  KEY XWS_ID_26 (XWS_ID,XWS_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `xwikistrings`
--

INSERT INTO xwikistrings VALUES (-944455444,'admin_email','webmaster@xwiki.com');
INSERT INTO xwikistrings VALUES (-944455444,'skin','default');
INSERT INTO xwikistrings VALUES (-944455444,'title','Wiki - $doc.web - $doc.name');
INSERT INTO xwikistrings VALUES (-944455444,'language','fr');
INSERT INTO xwikistrings VALUES (-944455444,'version','');
INSERT INTO xwikistrings VALUES (-944455444,'webcopyright','Copyright 2004 (c) Auteurs des pages');
INSERT INTO xwikistrings VALUES (-944455444,'webbgcolor','');
INSERT INTO xwikistrings VALUES (-944455444,'smtp_server','127.0.0.1');
INSERT INTO xwikistrings VALUES (-944455444,'plugins','');
INSERT INTO xwikistrings VALUES (-944455444,'authenticate_edit','yes');
INSERT INTO xwikistrings VALUES (-1383579234,'admin_email','');
INSERT INTO xwikistrings VALUES (-1383579234,'skin','default');
INSERT INTO xwikistrings VALUES (-1383579234,'title','Wiki - $doc.web - $doc.name');
INSERT INTO xwikistrings VALUES (-1383579234,'language','en');
INSERT INTO xwikistrings VALUES (-1383579234,'version','');
INSERT INTO xwikistrings VALUES (-1383579234,'webcopyright','Copyright (c) 2004 Contributing Authors');
INSERT INTO xwikistrings VALUES (-1383579234,'webbgcolor','');
INSERT INTO xwikistrings VALUES (-1383579234,'smtp_server','');
INSERT INTO xwikistrings VALUES (-1383579234,'plugins','');
INSERT INTO xwikistrings VALUES (-1383579234,'authenticate_edit','yes');
INSERT INTO xwikistrings VALUES (538939758,'groups','');
INSERT INTO xwikistrings VALUES (538939758,'users','Main.XWikiGuest');
INSERT INTO xwikistrings VALUES (538939758,'levels','edit');
INSERT INTO xwikistrings VALUES (-1531382335,'skin','');
INSERT INTO xwikistrings VALUES (-1531382335,'webcopyright','');
INSERT INTO xwikistrings VALUES (-1531382335,'webbgcolor','');
INSERT INTO xwikistrings VALUES (-1531382335,'title','');
INSERT INTO xwikistrings VALUES (-1531382335,'plugins','');
INSERT INTO xwikistrings VALUES (-1531382335,'authenticate_edit','');
INSERT INTO xwikistrings VALUES (134878161,'member','');
INSERT INTO xwikistrings VALUES (-790920225,'member','');
INSERT INTO xwikistrings VALUES (-1468381210,'users','XWiki.Admin');
INSERT INTO xwikistrings VALUES (-1468381210,'levels','admin, edit');
INSERT INTO xwikistrings VALUES (165499024,'groups','XWiki.XWikiAdminGroup');
INSERT INTO xwikistrings VALUES (165499024,'levels','view, edit');
INSERT INTO xwikistrings VALUES (130775106,'users','XWiki.Admin');
INSERT INTO xwikistrings VALUES (130775106,'levels','view');
INSERT INTO xwikistrings VALUES (562594863,'password','admin');
INSERT INTO xwikistrings VALUES (562594863,'email','');
INSERT INTO xwikistrings VALUES (562594863,'last_name','Admin');
INSERT INTO xwikistrings VALUES (562594863,'first_name','Admin');
INSERT INTO xwikistrings VALUES (-142093066,'member','XWiki.Admin');
INSERT INTO xwikistrings VALUES (1886528249,'groups','XWiki.XWikiAdminGroup');
INSERT INTO xwikistrings VALUES (1886528249,'levels','view, edit');
INSERT INTO xwikistrings VALUES (477451371,'users','XWiki.User1');
INSERT INTO xwikistrings VALUES (477451371,'levels','view');
INSERT INTO xwikistrings VALUES (-213172058,'password','user1');
INSERT INTO xwikistrings VALUES (-213172058,'email','');
INSERT INTO xwikistrings VALUES (-213172058,'last_name','');
INSERT INTO xwikistrings VALUES (-213172058,'first_name','User1');
INSERT INTO xwikistrings VALUES (887520040,'member','XWiki.User1');
INSERT INTO xwikistrings VALUES (-1468381210,'groups','XWiki.AdminGroup');
INSERT INTO xwikistrings VALUES (1520038052,'member','XWiki.Admin');
INSERT INTO xwikistrings VALUES (467690326,'member','');

