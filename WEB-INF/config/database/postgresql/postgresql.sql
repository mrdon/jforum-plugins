# #############
# CategoryModel
# #############
CategoryModel.addNew = INSERT INTO jforum_categories (title, display_order) VALUES (?, NEXTVAL('jforum_categories_order_seq'))

# ##########
# UserModel
# ##########
UserModel.selectAllByLimit = SELECT user_email, user_id, user_posts, user_regdate, username FROM jforum_users ORDER BY username LIMIT ? OFFSET ?
UserModel.lastGeneratedUserId = SELECT CURRVAL('jforum_users_seq')

UserModel.selectById = SELECT u.*, \
	(SELECT COUNT(1) FROM jforum_privmsgs pm \
	WHERE pm.privmsgs_to_userid = u.user_id \
	AND pm.privmsgs_type = 1) AS private_messages \
	FROM jforum_users u \
	WHERE u.user_id = ?

# #############
# PostModel
# #############
PostModel.lastGeneratedPostId = SELECT CURRVAL('jforum_posts_seq')

PostModel.selectAllByTopicByLimit = SELECT p.post_id, topic_id, forum_id, p.user_id, post_time, poster_ip, enable_bbcode, \
	enable_html, enable_smilies, enable_sig, post_edit_time, post_edit_count, status, pt.post_subject, pt.post_text, username \
	FROM jforum_posts p, jforum_posts_text pt, jforum_users u \
	WHERE p.post_id = pt.post_id \
	AND topic_id = ? \
	AND p.user_id = u.user_id \
	ORDER BY post_time ASC \
	LIMIT ? OFFSET ?
	
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

ForumModel.selectAll = SELECT f.*, COUNT(p.post_id) AS total_posts \
	FROM jforum_forums f \
	LEFT JOIN jforum_topics t ON t.forum_id = f.forum_id \
	LEFT JOIN jforum_posts p ON p.topic_id = t.topic_id \
	GROUP BY f.categories_id, f.forum_id, \
	      f.forum_name, f.forum_desc, f.forum_order, \
	      f.forum_topics, f.forum_last_post_id, f.moderated

ForumModel.generatedForumId = SELECT CURRVAL('jforum_forums_seq');

# #############
# TopicModel
# #############
TopicModel.selectAllByForumByLimit = SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time \
	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
	WHERE t.forum_id = ? \
	AND t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id \
	ORDER BY t.topic_type DESC, t.topic_time DESC, t.topic_last_post_id DESC \
	LIMIT ? OFFSET ?
	
TopicModel.selectRecentTopicsByLimit = SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time \
	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
	WHERE t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id \
	ORDER BY t.topic_type DESC, p2.post_time DESC, t.topic_last_post_id DESC \
	LIMIT ?
	
TopicModel.lastGeneratedTopicId = SELECT CURRVAL('jforum_topics_seq')

# #####################
# PrivateMessagesModel
# #####################
PrivateMessagesModel.lastGeneratedPmId = SELECT CURRVAL('jforum_privmsgs_seq')

# ############
# SearchModel
# ############
SearchModel.lastGeneratedWordId = SELECT CURRVAL('jforum_search_words_seq')

SearchModel.cleanSearchResults = DELETE FROM jforum_search_results WHERE session = ? OR time < (NOW() - INTERVAL '1 HOUR')
SearchModel.cleanSearchTopics = DELETE FROM jforum_search_topics WHERE session = ? OR time < (NOW() - INTERVAL '1 HOUR')

SearchModel.insertTopicsIds = INSERT INTO jforum_search_results ( topic_id, session, time ) SELECT DISTINCT t.topic_id, ?::varchar, NOW() FROM jforum_topics t, jforum_posts p \
	WHERE t.topic_id = p.topic_id \
	AND p.post_id IN (:posts:)

# #############
# SmiliesModel
# #############
SmiliesModel.lastGeneratedSmilieId = SELECT CURRVAL('jforum_smilies_seq')

# ##################
# PermissionControl
# ##################
PermissionControl.lastGeneratedRoleId = SELECT CURRVAL('jforum_roles_seq')


# ##############
# CategoryModel
# ##############
CategoryModel.lastGeneratedCategoryId = SELECT CURRVAL('jforum_categories_seq')