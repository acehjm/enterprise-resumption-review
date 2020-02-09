package cn.dyoon.review.common.enums;

import lombok.Getter;

/**
 * cn.dyoon.review.common.enums
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Getter
public enum MenusEnum {

    ENTERPRISE_MANAGE(1, "ENTERPRISE_MANAGE", "企业管理"),
    POLICY_MANAGE(2, "POLICY_MANAGE", "政策发布管理"),
    ENTERPRISE_BASE_INFO(3, "ENTERPRISE_BASE_INFO", "企业基本信息"),
    EPIDEMIC_PREVENTION_CONTROL(4, "POLICY_FILE_DOWNLOAD", "疫情防控相关政策"),
    CONTACT_DETAILS(5, "CONTACT_DETAILS", "企业复工咨询联系方式"),
    REVIEW_TEMPLATE_DOWNLOAD(6, "REVIEW_TEMPLATE", "复工审核资料模板下载"),
    REVIEW_TEMPLATE_MANAGER(7, "REVIEW_TEMPLATE_MANAGER", "复工审核资料模板管理"),

    ;

    private Integer code;
    private String name;
    private String desc;

    MenusEnum(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

}
