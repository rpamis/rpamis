package com.rpamis.common.utils;

import cn.hutool.core.io.FileUtil;

import java.io.File;

/**
 * 文件工具类
 *
 * @author benym
 * @date 2023/6/2 18:00
 */
public class FileUtils {

    /**
     * 生成gitkeep文件占位
     *
     * @param path path
     */
    public static void generateGitKeepFiles(String path) {
        File directory = new File(path);
        generateGitKeepFilesRecursively(directory);
    }

    /**
     * 递归在目录下无文件的目录生成.gitkeep文件
     *
     * @param directory directory
     */
    public static void generateGitKeepFilesRecursively(File directory) {
        if (directory.isDirectory() && FileUtil.isEmpty(directory)) {
            String gitKeepFilePath = new File(directory, ".gitkeep").getAbsolutePath();
            FileUtil.touch(gitKeepFilePath);
        } else if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    generateGitKeepFilesRecursively(file);
                }
            }
        }
    }
}
