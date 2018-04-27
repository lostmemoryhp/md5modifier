package com.lhp.md5modifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Author: liaohaiping
 * @Description:
 * @Date: Created in 2018/4/27 0027 16:19
 */
public class ImageFileModifier implements FileModifier {
    @Override
    public boolean canModify(File file) {
        String ext = FileUtil.getFileExt(file);
        switch (ext) {
            case ".jpg":
            case ".jpeg":
            case ".bmp":
            case ".gif":
            case ".png":
                return true;
            default:
                return false;
        }
    }

    @Override
    public void modify(File f) {
        RandomAccessFile file = null;
        try {
            System.out.println("文件：" + f.getAbsolutePath());
            System.out.println("变更前md5： " + Md5Util.getMd5ByFile(f));
            file = new RandomAccessFile(f, "rw");
            ImageAppendContent imageAppendContent = new ImageAppendContent(file);
            imageAppendContent.writeNewContent();
            System.out.println("变更后md5： " + Md5Util.getMd5ByFile(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
