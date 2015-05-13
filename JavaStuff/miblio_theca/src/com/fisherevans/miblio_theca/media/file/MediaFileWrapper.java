package com.fisherevans.miblio_theca.media.file;

import com.fisherevans.miblio_theca.util.FileUtil;

import java.io.File;

/**
 * Created by immortal on 5/10/2015.
 */
public abstract class MediaFileWrapper implements Comparable<MediaFileWrapper> {
    private File _file;
    private boolean _exists;
    private String _md5;
    private Long _size = null;

    public MediaFileWrapper(File file) throws Exception {
        _file = file;
        refresh();
    }

    public void refresh() throws Exception {
        if(_file.exists() && _file.isFile() && _file.canRead()) {
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

    public boolean containWithin(File folder) {
        return _file.getAbsolutePath().startsWith(folder.getAbsolutePath() + File.separator);
    }

    public abstract String getDetails();

    public abstract String keyLookup(String key);

    public abstract void keySet(String key, String value) throws Exception;

    protected abstract void refreshFile() throws Exception;

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
