package com.fisherevans.miblio_theca.media.file;

import com.fisherevans.miblio_theca.util.FileUtil;

import java.io.File;

/**
 * Created by immortal on 5/10/2015.
 */
public class MediaFileWraper {
    private File _file;
    private String _md5;

    public MediaFileWraper(File file) {
        _file = file;
        FileUtil.getMD5(_file);
    }

    public File getFile() {
        return _file;
    }

    public String getMD5() {
        return _md5;
    }
}
