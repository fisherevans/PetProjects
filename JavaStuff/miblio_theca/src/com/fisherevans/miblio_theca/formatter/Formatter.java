package com.fisherevans.miblio_theca.formatter;

import com.fisherevans.miblio_theca.formatter.filter.Filter;
import com.fisherevans.miblio_theca.formatter.filter.ZeroPadFilter;
import com.fisherevans.miblio_theca.formatter.key_lookup.KeyLookup;
import com.fisherevans.miblio_theca.formatter.segment.FormatSegment;
import com.fisherevans.miblio_theca.formatter.segment.PlaceholderSegment;
import com.fisherevans.miblio_theca.formatter.segment.StaticSegment;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Created by immortal on 5/10/2015.
 */
public class Formatter {
    private Map<String, Constructor> _filters;
    private String _format;
    private List<FormatSegment> _segments;

    public Formatter() {
        _filters = new HashMap<>();
    }

    public void registerFilter(String filterName, Class filterClass) {
        Constructor constructor = null;
        try {
            constructor = filterClass.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        _filters.put(filterName, constructor);
    }

    public Filter getFilter(String filterName, String[] arguments) {
        try {
            Constructor constructor = _filters.get(filterName);
            if(constructor == null)
                return null;
            Filter filter = (Filter) constructor.newInstance();
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

    public String compute(KeyLookup keyLookup) {
        String text = "";
        for(FormatSegment segment:_segments)
            text += segment.getText(keyLookup);
        return text;
    }

    public static Formatter getDefaultFormatter(String format) {
        Formatter formatter = new Formatter();
        formatter.registerFilter(ZeroPadFilter.NAME, ZeroPadFilter.class);
        formatter.setFormat(format);
        return formatter;
    }
}