package cn.dyoon.review.controller;

import cn.dyoon.review.common.response.Result;
import cn.dyoon.review.controller.param.UserLoginParam;
import cn.dyoon.review.controller.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * cn.dyoon.review.controller-
 *
 * @author majhdk
 * @date 2020/2/6
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserLoginParam param) {

        return new Result<>();
    }

    @PostMapping("/signout")
    public Result<Void> signout() {

        return new Result<>();
    }

}
