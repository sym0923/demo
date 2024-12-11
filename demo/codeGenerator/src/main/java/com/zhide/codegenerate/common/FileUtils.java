package com.zhide.codegenerate.common;

import java.io.File;

public class FileUtils {
    public static void delete(File folder) {
        if(folder.exists()){
            if(folder.isDirectory()) {
                File[] files = folder.listFiles();
                if(files != null) { // 如果文件夹为空，files可能为null
                    for(File file : files) {
                        delete(file); // 递归删除子文件夹和文件
                    }
                }
            }
            folder.delete(); // 删除空文件夹或者文件
        }
    }
}
