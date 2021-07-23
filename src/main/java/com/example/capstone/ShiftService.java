package com.example.capstone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

//    public ArrayList<Shift> generateShifts(Date currentDate) {
//        //Checks the previous week from what group went and alternates so it will never be two groups of the same type going
//        //in a row
//        Shift lastShift = repo.findLastShift();
//        if (currentDate == lastShift.getDate()) {
//            Long lastShiftUserID = lastShift.getUserID();
//            Optional<User> lastShiftUser = userRepo.findById(lastShiftUserID);
//            String lastGroup = lastShiftUser.get().getGroup();
//            if (lastGroup.equals("A")) {
//                generateGroupAShift();
//            }
//        }
//
//    }

    public static Long dayChecker( ArrayList<Shift> newShifts, Shift newShift, User randomElement, List<User> usersManagersA, ArrayList<Long>userids) {
        Date given = newShift.getDate();
        String givenShiftDate = given.toString();
        if (newShifts.size() == 0) {
            for (int s=0; s < newShifts.size(); s++) {
                Date checkDate = newShifts.get(s).getDate();
                String newShiftDate = checkDate.toString();
                while(newShiftDate.equalsIgnoreCase(givenShiftDate) && userids.contains(randomElement.getId())){
                    Random rand = new Random();
                    randomElement = usersManagersA.get(rand.nextInt(usersManagersA.size()));
                }
                if (!newShiftDate.equalsIgnoreCase(givenShiftDate) && !userids.contains(randomElement.getId())) {
                    break;
                }
            }
        } else {
            Shift lastShift = newShifts.get(newShifts.size() - 1);
            for (int s=0; s < newShifts.size(); s++) {
                Date checkDate =  newShifts.get(s).getDate();
                String newShiftDate = checkDate.toString();

                while (newShiftDate.equalsIgnoreCase(givenShiftDate) && userids.contains(randomElement.getId()) || randomElement.getId() == lastShift.getUserID()){
                    Random rand = new Random();
                    randomElement = usersManagersA.get(rand.nextInt(usersManagersA.size()));
                }

                if (!newShiftDate.equalsIgnoreCase(givenShiftDate) && !userids.contains(randomElement.getId()) && randomElement.getId() != lastShift.getUserID()){
                    break;
                }
            }
        }


        return randomElement.getId();
    }

    public  ArrayList<Shift> generateGroupAShift() {
        ArrayList<Shift> newShifts = new ArrayList<>();
        List<User> usersManagersA = userRepo.findAllByGroupAndRole("A", "Manager");
        String[] shifts = {"Day", "Evening", "Night"};
        ArrayList<Long> userids;
        for (int i = 0; i < 7; i++) {
            if (newShifts.size() == 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j <3; j++) {
                    Shift newShift = new Shift();
                    newShift.setDate(calendar.getTime());
                    newShift.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersManagersA.get(rand.nextInt(usersManagersA.size()));
                    newShift.setUserID(dayChecker(newShifts, newShift, randomElement, usersManagersA, userids));
                    newShifts.add(newShift);
                    userids.add(newShift.getUserID());
                    System.out.println(newShift.getDate());
                    System.out.println(newShift.getShift());
                    System.out.println(newShift.getUserID());

                }
            } else {
                Shift lastNewShift = newShifts.get(newShifts.size() - 1);
                Date date = lastNewShift.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    Shift newShift = new Shift();
                    newShift.setDate(calendar.getTime());
                    newShift.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersManagersA.get(rand.nextInt(usersManagersA.size()));
                    newShift.setUserID(dayChecker(newShifts, newShift, randomElement, usersManagersA, userids));
                    newShifts.add(newShift);
                    userids.add(newShift.getUserID());
                    System.out.println(newShift.getDate());
                    System.out.println(newShift.getShift());
                    System.out.println(newShift.getUserID());

                }
            }
        }
        List<User> usersPCAsA = userRepo.findAllByGroupAndRole("A", "PCA");

//        for (int i = 0; i < 3; i ++) {
//            Shift newShift = new Shift();
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DATE, 1);
//            newShift.setDate(calendar.getTime());
//            newShift.setShift(shifts.get[i]);
//            if (i == 3) {
//                i = 0;
//            }
//            Random rand = new Random();
//            User randomElement = usersPCAsA.get(rand.nextInt(usersPCAsA.size()));
//
//            newShift.setUserID(randomElement.getId());
//            newShifts.add(newShift);

//        }
        return newShifts;
    }



}
