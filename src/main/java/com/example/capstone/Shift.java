package com.example.capstone;

import javax.persistence.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shift")

public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "userID", nullable = false, unique = true)
    private long userID;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "shift", nullable = false)
    private String shift;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public static ArrayList<Shift> generateShift() {
        ArrayList<Shift> newShifts = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Shift newShift = new Shift();
            newShift.setDate(new Date(Calendar.DAY_OF_WEEK_IN_MONTH));
            newShift.setShift("Night");
            newShift.setUserID(3);
            newShifts.add(newShift);
        }
        System.out.println(newShifts);
        return newShifts;
    }
}
