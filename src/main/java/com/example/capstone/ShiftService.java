package com.example.capstone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Service
@Transactional
public class ShiftService {

    @Autowired
    private ShiftRepository repo;

    @Autowired
    private UserRepository userRepo;

    public static Date between(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

    public  ArrayList<Shift> generateShift() {
        ArrayList<Shift> newShifts = new ArrayList<>();
        List<User> usersManagersA = userRepo.findAll();
        usersManagersA.removeIf(user ->  !user.getGroup().equals("A"));
        usersManagersA.removeIf(user -> !user.getRole().equals("Manager"));
        String[] strArray = {"Day", "Evening", "Night"};
        List<String> shifts = new ArrayList<>(Arrays.asList(strArray));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < shifts.size(); j++){
                Shift newShift = new Shift();
                Calendar calendar = Calendar.getInstance();
                calendar.set(2021, 9, 01);
                newShift.setDate(calendar.getTime());
                newShift.setShift(shifts.get(j));
                newShift.setUserID(3);
                newShifts.add(newShift);
                System.out.println("Shift: ");
                System.out.println(newShift.getDate());
                System.out.println(newShift.getShift());
                System.out.println(newShift.getUserID());
            }
        }
        return newShifts;
    }


}
