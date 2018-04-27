package com.lhp.md5modifier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: liaohaiping
 * @Description:
 * @Date: Created in 2018/4/27 0027 16:49
 */
public class AppendContentBuilder {
    private static final String PREFIX = "__append__"; //length 10
    static SimpleDateFormat yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS"); //length 17
    static Pattern pattern = Pattern.compile("^" + PREFIX + "\\d+" + PREFIX + "$");


    public static final int CONTENT_LENGTH = 37;


    public static boolean isAppendContent(String content) {
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static String newAppendContent() {
        return PREFIX + yyyyMMddHHmmssSSS.format(new Date()) + PREFIX;
    }
}
