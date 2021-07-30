package com.example.capstone;

import java.util.*;

public class DateItem {
    String date;

    DateItem(String date) {
        this.date = date;
    }

}

class sortItems implements Comparator<DateItem> {
    public int compare(DateItem a, DateItem b) {
        return a.date.compareTo(b.date);
    }
}