package com.fisherevans.miblio_theca.formatter.segment;

import com.fisherevans.miblio_theca.exception.InsufficientKeyDataException;
import com.fisherevans.miblio_theca.formatter.Formatter;
import com.fisherevans.miblio_theca.formatter.filter.SegmentFilter;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;

/**
 * Created by h13730 on 5/12/2015.
 */
public class PlaceholderSegment implements FormatSegment {
    private static final String LITERAL_PATTERN = "^'(.*)'$";

    private String[] _keys;
    private SegmentFilter[] _filters;
    private String _configLine;

    // [KEY1,KEY2|FILTER1:ARG1,ARG2|FILTER2]

    public PlaceholderSegment(Formatter fileNameFormatter, String configLine) {
        _configLine = configLine;
        String[] pipeSplit = configLine.split("\\|");
        _keys = pipeSplit[0].split(",");
        _filters = new SegmentFilter[pipeSplit.length-1];
        for(int filterID = 1;filterID < pipeSplit.length;filterID++) {
            String[] colonSplit = pipeSplit[filterID].split(":", 2);
            String[] commaSplit = colonSplit.length > 1 ? colonSplit[1].split(",") : new String[0];
            _filters[filterID-1] = fileNameFormatter.getFilter(colonSplit[0], commaSplit);
        }
    }

    @Override
    public String getDescriptor() {
        return _configLine;
    }

    @Override
    public String getFieldText(MediaFileWrapper mediaFile) throws InsufficientKeyDataException {
        for(String key:_keys) {
            String result;
            if(key.matches(LITERAL_PATTERN))
                result = key.replaceAll(LITERAL_PATTERN, "$1");
            else
                result = mediaFile.keyLookup(key);
            result = result.replaceAll("[\\\\/]", "-").replaceAll("\\.", " ").trim();
            if(result.length() > 0)
                return result;
        }
        throw new InsufficientKeyDataException(_keys);
    }

    @Override
    public String filterText(String text) {
        for(SegmentFilter filter:_filters)
            text = filter.filter(text);
        return text;
    }
}
