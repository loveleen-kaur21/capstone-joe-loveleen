package com.example.capstone;

import javax.persistence.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "request")

public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "requesterID", nullable = false, unique = true)
    private long requesterID;

    @Column(name = "requesteeID", nullable = false, unique = true)
    private long requesteeID;

    @Column(name = "requesteeShiftID", nullable = false, unique = true)
    private long requesteeShiftID;

    @Column(name = "is_accepted", nullable = true)
    private boolean is_accepted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(long requesterID) {
        this.requesterID = requesterID;
    }

    public long getRequesteeID() {
        return requesteeID;
    }

    public void setRequesteeID(long requesteeID) {
        this.requesteeID = requesteeID;
    }

    public long getRequesteeShiftID() {
        return requesteeShiftID;
    }

    public void setRequesteeShiftID(long requesteeShiftID) {
        this.requesteeShiftID = requesteeShiftID;
    }

    public boolean isIs_accepted() {
        return is_accepted;
    }

    public void setIs_accepted(boolean is_accepted) {
        this.is_accepted = is_accepted;
    }
}

