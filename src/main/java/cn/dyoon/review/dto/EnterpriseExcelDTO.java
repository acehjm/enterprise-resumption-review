package cn.dyoon.review.dto;

import cn.dyoon.review.manage.excel.annotation.ExcelColumnDesc;
import cn.dyoon.review.manage.excel.annotation.ExcelFileDesc;
import lombok.Data;

/**
 * cn.dyoon.review.dto
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Data
@ExcelFileDesc
public class EnterpriseExcelDTO {

    @ExcelColumnDesc(column = "序号", order = 1)
    private Integer order;
    @ExcelColumnDesc(column = "复工企业名称", order = 2)
    private String name;
    @ExcelColumnDesc(column = "统一社会信用代码", order = 3)
    private String unifiedSocialCreditCode;
    @ExcelColumnDesc(column = "所在镇(街道)/园区", order = 4)
    private String street;
    @ExcelColumnDesc(column = "企业类别", order = 5)
    private String type;
    @ExcelColumnDesc(column = "企业规模", order = 6)
    private String scaleType;
    @ExcelColumnDesc(column = "复工类别", order = 7)
    private String resumptionType;
    @ExcelColumnDesc(column = "行业类别", order = 8)
    private String industryType;
    @ExcelColumnDesc(column = "拟复工日期", order = 9)
    private String resumptionDate;
    @ExcelColumnDesc(column = "复工员工数", order = 10)
    private Integer employeeNum;
    @ExcelColumnDesc(column = "企业总人数", order = 11)
    private Integer employeeTotalNum;
    @ExcelColumnDesc(column = "填报人姓名", order = 12)
    private String transactorName;
    @ExcelColumnDesc(column = "联系电话", order = 13)
    private String phone;
    @ExcelColumnDesc(column = "详细地址", order = 14)
    private String address;
    @ExcelColumnDesc(column = "流程状态", order = 15)
    private String reviewStatus;
}
