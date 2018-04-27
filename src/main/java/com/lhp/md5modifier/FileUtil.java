package com.lhp.md5modifier;

import java.io.File;

/**
 * @Author: liaohaiping
 * @Description:
 * @Date: Created in 2018/4/27 0027 15:44
 */
public class FileUtil {

    public static String getFileExt(File f) {
        int index = f.getName().lastIndexOf(".");
        return index == -1 ? "" : f.getName().substring(index);
    }

}
