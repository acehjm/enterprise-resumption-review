SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for enterprise_info 企业信息
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_info`;
CREATE TABLE `enterprise_info`  (
  `id` varchar(32) NOT NULL COMMENT '企业ID',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '企业名称',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '企业类型',
  `scale_type` tinyint(1) NULL DEFAULT NULL COMMENT '企业规模',
  `resumption_type` tinyint(1) NULL DEFAULT NULL COMMENT '复工类型',
  `industry_type` tinyint(1) NULL DEFAULT NULL COMMENT '行业类型',
  `employee_num` int(11) NULL DEFAULT NULL COMMENT '企业人员数量',
  `unified_social_credit_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统一社会信用代码',
  `street` tinyint(1) NULL DEFAULT NULL COMMENT '所属街道',
  `transactor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '填报人姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `apply_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `review_status` tinyint(1) NULL DEFAULT NULL COMMENT '审核状态',
  `review_user` varchar(32) NULL DEFAULT NULL COMMENT '审核人',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `review_result` varchar (1000) NULL DEFAULT NULL COMMENT '审核结果',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for policy_document 政策附件文档
-- ----------------------------
DROP TABLE IF EXISTS `policy_document`;
CREATE TABLE `policy_document`  (
  `id` varchar(32) NOT NULL,
  `file_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `virtual_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '虚拟文件名称',
  `file_size` double(11, 1) NULL DEFAULT NULL COMMENT '文件大小',
  `upload_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传人姓名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文档存放位置',
  `policy_id` varchar(32) NULL DEFAULT NULL COMMENT '政策信息表ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for policy_info 政策信息
-- ----------------------------
DROP TABLE IF EXISTS `policy_info`;
CREATE TABLE `policy_info`  (
  `id` varchar(32) NOT NULL COMMENT '政策信息ID',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `desc` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `release_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态（0-未发布，1-发布）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rework_document 复工附件文档
-- ----------------------------
DROP TABLE IF EXISTS `rework_document`;
CREATE TABLE `rework_document`  (
  `id` varchar(32) NOT NULL COMMENT '文档ID',
  `file_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `virtual_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '虚拟文件名称',
  `file_size` double(11, 1) NULL DEFAULT NULL COMMENT '文件大小',
  `upload_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文档存放位置',
  `enterprise_id` varchar(32) NULL DEFAULT NULL COMMENT '企业信息表ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(32) NOT NULL COMMENT '用户ID',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `user_type` tinyint(1) NULL DEFAULT NULL COMMENT '用户类型',
  `user_subtype` tinyint(1) NULL DEFAULT NULL COMMENT '用户子类型',
  `role` tinyint(1) NULL DEFAULT NULL COMMENT '用户角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
