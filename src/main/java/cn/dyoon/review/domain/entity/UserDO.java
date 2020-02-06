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
 * @date 2020/2/6
 */
@Data
@TableName("user")
public class UserDO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("user_name")
    private String username;

    @TableField("password")
    private String password;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("user_type")
    private Integer userType;

}
