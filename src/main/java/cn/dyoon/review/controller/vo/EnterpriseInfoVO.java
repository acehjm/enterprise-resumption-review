package cn.dyoon.review.controller.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 企业信息
 */
@Data
public class EnterpriseInfoVO {
    private String id;
    private String name;
    private String unifiedSocialCreditCode;
    private Integer type;
    private Integer street;
    private String transactorName;
    private String phone;
    private Review review;
    private List<File> files;

    @Data
    public static class Review {
        private Integer reviewStatus;
        private String reviewUser;
        private LocalDateTime reviewTime;
        private String reviewResult;
    }

    @Data
    public static class File {
        private String fileId;
        private String fileName;
        private Integer fileSize;
        private String uploadUser;
        private LocalDateTime uploadTime;
    }

}
