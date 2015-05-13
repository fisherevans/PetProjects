package com.fisherevans.miblio_theca.exception;

/**
 * Created by h13730 on 5/13/2015.
 */
public class InsufficientKeyDataException extends Exception {
    private String[] _keys;

    public InsufficientKeyDataException(String[] keys) {
        _keys = keys;
    }

    public String[] getKeys() {
        return _keys;
    }
}
