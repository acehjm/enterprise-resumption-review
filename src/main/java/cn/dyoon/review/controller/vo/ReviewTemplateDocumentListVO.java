package cn.dyoon.review.controller.vo;

import cn.dyoon.review.domain.entity.ReviewTemplateDocumentDO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 复工审核资料模板文件
 */
@Data
public class ReviewTemplateDocumentListVO {
    private String id;
    private String fileName;
    private Double fileSize;
    private String uploadUserName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String path;

    public ReviewTemplateDocumentListVO(ReviewTemplateDocumentDO reviewTemplateDocument) {
        this.id = reviewTemplateDocument.getId();
        this.fileName = reviewTemplateDocument.getFileName();
        this.fileSize = reviewTemplateDocument.getFileSize();
        this.uploadUserName = reviewTemplateDocument.getUploadUserName();
        this.createDate = reviewTemplateDocument.getCreateTime();
        this.updateDate = reviewTemplateDocument.getUpdateTime();
        this.path = reviewTemplateDocument.getPath();
    }
}
