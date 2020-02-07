package cn.dyoon.review.controller.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * cn.dyoon.review.controller.param
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Data
public class EnterpriseParam {
    @NotNull(message = "企业类型不能为空")
    private Integer type;
    @NotNull(message = "企业规模不能为空")
    private Integer scaleType;
    @NotBlank(message = "企业名称不能为空")
    private String name;
    @NotBlank(message = "统一社会信用代码不能为空")
    private String unifiedSocialCreditCode;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "填报人姓名不能为空")
    private String transactorName;
    @NotBlank(message = "联系电话不能为空")
    private String phone;
    @NotNull(message = "所属街道不能为空")
    private Integer street;

}
