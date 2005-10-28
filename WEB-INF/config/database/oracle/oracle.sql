# #############
# GroupModel
# #############
GroupModel.addNew = INSERT INTO jforum_groups (group_id, group_name, group_description, parent_id) VALUES (jforum_groups_seq.nextval, ?, ?, ?)

# #############
# CategoryModel
# #############
CategoryModel.addNew = INSERT INTO jforum_categories (categories_id, title, display_order, moderated) VALUES (jforum_categories_seq.nextval, ?, ?, ?)

# #############
# RankingModel
# #############
RankingModel.addNew = INSERT INTO jforum_ranks (rank_id, rank_title, rank_min ) VALUES (jforum_ranks_seq.nextval, ?, ? )

# #############
# ConfigModel
# #############
ConfigModel.insert = INSERT INTO jforum_config (config_id, config_name, config_value) VALUES (jforum_config_seq.nextval, ?, ?)

# ##########
# UserModel
# ##########
UserModel.addNew = INSERT INTO jforum_users (user_id, username, user_password, user_email, user_regdate, user_actkey) VALUES (jforum_users_seq.nextval, ?, ?, ?, ?, ?)


UserModel.selectAllByLimit = SELECT * FROM ( \
        SELECT user_email, user_id, user_posts, user_regdate, username, deleted, user_karma, user_from, user_website, user_viewemail, ROW_NUMBER() OVER(ORDER BY user_id) LINENUM  \
        FROM jforum_users ORDER BY username \
        ) \
        WHERE LINENUM >= ? AND LINENUM <= ?

UserModel.lastGeneratedUserId = SELECT jforum_users_seq.currval FROM DUAL

UserModel.selectById = SELECT u.*, \
	(SELECT COUNT(1) FROM jforum_privmsgs pm \
	WHERE pm.privmsgs_to_userid = u.user_id \
	AND pm.privmsgs_type = 1) AS private_messages \
	FROM jforum_users u \
	WHERE u.user_id = ?


UserModel.lastUserRegistered = SELECT * FROM ( \
        SELECT user_id, username, ROW_NUMBER() OVER(ORDER BY user_regdate DESC) LINENUM FROM jforum_users \
        ORDER BY user_regdate DESC \
        ) \
        WHERE LINENUM <= 1
        
UserModel.selectAllByGroup = SELECT * FROM ( \
	SELECT user_email, u.user_id, user_posts, user_regdate, username, deleted, user_karma, user_from, user_website, user_viewemail, ROW_NUMBER() OVER(ORDER BY u.user_id) LINENUM \
	FROM jforum_users u, jforum_user_groups ug \
	WHERE u.user_id = ug.user_id \
	AND ug.group_id = ? \
	ORDER BY username ) WHERE LINENUM >= ? AND LINENUM <= ?

# ################
# PermissionControl
# ################
PermissionControl.addGroupRole = INSERT INTO jforum_roles (role_id, group_id, name, role_type ) VALUES (jforum_roles_seq.nextval, ?, ?, ?)
PermissionControl.addUserRole = INSERT INTO jforum_roles (role_id, user_id, name, role_type ) VALUES (jforum_roles_seq.nextval, ?, ?, ?)

# #############
# PostModel
# #############
PostModel.addNewPost = INSERT INTO jforum_posts (post_id, topic_id, forum_id, user_id, post_time, poster_ip, enable_bbcode, enable_html, enable_smilies, enable_sig, post_edit_time, need_moderate) \
	VALUES (jforum_posts_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)
PostModel.addNewPostText = INSERT INTO jforum_posts_text ( post_text, post_id, post_subject ) VALUES (EMPTY_BLOB(), ?, ?)
PostModel.addNewPostTextField = SELECT post_text from jforum_posts_text WHERE post_id = ? FOR UPDATE
PostModel.updatePostText = UPDATE jforum_posts_text SET post_subject = ? WHERE post_id = ?

PostModel

PostModel.lastGeneratedPostId = SELECT jforum_posts_seq.currval FROM DUAL

PostModel.selectAllByTopicByLimit = SELECT * FROM ( \
    SELECT p.post_id, topic_id, forum_id, p.user_id, post_time, poster_ip, enable_bbcode, p.attach, p.need_moderate, \
   	enable_html, enable_smilies, enable_sig, post_edit_time, post_edit_count, status, pt.post_subject, pt.post_text, username,  \
   	ROW_NUMBER() OVER(ORDER BY p.post_time ASC) LINENUM \
   	FROM jforum_posts p, jforum_posts_text pt, jforum_users u \
	WHERE p.post_id = pt.post_id  \
	AND topic_id = ? \
	AND p.user_id = u.user_id \
	AND p.need_moderate = 0 \
	ORDER BY post_time ASC \
) \
WHERE LINENUM BETWEEN ? AND ?


PostModel.selectByUserByLimit = SELECT * FROM ( \
    SELECT p.post_id, topic_id, forum_id, p.user_id, post_time, poster_ip, enable_bbcode, p.attach, \
	enable_html, enable_smilies, enable_sig, post_edit_time, post_edit_count, status, pt.post_subject, pt.post_text, username, p.need_moderate, \
	ROW_NUMBER() OVER(ORDER BY p.post_time ASC) LINENUM \
	FROM jforum_posts p, jforum_posts_text pt, jforum_users u \
	WHERE p.post_id = pt.post_id \
	AND p.user_id = u.user_id \
	AND p.user_id = ? \
	AND p.need_moderate = 0 \
	ORDER BY post_time ASC \
) \
WHERE LINENUM BETWEEN ? AND ?

TopicModel.selectByUserByLimit = SELECT * FROM ( \
    SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time, p.attach, \
    ROW_NUMBER() OVER(ORDER BY p2.post_time ASC) LINENUM \
	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
	WHERE t.user_id = u.user_id \
	AND t.user_id = ? \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id \
	AND p.need_moderate = 0 \
	ORDER BY p2.post_time DESC, t.topic_last_post_id DESC \
) \
WHERE LINENUM BETWEEN ? AND ?

# #############
# ForumModel
# #############
ForumModel.addNew = INSERT INTO jforum_forums (forum_id, categories_id, forum_name, forum_desc, forum_order, moderated) VALUES (jforum_forums_seq.nextval, ?, ?, ?, ?, ?)

ForumModel.selectById = SELECT f.*, COUNT(p.post_id) AS total_posts \
	FROM jforum_forums f, jforum_topics t, jforum_posts p  \
	WHERE 	t.forum_id(+) = f.forum_id AND \
        	p.topic_id(+) = t.topic_id AND \
        	f.forum_id = ? \
	GROUP BY f.categories_id, f.forum_id, \
	      f.forum_name, f.forum_desc, f.forum_order, \
	      f.forum_topics, f.forum_last_post_id, f.moderated

ForumModel.selectAll = SELECT f.*, COUNT(p.post_id) AS total_posts \
	FROM jforum_forums f, jforum_topics t, jforum_posts p \
	WHERE 	t.forum_id(+) = f.forum_id AND \
        	p.topic_id(+) = t.topic_id \
	GROUP BY f.categories_id, f.forum_id, \
	      f.forum_name, f.forum_desc, f.forum_order, \
	      f.forum_topics, f.forum_last_post_id, f.moderated

ForumModel.lastGeneratedForumId = SELECT jforum_forums_seq.currval FROM DUAL

# #############
# TopicModel
# #############
TopicModel.addNew = INSERT INTO jforum_topics (topic_id, forum_id, topic_title, user_id, topic_time, topic_first_post_id, topic_last_post_id, topic_type, moderated) \
	VALUES (jforum_topics_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)

##########################################################################################
# Ignores attachements (0 as attach), but goes two orders of magnitude higher...
##########################################################################################
TopicModel.selectAllByForumByLimit = SELECT * FROM ( \
    SELECT row_.*, rownum rownum_ from ( \
    SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, \
        u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p.post_time, 0 as attach, rownum \
	FROM jforum_topics t, jforum_users u, \
        jforum_posts p, jforum_users u2 \
	WHERE t.forum_id = ? \
	AND t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND u2.user_id = p.user_id \
	ORDER BY t.topic_type DESC, p.post_time DESC, t.topic_last_post_id DESC \
    ) row_ where rownum <= ? ) where rownum_ >= ? 

##########################################################################################
# This version returns values for the attachment, but is MUCH slower than the above query.
##########################################################################################
#TopicModel.selectAllByForumByLimit = SELECT * FROM ( \
#    SELECT row_.*, rownum rownum_ from ( \
#    SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, \
#        u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time, p.attach, rownum \
#	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
#	WHERE t.forum_id = ? \
#	AND t.user_id = u.user_id \
#	AND p.post_id = t.topic_first_post_id \
#	AND p2.post_id = t.topic_last_post_id \
#	AND u2.user_id = p2.user_id \
#	ORDER BY t.topic_type DESC, p2.post_time DESC, t.topic_last_post_id DESC)  \
#	row_ where rownum <= ? \
#) \
#where rownum_ >= ?

#TopicModel.selectAllByForumByLimit = SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, \
#    u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time, p.attach \
#	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
#	WHERE t.forum_id = ? \
#	AND t.user_id = u.user_id \
#	AND p.post_id = t.topic_first_post_id \
#	AND p2.post_id = t.topic_last_post_id \
#	AND u2.user_id = p2.user_id \
#	ORDER BY t.topic_type DESC, p2.post_time DESC, t.topic_last_post_id DESC 


TopicModel.selectRecentTopicsByLimit = SELECT * FROM ( \
    SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id,\
    	u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time, p.attach, \
        ROW_NUMBER() OVER(ORDER BY p2.post_time DESC, t.topic_last_post_id DESC) LINENUM \
	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
	WHERE t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id \
	AND t.topic_type = 0 \
	ORDER BY p2.post_time DESC, t.topic_last_post_id DESC \
    ) \
    WHERE LINENUM <= ?

TopicModel.notifyUsers = SELECT u.user_id AS user_id, u.username AS username, \
	u.user_lang AS user_lang, u.user_email AS user_email \
	FROM jforum_topics_watch tw, jforum_users u \
	WHERE   tw.user_id = u.user_id AND \
	        tw.topic_id = ? \
	AND tw.is_read = 1 \
	AND u.user_id NOT IN ( ?, ? )

TopicModel.lastGeneratedTopicId = SELECT jforum_topics_seq.currval FROM DUAL

TopicModel.selectLastN = SELECT * FROM ( \
    SELECT topic_title, topic_time, topic_id, topic_type, ROW_NUMBER() OVER(ORDER BY topic_time DESC) LINENUM \
    FROM jforum_topics \
    ORDER BY topic_time DESC \
    ) \
    WHERE LINENUM <= ?

TopicModel.topicPosters = SELECT user_id, username, user_karma, user_avatar, user_allowavatar, user_regdate, user_posts, user_icq, \
	user_from, user_email, rank_id, user_sig, user_attachsig, user_viewemail, user_msnm, user_yim, user_website, user_sig, user_aim \
	FROM jforum_users \
	WHERE user_id IN (:ids:)

# ####################
# PrivateMessageModel
# ####################
PrivateMessageModel.add = INSERT INTO jforum_privmsgs (privmsgs_id, privmsgs_type, privmsgs_subject, privmsgs_from_userid, \
	privmsgs_to_userid, privmsgs_date, privmsgs_enable_bbcode, privmsgs_enable_html, privmsgs_enable_smilies, \
	privmsgs_attach_sig ) \
	VALUES (jforum_privmsgs_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ? )

PrivateMessagesModel.addText = INSERT INTO jforum_privmsgs_text ( privmsgs_id, privmsgs_text ) VALUES ( ?, EMPTY_BLOB() )
PrivateMessagesModel.addTextField = SELECT privmsgs_text from jforum_privmsgs_text WHERE privmsgs_id = ? FOR UPDATE
PrivateMessagesModel.lastGeneratedPmId = SELECT jforum_privmsgs_seq.currval FROM DUAL

# ############
# SearchModel
# ############
SearchModel.insertWords = INSERT INTO jforum_search_words (word_id, word, word_hash) VALUES (jforum_search_words_seq.nextval, ?, ?)

SearchModel.searchBase = SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time, p.attach \
	FROM jforum_search_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2, jforum_forums f, jforum_search_results sr \
	WHERE t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id \
	AND f.forum_id = t.forum_id \
	AND t.topic_id = sr.topic_id \
	AND sr.session_id = ? \
	AND t.session_id = ? \
	:criterias: \
	ORDER BY :orderByField: :orderBy:

SearchModel.insertTopicsIds = INSERT INTO jforum_search_results ( topic_id, session_id, search_time ) SELECT DISTINCT t.topic_id, ?, sysdate FROM jforum_topics t, jforum_posts p \
	WHERE t.topic_id = p.topic_id \
	AND p.post_id IN (:posts:)


SearchModel.selectTopicData = INSERT INTO jforum_search_topics (topic_id, forum_id, topic_title, user_id, topic_time, \
	topic_views, topic_status, topic_replies, topic_vote, topic_type, topic_first_post_id, topic_last_post_id, moderated, session_id, search_time) \
	SELECT t.topic_id, t.forum_id, t.topic_title, t.user_id, t.topic_time, \
	t.topic_views, t.topic_status, t.topic_replies, t.topic_vote, t.topic_type, t.topic_first_post_id, t.topic_last_post_id, t.moderated, ?, sysdate \
	FROM jforum_topics t, jforum_search_results s \
	WHERE t.topic_id = s.topic_id \
	AND s.session_id = ?

SearchModel.lastGeneratedWordId = SELECT jforum_search_words_seq.currval FROM DUAL

SearchModel.getPostsToIndex = SELECT * FROM ( \
	SELECT row_.*, rownum rownum_ from ( \
	SELECT p.post_id, pt.post_text, pt.post_subject \
	FROM jforum_posts p, jforum_posts_text pt \
	WHERE p.post_id = pt.post_id \
	AND p.post_id BETWEEN ? AND ?) \
	row_ where rownum < ?) \
	where rownum_ >= ?
#
# The construction ((SYSDATE - time_field)*24) > 1.0 mean following:
# (SYSDATE - time_field) return days. E.q if delta is 20 minuts it return 0.0125. If multyply on 24, that it would be hours - 0.3
# So, ((SYSDATE - time_field)*24) > 1.0 totally mean 'delta' > 1 hour   
#
SearchModel.cleanSearchResults = DELETE FROM jforum_search_results WHERE session_id = ? OR ((SYSDATE - search_time)*24) > 1.0
SearchModel.cleanSearchTopics = DELETE FROM jforum_search_topics WHERE session_id = ? OR ((SYSDATE - search_time)*24) > 1.0

SearchModel.searchByTime = INSERT INTO jforum_search_results (topic_id, session_id, search_time) SELECT DISTINCT t.topic_id, ?, SYSDATE FROM jforum_topics t, jforum_posts p \
	WHERE t.topic_id = p.topic_id \
	AND p.post_time > ?

# #############
# SmiliesModel
# #############
SmiliesModel.addNew = INSERT INTO jforum_smilies (smilie_id, code, url, disk_name) VALUES (jforum_smilies_seq.nextval, ?, ?, ?)

SmiliesModel.lastGeneratedSmilieId = SELECT jforum_smilies_seq.currval FROM DUAL

# ##################
# PermissionControl
# ##################
PermissionControl.lastGeneratedRoleId = SELECT jforum_roles_seq.currval FROM DUAL

PermissionControl.loadGroupRoles = SELECT r.role_id, r.name, rv.role_value, rv.role_type AS rv_type, r.role_type \
	FROM jforum_roles r, jforum_role_values rv \
	WHERE rv.role_id(+) = r.role_id AND r.group_id = ? \
	ORDER BY r.role_id 

# ##############
# CategoryModel
# ##############
CategoryModel.lastGeneratedCategoryId = SELECT jforum_categories_seq.currval  FROM DUAL

# ###########
# KarmaModel
# ###########
KarmaModel.add = INSERT INTO jforum_karma (karma_id, post_id, post_user_id, from_user_id, points, topic_id, rate_date) VALUES (jforum_karma_seq.nextval, ?, ?, ?, ?, ?, ?)

# ##############
# BookmarkModel
# ##############
BookmarkModel.add = INSERT INTO jforum_bookmarks (bookmark_id, user_id, relation_id, relation_type, public_visible, title, description) \
	VALUES (jforum_bookmarks_seq.nextval, ?, ?, ?, ?, ?, ?)

# ################
# AttachmentModel
# ################
AttachmentModel.addQuotaLimit = INSERT INTO jforum_quota_limit (quota_limit_id, quota_desc, quota_limit, quota_type) VALUES (jforum_quota_limit_seq.nextval, ?, ?, ?)
AttachmentModel.lastGeneratedAttachmentId = SELECT jforum_attach_seq.currval FROM dual

AttachmentModel.addExtensionGroup = INSERT INTO jforum_extension_groups (extension_group_id, name, allow, upload_icon, download_mode) \
	VALUES (jforum_extension_group_seq.nextval, ?, ?, ?, ?)

AttachmentModel.addExtension = INSERT INTO jforum_extensions (extension_id, extension_group_id, description, upload_icon, extension, allow) \
	VALUES (jforum_extensions_seq.nextval, ?, ?, ?, ?, ?)

AttachmentModel.addAttachment = INSERT INTO jforum_attach (attach_id, post_id, privmsgs_id, user_id) VALUES (jforum_attach_seq.nextval, ?, ?, ?)

AttachmentModel.addAttachmentInfo = INSERT INTO jforum_attach_desc (attach_desc_id, attach_id, physical_filename, real_filename, description, \
	mimetype, filesize, upload_time, thumb, extension_id ) VALUES (jforum_attach_desc_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)
