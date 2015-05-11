package com.fisherevans.miblio_theca.media;

import com.fisherevans.miblio_theca.Config;
import com.fisherevans.miblio_theca.formatter.FileNameFormatter;
import com.fisherevans.miblio_theca.formatter.key_lookup.ID3KeyLookup;
import com.fisherevans.miblio_theca.formatter.key_lookup.KeyLookup;
import com.fisherevans.miblio_theca.media.file.AudioFileWrapper;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;
import com.fisherevans.miblio_theca.util.FileUtil;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by immortal on 5/10/2015.
 */
public class MediaManager {
    public static final String[] AUDIO_EXTENSIONS = new String[] { "mp3", "wma", "mp4", "wav" };

    public static <T1 extends MediaFileWrapper, T2 extends KeyLookup> Map<T1, Set<T1>> readFiles(Class<T1> mediaFileClass, Class<T2> keyLookupClass, String[] validExtensions, File outputDir, String format) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T1> mediaConstructor = getMediaFileConstructor(mediaFileClass);
        Constructor<T2> lookupConstructor = getMediaFileConstructor(keyLookupClass);
        FileNameFormatter formatter = FileNameFormatter.getDefaultFormatter(outputDir, format);
        Map<T1, Set<T1>> fileMap = new HashMap<>();
        for(File inputFile: FileUtil.getFiles(Config.getInstance().INPUT_DIR, Config.getInstance().INPUT_RECURSION, validExtensions)) {
            T1 inputMediaFile = mediaConstructor.newInstance(inputFile);
            File outputFile = formatter.getFormattedFile(inputFile, constructorInstance(lookupConstructor, inputFile));
            T1 outputMediaFile = mediaConstructor.newInstance(outputFile);
            if(!fileMap.containsKey(outputMediaFile))
                fileMap.put(outputMediaFile, new HashSet<T1>());
            fileMap.get(outputMediaFile).add(inputMediaFile);
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
