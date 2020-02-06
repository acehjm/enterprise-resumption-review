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
