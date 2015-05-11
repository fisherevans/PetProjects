package com.fisherevans.miblio_theca.formatter;

import com.fisherevans.miblio_theca.formatter.filter.Filter;
import com.fisherevans.miblio_theca.formatter.filter.ZeroPadFilter;
import com.fisherevans.miblio_theca.formatter.key_lookup.KeyLookup;
import com.fisherevans.miblio_theca.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by immortal on 5/10/2015.
 */
public class FileNameFormatter {

    // [FIELedddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddD1,FIELD2,'String Literal'|FILTER1:ARG1,ARG2|FILTER2:ARG3,ARG4]
    // [ALBUM_ARTIST,ARTIST,'(No Artist)']/[ALBUM,'(No Album)']/[ARTIST|APPEND:' - '][ALBUM|APPEND:' - '][TRACK,'0'|ZERO_PAD:2] - [TITLE,'(No Title)']
    // [ALBUM_ARTIST,ARTIST]/[ALBUM]/[ARTIST,ALBUM_ARTIST] - [ALBUM] - [TRACK|ZERO_PAD:2] - [TITLE]

    private File _baseFolder;
    private String _format;
    private List<Object> _parsedFormat;
    private Map<String, Constructor> _filters;

    public FileNameFormatter(File baseFolder) {
        _baseFolder = baseFolder;
        _filters = new HashMap<>();
    }

    public void setFormat(String format) {
        _parsedFormat = new LinkedList<>();
        _format = format;
        int index = 0;
        while(index < format.length()) {
            int start = format.indexOf("[", index);
            if(start == -1) {
                _parsedFormat.add(format.substring(index));
                break;
            } else {
                int end = format.indexOf("]", start);
                String plainText = format.substring(index, start);
                if(plainText.length() > 0)
                    _parsedFormat.add(plainText);
                _parsedFormat.add(new Placeholder(this, format.substring(start + 1, end)));
                index = end + 1;
            }
        }
    }

    public String getFormat() {
        return _format;
    }

    public File getBaseFolder() {
        return _baseFolder;
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

    public void registerFilter(String filterName, Class filterClass) {
        Constructor constructor = null;
        try {
            constructor = filterClass.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        _filters.put(filterName, constructor);
    }

    public File getFormattedFile(File file, KeyLookup keyLookup) {
        try {
            String path = "";
            for(Object pathElement:_parsedFormat) {
                if(pathElement instanceof String) {
                    path += pathElement;
                } else if(pathElement instanceof Placeholder) {
                    Placeholder placeholder = (Placeholder) pathElement;
                    path += placeholder.compute(keyLookup);
                }
            }
            path = path.replaceAll("[ ]+", " ").trim().replaceAll("\\.+$", "").trim();
            path += "." + FileUtil.getFileExtension(file);
            return new File(_baseFolder.getPath() + "\\" + path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FileNameFormatter getDefaultFormatter(File dir, String format) {
        FileNameFormatter formatter = new FileNameFormatter(dir);
        formatter.registerFilter("ZERO_PAD", ZeroPadFilter.class);
        formatter.setFormat(format);
        return formatter;
    }
}
