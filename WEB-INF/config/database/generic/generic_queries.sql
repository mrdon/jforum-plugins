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
CategoryModel.addNew = INSERT INTO jforum_categories (title, display_order) VALUES (?, ?)
CategoryModel.setOrderById = UPDATE jforum_categories SET display_order = ? WHERE categories_id = ?
CategoryModel.getMaxOrder = SELECT MAX(display_order) FROM jforum_categories

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
ConfigModel.insert = INSERT INTO jforum_config (config_name, config_value) VALUES (?, ?)
ConfigModel.selectById = SELECT config_name, config_value FROM jforum_config WHERE config_id = ?
ConfigModel.selectByName = SELECT config_name, config_value, config_id FROM jforum_config WHERE config_name = ?
ConfigModel.selectAll = SELECT config_name, config_value, config_id FROM jforum_config
ConfigModel.delete = DELETE FROM jforum_config WHERE config_id = ?
ConfigModel.update = UPDATE jforum_config SET config_value = ? WHERE config_name = ?

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
UserModel.selectByName = SELECT * FROM jforum_users WHERE username = ?
UserModel.addNewWithId = INSERT INTO jforum_users (username, user_password, user_email, user_regdate, user_actkey, user_id) VALUES (?, ?, ?, ?, ?, ?)

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
UserModel.writeUserActive = UPDATE jforum_users SET user_active = 1, user_actkey = NULL WHERE user_id = ?`
UserModel.updateUsername = UPDATE jforum_users SET username = ? WHERE user_id = ?
UserModel.getUsernam = SELECT username FROM jforum_users WHERE user_id = ?

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
	GROUP BY f.categories_id, f.forum_order

ForumModel.delete = DELETE FROM jforum_forums WHERE forum_id = ?
ForumModel.update = UPDATE jforum_forums SET categories_id = ?, forum_name = ?, forum_desc = ?, moderated = ? WHERE forum_id = ?
ForumModel.addNew = INSERT INTO jforum_forums (categories_id, forum_name, forum_desc, forum_order, moderated) VALUES (?, ?, ?, ?, ?)
ForumModel.updateLastPost = UPDATE jforum_forums SET forum_last_post_id = ? WHERE forum_id = ?
ForumModel.incrementTotalTopics = UPDATE jforum_forums SET forum_topics = forum_topics + ? WHERE forum_id = ?
ForumModel.decrementTotalTopics = UPDATE jforum_forums SET forum_topics = forum_topics - ? WHERE forum_id = ?
ForumModel.decrementTotalPosts = UPDATE jforum_forums SET total_posts = total_posts - ? WHERE forum_id = ?
ForumModel.getTotalTopics = SELECT COUNT(topic_id) as total FROM jforum_topics WHERE forum_id = ?
ForumModel.setOrderById = UPDATE jforum_forums SET forum_order = ? WHERE forum_id = ? 
ForumModel.getMaxOrder = SELECT MAX(forum_order) FROM jforum_forums

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
ForumModel.checkUnreadTopics = SELECT MAX(post_time), topic_id FROM jforum_posts WHERE forum_id = ? AND post_time > ? GROUP BY topic_id

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

TopicModel.deleteByForum = SELECT topic_id FROM jforum_topics where forum_id = ?

TopicModel.delete = DELETE FROM jforum_topics WHERE topic_id = ?
TopicModel.deletePosts = DELETE FROM jforum_posts WHERE topic_id = ?
TopicModel.incrementTotalViews = UPDATE jforum_topics SET topic_views = topic_views + 1 WHERE topic_id = ?
TopicModel.incrementTotalReplies = UPDATE jforum_topics SET topic_replies = topic_replies + 1 WHERE topic_id = ?
TopicModel.decrementTotalReplies = UPDATE jforum_topics SET topic_replies = topic_replies - 1 WHERE topic_id = ?
TopicModel.setLastPostId = UPDATE jforum_topics SET topic_last_post_id = ? WHERE topic_id = ?
TopicModel.setFirstPostId = UPDATE jforum_topics SET topic_first_post_id = ? WHERE topic_id = ?
TopicModel.getMinPostId = SELECT MIN(post_id) AS post_id FROM jforum_posts WHERE topic_id = ?

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

TopicModel.selectRecentTopicsByLimit = SELECT t.*, u.username AS posted_by_username, u.user_id AS posted_by_id, u2.username AS last_post_by_username, u2.user_id AS last_post_by_id, p2.post_time \
	FROM jforum_topics t, jforum_users u, jforum_posts p, jforum_posts p2, jforum_users u2 \
	WHERE t.user_id = u.user_id \
	AND p.post_id = t.topic_first_post_id \
	AND p2.post_id = t.topic_last_post_id \
	AND u2.user_id = p2.user_id \
	AND t.topic_type = 0 \
	ORDER BY p2.post_time DESC, t.topic_last_post_id DESC \
	LIMIT 0, ?

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
	AND w.word = ?
	
SearchModel.insertTopicsIds = INSERT INTO jforum_search_results ( topic_id, session, search_time ) SELECT DISTINCT t.topic_id, ?, NOW() FROM jforum_topics t, jforum_posts p \
	WHERE t.topic_id = p.topic_id \
	AND p.post_id IN (:posts:)
	
SearchModel.selectTopicData = INSERT INTO jforum_search_topics (topic_id, forum_id, topic_title, user_id, topic_time, \
	topic_views, topic_status, topic_replies, topic_vote, topic_type, topic_first_post_id, topic_last_post_id, moderated, session, search_time) \
	SELECT t.topic_id, t.forum_id, t.topic_title, t.user_id, t.topic_time, \
	t.topic_views, t.topic_status, t.topic_replies, t.topic_vote, t.topic_type, t.topic_first_post_id, t.topic_last_post_id, t.moderated, ?, NOW() \
	FROM jforum_topics t, jforum_search_results s \
	WHERE t.topic_id = s.topic_id \
	AND s.session = ?
	
SearchModel.cleanSearchResults = DELETE FROM jforum_search_results WHERE session = ? OR search_time < DATE_SUB(NOW(), INTERVAL 1 HOUR)
SearchModel.cleanSearchTopics = DELETE FROM jforum_search_topics WHERE session = ? OR search_time < DATE_SUB(NOW(), INTERVAL 1 HOUR)
	
SearchModel.searchByTime = INSERT INTO jforum_search_results (topic_id, session, search_time) SELECT DISTINCT t.topic_id, ?, NOW() FROM jforum_topics t, jforum_posts p \
	WHERE t.topic_id = p.topic_id \
	AND p.post_time > ?

SearchModel.associateWordToPost = INSERT INTO jforum_search_wordmatch (post_id, word_id, title_match) VALUES (?, ?, ?)

SearchModel.searchExistingWord = SELECT w.word_id FROM jforum_search_words w WHERE w.word_hash = ?
	
SearchModel.searchExistingAssociation = SELECT post_id FROM jforum_search_wordmatch WHERE word_id = ? AND post_id = ?

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
PermissionControl.addGroupRole = INSERT INTO jforum_roles ( group_id, name, role_type ) VALUES (?, ?, ?)
PermissionControl.addUserRole = INSERT INTO jforum_roles ( user_id, name, role_type ) VALUES (?, ?, ?)
PermissionControl.addRoleValues = INSERT INTO jforum_role_values (role_id, role_value, role_type ) VALUES (?, ?, ?)

PermissionControl.loadGroupRoles = SELECT r.role_id, r.name, rv.role_value, rv.role_type AS rv_type, r.role_type \
	FROM jforum_roles r \
	LEFT JOIN jforum_role_values rv ON rv.role_id = r.role_id \
	WHERE r.group_id = ? \
	AND user_id = 0

PermissionControl.loadUserRoles = SELECT r.role_id, r.name, rv.role_value, rv.role_type AS rv_type, r.role_type \
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
SmiliesModel.selectAll = SELECT * FROM jforum_smilies ORDER BY smilie_id
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
	    OR (privmsgs_to_userid = ? AND privmsgs_type IN(0, 1, 5)) \
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

# ###########
# KarmaModel
# ###########
KarmaModel.add = INSERT INTO jforum_karma (post_id, post_user_id, from_user_id, points, topic_id) VALUES (?, ?, ?, ?, ?)
KarmaModel.update = UPDATE jforum_karma SET points = ? WHERE karma_id = ?
KarmaModel.getUserKarma = SELECT user_karma FROM jforum_users WHERE user_id = ?
KarmaModel.updateUserKarma = UPDATE jforum_users SET user_karma = ? WHERE user_id = ?
KarmaModel.getPostKarma = SELECT SUM(points) / COUNT(post_id) points FROM jforum_karma WHERE post_id = ?
KarmaModel.userCanAddKarma = SELECT COUNT(1) FROM jforum_karma WHERE post_id = ? AND from_user_id = ?

KarmaModel.getUserKarmaPoints = SELECT SUM(points) points, COUNT(1) votes, from_user_id \
	FROM jforum_karma WHERE post_user_id = ? GROUP BY from_user_id
	
KarmaModel.getUserVotes = SELECT points, post_id FROM jforum_karma WHERE topic_id = ?

# ##############
# BookmarkModel
# ##############
BookmarkModel.add = INSERT INTO jforum_bookmarks (user_id, relation_id, relation_type, public_visible, title, description) VALUES (?, ?, ?, ?, ?, ?)
BookmarkModel.update = UPDATE jforum_bookmarks SET public_visible = ?, title = ?, description = ? WHERE bookmark_id = ?
BookmarkModel.remove = DELETE FROM jforum_bookmarks WHERE bookmark_id = ?

BookmarkModel.selectForumBookmarks = SELECT b.bookmark_id, b.user_id, b.relation_type, b.relation_id, b.public_visible, b.title, b.description, f.forum_name, f.forum_desc \
	FROM jforum_bookmarks b, jforum_forums f \
	WHERE b.relation_type = 1 \
	AND b.relation_id = f.forum_id \
	AND b.user_id = ? \
	ORDER BY f.forum_name
	
BookmarkModel.selectTopicBookmarks = SELECT b.bookmark_id, b.user_id, b.relation_type, b.relation_id, b.public_visible, b.title, b.description, t.topic_title \
	FROM jforum_bookmarks b, jforum_topics t \
	WHERE b.relation_type = 2 \
	AND b.relation_id = t.topic_id \
	AND b.user_id = ? \
	ORDER BY t.topic_title
	
BookmarkModel.selectUserBookmarks = SELECT b.bookmark_id, b.user_id, b.relation_type, b.relation_id, b.public_visible, b.title, b.description, u.username \
	FROM jforum_bookmarks b, jforum_users u \
	WHERE b.relation_type = 3 \
	AND b.relation_id = u.user_id \
	AND b.user_id = ? \
	ORDER BY u.username

BookmarkModel.selectForUpdate = SELECT bookmark_id, relation_id, public_visible, relation_type, title, description, user_id \
	FROM jforum_bookmarks WHERE relation_id = ? AND relation_type = ? AND user_id = ?
	
BookmarkModel.selectById = SELECT bookmark_id, relation_id, public_visible, title, description, user_id, relation_type \
	FROM jforum_bookmarks WHERE bookmark_id = ?
	
# ################
# AttachmentModel
# ################
AttachmentModel.addQuotaLimit = INSERT INTO jforum_quota_limit (quota_desc, quota_limit, quota_type) VALUES (?, ?, ?)
AttachmentModel.updateQuotaLimit = UPDATE jforum_quota_limit SET quota_desc = ?, quota_limit = ?, quota_type = ? WHERE quota_limit_id = ?
AttachmentModel.removeQuotaLimit = DELETE FROM jforum_quota_limit WHERE quota_limit_id = ?

AttachmentModel.selectQuotaLimit = SELECT quota_limit_id, quota_desc, quota_limit, quota_type \
	FROM jforum_quota_limit ORDER BY quota_type, quota_limit
	
AttachmentModel.addExtensionGroup = INSERT INTO jforum_extension_groups (name, allow, upload_icon, download_mode) VALUES (?, ?, ?, ?)
AttachmentModel.updateExtensionGroups = UPDATE jforum_extension_groups SET name = ?, allow = ?, upload_icon = ?, download_mode = ? \
	WHERE extension_group_id = ?
AttachmentModel.removeExtensionGroups = DELETE FROM jforum_extension_groups WHERE extension_group_id = ?
AttachmentModel.selectExtensionGroups = SELECT extension_group_id, name, allow, upload_icon, download_mode FROM jforum_extension_groups ORDER BY name

AttachmentModel.addExtension = INSERT INTO jforum_extensions (extension_group_id, comment, upload_icon, extension, allow) VALUES (?, ?, ?, ?, ?)
AttachmentModel.updateExtension = UPDATE jforum_extensions SET extension_group_id = ?, comment = ?, upload_icon = ?, extension = ?, allow = ? \
	WHERE extension_id = ?
AttachmentModel.removeExtension = DELETE FROM jforum_extensions WHERE extension_id = ?
AttachmentModel.selectExtensions = SELECT extension_id, extension_group_id, extension, comment, upload_icon, allow FROM jforum_extensions ORDER BY extension