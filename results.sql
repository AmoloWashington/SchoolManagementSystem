/*
 Navicat Premium Data Transfer

 Source Server         : Test
 Source Server Type    : MySQL
 Source Server Version : 50145 (5.1.45-community)
 Source Host           : localhost:3306
 Source Schema         : results

 Target Server Type    : MySQL
 Target Server Version : 50145 (5.1.45-community)
 File Encoding         : 65001

 Date: 16/06/2023 17:42:16
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for county
-- ----------------------------
DROP TABLE IF EXISTS `county`;
CREATE TABLE `county`  (
  `county_id` int(11) NOT NULL AUTO_INCREMENT,
  `county_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`county_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of county
-- ----------------------------
INSERT INTO `county` VALUES (1, 'mombasa');
INSERT INTO `county` VALUES (2, 'kilifi');
INSERT INTO `county` VALUES (3, 'kiambu');

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login`  (
  `username` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('Amolo ', 'amolo');
INSERT INTO `login` VALUES ('Gg', 'nicholus');
INSERT INTO `login` VALUES ('Ian', 'dudu');
INSERT INTO `login` VALUES ('Japheth', 'wahura');

-- ----------------------------
-- Table structure for members
-- ----------------------------
DROP TABLE IF EXISTS `members`;
CREATE TABLE `members`  (
  `regno` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `name` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`regno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of members
-- ----------------------------
INSERT INTO `members` VALUES ('COM/B/01-00123/2021', 'IAN NTHULI');
INSERT INTO `members` VALUES ('COM/B/01-00139/2021', 'NICHOLUS GATHIRWA');
INSERT INTO `members` VALUES ('COM/B/01-00156/2021', 'MIKE KIMUTAI');
INSERT INTO `members` VALUES ('COM/B/01-55793/2021', 'DOMINIC MUTOBELA');
INSERT INTO `members` VALUES ('COM/B/01-57350/2021', 'JAPHETH WAKHURA');
INSERT INTO `members` VALUES ('SIT/B/01-02299/2021', 'NEMROD SHIHEMI');
INSERT INTO `members` VALUES ('SIT/B/01-02315/2021', 'AMOLO WASHINGTON');

-- ----------------------------
-- Table structure for programme
-- ----------------------------
DROP TABLE IF EXISTS `programme`;
CREATE TABLE `programme`  (
  `prog_id` int(11) NOT NULL AUTO_INCREMENT,
  `prog_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`prog_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 226 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of programme
-- ----------------------------
INSERT INTO `programme` VALUES (3, 'eds');
INSERT INTO `programme` VALUES (221, 'bcs');
INSERT INTO `programme` VALUES (222, 'bit');
INSERT INTO `programme` VALUES (223, 'sit');
INSERT INTO `programme` VALUES (224, 'sik');
INSERT INTO `programme` VALUES (225, 'ets');

-- ----------------------------
-- Table structure for studentdetails
-- ----------------------------
DROP TABLE IF EXISTS `studentdetails`;
CREATE TABLE `studentdetails`  (
  `regno` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `lname` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `program_id` int(11) NULL DEFAULT NULL,
  `county_id` int(11) NULL DEFAULT NULL,
  `gender` enum('Male','Female') CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `prog` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `countyname` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `pnumber` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`regno`) USING BTREE,
  INDEX `studentdetails_ibfk_1`(`program_id`) USING BTREE,
  INDEX `studentdetails_ibfk_2`(`county_id`) USING BTREE,
  CONSTRAINT `studentdetails_ibfk_1` FOREIGN KEY (`program_id`) REFERENCES `programme` (`prog_id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `studentdetails_ibfk_2` FOREIGN KEY (`county_id`) REFERENCES `county` (`county_id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE = InnoDB AUTO_INCREMENT = 9877667 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of studentdetails
-- ----------------------------
INSERT INTO `studentdetails` VALUES (9877666, 'hvirjobs', 'rihhfjij', NULL, NULL, 'Male', '221', 'mombasa', '9irg9i');

SET FOREIGN_KEY_CHECKS = 1;
