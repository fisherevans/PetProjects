package com.fisherevans.miblio_theca.formatter.segment;

import com.fisherevans.miblio_theca.formatter.Formatter;
import com.fisherevans.miblio_theca.formatter.filter.SegmentFilter;
import com.fisherevans.miblio_theca.formatter.key_lookup.KeyLookup;

/**
 * Created by h13730 on 5/12/2015.
 */
public class PlaceholderSegment implements FormatSegment {
    private String[] _keys;
    private SegmentFilter[] _filters;

    // [KEY1,KEY2|FILTER1:ARG1,ARG2|FILTER2]

    public PlaceholderSegment(Formatter fileNameFormatter, String configLine) {
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
    public String getText(KeyLookup keyLookup) {
        String result = "";
        for(String key:_keys) {
            result = keyLookup.value(key).replaceAll("[\\\\/]", "-").replaceAll("\\.", " ").trim();
            if(result.length() > 0)
                break;
        }
        for(SegmentFilter filter:_filters)
            result = filter.filter(result);
        return result;
    }
}
