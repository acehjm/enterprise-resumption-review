package cn.dyoon.review.common.exception;

/**
 * cn.dyoon.review.common.exception
 *
 * @author majhdk
 * @date 2020/2/7
 */
public enum BaseExceptionEnum implements BaseError {

    USER_NOT_EXISTS("0x0a00001", "该用户不存在"),

    USER_PASSWORD_ERROR("0x0a00002", "用户密码错误"),

    USER_NAME_HAS_EXISTS("0x0a00003", "用户名已存在，请重新输入"),

    ENTERPRISE_NOT_EXISTS("0x0a00004", "企业不存在"),

    ENTERPRISE_NO_DATA_BY_CONDITION("0x0a00005", "企业数据为空"),

    POLICY_TITLE_HAS_EXISTS("0x0a0006", "政策标题已存在, 请重新输入"),

    ;

    private String code;
    private String message;

    BaseExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
