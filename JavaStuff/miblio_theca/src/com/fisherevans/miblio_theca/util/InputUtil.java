package com.fisherevans.miblio_theca.util;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by h13730 on 5/12/2015.
 */
public class InputUtil {
    private static BufferedReader reader = null;
    private static BufferedReader getReader() {
        if(reader == null)
            reader = new BufferedReader(new InputStreamReader(System.in));
        return reader;
    }

    public synchronized static Integer getInt() {
        BufferedReader reader = getReader();
        while(true) {
            try {
                Integer result = new Integer(reader.readLine());
                return result;
            } catch(Exception e) {
                System.out.print("Invalid integer, try again: ");
            }
        }
    }

    public synchronized static String getString() {
        BufferedReader reader = getReader();
        while(true) {
            try {
                String input = reader.readLine();
                return input;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Try again: ");
            }
        }
    }

    public synchronized static void close() {
        if(reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
