package com.fisherevans.miblio_theca.formatter.key_lookup;

import com.fisherevans.miblio_theca.media.file.AudioFileWrapper;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;
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
    private AudioFileWrapper _audioFile;

    public ID3KeyLookup(MediaFileWrapper file) {
        if(!(file instanceof AudioFileWrapper))
            throw new RuntimeException("ID3 expects audio wrapper.");
        _audioFile = (AudioFileWrapper) file;
    }

    @Override
    public String value(String key) {
        FieldKey fieldKey = FieldKey.valueOf(key);
        if(fieldKey == null)
            return "";
        String value = _audioFile.getAudioFile().getTag().getFirst(fieldKey);
        return value == null ? "" : value;
    }
}
