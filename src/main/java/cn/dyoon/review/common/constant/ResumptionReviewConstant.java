package cn.dyoon.review.common.constant;

/**
 * cn.dyoon.review.common.constant
 *
 * @author majhdk
 * @date 2020/2/7
 */
public class ResumptionReviewConstant {
    private ResumptionReviewConstant() {
    }

    /**
     * 文件库
     */
    public static final String BASE_PATH = "./vault";
    /**
     * 企业复工文件路径
     */
    public static final String ENTERPRISE_RESUMPTION_PATH = BASE_PATH + "/resumption";
    /**
     * 政府政策文件路径
     */
    public static final String POLICY_PATH = BASE_PATH + "/policy";

    /**
     * 复工审核资料模板路径
     */
    public static final String REVIEW_TEMPLATE_DOCUMENT_PATH = BASE_PATH + "/reviewtemplate";

    /**
     * 标准时间格式
     */
    public static final String STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准日前格式
     */
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";



}
