/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.100.123_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.100.123:3306
 Source Schema         : db_131、db_132

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 24/05/2022 18:38:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_1
-- ----------------------------
DROP TABLE IF EXISTS `account_1`;
CREATE TABLE `account_1`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `balance` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_1
-- ----------------------------
INSERT INTO `account_1` VALUES (1, '用户A', 1000.00);

-- ----------------------------
-- Table structure for account_2
-- ----------------------------
DROP TABLE IF EXISTS `account_2`;
CREATE TABLE `account_2`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `balance` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_2
-- ----------------------------
INSERT INTO `account_2` VALUES (2, '用户B', 1000.00);

-- ----------------------------
-- Table structure for payment_msg
-- ----------------------------
DROP TABLE IF EXISTS `payment_msg`;
CREATE TABLE `payment_msg`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `status` int NOT NULL DEFAULT 0 COMMENT '0：未发送；1：发送成功；2：超过最大发送次数；',
  `failure_count` int NOT NULL DEFAULT 0 COMMENT '失败次数：最大5次',
  `create_time` datetime NOT NULL,
  `create_user` int NOT NULL,
  `update_time` datetime NOT NULL,
  `update_user` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payment_msg
-- ----------------------------

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_status` int NOT NULL DEFAULT 0 COMMENT '0：未支付；1：已支付；',
  `order_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '订单金额',
  `receive_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人',
  `receive_mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人电话',
  `create_time` datetime NOT NULL,
  `create_user` int NOT NULL,
  `update_time` datetime NOT NULL,
  `update_user` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sex` int NOT NULL,
  `age` int NOT NULL,
  `update_count` int NOT NULL,
  `version` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
