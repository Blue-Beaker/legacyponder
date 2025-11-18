package io.bluebeaker.legacyponder.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathUtils {
    public static List<File> listRecursive(File file){
        List<File> files = new ArrayList<>();
        listRecursive(file,files);
        return files;
    }
    public static void listRecursive(File file, List<File> list){
        File[] files = file.listFiles();
        if(files==null) return;

        for (File listFile : files) {
            if(listFile.isDirectory()){
                listRecursive(listFile,list);
            }else {
                list.add(listFile);
            }
        }
    }
}
