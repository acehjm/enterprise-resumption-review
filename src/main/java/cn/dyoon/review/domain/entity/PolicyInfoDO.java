package cn.dyoon.review.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("policy_info")
public class PolicyInfoDO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("title")
    private String title;

    @TableField("`desc`")
    private String desc;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("release_time")
    private LocalDateTime releaseTime;

    @TableField("status")
    private Integer status;
}
