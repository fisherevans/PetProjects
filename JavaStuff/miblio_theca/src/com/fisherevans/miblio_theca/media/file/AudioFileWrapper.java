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
    private static String DISPLAY_FORMAT = "[Track: %s] [Length: %s] [BitRate: %skbs] [Format: %s]";

    private AudioFile _audioFile;
    private Integer _bitRate;
    private Integer _lengthSeconds;
    private String _lengthPretty;

    public AudioFileWrapper(File file) {
        super(file);
    }

/*
    @Override
    public String getDisplayColumns() {
        return String.format(DISPLAY_FORMAT, "Artist", "Album", "Album Artist", "Track", "Title", "Length", "Bit Rate");
    }
*/

    @Override
    public String getDetails() {
        if(exists()) {
            Tag tag = _audioFile.getTag();
            return String.format(DISPLAY_FORMAT, tag.getFirst(FieldKey.TRACK), _lengthPretty, _bitRate + "", _audioFile.getAudioHeader().getFormat());
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

    public Integer getBitRate() {
        return _bitRate;
    }

    public Integer getLengthSeconds() {
        return _lengthSeconds;
    }

    public String getLengthPretty() {
        return _lengthPretty;
    }

    @Override
    public int compareTo(MediaFileWrapper mediaFile) {
        if(mediaFile instanceof AudioFileWrapper) {
            AudioFileWrapper other = (AudioFileWrapper) mediaFile;
            return other.getBitRate() - getBitRate();
        } else
            return 0;
    }
}
