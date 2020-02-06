package cn.dyoon.review.service.impl;

import cn.dyoon.review.controller.param.UserLoginParam;
import cn.dyoon.review.controller.vo.UserVO;
import cn.dyoon.review.service.UserService;
import org.springframework.stereotype.Service;

/**
 * cn.dyoon.review.service.impl
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserVO login(UserLoginParam param) {
        return null;
    }

    @Override
    public void signout(String username) {

    }
}
