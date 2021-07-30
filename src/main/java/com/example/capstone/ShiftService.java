package com.example.capstone;


import org.springframework.beans.factory.annotation.Autowired;
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

    public static Date getDateWithoutTimeUsingCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public LocalDate getWeeklyDate () {
        // getting the start date representing sunday and if it is not sunday it will subtract until it is sunday
        Calendar c = Calendar.getInstance();
        Date checkingDate = new GregorianCalendar(2021, Calendar.MARCH, 15).getTime();
        long mil = System.currentTimeMillis();
        Date date = new java.sql.Date(mil);
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek + "Pain");
        ZoneId defaultZoneId = ZoneId.systemDefault();

        if (dayOfWeek == 1) {
            Instant instant = date.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            return localDate;
        } else if (dayOfWeek == 2) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            Date subtractDate = cal.getTime();
            Instant instant = subtractDate.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            System.out.println(localDate + "man");
            return localDate;
        } else if (dayOfWeek == 3) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -2);
            Date subtractDate = cal.getTime();
            Instant instant = subtractDate.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            System.out.println(localDate + "test");
            return localDate;
        } else if (dayOfWeek == 4) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -3);
            Date subtractDate = cal.getTime();
            Instant instant = subtractDate.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            System.out.println(localDate + "Hi Man");
            return localDate;
        } else if (dayOfWeek == 5) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -4);
            Date subtractDate = cal.getTime();
            Instant instant = subtractDate.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            System.out.println(localDate + "Hi Man");
            return localDate;
        } else if (dayOfWeek == 6) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -5);
            Date subtractDate = cal.getTime();
            Instant instant = subtractDate.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            System.out.println(localDate + "Hi Man");
            return localDate;
        } else if (dayOfWeek == 7) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -6);
            Date subtractDate = cal.getTime();
            Instant instant = subtractDate.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            System.out.println(localDate + "Hi Man");
            return localDate;
        }

        return null;
    }

    public ArrayList<Shift> getShifts(Model model) {
        ArrayList<Shift> allShifts = (ArrayList<Shift>) repo.findAll();
        ArrayList<Shift> weeklyShifts = new ArrayList<>();

        LocalDate startDate = getWeeklyDate();
        // remember lance thing with the offset date
        LocalDate endDate = startDate.plusDays(6);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date newStartDate = Date.from(startDate.atStartOfDay(defaultZoneId).toInstant());
        Date newEndDate = Date.from(endDate.atStartOfDay(defaultZoneId).toInstant());

        for (Shift current : allShifts) {
            Date currentDate = current.getDate();
            Instant instant = currentDate.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            Date newCurrentDate = Date.from(localDate.atStartOfDay((defaultZoneId)).toInstant());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            System.out.println(newCurrentDate);
            System.out.println(newStartDate);
            System.out.println(newEndDate);

            // Only add dates within the week's range
            if (newCurrentDate.after(newStartDate) && newCurrentDate.before(newEndDate)) {
                System.out.println("adding1 ...");
                weeklyShifts.add(current);
            } else if (newCurrentDate.equals(newStartDate) || newCurrentDate.equals(newEndDate)) {
                System.out.println("adding2 ...");
                weeklyShifts.add(current);
            }
        }
        System.out.println("dates: ");

        for (Shift s : weeklyShifts) {
            System.out.println(s.getDate().toString() + " - " + s.getUserID());
        }

//        System.out.println("ollo" + weeklyShifts.size());
        return weeklyShifts;
    }

    public ArrayList<dateFromRange> weekDatesList(Date sDate) {
        ArrayList<dateFromRange> weekDates = new ArrayList<>();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = sDate.toInstant();
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

        for (int i = 0; i < 7; i++) {
            LocalDate date = localDate.plusDays(i);
            Date newDate = Date.from(date.atStartOfDay(defaultZoneId).toInstant());

            dateFromRange trial = new dateFromRange();
            trial.setItself(newDate);

            weekDates.add(trial);
        }

        System.out.println("look here " + weekDates);

        for (dateFromRange s : weekDates) {
            System.out.println(s.getItself().toString());
        }

        return weekDates;
    }

    public void generateShifts(Date currentDate) {
        Shift lastShift = repo.findLastShift();
        Date lastDate = lastShift.getDate();
        lastDate = getDateWithoutTimeUsingCalendar(lastDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strCurrentDate = dateFormat.format(currentDate);
        String strLastDate = dateFormat.format(lastDate);
        if (strLastDate.equalsIgnoreCase(strCurrentDate)) {
            Long lastShiftUserID = lastShift.getUserID();
            Optional<User> lastShiftUser = userRepo.findById(lastShiftUserID);
            String lastGroup = lastShiftUser.get().getGroup();
            if (lastGroup.equals("A")) {
                generateGroupBShifts(lastDate);
                generateGroupAShifts(lastDate);
                generateGroupBShifts(lastDate);
                generateGroupAShifts(lastDate);
                generateGroupBShifts(lastDate);
            } else {
                generateGroupAShifts(lastDate);
                generateGroupBShifts(lastDate);
                generateGroupAShifts(lastDate);
                generateGroupBShifts(lastDate);
                generateGroupAShifts(lastDate);
            }
        }
    }

    public static Long dayChecker( ArrayList<Shift> newShifts, Shift newShift, User randomElement, List<User> usersList, ArrayList<Long>userids) {
        Date given = newShift.getDate();
        String givenShiftDate = given.toString();
        if (newShifts.size() == 0) {
            for (int s=0; s < newShifts.size(); s++) {
                Date checkDate = newShifts.get(s).getDate();
                String newShiftDate = checkDate.toString();
                while(newShiftDate.equalsIgnoreCase(givenShiftDate) && userids.contains(randomElement.getId())){
                    Random rand = new Random();
                    randomElement = usersList.get(rand.nextInt(usersList.size()));
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
                    randomElement = usersList.get(rand.nextInt(usersList.size()));
                }

                if (!newShiftDate.equalsIgnoreCase(givenShiftDate) && !userids.contains(randomElement.getId()) && randomElement.getId() != lastShift.getUserID()){
                    break;
                }
            }
        }


        return randomElement.getId();
    }


    public void generateGroupAShifts(Date lastDate) {

        ArrayList<Shift> managers = generateGroupAManagerShift(lastDate);
        ArrayList<Shift> pcas = generateGroupAPcaShift(lastDate);
        ArrayList<Shift> nurses = generateGroupANurseShift(lastDate);
        for (int m = 0; m < managers.size(); m++) {
            repo.save(managers.get(m));
        }
        for (int p = 0; p < pcas.size(); p++) {
            repo.save(pcas.get(p));
        }
        for (int n = 0; n < nurses.size(); n++) {
            repo.save(nurses.get(n));
        }
    }


    public  ArrayList<Shift> generateGroupAManagerShift(Date lastDate) {
        ArrayList<Shift> newShifts = new ArrayList<>();
        List<User> usersManagersA = userRepo.findAllByGroupAndRole("A", "Manager");
        String[] shifts = {"Day", "Evening", "Night"};
        ArrayList<Long> userids;
        for (int i = 0; i < 7; i++) {
            if (newShifts.size() == 0) {
                Calendar calendar = Calendar.getInstance();
                Shift lastShift = repo.findLastShift();
                Date date = lastShift.getDate();
                calendar.setTime(date);
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
                }
            } else {
                Shift lastNewShift = newShifts.get(newShifts.size() - 1);
                Date lastdate = lastNewShift.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lastdate);
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

                }
            }
        }
        return newShifts;
    }


    public ArrayList<Shift> generateGroupAPcaShift(Date lastDate) {
        ArrayList<Shift> newShifts = new ArrayList<>();
        List<User> usersPCAsA = userRepo.findAllByGroupAndRole("A", "PCA");
        String[] shifts = {"Day", "Evening", "Night"};
        ArrayList<Long> userids;
        for (int i = 0; i < 7; i++) {
            if (newShifts.size() == 0) {
                Calendar calendar = Calendar.getInstance();
                Shift lastShift = repo.findLastShift();
                Date date = lastShift.getDate();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    Shift newShift1 = new Shift();
                    newShift1.setDate(calendar.getTime());
                    newShift1.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersPCAsA.get(rand.nextInt(usersPCAsA.size()));
                    newShift1.setUserID(dayChecker(newShifts, newShift1, randomElement, usersPCAsA, userids));
                    newShifts.add(newShift1);
                    userids.add(newShift1.getUserID());

                    Shift newShift2 = new Shift();
                    newShift2.setDate(calendar.getTime());
                    newShift2.setShift(shifts[j]);
                    User randomElement2 = usersPCAsA.get(rand.nextInt(usersPCAsA.size()));
                    newShift2.setUserID(dayChecker(newShifts, newShift2, randomElement2, usersPCAsA, userids));
                    newShifts.add(newShift2);
                    userids.add(newShift2.getUserID());

                }
            } else {
                Shift lastNewShift = newShifts.get(newShifts.size() - 1);
                Date lastdate = lastNewShift.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lastdate);
                calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    Shift newShift1 = new Shift();
                    newShift1.setDate(calendar.getTime());
                    newShift1.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersPCAsA.get(rand.nextInt(usersPCAsA.size()));
                    newShift1.setUserID(dayChecker(newShifts, newShift1, randomElement, usersPCAsA, userids));
                    newShifts.add(newShift1);
                    userids.add(newShift1.getUserID());

                    Shift newShift2 = new Shift();
                    newShift2.setDate(calendar.getTime());
                    newShift2.setShift(shifts[j]);
                    User randomElement2 = usersPCAsA.get(rand.nextInt(usersPCAsA.size()));
                    newShift2.setUserID(dayChecker(newShifts, newShift2, randomElement2, usersPCAsA, userids));
                    newShifts.add(newShift2);
                    userids.add(newShift2.getUserID());

                }
            }
        }
        return newShifts;

    }

    public ArrayList<Shift> generateGroupANurseShift(Date lastDate) {
        ArrayList<Shift> newShifts = new ArrayList<>();
        List<User> usersNurseA = userRepo.findAllByGroupAndRole("A", "Nurse");
        String[] shifts = {"Day", "Evening", "Night"};
        ArrayList<Long> userids;
        for (int i = 0; i < 7; i++) {
            if (newShifts.size() == 0) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strLastDate = dateFormat.format(lastDate);
                Calendar calendar = Calendar.getInstance();
                    Shift lastShift = repo.findLastShift();
                    Date date = lastShift.getDate();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    Shift newShift1 = new Shift();
                    newShift1.setDate(calendar.getTime());
                    newShift1.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersNurseA.get(rand.nextInt(usersNurseA.size()));
                    newShift1.setUserID(dayChecker(newShifts, newShift1, randomElement, usersNurseA, userids));
                    newShifts.add(newShift1);
                    userids.add(newShift1.getUserID());

                    Shift newShift2 = new Shift();
                    newShift2.setDate(calendar.getTime());
                    newShift2.setShift(shifts[j]);
                    User randomElement2 = usersNurseA.get(rand.nextInt(usersNurseA.size()));
                    newShift2.setUserID(dayChecker(newShifts, newShift2, randomElement2, usersNurseA, userids));
                    newShifts.add(newShift2);
                    userids.add(newShift2.getUserID());


                    Shift newShift3 = new Shift();
                    newShift3.setDate(calendar.getTime());
                    newShift3.setShift(shifts[j]);
                    User randomElement3 = usersNurseA.get(rand.nextInt(usersNurseA.size()));
                    newShift3.setUserID(dayChecker(newShifts, newShift3, randomElement3, usersNurseA, userids));
                    newShifts.add(newShift3);
                    userids.add(newShift3.getUserID());


                    Shift newShift4 = new Shift();
                    newShift4.setDate(calendar.getTime());
                    newShift4.setShift(shifts[j]);
                    User randomElement4 = usersNurseA.get(rand.nextInt(usersNurseA.size()));
                    newShift4.setUserID(dayChecker(newShifts, newShift4, randomElement4, usersNurseA, userids));
                    newShifts.add(newShift4);
                    userids.add(newShift4.getUserID());



                    Shift newShift5 = new Shift();
                    newShift5.setDate(calendar.getTime());
                    newShift5.setShift(shifts[j]);
                    User randomElement5 = usersNurseA.get(rand.nextInt(usersNurseA.size()));
                    newShift5.setUserID(dayChecker(newShifts, newShift5, randomElement5, usersNurseA, userids));
                    newShifts.add(newShift5);
                    userids.add(newShift5.getUserID());
                }
            } else {
                Shift lastNewShift = newShifts.get(newShifts.size() - 1);
                Date lastdate = lastNewShift.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lastdate);
                calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j < 3; j++) {

                    Shift newShift1 = new Shift();
                    newShift1.setDate(calendar.getTime());
                    newShift1.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersNurseA.get(rand.nextInt(usersNurseA.size()));
                    newShift1.setUserID(dayChecker(newShifts, newShift1, randomElement, usersNurseA, userids));
                    newShifts.add(newShift1);
                    userids.add(newShift1.getUserID());

                    Shift newShift2 = new Shift();
                    newShift2.setDate(calendar.getTime());
                    newShift2.setShift(shifts[j]);
                    User randomElement2 = usersNurseA.get(rand.nextInt(usersNurseA.size()));
                    newShift2.setUserID(dayChecker(newShifts, newShift2, randomElement2, usersNurseA, userids));
                    newShifts.add(newShift2);
                    userids.add(newShift2.getUserID());


                    Shift newShift3 = new Shift();
                    newShift3.setDate(calendar.getTime());
                    newShift3.setShift(shifts[j]);
                    User randomElement3 = usersNurseA.get(rand.nextInt(usersNurseA.size()));
                    newShift3.setUserID(dayChecker(newShifts, newShift3, randomElement3, usersNurseA, userids));
                    newShifts.add(newShift3);
                    userids.add(newShift3.getUserID());


                    Shift newShift4 = new Shift();
                    newShift4.setDate(calendar.getTime());
                    newShift4.setShift(shifts[j]);
                    User randomElement4 = usersNurseA.get(rand.nextInt(usersNurseA.size()));
                    newShift4.setUserID(dayChecker(newShifts, newShift4, randomElement4, usersNurseA, userids));
                    newShifts.add(newShift4);
                    userids.add(newShift4.getUserID());



                    Shift newShift5 = new Shift();
                    newShift5.setDate(calendar.getTime());
                    newShift5.setShift(shifts[j]);
                    User randomElement5 = usersNurseA.get(rand.nextInt(usersNurseA.size()));
                    newShift5.setUserID(dayChecker(newShifts, newShift5, randomElement5, usersNurseA, userids));
                    newShifts.add(newShift5);
                    userids.add(newShift5.getUserID());
                }
            }
        }
        return newShifts;
    }







    public void generateGroupBShifts(Date date) {
        ArrayList<Shift> managers = generateGroupBManagerShift(date);
        ArrayList<Shift> nurses = generateGroupBNurseShift(date);
        ArrayList<Shift> pcas = generateGroupBPcaShift(date);
        for (int m = 0; m < managers.size(); m++) {
            repo.save(managers.get(m));
        }
        for (int n = 0; n < nurses.size(); n++) {
            repo.save(nurses.get(n));
        }
        for (int p = 0; p < pcas.size(); p++) {
            repo.save(pcas.get(p));
        }
    }


    public  ArrayList<Shift> generateGroupBManagerShift(Date lastDate) {
        ArrayList<Shift> newShifts = new ArrayList<>();
        List<User> usersManagersB = userRepo.findAllByGroupAndRole("A", "Manager");
        String[] shifts = {"Day", "Evening", "Night"};
        ArrayList<Long> userids;
        for (int i = 0; i < 7; i++) {

            if (newShifts.size() == 0) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strLastDate = dateFormat.format(lastDate);
                Calendar calendar = Calendar.getInstance();
                    Shift lastShift = repo.findLastShift();
                    Date date = lastShift.getDate();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j <3; j++) {
                    Shift newShift = new Shift();
                    newShift.setDate(calendar.getTime());
                    newShift.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersManagersB.get(rand.nextInt(usersManagersB.size()));
                    newShift.setUserID(dayChecker(newShifts, newShift, randomElement, usersManagersB, userids));
                    newShifts.add(newShift);
                    userids.add(newShift.getUserID());
                }
            } else {
                Shift lastNewShift = newShifts.get(newShifts.size() - 1);
                Date lastdate = lastNewShift.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lastdate);
                calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    Shift newShift = new Shift();
                    newShift.setDate(calendar.getTime());
                    newShift.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersManagersB.get(rand.nextInt(usersManagersB.size()));
                    newShift.setUserID(dayChecker(newShifts, newShift, randomElement, usersManagersB, userids));
                    newShifts.add(newShift);
                    userids.add(newShift.getUserID());
                }
            }
        }
        return newShifts;
    }

    public ArrayList<Shift> generateGroupBPcaShift(Date lastDate) {
        ArrayList<Shift> newShifts = new ArrayList<>();
        List<User> usersPCAsB = userRepo.findAllByGroupAndRole("B", "PCA");
        String[] shifts = {"Day", "Evening", "Night"};
        ArrayList<Long> userids;
        for (int i = 0; i < 7; i++) {
            if (newShifts.size() == 0) {

                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strLastDate = dateFormat.format(lastDate);
                Calendar calendar = Calendar.getInstance();
                    Shift lastShift = repo.findLastShift();
                    Date date = lastShift.getDate();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    Shift newShift1 = new Shift();
                    newShift1.setDate(calendar.getTime());
                    newShift1.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersPCAsB.get(rand.nextInt(usersPCAsB.size()));
                    newShift1.setUserID(dayChecker(newShifts, newShift1, randomElement, usersPCAsB, userids));
                    newShifts.add(newShift1);
                    userids.add(newShift1.getUserID());



                    Shift newShift2 = new Shift();
                    newShift2.setDate(calendar.getTime());
                    newShift2.setShift(shifts[j]);
                    User randomElement2 = usersPCAsB.get(rand.nextInt(usersPCAsB.size()));
                    newShift2.setUserID(dayChecker(newShifts, newShift2, randomElement2, usersPCAsB, userids));
                    newShifts.add(newShift2);
                    userids.add(newShift2.getUserID());

                }
            } else {
                Shift lastNewShift = newShifts.get(newShifts.size() - 1);
                Date lastdate = lastNewShift.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lastdate);
                calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    Shift newShift1 = new Shift();
                    newShift1.setDate(calendar.getTime());
                    newShift1.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersPCAsB.get(rand.nextInt(usersPCAsB.size()));
                    newShift1.setUserID(dayChecker(newShifts, newShift1, randomElement, usersPCAsB, userids));
                    newShifts.add(newShift1);
                    userids.add(newShift1.getUserID());



                    Shift newShift2 = new Shift();
                    newShift2.setDate(calendar.getTime());
                    newShift2.setShift(shifts[j]);
                    User randomElement2 = usersPCAsB.get(rand.nextInt(usersPCAsB.size()));
                    newShift2.setUserID(dayChecker(newShifts, newShift2, randomElement2, usersPCAsB, userids));
                    newShifts.add(newShift2);
                    userids.add(newShift2.getUserID());
                }
            }
        }
        return newShifts;

    }


    public ArrayList<Shift> generateGroupBNurseShift(Date lastDate) {
        ArrayList<Shift> newShifts = new ArrayList<>();
        List<User> usersNurseB = userRepo.findAllByGroupAndRole("B", "Nurse");
        String[] shifts = {"Day", "Evening", "Night"};
        ArrayList<Long> userids;
        for (int i = 0; i < 7; i++) {
            if (newShifts.size() == 0) {

                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strLastDate = dateFormat.format(lastDate);
                Calendar calendar = Calendar.getInstance();
                    Shift lastShift = repo.findLastShift();
                    Date date = lastShift.getDate();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    Shift newShift1 = new Shift();
                    newShift1.setDate(calendar.getTime());
                    newShift1.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersNurseB.get(rand.nextInt(usersNurseB.size()));
                    newShift1.setUserID(dayChecker(newShifts, newShift1, randomElement, usersNurseB, userids));
                    newShifts.add(newShift1);
                    userids.add(newShift1.getUserID());

                    Shift newShift2 = new Shift();
                    newShift2.setDate(calendar.getTime());
                    newShift2.setShift(shifts[j]);
                    User randomElement2 = usersNurseB.get(rand.nextInt(usersNurseB.size()));
                    newShift2.setUserID(dayChecker(newShifts, newShift2, randomElement2, usersNurseB, userids));
                    newShifts.add(newShift2);
                    userids.add(newShift2.getUserID());

                    Shift newShift3 = new Shift();
                    newShift3.setDate(calendar.getTime());
                    newShift3.setShift(shifts[j]);
                    User randomElement3 = usersNurseB.get(rand.nextInt(usersNurseB.size()));
                    newShift3.setUserID(dayChecker(newShifts, newShift3, randomElement3, usersNurseB, userids));
                    newShifts.add(newShift3);
                    userids.add(newShift3.getUserID());

                    Shift newShift4 = new Shift();
                    newShift4.setDate(calendar.getTime());
                    newShift4.setShift(shifts[j]);
                    User randomElement4 = usersNurseB.get(rand.nextInt(usersNurseB.size()));
                    newShift4.setUserID(dayChecker(newShifts, newShift4, randomElement4, usersNurseB, userids));
                    newShifts.add(newShift4);
                    userids.add(newShift4.getUserID());

                    Shift newShift5 = new Shift();
                    newShift5.setDate(calendar.getTime());
                    newShift5.setShift(shifts[j]);
                    User randomElement5 = usersNurseB.get(rand.nextInt(usersNurseB.size()));
                    newShift5.setUserID(dayChecker(newShifts, newShift5, randomElement5, usersNurseB, userids));
                    newShifts.add(newShift5);
                    userids.add(newShift5.getUserID());
                }
            } else {
                Shift lastNewShift = newShifts.get(newShifts.size() - 1);
                Date lastdate = lastNewShift.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lastdate);
                calendar.add(Calendar.DATE, 1);
                userids = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    Shift newShift1 = new Shift();
                    newShift1.setDate(calendar.getTime());
                    newShift1.setShift(shifts[j]);
                    Random rand = new Random();
                    User randomElement = usersNurseB.get(rand.nextInt(usersNurseB.size()));
                    newShift1.setUserID(dayChecker(newShifts, newShift1, randomElement, usersNurseB, userids));
                    newShifts.add(newShift1);
                    userids.add(newShift1.getUserID());

                    Shift newShift2 = new Shift();
                    newShift2.setDate(calendar.getTime());
                    newShift2.setShift(shifts[j]);
                    User randomElement2 = usersNurseB.get(rand.nextInt(usersNurseB.size()));
                    newShift2.setUserID(dayChecker(newShifts, newShift2, randomElement2, usersNurseB, userids));
                    newShifts.add(newShift2);
                    userids.add(newShift2.getUserID());

                    Shift newShift3 = new Shift();
                    newShift3.setDate(calendar.getTime());
                    newShift3.setShift(shifts[j]);
                    User randomElement3 = usersNurseB.get(rand.nextInt(usersNurseB.size()));
                    newShift3.setUserID(dayChecker(newShifts, newShift3, randomElement3, usersNurseB, userids));
                    newShifts.add(newShift3);
                    userids.add(newShift3.getUserID());


                    Shift newShift4 = new Shift();
                    newShift4.setDate(calendar.getTime());
                    newShift4.setShift(shifts[j]);
                    User randomeElement4 = usersNurseB.get(rand.nextInt(usersNurseB.size()));
                    newShift4.setUserID(dayChecker(newShifts, newShift4, randomeElement4, usersNurseB, userids));
                    newShifts.add(newShift4);
                    userids.add(newShift4.getUserID());


                    Shift newShift5 = new Shift();
                    newShift5.setDate(calendar.getTime());
                    newShift5.setShift(shifts[j]);
                    User randomElement5 = usersNurseB.get(rand.nextInt(usersNurseB.size()));
                    newShift5.setUserID(dayChecker(newShifts, newShift5, randomElement5, usersNurseB, userids));
                    newShifts.add(newShift5);
                    userids.add(newShift5.getUserID());

                }
            }
        }
        return newShifts;
    }

}