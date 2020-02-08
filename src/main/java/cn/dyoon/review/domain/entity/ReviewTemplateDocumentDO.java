package cn.dyoon.review.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("review_template_document")
@Data
public class ReviewTemplateDocumentDO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("file_name")
    private String fileName;

    @TableField("virtual_name")
    private String virtualName;

    @TableField("file_size")
    private Double fileSize;

    @TableField("upload_user_name")
    private String uploadUserName;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("path")
    private String path;
}
