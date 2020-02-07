package cn.dyoon.review.controller.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * cn.dyoon.review.controller.param
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Data
public class EnterpriseApplyParam {
    @NotBlank(message = "企业ID不能为空")
    private String enterpriseId;
    @NotNull(message = "复工申请类别不能为空")
    private Integer scaleType;
    @NotNull(message = "企业员工数不能为空")
    private Integer employeeNum;
}
