package cn.dyoon.review.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StreetTypeEnum {
    ZH_ZBS(1, "招宝山街道"),
    ZH_JCJD(2, "蛟川街道"),
    ZH_LTJD(3, "骆驼街道"),
    ZH_ZSJD(4, "庄市街道"),
    ZH_XPZ(5, "澥浦镇"),
    ZH_JLH(6, "九龙湖镇"),
    ZH_GS(7, "贵泗街道");

    private Integer code;
    private String desc;

    StreetTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(Integer code) {
        return Arrays.stream(StreetTypeEnum.values())
                .filter(it -> it.getCode().equals(code))
                .map(StreetTypeEnum::getDesc)
                .findFirst()
                .orElse("NOT_EXIST");
    }
}
