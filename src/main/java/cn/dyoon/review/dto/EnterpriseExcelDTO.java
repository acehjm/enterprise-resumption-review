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

    @ExcelColumnDesc(column = "复工企业名称", order = 1)
    private String name;
    @ExcelColumnDesc(column = "所在镇(街道)/园区", order = 2)
    private String street;
    @ExcelColumnDesc(column = "企业类别", order = 3)
    private String type;
    @ExcelColumnDesc(column = "企业规模", order = 4)
    private String scaleType;
    @ExcelColumnDesc(column = "复工类别", order = 5)
    private String resumptionType;
    @ExcelColumnDesc(column = "行业类别", order = 6)
    private String industryType;
    @ExcelColumnDesc(column = "复工时间", order = 7)
    private String resumptionDate;
    @ExcelColumnDesc(column = "复工员工数", order = 8)
    private Integer employeeNum;

}
