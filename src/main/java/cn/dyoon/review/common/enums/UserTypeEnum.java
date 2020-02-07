package cn.dyoon.review.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum UserTypeEnum {

    SUPER_ADMIN(0, "SUPER_ADMIN", "超级管理员") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_MANAGE.getName(), MenusEnum.POLICY_MANAGE.getName());
        }
    },

    ENTERPRISE(1, "ENTERPRISE", "企业用户") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_BASE_INFO.getName(), MenusEnum.POLICY_FILE_DOWNLOAD.getName(),
                    MenusEnum.CONTACT_DETAILS.getName());
        }
    },

    ZF_COMMON(2, "COMMON", "经信局/商务局") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_MANAGE.getName(), MenusEnum.POLICY_FILE_DOWNLOAD.getName(),
                    MenusEnum.CONTACT_DETAILS.getName());
        }
    },

    ZF_STREET(3, "STREET", "镇/街道/园区") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_MANAGE.getName(), MenusEnum.POLICY_FILE_DOWNLOAD.getName(),
                    MenusEnum.CONTACT_DETAILS.getName());
        }
    },

    ZF_PREVENTION(4, "PREVENTION", "区防控领导小组") {
        @Override
        public List<String> authorities() {
            return Arrays.asList(MenusEnum.ENTERPRISE_MANAGE.getName(), MenusEnum.POLICY_FILE_DOWNLOAD.getName(),
                    MenusEnum.CONTACT_DETAILS.getName());
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
                .orElse(ZF_COMMON);
    }

    public abstract List<String> authorities();
}
