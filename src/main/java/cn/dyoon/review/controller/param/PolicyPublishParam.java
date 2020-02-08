package cn.dyoon.review.controller.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PolicyPublishParam {
    @NotNull(message = "政策ID不能为空")
    private String policyId;
    @NotNull(message = "发布状态不能为空")
    private Integer status;
}
