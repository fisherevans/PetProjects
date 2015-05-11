package com.fisherevans.webservutil.json.test;

import com.fisherevans.webservutil.json.DateOfBirth;
import com.fisherevans.webservutil.json.User;

/**
 * Author: Fisher Evans
 * Date: 7/21/14
 */
public class JSONTest {
    public static void main(String[] args) {
        DateOfBirth dob = new DateOfBirth(1993, 6, 3);
        User user = new User("jeebs", "Fisher", "Evans", "contact@fisherevans.com", dob);
        System.out.println(user.getJSON(9));
    }
}
