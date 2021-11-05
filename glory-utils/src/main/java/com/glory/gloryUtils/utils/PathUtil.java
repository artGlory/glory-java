package com.glory.gloryUtils.utils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.UUID;

/**
 * 路径工具
 */
public class PathUtil {
    /**
     * 获取桌面绝对路径 , 结尾没有分隔符
     * （通过不同系统的分隔符不同区分系统，支持window,unix）
     *
     * @return
     */
    public static String getDesktopPath() {
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        return desktopPath;
    }

    /**
     * 获取桌面绝对路径  ， 结尾有分隔符
     *
     * @return
     */
    public static String getDesktopPathAndSeparatorChar() {
        return getDesktopPath() + File.separatorChar;
    }

    /**
     * 获取桌面绝
     *
     * @return
     */
    public static String getRandomDesktopFilePath(String fileSuffix) {
        return getDesktopPath() + File.separatorChar + UUID.randomUUID().toString() + fileSuffix;
    }
}
