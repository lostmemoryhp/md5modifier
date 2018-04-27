package com.lhp.md5modifier;

import java.io.*;
import java.util.*;

/**
 * @Author: liaohaiping
 * @Description:
 * @Date: Created in 2018/4/27 0027 13:55
 */
public class Application {
    static List<FileModifier> fileModifiers=new ArrayList<>();

    public static void main(String[] args) {
        if (args.length == 0) {
            printUseage();
            return;
        }
        String dir = args[0];
        fileModifiers.add(new ImageFileModifier());
        fileModifiers.add(new AndroidManifestModifier());
        parseDir(new File(dir));
    }

    static void printUseage() {
        System.out.println("请在命令后面跟上文件夹的路径。");
    }


    static void parseDir(File dir) {
//        System.out.println("遍历文件夹：" + dir.getAbsolutePath());
        Arrays.asList(dir.listFiles(f -> f.isFile()))
                .forEach(file -> {
                    for (FileModifier fm :fileModifiers) {
                        if(fm.canModify(file)){
                            fm.modify(file);
                            break;
                        }
                    }
                });
        Arrays.asList(dir.listFiles(f -> f.isDirectory()))
                .forEach(file -> {
                    parseDir(file);
                });

    }



}
