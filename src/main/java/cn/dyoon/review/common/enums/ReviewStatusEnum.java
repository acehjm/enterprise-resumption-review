package cn.dyoon.review.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ReviewStatusEnum {
    NOT_STARTED(0, "待申请"),
    IN_REVIEW(1, "审核中"),
    PASS(2, "批准"),
    NOT_PASS(3, "退回");

    private Integer code;
    private String desc;

    ReviewStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(Integer code) {
        return Arrays.stream(ReviewStatusEnum.values())
                .filter(it -> it.getCode().equals(code))
                .map(ReviewStatusEnum::getDesc)
                .findFirst()
                .orElse("NOT_EXIST");
    }
}
