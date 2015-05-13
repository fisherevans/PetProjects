package com.fisherevans.miblio_theca.formatter.segment;

        import com.fisherevans.miblio_theca.exception.InsufficientKeyDataException;
        import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;

/**
 * Created by h13730 on 5/12/2015.
 */
public interface FormatSegment {
    public String getDescriptor();

    public String getFieldText(MediaFileWrapper mediaFile) throws InsufficientKeyDataException;

    public String filterText(String text);
}
