/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : teemo

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2016-11-23 18:39:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_key` varchar(32) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `department_value` varchar(32) NOT NULL,
  `description` varchar(128) DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_department_department_key` (`department_key`) USING BTREE,
  KEY `idx_department_department_value` (`department_value`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', 'IT', null, '信息部', '负责公司基础IT运维、产品研发', '\0');
INSERT INTO `department` VALUES ('2', 'IT_DEV', '1', '研发部', '负责公司产品研发', '\0');
INSERT INTO `department` VALUES ('3', 'IT_OPT', '1', 'IT运维', '负责公司基础IT运维', '\0');

-- ----------------------------
-- Table structure for dynamic_property
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_property`;
CREATE TABLE `dynamic_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(64) NOT NULL,
  `value` varchar(128) NOT NULL,
  `author` varchar(32) DEFAULT 'anonymous',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` float NOT NULL DEFAULT '1',
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_dynamic_property_key` (`key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dynamic_property
-- ----------------------------
INSERT INTO `dynamic_property` VALUES ('1', 'System.Version', '0.0', 'beiyoufx', '2016-10-31 15:52:51', '2016-10-31 15:52:51', '1', '0');
INSERT INTO `dynamic_property` VALUES ('2', 'Static.Path', 'http://localhost:8080/teemo/static', 'beiyoufx', '2016-11-08 08:55:15', '2016-11-08 08:55:15', '1', '0');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_key` varchar(32) NOT NULL,
  `permission_value` varchar(32) NOT NULL,
  `description` varchar(128) DEFAULT NULL,
  `available` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '*', '所有', '拥有系统所有权限', '1');
INSERT INTO `permission` VALUES ('2', 'create', '创建', '拥有创建资源的权限', '1');
INSERT INTO `permission` VALUES ('3', 'delete', '删除', '拥有删除资源的权限', '1');
INSERT INTO `permission` VALUES ('4', 'update', '更新', '拥有更新资源的权限', '1');
INSERT INTO `permission` VALUES ('5', 'view', '查看', '拥有查看资源的权限', '1');
INSERT INTO `permission` VALUES ('6', 'export', '导出', '拥有导出资源的权限', '1');

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_key` varchar(128) DEFAULT '',
  `resource_value` varchar(128) NOT NULL,
  `url` varchar(256) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(256) DEFAULT NULL,
  `available` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', '', '资源', null, '0', '0/', '1');
INSERT INTO `resource` VALUES ('2', 'system', '系统管理', null, '1', '0/1/', '1');
INSERT INTO `resource` VALUES ('3', '', '用户管理', null, '2', '0/1/2/', '1');
INSERT INTO `resource` VALUES ('4', 'user', '用户列表', '/sys/user', '3', '0/1/2/3/', '1');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_key` varchar(32) NOT NULL,
  `role_value` varchar(32) NOT NULL,
  `description` varchar(128) DEFAULT NULL,
  `available` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_role_role_key` (`role_key`) USING BTREE,
  KEY `idx_role_role_value` (`role_value`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'super_admin', '超级管理员', '拥有系统所有权限', '1');
INSERT INTO `role` VALUES ('2', 'admin', '管理员', '管理员', '1');
INSERT INTO `role` VALUES ('3', 'user', '用户', '用户', '1');

-- ----------------------------
-- Table structure for role_resource_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_resource_permission`;
CREATE TABLE `role_resource_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  `permission_ids` varchar(512) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbiq54mrmnyihs93t6fbr69tls` (`role_id`),
  CONSTRAINT `FKbiq54mrmnyihs93t6fbr69tls` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_resource_permission
-- ----------------------------
INSERT INTO `role_resource_permission` VALUES ('1', '1', '4', '2,3,4,5,6');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `salt` varchar(8) NOT NULL,
  `email` varchar(32) NOT NULL,
  `mobile_phone` varchar(16) NOT NULL,
  `department_key` varchar(32) CHARACTER SET utf8mb4 DEFAULT '',
  `last_login_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `status` enum('blocked','normal') NOT NULL DEFAULT 'normal',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_email` (`email`) USING BTREE,
  UNIQUE KEY `unique_user_username` (`username`) USING BTREE,
  KEY `idx_user_mobile_phone` (`mobile_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'alibaba', '马云', '0c5b1166e768df963b8c353fd8936be1', 'mAd7IZLQ', 'mayun@alibaba.com', '18311112222', 'DEVELOPMENT', null, '2016-10-27 16:53:25', '2016-10-28 11:08:00', '\0', 'normal');
INSERT INTO `user` VALUES ('2', 'tencent', '马化腾', '123', '123', 'tencent@qq.com', '123', 'YF', null, '2016-11-05 16:31:40', '2016-11-05 16:31:40', '\0', 'normal');

-- ----------------------------
-- Table structure for user_last_online
-- ----------------------------
DROP TABLE IF EXISTS `user_last_online`;
CREATE TABLE `user_last_online` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `username` varchar(32) DEFAULT NULL,
  `uid` varchar(32) DEFAULT NULL,
  `host` varchar(32) DEFAULT NULL,
  `user_agent` varchar(200) DEFAULT NULL,
  `system_host` varchar(32) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `last_quit_time` datetime DEFAULT NULL,
  `login_count` int(11) DEFAULT NULL,
  `total_online_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_last_online_user_id` (`user_id`),
  KEY `idx_user_last_online_username` (`username`),
  KEY `idx_user_last_online_host` (`host`),
  KEY `idx_user_last_online_system_host` (`system_host`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_last_online
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
