package com.fisherevans.miblio_theca.worker;

import com.fisherevans.miblio_theca.Config;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;

import java.io.File;

/**
 * Created by h13730 on 5/12/2015.
 */
public abstract class Worker <T1 extends MediaFileWrapper> {
    public Class<T1> _mediaFileWrapperClass;

    private Config _config;

    public Worker(Class<T1> mediaFileWrapperClass) {
        _mediaFileWrapperClass = mediaFileWrapperClass;
        _config = Config.getInstance();
    }

    public Class<T1> getMediaFileWrapperClass() {
        return _mediaFileWrapperClass;
    }

    public Config getConfig() {
        return _config;
    }

    public abstract String[] getValidExtensions();

    public abstract File getOutputDirectory();

    public abstract String getFormat();
}
