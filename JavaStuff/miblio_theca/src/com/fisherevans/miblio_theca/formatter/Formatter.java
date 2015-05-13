package com.fisherevans.miblio_theca.formatter;

import com.fisherevans.miblio_theca.exception.InsufficientKeyDataException;
import com.fisherevans.miblio_theca.formatter.filter.SegmentFilter;
import com.fisherevans.miblio_theca.formatter.filter.ZeroPadFilter;
import com.fisherevans.miblio_theca.formatter.segment.FormatSegment;
import com.fisherevans.miblio_theca.formatter.segment.PlaceholderSegment;
import com.fisherevans.miblio_theca.formatter.segment.StaticSegment;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Created by immortal on 5/10/2015.
 */
public class Formatter {
    private Map<String, Constructor<SegmentFilter>> _filters;
    private String _format;
    private List<FormatSegment> _segments;

    public Formatter() {
        _filters = new HashMap<>();
    }

    public void registerFilter(String filterName, Class filterClass) {
        Constructor<SegmentFilter> constructor = null;
        try {
            constructor = filterClass.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        _filters.put(filterName, constructor);
    }

    public SegmentFilter getFilter(String filterName, String[] arguments) {
        try {
            Constructor<SegmentFilter> constructor = _filters.get(filterName);
            if(constructor == null)
                return null;
            SegmentFilter filter = (SegmentFilter) constructor.newInstance();
            filter.setArguments(arguments);
            return filter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void unregisterFilter(String filterName) {
        _filters.remove(filterName);
    }

    public void setFormat(String format) {
        _segments = new LinkedList<>();
        _format = format;
        int index = 0;
        while(index < format.length()) {
            int start = format.indexOf("[", index);
            if(start == -1) {
                _segments.add(new StaticSegment(format.substring(index)));
                break;
            } else {
                int end = format.indexOf("]", start);
                String plainText = format.substring(index, start);
                if(plainText.length() > 0)
                    _segments.add(new StaticSegment(plainText));
                _segments.add(new PlaceholderSegment(this, format.substring(start + 1, end)));
                index = end + 1;
            }
        }
    }

    public String getFormat() {
        return _format;
    }

    public String compute(MediaFileWrapper mediaFile) throws InsufficientKeyDataException {
        String segmentText, text = "";
        Set<String> missingKeys = new HashSet<>();
        for(FormatSegment segment:_segments) {
            try {
                segmentText = segment.getFieldText(mediaFile);
                text += segment.filterText(segmentText);
            } catch (InsufficientKeyDataException e) {
                for(String key:e.getKeys())
                    missingKeys.add(key);
            }
        }
        if(missingKeys.size() > 0)
            throw new InsufficientKeyDataException(missingKeys.toArray(new String[0]));
        return text;
    }

    public static Formatter getDefaultFormatter(String format) {
        Formatter formatter = new Formatter();
        formatter.registerFilter(ZeroPadFilter.NAME, ZeroPadFilter.class);
        formatter.setFormat(format);
        return formatter;
    }
}
