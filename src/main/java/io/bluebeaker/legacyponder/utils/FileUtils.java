package io.bluebeaker.legacyponder.utils;

import java.io.File;
import java.util.List;

public class FileUtils {
    /**
     * @param dir the directory to list files from
     * @param list the list to add files to
     */
    public static void listFilesRecursive(File dir, List<File> list) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile()) {
                list.add(file);
            }
            if (file.isDirectory()) {
                listFilesRecursive(file,list);
            }
        }
    }
}
