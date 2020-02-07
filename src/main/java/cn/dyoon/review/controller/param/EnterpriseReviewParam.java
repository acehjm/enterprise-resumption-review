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
public class EnterpriseReviewParam {
    @NotBlank(message = "企业ID不能为空")
    private String enterpriseId;
    @NotNull(message = "审核状态不为空")
    private Integer reviewStatus;
    private String reviewResult;
}
