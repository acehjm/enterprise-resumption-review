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

    @ExcelColumnDesc(column = "企业名称", order = 1)
    private String name;
    @ExcelColumnDesc(column = "企业类型", order = 2)
    private String type;
    @ExcelColumnDesc(column = "统一社会信用代码", order = 3)
    private String uniqueCode;
    @ExcelColumnDesc(column = "所属街道", order = 4)
    private String street;
    @ExcelColumnDesc(column = "申请人姓名", order = 5)
    private String transactorName;
    @ExcelColumnDesc(column = "联系电话", order = 6)
    private String phone;

    @ExcelColumnDesc(column = "申请时间", order = 7)
    private String applyTime;
    @ExcelColumnDesc(column = "审核状态", order = 8)
    private String reviewStatus;
    @ExcelColumnDesc(column = "审核时间", order = 9)
    private String reviewTime;
    @ExcelColumnDesc(column = "审核结果", order = 10)
    private String reviewResult;

}
