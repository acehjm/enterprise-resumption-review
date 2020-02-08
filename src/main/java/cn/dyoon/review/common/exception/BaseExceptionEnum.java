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

    POLICY_NOT_EXISTS("0x0a0007", "政策信息不存在"),

    ENTERPRISE_NOT_MATCH("0x0a00008", "当前用户没有权限处理该企业"),

    ENTERPRISE_IN_PROCESSING("0x0a00009", "企业复工申请审批中，不允许修改"),

    UPLOAD_FILES_IS_EMPTY("0x0a00010", "上传文件为空"),

    UPLOAD_FILES_FAILURE("0x0a00011", "上传文件失败"),

    DOWNLOAD_FILES_NOT_EXISTS("0x0a00012", "文件不存在"),

    DOWNLOAD_FILES_FAILURE("0x0a00013", "下载文件失败"),

    DELETE_FILES_FAILURE("0x0a00014", "删除文件失败"),

    POLICY_HAS_PUBLISHED("0x0a00015", "政策已发布, 不允许修改")

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
