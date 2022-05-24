/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.73.130_8806
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.73.131:3306、192.168.73.132:3306
 Source Schema         : user_131、user_132

 Target Server Type    : MyCat
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 24/05/2022 14:22:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称',
  `province_id` int NOT NULL COMMENT '省ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
