package com.fisherevans.miblio_theca.util.compare;

import com.fisherevans.miblio_theca.media.file.AudioFileWrapper;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;

import java.util.Comparator;

/**
 * Created by immortal on 5/10/2015.
 */
public class QualityComparator implements Comparator<MediaFileWrapper> {
    private static QualityComparator instance = null;

    public static QualityComparator getInstance() {
        if(instance == null)
            instance = new QualityComparator();
        return instance;
    }

    @Override
    public int compare(MediaFileWrapper a, MediaFileWrapper b) {
        if(a instanceof AudioFileWrapper && b instanceof AudioFileWrapper) {
            int brA = (int)((AudioFileWrapper)b).getAudioFile().getAudioHeader().getBitRateAsNumber();
            int brB = (int)((AudioFileWrapper)b).getAudioFile().getAudioHeader().getBitRateAsNumber();
            return brB - brA;
        } else {
            return 0;
        }
    }
}
