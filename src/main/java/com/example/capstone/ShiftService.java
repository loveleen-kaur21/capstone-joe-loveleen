package com.example.capstone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepo;

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

    public static Long dayChecker(ArrayList<Shift> newShifts, Shift newShift, User randomElement, List<User> usersManagersA, ArrayList<Long> userids) {
        Date given = newShift.getDate();
        String givenShiftDate = given.toString();
        if (newShifts.size() == 0) {
            for (int s = 0; s < newShifts.size(); s++) {
                Date checkDate = newShifts.get(s).getDate();
                String newShiftDate = checkDate.toString();
                while (newShiftDate.equalsIgnoreCase(givenShiftDate) && userids.contains(randomElement.getId())) {
                    Random rand = new Random();
                    randomElement = usersManagersA.get(rand.nextInt(usersManagersA.size()));
                }
                if (!newShiftDate.equalsIgnoreCase(givenShiftDate) && !userids.contains(randomElement.getId())) {
                    break;
                }
            }
        } else {
            Shift lastShift = newShifts.get(newShifts.size() - 1);
            for (int s = 0; s < newShifts.size(); s++) {
                Date checkDate = newShifts.get(s).getDate();
                String newShiftDate = checkDate.toString();

                while (newShiftDate.equalsIgnoreCase(givenShiftDate) && userids.contains(randomElement.getId()) || randomElement.getId() == lastShift.getUserID()) {
                    Random rand = new Random();
                    randomElement = usersManagersA.get(rand.nextInt(usersManagersA.size()));
                }

                if (!newShiftDate.equalsIgnoreCase(givenShiftDate) && !userids.contains(randomElement.getId()) && randomElement.getId() != lastShift.getUserID()) {
                    break;
                }
            }
        }


        return randomElement.getId();
    }

    public ArrayList<Shift> generateGroupAShift() {
        ArrayList<Shift> newShifts = new ArrayList<>();
        List<User> usersManagersA = userRepo.findAllByGroupAndRole("A", "Manager");
        String[] shifts = {"Day", "Evening", "Night"};
        ArrayList<Long> userids;
        for (int i = 0; i < 7; i++) {
            if (newShifts.size() == 0) {
                Calendar calendar = Calendar.getInstance();
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
//                    System.out.println(newShift.getDate());
//                    System.out.println(newShift.getShift());
//                    System.out.println(newShift.getUserID());

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
//                    System.out.println(newShift.getDate());
//                    System.out.println(newShift.getShift());
//                    System.out.println(newShift.getUserID());

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

    public void getDate(Model model) {

        LocalDate startDate = LocalDate.of(2021, 2, 8);
        LocalDate endDate = startDate.plusDays(6);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date newStartDate = Date.from(startDate.atStartOfDay(defaultZoneId).toInstant());
        Date newEndDate = Date.from(endDate.atStartOfDay(defaultZoneId).toInstant());

//        System.out.println(newStartDate);
//        System.out.println(newEndDate);

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(newEndDate);
//        calendar.add(Calendar.WEEK_OF_YEAR, 9);
//        System.out.println(calendar.getTime());
//        System.out.println("test");
        // loop inserting values based on each week
        // Getting the next 10 weeks from the start of the schedule
        // Changes values in the

        ArrayList<Shift> allShifts = (ArrayList<Shift>) shiftRepo.findAll();
        ArrayList<Shift> weeklyShifts = new ArrayList<>();
//        List<Shift> listShifts = shiftRepo.findAllByDateBetween(newStartDate, newEndDate);
//        model.addAttribute("listShifts", listShifts);
        model.addAttribute("allShifts", allShifts);
        for (Shift current : allShifts) {
            Date currentDate = current.getDate();
            Instant instant = currentDate.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            Date newCurrentDate = Date.from(localDate.atStartOfDay((defaultZoneId)).toInstant());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strCurrentDate = dateFormat.format(newCurrentDate);
            String strStartDate = dateFormat.format(newStartDate);
            String strEndDate = dateFormat.format(newEndDate);
//            System.out.println(strCurrentDate.equalsIgnoreCase(strStartDate));
//            System.out.println(strCurrentDate.equalsIgnoreCase(strEndDate));
//            System.out.println(strCurrentDate);
//            System.out.println(strStartDate);
//            System.out.println(strEndDate);
//            if (current.getDate().after(newStartDate) && current.getDate().before(newEndDate)) {
//                weeklyShifts.add(current);
////                System.out.println(current.getDate().after(newStartDate) && current.getDate().before(newEndDate));
////                System.out.println(current.getShift());
////                System.out.println(current.getDate());
//            } else
                if (strCurrentDate.equalsIgnoreCase(strStartDate)) {
                    System.out.println(strStartDate);
//                    System.out.println(current);
                    weeklyShifts.add(current);
//                System.out.println("date " + current.getDate());
//                System.out.println(current.getDate() == newStartDate);
            }
//                else if (strCurrentDate.equalsIgnoreCase(strEndDate)) {
//                weeklyShifts.add(current);
////                System.out.println(current.getDate() == newEndDate);
//                System.out.println("date " + current.getDate());
//            }
        }

        System.out.println(weeklyShifts.size());



    }

}
