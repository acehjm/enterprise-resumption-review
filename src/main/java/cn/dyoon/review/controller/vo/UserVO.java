package cn.dyoon.review.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * 用户
 */
@Data
public class UserVO {
    private String userid;
    private String username;
    private String userType;
    private String role;
    private String token;
    private List<String> menus;
}
