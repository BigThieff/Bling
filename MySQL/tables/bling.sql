-- =============================
-- 1. 用户服务 bling_user
-- =============================
CREATE DATABASE IF NOT EXISTS bling_user DEFAULT CHARSET utf8mb4;

USE bling_user;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nickname VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    avatar VARCHAR(255),
    status TINYINT DEFAULT 1 COMMENT '0=禁用,1=启用',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================
-- 2. 认证服务 bling_auth
-- =============================
CREATE DATABASE IF NOT EXISTS bling_auth DEFAULT CHARSET utf8mb4;

USE bling_auth;

CREATE TABLE sys_user_auth (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    identity_type VARCHAR(50) NOT NULL COMMENT '认证类型：password, github, phone',
    identifier VARCHAR(100) NOT NULL COMMENT '用户名、手机号或第三方ID',
    credential VARCHAR(255) NOT NULL COMMENT '密码或token',
    UNIQUE KEY uk_identity (identity_type, identifier)
);

-- =============================
-- 3. 权限服务 bling_permission
-- =============================
CREATE DATABASE IF NOT EXISTS bling_permission DEFAULT CHARSET utf8mb4;

USE bling_permission;

CREATE TABLE sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sys_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(20),
    -- menu / button / api
    url VARCHAR(255),
    method VARCHAR(10),
    parent_id BIGINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sys_user_role (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE sys_role_permission (
    role_id BIGINT,
    permission_id BIGINT,
    PRIMARY KEY (role_id, permission_id)
);