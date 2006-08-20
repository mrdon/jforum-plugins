#
# Clean
#
query.clean.categories = DELETE FROM jforum_categories
query.clean.forums = DELETE FROM jforum_forums
query.clean.posts = DELETE FROM jforum_posts
query.clean.posts.text = DELETE FROM jforum_posts_text
query.clean.privmsgs = DELETE FROM jforum_privmsgs
query.clean.privmsgs.text = DELETE FROM jforum_privmsgs_text
query.clean.ranks = DELETE FROM jforum_ranks
query.clean.search.words = DELETE FROM jforum_search_words
query.clean.search.wordmatch = DELETE FROM jforum_search_wordmatch
query.clean.topics = DELETE FROM jforum_topics
query.clean.topicswatch = DELETE FROM jforum_topics_watch
query.clean.users = DELETE FROM jforum_users
query.clean.words = DELETE FROM jforum_words

#
# General
#
query.totalposts = SELECT COUNT(1) AS total FROM phpbb_posts
query.select.poststext = SELECT post_id, post_subject, post_text FROM phpbb_posts_text

query.select.users = SELECT user_id, user_active, username, user_password, user_regdate user_regdate, user_level, user_posts, user_timezone, \
	user_style, user_lang, user_dateformat, user_new_privmsg, user_unread_privmsg, user_last_privmsg, \
	user_viewemail, user_attachsig, \
	user_allowhtml, user_allowbbcode, user_allowsmile, user_allowavatar, user_allow_pm, \
	user_notify, user_notify_pm, user_popup_pm, user_rank, user_avatar, user_avatar_type, user_email, user_icq, \
	user_website, user_from, user_sig, user_aim, user_yim, user_msnm, user_occ, user_interests, \
	user_allow_viewonline FROM phpbb_users

query.select.pm = SELECT privmsgs_text_id, privmsgs_text FROM phpbb_privmsgs_text
query.update.anonymous = UPDATE jforum_users SET user_id = 1 WHERE user_id = -1
#
# Insert
#
query.posts = INSERT INTO jforum_posts ( post_id, topic_id, forum_id, user_id, post_time, poster_ip, enable_bbcode, \
	enable_html, enable_smilies, enable_sig ) \
	SELECT post_id, topic_id, forum_id, poster_id, FROM_UNIXTIME(post_time), poster_ip, enable_bbcode, \
	enable_html, enable_smilies, enable_sig \
	FROM phpbb_posts
	
query.posts.text = INSERT INTO jforum_posts_text ( post_id, post_subject, post_text ) VALUES (?, ?, ?)

query.categories = INSERT INTO jforum_categories ( categories_id, title, display_order ) \
	SELECT cat_id, cat_title, cat_order FROM phpbb_categories

query.forums = INSERT INTO jforum_forums ( forum_id, categories_id, forum_name, forum_desc, forum_order, forum_topics, forum_last_post_id ) \
	SELECT forum_id, cat_id, forum_name, forum_desc, forum_order, forum_topics, forum_last_post_id FROM phpbb_forums

query.privmsgs = INSERT INTO jforum_privmsgs ( privmsgs_id, privmsgs_type, privmsgs_subject, privmsgs_from_userid, privmsgs_to_userid, \
	privmsgs_date, privmsgs_ip, privmsgs_enable_bbcode, privmsgs_enable_html, privmsgs_enable_smilies, \
	privmsgs_attach_sig ) \
	SELECT privmsgs_id, privmsgs_type, privmsgs_subject, privmsgs_from_userid, privmsgs_to_userid, \
	FROM_UNIXTIME(privmsgs_date), privmsgs_ip, privmsgs_enable_bbcode, privmsgs_enable_html, privmsgs_enable_smilies, \
	privmsgs_attach_sig \
	FROM phpbb_privmsgs
	
query.privmsgs.text = INSERT INTO jforum_privmsgs_text ( privmsgs_id, privmsgs_text ) VALUES (?, ?)

query.ranks = INSERT INTO jforum_ranks ( rank_id, rank_title, rank_min, rank_special, rank_image ) \
	SELECT rank_id, rank_title, rank_min, rank_special, rank_image FROM phpbb_ranks

query.search.words = INSERT INTO jforum_search_words ( word_id, word ) \
	SELECT word_id, word_text FROM phpbb_search_wordlist

query.search.wordmatch = INSERT INTO jforum_search_wordmatch ( post_id, word_id, title_match ) \
	SELECT post_id, word_id, title_match FROM phpbb_search_wordmatch

query.topics = INSERT INTO jforum_topics ( topic_id, forum_id, topic_title, user_id, topic_time, topic_views, \
	topic_replies, topic_status, topic_vote, topic_type, topic_first_post_id, topic_last_post_id ) \
	SELECT topic_id, forum_id, topic_title, topic_poster, FROM_UNIXTIME(topic_time), topic_views, \
	topic_replies, topic_status, topic_vote, topic_type, topic_first_post_id, topic_last_post_id \
	FROM phpbb_topics

query.topics.watch = INSERT INTO jforum_topics_watch ( topic_id, user_id, is_read ) \
	SELECT topic_id, user_id, '1' FROM phpbb_topics_watch

query.users = INSERT INTO jforum_users ( user_id, user_active, username, user_password, user_regdate, \
	user_level, user_posts, user_timezone, user_style, user_lang, user_dateformat, user_new_privmsg, \
	user_unread_privmsg, user_last_privmsg, user_viewemail, user_attachsig, \
	user_allowhtml, user_allowbbcode, user_allowsmilies, user_allowavatar, user_allow_pm, \
	user_notify, user_notify_pm, user_popup_pm, rank_id, user_avatar, user_avatar_type, user_email, user_icq, \
	user_website, user_from, user_sig, user_aim, user_yim, user_msnm, user_occ, user_interests, \
	user_viewonline ) VALUES (?, ?, ?, ?, FROM_UNIXTIME(?), ?, ?, ?, ?, ?, ?, ?, ?, FROM_UNIXTIME(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)

query.words = INSERT INTO jforum_words ( word_id, word, replacement ) \
	SELECT word_id, word, replacement FROM phpbb_words