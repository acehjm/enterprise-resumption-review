package cn.dyoon.review.controller.param;

import lombok.Data;

/**
 * cn.dyoon.review.controller.param
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Data
public class EnterpriseParam {

    private Integer type;
    private String name;
    private String unifiedSocialCreditCode;
    private String username;
    private String password;
    private String transactorName;
    private String phone;
    private String street;

}
