CREATE SEQUENCE jforum_banlist_seq;
CREATE TABLE jforum_banlist (
  banlist_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_banlist_seq'),
  user_id INTEGER NOT NULL DEFAULT 0,
  banlist_ip VARCHAR(20) NOT NULL DEFAULT '',
  banlist_email VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (banlist_id)
);
CREATE INDEX idx_banlist_user ON jforum_banlist(user_id);

--
-- Table structure for table 'jforum_categories'
--

CREATE SEQUENCE jforum_categories_seq;
CREATE SEQUENCE jforum_categories_order_seq;
CREATE TABLE jforum_categories (
  categories_id INTEGER NOT NULL PRIMARY KEY DEFAULT NEXTVAL('jforum_categories_seq'),
  title VARCHAR(100) NOT NULL DEFAULT '',
  display_order INTEGER NOT NULL DEFAULT 0
);

--
-- Table structure for table 'jforum_config'
--

CREATE SEQUENCE jforum_config_seq;
CREATE TABLE jforum_config (
  config_name VARCHAR(255) NOT NULL DEFAULT '',
  config_value VARCHAR(255) NOT NULL DEFAULT '',
  config_id int NOT NULL PRIMARY KEY DEFAULT nextval('jforum_config_seq')
);

--
-- Table structure for table 'jforum_forums'
--

CREATE SEQUENCE jforum_forums_seq;
CREATE TABLE jforum_forums (
  forum_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_forums_seq'),
  categories_id INTEGER NOT NULL DEFAULT 1,
  forum_name VARCHAR(150) NOT NULL DEFAULT '',
  forum_desc VARCHAR(255) DEFAULT NULL,
  forum_order INTEGER DEFAULT 1,
  forum_topics INTEGER NOT NULL DEFAULT 0,
  forum_last_post_id INTEGER NOT NULL DEFAULT 0,
  moderated INTEGER DEFAULT 0,
  PRIMARY KEY  (forum_id)
);
CREATE INDEX idx_forums_categories_id ON jforum_forums(categories_id);

--
-- Table structure for table 'jforum_groups'
--

CREATE SEQUENCE jforum_groups_seq;
CREATE TABLE jforum_groups (
  group_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_groups_seq'),
  group_name VARCHAR(40) NOT NULL DEFAULT '',
  group_description VARCHAR(255) DEFAULT NULL,
  parent_id INTEGER DEFAULT 0,
  PRIMARY KEY  (group_id)
);


CREATE TABLE jforum_user_groups (
	group_id INT NOT NULL,
	user_id INT NOT NULL
);
CREATE INDEX idx_ug_group ON jforum_user_groups(group_id);
CREATE INDEX idx_ug_user ON jforum_user_groups(user_id);

--
-- Table structure for table 'jforum_roles'
--

CREATE SEQUENCE jforum_roles_seq;
CREATE TABLE jforum_roles (
  role_id INT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('jforum_roles_seq'),
  group_id INTEGER DEFAULT 0,
  user_id INTEGER DEFAULT 0,
  name VARCHAR(255) NOT NULL,
  type INTEGER DEFAULT 1
);
CREATE INDEX idx_roles_group ON jforum_roles(group_id);
CREATE INDEX idx_roles_user ON jforum_roles(user_id);
CREATE INDEX idx_roles_name ON jforum_roles(name);

--
-- Table structure for table 'jforum_role_values'
--
CREATE TABLE jforum_role_values (
  role_id INT NOT NULL,
  value VARCHAR(255),
  type INTEGER DEFAULT 1
);
CREATE INDEX idx_rv_role ON jforum_role_values(role_id);

--
-- Table structure for table 'jforum_posts'
--

CREATE SEQUENCE jforum_posts_seq;
CREATE TABLE jforum_posts (
  post_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_posts_seq'),
  topic_id INTEGER NOT NULL DEFAULT 0,
  forum_id INTEGER NOT NULL DEFAULT 0,
  user_id INTEGER DEFAULT NULL,
  post_time timestamp DEFAULT NULL,
  poster_ip VARCHAR(15) DEFAULT NULL,
  enable_bbcode INTEGER NOT NULL DEFAULT 1,
  enable_html INTEGER NOT NULL DEFAULT 1,
  enable_smilies INTEGER NOT NULL DEFAULT 1,
  enable_sig INTEGER NOT NULL DEFAULT 1,
  post_edit_time timestamp DEFAULT NULL,
  post_edit_count INTEGER NOT NULL DEFAULT 0,
  status INTEGER DEFAULT 1,
  PRIMARY KEY  (post_id)
);
CREATE INDEX idx_posts_user ON jforum_posts(user_id);
CREATE INDEX idx_posts_topic ON jforum_posts(topic_id);
CREATE INDEX idx_posts_forum ON jforum_posts(forum_id);

--
-- Table structure for table 'jforum_posts_text'
--
CREATE TABLE jforum_posts_text (
	post_id INTEGER NOT NULL,
	post_text TEXT,
	post_subject VARCHAR(100) DEFAULT NULL,
	PRIMARY KEY ( post_id )
);

--
-- Table structure for table 'jforum_privmsgs'
--

CREATE SEQUENCE jforum_privmsgs_seq;
CREATE TABLE jforum_privmsgs (
  privmsgs_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_privmsgs_seq'),
  privmsgs_type INTEGER NOT NULL DEFAULT 0,
  privmsgs_subject VARCHAR(255) NOT NULL DEFAULT '',
  privmsgs_from_userid INTEGER NOT NULL DEFAULT 0,
  privmsgs_to_userid INTEGER NOT NULL DEFAULT 0,
  privmsgs_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  privmsgs_ip VARCHAR(8) NOT NULL DEFAULT '',
  privmsgs_enable_bbcode INTEGER NOT NULL DEFAULT 1,
  privmsgs_enable_html INTEGER NOT NULL DEFAULT 0,
  privmsgs_enable_smilies INTEGER NOT NULL DEFAULT 1,
  privmsgs_attach_sig INTEGER NOT NULL DEFAULT 1,
  PRIMARY KEY  (privmsgs_id)
);

CREATE TABLE jforum_privmsgs_text (
	privmsgs_id INTEGER NOT NULL,
	privmsgs_text TEXT
);
CREATE INDEX idx_pm_text_id ON jforum_privmsgs_text (privmsgs_id);

--
-- Table structure for table 'jforum_ranks'
--

CREATE SEQUENCE jforum_ranks_seq;
CREATE TABLE jforum_ranks (
  rank_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_ranks_seq'),
  rank_title VARCHAR(50) NOT NULL DEFAULT '',
  rank_min INTEGER NOT NULL DEFAULT 0,
  rank_special INTEGER DEFAULT NULL,
  rank_image VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (rank_id)
);

--
-- Table structure for table 'jforum_sessions'
--

CREATE TABLE jforum_sessions (
  session_id VARCHAR(50) NOT NULL DEFAULT '',
  session_user_id INTEGER NOT NULL DEFAULT 0,
  session_start timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  session_time int NOT NULL DEFAULT 0,
  session_ip VARCHAR(8) NOT NULL DEFAULT '',
  session_page INTEGER NOT NULL DEFAULT 0,
  session_logged_int INTEGER DEFAULT NULL
);

--
-- Table structure for table 'jforum_smilies'
--

CREATE SEQUENCE jforum_smilies_seq;
CREATE TABLE jforum_smilies (
  smilie_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_smilies_seq'),
  code VARCHAR(50) NOT NULL DEFAULT '',
  url VARCHAR(100) DEFAULT NULL,
  disk_name VARCHAR(255),
  PRIMARY KEY  (smilie_id)
);

--
-- Table structure for table 'jforum_themes'
--

CREATE SEQUENCE jforum_themes_seq;
CREATE TABLE jforum_themes (
  themes_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_themes_seq'),
  template_name VARCHAR(30) NOT NULL DEFAULT '',
  style_name VARCHAR(30) NOT NULL DEFAULT '',
  PRIMARY KEY  (themes_id)
);

--
-- Table structure for table 'jforum_topics'
--

CREATE SEQUENCE jforum_topics_seq;
CREATE TABLE jforum_topics (
  topic_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_topics_seq'),
  forum_id INTEGER NOT NULL DEFAULT 0,
  topic_title VARCHAR(100) NOT NULL DEFAULT '',
  user_id INTEGER NOT NULL DEFAULT 0,
  topic_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  topic_views INTEGER DEFAULT 1,
  topic_replies INTEGER DEFAULT 0,
  topic_status INTEGER DEFAULT 0,
  topic_vote INTEGER DEFAULT 0,
  topic_type INTEGER DEFAULT 0,
  topic_first_post_id INTEGER DEFAULT 0,
  topic_last_post_id INTEGER NOT NULL DEFAULT 0,
  moderated INTEGER DEFAULT 0,
  PRIMARY KEY  (topic_id)
);
CREATE INDEX idx_topics_forum ON jforum_topics(forum_id);
CREATE INDEX idx_topics_user ON jforum_topics(user_id);
CREATE INDEX idx_topics_fp ON jforum_topics(topic_first_post_id);
CREATE INDEX idx_topics_lp ON jforum_topics(topic_last_post_id);

--
-- Table structure for table 'jforum_topics_watch'
--

CREATE TABLE jforum_topics_watch (
  topic_id INTEGER NOT NULL DEFAULT 0,
  user_id INTEGER NOT NULL DEFAULT 0,
  is_read INTEGER NOT NULL DEFAULT 0
);
CREATE INDEX idx_tw_topic ON jforum_topics_watch(topic_id);
CREATE INDEX idx_tw_user ON jforum_topics_watch(user_id);

--
-- Table structure for table 'jforum_users'
--

CREATE SEQUENCE jforum_users_seq;
CREATE TABLE jforum_users (
  user_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_users_seq'),
  user_active INTEGER DEFAULT NULL,
  username VARCHAR(50) NOT NULL DEFAULT '',
  user_password VARCHAR(32) NOT NULL DEFAULT '',
  user_session_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  user_session_page INTEGER NOT NULL DEFAULT 0,
  user_lastvisit timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  user_regdate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  user_level INTEGER DEFAULT NULL,
  user_posts INTEGER NOT NULL DEFAULT 0,
  user_timezone VARCHAR(5) NOT NULL DEFAULT '',
  user_style INTEGER DEFAULT NULL,
  user_lang VARCHAR(255) DEFAULT NULL,
  user_dateformat VARCHAR(20) NOT NULL DEFAULT '%d/%M/%Y %H:%i',
  user_new_privmsg INTEGER NOT NULL DEFAULT 0,
  user_unread_privmsg INTEGER NOT NULL DEFAULT 0,
  user_last_privmsg timestamp NULL,
  user_emailtime timestamp NULL,
  user_viewemail INTEGER DEFAULT 0,
  user_attachsig INTEGER DEFAULT 1,
  user_allowhtml INTEGER DEFAULT 0,
  user_allowbbcode INTEGER DEFAULT 1,
  user_allowsmilies INTEGER DEFAULT 1,
  user_allowavatar INTEGER DEFAULT 1,
  user_allow_pm INTEGER DEFAULT 1,
  user_allow_viewonline INTEGER DEFAULT 1,
  user_notify INTEGER DEFAULT 1,
  user_notify_pm INTEGER DEFAULT 1,
  user_popup_pm INTEGER DEFAULT 1,
  rank_id INTEGER DEFAULT 1,
  user_avatar VARCHAR(100) DEFAULT NULL,
  user_avatar_type INTEGER NOT NULL DEFAULT 0,
  user_email VARCHAR(255) NOT NULL DEFAULT '',
  user_icq VARCHAR(15) DEFAULT NULL,
  user_website VARCHAR(100) DEFAULT NULL,
  user_from VARCHAR(100) DEFAULT NULL,
  user_sig TEXT,
  user_sig_bbcode_uid VARCHAR(10) DEFAULT NULL,
  user_aim VARCHAR(255) DEFAULT NULL,
  user_yim VARCHAR(255) DEFAULT NULL,
  user_msnm VARCHAR(255) DEFAULT NULL,
  user_occ VARCHAR(100) DEFAULT NULL,
  user_interests VARCHAR(255) DEFAULT NULL,
  user_actkey VARCHAR(32) DEFAULT NULL,
  gender CHAR(1) DEFAULT NULL,
  themes_id INTEGER DEFAULT NULL,
  deleted INTEGER DEFAULT NULL,
  user_viewonline INTEGER DEFAULT 1,
  security_hash VARCHAR(32),
  PRIMARY KEY  (user_id)
);


--
-- Table structure for table 'jforum_vote_desc'
--

CREATE SEQUENCE jforum_vote_desc_seq;
CREATE TABLE jforum_vote_desc (
  vote_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_vote_desc_seq'),
  topic_id INTEGER NOT NULL DEFAULT 0,
  vote_text TEXT NOT NULL,
  vote_start INTEGER NOT NULL DEFAULT 0,
  vote_length INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY  (vote_id)
);

--
-- Table structure for table 'jforum_vote_results'
--

CREATE TABLE jforum_vote_results (
  vote_id INTEGER NOT NULL DEFAULT 0,
  vote_option_id INTEGER NOT NULL DEFAULT 0,
  vote_option_text VARCHAR(255) NOT NULL DEFAULT '',
  vote_result INTEGER NOT NULL DEFAULT 0
);

--
-- Table structure for table 'jforum_vote_voters'
--

CREATE TABLE jforum_vote_voters (
  vote_id INTEGER NOT NULL DEFAULT 0,
  vote_user_id INTEGER NOT NULL DEFAULT 0,
  vote_user_ip CHAR(8) NOT NULL DEFAULT ''
);

--
-- Table structure for table 'jforum_words'
--

CREATE SEQUENCE jforum_words_seq;
CREATE TABLE jforum_words (
  word_id INTEGER NOT NULL DEFAULT NEXTVAL('jforum_words_seq'),
  word VARCHAR(100) NOT NULL DEFAULT '',
  replacement VARCHAR(100) NOT NULL DEFAULT '',
  PRIMARY KEY  (word_id)
);

--
-- Table structure for table 'jforum_search_words'
--
CREATE SEQUENCE jforum_search_words_seq;
CREATE TABLE jforum_search_words (
  word_id INT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('jforum_search_words_seq'),
  word VARCHAR(100) NOT NULL,
  word_hash INT
);
CREATE INDEX idx_sw_word ON jforum_search_words(word);
CREATE INDEX idx_sw_hash ON jforum_search_words(word_hash);

-- 
-- Table structure for table 'jforum_search_wordmatch'
--
CREATE TABLE jforum_search_wordmatch (
  post_id INT NOT NULL,
  word_id INT NOT NULL,
  title_match INTEGER DEFAULT 0
);
CREATE INDEX idx_swm_post ON jforum_search_wordmatch(post_id);
CREATE INDEX idx_swm_word ON jforum_search_wordmatch(word_id);
CREATE INDEX idx_swm_title ON jforum_search_wordmatch(title_match);

--
-- Table structure for table 'jforum_search_results'
--
CREATE TABLE jforum_search_results (
  topic_id INT NOT NULL,
  session VARCHAR(50),
  time TIMESTAMP
);
CREATE INDEX idx_sr_topic ON jforum_search_results(topic_id);


CREATE TABLE jforum_search_topics (
  topic_id INTEGER NOT NULL,
  forum_id INTEGER NOT NULL DEFAULT 0,
  topic_title VARCHAR(60) NOT NULL DEFAULT '',
  user_id INTEGER NOT NULL DEFAULT 0,
  topic_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  topic_views INTEGER DEFAULT 1,
  topic_replies INTEGER DEFAULT 0,
  topic_status INTEGER DEFAULT 0,
  topic_vote INTEGER DEFAULT 0,
  topic_type INTEGER DEFAULT 0,
  topic_first_post_id INTEGER DEFAULT 0,
  topic_last_post_id INTEGER NOT NULL DEFAULT 0,
  moderated INTEGER DEFAULT 0,
  session VARCHAR(50),
  time TIMESTAMP
);
CREATE INDEX idx_st_topic ON jforum_search_topics(topic_id);
CREATE INDEX idx_st_forum ON jforum_search_topics(forum_id);
CREATE INDEX idx_st_user ON jforum_search_topics(user_id);
CREATE INDEX idx_st_fp ON jforum_search_topics(topic_first_post_id);
CREATE INDEX idx_st_lp ON jforum_search_topics(topic_last_post_id);