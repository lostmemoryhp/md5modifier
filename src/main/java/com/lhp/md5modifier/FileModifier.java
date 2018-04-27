package com.lhp.md5modifier;

import java.io.File;

/**
 * @Author: liaohaiping
 * @Description:
 * @Date: Created in 2018/4/27 0027 16:17
 */
public interface FileModifier {
    boolean canModify(File file);
    void modify(File file);
}
