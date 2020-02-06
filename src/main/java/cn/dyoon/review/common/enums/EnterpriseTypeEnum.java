package cn.dyoon.review.common.enums;

import lombok.Getter;

@Getter
public enum EnterpriseTypeEnum {
    INDUSTRIAL(0, "工业企业"),
    SERVICE(1, "服务业企业");

    private int code;
    private String desc;

    EnterpriseTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
