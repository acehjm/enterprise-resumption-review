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

    /**
     * 新增用户
     *
     * @param username
     * @param password
     */
    void addEnterpriseUser(String username, String password);

    /**
     * 删除用户
     * - 用在超级管理员删除企业时
     *
     * @param username
     */
    void deleteByUsername(String username);
}
