package cn.dyoon.review.controller.vo;

import cn.dyoon.review.domain.entity.EnterpriseDO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * cn.dyoon.review.controller.vo
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Data
public class EnterpriseListVO {
    private String id;
    private String name;
    private String unifiedSocialCreditCode;
    private Integer type;
    private Integer industryType;
    private Integer street;
    private String transactorName;
    private String phone;
    private Integer reviewStatus;
    private LocalDateTime reviewTime;
    private LocalDate resumptionDate;
    private String address;

    public EnterpriseListVO() {
    }

    public EnterpriseListVO(EnterpriseDO enterpriseDO) {
        this.id = enterpriseDO.getId();
        this.name = enterpriseDO.getName();
        this.unifiedSocialCreditCode = enterpriseDO.getUnifiedSocialCreditCode();
        this.type = enterpriseDO.getType();
        this.industryType = enterpriseDO.getIndustryType();
        this.street = enterpriseDO.getStreet();
        this.transactorName = enterpriseDO.getTransactorName();
        this.phone = enterpriseDO.getPhone();
        this.reviewStatus = enterpriseDO.getReviewStatus();
        this.reviewTime = enterpriseDO.getReviewTime();
        this.resumptionDate = enterpriseDO.getResumptionDate();
        this.address = enterpriseDO.getAddress();
    }
}
