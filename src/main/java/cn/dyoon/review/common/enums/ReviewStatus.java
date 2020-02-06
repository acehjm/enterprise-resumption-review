package cn.dyoon.review.common.enums;

import lombok.Getter;

@Getter
public enum ReviewStatus {
    NOT_STARTED(0, "未开始"),
    IN_REVIEW(1, "审核中"),
    PASS(2, "批准"),
    NOT_PASS(3, "未通过");

    private int code;
    private String desc;

    ReviewStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
