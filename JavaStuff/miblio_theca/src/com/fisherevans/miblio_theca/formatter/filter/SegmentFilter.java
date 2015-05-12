package com.fisherevans.miblio_theca.formatter.filter;

/**
 * Created by immortal on 5/10/2015.
 */
public abstract class SegmentFilter {
    private String[] _arguments = new String[0];

    public void setArguments(String[] arguments) {
        _arguments = arguments;
    }

    public String[] getArguments() {
        return _arguments;
    }

    public abstract String filter(String input);
}
