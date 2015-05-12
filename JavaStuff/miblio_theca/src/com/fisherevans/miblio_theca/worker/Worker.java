package com.fisherevans.miblio_theca.worker;

import com.fisherevans.miblio_theca.Config;
import com.fisherevans.miblio_theca.formatter.key_lookup.KeyLookup;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;

import java.io.File;

/**
 * Created by h13730 on 5/12/2015.
 */
public abstract class Worker <T1 extends MediaFileWrapper, T2 extends KeyLookup> {
    public Class<T1> _mediaFileWrapperClass;
    public Class<T2> _keyLookupClass;

    private Config _config;

    public Worker(Class<T1> mediaFileWrapperClass, Class<T2> keyLookupClass) {
        _mediaFileWrapperClass = mediaFileWrapperClass;
        _keyLookupClass = keyLookupClass;

        _config = Config.getInstance();
    }

    public Class<T1> getMediaFileWrapperClass() {
        return _mediaFileWrapperClass;
    }

    public Class<T2> getKeyLookupClass() {
        return _keyLookupClass;
    }

    public Config getConfig() {
        return _config;
    }

    public abstract String[] getValidExtensions();

    public abstract File getOutputDirectory();

    public abstract String getFormat();
}
