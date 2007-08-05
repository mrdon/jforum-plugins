CREATE INDEX idx_posts_moderate ON jforum_posts(need_moderate);
ALTER TABLE jforum_topics ADD topic_moved_id INT;
ALTER TABLE jforum_topics ALTER COLUMN topic_moved_id SET DEFAULT 0;
CREATE INDEX idx_topics_moved ON jforum_topics(topic_moved_id);
ALTER TABLE jforum_users ALTER COLUMN rank_id SET DEFAULT 1;

DROP TABLE jforum_search_words;
DROP TABLE jforum_search_wordmatch;
DROP TABLE jforum_search_results;
DROP TABLE jforum_search_topics;

CREATE SEQUENCE jforum_moderation_log_seq;
CREATE TABLE jforum_moderation_log (
	log_id INT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('jforum_moderation_log_seq'),
	user_id INT NOT NULL,
	log_description TEXT NOT NULL,
	log_original_message TEXT,
	log_date timestamp NOT NULL,
	log_type INT DEFAULT 0,
	post_id INT DEFAULT 0,
	topic_id INT DEFAULT 0,
	post_user_id INT DEFAULT 0
);

CREATE INDEX idx_moderation_user ON jforum_moderation_log(user_id);
CREATE INDEX idx_moderation_pu ON jforum_moderation_log(post_user_id);