# ################
# PermissionControl
# ################
	
PermissionControl.deleteAllUserRoleValuesByGroup = DELETE jforum_role_values \
	FROM jforum_roles r, jforum_role_values rv, jforum_users u, jforum_user_groups ug \
	WHERE r.role_id = rv.role_id \
	AND u.user_id = ug.user_id \
	AND ug.group_id = ? \
	AND r.user_id = u.user_id \
	AND r.role_name = ?

PermissionControl.deleteUserRoleByGroup = DELETE jforum_roles \
	FROM jforum_roles r, jforum_user_groups ug \
	WHERE ug.user_id = r.user_id \
	AND ug.group_id = ? \
	AND r.name = ?
	
PermissionControl.deleteUserRoleValuesByRoleName = DELETE jforum_role_values \
	FROM jforum_role_values rv, jforum_roles r, jforum_user_groups ug \
	WHERE r.role_id = rv.role_id \
	AND ug.user_id = r.user_id \
	AND ug.group_id = ? \
	AND r.name = ?

PermissionControl.deleteUserRoleValueByGroup =  DELETE jforum_role_values \
	FROM jforum_role_values rv, jforum_roles r, jforum_user_groups ug \
	WHERE r.role_id = rv.role_id \
	AND ug.user_id = r.user_id \
	AND ug.group_id = ? \
	AND r.name = ?
	
PermissionControl.deleteAllRoleValues = DELETE jforum_role_values \
	FROM jforum_role_values rv, jforum_roles r \
	WHERE r.role_id = rv.role_id \
	AND r.group_id = ?