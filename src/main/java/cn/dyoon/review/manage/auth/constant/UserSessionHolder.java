package cn.dyoon.review.manage.auth.constant;

import lombok.Data;

/**
 * 用户全局信息
 *
 * @author majhdk
 * @date 2019-08-15
 */
@Data
public class UserSessionHolder {

    /**
     * 已授权用户缓存
     */
    public static final ThreadLocal<UserSession> userSessionThreadLocal = new ThreadLocal<>();

}
