package cn.dyoon.review.common.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * cn.dyoon.review.common.enums
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Getter
public enum UserRoleEnum {

    SUPER_ADMIN(0, "SUPER_ADMIN", "超级管理员"),
    GENERAL_USER(1, "GENERAL", "普通用户"),
    ASSIGNEE_USER(2, "ASSIGNEE", "受理人"),
    REVIEW_USER(3, "REVIEW", "审核人"),

    ;

    private Integer code;
    private String name;
    private String desc;

    UserRoleEnum(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static UserRoleEnum get(Integer code) {
        return Arrays.stream(UserRoleEnum.values())
                .filter(it -> it.getCode().equals(code))
                .findFirst()
                .orElse(GENERAL_USER);
    }

}
