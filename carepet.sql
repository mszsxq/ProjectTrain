/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50701
Source Host           : localhost:3306
Source Database       : carepet

Target Server Type    : MYSQL
Target Server Version : 50701
File Encoding         : 65001

<<<<<<< HEAD
Date: 2020-04-29 22:48:22
*/

SET FOREIGN_KEY_CHECKS=0;
=======
Date: 2020-04-30 18:12:45
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of community
-- ----------------------------
INSERT INTO `community` VALUES ('4', 'o', 'magazine-unlock-05-2.3.3972-_9C53CA2BB8A12AD150442022F22F5322.jpg', '#nullo', '0', '20200430 16:24:09', null, 'puppy', '0', '0');
INSERT INTO `community` VALUES ('5', 'k', 'magazine-unlock-01-2.3.3942-_C3ED9684E2A58EB8AF7C76949F38EB54.jpg', '#nullk', '0', '20200430 16:31:03', null, 'puppy', '0', '0');
INSERT INTO `community` VALUES ('7', 'b', 'magazine-unlock-05-2.3.3922-_185B1E4E5931153ED75505861D09B87C.jpg--magazine-unlock-05-2.3.3922-_60E3A60C2E7480AA56EF46DCA5EB350D.jpg', '#nullb', '0', '20200430 16:35:33', null, 'puppy', '0', '0');
INSERT INTO `community` VALUES ('8', 'bbb', 'magazine-unlock-01-2.3.3952-_735CE9B9E60CBC90D5C5C74B926E0EB2.jpg--magazine-unlock-01-2.3.3952-_AFF49FEF8028D352BC98B740D12F85E9.jpg--magazine-unlock-01-2.3.3952-_E3ECB50520FE7246F0D5CFAE4B44FBFE.jpg', '#nullbbb', '0', '20200430 17:16:57', null, 'puppy', '0', '0');
INSERT INTO `community` VALUES ('9', 'b', 'magazine-unlock-01-2.3.3922-_1D7417DF74AFAF7B33F95F9F551445EE.jpg--magazine-unlock-05-2.3.3912-_E055A6EF63D93300213EABAC01D1B9EF.jpg', '#nullu', '0', '20200430 17:25:31', null, 'puppy', '0', '0');
INSERT INTO `community` VALUES ('10', '', 'magazine-unlock-01-2.3.3962-_99E4CFF33CB8EB54CE44F39CBA85B03F.jpg', '#null', '0', '20200430 17:27:32', null, 'puppy', '0', '0');
INSERT INTO `community` VALUES ('11', '', 'magazine-unlock-05-2.3.3972-_7F9356A4B2710E3A280EB397B35D2A78.jpg', '#null', '0', '20200430 17:43:25', null, 'puppy', '0', '0');
INSERT INTO `community` VALUES ('12', '', 'magazine-unlock-01-2.3.3962-_111C9F9449A4D6A3246739B300364409.jpg', '#null', '0', '20200430 17:52:05', null, 'puppy', '0', '0');
INSERT INTO `community` VALUES ('13', '', 'magazine-unlock-05-2.3.3972-_A74BCE09EC598090963A789AF7F302F5.jpg--magazine-unlock-05-2.3.3972-_D94298A3EBAE5A99049D9CEFD23185BD.jpg--magazine-unlock-01-2.3.3972-_D696898D9749990698BAE944CD3E116B.jpg', '#null', '0', '20200430 18:05:13', null, 'puppy', '0', '0');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of findtable
-- ----------------------------
>>>>>>> dbe3f07cd1cd676e41b0c001c3c5c67d9e2eb98f
