package com.example.capstone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class RequestService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ShiftRepository shiftRepo;

    @Autowired
    private RequestRepository repo;

    public Request createRequest(String requesteeName, String date, String shift, String requesterName) throws ParseException {
        User requesteeUser = userRepo.findByFullName(requesteeName);
        String[] parts = date.split("-");
        String newDate = parts[2] + "-" + parts[1] + "-" + parts[0];
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

    public void switchShifts (Request request) {
        Optional<Shift> shift = shiftRepo.findById(request.getRequesteeShiftID());
        if (shift.isPresent()){
            shift.get().setUserID(request.getRequesterID());
        }
    }

    public List<Request> usersRequests (Long userid) {
        List<Request> requests = repo.findAllByrequesterID(userid);
        return requests;
    }


}
