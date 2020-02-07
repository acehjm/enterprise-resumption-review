package cn.dyoon.review.controller;

import cn.dyoon.review.common.response.Result;
import cn.dyoon.review.controller.param.UserLoginParam;
import cn.dyoon.review.controller.vo.UserVO;
import cn.dyoon.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<UserVO> login(@Validated @RequestBody UserLoginParam param) {
        return new Result<>(userService.login(param));
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signout")
    public Result<Void> signout() {
        userService.signout("");
        return new Result<>();
    }

}
