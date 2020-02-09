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
public enum ResumptionTypeEnum {
    ENTERPRISE_PRIORITY_PROTECTION(1, "优先保障企业"),
    ENTERPRISE_EARLY_RESUMPTION(2, "提前开工企业"),
    ENTERPRISE_STEADY_RESUMPTION(3, "稳步复工企业"),

    ;
    private Integer code;
    private String desc;

    ResumptionTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(Integer code) {
        return Arrays.stream(ResumptionTypeEnum.values())
                .filter(it -> it.getCode().equals(code))
                .map(ResumptionTypeEnum::getDesc)
                .findFirst()
                .orElse("");
    }

    public static boolean isValidType(Integer code) {
        return Arrays.stream(ResumptionTypeEnum.values()).anyMatch(it -> it.getCode().equals(code));
    }

}
