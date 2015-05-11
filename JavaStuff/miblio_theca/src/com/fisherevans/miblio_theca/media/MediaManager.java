package com.fisherevans.miblio_theca.media;

import com.fisherevans.miblio_theca.Config;
import com.fisherevans.miblio_theca.formatter.FileNameFormatter;
import com.fisherevans.miblio_theca.formatter.key_lookup.ID3KeyLookup;
import com.fisherevans.miblio_theca.util.FileUtil;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by immortal on 5/10/2015.
 */
public class MediaManager {
    public static final String[] AUDIO_EXTENSIONS = new String[] { "mp3", "wma", "mp4", "wav" };

    public static List<AudioFile> getInputAudioFiles() {
        Config config = Config.getInstance();
        List<AudioFile> audioFiles = new LinkedList<>();
        try {
            for(File file: FileUtil.getFiles(config.INPUT_DIR, config.INPUT_RECURSION, AUDIO_EXTENSIONS)) {
                AudioFile audioFile = AudioFileIO.read(file);
                audioFiles.add(audioFile);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return audioFiles;
    }

    public static List<File> getOutoutAudioFiles(List<AudioFile> inputAudioFiles) {
        Config config = Config.getInstance();
        FileNameFormatter formatter = FileNameFormatter.getDefaultFormatter(config.OUTPUT_AUDIO_DIR, config.OUTPUT_AUDIO_FORMAT);
        List<File> outputFiles = new LinkedList<>();
        for(AudioFile inputAudioFile:inputAudioFiles) {
            File outputFile = formatter.getFormattedFile(inputAudioFile.getFile(), new ID3KeyLookup(inputAudioFile.getFile()));
            outputFiles.add(outputFile);
        }
        return outputFiles;
    }
}
