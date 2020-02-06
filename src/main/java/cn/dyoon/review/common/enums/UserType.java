package cn.dyoon.review.common.enums;

import lombok.Getter;

@Getter
public enum UserType {
    SUPER_ADMIN(0, "超级管理员"),

    ENTERPRISE(1, "企业用户"),

    ZF_COMMON(2, "经信局/商务局"),

    ZF_STREET(3, "镇/街道"),

    ;

    private Integer code;

    private String desc;

    UserType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
