# ############
# GroupModel 
# ############
GroupModel.selectAll = SELECT group_id, group_name, parent_id, group_description FROM jforum_groups ORDER BY group_name
GroupModel.selectById = SELECT group_id, group_name, parent_id, group_description FROM jforum_groups WHERE group_id = ?
GroupModel.canDelete = SELECT COUNT(1) AS total FROM jforum_user_groups WHERE group_id = ?
GroupModel.delete = DELETE FROM jforum_groups WHERE group_id = ?
GroupModel.update = UPDATE jforum_groups SET group_name = ?, parent_id = ?, group_description = ? WHERE group_id = ?
GroupModel.addNew = INSERT INTO jforum_groups (group_name, group_description, parent_id) VALUES (?, ?, ?)
GroupModel.selectUsersIds = SELECT user_id FROM jforum_user_groups WHERE group_id = ?

# #############
# CategoryModel
# #############
CategoryModel.selectById = SELECT * FROM jforum_categories WHERE categories_id = ? ORDER BY title 
CategoryModel.selectAll = SELECT * FROM jforum_categories ORDER BY display_order
CategoryModel.canDelete = SELECT COUNT(1) AS total FROM jforum_forums WHERE categories_id = ?
CategoryModel.delete = DELETE FROM jforum_categories WHERE categories_id = ?
CategoryModel.update = UPDATE jforum_categories SET title = ? WHERE categories_id = ?
CategoryModel.addNew = INSERT INTO jforum_categories (title, display_order) VALUES (?, display_order + 1)

# #############
# RankingModel
# #############
RankingModel.selectById = SELECT * FROM jforum_ranks WHERE rank_id = ?
RankingModel.selectAll = SELECT * FROM jforum_ranks ORDER BY rank_min
RankingModel.delete = DELETE FROM jforum_ranks WHERE rank_id = ?
RankingModel.update = UPDATE jforum_ranks SET rank_title = ?, rank_image = ?, rank_special = ?, rank_min = ? WHERE rank_id = ?
RankingModel.addNew = INSERT INTO jforum_ranks ( rank_title, rank_min ) VALUES ( ?, ? )

# #############
# ConfigModel
# #############
ConfigModel.addEntry = INSERT INTO jforum_config (config_name, config_value) VALUES (?, ?)
ConfigModel.selectById = SELECT config_name, config_value FROM jforum_config WHERE config_id = ?
ConfigModel.selectAll = SELECT config_name, config_value, config_id FROM jforum_config ORDER BY config_name
ConfigModel.delete = DELETE FROM jforum_config WHERE config_id = ?
ConfigModel.update = UPDATE jforum_config SET config_name = ?, config_value = ? WHERE config_id = ?

# ##########
# UserModel
# ##########
UserModel.selectById = SELECT COUNT(pm.privmsgs_to_userid) AS private_messages, u.* \
	FROM jforum_users u \
	LEFT JOIN jforum_privmsgs pm ON pm.privmsgs_type = 1 AND pm.privmsgs_to_userid = u.user_id \
	WHERE u.user_id = ? \
	GROUP BY pm.privmsgs_to_userid

UserModel.selectAll = SELECT user_email, user_id, user_posts, user_regdate, username, deleted FROM jforum_users ORDER BY username
UserModel.selectAllByLimit = SELECT user_email, user_id, user_posts, user_regdate, username, deleted FROM jforum_users ORDER BY username LIMIT ?, ?
UserModel.deletedStatus = UPDATE jforum_users SET deleted = ? WHERE user_id = ?
UserModel.isDeleted = SELECT deleted FROM jforum_users WHERE user_id = ?
UserModel.incrementPosts = UPDATE jforum_users SET user_posts = user_posts + 1 WHERE user_id = ?
UserModel.decrementPosts = UPDATE jforum_users SET user_posts = user_posts - 1 WHERE user_id = ?
UserModel.rankingId = UPDATE jforum_users SET rank_id = ? WHERE user_id = ?
UserModel.activeStatus = UPDATE jforum_users SET user_active = ? WHERE user_id = ?
UserModel.addNew = INSERT INTO jforum_users (username, user_password, user_email, user_regdate, user_actkey) VALUES (?, ?, ?, ?, ?)
UserModel.findByName = SELECT user_id, username, user_email FROM jforum_users WHERE UPPER(username) LIKE UPPER(?)
# Added by Pieter for external login support:
UserModel.selectByName = SELECT * FROM jforum_users WHERE username = ?
UserModel.addNewWithId = INSERT INTO jforum_users (username, user_password, user_email, user_regdate, user_id) VALUES (?, ?, ?, ?, ?)
# End Added by Pieter

UserModel.update = UPDATE jforum_users SET user_aim = ?, \
	user_avatar = ?,\
	gender = ?, \
	themes_id = ?,\
	user_allow_pm = ?, \
	user_allowavatar = ?, \
	user_allowbbcode = ?, \
	user_allowhtml = ?, \
	user_allowsmilies = ?, \
	user_email = ?, \
	user_from = ?, \
	user_icq = ?, \
	user_interests = ?, \
	user_occ = ?, \
	user_sig = ?, \
	user_website = ?, \
	user_yim = ?, \
	user_msnm = ?, \
	user_password = ?, \
	user_viewemail = ?, \
	user_viewonline = ?, \
	user_notify = ?, \
	user_attachsig = ?, \
	username = ?, \
	user_lang = ? \
	WHERE user_id = ?
	
UserModel.lastUserRegistered = SELECT user_id, username FROM jforum_users ORDER BY user_regdate DESC LIMIT 1
UserModel.totalUsers = SELECT COUNT(1) as total_users FROM jforum_users
UserModel.isUsernameRegistered = SELECT COUNT(1) as registered FROM jforum_users WHERE username = ?
UserModel.login = SELECT user_id FROM jforum_users WHERE username = ? AND user_password = ?
UserModel.addToGroup = INSERT INTO jforum_user_groups ( user_id, group_id ) VALUES ( ?, ? )
UserModel.removeFromGroup = DELETE FROM jforum_user_groups WHERE user_id = ? AND group_id = ?

UserModel.selectGroups = SELECT ug.group_id, g.group_name FROM jforum_user_groups ug, jforum_groups g \
	WHERE ug.group_id = g.group_id \
	AND ug.user_id = ?

UserModel.saveNewPassword = UPDATE jforum_users SET user_password = ? WHERE user_email = ?
UserModel.validateLostPasswordHash = SELECT COUNT(1) AS valid FROM jforum_users WHERE security_hash = ? AND user_email = ?
UserModel.writeLostPasswordHash = UPDATE jforum_users SET security_hash = ? WHERE user_email = ?
UserModel.getUsernameByEmail = SELECT username FROM jforum_users WHERE user_email = ?
UserModel.validateActivationKeyHash = SELECT COUNT(1) AS valid FROM jforum_users WHERE user_actkey = ? AND user_id = ?
UserModel.writeUserActive = UPDATE jforum_users SET user_active = 1 WHERE user_id = ?

# #############
# PostModel
# #############
PostModel.selectById = SELECT p.post_id, topic_id, forum_id, p.user_id, post_time, poster_ip, enable_bbcode, enable_html, \
	enable_smilies, enable_sig, post_edit_time, post_edit_count, status, pt.post_subject, pt.post_text, username \
	FROM jforum_posts p, jforum_posts_text pt, jforum_users u \
	WHERE p.post_id = pt.post_id \
	AND p.post_id = ? \
	AND p.user_id = u.user_id

PostModel.deletePost = DELETE FROM jforum_posts WHERE post_id = ?
PostModel.deletePostText = DELETE FROM jforum_posts_text WHERE post_id = ?

PostModel.updatePost = UPDATE jforum_posts SET topic_id = ?, forum_id = ?, enable_bbcode = ?, enable_html = ?, enable_smilies = ?, enable_sig = ?, post_edit_time = ?, post_edit_count = post_edit_count + 1, poster_ip = ? WHERE post_id = ?
PostModel.updatePostText = UPDATE jforum_posts_text SET post_text = ?, post_subject = ? WHERE post_id = ?

PostModel.addNewPost = INSERT INTO jforum_posts (topic_id, forum_id, user_id, post_time, poster_ip, enable_bbcode, enable_html, enable_smilies, enable_sig, post_edit_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())
PostModel.addNewPostText = INSERT INTO jforum_posts_text ( post_id, post_text, post_subject ) VALUES (?, ?, ?)

PostModel.selectAllByTopicByLimit = SELECT p.post_id, topic_id, forum_id, p.user_id, post_time, poster_ip, enable_bbcode, \
	enable_html, enable_smilies, enable_sig, post_edit_time, post_edit_count, status, pt.post_subject, pt.post_text, username \
	FROM jforum_posts p, jforum_posts_text pt, jforum_users u \
	WHERE p.post_id = pt.post_id \
	AND topic_id = ? \
	AND p.user_id = u.user_id \
	ORDER BY post_time ASC \
	LIMIT ?, ?
	
PostModel.setForumByTopic = UPDATE jforum_posts SET forum_id = ? WHERE topic_id = ?
PostModel.deleteByTopic = SELECT post_id, user_id FROM jforum_posts WHERE topic_id = ?

# #############
# ForumModel
# #############
ForumModel.selectById = SELECT f.*, COUNT(p.post_id) AS total_posts \
	FROM jforum_forums f \
	LEFT JOIN jforum_topics t ON t.forum_id = f.forum_id \
	LEFT JOIN jforum_posts p ON p.topic_id = t.topic_id \
	WHERE f.forum_id = ? \
	GROUP BY f.forum_id

ForumModel.selectAll = SELECT f.*, COUNT(p.post_id) AS total_posts \
	FROM jforum_forums f \
	LEFT JOIN jforum_topics t ON t.forum_id = f.forum_id \
	LEFT JOIN jforum_posts p ON p.topic_id = t.topic_id \
	GROUP BY f.forum_id

ForumModel.delete = DELETE FROM jforum_forums WHERE forum_id = ?
ForumModel.update = UPDATE jforum_forums SET categories_id = ?, forum_name = ?, forum_desc = ?, moderated = ? WHERE forum_id = ?
ForumModel.addNew = INSERT INTO jforum_forums (categories_id, forum_name, forum_desc, forum_order, moderated) VALUES (?, ?, ?, ?, ?)
ForumModel.getMaxOrder = SELECT max(forum_order) as maxOrder FROM jforum_forums
ForumModel.updateLastPost = UPDATE jforum_forums SET forum_last_post_id = ? WHERE forum_id = ?
ForumModel.incrementTotalTopics = UPDATE jforum_forums SET forum_topics = forum_topics + ? WHERE forum_id = ?
ForumModel.decrementTotalTopics = UPDATE jforum_forums SET forum_topics = forum_topics - ? WHERE forum_id = ?
ForumModel.decrementTotalPosts = UPDATE jforum_forums SET total_posts = total_posts - ? WHERE forum_id = ?
ForumModel.getTotalTopics = SELECT COUNT(topic_id) as total FROM jforum_topics WHERE forum_id = ?
ForumModel.getOrder = SELECT forum_order FROM jforum_forums WHERE forum_id = ?
ForumModel.setOrderByOrder = UPDATE jforum_forums SET forum_order = ? WHERE forum_order = ?
ForumModel.setOrderById = UPDATE jforum_forums SET forum_order = ? WHERE forum_id = ?

ForumModel.lastPostInfo = SELECT post_time, p.topic_id, t.topic_replies, post_id, u.user_id, username \
	FROM jforum_posts p, jforum_users u, jforum_topics t , jforum_forums f \
	WHERE t.forum_id = f.forum_id \
	AND t.topic_id = p.topic_id \
	AND f.forum_last_post_id = t.topic_last_post_id \
	AND t.topic_last_post_id = p.post_id \
	AND p.forum_id = ? \
	AND p.user_id = u.user_id

ForumModel.totalMessages = SELECT COUNT(1) as total_messages FROM jforum_posts
ForumModel.getMaxPostId = SELECT MAX(post_id) AS post_id FROM jforum_posts WHERE forum_id = ?
ForumModel.moveTopics = UPDATE jforum_topics SET forum_id = ? WHERE topic_id = ?

ForumModel.getUnreadForums = SELECT t.forum_id, t.topic_id, p.post_time \
	FROM jforum_topics t, jforum_posts p \
	WHERE p.post_id = t.topic_last_post_id \
	AND p.post_time > ?

# #############
# TopicModel
# #############
TopicModel.selectById = SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time \
	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
	WHERE t.topic_id = ? \
	AND t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id

TopicModel.selectAllByForumByLimit = SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time \
	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
	WHERE t.forum_id = ? \
	AND t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id \
	ORDER BY t.topic_type DESC, p2.post_time DESC, t.topic_last_post_id DESC \
	LIMIT ?, ?
	
TopicModel.selectLastN = SELECT topic_title, topic_time, topic_id, topic_type FROM jforum_topics ORDER BY topic_time DESC LIMIT ?

TopicModel.delete = DELETE FROM jforum_topics WHERE topic_id = ?
TopicModel.deletePosts = DELETE FROM jforum_posts WHERE topic_id = ?
TopicModel.incrementTotalViews = UPDATE jforum_topics SET topic_views = topic_views + 1 WHERE topic_id = ?
TopicModel.incrementTotalReplies = UPDATE jforum_topics SET topic_replies = topic_replies + 1 WHERE topic_id = ?
TopicModel.decrementTotalReplies = UPDATE jforum_topics SET topic_replies = topic_replies - 1 WHERE topic_id = ?
TopicModel.setLastPostId = UPDATE jforum_topics SET topic_last_post_id = ? WHERE topic_id = ?

TopicModel.addNew = INSERT INTO jforum_topics (forum_id, topic_title, user_id, topic_time, topic_first_post_id, topic_last_post_id, topic_type) \
	VALUES (?, ?, ?, ?, ?, ?, ?)

TopicModel.update = UPDATE jforum_topics SET topic_title = ?, topic_last_post_id = ?, topic_first_post_id = ?, topic_type = ? WHERE topic_id = ?
TopicModel.getMaxPostId = SELECT MAX(post_id) AS post_id FROM jforum_posts WHERE topic_id = ?
TopicModel.getTotalPosts = SELECT COUNT(1) AS total FROM jforum_posts WHERE topic_id = ?

TopicModel.subscribeUser = INSERT INTO jforum_topics_watch(topic_id, user_id, is_read) VALUES (?, ?, '1')
TopicModel.isUserSubscribed = SELECT user_id FROM jforum_topics_watch WHERE topic_id = ? AND user_id = ?
TopicModel.removeSubscription = DELETE FROM jforum_topics_watch WHERE topic_id = ? AND user_id = ?
TopicModel.removeSubscriptionByTopic = DELETE FROM jforum_topics_watch WHERE topic_id = ?
TopicModel.updateReadStatus = UPDATE jforum_topics_watch SET is_read = ? WHERE topic_id = ? AND user_id = ?

TopicModel.notifyUsers = SELECT u.user_id AS user_id, u.username AS username, \
	u.user_lang AS user_lang, u.user_email AS user_email \
	FROM jforum_topics_watch tw \
	INNER JOIN jforum_users u ON (tw.user_id = u.user_id) \
	WHERE tw.topic_id = ? \
	AND tw.is_read = 1 \
	AND u.user_id NOT IN ( ?, ? )
	
TopicModel.markAllAsUnread = UPDATE jforum_topics_watch SET is_read = '0' WHERE topic_id = ? AND user_id NOT IN (?, ?)
TopicModel.lockUnlock = UPDATE jforum_topics SET topic_status = ? WHERE topic_id = ?

# ############
# SearchModel
# ############
SearchModel.searchBase = SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time \
	FROM jforum_search_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2, jforum_forums f, jforum_search_results sr \
	WHERE t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id \
	AND f.forum_id = t.forum_id \
	AND t.topic_id = sr.topic_id \
	AND sr.session = ? \
	AND t.session = ? \
	:criterias: \
	ORDER BY :orderByField: :orderBy:
	
SearchModel.insertWords = INSERT INTO jforum_search_words ( word_hash, word ) VALUES (?, ?)

SearchModel.searchByWord = SELECT post_id FROM jforum_search_wordmatch wm, jforum_search_words w \
	WHERE wm.word_id = w.word_id \
	AND w.word LIKE ?
	
SearchModel.insertTopicsIds = INSERT INTO jforum_search_results ( topic_id, session, time ) SELECT DISTINCT t.topic_id, ?, NOW() FROM jforum_topics t, jforum_posts p \
	WHERE t.topic_id = p.topic_id \
	AND p.post_id IN (:posts:)
	
SearchModel.selectTopicData = INSERT INTO jforum_search_topics (topic_id, forum_id, topic_title, user_id, topic_time, \
	topic_views, topic_status, topic_replies, topic_vote, topic_type, topic_first_post_id, topic_last_post_id, moderated, session, time) \
	SELECT t.topic_id, t.forum_id, t.topic_title, t.user_id, t.topic_time, \
	t.topic_views, t.topic_status, t.topic_replies, t.topic_vote, t.topic_type, t.topic_first_post_id, t.topic_last_post_id, t.moderated, ?, NOW() \
	FROM jforum_topics t, jforum_search_results s \
	WHERE t.topic_id = s.topic_id \
	AND s.session = ?
	
SearchModel.cleanSearchResults = DELETE FROM jforum_search_results WHERE session = ? OR time < DATE_SUB(NOW(), INTERVAL 1 HOUR)
SearchModel.cleanSearchTopics = DELETE FROM jforum_search_topics WHERE session = ? OR time < DATE_SUB(NOW(), INTERVAL 1 HOUR)
	
SearchModel.searchByTime = INSERT INTO jforum_search_results (topic_id, session, time) SELECT DISTINCT t.topic_id, ?, NOW() FROM jforum_topics t, jforum_posts p \
	WHERE t.topic_id = p.topic_id \
	AND p.post_time > ?

SearchModel.associateWordToPost = INSERT INTO jforum_search_wordmatch (post_id, word_id, title_match) VALUES (?, ?, ?)

SearchModel.searchExistingWord = SELECT w.word_id, wm.post_id \
	FROM jforum_search_words w \
	LEFT JOIN jforum_search_wordmatch wm ON wm.word_id = w.word_id \
	WHERE w.word_hash = ?

# ##########
# TreeGroup
# ##########
TreeGroup.selectGroup = SELECT group_id, group_name FROM jforum_groups WHERE parent_id = ? ORDER BY group_name

# ################
# PermissionControl
# ################
PermissionControl.deleteAllUserRoles = DELETE FROM jforum_roles WHERE user_id = ?
PermissionControl.deleteAllGroupRoles = DELETE FROM jforum_roles WHERE group_id = ?
PermissionControl.deleteUserRole = DELETE from jforum_roles WHERE user_id = ? AND name = ?
PermissionControl.deleteGroupRole = DELETE FROM jforum_roles WHERE group_id = ? AND name = ?
PermissionControl.addGroupRole = INSERT INTO jforum_roles ( group_id, name, type ) VALUES (?, ?, ?)
PermissionControl.addUserRole = INSERT INTO jforum_roles ( user_id, name, type ) VALUES (?, ?, ?)
PermissionControl.addRoleValues = INSERT INTO jforum_role_values (role_id, value, type ) VALUES (?, ?, ?)

PermissionControl.loadGroupRoles = SELECT r.role_id, r.name, rv.value, rv.type AS rv_type, r.type \
	FROM jforum_roles r \
	LEFT JOIN jforum_role_values rv ON rv.role_id = r.role_id \
	WHERE r.group_id = ? \
	AND user_id = 0

PermissionControl.loadUserRoles = SELECT r.role_id, r.name, rv.value, rv.type AS rv_type, r.type \
	FROM jforum_roles r \
	LEFT JOIN jforum_role_values rv ON rv.role_id = r.role_id \
	WHERE r.user_id = ? \
	AND r.group_id = 0
	
PermissionControl.deleteAllUserRoleValuesByGroup = DELETE FROM jforum_roles \
	where role_id in (select r.role_id from jforum_role_values rv, jforum_users u, jforum_user_groups ug \
	WHERE u.user_id = ug.user_id \
	AND ug.group_id = ? \
	AND r.user_id = u.user_id \
	AND r.role_name = ? )

PermissionControl.deleteUserRoleByGroup = DELETE FROM jforum_roles \
	where user_id in (select user_id from jforum_user_groups ug where  ug.group_id = ?) \
	and name = ? \
	
PermissionControl.deleteUserRoleValuesByRoleName = DELETE FROM jforum_role_values \
	where role_id in (select r.role_id from jforum_roles r, jforum_user_groups ug \
	WHERE ug.user_id = r.user_id \
	AND ug.group_id = ? \
	AND r.name = ? )

PermissionControl.deleteUserRoleValueByGroup = DELETE FROM jforum_role_values\
	where role_id in (select r.role_id from jforum_roles r, jforum_user_groups ug \
	WHERE ug.user_id = r.user_id \
	AND ug.group_id = ? \
	AND r.name = ? ) \
	AND rv.value = ?

# #############
# TopicListing
# #############
TopicListing.selectTopicData = SELECT topic_id, topic_title, topic_views, topic_replies, topic_last_post_id, user_id FROM jforum_topics WHERE forum_id = ?

# #############
# SmiliesModel
# #############
SmiliesModel.addNew = INSERT INTO jforum_smilies ( code, url, disk_name) VALUES (?, ?, ?)
SmiliesModel.delete = DELETE FROM jforum_smilies WHERE smilie_id = ?
SmiliesModel.update = UPDATE jforum_smilies SET code = ?, url = ?, disk_name =? WHERE smilie_id = ?
SmiliesModel.selectAll = SELECT * FROM jforum_smilies
SmiliesModel.selectById = SELECT * FROM jforum_smilies WHERE smilie_id = ?

# ####################
# PrivateMessageModel
# ####################
PrivateMessageModel.add = INSERT INTO jforum_privmsgs ( privmsgs_type, privmsgs_subject, privmsgs_from_userid, \
	privmsgs_to_userid, privmsgs_date, privmsgs_enable_bbcode, privmsgs_enable_html, privmsgs_enable_smilies, \
	privmsgs_attach_sig ) \
	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )
	
PrivateMessagesModel.addText = INSERT INTO jforum_privmsgs_text ( privmsgs_id, privmsgs_text ) VALUES (?, ?)
	
PrivateMessageModel.delete = DELETE FROM jforum_privmsgs WHERE privmsgs_id = ? \
	AND ( \
	    (privmsgs_from_userid = ? AND privmsgs_type = 2) \
	    OR (privmsgs_to_userid = ? AND (privmsgs_type = 0 OR privmsgs_type = 1 OR privmsgs_type = 5)) \
	)
	
PrivateMessagesModel.deleteText = DELETE FROM jforum_privmsgs_text WHERE privmsgs_id = ?

PrivateMessageModel.baseListing = SELECT pm.privmsgs_type, pm.privmsgs_id, pm.privmsgs_date, pm.privmsgs_subject, u.user_id, u.username \
	FROM jforum_privmsgs pm, jforum_users u \
	#FILTER# \
	ORDER BY pm.privmsgs_date DESC
	
PrivateMessageModel.inbox = WHERE privmsgs_to_userid = ? \
	AND u.user_id = pm.privmsgs_from_userid \
	AND ( pm.privmsgs_type = 1 \
	OR pm.privmsgs_type = 0 \
	OR privmsgs_type = 5)
	
PrivateMessageModel.sent = WHERE privmsgs_from_userid = ? \
	AND u.user_id = pm.privmsgs_to_userid \
	AND pm.privmsgs_type = 2
	
PrivateMessageModel.updateType = UPDATE jforum_privmsgs SET privmsgs_type = ? WHERE privmsgs_id = ?

PrivateMessageModel.selectById = SELECT p.*, pt.privmsgs_text \
	FROM jforum_privmsgs p, jforum_privmsgs_text pt \
	WHERE p.privmsgs_id = pt.privmsgs_id \
	AND p.privmsgs_id = ?


# #################
# UserSessionModel
# #################
UserSessionModel.add = INSERT INTO jforum_sessions ( session_id, session_user_id, session_start ) VALUES (?, ?, ?)
UserSessionModel.update = UPDATE jforum_sessions SET session_start = ?, session_time = ?, session_id = ? WHERE session_user_id = ?
UserSessionModel.delete = DELETE FROM jforum_sessions WHERE session_user_id = ?
UserSessionModel.selectById = SELECT session_time, session_start, session_id FROM jforum_sessions WHERE session_user_id = ?
