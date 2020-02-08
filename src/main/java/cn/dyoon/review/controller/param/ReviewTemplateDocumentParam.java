package cn.dyoon.review.controller.param;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class ReviewTemplateDocumentParam {
    @NotNull(message = "分页页数不能为空")
    private Integer pageNo;
    @NotNull(message = "分页页面大小不能为空")
    @Max(value = 500, message = "分页页面大小不能超过500")
    private Integer pageSize;
}
