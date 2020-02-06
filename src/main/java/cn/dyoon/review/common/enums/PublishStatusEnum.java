package cn.dyoon.review.common.enums;

import lombok.Getter;

@Getter
public enum PublishStatusEnum {
    UNPUBLISHED(0, "未发布"),
    PUBLISH(1, "发布");

    private int code;
    private String desc;

    PublishStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
