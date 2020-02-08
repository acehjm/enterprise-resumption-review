package cn.dyoon.review.controller.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PolicyParam {
    @NotNull(message = "标题不能为空")
    private String title;
    private String desc;
}
