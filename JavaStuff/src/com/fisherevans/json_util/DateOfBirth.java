package com.fisherevans.webservutil.json;

/**
 * Author: Fisher Evans
 * Date: 7/21/14
 */
public class DateOfBirth extends JSONObject {
    private int _year, _month, _day;

    public DateOfBirth(int year, int month, int day) {
        _year = year;
        _month = month;
        _day = day;
    }

    @JSONField(name = "year", desc = "Birth Year")
    public int getYear() {
        return _year;
    }

    public void setYear(int year) {
        _year = year;
    }

    @JSONField(name = "month", desc = "Birth Month")
    public int getMonth() {
        return _month;
    }

    public void setMonth(int month) {
        _month = month;
    }

    @JSONField(name = "day", desc = "Birth Day")
    public int getDay() {
        return _day;
    }

    public void setDay(int day) {
        _day = day;
    }
}
