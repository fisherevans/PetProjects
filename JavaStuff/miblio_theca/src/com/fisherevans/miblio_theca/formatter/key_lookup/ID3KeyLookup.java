package com.fisherevans.miblio_theca.formatter.key_lookup;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by immortal on 5/10/2015.
 */
public class ID3KeyLookup implements KeyLookup {
    private File _file;
    private AudioFile _audioFile;
    private Tag _tag;

    public ID3KeyLookup(File file) {
        try {
            _file = file;
            _audioFile = AudioFileIO.read(_file);
            _tag = _audioFile.getTag();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String value(String key) {
        FieldKey fieldKey = FieldKey.valueOf(key);
        if(fieldKey == null)
            return "";
        String value = _tag.getFirst(fieldKey);
        return value == null ? "" : value;
    }
}
