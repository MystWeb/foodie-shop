/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.100.123_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.100.123:3306
 Source Schema         : shading_order_1、shading_order_2

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 24/05/2022 15:56:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`
(
    `id`   int                                                           NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of area
-- ----------------------------

-- ----------------------------
-- Table structure for order_1
-- ----------------------------
DROP TABLE IF EXISTS `order_1`;
CREATE TABLE `order_1`
(
    `id`           bigint         NOT NULL,
    `order_amount` decimal(10, 2) NOT NULL,
    `order_status` int            NOT NULL,
    `user_id`      int            NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_1
-- ----------------------------

-- ----------------------------
-- Table structure for order_2
-- ----------------------------
DROP TABLE IF EXISTS `order_2`;
CREATE TABLE `order_2`
(
    `id`           bigint         NOT NULL,
    `order_amount` decimal(10, 2) NOT NULL,
    `order_status` int            NOT NULL,
    `user_id`      int            NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_2
-- ----------------------------

-- ----------------------------
-- Table structure for order_item_1
-- ----------------------------
DROP TABLE IF EXISTS `order_item_1`;
CREATE TABLE `order_item_1`
(
    `id`           int                                                           NOT NULL,
    `order_id`     int                                                           NOT NULL,
    `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `num`          int                                                           NOT NULL,
    `user_id`      int                                                           NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item_1
-- ----------------------------

-- ----------------------------
-- Table structure for order_item_2
-- ----------------------------
DROP TABLE IF EXISTS `order_item_2`;
CREATE TABLE `order_item_2`
(
    `id`           int                                                           NOT NULL,
    `order_id`     int                                                           NOT NULL,
    `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `num`          int                                                           NOT NULL,
    `user_id`      int                                                           NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item_2
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
