package com.fisherevans.miblio_theca;

import com.fisherevans.miblio_theca.audio.AudioFileComparator;
import com.fisherevans.miblio_theca.formatter.FileNameFormatter;
import com.fisherevans.miblio_theca.formatter.filter.ZeroPadFilter;
import com.fisherevans.miblio_theca.formatter.key_lookup.ID3KeyLookup;
import com.fisherevans.miblio_theca.media.MediaManager;
import com.fisherevans.miblio_theca.util.FileUtil;
import com.fisherevans.miblio_theca.util.StringUtil;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by immortal on 5/10/2015.
 */
public class Driver {
    public static void main(String[] args) throws Exception {
        System.out.println("Loading settings...");
        Config config = Config.getInstance();
        System.out.println("Finding new media files...");
        List<AudioFile> inputAudioFiles = MediaManager.getInputAudioFiles();
        System.out.println("Generating new file names...");
        List<File> outputFiles = MediaManager.getOutoutAudioFiles(inputAudioFiles);
        System.out.println("Mapping new file locations...");


        for(File toFile:fileMap.keySet()) {
            String toMD5 = "";
            if(toFile.exists()) {
                toMD5 = FileUtil.getMD5(toFile);
                fileMap.get(toFile).add(AudioFileIO.read(toFile));
            }
            Collections.sort(fileMap.get(toFile), AudioFileComparator.getInstance());
            if(fileMap.get(toFile).size() > 1) {
                //System.out.println(toFile.getAbsolutePath());
                for(AudioFile fromFile:fileMap.get(toFile)) {
                    String path = fromFile.getFile().getAbsolutePath();
                    String length = StringUtil.getMinuteSeconds(fromFile.getAudioHeader().getTrackLength());
                    long bitRate = fromFile.getAudioHeader().getBitRateAsNumber();
                    String md5 = FileUtil.getMD5(fromFile.getFile());
                    String out = "> " + path + "(";
                    out += "Length=" + length + ", ";
                    out += "BitRate=" + bitRate + ", ";
                    out += "MD5=" + md5 + ") ";
                    if(toMD5.equals(md5))
                        out += "Same as stored";
                    //System.out.println(out);
                }
            }
        }
    }
}
