package com.fisherevans.miblio_theca.audio;

import com.fisherevans.miblio_theca.media.file.AudioFileWrapper;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;

import javax.print.attribute.standard.Media;
import java.util.Comparator;

/**
 * Created by immortal on 5/10/2015.
 */
public class MediaFileWrapperQualityComparator implements Comparator<MediaFileWrapper> {
    private static MediaFileWrapperQualityComparator instance = null;

    public static MediaFileWrapperQualityComparator getInstance() {
        if(instance == null)
            instance = new MediaFileWrapperQualityComparator();
        return instance;
    }

    @Override
    public int compare(MediaFileWrapper a, MediaFileWrapper b) {
        if(a instanceof AudioFileWrapper && b instanceof AudioFileWrapper) {
            return (int)(((AudioFileWrapper)b).getAudioFile().getAudioHeader().getBitRateAsNumber()
                    - ((AudioFileWrapper)a).getAudioFile().getAudioHeader().getBitRateAsNumber());
        } else {
            return 0;
        }
    }
}
