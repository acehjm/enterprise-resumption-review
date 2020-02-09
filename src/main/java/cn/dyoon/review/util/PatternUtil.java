package cn.dyoon.review.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * cn.dyoon.review.util
 *
 * @author majhdk
 * @date 2020/2/9
 */
public class PatternUtil {
    private PatternUtil() {
    }

    /**
     * 手机号校验
     *
     * @param str
     * @return
     */
    public static boolean checkTelephone(String str) {
        return Pattern.compile(
                "(?<![0-9])" // 左边不能有数字
                        + "((0|\\+86|0086)\\s?)?" // 0 +86 0086
                        + "1[34578][0-9]-?[0-9]{4}-?[0-9]{4}" // 186-1234-5678
                        + "(?![0-9])" // 右边不能有数字
        ).matcher(str).find();
    }

    /**
     * 固定号码校验
     *
     * @param str
     * @return
     */
    public static boolean checkLandline(String str) {
        return Pattern.compile(
                "(?<![0-9])" // 左边不能有数字
                        + "(\\(?0[0-9]{2,3}\\)?-?)?" // 区号
                        + "[0-9]{7,8}" // 市内号码
                        + "(?![0-9])" // 右边不能有数字
        ).matcher(str).find();
    }

    /**
     * 校验文件格式
     *
     * @param fileName
     * @return
     */
    public static boolean checkFileType(String fileName) {
        String[] types = {"doc", "docx", "xls", "xlsx", "pdf"};
        String[] split = StringUtils.split(fileName, ".");
        return Arrays.asList(types).contains(Objects.requireNonNull(split)[split.length - 1]);
    }
}
