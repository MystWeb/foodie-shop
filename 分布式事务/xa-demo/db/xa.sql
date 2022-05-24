/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.100.123_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.100.123:3306
 Source Schema         : xa_131、xa_132

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 24/05/2022 18:04:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xa_131
-- ----------------------------
DROP TABLE IF EXISTS `xa_131`;
CREATE TABLE `xa_131`
(
    `id`   int                                                           NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xa_131
-- ----------------------------

-- ----------------------------
-- Table structure for xa_132
-- ----------------------------
DROP TABLE IF EXISTS `xa_132`;
CREATE TABLE `xa_132`
(
    `id`   int                                                           NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xa_132
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
