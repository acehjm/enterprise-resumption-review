package cn.dyoon.review.controller.param;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class EnterpriseSelectListParam {
    private Integer type;
    private Integer scaleType;
    private Integer resumptionType;
    private Integer industryType;
    private Integer street;
    private Integer reviewStatus;
    private String name;
}
