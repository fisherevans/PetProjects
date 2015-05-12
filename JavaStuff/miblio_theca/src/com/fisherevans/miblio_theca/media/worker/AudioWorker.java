package com.fisherevans.miblio_theca.media.worker;

import com.fisherevans.miblio_theca.formatter.key_lookup.ID3KeyLookup;
import com.fisherevans.miblio_theca.formatter.key_lookup.KeyLookup;
import com.fisherevans.miblio_theca.media.file.AudioFileWrapper;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;

import java.io.File;

/**
 * Created by h13730 on 5/12/2015.
 */
public class AudioWorker extends Worker<AudioFileWrapper, ID3KeyLookup> {
    private static String[] VALID_EXTENSIONS = new String[] { "mp3", "wma", "mp4", "wav" };

    public AudioWorker() {
        super(AudioFileWrapper.class, ID3KeyLookup.class);
    }

    @Override
    public String[] getValidExtensions() {
        return VALID_EXTENSIONS;
    }

    @Override
    public File getOutputDirectory() {
        return getConfig().OUTPUT_AUDIO_DIR;
    }

    @Override
    public String getFormat() {
        return getConfig().OUTPUT_AUDIO_FORMAT;
    }
}
