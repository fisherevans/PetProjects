package com.fisherevans.miblio_theca.util;

/**
 * Created by immortal on 5/10/2015.
 */
public class StringUtil {
    public static String getMinuteSeconds(int totalSeconds) {
        int minutes = totalSeconds/60;
        int seconds = totalSeconds%60;
        String time = minutes + ":";
        time += seconds < 10 ? "0" + seconds : seconds;
        return time;
    }

    public static String join(String[] arr, String glue) {
        String joined = "";
        boolean first = true;
        for(String ele:arr) {
            if(!first)
                joined += glue;
            joined += ele;
            first = false;
        }
        return joined;
    }
}
