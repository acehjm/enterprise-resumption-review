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
    POLICY_FILE_DOWNLOAD(4, "POLICY_FILE_DOWNLOAD", "政策文件及附件下载"),
    CONTACT_DETAILS(5, "CONTACT_DETAILS", "联系方式"),

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
