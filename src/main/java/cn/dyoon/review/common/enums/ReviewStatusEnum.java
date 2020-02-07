package cn.dyoon.review.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ReviewStatusEnum {
    NOT_STARTED(0, "待申请"),
    ACCEPTED(1, "受理中"),
    STREET_REVIEW(2, "镇(街道)/园区审核"),
    DEPARTMENT_REVIEW(3, "主管部门审核"),
    PREVENTION_REVIEW(4, "防控小组审核"),

    PASS(5, "通过"),
    NOT_PASS(6, "退回");

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
