package cn.dyoon.review.manage.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author majhdk
 * @DESCRIPTION Excel文件描述
 * @date 2018-12-02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelFileDesc {

    /**
     * 编码格式，暂时未使用，后续完善
     */
    String charset() default "utf-8";

    /**
     * 忽略的行，也是数据开始行
     */
    int skipLines() default 0;

}
