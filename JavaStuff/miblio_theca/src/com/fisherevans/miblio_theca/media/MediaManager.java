package com.fisherevans.miblio_theca.media;

import com.fisherevans.miblio_theca.Config;
import com.fisherevans.miblio_theca.exception.InsufficientKeyDataException;
import com.fisherevans.miblio_theca.formatter.Formatter;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;
import com.fisherevans.miblio_theca.util.FileUtil;
import com.fisherevans.miblio_theca.util.InputUtil;
import com.fisherevans.miblio_theca.util.StringUtil;
import com.sun.corba.se.spi.orbutil.fsm.Input;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by immortal on 5/10/2015.
 */
public class MediaManager {
    private static int BAR_WIDTH = 50;
    private static String CLEAR = "\r                                                                                                    ";

    private static Map<String, Map<String, String>> lastKeyEntries = new HashMap<>();

    public static <T1 extends MediaFileWrapper> Map<String, Set<T1>> readFiles(Class<T1> mediaFileClass, String[] validExtensions, File outputDir, String format) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T1> mediaConstructor = getMediaFileConstructor(mediaFileClass);
        Formatter formatter = com.fisherevans.miblio_theca.formatter.Formatter.getDefaultFormatter(format);
        Map<String, Set<T1>> fileMap = new HashMap<>();
        List<File> inputFiles = FileUtil.getFiles(Config.getInstance().INPUT_DIR, Config.getInstance().INPUT_RECURSION, validExtensions);
        int updateStep = Math.min(inputFiles.size()/BAR_WIDTH, 10);
        String bar = "";
        long startTime = System.currentTimeMillis();
        for(int id = 0;id < inputFiles.size();id++) {
            if(updateStep == 0 || id % updateStep == 0)
                bar = StringUtil.getFullProgressBar(startTime, BAR_WIDTH, (id + 1) / ((double) inputFiles.size()));
            File inputFile = inputFiles.get(id);
            System.out.print(CLEAR);
            System.out.println("\r  Scanning " + inputFile.getAbsolutePath());
            System.out.print(bar);
            ScannedFile<T1> scannedFile;
            try {
                scannedFile = readFile(mediaConstructor, formatter, inputFile);
            } catch(Exception e) {
                System.out.print(CLEAR);
                System.out.println("\r      Skipping file: " + e.getMessage());
                System.out.print(bar);
                continue;
            }
            if(!fileMap.containsKey(scannedFile.getFormattedOutput()))
                fileMap.put(scannedFile.getFormattedOutput(), new HashSet<T1>());
            fileMap.get(scannedFile.getFormattedOutput()).add(scannedFile.getMediaFileWrapper());
        }
        for(String formattedOutput:fileMap.keySet()) {
            for(int i = 0;i < 2;i++) {
                for(String validExtension:validExtensions) {
                    String extension = (i == 0 ? validExtension.toLowerCase() : validExtension.toUpperCase());
                    File outputFile = new File(outputDir.getAbsolutePath() + File.separator + formattedOutput + "." + extension);
                    T1 outputMediaFile = mediaConstructor.newInstance(outputFile);
                    if(outputMediaFile.exists())
                        fileMap.get(formattedOutput).add(outputMediaFile);
                }
            }
        }
        System.out.println(CLEAR);
        return fileMap;
    }

    private static <T1 extends MediaFileWrapper> ScannedFile readFile(Constructor<T1> mediaConstructor, Formatter formatter, File file) throws Exception {
        T1 inputMediaFile =  mediaConstructor.newInstance(file);
        boolean scanFile = true;
        while(scanFile) {
            try {
                String formattedOutput = formatter.compute(inputMediaFile);
                return new ScannedFile(inputMediaFile, formattedOutput);
            } catch(InsufficientKeyDataException e) {
                System.out.print(CLEAR);
                System.out.println("\r    Please fill in the missing data");
                for(String key:e.getKeys()) {
                    while(true) {
                        if(!lastKeyEntries.containsKey(file.getParent()))
                            lastKeyEntries.put(file.getParent(), new HashMap<String, String>());
                        Map<String, String> map = lastKeyEntries.get(file.getParent());
                        String previous = map.containsKey(key) ? map.get(key) : "";
                        System.out.print("    " + key + " [Default=" + previous + "]: ");
                        String input = InputUtil.getString().trim();
                        if(input.length() == 0)
                            input = previous;
                        try {
                            inputMediaFile.keySet(key, input);
                            map.put(key, input);
                            break;
                        } catch(Exception e2) {
                            System.out.println("Failed to update tag: " + e2.getMessage());
                        }
                    }
                }
            }
        }
        throw new Exception("User decided to skip tagging.");
    }

    private static <T> Constructor<T> getMediaFileConstructor(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(File.class);
            return constructor;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    private static <T> T constructorInstance(Constructor<T> constructor, Object argument) {
        try {
            return constructor.newInstance(argument);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    private static class ScannedFile<T1 extends MediaFileWrapper> {
        private T1 _mediaFileWrapper;
        private String _formattedOutput;

        public ScannedFile(T1 mediaFileWrapper, String formattedOutput) {
            _mediaFileWrapper = mediaFileWrapper;
            _formattedOutput = formattedOutput;
        }

        public T1 getMediaFileWrapper() {
            return _mediaFileWrapper;
        }

        public String getFormattedOutput() {
            return _formattedOutput;
        }
    }
}
