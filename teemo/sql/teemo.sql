/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : teemo

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-01-20 11:39:43
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', 'IT_DEPT', null, '信息部', 'Information Technology Department 负责公司基础IT运维、产品研发', '\0');
INSERT INTO `department` VALUES ('2', 'IT_RD_DEPT', '2', '研发部', 'IT Research and Develop Department 负责公司产品研发', '\0');
INSERT INTO `department` VALUES ('3', 'IT_OPT_DEPT', '3', 'IT运维', 'IT Operation Department 负责公司基础IT运维', '\0');
INSERT INTO `department` VALUES ('4', 'HR_DEPT', null, '人事部', 'Human Resource Department 负责公司人事资源管理', '\0');
INSERT INTO `department` VALUES ('5', 'MKT_DEPT', null, '市场部', 'Marketing Department 负责公司的市场调研、推广、品牌形象管理', '\0');
INSERT INTO `department` VALUES ('6', 'AD_DEPT', null, '行政部', 'Administration Department 负责公司行政相关工作', '\0');
INSERT INTO `department` VALUES ('7', 'Test', '6', '测试部门名称', '测试部门', '\0');

-- ----------------------------
-- Table structure for dynamic_property
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_property`;
CREATE TABLE `dynamic_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_key` varchar(64) NOT NULL,
  `property_value` varchar(128) NOT NULL,
  `author` varchar(32) DEFAULT 'anonymous',
  `description` varchar(128) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` float NOT NULL DEFAULT '1',
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_dynamic_property_key` (`property_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dynamic_property
-- ----------------------------
INSERT INTO `dynamic_property` VALUES ('1', 'System.Version', '0.0', 'beiyoufx', '系统版本号', '2016-10-31 15:52:51', '2017-01-06 15:32:18', '1.1', '0');
INSERT INTO `dynamic_property` VALUES ('2', 'Static.Path', 'http://localhost:8080/teemo', 'beiyoufx', '系统静态资源根地址', '2016-11-08 08:55:15', '2017-01-06 15:32:04', '1.1', '0');
INSERT INTO `dynamic_property` VALUES ('3', 'test', 'test2', 'anonymous', 'test2', '2017-01-06 14:57:23', '2017-01-06 15:29:32', '1.1', '1');
INSERT INTO `dynamic_property` VALUES ('4', 'test2k', 'test2v', 'alibaba', 'test2d', '2017-01-06 15:10:18', '2017-01-06 15:10:18', '1', '1');

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
INSERT INTO `permission` VALUES ('1', 'create', '创建', '拥有创建资源的权限', '1');
INSERT INTO `permission` VALUES ('2', 'delete', '删除', '拥有删除资源的权限', '1');
INSERT INTO `permission` VALUES ('3', 'update', '更新', '拥有更新资源的权限', '1');
INSERT INTO `permission` VALUES ('4', 'view', '查看', '拥有查看资源的权限', '1');
INSERT INTO `permission` VALUES ('5', 'export', '导出', '拥有导出资源的权限', '1');
INSERT INTO `permission` VALUES ('6', 'aduit', '审核', '拥有审核用户审批的权限', '0');

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
  `type` enum('url','menu','entity','view') DEFAULT NULL,
  `menu_icon` varchar(128) DEFAULT NULL,
  `sequence` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', null, '资源', null, null, '0/', '1', 'menu', null, null);
INSERT INTO `resource` VALUES ('2', 'sys', '系统管理', null, '1', '0/1/', '1', 'menu', 'fa fa-tv', '1');
INSERT INTO `resource` VALUES ('3', null, '用户管理', '/sys/user/main', '2', '0/1/2/', '1', 'menu', null, '1');
INSERT INTO `resource` VALUES ('4', 'user', '用户列表', null, '3', '0/1/2/3', '1', 'entity', null, null);
INSERT INTO `resource` VALUES ('5', null, '部门管理', '/sys/department/main', '2', '0/1', '1', 'menu', null, '2');
INSERT INTO `resource` VALUES ('6', 'department', '部门列表', null, '5', '0/1/2/5', '1', 'entity', null, null);
INSERT INTO `resource` VALUES ('7', null, '角色管理', '/sys/role/main', '2', '0/1', '1', 'menu', null, '3');
INSERT INTO `resource` VALUES ('8', 'role', '角色列表', null, '7', '0/1/2/7', '1', 'entity', null, null);
INSERT INTO `resource` VALUES ('9', null, '权限管理', '/sys/permission/main', '2', '0/1', '1', 'menu', null, '4');
INSERT INTO `resource` VALUES ('10', 'permission', '权限列表', null, '9', '0/1/2/9', '1', 'entity', null, null);
INSERT INTO `resource` VALUES ('11', null, '资源管理', '/sys/resource/main', '2', '0/1', '1', 'menu', null, '5');
INSERT INTO `resource` VALUES ('12', 'resource', '资源列表', null, '11', '0/1/2/11', '1', 'entity', null, null);
INSERT INTO `resource` VALUES ('13', 'auth', '用户授权', null, '2', '0/1/2', '1', 'entity', null, null);
INSERT INTO `resource` VALUES ('14', 'monitor', '系统监控', null, '1', '0/1', '1', 'menu', 'fa fa-area-chart', '2');
INSERT INTO `resource` VALUES ('15', null, '数据库', '/druid', '14', '0/1/14', '1', 'menu', null, '1');
INSERT INTO `resource` VALUES ('16', 'druid', 'Druid', null, '15', '0/1/14/15', '1', 'view', null, null);
INSERT INTO `resource` VALUES ('17', 'center', '个人中心', null, '1', '0/1', '1', 'menu', 'fa fa-user', '3');
INSERT INTO `resource` VALUES ('18', 'profile', '个人资料', '/sys/user/profile', '17', '0/1/17', '1', 'menu', null, '1');
INSERT INTO `resource` VALUES ('24', '', '动态属性', '/sys/property/main', '2', null, '1', 'menu', '', null);
INSERT INTO `resource` VALUES ('25', 'dynamicProperty', '属性列表', '', '24', null, '1', 'entity', '', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'super_admin', '超级管理员', '拥有系统所有权限', '1');
INSERT INTO `role` VALUES ('3', 'user', '用户', '用户', '1');
INSERT INTO `role` VALUES ('4', 'admin', '管理员', '拥有管理员权限', '1');
INSERT INTO `role` VALUES ('5', 'guest', '游客', '拥有游客权限', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_resource_permission
-- ----------------------------
INSERT INTO `role_resource_permission` VALUES ('8', '4', '6', '1,3,4');
INSERT INTO `role_resource_permission` VALUES ('9', '4', '10', '4');
INSERT INTO `role_resource_permission` VALUES ('10', '4', '8', '1,3,4');
INSERT INTO `role_resource_permission` VALUES ('11', '4', '12', '1,3,4');
INSERT INTO `role_resource_permission` VALUES ('12', '4', '16', '4');
INSERT INTO `role_resource_permission` VALUES ('13', '4', '13', '1,3,4');
INSERT INTO `role_resource_permission` VALUES ('14', '4', '4', '1,3,4');
INSERT INTO `role_resource_permission` VALUES ('21', '5', '6', '4');
INSERT INTO `role_resource_permission` VALUES ('22', '5', '4', '4');
INSERT INTO `role_resource_permission` VALUES ('23', '3', '4', '4');
INSERT INTO `role_resource_permission` VALUES ('24', '3', '8', '4');
INSERT INTO `role_resource_permission` VALUES ('25', '3', '6', '4');
INSERT INTO `role_resource_permission` VALUES ('26', '3', '13', '4');
INSERT INTO `role_resource_permission` VALUES ('35', '1', '4', '1,2,3,4,5');
INSERT INTO `role_resource_permission` VALUES ('36', '1', '12', '1,2,3,4,5');
INSERT INTO `role_resource_permission` VALUES ('37', '1', '25', '1,2,3,4,5');
INSERT INTO `role_resource_permission` VALUES ('38', '1', '8', '1,2,3,4,5');
INSERT INTO `role_resource_permission` VALUES ('39', '1', '6', '1,2,3,4,5');
INSERT INTO `role_resource_permission` VALUES ('40', '1', '13', '1,2,3,4,5');
INSERT INTO `role_resource_permission` VALUES ('41', '1', '16', '4');
INSERT INTO `role_resource_permission` VALUES ('42', '1', '10', '1,2,3,4,5');

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
  `mobile_phone` varchar(16) DEFAULT NULL,
  `department_key` varchar(32) CHARACTER SET utf8mb4 DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `status` enum('blocked','normal') NOT NULL DEFAULT 'normal',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_email` (`email`) USING BTREE,
  UNIQUE KEY `unique_user_username` (`username`) USING BTREE,
  KEY `idx_user_mobile_phone` (`mobile_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'alibaba', '马云', '1e995a7661a320eb1d5a7fe9440c9189', 'mAd7IZLQ', 'mayun@alibaba.com', '18311113333', 'IT_DEPT', '2016-10-27 16:53:25', '2017-01-06 15:28:38', '\0', 'normal');
INSERT INTO `user` VALUES ('6', 'wukong.sun', '孙悟空', '99ee9a2061773a5e2368d87f60d68ad4', 'xjXh1msA', 'wukong.sun@qq.com', null, '', '2016-12-06 16:07:37', '2016-12-06 16:07:37', '\0', 'normal');
INSERT INTO `user` VALUES ('7', 'seng.tang', '唐僧', '1f532128669ec321c2b91b9eb92ce39f', 'g86fVPT4', 'seng.tang@qq.com', null, '', '2016-12-06 16:08:08', '2016-12-06 16:08:08', '\0', 'normal');
INSERT INTO `user` VALUES ('8', 'seng.sha', '沙僧', '53a8fc6e2a8dad3f83a8e853272c3eca', 'DSZNJQuk', 'seng.sha@qq.com', null, '', '2016-12-06 16:09:00', '2016-12-06 16:09:00', '\0', 'normal');
INSERT INTO `user` VALUES ('9', 'bajie.zhu', '猪八戒', '352c15a5b985b9ac067e05afb06e92cf', 'zwwSvDsb', 'bajie.zhu@qq.com', null, '', '2016-12-06 16:09:32', '2016-12-06 16:09:32', '\0', 'normal');
INSERT INTO `user` VALUES ('10', 'mowang.niu', '牛魔王', '241c71fbcf387adb30492b0a5f617f71', 'qsahZqLt', 'mowang.niu@qq.com', null, '', '2016-12-08 09:53:22', '2016-12-08 09:53:22', '\0', 'normal');
INSERT INTO `user` VALUES ('11', 'han.han', '韩寒', '9ad3dc46f6bf32abbbbe480346999d38', 'Ye6R8OEV', 'han.han@baidu.com', null, '', '2016-12-08 09:54:29', '2016-12-08 09:54:29', '\0', 'normal');
INSERT INTO `user` VALUES ('12', 'jingming.guo', '郭敬明', '712dd66ca6946c6af1c728930734e0f8', 'qNwrKp1d', 'jingming.guo@baidu.com', null, '', '2016-12-08 09:55:15', '2016-12-08 09:55:15', '\0', 'normal');
INSERT INTO `user` VALUES ('13', 'yanhong.li', '李彦宏', '8bf1e99db19908e5f8a0d26639283337', '5NP7NdtP', 'yanhong.li@baidu.com', null, '', '2016-12-08 09:56:34', '2016-12-08 09:56:34', '\0', 'normal');
INSERT INTO `user` VALUES ('14', 'qiaoen.chen', '陈乔恩', 'd3963f1c83165ab8b7b2cc4b2e44806e', 'gDlEGF4C', 'qiaoen.chen@teemo.com', null, '', '2016-12-08 09:58:44', '2016-12-08 09:58:44', '\0', 'normal');
INSERT INTO `user` VALUES ('15', 'bingbing.fan', '范冰冰', 'd1e143efeeb0ba9a6b96308293677a95', 'sM1mGKIt', 'bingbing.fan@teemo.com', null, '', '2016-12-08 09:59:18', '2016-12-08 09:59:18', '\0', 'normal');
INSERT INTO `user` VALUES ('16', 'he.chen', '陈赫', '8ed42c8545712ce396f7dbf0559d2db7', 'ZPx8NP9G', 'he.chen@teemo.com', null, '', '2016-12-08 09:59:43', '2016-12-08 09:59:43', '\0', 'normal');
INSERT INTO `user` VALUES ('17', 'chao.deng', '邓超', '31a5bcfe603e19446bad4c7bcb0f658d', 'AgeSGmvs', 'chao.deng@teemo.com', null, '', '2016-12-08 10:00:21', '2016-12-08 10:00:21', '\0', 'normal');
INSERT INTO `user` VALUES ('18', 'ying.yang', '杨颖', '2f2633b9082f8968dad1d0a6c44f8abc', 'i6U3l3Va', 'ying.yang@teemo.com', null, '', '2016-12-08 10:01:39', '2016-12-08 10:01:39', '\0', 'normal');
INSERT INTO `user` VALUES ('19', 'zulan.wang', '王祖蓝', '43a36415ceba280e84a9e2830d2a1692', 'JGtcghJC', 'zulan.wang@teemo.com', null, '', '2016-12-08 10:02:14', '2016-12-08 10:02:14', '\0', 'normal');
INSERT INTO `user` VALUES ('20', 'chen.li', '李晨', '4857e81810b612797c187324c9004938', 'vfYYiv3N', 'li.chen@teemo.com', null, '', '2016-12-08 10:05:53', '2016-12-08 10:05:53', '\0', 'normal');
INSERT INTO `user` VALUES ('21', 'kai.zheng', '郑恺', 'b96719cac682220d794524fd4ac6d5b4', 'NfExhL0u', 'kai.zheng@teemo.com', null, '', '2016-12-08 10:06:17', '2016-12-08 10:06:17', '\0', 'normal');
INSERT INTO `user` VALUES ('22', 'han.lu', '鹿晗', '269ac378b6f39dad7ad0db4084f9effa', '3Vi2ysn6', 'han.lu@teemo.com', null, '', '2016-12-08 10:07:27', '2016-12-08 10:07:27', '\0', 'normal');
INSERT INTO `user` VALUES ('23', 'baoqiang.wang', '王宝强', '0cefd9bb63e87361b268910f4ac5348c', 'xPpu6gbM', 'baoqiang.wang@teemo.com', null, '', '2016-12-08 10:08:01', '2016-12-08 10:08:01', '\0', 'normal');
INSERT INTO `user` VALUES ('24', 'beier.bao', '包贝尔', 'b31847d20820a7b04bfd53754c9ed08a', 'vxzSWz03', 'beier.bao@teemo.com', null, 'IT_OPT_DEPT', '2016-12-08 10:08:39', '2016-12-21 17:35:06', '\0', 'normal');
INSERT INTO `user` VALUES ('25', 'huateng.ma', '马化腾', 'd1d20e560e00aceaed1a9c8b86b81d51', 'WM4Gp8kd', 'huateng.ma@tencent.com', '13100003636', 'IT_DEPT', '2016-12-08 12:34:54', '2016-12-19 18:29:15', '\0', 'normal');

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
INSERT INTO `user_role` VALUES ('6', '1');
INSERT INTO `user_role` VALUES ('6', '3');
INSERT INTO `user_role` VALUES ('25', '3');
INSERT INTO `user_role` VALUES ('6', '5');
INSERT INTO `user_role` VALUES ('24', '5');
