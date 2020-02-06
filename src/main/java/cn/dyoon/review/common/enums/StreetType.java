package cn.dyoon.review.common.enums;

import lombok.Getter;

@Getter
public enum StreetType {
    ZH_ZBS(0, "招宝山街道"),
    ZH_JCJD(1, "蛟川街道"),
    ZH_LTJD(2, "骆驼街道"),
    ZH_ZSJD(3, "庄市街道"),
    ZH_XPZ(4, "澥浦镇"),
    ZH_JLH(5, "九龙湖镇");

    private int code;
    private String desc;

    StreetType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
