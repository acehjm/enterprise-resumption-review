package cn.dyoon.review.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StreetTypeEnum {

    ZH_XPZ(1, "澥浦镇"),
    ZH_JLH(2, "九龙湖镇"),
    ZH_ZSJD(3, "庄市街道"),
    ZH_ZBS(4, "招宝山街道"),
    ZH_LTJD(5, "骆驼街道"),
    ZH_JCJD(6, "蛟川街道"),
    ZH_DXKJY(7, "大学科技园"),
    ZH_XCGWH(8, "新城管委会"),

    ;
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
