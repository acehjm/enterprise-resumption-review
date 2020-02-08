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
@TableName("rework_document")
public class ReworkDocumentDO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("file_name")
    private String fileName;

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

    @TableField("enterprise_id")
    private String enterpriseId;

    @TableField("file_disk_name")
    private String fileDiskName;

}
