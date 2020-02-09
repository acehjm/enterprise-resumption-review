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
public enum EnterpriseScaleEnum {

    ENTERPRISE_ABOVE_SCALE(1, "ENTERPRISE_ABOVE_SCALE", "规上(限上)企业"),
    ENTERPRISE_BELOW_SCALE(2, "ENTERPRISE_BELOW_SCALE", "规下(限下)企业"),
    ENTERPRISE_MICRO_SCALE(3, "ENTERPRISE_MICRO_SCALE", "20人以下的微型工业（商贸企业）"),
    ;

    private Integer code;
    private String name;
    private String desc;

    EnterpriseScaleEnum(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static String getDesc(Integer code) {
        return Arrays.stream(EnterpriseScaleEnum.values())
                .filter(it -> it.getCode().equals(code))
                .map(EnterpriseScaleEnum::getDesc)
                .findFirst()
                .orElse("");
    }

    public static boolean isValidType(Integer code) {
        return Arrays.stream(EnterpriseScaleEnum.values()).anyMatch(it -> it.getCode().equals(code));
    }
}
