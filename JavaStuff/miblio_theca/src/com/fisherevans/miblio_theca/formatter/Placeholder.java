package com.fisherevans.miblio_theca.formatter;

import com.fisherevans.miblio_theca.formatter.filter.Filter;
import com.fisherevans.miblio_theca.formatter.key_lookup.KeyLookup;

import java.security.Key;

/**
 * Created by immortal on 5/10/2015.
 */
public class Placeholder {
    FileNameFormatter _fileNameFormatter;
    private String[] _keys;
    private Filter[] _filters;

    public Placeholder(FileNameFormatter fileNameFormatter, String configLine) {
        _fileNameFormatter = fileNameFormatter;
        String[] pipeSplit = configLine.split("\\|");
        _keys = pipeSplit[0].split(",");
        _filters = new Filter[pipeSplit.length-1];
        for(int filterID = 1;filterID < pipeSplit.length;filterID++) {
            String[] colonSplit = pipeSplit[filterID].split(":", 2);
            String[] commaSplit = colonSplit.length > 1 ? colonSplit[1].split(",") : new String[0];
            _filters[filterID-1] = _fileNameFormatter.getFilter(colonSplit[0], commaSplit);
        }
    }

    public String compute(KeyLookup lookup) {
        String result = "";
        for(String key:_keys) {
            result = lookup.value(key).replaceAll("[\\\\/]", "-").replaceAll("\\.", " ").trim();
            if(result.length() > 0)
                break;
        }
        for(Filter filter:_filters) {
            result = filter.filter(result);
        }
        return result;
    }
}
