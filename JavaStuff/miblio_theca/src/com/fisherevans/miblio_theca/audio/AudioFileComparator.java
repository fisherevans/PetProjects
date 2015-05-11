package com.fisherevans.miblio_theca.audio;

import org.jaudiotagger.audio.AudioFile;

import java.util.Comparator;

/**
 * Created by immortal on 5/10/2015.
 */
public class AudioFileComparator implements Comparator<AudioFile> {
    private static AudioFileComparator instance = null;

    public static AudioFileComparator getInstance() {
        if(instance == null)
            instance = new AudioFileComparator();
        return instance;
    }

    @Override
    public int compare(AudioFile a, AudioFile b) {
        return (int)(b.getAudioHeader().getBitRateAsNumber() - a.getAudioHeader().getBitRateAsNumber());
    }
}
