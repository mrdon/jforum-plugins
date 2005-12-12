#Sun Dec 11 22:32:46 GMT-03:00 2005
PermissionControl.getRoleIdsByGroup=SELECT DISTINCT rv.role_id FROM jforum_role_values rv, jforum_roles r WHERE r.role_id \= rv.role_id AND r.group_id \= ?
PermissionControl.deleteAllRoleValues=DELETE jforum_role_values FROM jforum_role_values rv, jforum_roles r WHERE r.role_id \= rv.role_id AND r.group_id \= ?
PermissionControl.deleteRoleValuesByRoleId=DELETE FROM jforum_role_values WHERE role_id IN (?)
