USE bling_user;

INSERT INTO sys_user (nickname, phone, email, avatar, status)
VALUES ('张三', '13800138000', 'zhangsan@example.com', 'http://example.com/avatar1.png', 1),
       ('李四', '13900139000', 'lisi@example.com', 'http://example.com/avatar2.png', 1);

USE bling_auth;

INSERT INTO sys_user_auth (user_id, identity_type, identifier, credential)
VALUES (1, 'password', 'zhangsan', '123456'),  -- 假设密码明文123456，实际要存加密密码
       (2, 'password', 'lisi', '123456');

USE bling_permission;

-- 插入角色
INSERT INTO sys_role (name, code, description) VALUES
('管理员', 'admin', '系统管理员，拥有所有权限'),
('普通用户', 'user', '普通用户，权限受限');

-- 插入权限（示例）
INSERT INTO sys_permission (name, code, type, url, method, parent_id) VALUES
('查看用户', 'user:view', 'api', '/api/users', 'GET', 0),
('编辑用户', 'user:edit', 'api', '/api/users', 'POST', 0);

-- 给用户分配角色
INSERT INTO sys_user_role (user_id, role_id) VALUES
(1, 1), -- 用户1是管理员
(2, 2); -- 用户2是普通用户

-- 给角色分配权限
INSERT INTO sys_role_permission (role_id, permission_id) VALUES
(1, 1), -- 管理员有查看用户权限
(1, 2), -- 管理员有编辑用户权限
(2, 1); -- 普通用户只有查看用户权限
