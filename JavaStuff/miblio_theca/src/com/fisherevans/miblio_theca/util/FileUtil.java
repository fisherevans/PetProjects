package com.fisherevans.miblio_theca.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by immortal on 5/10/2015.
 */
public class FileUtil {
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.contains("."))
            return fileName.replaceAll(".*\\.", "").toLowerCase();
        else
            return "";
    }

    public static boolean hasValidExtension(File file, String... validExtensions) {
        String fileExtension = FileUtil.getFileExtension(file);
        for(String validExtension:validExtensions) {
            if(fileExtension.equalsIgnoreCase(validExtension)) {
                return true;
            }
        }
        return false;
    }

    public static List<File> getFiles(File folder, int recursionDepth, String ... validExtensions) {
        List<File> files = new ArrayList<>();
        for(File child:folder.listFiles()) {
            if(child.exists() && child.canRead()) {
                if(child.isFile() && FileUtil.hasValidExtension(child, validExtensions)) {
                    files.add(child);
                } else if(recursionDepth != 0 && child.isDirectory()) {
                    files.addAll(getFiles(child, recursionDepth-1, validExtensions));
                }
            }
        }
        return files;
    }

    public static String getMD5(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            String md5 = DigestUtils.md5Hex(fis);
            fis.close();
            return md5;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void copy(File from, File to) {
        if(from.equals(to))
            return;
        try {
            System.out.println("  Copying " + from.getAbsolutePath() + " -> " + to.getAbsolutePath());
            to.getParentFile().mkdirs();
            Files.copy(from.toPath(), to.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void move(File from, File to) {
        if(from.equals(to))
            return;
        try {
            System.out.println("  Moving " + from.getAbsolutePath() + " -> " + to.getAbsolutePath());
            to.getParentFile().mkdirs();
            Files.move(from.toPath(), to.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void delete(File file) {
        System.out.println("  Deleting " + file.getAbsolutePath());
        file.delete();
    }
}
