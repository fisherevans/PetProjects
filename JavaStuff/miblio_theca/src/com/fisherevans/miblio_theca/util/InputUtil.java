package com.fisherevans.miblio_theca.util;

import java.util.Scanner;

/**
 * Created by h13730 on 5/12/2015.
 */
public class InputUtil {
    public static Integer getInt() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            try {
                Integer result = new Integer(input);
                scanner.close();
                return result;
            } catch(Exception e) {
                System.out.print("Invalid integer, try again: ");
            }
        }
    }

    public static String getString() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input;
    }
}
