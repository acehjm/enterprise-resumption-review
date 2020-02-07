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
    private Integer street;
    private String reviewStatus;
    private String name;
}
