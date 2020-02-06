package cn.dyoon.review.service;

import cn.dyoon.review.controller.param.UserLoginParam;
import cn.dyoon.review.controller.vo.UserVO;

/**
 * cn.dyoon.review.service
 *
 * @author majhdk
 * @date 2020/2/6
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param param
     * @return
     */
    UserVO login(UserLoginParam param);

    /**
     * 登出
     *
     * @param username
     */
    void signout(String username);
}
