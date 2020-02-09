package cn.dyoon.review.controller.vo;

import cn.dyoon.review.domain.entity.EnterpriseDO;
import cn.dyoon.review.domain.entity.ReworkDocumentDO;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 企业信息
 */
@Data
public class EnterpriseInfoVO {
    private String id;
    private String name;
    private String unifiedSocialCreditCode;
    private Integer type;
    private Integer scaleType;
    private Integer resumptionType;
    private Integer industryType;
    private Integer employeeNum;
    private Integer street;
    private String transactorName;
    private String phone;
    private LocalDateTime applyTime;
    private LocalDateTime updateTime;
    private LocalDate resumptionDate;

    private Review review;
    private List<ReworkDocument> files;

    public EnterpriseInfoVO() {
    }

    public EnterpriseInfoVO(EnterpriseDO enterpriseDO, List<ReworkDocumentDO> files) {
        this.id = enterpriseDO.getId();
        this.name = enterpriseDO.getName();
        this.unifiedSocialCreditCode = enterpriseDO.getUnifiedSocialCreditCode();
        this.type = enterpriseDO.getType();
        this.scaleType = enterpriseDO.getScaleType();
        this.resumptionType = enterpriseDO.getResumptionType();
        this.industryType = enterpriseDO.getIndustryType();
        this.employeeNum = enterpriseDO.getEmployeeNum();
        this.street = enterpriseDO.getStreet();
        this.transactorName = enterpriseDO.getTransactorName();
        this.phone = enterpriseDO.getPhone();
        this.applyTime = enterpriseDO.getApplyTime();
        this.updateTime = enterpriseDO.getUpdateTime();
        this.resumptionDate = enterpriseDO.getResumptionDate();
        this.review = Review.builder()
                .reviewStatus(enterpriseDO.getReviewStatus())
                .reviewUser(enterpriseDO.getReviewUser())
                .reviewTime(enterpriseDO.getReviewTime())
                .reviewResult(enterpriseDO.getReviewResult())
                .build();
        this.files = files.stream()
                .map(it -> ReworkDocument.builder()
                        .fileId(it.getId())
                        .fileName(it.getFileName())
                        .fileSize(it.getFileSize())
                        .uploadUser(it.getUploadUserName())
                        .uploadTime(it.getCreateTime())
                        .build())
                .collect(Collectors.toList());
    }

    @Data
    @Builder
    public static class Review {
        private Integer reviewStatus;
        private String reviewUser;
        private LocalDateTime reviewTime;
        private String reviewResult;
    }

    @Data
    @Builder
    public static class ReworkDocument {
        private String fileId;
        private String fileName;
        private Double fileSize;
        private String uploadUser;
        private LocalDateTime uploadTime;
    }
}
