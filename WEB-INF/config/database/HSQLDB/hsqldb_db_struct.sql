-- HSQL dump 1.0
-- Author: Franklin Samir (franklin@portaljava.com) based on MySQL dump file.
-- Obs.: All int variants like mediumint and smallint from MySql were converted to int.
---------------------------------------------------------
--
-- Table structure for table 'jforum_banlist' - VERIFIED
--
DROP TABLE jforum_banlist IF EXISTS;
CREATE TABLE jforum_banlist (
  banlist_id int(8) NOT NULL IDENTITY,
  user_id int(8) default '0' NOT NULL ,
  banlist_ip varchar(8) default ' ' NOT NULL ,
  banlist_email varchar(255) default 'NULL'  NULL
);
CREATE INDEX idx_user ON jforum_banlist (user_id);

--
-- Table structure for table 'jforum_categories' - VERIFIED
--
DROP TABLE jforum_categories IF EXISTS;
CREATE TABLE jforum_categories (
  categories_id int(8) NOT NULL IDENTITY,
  title varchar(100) default '' NOT NULL,
  display_order int(8) default '0' NOT NULL  
);

--
-- Table structure for table 'jforum_config' - VERIFIED
--
DROP TABLE jforum_config IF EXISTS;
CREATE TABLE jforum_config (
  config_name varchar(255) default '' NOT NULL,
  config_value varchar(255) default '' NOT NULL,
  config_id int NOT NULL IDENTITY
);

--
-- Table structure for table 'jforum_forums' - VERIFIED
--
DROP TABLE jforum_forums IF EXISTS;
CREATE TABLE jforum_forums (
  forum_id int(5) NOT NULL IDENTITY,
  categories_id int(8) default '1' NOT NULL,
  forum_name varchar(150) default '' NOT NULL,
  forum_desc varchar(255) default 'NULL' NULL,
  forum_order int(8) default '1' NOT NULL,
  forum_topics int(8) default '0' NOT NULL,
  forum_last_post_id int(8) default '0' NOT NULL,
  moderated int(6) default '0'  
);
CREATE INDEX idx_forums_cat_id ON jforum_forums (categories_id);

--
-- Table structure for table 'jforum_groups' - VERIFIED
--
DROP TABLE jforum_groups IF EXISTS;
CREATE TABLE jforum_groups (
  group_id int(8) NOT NULL IDENTITY,
  group_name varchar(40) default '' NOT NULL,
  group_description varchar(255) default 'NULL' NULL,
  parent_id int(8) default '0' NULL
) ;

--
-- Table structure for table 'jforum_user_groups' - VERIFIED
--
DROP TABLE jforum_user_groups IF EXISTS;
CREATE TABLE jforum_user_groups (
	group_id INT NOT NULL,
	user_id INT NOT NULL	
) ;
CREATE INDEX idx_user_grps ON jforum_user_groups (group_id, user_id) ;

--
-- Table structure for table 'jforum_roles' - VERIFIED
--
DROP TABLE jforum_roles IF EXISTS;
CREATE TABLE jforum_roles (
  role_id INT NOT NULL IDENTITY,
  group_id int(8) default '0' NULL,
  user_id int(8) default '0' NULL,
  name varchar(255) NOT NULL,
  type TINYINT(1) DEFAULT '1' NOT NULL  
) ;
CREATE INDEX idx_roles ON jforum_roles(group_id,user_id,name) ;

--
-- Table structure for table 'jforum_role_values' - VERIFIED
--
DROP TABLE jforum_role_values IF EXISTS;
CREATE TABLE jforum_role_values (
  role_id INT NOT NULL,
  value VARCHAR(255),
  type TINYINT(1) DEFAULT '1' NOT NULL
) ;
CREATE INDEX idx_role_values ON jforum_role_values(role_id) ;

--
-- Table structure for table 'jforum_posts' - VERIFIED
--
DROP TABLE jforum_posts IF EXISTS;
CREATE TABLE jforum_posts (
  post_id int(8) NOT NULL IDENTITY,
  topic_id int(8) default '0' NOT NULL,
  forum_id int(5) default '0' NOT NULL,
  user_id int(8) default '-1' NULL,
  post_time varchar(13) default 'NULL' NULL,
  poster_ip varchar(15) default 'NULL NULL',
  enable_bbcode tinyint(1) default '1' NOT NULL,
  enable_html tinyint(1) default '1' NOT NULL,
  enable_smilies tinyint(1) default '1' NOT NULL,
  enable_sig tinyint(1) default '1' NOT NULL,
  post_edit_time varchar(13) default 'NULL' NULL,
  post_edit_count int(5) default '0' NOT NULL,
  status tinyint(1) default '1' NOT NULL
) ;
CREATE INDEX idx_posts ON jforum_posts(user_id,topic_id,forum_id);

--
-- Table structure for table 'jforum_posts_text'
--
DROP TABLE jforum_posts_text IF EXISTS;
CREATE TABLE jforum_posts_text (
	post_id int(8) NOT NULL IDENTITY,
	post_text LONGVARCHAR,
	post_subject VARCHAR(100) DEFAULT 'NULL'
);

--
-- Table structure for table 'jforum_privmsgs' - VERIFIED
--

DROP TABLE jforum_privmsgs IF EXISTS;
CREATE TABLE jforum_privmsgs (
  privmsgs_id int(8) NOT NULL IDENTITY,
  privmsgs_type tinyint(4) default '0' NOT NULL,
  privmsgs_subject varchar(255) default '' NOT NULL,
  privmsgs_from_userid int(8) default '0' NOT NULL,
  privmsgs_to_userid int(8) default '0' NOT NULL,
  privmsgs_date varchar(13) default '0' NOT NULL,
  privmsgs_ip varchar(8) default '' NOT NULL,
  privmsgs_enable_bbcode tinyint(1) default '1' NOT NULL,
  privmsgs_enable_html tinyint(1) default '0' NOT NULL,
  privmsgs_enable_smilies tinyint(1) default '1' NOT NULL,
  privmsgs_attach_sig tinyint(1) default '1' NOT NULL
) ;

DROP TABLE jforum_privmsgs_text IF EXISTS;
CREATE TABLE jforum_privmsgs_text (
	privmsgs_id INT(8) NOT NULL IDENTITY,
	privmsgs_text TEXT
);

--
-- Table structure for table 'jforum_ranks' - VERIFIED
--
DROP TABLE jforum_ranks IF EXISTS;
CREATE TABLE jforum_ranks (
  rank_id int(5) NOT NULL IDENTITY,
  rank_title varchar(50) default '' NOT NULL,
  rank_min int(8) default '0' NOT NULL,
  rank_special tinyint(1) default '-1' NULL,
  rank_image varchar(255) default 'NULL' NULL
) ;

--
-- Table structure for table 'jforum_sessions' - VERIFIED
--
DROP TABLE jforum_sessions IF EXISTS;
CREATE TABLE jforum_sessions (
  session_id varchar(50) default '' NOT NULL,
  session_user_id int(8) default '0' NOT NULL,
  session_start varchar(13) default '0' NOT NULL,
  session_time varchar(13) default '0' NOT NULL,
  session_ip varchar(8) default '' NOT NULL,
  session_page int(11) default '0' NOT NULL,
  session_logged_int tinyint(1) default '-1'
) ;

--
-- Table structure for table 'jforum_smilies' - VERIFIED
--
DROP TABLE jforum_smilies IF EXISTS;
CREATE TABLE jforum_smilies (
  smilie_id int(5) NOT NULL IDENTITY,
  code varchar(50) default '' NOT NULL,
  url varchar(100) default 'NULL' NULL,
  disk_name varchar(255)
) ;

--
-- Table structure for table 'jforum_themes'- VERIFIED
--
DROP TABLE jforum_themes IF EXISTS;
CREATE TABLE jforum_themes (
  themes_id int(8) NOT NULL IDENTITY,
  template_name varchar(30) default '' NOT NULL,
  style_name varchar(30) default '' NOT NULL
);

--
-- Table structure for table 'jforum_topics' - VERIFIED
--
DROP TABLE jforum_topics IF EXISTS;
CREATE TABLE jforum_topics (
  topic_id int(8) NOT NULL IDENTITY,
  forum_id int(8) default '0' NOT NULL,
  topic_title varchar(100) default '' NOT NULL,
  user_id int(8) default '0' NOT NULL,
  topic_time varchar(13) default '0' NOT NULL,
  topic_views int(8) default '1' NOT NULL,
  topic_replies int(8) default '0',
  topic_status tinyint(3) default '0',
  topic_vote tinyint(1) default '0',
  topic_type tinyint(3) default '0',
  topic_first_post_id int(8) default '0',
  topic_last_post_id int(8) default '0' NOT NULL,
  moderated int(1) default '0'
) ;
CREATE INDEX idx_topics ON jforum_topics(forum_id,user_id,topic_first_post_id,topic_last_post_id) ;
  
--
-- Table structure for table 'jforum_topics_watch' - VERIFIED
--
DROP TABLE jforum_topics_watch IF EXISTS;
CREATE TABLE jforum_topics_watch (
  topic_id int(8) default '0' NOT NULL,
  user_id int(8) default '0' NOT NULL,
  is_read tinyint(1) default '0' NOT NULL
) ;
CREATE INDEX idx_topics_watch ON jforum_topics_watch(topic_id,user_id);

--
-- Table structure for table 'jforum_users' - VERIFIED
--
DROP TABLE jforum_users IF EXISTS;
CREATE TABLE jforum_users (
  user_id int(8) NOT NULL IDENTITY,
  user_active tinyint(1) default '-1' NULL,
  username varchar(50) default '' NOT NULL,
  user_password varchar(32) default '' NOT NULL,
  user_session_time varchar(13) default '0' NOT NULL,
  user_session_page int(5) default '0' NOT NULL,
  user_lastvisit int(11) default '0' NOT NULL,
  user_regdate varchar(13) default '0' NOT NULL,
  user_level tinyint(4) default '-1' NULL,
  user_posts int(8) default '0' NOT NULL,
  user_timezone varchar(5) default '' NOT NULL,
  user_style tinyint(4) default '0' NULL,
  user_lang varchar(255) default 'NULL' NULL,
  user_dateformat varchar(20) default '%d/%M/%Y %H:%i' NOT NULL ,
  user_new_privmsg int(5) default '0' NOT NULL,
  user_unread_privmsg int(5) default '0' NOT NULL,
  user_last_privmsg int(11) default '0' NOT NULL,
  user_emailtime int(11) default '0' NULL,
  user_viewemail tinyint(1) default '0',
  user_attachsig tinyint(1) default '1' NOT NULL,
  user_allowhtml tinyint(1) default '0',
  user_allowbbcode tinyint(1) default '1' NOT NULL,
  user_allowsmilies tinyint(1) default '1' NOT NULL,
  user_allowavatar tinyint(1) default '1' NOT NULL,
  user_allow_pm tinyint(1) default '1' NOT NULL,
  user_allow_viewonline tinyint(1) default '1' NOT NULL,
  user_notify tinyint(1) default '1' NOT NULL,
  user_notify_pm tinyint(1) default '1' NOT NULL,
  user_popup_pm tinyint(1) default '1' NOT NULL,
  rank_id int(5) default '1' NOT NULL,
  user_avatar varchar(100) default 'NULL' NULL,
  user_avatar_type tinyint(4) default '0' NOT NULL,
  user_email varchar(255) default '' NOT NULL,
  user_icq varchar(15) default 'NULL' NULL,
  user_website varchar(100) default 'NULL' NULL,
  user_from varchar(100) default 'NULL' NULL,
  user_sig LONGVARCHAR NULL,
  user_sig_bbcode_uid varchar(10) default 'NULL' NULL,
  user_aim varchar(255) default 'NULL' NULL,
  user_yim varchar(255) default 'NULL' NULL,
  user_msnm varchar(255) default 'NULL' NULL,
  user_occ varchar(100) default 'NULL' NULL,
  user_interests varchar(255) default 'NULL' NULL,
  user_actkey varchar(32) default 'NULL' NULL,
  gender char(1) default '' NULL,
  themes_id int(8) default '0' NULL,
  deleted tinyint(1) default '0' NULL,
  user_viewonline tinyint(1) default '1' NOT NULL,
  security_hash varchar(32)
) ;

--
-- Table structure for table 'jforum_vote_desc' - VERIFIED
--
DROP TABLE jforum_vote_desc IF EXISTS;
CREATE TABLE jforum_vote_desc (
  vote_id int(8) NOT NULL IDENTITY,
  topic_id int(8) default '0' NOT NULL,
  vote_text LONGVARCHAR NOT NULL,
  vote_start int(11) default '0' NOT NULL,
  vote_length int(11) default '0' NOT NULL  
) ;

--
-- Table structure for table 'jforum_vote_results'- VERIFIED
--
DROP TABLE jforum_vote_results IF EXISTS;
CREATE TABLE jforum_vote_results (
  vote_id int(8) default '0' NOT NULL,
  vote_option_id tinyint(4) default '0' NOT NULL,
  vote_option_text varchar(255) default '' NOT NULL,
  vote_result int(11) default '0' NOT NULL
) ;

--
-- Table structure for table 'jforum_vote_voters'- VERIFIED
--
DROP TABLE jforum_vote_voters IF EXISTS;
CREATE TABLE jforum_vote_voters (
  vote_id int(8) default '0' NOT NULL,
  vote_user_id int(8) default '0' NOT NULL,
  vote_user_ip char(8) default '' NOT NULL
) ;

--
-- Table structure for table 'jforum_words' - VERIFIED
--
DROP TABLE jforum_words IF EXISTS;
CREATE TABLE jforum_words (
  word_id int(8) NOT NULL IDENTITY,
  word varchar(100) default '' NOT NULL,
  replacement varchar(100) default '' NOT NULL
) ;

--
-- Table structure for table 'jforum_search_words'- VERIFIED
--
DROP TABLE jforum_search_words IF EXISTS 
CREATE TABLE jforum_search_words (
  word_id INT NOT NULL IDENTITY,
  word VARCHAR(100) NOT NULL,
  word_hash INT  
);
CREATE INDEX idx_srch_wrds ON jforum_search_words (word,word_hash)

-- 
-- Table structure for table 'jforum_search_wordmatch'- VERIFIED
--
DROP TABLE jforum_search_wordmatch IF EXISTS ;
CREATE TABLE jforum_search_wordmatch (
  post_id INT NOT NULL,
  word_id INT NOT NULL,
  title_match TINYINT(1) DEFAULT '0'   
);
CREATE INDEX idx_srch_wrdmtch ON jforum_search_wordmatch (post_id,word_id,title_match);

--
-- Table structure for table 'jforum_search_results'- VERIFIED
--
DROP TABLE jforum_search_results IF EXISTS;
CREATE TABLE jforum_search_results (
  topic_id INT NOT NULL,
  session VARCHAR(50),
  time DATETIME
);
CREATE INDEX idx_srch_results ON jforum_search_results(topic_id);

--
-- Table structure for table 'jforum_search_topics' - VERIFIED
-- 
DROP TABLE jforum_search_topics IF EXISTS ;
CREATE TABLE jforum_search_topics (
  topic_id int(8) NOT NULL,
  forum_id int(8) default '0' NOT NULL,
  topic_title varchar(60) default '' NOT NULL,
  user_id int(8) default '0' NOT NULL,
  topic_time varchar(13) default '0' NOT NULL,
  topic_views int(8) default '1' NOT NULL,
  topic_replies int(8) default '0',
  topic_status tinyint(3) default '0',
  topic_vote tinyint(1) default '0',
  topic_type tinyint(3) default '0',
  topic_first_post_id int(8) default '0',
  topic_last_post_id int(8) default '0' NOT NULL,
  moderated int(1) default '0',
  session varchar(50),
  time datetime  
) ;
CREATE INDEX idx_srch_topics ON jforum_search_topics (topic_id,forum_id,user_id,topic_first_post_id,topic_last_post_id);