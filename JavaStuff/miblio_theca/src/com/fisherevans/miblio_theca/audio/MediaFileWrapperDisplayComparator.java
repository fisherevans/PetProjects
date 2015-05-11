package com.fisherevans.miblio_theca.audio;

import com.fisherevans.miblio_theca.media.file.AudioFileWrapper;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;

import javax.print.attribute.standard.Media;
import java.util.Comparator;

/**
 * Created by immortal on 5/10/2015.
 */
public class MediaFileWrapperDisplayComparator implements Comparator<MediaFileWrapper> {
    private static MediaFileWrapperDisplayComparator instance = null;

    public static MediaFileWrapperDisplayComparator getInstance() {
        if(instance == null)
            instance = new MediaFileWrapperDisplayComparator();
        return instance;
    }

    @Override
    public int compare(MediaFileWrapper a, MediaFileWrapper b) {
        return a.getDisplay().compareTo(b.getDisplay());
    }
}
