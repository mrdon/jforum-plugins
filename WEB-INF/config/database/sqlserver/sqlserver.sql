# #############
# GenericModel
# #############

GenericModel.selectByLimit = SELECT TOP



# #############
# UserModel
# #############

UserModel.selectById = SELECT COUNT(pm.privmsgs_to_userid) AS private_messages, u.user_id, u.user_active, u.username, u.user_password, u.user_session_time, \
								u.user_session_page, u.user_lastvisit, u.user_regdate, u.user_level, u.user_posts, u.user_timezone, u.user_style, \
								u.user_lang, u.user_dateformat, u.user_new_privmsg, u.user_unread_privmsg, u.user_last_privmsg, u.user_emailtime, \
								u.user_viewemail, u.user_attachsig, u.user_allowhtml, u.user_allowbbcode, u.user_allowsmilies, u.user_allowavatar, \
								u.user_allow_pm, u.user_allow_viewonline, u.user_notify, u.user_notify_pm, u.user_popup_pm, u.rank_id, u.user_avatar, \
								u.user_avatar_type, u.user_email, u.user_icq, u.user_website, u.user_from, CAST(u.user_sig as varchar) as user_sig , u.user_sig_bbcode_uid, \
								u.user_aim, u.user_yim, u.user_msnm, u.user_occ, u.user_interests, u.user_actkey, u.gender, u.themes_id, u.deleted, \
								u.user_viewonline, u.security_hash, u.user_karma \
								FROM jforum_users u \
								LEFT JOIN jforum_privmsgs pm ON pm.privmsgs_type = 1 AND pm.privmsgs_to_userid = u.user_id \
								WHERE u.user_id = ? \
								GROUP BY pm.privmsgs_to_userid, u.user_id, u.user_active, u.username, u.user_password, u.user_session_time, \
								u.user_session_page, u.user_lastvisit, u.user_regdate, u.user_level, u.user_posts, u.user_timezone, u.user_style, \
								u.user_lang, u.user_dateformat, u.user_new_privmsg, u.user_unread_privmsg, u.user_last_privmsg, u.user_emailtime, \
								u.user_viewemail, u.user_attachsig, u.user_allowhtml, u.user_allowbbcode, u.user_allowsmilies, u.user_allowavatar, \
								u.user_allow_pm, u.user_allow_viewonline, u.user_notify, u.user_notify_pm, u.user_popup_pm, u.rank_id, u.user_avatar, \
								u.user_avatar_type, u.user_email, u.user_icq, u.user_website, u.user_from, CAST(u.user_sig as varchar), u.user_sig_bbcode_uid, \
								u.user_aim, u.user_yim, u.user_msnm, u.user_occ, u.user_interests, u.user_actkey, u.gender, u.themes_id, u.deleted, \
								u.user_viewonline, u.security_hash, u.user_karma
								
UserModel.lastUserRegistered = SELECT TOP 1 user_id, username FROM jforum_users ORDER BY user_regdate DESC
UserModel.lastGeneratedUserId = SELECT IDENT_CURRENT('jforum_users') AS user_id
UserModel.selectAllByLimit = user_email, user_id, user_posts, user_regdate, username, deleted, user_karma FROM jforum_users ORDER BY username

# #############
# GroupModel
# #############

GroupModel.lastGeneratedGroupId = SELECT IDENT_CURRENT('jforum_groups') AS group_id


# #############
# ForumModel
# #############

ForumModel.selectAll =  SELECT f.forum_id, f.categories_id, f.forum_name, f.forum_desc, f.forum_order, f.forum_topics, \
							  f.forum_last_post_id, f.moderated, COUNT(p.post_id) AS total_posts \
						FROM jforum_forums f \
						LEFT JOIN jforum_topics t \
						ON t.forum_id = f.forum_id \
						LEFT JOIN jforum_posts p \
						ON p.topic_id = t.topic_id \
						GROUP BY f.forum_id, f.categories_id, f.forum_name, f.forum_desc, f.forum_order, f.forum_topics, f.forum_last_post_id, f.moderated 

						
# #############
# CategoryModel
# #############		

#NOT NCESSARY - Use same as generic.sql
#CategoryModel.addNew = INSERT INTO jforum_categories (title, display_order) \
#									SELECT ? as title, \
#									case WHEN (SELECT count(*) from jforum_categories) > 0 THEN (select MAX(display_order) + 1 from jforum_categories) \
#										ELSE 1 \
#									end \
#									as display_order
									
CategoryModel.lastGeneratedCategoryId = SELECT IDENT_CURRENT('jforum_categories') AS categories_id 




# #############
# PostModel
# #############

PostModel.lastGeneratedPostId = SELECT IDENT_CURRENT('jforum_posts') AS post_id

PostModel.addNewPost = INSERT INTO jforum_posts (topic_id, forum_id, user_id, post_time, poster_ip, enable_bbcode, enable_html, enable_smilies, enable_sig, post_edit_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())

PostModel.selectAllByTopicByLimit = p.post_id, topic_id, forum_id, p.user_id, post_time, poster_ip, enable_bbcode, \
	enable_html, enable_smilies, enable_sig, post_edit_time, post_edit_count, status, user_karma, pt.post_subject, pt.post_text, username, attach \
	FROM jforum_posts p, jforum_posts_text pt, jforum_users u \
	WHERE p.post_id = pt.post_id \
	AND topic_id = ? \
	AND p.user_id = u.user_id \
	ORDER BY post_time ASC
	
# #############
# ForumModel
# #############

ForumModel.selectById = SELECT f.*, COUNT(p.post_id) AS total_posts \
	FROM jforum_forums f \
	LEFT JOIN jforum_topics t ON t.forum_id = f.forum_id \
	LEFT JOIN jforum_posts p ON p.topic_id = t.topic_id \
	WHERE f.forum_id = ? \
	GROUP BY f.categories_id, f.forum_id, \
	      f.forum_name, f.forum_desc, f.forum_order, \
	      f.forum_topics, f.forum_last_post_id, f.moderated


ForumModel.generatedForumId = SELECT IDENT_CURRENT('jforum_forums') AS forum_id



# #############
# TopicModel
# #############
	
TopicModel.selectAllByForumByLimit = t.*, p.attach, u.username AS posted_by_username, u.user_id AS posted_by_id, u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time \
	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
	WHERE t.forum_id = ? \
	AND t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id \
	ORDER BY t.topic_type DESC, t.topic_time DESC, t.topic_last_post_id DESC
	
	
TopicModel.selectRecentTopicsByLimit = t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time, p2.attach \
	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
	WHERE t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id \
	AND t.topic_type = 0 \
	ORDER BY p2.post_time DESC, t.topic_last_post_id DESC
	
TopicModel.lastGeneratedTopicId = SELECT IDENT_CURRENT('jforum_topics') AS topic_id 

# #############
# PrivateMessagesModel
# #############
PrivateMessagesModel.lastGeneratedPmId = SELECT IDENT_CURRENT('jforum_privmsgs') AS privmsgs_id 

PrivateMessageModel.selectById = SELECT p.privmsgs_id, p.privmsgs_type, p.privmsgs_subject, p.privmsgs_from_userid, p.privmsgs_to_userid, \
									p.privmsgs_date, p.privmsgs_ip, p.privmsgs_enable_bbcode, p.privmsgs_enable_html, p.privmsgs_enable_smilies, \
									p.privmsgs_attach_sig, pt.privmsgs_text FROM jforum_privmsgs p, jforum_privmsgs_text pt \
									WHERE p.privmsgs_id = pt.privmsgs_id AND p.privmsgs_id = ?

# #############
# SearchModel
# #############
SearchModel.lastGeneratedWordId = SELECT IDENT_CURRENT('jforum_search_words') AS word_id 

SearchModel.cleanSearchResults = DELETE FROM jforum_search_results WHERE session = ? OR search_time < DATEADD(HOUR, -1, getdate())
SearchModel.cleanSearchTopics = DELETE FROM jforum_search_topics WHERE session = ? OR search_time < DATEADD(HOUR, -1, getdate())


SearchModel.insertTopicsIds = INSERT INTO jforum_search_results ( topic_id, session, search_time ) \
									SELECT DISTINCT t.topic_id, ?, GETDATE() FROM jforum_topics t, jforum_posts p \
									WHERE t.topic_id = p.topic_id \
									AND p.post_id IN (:posts:)


SearchModel.searchByTime = INSERT INTO jforum_search_results (topic_id, session, search_time) SELECT DISTINCT t.topic_id, ?, GETDATE() FROM jforum_topics t, jforum_posts p \
	WHERE t.topic_id = p.topic_id \
	AND p.post_time > ?
	
SearchModel.selectTopicData = INSERT INTO jforum_search_topics (topic_id, forum_id, topic_title, user_id, topic_time, \
	topic_views, topic_status, topic_replies, topic_vote, topic_type, topic_first_post_id, topic_last_post_id, moderated, session, search_time) \
	SELECT t.topic_id, t.forum_id, t.topic_title, t.user_id, t.topic_time, \
	t.topic_views, t.topic_status, t.topic_replies, t.topic_vote, t.topic_type, t.topic_first_post_id, t.topic_last_post_id, t.moderated, ?, GETDATE() \
	FROM jforum_topics t, jforum_search_results s \
	WHERE t.topic_id = s.topic_id \
	AND s.session = ?

# #############
# SmiliesModel
# #############

SmiliesModel.lastGeneratedSmilieId = SELECT IDENT_CURRENT('jforum_smilies') AS smilie_id 


# #############
# PermissionControl
# #############

PermissionControl.lastGeneratedRoleId = SELECT IDENT_CURRENT('jforum_roles') AS role_id 

# #############
# PublishUserModel
# #############

PublishUserModel.selectById = SELECT mmpublish_user_uuid FROM jforum_mmpublish_users WHERE jforum_user_id = ?
PublishUserModel.deleteFromId = DELETE FROM jforum_mmpublish_users WHERE jforum_user_id = ?
PublishUserModel.deleteFromUuid = DELETE FROM jforum_mmpublish_users WHERE mmpublish_user_uuid = ?
PublishUserModel.update = UPDATE jforum_mmpublish_users SET mmpublish_user_uuid = ? WHERE jforum_user_id = ?
PublishUserModel.addNew = INSERT INTO jforum_mmpublish_users (jforum_user_id, mmpublish_user_uuid) VALUES(?,?)

