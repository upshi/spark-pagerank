package cn.upshi.sparkpagerank.util;

import java.io.File;

/**
 * spark-pagerank cn.upshi.sparkpagerank.util
 * 描述：
 * 时间：2017-4-10 16:42.
 */

public class FileUtil {

    private static File linkFolder = null;
    private static String linkPath = null;

    static {
        linkPath = PropUtil.get("linkPath");
        linkFolder = new File(linkPath);
        if(!linkFolder.exists()) {
            linkFolder.mkdir();
        }
    }

    public static void removeOldFile(int taskId) {
        File taskLink = new File(linkFolder, taskId + ".txt");
        if(taskLink.exists()) {
            taskLink.delete();
        }
    }

    public static String linkFileName(int taskId) {
        return linkPath + taskId + ".txt";
    }

    public static void main(String[] args) {
        removeOldFile(1);
    }

}
