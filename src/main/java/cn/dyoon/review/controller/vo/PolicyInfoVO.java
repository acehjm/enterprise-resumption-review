package cn.dyoon.review.controller.vo;

import cn.dyoon.review.domain.entity.PolicyDocumentDO;
import cn.dyoon.review.domain.entity.PolicyInfoDO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 政策信息
 */
@Data
public class PolicyInfoVO {
    private String id;
    private String title;
    private String desc;
    private LocalDateTime createDate;
    private LocalDateTime releaseDate;
    private Integer status;
    private List<PolicyDocument> files;

    public PolicyInfoVO(PolicyInfoDO policyInfoDO, List<PolicyDocumentDO> files) {
        this.id = policyInfoDO.getId();
        this.title = policyInfoDO.getTitle();
        this.desc = policyInfoDO.getDesc();
        this.createDate = policyInfoDO.getCreateTime();
        this.releaseDate = policyInfoDO.getReleaseTime();
        this.status = policyInfoDO.getStatus();
        this.files = files.stream()
                .map(it -> PolicyDocument.builder()
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
    public static class PolicyDocument {
        private String fileId;
        private String fileName;
        private Double fileSize;
        private String uploadUser;
        private LocalDateTime uploadTime;
    }
}
