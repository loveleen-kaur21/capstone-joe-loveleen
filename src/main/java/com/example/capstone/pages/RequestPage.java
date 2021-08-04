package com.example.capstone.pages;

import com.example.capstone.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RequestPage {

    private ShiftRepository shiftRepo;

    private CustomUserDetailsService customUserDetailsService;

    private UserRepository userRepo;


    private final List<Request> requestsList;

    public RequestPage(List<Request> requestsList, ShiftRepository shiftRepo, UserRepository userRepo) {
        this.requestsList = requestsList;
        this.shiftRepo = shiftRepo;
        this.userRepo = userRepo;
    }

    public String getRequesteeFullName(Request request) {
        Long id = request.getRequesteeID();
        System.out.println(id);
        System.out.println("user ");
        System.out.println(userRepo);
        User user = userRepo.getById(id);
        return user.getFullName();
    }

    public String getRequesterFullName(Request request)  {
        Long id = request.getRequesterID();
        User user = userRepo.getById(id);
        return user.getFullName();
    }

    public String getShiftDate(Request request){
        Long id = request.getRequesteeShiftID();
        Optional<Shift> shift = shiftRepo.findById(id);
        Date date = shift.get().getDate();
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = date.toInstant();

        //Converting the Date to LocalDate
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        System.out.println("here is " + localDate);
        DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy hh:mm:ss");
        String strDate = dateFormat.format(date);
        String output = strDate.substring(0, 10);
        return output;
    }

    public String getShiftType(Request request) {
        Long id = request.getRequesteeShiftID();
        Optional<Shift> shift = shiftRepo.findById(id);
        String shiftType = shift.get().getShift();
        return shiftType;
    }
}
