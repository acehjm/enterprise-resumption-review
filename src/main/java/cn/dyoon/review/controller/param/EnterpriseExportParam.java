package cn.dyoon.review.controller.param;

import lombok.Data;

/**
 * cn.dyoon.review.controller.param
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Data
public class EnterpriseExportParam {
    private Integer type;
    private Integer scaleType;
    private Integer resumptionType;
    private Integer industryType;
    private Integer street;
    private Integer reviewStatus;
    private String name;
}
