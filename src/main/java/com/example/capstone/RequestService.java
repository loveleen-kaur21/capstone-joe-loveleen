package com.example.capstone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@Service
@Transactional
public class RequestService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ShiftRepository shiftRepo;

    public Request createRequest(String requesteeName, String date, String shift, String requesterName) throws ParseException {
        User requesteeUser = userRepo.findByFullName(requesteeName);
        System.out.println(date);
        String[] parts = date.split("-");
        String newDate = parts[2] + "-" + parts[1] + "-" + parts[0];
        System.out.println(newDate);
//        Date dDate=new SimpleDateFormat("dd/MM/yyyy").parse(newDate);

        LocalDate lDate = LocalDate.parse(date);
        java.util.Date dDate = java.sql.Date.valueOf(lDate);

        Shift requestedShift = shiftRepo.findByUserIDAndShiftAndDate(requesteeUser.getId(), shift, dDate);
        Request cRequest = new Request();
        cRequest.setRequesteeID(requesteeUser.getId());
        cRequest.setRequesteeShiftID(requestedShift.getId());
        User requesterUser = userRepo.findByFullName(requesterName);
        cRequest.setRequesterID(requesterUser.getId());
        cRequest.setIs_accepted(false);

        return cRequest;

    }
}
