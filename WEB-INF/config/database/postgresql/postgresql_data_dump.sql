-- 
--  General Group
-- 
INSERT INTO jforum_groups ( group_name, group_description ) VALUES ('General', 'General Users');

INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_administration', 0);
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_moderation', 0);
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_moderation_post_remove', 0);
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_moderation_post_edit', 0);
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_moderation_topic_move', 0);
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_moderation_topic_lockUnlock', 0);

-- 
--  Users from General Group
-- 
INSERT INTO jforum_users ( username, user_password ) VALUES ('Anonymous', 'nopass');
INSERT INTO jforum_user_groups (group_id, user_id) VALUES ((SELECT CURRVAL('jforum_groups_seq')), (SELECT CURRVAL('jforum_users_seq')));

-- 
--  Admin Group
-- 
INSERT INTO jforum_groups ( group_name, group_description ) VALUES ('Administration', 'Admin Users');
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_administration', 1);
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_moderation', 1);
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_moderation_post_remove', 1);
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_moderation_post_edit', 1);
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_moderation_topic_move', 1);
INSERT INTO jforum_roles (group_id, name, type) VALUES ((SELECT CURRVAL('jforum_groups_seq')), 'perm_moderation_topic_lockUnlock', 1);

-- 
--  Users from Admin Group
-- 
INSERT INTO jforum_users ( username, user_password ) VALUES ('Admin', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO jforum_user_groups (group_id, user_id) VALUES ((SELECT CURRVAL('jforum_groups_seq')), (SELECT CURRVAL('jforum_users_seq')));

-- 
--  Moderation Group
-- 
INSERT INTO jforum_groups ( group_name, group_description ) VALUES ('Moderation', 'Moderation Users');

-- 
--  Smilies
-- 
INSERT INTO jforum_smilies VALUES (1,':)','<img src=\"#CONTEXT#/images/smilies/3b63d1616c5dfcf29f8a7a031aaa7cad.gif\" border=\"0\">','3b63d1616c5dfcf29f8a7a031aaa7cad.gif');
INSERT INTO jforum_smilies VALUES (2,':-)','<img src=\"#CONTEXT#/images/smilies/3b63d1616c5dfcf29f8a7a031aaa7cad.gif\" border=\"0\">','3b63d1616c5dfcf29f8a7a031aaa7cad.gif');
INSERT INTO jforum_smilies VALUES (3,':D','<img src=\"#CONTEXT#/images/smilies/283a16da79f3aa23fe1025c96295f04f.gif\" border=\"0\">','283a16da79f3aa23fe1025c96295f04f.gif');
INSERT INTO jforum_smilies VALUES (4,':-D','<img src=\"#CONTEXT#/images/smilies/283a16da79f3aa23fe1025c96295f04f.gif\" border=\"0\">','283a16da79f3aa23fe1025c96295f04f.gif');
INSERT INTO jforum_smilies VALUES (5,':(','<img src=\"#CONTEXT#/images/smilies/9d71f0541cff0a302a0309c5079e8dee.gif\" border=\"0\">','9d71f0541cff0a302a0309c5079e8dee.gif');
INSERT INTO jforum_smilies VALUES (6,':-(','<img src=\"#CONTEXT#/images/smilies/9d71f0541cff0a302a0309c5079e8dee.gif\" border=\"0\">','9d71f0541cff0a302a0309c5079e8dee.gif');
INSERT INTO jforum_smilies VALUES (7,':-o','<img src=\"#CONTEXT#/images/smilies/47941865eb7bbc2a777305b46cc059a2.gif\" border=\"0\">','47941865eb7bbc2a777305b46cc059a2.gif');
INSERT INTO jforum_smilies VALUES (8,':shock:','<img src=\"#CONTEXT#/images/smilies/385970365b8ed7503b4294502a458efa.gif\" border=\"0\">','385970365b8ed7503b4294502a458efa.gif');
INSERT INTO jforum_smilies VALUES (9,':?','<img src=\"#CONTEXT#/images/smilies/136dd33cba83140c7ce38db096d05aed.gif\" border=\"0\">','136dd33cba83140c7ce38db096d05aed.gif');
INSERT INTO jforum_smilies VALUES (10,'8)','<img src=\"#CONTEXT#/images/smilies/b2eb59423fbf5fa39342041237025880.gif\" border=\"0\">','b2eb59423fbf5fa39342041237025880.gif');
INSERT INTO jforum_smilies VALUES (11,':lol:','<img src=\"#CONTEXT#/images/smilies/97ada74b88049a6d50a6ed40898a03d7.gif\" border=\"0\">','97ada74b88049a6d50a6ed40898a03d7.gif');
INSERT INTO jforum_smilies VALUES (12,':x','<img src=\"#CONTEXT#/images/smilies/1069449046bcd664c21db15b1dfedaee.gif\" border=\"0\">','1069449046bcd664c21db15b1dfedaee.gif');
INSERT INTO jforum_smilies VALUES (13,':P','<img src=\"#CONTEXT#/images/smilies/69934afc394145350659cd7add244ca9.gif\" border=\"0\">','69934afc394145350659cd7add244ca9.gif');
INSERT INTO jforum_smilies VALUES (14,':-P','<img src=\"#CONTEXT#/images/smilies/69934afc394145350659cd7add244ca9.gif\" border=\"0\">','69934afc394145350659cd7add244ca9.gif');
INSERT INTO jforum_smilies VALUES (15,':oops:','<img src=\"#CONTEXT#/images/smilies/499fd50bc713bfcdf2ab5a23c00c2d62.gif\" border=\"0\">','499fd50bc713bfcdf2ab5a23c00c2d62.gif');
INSERT INTO jforum_smilies VALUES (16,':cry:','<img src=\"#CONTEXT#/images/smilies/c30b4198e0907b23b8246bdd52aa1c3c.gif\" border=\"0\">','c30b4198e0907b23b8246bdd52aa1c3c.gif');
INSERT INTO jforum_smilies VALUES (17,':evil:','<img src=\"#CONTEXT#/images/smilies/2e207fad049d4d292f60607f80f05768.gif\" border=\"0\">','2e207fad049d4d292f60607f80f05768.gif');
INSERT INTO jforum_smilies VALUES (18,':twisted:','<img src=\"#CONTEXT#/images/smilies/908627bbe5e9f6a080977db8c365caff.gif\" border=\"0\">','908627bbe5e9f6a080977db8c365caff.gif');
INSERT INTO jforum_smilies VALUES (19,':roll:','<img src=\"#CONTEXT#/images/smilies/2786c5c8e1a8be796fb2f726cca5a0fe.gif\" border=\"0\">','2786c5c8e1a8be796fb2f726cca5a0fe.gif');
INSERT INTO jforum_smilies VALUES (20,':wink:','<img src=\"#CONTEXT#/images/smilies/8a80c6485cd926be453217d59a84a888.gif\" border=\"0\">','8a80c6485cd926be453217d59a84a888.gif');
INSERT INTO jforum_smilies VALUES (21,';)','<img src=\"#CONTEXT#/images/smilies/8a80c6485cd926be453217d59a84a888.gif\" border=\"0\">','8a80c6485cd926be453217d59a84a888.gif');
INSERT INTO jforum_smilies VALUES (22,';-)','<img src=\"#CONTEXT#/images/smilies/8a80c6485cd926be453217d59a84a888.gif\" border=\"0\">','8a80c6485cd926be453217d59a84a888.gif');
INSERT INTO jforum_smilies VALUES (23,':!:','<img src=\"#CONTEXT#/images/smilies/9293feeb0183c67ea1ea8c52f0dbaf8c.gif\" border=\"0\">','9293feeb0183c67ea1ea8c52f0dbaf8c.gif');
INSERT INTO jforum_smilies VALUES (24,':?:','<img src=\"#CONTEXT#/images/smilies/0a4d7238daa496a758252d0a2b1a1384.gif\" border=\"0\">','0a4d7238daa496a758252d0a2b1a1384.gif');
INSERT INTO jforum_smilies VALUES (25,':idea:','<img src=\"#CONTEXT#/images/smilies/8f7fb9dd46fb8ef86f81154a4feaada9.gif\" border=\"0\">','8f7fb9dd46fb8ef86f81154a4feaada9.gif');
INSERT INTO jforum_smilies VALUES (26,':arrow:','<img src=\"#CONTEXT#/images/smilies/d6741711aa045b812616853b5507fd2a.gif\" border=\"0\">','d6741711aa045b812616853b5507fd2a.gif');
INSERT INTO jforum_smilies VALUES (31,':mrgreen:','<img src=\"#CONTEXT#/images/smilies/ed515dbff23a0ee3241dcc0a601c9ed6.gif\" border=\"0\">','ed515dbff23a0ee3241dcc0a601c9ed6.gif');