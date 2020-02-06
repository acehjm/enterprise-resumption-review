package cn.dyoon.review.common.enums;

import lombok.Getter;

@Getter
public enum PublishStatus {
    UNPUBLISHED(0, "未发布"),
    PUBLISH(1, "发布");

    private int code;
    private String desc;

    PublishStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
