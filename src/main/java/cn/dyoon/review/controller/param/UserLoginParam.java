package cn.dyoon.review.controller.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * cn.dyoon.review.controller.param
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Data
public class UserLoginParam {
    @NotBlank(message = "用户名不能为空")
    @Length(max = 32, message = "用户名长度不超过32个字符")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;

}
