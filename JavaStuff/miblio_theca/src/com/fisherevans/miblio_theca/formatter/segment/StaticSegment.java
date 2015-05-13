package com.fisherevans.miblio_theca.formatter.segment;

import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;

/**
 * Created by h13730 on 5/12/2015.
 */
public class StaticSegment implements FormatSegment {
    private String _text;

    public StaticSegment(String text) {
        _text = text;
    }

    @Override
    public String getText(MediaFileWrapper mediaFile) {
        return _text;
    }
}
