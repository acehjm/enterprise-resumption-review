package cn.dyoon.review.controller.vo;

import cn.dyoon.review.common.enums.UserType;
import lombok.Data;

import java.util.Date;

/**
 * 用户
 */
@Data
public class UserVo {
    private Integer id;
    private String userName;
    private String password;
    private Date createDate;
    private UserType userType;
}
