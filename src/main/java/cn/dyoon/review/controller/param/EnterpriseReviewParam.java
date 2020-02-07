package cn.dyoon.review.controller.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

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
    private String reviewResult;
}
