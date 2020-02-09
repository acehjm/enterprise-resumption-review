package cn.dyoon.review.controller.param;

import cn.dyoon.review.common.enums.EnterpriseScaleEnum;
import cn.dyoon.review.common.enums.EnterpriseTypeEnum;
import cn.dyoon.review.common.enums.IndustryTypeEnum;
import cn.dyoon.review.common.enums.ResumptionTypeEnum;
import cn.dyoon.review.common.validator.EnumCheck;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * cn.dyoon.review.controller.param
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Data
public class EnterpriseRegisteredParam {
    @NotNull(message = "企业类型不能为空")
    @EnumCheck(enumClass = EnterpriseTypeEnum.class, enumMethod = "isValidType", message = "无效的企业类型")
    private Integer type;
    @NotNull(message = "企业规模不能为空")
    @EnumCheck(enumClass = EnterpriseScaleEnum.class, enumMethod = "isValidType", message = "无效的企业规模")
    private Integer scaleType;
    @NotNull(message = "复工类型不能为空")
    @EnumCheck(enumClass = ResumptionTypeEnum.class, enumMethod = "isValidType", message = "无效的复工类型")
    private Integer resumptionType;
    @NotNull(message = "行业类型不能为空")
    @EnumCheck(enumClass = IndustryTypeEnum.class, enumMethod = "isValidType", message = "无效的行业类型")
    private Integer industryType;
    @NotNull(message = "复工人数不能为空")
    private Integer employeeNum;
    @NotBlank(message = "企业名称不能为空")
    private String name;
    @NotBlank(message = "统一社会信用代码不能为空")
    @Pattern(regexp = "[^_IOZSVa-z\\W]{2}\\d{6}[^_IOZSVa-z\\W]{10}",
            message = "不合法的统一社会信用代码")
    private String unifiedSocialCreditCode;
    @NotBlank(message = "用户名不能为空")
//    @Pattern(regexp = "[\u4e00-\u9fa5]", message = "用户名不允许包含中文")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "填报人姓名不能为空")
    private String transactorName;
    @NotBlank(message = "联系电话不能为空")
//    @Pattern(regexp = "^(1[3-9]([0-9]{9}))$", message = "联系电话格式不正确")
    private String phone;
    @NotNull(message = "所属街道不能为空")
    private Integer street;

}
