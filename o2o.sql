/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库-3306
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : o2o

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 18/12/2019 00:05:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `permission` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '管理员', 'admin');
INSERT INTO `sys_menu` VALUES (2, '店铺修改', 'shop_update');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_mark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色英文标识',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin');
INSERT INTO `sys_role` VALUES (2, '消费者', 'customer');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(11) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(11) NULL DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 PROHIBIT：禁用   NORMAL：正常',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  `role_id` bigint(11) NOT NULL COMMENT '用户角色',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `fk_user_role`(`role_id`) USING BTREE,
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', NULL, '2019-12-17 10:01:49', 1);
INSERT INTO `sys_user` VALUES (8, '张三', 'e10adc3949ba59abbe56e057f20f883e', '1', '2019-12-17 07:18:32', NULL, 2);

-- ----------------------------
-- Table structure for tb_area
-- ----------------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area`  (
  `area_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '区域id',
  `area_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区域名称',
  `priority` int(2) NULL DEFAULT 0 COMMENT '权重，越高排名越前',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`area_id`) USING BTREE,
  UNIQUE INDEX `UK_AREA`(`area_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '区域信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_area
-- ----------------------------
INSERT INTO `tb_area` VALUES (2, '东苑', 1, NULL, NULL);
INSERT INTO `tb_area` VALUES (3, '西苑', 2, NULL, NULL);
INSERT INTO `tb_area` VALUES (4, '南苑', 3, NULL, NULL);
INSERT INTO `tb_area` VALUES (5, '西区', 0, NULL, NULL);

-- ----------------------------
-- Table structure for tb_head_line
-- ----------------------------
DROP TABLE IF EXISTS `tb_head_line`;
CREATE TABLE `tb_head_line`  (
  `head_line_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '头条id',
  `head_line_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头条名称',
  `head_line_link` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头条指向链接',
  `head_line_img` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头条图片',
  `priority` int(2) NULL DEFAULT 0 COMMENT '权重，越大排名越靠前',
  `enable_status` int(2) NULL DEFAULT 0 COMMENT '启用状态，0：禁用 1：启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`head_line_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_head_line
-- ----------------------------
INSERT INTO `tb_head_line` VALUES (11, '1', 'http://115.28.159.6/myo2o/frontend/shopdetail?shopId=20', '/upload/images/item/headtitle/2017061320315746624.jpg', 1, 1, '2017-06-13 20:31:57', '2017-06-13 20:31:57');
INSERT INTO `tb_head_line` VALUES (12, '2', 'http://115.28.159.6/myo2o/frontend/shopdetail?shopId=20', '/upload/images/item/headtitle/2017061320371786788.jpg', 2, 1, '2017-06-13 20:37:17', '2017-06-13 20:37:17');
INSERT INTO `tb_head_line` VALUES (14, '3', 'http://115.28.159.6/myo2o/frontend/shopdetail?shopId=20', '/upload/images/item/headtitle/2017061320393452772.jpg', 3, 1, '2017-06-13 20:39:34', '2017-06-13 20:39:34');
INSERT INTO `tb_head_line` VALUES (15, '4', 'http://115.28.159.6/myo2o/frontend/shopdetail?shopId=20', '/upload/images/item/headtitle/2017061320400198256.jpg', 4, 1, '2017-06-13 20:40:01', '2017-06-13 20:40:01');

-- ----------------------------
-- Table structure for tb_local_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_local_auth`;
CREATE TABLE `tb_local_auth`  (
  `local_auth_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '本地账号id',
  `person_id` int(20) NOT NULL COMMENT '用户id（用于外键关联，对应对象里复合类型）',
  `local_auth_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '本地账号名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登陆密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`local_auth_id`) USING BTREE,
  UNIQUE INDEX `uk_local_profile`(`local_auth_name`) USING BTREE,
  INDEX `fk_localauth_profile`(`person_id`) USING BTREE,
  CONSTRAINT `fk_localauth_profile` FOREIGN KEY (`person_id`) REFERENCES `tb_person` (`person_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_local_auth
-- ----------------------------
INSERT INTO `tb_local_auth` VALUES (13, 1, 'testbind', '59s99bs556bb255by262e26s206e52bs', '2017-10-16 03:52:54', '2017-10-16 04:22:06');

-- ----------------------------
-- Table structure for tb_person
-- ----------------------------
DROP TABLE IF EXISTS `tb_person`;
CREATE TABLE `tb_person`  (
  `person_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `person_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `profile_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `email` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `enable_status` int(2) NULL DEFAULT 0 COMMENT '0:禁止使用本商城，1:允许使用本商城',
  `person_type` int(2) NULL DEFAULT 1 COMMENT '1:顾客，2:店家，3:超级管理员',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`person_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '个人信息表（用户表）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_person
-- ----------------------------
INSERT INTO `tb_person` VALUES (1, '测试', 'test', 'test', '1', 1, 2, NULL, NULL);
INSERT INTO `tb_person` VALUES (8, '李翔', 'http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJmNzyG67YKicCIOXYUKHEC32ZJANTfoaRGVB1MvkW8KagcYfDOic9IicZO5Gibp5QBsLC3p2tLq22quQ/0', NULL, '1', 1, 1, '2017-10-11 04:28:41', NULL);

-- ----------------------------
-- Table structure for tb_person_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_person_info`;
CREATE TABLE `tb_person_info`  (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `profile_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT 0 COMMENT '0:禁止使用本商城，1:允许使用本商城',
  `user_type` int(2) NOT NULL DEFAULT 1 COMMENT '1:顾客，2:店家，3:超级管理员',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_edit_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_person_info
-- ----------------------------
INSERT INTO `tb_person_info` VALUES (1, '测试', 'test', 'test', '1', 1, 2, NULL, NULL);
INSERT INTO `tb_person_info` VALUES (8, '李翔', 'http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJmNzyG67YKicCIOXYUKHEC32ZJANTfoaRGVB1MvkW8KagcYfDOic9IicZO5Gibp5QBsLC3p2tLq22quQ/0', NULL, '1', 1, 1, '2017-10-11 04:28:41', NULL);

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product`  (
  `product_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `product_category_id` int(20) NOT NULL COMMENT '商品类别id（外键，复合类型）',
  `shop_id` int(20) NOT NULL DEFAULT 0 COMMENT '所属店铺（外键，复合类型）',
  `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `product_desc` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品描述',
  `product_thum` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '缩略图地址',
  `normal_price` float(10, 2) NULL DEFAULT 0.00 COMMENT '商品正常价格',
  `discount_price` float(10, 2) NULL DEFAULT 0.00 COMMENT '商品折扣价格',
  `priority` int(2) NULL DEFAULT 0 COMMENT '权重（越大排名越靠前）',
  `enable_status` int(2) NULL DEFAULT 0 COMMENT '可用状态 0不可用  1可用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `fk_product_procate`(`product_category_id`) USING BTREE,
  INDEX `fk_product_shop`(`shop_id`) USING BTREE,
  CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_product
-- ----------------------------
INSERT INTO `tb_product` VALUES (1, 3, 29, '大黄人', '我是大黄人', 'upload/images/item/shop/29/2017092601204036435.jpg', 2.00, 1.00, 100, 1, '2017-09-26 01:20:40', '2017-09-26 01:20:40');
INSERT INTO `tb_product` VALUES (2, 2, 29, '小黄人', '我是小黄人', 'upload/images/item/shop/29/2017092601212211185.jpg', 3.00, 2.00, 90, 1, '2017-09-26 01:21:22', '2017-09-26 01:21:22');
INSERT INTO `tb_product` VALUES (3, 3, 29, '暴漫人', '开心了', 'upload/images/item/shop/29/2017092601220059819.jpg', 3.00, 2.00, 80, 1, '2017-09-26 01:22:00', '2017-09-26 01:22:00');
INSERT INTO `tb_product` VALUES (4, 3, 29, '宇宙第一', '宇宙无敌', 'upload/images/item/shop/29/2017092601224389939.jpg', 5.00, 2.00, 70, 1, '2017-09-26 01:22:43', '2017-09-26 01:22:43');
INSERT INTO `tb_product` VALUES (5, 3, 29, '眼凸凸', '宇宙无敌', 'upload/images/item/shop/29/2017092601231570458.jpg', 3.00, 2.00, 60, 1, '2017-09-26 01:23:15', '2017-09-26 01:23:15');
INSERT INTO `tb_product` VALUES (6, 3, 29, '笑眯眯', '笑眯眯 甜蜜蜜', 'upload/images/item/shop/29/2017092601234922140.jpg', 2.00, 2.00, 50, 1, '2017-09-26 01:23:49', '2017-09-26 01:23:49');
INSERT INTO `tb_product` VALUES (7, 4, 28, '优质小黄人奶茶', '非常好喝哦', '/upload/images/item/shop/28/2017100216554368403.jpg', 6.00, 3.00, 100, 1, '2017-10-02 16:55:43', '2017-10-02 16:55:43');
INSERT INTO `tb_product` VALUES (8, 4, 28, '优质暴漫奶茶', '非常好喝哦', '/upload/images/item/shop/28/2017100216561443475.jpg', 6.00, 3.00, 100, 1, '2017-10-02 16:56:14', '2017-10-02 16:56:14');
INSERT INTO `tb_product` VALUES (9, 4, 28, '优质大白奶茶', '非常好喝哦', '/upload/images/item/shop/28/2017100216564398563.jpg', 6.00, 3.00, 90, 1, '2017-10-02 16:56:43', '2017-10-02 16:56:43');
INSERT INTO `tb_product` VALUES (10, 4, 28, '优质二维码奶茶', '非常好喝哦', '/upload/images/item/shop/28/2017100216570762900.jpg', 5.00, 3.00, 80, 1, '2017-10-02 16:57:07', '2017-10-02 16:57:07');
INSERT INTO `tb_product` VALUES (11, 6, 28, '优质二维码咖啡', '非常好喝哦', '/upload/images/item/shop/28/2017100216573090557.jpg', 8.00, 3.00, 60, 1, '2017-10-02 16:57:30', '2017-10-02 16:57:30');
INSERT INTO `tb_product` VALUES (12, 6, 28, '优质大白咖啡', '非常好喝哦', '/upload/images/item/shop/28/2017100216575922088.jpg', 8.00, 3.00, 50, 1, '2017-10-02 16:57:59', '2017-10-02 16:57:59');

-- ----------------------------
-- Table structure for tb_product_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category`  (
  `product_category_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '商品类别Id',
  `shop_id` int(20) NOT NULL DEFAULT 0 COMMENT '所属店铺id（外键，只对应id）',
  `product_category_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品类别名称',
  `priority` int(2) NULL DEFAULT 0 COMMENT '权重',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`product_category_id`) USING BTREE,
  INDEX `fk_procate_shop`(`shop_id`) USING BTREE,
  CONSTRAINT `fk_procate_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品类别' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_product_category
-- ----------------------------
INSERT INTO `tb_product_category` VALUES (1, 29, '眼镜类', 1, NULL, NULL);
INSERT INTO `tb_product_category` VALUES (2, 29, '无镜类', 2, NULL, NULL);
INSERT INTO `tb_product_category` VALUES (3, 29, '开心类', 3, NULL, NULL);
INSERT INTO `tb_product_category` VALUES (4, 28, '优质奶茶', 6, NULL, NULL);
INSERT INTO `tb_product_category` VALUES (5, 28, '劣质奶茶', 3, NULL, NULL);
INSERT INTO `tb_product_category` VALUES (6, 28, '优质咖啡', 5, NULL, NULL);
INSERT INTO `tb_product_category` VALUES (7, 28, '劣质咖啡', 2, NULL, NULL);
INSERT INTO `tb_product_category` VALUES (8, 28, '甜品小吃', 4, NULL, NULL);
INSERT INTO `tb_product_category` VALUES (9, 28, '苦品凉茶', 4, NULL, NULL);

-- ----------------------------
-- Table structure for tb_product_img
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_img`;
CREATE TABLE `tb_product_img`  (
  `product_img_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '商品图片id',
  `product_id` int(20) NOT NULL COMMENT '所属商品id（外键，只对应id）',
  `product_img_addr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片地址',
  `product_img_desc` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片描述',
  `priority` int(2) NULL DEFAULT 0 COMMENT '权重（越大排名越靠前）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`product_img_id`) USING BTREE,
  INDEX `fk_proimg_product`(`product_id`) USING BTREE,
  CONSTRAINT `fk_proimg_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_product_img
-- ----------------------------
INSERT INTO `tb_product_img` VALUES (1, 1, 'upload/images/item/shop/29/2017092601204025128.jpg', NULL, NULL, '2017-09-26 01:20:40');
INSERT INTO `tb_product_img` VALUES (2, 1, 'upload/images/item/shop/29/2017092601204051262.jpg', NULL, NULL, '2017-09-26 01:20:40');
INSERT INTO `tb_product_img` VALUES (3, 2, 'upload/images/item/shop/29/2017092601212217105.jpg', NULL, NULL, '2017-09-26 01:21:22');
INSERT INTO `tb_product_img` VALUES (4, 2, 'upload/images/item/shop/29/2017092601212268219.jpg', NULL, NULL, '2017-09-26 01:21:22');
INSERT INTO `tb_product_img` VALUES (5, 3, 'upload/images/item/shop/29/2017092601220074062.jpg', NULL, NULL, '2017-09-26 01:22:00');
INSERT INTO `tb_product_img` VALUES (6, 3, 'upload/images/item/shop/29/2017092601220019993.jpg', NULL, NULL, '2017-09-26 01:22:00');
INSERT INTO `tb_product_img` VALUES (7, 4, 'upload/images/item/shop/29/2017092601224322685.jpg', NULL, NULL, '2017-09-26 01:22:43');
INSERT INTO `tb_product_img` VALUES (8, 4, 'upload/images/item/shop/29/2017092601224353777.jpg', NULL, NULL, '2017-09-26 01:22:43');
INSERT INTO `tb_product_img` VALUES (9, 5, 'upload/images/item/shop/29/2017092601231572675.jpg', NULL, NULL, '2017-09-26 01:23:15');
INSERT INTO `tb_product_img` VALUES (10, 5, 'upload/images/item/shop/29/2017092601231516853.jpg', NULL, NULL, '2017-09-26 01:23:15');
INSERT INTO `tb_product_img` VALUES (11, 6, 'upload/images/item/shop/29/2017092601234987131.jpg', NULL, NULL, '2017-09-26 01:23:49');
INSERT INTO `tb_product_img` VALUES (12, 6, 'upload/images/item/shop/29/2017092601234984991.jpg', NULL, NULL, '2017-09-26 01:23:49');
INSERT INTO `tb_product_img` VALUES (13, 7, '/upload/images/item/shop/28/2017100216554379623.jpg', NULL, NULL, '2017-10-02 16:55:43');
INSERT INTO `tb_product_img` VALUES (14, 7, '/upload/images/item/shop/28/2017100216554382464.jpg', NULL, NULL, '2017-10-02 16:55:43');
INSERT INTO `tb_product_img` VALUES (15, 7, '/upload/images/item/shop/28/2017100216554324232.jpg', NULL, NULL, '2017-10-02 16:55:43');
INSERT INTO `tb_product_img` VALUES (16, 8, '/upload/images/item/shop/28/2017100216561440352.jpg', NULL, NULL, '2017-10-02 16:56:14');
INSERT INTO `tb_product_img` VALUES (17, 8, '/upload/images/item/shop/28/2017100216561435083.jpg', NULL, NULL, '2017-10-02 16:56:14');
INSERT INTO `tb_product_img` VALUES (18, 8, '/upload/images/item/shop/28/2017100216561472866.jpg', NULL, NULL, '2017-10-02 16:56:14');
INSERT INTO `tb_product_img` VALUES (19, 9, '/upload/images/item/shop/28/2017100216564440981.jpg', NULL, NULL, '2017-10-02 16:56:44');
INSERT INTO `tb_product_img` VALUES (20, 9, '/upload/images/item/shop/28/2017100216564491563.jpg', NULL, NULL, '2017-10-02 16:56:44');
INSERT INTO `tb_product_img` VALUES (21, 9, '/upload/images/item/shop/28/2017100216564437552.jpg', NULL, NULL, '2017-10-02 16:56:44');
INSERT INTO `tb_product_img` VALUES (22, 10, '/upload/images/item/shop/28/2017100216570748189.jpg', NULL, NULL, '2017-10-02 16:57:07');
INSERT INTO `tb_product_img` VALUES (23, 10, '/upload/images/item/shop/28/2017100216570710458.jpg', NULL, NULL, '2017-10-02 16:57:07');
INSERT INTO `tb_product_img` VALUES (24, 10, '/upload/images/item/shop/28/2017100216570779065.jpg', NULL, NULL, '2017-10-02 16:57:07');
INSERT INTO `tb_product_img` VALUES (25, 11, '/upload/images/item/shop/28/2017100216573094393.jpg', NULL, NULL, '2017-10-02 16:57:30');
INSERT INTO `tb_product_img` VALUES (26, 11, '/upload/images/item/shop/28/2017100216573050300.jpg', NULL, NULL, '2017-10-02 16:57:30');
INSERT INTO `tb_product_img` VALUES (27, 11, '/upload/images/item/shop/28/2017100216573037951.jpg', NULL, NULL, '2017-10-02 16:57:30');
INSERT INTO `tb_product_img` VALUES (28, 12, '/upload/images/item/shop/28/2017100216580055004.jpg', NULL, NULL, '2017-10-02 16:58:00');
INSERT INTO `tb_product_img` VALUES (29, 12, '/upload/images/item/shop/28/2017100216580081030.jpg', NULL, NULL, '2017-10-02 16:58:00');
INSERT INTO `tb_product_img` VALUES (30, 12, '/upload/images/item/shop/28/2017100216580022626.jpg', NULL, NULL, '2017-10-02 16:58:00');

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop`  (
  `shop_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '店铺id',
  `owner_id` int(20) NOT NULL COMMENT '店铺创建人（外键）',
  `area_id` int(20) NOT NULL COMMENT '区域（外键）',
  `shop_category_id` int(20) NOT NULL COMMENT '店铺类别（外键）',
  `shop_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺名称',
  `shop_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '店铺描述',
  `shop_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '店铺图片',
  `priority` int(3) NULL DEFAULT 0 COMMENT '权重',
  `shop_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '店铺地址',
  `shop_phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '联系电话',
  `enable_status` int(2) NULL DEFAULT 0 COMMENT '可用状态 -1不可用  0审核中  1可用',
  `advice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '建议（超管对于店铺的建议）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`shop_id`) USING BTREE,
  UNIQUE INDEX `un_owner_id`(`owner_id`) USING BTREE COMMENT '一个人只能拥有一个店铺',
  INDEX `fk_shop_area`(`area_id`) USING BTREE,
  INDEX `fk_shop_profile`(`owner_id`) USING BTREE,
  INDEX `fk_shop_shopcate`(`shop_category_id`) USING BTREE,
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person` (`person_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '店铺信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_shop
-- ----------------------------
INSERT INTO `tb_shop` VALUES (28, 1, 2, 22, '王三烧烤', '不接受预订，请直接来店里进行消费', '/upload/images/item/shop/28/2017092601041469991.png', 50, '位于东苑2号', '13810524086', 1, NULL, '2017-09-26 01:04:13', '2019-10-04 15:57:17');
INSERT INTO `tb_shop` VALUES (29, 8, 3, 22, '王三烧烤', '过来喝喝就知道啦，你是我的奶茶', '/upload/images/item/shop/29/2017092601054939287.jpg', 40, '西苑1号', '1211334565', 1, NULL, '2017-09-26 01:05:49', '2019-11-28 22:06:25');

-- ----------------------------
-- Table structure for tb_shop_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_category`;
CREATE TABLE `tb_shop_category`  (
  `shop_category_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '店铺类别id',
  `parent_id` int(20) NULL DEFAULT NULL COMMENT '上级id（外键自关联）',
  `shop_category_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '店铺类别名称',
  `shop_category_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '店铺类别描述',
  `shop_category_img` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '店铺类别图片',
  `priority` int(2) NULL DEFAULT 0 COMMENT '权重',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`shop_category_id`) USING BTREE,
  INDEX `fk_shop_category_self`(`parent_id`) USING BTREE,
  CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '店铺类别' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_shop_category
-- ----------------------------
INSERT INTO `tb_shop_category` VALUES (10, NULL, '二手市场', '二手商品交易', '/upload/images/item/shopcategory/2017061223272255687.png', 100, '2017-06-04 20:10:58', '2017-06-12 23:27:22');
INSERT INTO `tb_shop_category` VALUES (11, NULL, '美容美发', '美容美发', '/upload/images/item/shopcategory/2017061223273314635.png', 99, '2017-06-04 20:12:57', '2017-06-12 23:27:33');
INSERT INTO `tb_shop_category` VALUES (12, NULL, '美食饮品', '美食饮品', '/upload/images/item/shopcategory/2017061223274213433.png', 98, '2017-06-04 20:15:21', '2017-06-12 23:27:42');
INSERT INTO `tb_shop_category` VALUES (13, NULL, '休闲娱乐', '休闲娱乐', '/upload/images/item/shopcategory/2017061223275121460.png', 97, '2017-06-04 20:19:29', '2017-06-12 23:27:51');
INSERT INTO `tb_shop_category` VALUES (14, 10, '旧车', '旧车', '/upload/images/item/shopcategory/2017060420315183203.png', 80, '2017-06-04 20:31:51', '2017-06-04 20:31:51');
INSERT INTO `tb_shop_category` VALUES (15, 10, '二手书籍', '二手书籍', '/upload/images/item/shopcategory/2017060420322333745.png', 79, '2017-06-04 20:32:23', '2017-06-04 20:32:23');
INSERT INTO `tb_shop_category` VALUES (17, 11, '护理', '护理', '/upload/images/item/shopcategory/2017060420372391702.png', 76, '2017-06-04 20:37:23', '2017-06-04 20:37:23');
INSERT INTO `tb_shop_category` VALUES (18, 11, '理发', '理发', '/upload/images/item/shopcategory/2017060420374775350.png', 74, '2017-06-04 20:37:47', '2017-06-04 20:37:47');
INSERT INTO `tb_shop_category` VALUES (20, 12, '大排档', '大排档', '/upload/images/item/shopcategory/2017060420460491494.png', 59, '2017-06-04 20:46:04', '2017-06-04 20:46:04');
INSERT INTO `tb_shop_category` VALUES (22, 12, '奶茶店', '奶茶店', '/upload/images/item/shopcategory/2017060420464594520.png', 58, '2017-06-04 20:46:45', '2017-06-04 20:46:45');
INSERT INTO `tb_shop_category` VALUES (24, 13, '密室逃生', '密室逃生', '/upload/images/item/shopcategory/2017060420500783376.png', 56, '2017-06-04 20:50:07', '2017-06-04 21:45:53');
INSERT INTO `tb_shop_category` VALUES (25, 13, 'KTV', 'KTV', '/upload/images/item/shopcategory/2017060420505834244.png', 57, '2017-06-04 20:50:58', '2017-06-04 20:51:14');
INSERT INTO `tb_shop_category` VALUES (27, NULL, '培训教育', '培训教育', '/upload/images/item/shopcategory/2017061223280082147.png', 96, '2017-06-04 21:51:36', '2017-06-12 23:28:00');
INSERT INTO `tb_shop_category` VALUES (28, NULL, '租赁市场', '租赁市场', '/upload/images/item/shopcategory/2017061223281361578.png', 95, '2017-06-04 21:53:52', '2017-06-12 23:28:13');
INSERT INTO `tb_shop_category` VALUES (29, 27, '程序设计', '程序设计', '/upload/images/item/shopcategory/2017060421593496807.png', 50, '2017-06-04 21:59:34', '2017-06-04 21:59:34');
INSERT INTO `tb_shop_category` VALUES (30, 27, '声乐舞蹈', '声乐舞蹈', '/upload/images/item/shopcategory/2017060421595843693.png', 49, '2017-06-04 21:59:58', '2017-06-04 21:59:58');
INSERT INTO `tb_shop_category` VALUES (31, 28, '演出道具', '演出道具', '/upload/images/item/shopcategory/2017060422114076152.png', 45, '2017-06-04 22:11:40', '2017-06-04 22:11:40');
INSERT INTO `tb_shop_category` VALUES (32, 28, '交通工具', '交通工具', '/upload/images/item/shopcategory/2017060422121144586.png', 44, '2017-06-04 22:12:11', '2017-06-04 22:12:11');
INSERT INTO `tb_shop_category` VALUES (33, 12, 'test1', '', NULL, 0, NULL, NULL);
INSERT INTO `tb_shop_category` VALUES (34, 12, 'test2', '', NULL, 0, NULL, NULL);

-- ----------------------------
-- Table structure for tb_wechat_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_wechat_auth`;
CREATE TABLE `tb_wechat_auth`  (
  `wechat_auth_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '微信授权id',
  `person_id` int(20) NOT NULL COMMENT '用户id（外键关联，对应对象里复合类型）',
  `open_id` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '与微信账号关联的id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`wechat_auth_id`) USING BTREE,
  UNIQUE INDEX `open_id`(`open_id`) USING BTREE,
  INDEX `fk_wechatauth_profile`(`person_id`) USING BTREE,
  CONSTRAINT `fk_wechatauth_profile` FOREIGN KEY (`person_id`) REFERENCES `tb_person` (`person_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '微信授权表（生成后不可修改，故不需设置最后修改时间）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_wechat_auth
-- ----------------------------
INSERT INTO `tb_wechat_auth` VALUES (6, 8, 'ovLbns-gxJHqC-UTPQKvgEuENl-E', '2017-10-11 04:28:41');

SET FOREIGN_KEY_CHECKS = 1;
