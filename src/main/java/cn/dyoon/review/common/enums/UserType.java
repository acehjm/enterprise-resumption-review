package cn.dyoon.review.common.enums;

import lombok.Getter;

@Getter
public enum UserType {
    ENTERPRISE(0, "企业用户"),
    ZH_ZXJ(1, "经信局"),
    ZH_SWJ(2, "商务局"),
    ZH_ZBS(3, "招宝山街道"),
    ZH_JCJD(4, "蛟川街道"),
    ZH_LTJD(5, "骆驼街道"),
    ZH_ZSJD(6, "庄市街道"),
    ZH_XPZ(7, "澥浦镇"),
    ZH_JLH(8, "九龙湖镇");

    private Integer code;

    private String desc;

    UserType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
