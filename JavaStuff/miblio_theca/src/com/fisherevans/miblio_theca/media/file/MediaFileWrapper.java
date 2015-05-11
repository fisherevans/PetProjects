package com.fisherevans.miblio_theca.media.file;

import com.fisherevans.miblio_theca.util.FileUtil;

import java.io.File;

/**
 * Created by immortal on 5/10/2015.
 */
public abstract class MediaFileWrapper {
    private File _file;
    private boolean _exists;
    private String _md5;
    private Long _size = null;

    public MediaFileWrapper(File file) {
        _file = file;
        refresh();
    }

    public void refresh() {
        if(_file.exists()) {
            _exists = true;
            _md5 = FileUtil.getMD5(_file);
            _size = _file.length();
            refreshFile();
        } else {
            _exists = false;
            _md5 = null;
            _size = null;
            nullifyFile();
        }
    }

    public abstract String getDisplayColumns();

    public abstract String getDisplay();

    protected abstract void refreshFile();

    protected abstract void nullifyFile();

    public File getFile() {
        return _file;
    }

    public boolean exists() {
        return _exists;
    }

    public String getMD5() {
        return _md5;
    }

    public Long getSize() {
        return _size;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MediaFileWrapper) {
            MediaFileWrapper other = (MediaFileWrapper) obj;
            return _file.equals(other.getFile());
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return _file.hashCode();
    }
}
