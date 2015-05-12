package com.fisherevans.miblio_theca.formatter.segment;

import com.fisherevans.miblio_theca.formatter.key_lookup.KeyLookup;

/**
 * Created by h13730 on 5/12/2015.
 */
public interface FormatSegment {
    public String getText(KeyLookup keyLookup);
}
