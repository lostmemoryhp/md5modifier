package com.lhp.md5modifier;


import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Author: liaohaiping
 * @Description:
 * @Date: Created in 2018/4/27 0027 14:49
 */
public class ImageAppendContent {
    String appendContent;
    boolean hasAppendContent = false;
    long fileLength;
    RandomAccessFile file;

    public String getAppendContent() {
        return appendContent;
    }

    public void setAppendContent(String appendContent) {
        this.appendContent = appendContent;
    }

    public ImageAppendContent(RandomAccessFile f) {
        this.file = f;
        try {
            fileLength = f.length();
            f.seek(f.length() - AppendContentBuilder.CONTENT_LENGTH);
            byte[] buffer = new byte[AppendContentBuilder.CONTENT_LENGTH];
            f.read(buffer, 0, buffer.length);
            String str = new String(buffer);
            if (AppendContentBuilder.isAppendContent(str)) {
                hasAppendContent = true;
                appendContent = str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeNewContent() {
        long pos = fileLength;
        if (hasAppendContent) {
            pos = fileLength - AppendContentBuilder.CONTENT_LENGTH;
        }
        try {
            file.seek(pos);
            String newcontent = AppendContentBuilder.newAppendContent();
            file.writeBytes(newcontent);
            hasAppendContent = true;
            appendContent = newcontent;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "ImageAppendContent{" +
                "appendContent='" + appendContent + '\'' +
                ", hasAppendContent=" + hasAppendContent +
                '}';
    }
}
