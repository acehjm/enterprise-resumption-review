package cn.dyoon.review.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum UserTypeEnum {

    SUPER_ADMIN(0, "SUPER_ADMIN", "超级管理员") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_MANAGE.getName(), MenusEnum.REVIEW_TEMPLATE_MANAGER.getName(),
                    MenusEnum.POLICY_MANAGE.getName());
        }
    },

    ENTERPRISE(1, "ENTERPRISE_USER", "企业用户") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_BASE_INFO.getName(), MenusEnum.REVIEW_TEMPLATE_DOWNLOAD.getName(),
                    MenusEnum.EPIDEMIC_PREVENTION_CONTROL.getName());
        }
    },

    ZF_JINGXIN(2, "JINGXINJU_USER", "经信局用户") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_MANAGE.getName(), MenusEnum.REVIEW_TEMPLATE_DOWNLOAD.getName(),
                    MenusEnum.EPIDEMIC_PREVENTION_CONTROL.getName());
        }
    },

    ZF_SHANGWU(3, "SHANGWUJU_USER", "商务局用户") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_MANAGE.getName(), MenusEnum.REVIEW_TEMPLATE_DOWNLOAD.getName(),
                    MenusEnum.EPIDEMIC_PREVENTION_CONTROL.getName());
        }
    },

    ZF_STREET(4, "STREET_USER", "镇（街道）/园区用户") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_MANAGE.getName(), MenusEnum.REVIEW_TEMPLATE_DOWNLOAD.getName(),
                    MenusEnum.EPIDEMIC_PREVENTION_CONTROL.getName());
        }
    },

    ZF_PREVENTION(5, "PREVENTION_USER", "防控领导小组用户") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_MANAGE.getName(), MenusEnum.REVIEW_TEMPLATE_DOWNLOAD.getName(),
                    MenusEnum.EPIDEMIC_PREVENTION_CONTROL.getName());
        }
    },

    ;
    private Integer code;
    private String name;
    private String desc;

    UserTypeEnum(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static UserTypeEnum get(Integer code) {
        return Arrays.stream(UserTypeEnum.values())
                .filter(it -> it.getCode().equals(code))
                .findFirst()
                .orElse(SUPER_ADMIN);
    }

    public static UserTypeEnum get(String name) {
        return Arrays.stream(UserTypeEnum.values())
                .filter(it -> it.getName().equals(name))
                .findFirst()
                .orElse(SUPER_ADMIN);
    }

    public abstract List<String> authorities();
}
