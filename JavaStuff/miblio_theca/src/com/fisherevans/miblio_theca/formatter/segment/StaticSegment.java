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
    public String getDescriptor() {
        return _text;
    }

    @Override
    public String getFieldText(MediaFileWrapper mediaFile) {
        return _text;
    }

    @Override
    public String filterText(String text) {
        return text;
    }
}
