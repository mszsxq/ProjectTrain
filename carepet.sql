/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : carepet

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2020-04-28 20:16:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `community`
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `imgjson` varchar(1024) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `city` varchar(40) DEFAULT NULL,
  `tag` varchar(20) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `pic` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of community
-- ----------------------------

-- ----------------------------
-- Table structure for `findtable`
-- ----------------------------
DROP TABLE IF EXISTS `findtable`;
CREATE TABLE `findtable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `imgjson` varchar(1024) DEFAULT NULL,
  `title` varchar(1024) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `time` varchar(1024) DEFAULT NULL,
  `city` varchar(1024) DEFAULT NULL,
  `pettype` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of findtable
-- ----------------------------
