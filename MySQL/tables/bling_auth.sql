CREATE DATABASE IF NOT EXISTS bling_auth DEFAULT CHARSET utf8mb4;

USE bling_auth;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    status TINYINT DEFAULT 1 COMMENT '0=禁用,1=启用',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);