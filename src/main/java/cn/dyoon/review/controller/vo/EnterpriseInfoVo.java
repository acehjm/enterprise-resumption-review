package cn.dyoon.review.controller.vo;

import cn.dyoon.review.common.enums.EnterpriseType;
import cn.dyoon.review.common.enums.ReviewStatus;
import cn.dyoon.review.common.enums.StreetType;
import lombok.Data;

import java.util.Date;

/**
 * 企业信息
 */
@Data
public class EnterpriseInfoVo {
    private Integer id;
    private String name;
    private EnterpriseType type;
    private String unifiedSocialCreditCode;
    private StreetType streetId;
    private String transactorName;
    private String phone;
    private Date applyDate;
    private ReviewStatus reviewStatus = ReviewStatus.NOT_STARTED;
    private String reviewUser;
    private Date reviewTime;

}
