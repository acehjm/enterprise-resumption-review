
-- 更新字段
ALTER TABLE enterprise_resumption_review.enterprise_info ADD scale_type TINYINT NOT NULL;
ALTER TABLE enterprise_resumption_review.enterprise_info MODIFY COLUMN name varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '企业名称';
ALTER TABLE enterprise_resumption_review.enterprise_info MODIFY COLUMN `type` tinyint(1) NOT NULL COMMENT '企业类型';
ALTER TABLE enterprise_resumption_review.enterprise_info MODIFY COLUMN unified_social_credit_code varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '统一社会信用代码';
ALTER TABLE enterprise_resumption_review.enterprise_info MODIFY COLUMN street tinyint(1) NOT NULL COMMENT '所属街道/镇';
ALTER TABLE enterprise_resumption_review.enterprise_info MODIFY COLUMN transactor_name varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请人姓名';
ALTER TABLE enterprise_resumption_review.enterprise_info MODIFY COLUMN phone varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系电话';
ALTER TABLE enterprise_resumption_review.enterprise_info MODIFY COLUMN review_result VARCHAR(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '审核结果';;
