package cn.dyoon.review.manage.auth.constant;

import lombok.Data;

import java.util.List;

/**
 * me.solby.xtool.constant
 *
 * @author majhdk
 * @date 2019-08-26
 */
@Data
public class UserSession {

    /**
     * 用户名
     */
    private String username;
    /**
     * 所属街道/镇
     */
    private String street;
    /**
     * 角色权限
     */
    private String userType;

    /**
     * 菜单权限
     */
    private List<String> menus;


}
