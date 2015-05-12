package com.fisherevans.miblio_theca.formatter.filter;

/**
 * Created by immortal on 5/10/2015.
 */
public class ZeroPadFilter extends SegmentFilter {
    public static final String NAME = "ZERO_PAD";
    private static final Integer DEFAULT_PADDING_LENGTH = 0;

    private Integer _paddingLength = null;

    private int getPaddingLength() {
        if(_paddingLength == null) {
            if(getArguments().length == 0)
                _paddingLength = DEFAULT_PADDING_LENGTH;
            else {
                String lengthArgument = getArguments()[0];
                try {
                    _paddingLength = Integer.parseInt(lengthArgument);
                } catch(Exception e) {
                    System.out.println("Failed to parse zero padding length: " + lengthArgument);
                    _paddingLength = DEFAULT_PADDING_LENGTH;
                }
            }
        }
        return _paddingLength;
    }

    @Override
    public String filter(String input) {
        int paddingLength = getPaddingLength();
        for(;input.length() < paddingLength;input = "0" + input);
        return input;
    }
}
