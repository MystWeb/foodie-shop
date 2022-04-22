/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.100.123_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.100.123:3306
 Source Schema         : foodie-shop-dev

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 22/04/2022 19:39:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for distribute_lock
-- ----------------------------
DROP TABLE IF EXISTS `distribute_lock`;
CREATE TABLE `distribute_lock`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `business_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务代码（区分不同业务使用不同锁）',
  `business_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分布式锁' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of distribute_lock
-- ----------------------------
INSERT INTO `distribute_lock` VALUES (1, 'single', '单节点');
INSERT INTO `distribute_lock` VALUES (2, 'cluster', '分布式集群');

SET FOREIGN_KEY_CHECKS = 1;
