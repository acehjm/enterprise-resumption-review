package cn.dyoon.review.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * cn.dyoon.review.domain.entity
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Data
@TableName("enterprise_info")
public class EnterpriseDO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("name")
    private String name;

    @TableField("type")
    private Integer type;

    @TableField("unified_social_credit_code")
    private String unifiedSocialCreditCode;

    @TableField("street")
    private Integer street;

    @TableField("transactor_name")
    private String transactorName;

    @TableField("phone")
    private String phone;

    @TableField("username")
    private String username;

    @TableField("scale_type")
    private Integer scaleType;

    @TableField("industry_type")
    private Integer industryType;

    @TableField("employee_num")
    private Integer employeeNum;

    @TableField("apply_time")
    private LocalDateTime applyTime;

    @TableField("review_status")
    private Integer reviewStatus;

    @TableField("review_user")
    private String reviewUser;

    @TableField("review_time")
    private LocalDateTime reviewTime;

    @TableField("review_result")
    private String reviewResult;
}