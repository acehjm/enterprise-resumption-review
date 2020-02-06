package cn.dyoon.review.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 政策附件文档
 */
@Data
public class PolicyDocumentVo {
    private Integer id;
    private String fileName;
    private Double fileSize;
    private String uploadUserName;
    private Date createDate;
    private Date updateDate;
    private String path;
    private Integer policyTag;
}
