package com.fisherevans.miblio_theca;

import com.fisherevans.miblio_theca.audio.MediaFileWrapperDisplayComparator;
import com.fisherevans.miblio_theca.audio.MediaFileWrapperQualityComparator;
import com.fisherevans.miblio_theca.formatter.key_lookup.ID3KeyLookup;
import com.fisherevans.miblio_theca.formatter.key_lookup.KeyLookup;
import com.fisherevans.miblio_theca.media.MediaManager;
import com.fisherevans.miblio_theca.media.file.AudioFileWrapper;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;

import java.io.File;
import java.util.*;

/**
 * Created by immortal on 5/10/2015.
 */
public class Miblio<T1 extends MediaFileWrapper, T2 extends KeyLookup, T3 extends Comparator<T1>> {
    public static final int TYPE_AUDIO = 1;

    private String[] _validExtensions;

    private Class<T1> _mediaFileWrapperClass;
    private Class<T2> _keyLookupClass;
    private Class<T3> _comparatorClass;

    private File _outputDir;
    private String _format;

    private Map<T1, Set<T1>> _fileMap;
    private Map<T1, T1> _copyMap;

    public static void main(String[] args) throws Exception {
        Config config = Config.getInstance();
        //int mediaType = 0;
        //do {
        //    mediaType = InputUtil.getInt();
        //    if(mediaType == TYPE_AUDIO) {
                new Miblio(MediaManager.AUDIO_EXTENSIONS, AudioFileWrapper.class, ID3KeyLookup.class, MediaFileWrapperQualityComparator.class, config.OUTPUT_AUDIO_DIR, config.OUTPUT_AUDIO_FORMAT);
        //    }
        //} while(mediaType != 0);

    }

    public Miblio(String[] validExtensions, Class<T1> mediaFileWrapperClass, Class<T2> keyLookupClass, Class<T3> comparatorClass, File outputDir, String format) {
        _validExtensions = validExtensions;
        _mediaFileWrapperClass = mediaFileWrapperClass;
        _keyLookupClass = keyLookupClass;
        _comparatorClass = comparatorClass;

        _outputDir = outputDir;
        _format = format;

        readFiles();

        List<T1> keys = new ArrayList<>(_fileMap.size());
        keys.addAll(_fileMap.keySet());
        Collections.sort(keys, MediaFileWrapperDisplayComparator.getInstance());
        for(T1 outFile:keys) {
            System.out.println(outFile.getDisplayColumns());
            for(T1 inFile:_fileMap.get(outFile)) {
                System.out.println(inFile.getDisplay());
            }
            System.out.println();
        }
    }

    private void readFiles() {
        System.out.print("Scanning files...");
        try {
            _fileMap = MediaManager.readFiles(_mediaFileWrapperClass, _keyLookupClass, _validExtensions, _outputDir, _format);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(" Done.");
    }

/*
    public  void main() throws Exception {
        System.out.println("Finding new media files...");
        System.out.println("Generating new file names...");
        List<File> outputFiles = MediaManager.getOutputAudioFiles(inputAudioFiles);
        System.out.println("Mapping new file locations...");


        for(File toFile:fileMap.keySet()) {
            String toMD5 = "";
            if(toFile.exists()) {
                toMD5 = FileUtil.getMD5(toFile);
                fileMap.get(toFile).add(AudioFileIO.read(toFile));
            }
            Collections.sort(fileMap.get(toFile), MediaFileWrapperQualityComparator.getInstance());
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
    */
}
