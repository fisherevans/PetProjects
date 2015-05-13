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

    public static String getProgressBar(int width, double progressPercentage, String full, String empty) {
        String bar = "";
        int i = 0;
        for (; i <= (int)(progressPercentage*width); i++)
            bar += full;
        for (; i < width; i++)
            bar += empty;
        return bar;
    }

    public static String getFullProgressBar(long startTime, int width, double progressPercentage) {
        String bar = "Progress: [";
        bar += getProgressBar(width, progressPercentage, "|", " ");
        int elapsedSeconds = (int)((System.currentTimeMillis() - startTime)/1000L);
        int estimatedSeconds = (int) ((1.0/progressPercentage)*((double)elapsedSeconds)) - elapsedSeconds;
        String elapsedTime = getMinuteSeconds(elapsedSeconds);
        String estimatedTime = getMinuteSeconds(estimatedSeconds);
        bar += String.format("] Elapsed: %-5s  Left: %-5s", elapsedTime, estimatedTime);
        return bar;
    }
}
