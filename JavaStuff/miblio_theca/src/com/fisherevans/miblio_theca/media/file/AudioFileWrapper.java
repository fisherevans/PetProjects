package com.fisherevans.miblio_theca.media.file;

import com.fisherevans.miblio_theca.util.StringUtil;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;

/**
 * Created by immortal on 5/10/2015.
 */
public class AudioFileWrapper extends MediaFileWrapper {
    private static String DISPLAY_FORMAT = "%-20s | %-20s | %-20s | %-20s | %5s | %5s | %7s";

    private AudioFile _audioFile;
    private Integer _bitRate;
    private Integer _lengthSeconds;
    private String _lengthPretty;

    public AudioFileWrapper(File file) {
        super(file);
    }

    @Override
    public String getDisplayColumns() {
        return String.format(DISPLAY_FORMAT, "Artist", "Album", "Album Artist", "Track", "Title", "Length", "Bit Rate");
    }

    @Override
    public String getDisplay() {
        if(exists()) {
            Tag tag = _audioFile.getTag();
            return String.format(DISPLAY_FORMAT, tag.getFirst(FieldKey.ARTIST), tag.getFirst(FieldKey.ALBUM),
                    tag.getFirst(FieldKey.ALBUM_ARTIST), tag.getFirst(FieldKey.TRACK), tag.getFirst(FieldKey.TITLE),
                    _lengthPretty, _bitRate + "");
        } else
            return "";
    }

    @Override
    protected void refreshFile() {
        try {
            _audioFile = AudioFileIO.read(getFile());
            _bitRate = (int) _audioFile.getAudioHeader().getBitRateAsNumber();
            _lengthSeconds = _audioFile.getAudioHeader().getTrackLength();
            _lengthPretty = StringUtil.getMinuteSeconds(_lengthSeconds);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    protected void nullifyFile() {
        _audioFile = null;
        _bitRate = null;
        _lengthSeconds = null;
        _lengthPretty = null;
    }

    public AudioFile getAudioFile() {
        return _audioFile;
    }
}
