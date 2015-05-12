package com.fisherevans.miblio_theca.media;

import com.fisherevans.miblio_theca.Config;
import com.fisherevans.miblio_theca.formatter.Formatter;
import com.fisherevans.miblio_theca.formatter.key_lookup.KeyLookup;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;
import com.fisherevans.miblio_theca.util.FileUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by immortal on 5/10/2015.
 */
public class MediaManager {
    public static <T1 extends MediaFileWrapper, T2 extends KeyLookup> Map<String, Set<T1>> readFiles(Class<T1> mediaFileClass, Class<T2> keyLookupClass, String[] validExtensions, File outputDir, String format) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T1> mediaConstructor = getMediaFileConstructor(mediaFileClass);
        Constructor<T2> lookupConstructor = getMediaFileConstructor(keyLookupClass);
        Formatter formatter = com.fisherevans.miblio_theca.formatter.Formatter.getDefaultFormatter(format);
        Map<String, Set<T1>> fileMap = new HashMap<>();
        for(File inputFile: FileUtil.getFiles(Config.getInstance().INPUT_DIR, Config.getInstance().INPUT_RECURSION, validExtensions)) {
            T1 inputMediaFile = mediaConstructor.newInstance(inputFile);
            String formattedOutput = formatter.compute(constructorInstance(lookupConstructor, inputMediaFile));
            if(!fileMap.containsKey(formattedOutput))
                fileMap.put(formattedOutput, new HashSet<T1>());
            fileMap.get(formattedOutput).add(inputMediaFile);
        }
        for(String formattedOutput:fileMap.keySet()) {
            for(String validExtension:validExtensions) {
                File outputFile = new File(outputDir.getAbsolutePath() + "\\" + formattedOutput + "." + validExtension);
                T1 outputMediaFile = mediaConstructor.newInstance(outputFile);
                if(outputMediaFile.exists())
                    fileMap.get(formattedOutput).add(outputMediaFile);
            }
        }
        return fileMap;
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
}
