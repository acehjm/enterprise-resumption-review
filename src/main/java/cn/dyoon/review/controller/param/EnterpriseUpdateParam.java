package cn.dyoon.review.controller.param;

import cn.dyoon.review.common.enums.EnterpriseScaleEnum;
import cn.dyoon.review.common.enums.EnterpriseTypeEnum;
import cn.dyoon.review.common.enums.IndustryTypeEnum;
import cn.dyoon.review.common.enums.ResumptionTypeEnum;
import cn.dyoon.review.common.validator.EnumCheck;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * cn.dyoon.review.controller.param
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Data
public class EnterpriseUpdateParam {
    @NotNull(message = "企业类型不能为空")
    @EnumCheck(enumClass = EnterpriseTypeEnum.class, enumMethod = "isValidType", message = "无效的企业类型")
    private Integer type;
    @NotNull(message = "企业规模不能为空")
    @EnumCheck(enumClass = EnterpriseScaleEnum.class, enumMethod = "isValidType", message = "无效的企业规模")
    private Integer scaleType;
    @NotNull(message = "复工类型不能为空")
    @EnumCheck(enumClass = ResumptionTypeEnum.class, enumMethod = "isValidType", message = "无效的复工类型")
    private Integer resumptionType;
//    @NotNull(message = "行业类型不能为空")
//    @EnumCheck(enumClass = IndustryTypeEnum.class, enumMethod = "isValidType", message = "无效的行业类型")
    private Integer industryType;
    @NotNull(message = "复工人数不能为空")
    private Integer employeeNum;
    @NotNull(message = "企业总人数不能为空")
    private Integer employeeTotalNum;
    @NotBlank(message = "企业名称不能为空")
    private String name;
    @NotBlank(message = "统一社会信用代码不能为空")
    private String unifiedSocialCreditCode;
    @NotBlank(message = "填报人姓名不能为空")
    private String transactorName;
    @NotBlank(message = "联系电话不能为空")
    private String phone;
    @NotNull(message = "所属街道不能为空")
    private Integer street;
    @NotNull(message = "企业复工日期不能为空")
    private LocalDate resumptionDate;

}
