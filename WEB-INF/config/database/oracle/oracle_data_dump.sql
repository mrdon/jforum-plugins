-- 
--  General Group
-- 
INSERT INTO jforum_groups ( group_id, group_name, group_description ) VALUES (jforum_groups_seq.nextval, 'General', 'General Users');

INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_administration', 0);
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_moderation', 0);
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_moderation_post_remove', 0);
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_moderation_post_edit', 0);
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_moderation_topic_move', 0);
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_moderation_topic_lockUnlock', 0);

--
--  Users from General Group
-- 
INSERT INTO jforum_users ( user_id, username, user_password, user_regdate ) VALUES ( jforum_users_seq.nextval, 'Anonymous', 'nopass', SYSDATE);
INSERT INTO jforum_user_groups (group_id, user_id) VALUES (jforum_groups_seq.currval, jforum_users_seq.currval);

-- 
--  Admin Group
-- 
INSERT INTO jforum_groups ( group_id, group_name, group_description ) VALUES (jforum_groups_seq.nextval, 'Administration', 'Admin Users');
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_administration', 1);
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_moderation', 1);
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_moderation_post_remove', 1);
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_moderation_post_edit', 1);
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_moderation_topic_move', 1);
INSERT INTO jforum_roles (role_id, group_id, name, role_type) VALUES (jforum_roles_seq.nextval, jforum_groups_seq.currval, 'perm_moderation_topic_lockUnlock', 1);

-- 
--  Users from Admin Group
-- 
INSERT INTO jforum_users ( user_id, username, user_password, user_regdate, user_posts ) VALUES (jforum_users_seq.nextval, 'Admin', '21232f297a57a5a743894a0e4a801fc3', SYSDATE, 1);
INSERT INTO jforum_user_groups (group_id, user_id) VALUES (jforum_groups_seq.currval, jforum_users_seq.currval );

-- 
--  Smilies
-- 
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':)','<img src=\"#CONTEXT#/images/smilies/3b63d1616c5dfcf29f8a7a031aaa7cad.gif\" border=\"0\">','3b63d1616c5dfcf29f8a7a031aaa7cad.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':-)','<img src=\"#CONTEXT#/images/smilies/3b63d1616c5dfcf29f8a7a031aaa7cad.gif\" border=\"0\">','3b63d1616c5dfcf29f8a7a031aaa7cad.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':D','<img src=\"#CONTEXT#/images/smilies/283a16da79f3aa23fe1025c96295f04f.gif\" border=\"0\">','283a16da79f3aa23fe1025c96295f04f.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':-D','<img src=\"#CONTEXT#/images/smilies/283a16da79f3aa23fe1025c96295f04f.gif\" border=\"0\">','283a16da79f3aa23fe1025c96295f04f.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':(','<img src=\"#CONTEXT#/images/smilies/9d71f0541cff0a302a0309c5079e8dee.gif\" border=\"0\">','9d71f0541cff0a302a0309c5079e8dee.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':-(','<img src=\"#CONTEXT#/images/smilies/9d71f0541cff0a302a0309c5079e8dee.gif\" border=\"0\">','9d71f0541cff0a302a0309c5079e8dee.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':-o','<img src=\"#CONTEXT#/images/smilies/47941865eb7bbc2a777305b46cc059a2.gif\" border=\"0\">','47941865eb7bbc2a777305b46cc059a2.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':shock:','<img src=\"#CONTEXT#/images/smilies/385970365b8ed7503b4294502a458efa.gif\" border=\"0\">','385970365b8ed7503b4294502a458efa.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':?:','<img src=\"#CONTEXT#/images/smilies/136dd33cba83140c7ce38db096d05aed.gif\" border=\"0\">','136dd33cba83140c7ce38db096d05aed.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, '8)','<img src=\"#CONTEXT#/images/smilies/b2eb59423fbf5fa39342041237025880.gif\" border=\"0\">','b2eb59423fbf5fa39342041237025880.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':lol:','<img src=\"#CONTEXT#/images/smilies/97ada74b88049a6d50a6ed40898a03d7.gif\" border=\"0\">','97ada74b88049a6d50a6ed40898a03d7.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':x','<img src=\"#CONTEXT#/images/smilies/1069449046bcd664c21db15b1dfedaee.gif\" border=\"0\">','1069449046bcd664c21db15b1dfedaee.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':P','<img src=\"#CONTEXT#/images/smilies/69934afc394145350659cd7add244ca9.gif\" border=\"0\">','69934afc394145350659cd7add244ca9.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':-P','<img src=\"#CONTEXT#/images/smilies/69934afc394145350659cd7add244ca9.gif\" border=\"0\">','69934afc394145350659cd7add244ca9.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':oops:','<img src=\"#CONTEXT#/images/smilies/499fd50bc713bfcdf2ab5a23c00c2d62.gif\" border=\"0\">','499fd50bc713bfcdf2ab5a23c00c2d62.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':cry:','<img src=\"#CONTEXT#/images/smilies/c30b4198e0907b23b8246bdd52aa1c3c.gif\" border=\"0\">','c30b4198e0907b23b8246bdd52aa1c3c.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':evil:','<img src=\"#CONTEXT#/images/smilies/2e207fad049d4d292f60607f80f05768.gif\" border=\"0\">','2e207fad049d4d292f60607f80f05768.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':twisted:','<img src=\"#CONTEXT#/images/smilies/908627bbe5e9f6a080977db8c365caff.gif\" border=\"0\">','908627bbe5e9f6a080977db8c365caff.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':roll:','<img src=\"#CONTEXT#/images/smilies/2786c5c8e1a8be796fb2f726cca5a0fe.gif\" border=\"0\">','2786c5c8e1a8be796fb2f726cca5a0fe.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':wink:','<img src=\"#CONTEXT#/images/smilies/8a80c6485cd926be453217d59a84a888.gif\" border=\"0\">','8a80c6485cd926be453217d59a84a888.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ';)','<img src=\"#CONTEXT#/images/smilies/8a80c6485cd926be453217d59a84a888.gif\" border=\"0\">','8a80c6485cd926be453217d59a84a888.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ';-)','<img src=\"#CONTEXT#/images/smilies/8a80c6485cd926be453217d59a84a888.gif\" border=\"0\">','8a80c6485cd926be453217d59a84a888.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':!:','<img src=\"#CONTEXT#/images/smilies/9293feeb0183c67ea1ea8c52f0dbaf8c.gif\" border=\"0\">','9293feeb0183c67ea1ea8c52f0dbaf8c.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':?','<img src=\"#CONTEXT#/images/smilies/0a4d7238daa496a758252d0a2b1a1384.gif\" border=\"0\">','0a4d7238daa496a758252d0a2b1a1384.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':idea:','<img src=\"#CONTEXT#/images/smilies/8f7fb9dd46fb8ef86f81154a4feaada9.gif\" border=\"0\">','8f7fb9dd46fb8ef86f81154a4feaada9.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':arrow:','<img src=\"#CONTEXT#/images/smilies/d6741711aa045b812616853b5507fd2a.gif\" border=\"0\">','d6741711aa045b812616853b5507fd2a.gif');
INSERT INTO jforum_smilies VALUES ( jforum_smilies_seq.nextval, ':mrgreen:','<img src=\"#CONTEXT#/images/smilies/ed515dbff23a0ee3241dcc0a601c9ed6.gif\" border=\"0\">','ed515dbff23a0ee3241dcc0a601c9ed6.gif');
