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

    ENTERPRISE_IN_PROCESSING("0x0a00009", "企业复工申请审批中，不允许操作"),

    UPLOAD_FILES_IS_EMPTY("0x0a00010", "上传文件为空"),

    UPLOAD_FILES_FAILURE("0x0a00011", "上传文件失败"),

    DOWNLOAD_FILES_NOT_EXISTS("0x0a00012", "文件不存在"),

    DOWNLOAD_FILES_FAILURE("0x0a00013", "下载文件失败"),

    DELETE_FILES_FAILURE("0x0a00014", "删除文件失败"),

    POLICY_HAS_PUBLISHED("0x0a00015", "政策已发布, 不允许修改"),

    FILE_TYPE_NOT_SUPPORT("0x0a00016", "不支持的文件类型"),

    ENTERPRISE_EMPLOYEE_ERROR("0x0a00017", "20人以下的微型工业（商贸企业）人数不能超过20人"),

    UNIFIED_SOCIAL_CREDIT_CODE_EXISTS("0x0a00018", "统一社会信用代码已存在，请重新输入"),

    ENTERPRISE_NOT_IN_PROCESSING("0x0a00019", "企业复工申请状态为通过或待申请，不允许操作"),

    TELEPHONE_LANDLINE_ERROR("0x0a00020", "移动手机或固定电话号码错误，请重新输入"),

    FILE_SIZE_LIMIT_UPLOAD("0x0a00021", "文件大小超过限制，请重新上传"),

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
