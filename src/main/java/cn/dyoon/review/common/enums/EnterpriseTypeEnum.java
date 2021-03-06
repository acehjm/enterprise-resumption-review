package cn.dyoon.review.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum EnterpriseTypeEnum {
    INDUSTRIAL(1, "工业企业"),
    BUSINESS(2, "商贸业企业");

    private Integer code;
    private String desc;

    EnterpriseTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(Integer code) {
        return Arrays.stream(EnterpriseTypeEnum.values())
                .filter(it -> it.getCode().equals(code))
                .map(EnterpriseTypeEnum::getDesc)
                .findFirst()
                .orElse("");
    }

    public static boolean isValidType(Integer code) {
        return Arrays.stream(EnterpriseTypeEnum.values()).anyMatch(it -> it.getCode().equals(code));
    }
}
