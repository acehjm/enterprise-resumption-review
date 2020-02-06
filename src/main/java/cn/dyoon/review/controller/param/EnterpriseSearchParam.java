package cn.dyoon.review.controller.param;

import lombok.Data;

/**
 * cn.dyoon.review.controller.param
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Data
public class EnterpriseSearchParam {
    private Integer pageNo;
    private Integer pageSize;
    private Integer type;
    private Integer street;
    private String reviewStatus;
    private String name;
}
