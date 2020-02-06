package cn.dyoon.review.controller.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 政策附件文档
 */
@Data
public class PolicyDocumentVO {
    private Integer id;
    private String fileName;
    private Double fileSize;
    private String uploadUserName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String path;
    private Integer policyTag;
}
