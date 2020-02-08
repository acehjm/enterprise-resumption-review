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
public enum IndustryTypeEnum {

    ENTERPRISE_EPIDEMIC_PREVENTION_CONTROL(1, "疫情防控必需"),
    ENTERPRISE_GUARANTEE_OPERATION_PRODUCTION(2, "保障城市运行和企业生产必需"),
    ENTERPRISE_LIFE_MASSES(3, "群众生活必需"),
    ENTERPRISE_OTHER_ECONOMY_LIVELIHOOD(4, "其他涉及重要国计民生的相关企业"),
    ENTERPRISE_CONSTRUCTION_PROJECTS(5, "重点建设项目"),
    ENTERPRISE_URGENTLY_FULFILL_INTERNATIONAL(6, "亟需履行国际大型订单企业 "),
    ;

    private Integer code;
    private String desc;

    IndustryTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(Integer code) {
        return Arrays.stream(EnterpriseTypeEnum.values())
                .filter(it -> it.getCode().equals(code))
                .map(EnterpriseTypeEnum::getDesc)
                .findFirst()
                .orElse("NOT_EXISTS");
    }

    public static boolean isValidType(Integer code) {
        return Arrays.stream(IndustryTypeEnum.values()).anyMatch(it -> it.getCode().equals(code));
    }

}
