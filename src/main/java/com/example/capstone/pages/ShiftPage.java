package com.example.capstone.pages;

import com.example.capstone.Shift;
import com.example.capstone.ShiftRepository;
import com.example.capstone.ShiftService;
import com.example.capstone.User;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class ShiftPage {

    @Autowired
    private ShiftRepository shiftRepo;

    private List<Shift> shifts;
    private List<User> users;
    private HashMap<Pair<Long, Date>, Shift> shiftMap;

    public ShiftPage(List<Shift> shifts, List<User> users) {
        this.shifts = shifts;
        this.users = users;
        shiftMap = buildShiftMap(shifts);
    }

    public HashMap<Pair<Long, Date>, Shift> getShiftMap() {
        System.out.println(shiftMap + "whoo2");
        return shiftMap;
    }

    public void setShiftMap(HashMap<Pair<Long, Date>, Shift> shiftMap) {
        System.out.println(shiftMap + "whoo3");
        this.shiftMap = shiftMap;
    }

    public Shift getShift(User user, Date date) {
//        System.out.println(shiftMap.get(new Pair<>(user.getId(), date)) + "shiftmap");
        System.out.println("getting id");
        System.out.println(user.getId() + " - " + date);
        if (shiftMap.get(new Pair<>(user.getId(), date)) == null) {
            Shift emptyShift = new Shift();
            emptyShift.setShift(" ");
            emptyShift.setDate(date);
            emptyShift.setUserID(user.getId());
            return  emptyShift;
        }
        return shiftMap.get(new Pair<>(user.getId(), date));
    }

    public List<User> managers() {
        return users.stream().filter(u -> u.getRole().equals("Manager")).toList();
    }

    public List<User> nurses() {
        return users.stream().filter(u -> u.getRole().equals("Nurse")).toList();
    }

    public List<User> pcas() {
        return users.stream().filter(u -> u.getRole().equals("PCA")).toList();
    }

    public Date getStartDate () {
        // getting the start date representing sunday and if it is not sunday it will subtract until it is sunday;
        Calendar c = Calendar.getInstance();
        Date checkingDate = new GregorianCalendar(2021, Calendar.MARCH, 15).getTime();
        long mil = System.currentTimeMillis();
        Date date = new java.sql.Date(mil);
        c.setTime(date);
        // setting timestamp of date to 0 to match dates in db
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Date cDate = new Date();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        if (dayOfWeek == 1) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            cDate = cal.getTime();
//            Instant instant = date.toInstant();
//            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        } else if (dayOfWeek == 2) {

            cDate = c.getTime();
//            Instant instant = subtractDate.toInstant();
//            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
//            System.out.println(localDate + "man");
        } else if (dayOfWeek == 3) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            cDate = cal.getTime();
//            Instant instant = subtractDate.toInstant();
//            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        } else if (dayOfWeek == 4) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -2);
            cDate = cal.getTime();
//            Instant instant = subtractDate.toInstant();
//            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        } else if (dayOfWeek == 5) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -3);
            cDate = cal.getTime();
//            Instant instant = subtractDate.toInstant();
//            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        } else if (dayOfWeek == 6) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -4);
            cDate = cal.getTime();
//            Instant instant = subtractDate.toInstant();
//            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        } else if (dayOfWeek == 7) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -5);
            cDate = cal.getTime();
//            Instant instant = subtractDate.toInstant();
//            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        }

        System.out.println(cDate.getClass());
        return cDate;
    }

    public List<Date> dates() {
        ArrayList<Date> weeksDates = new ArrayList<>();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = getStartDate().toInstant();
        LocalDate startDate = instant.atZone(defaultZoneId).toLocalDate();
        LocalDate endDate = startDate.plusDays(6);
//        Date newStartDate = Date.from(startDate.atStartOfDay(defaultZoneId).toInstant());
//        Date newEndDate = Date.from(endDate.atStartOfDay(defaultZoneId).toInstant());

        for (int i=0; i < 7; i++) {
            LocalDate newDate = startDate.plusDays(i);
            Date additionalDate = Date.from(newDate.atStartOfDay(defaultZoneId).toInstant());
            weeksDates.add(additionalDate);


//            Date currentDate = current.getDate();
//            Instant instant = currentDate.toInstant();
//            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
//            Date newCurrentDate = Date.from(localDate.atStartOfDay((defaultZoneId)).toInstant());
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            if (newCurrentDate.after(newStartDate) && newCurrentDate.before(newEndDate)) {
//
//            }
        }

        // for the dates that are relevant return the relevant dates
        System.out.println(weeksDates + "hullo");
        return weeksDates;

    }


    public HashMap<Pair<Long, Date>, Shift> buildShiftMap(List<Shift> shifts) {
        var shiftMap = new HashMap<Pair<Long, Date>, Shift>();
        for (var shift : shifts) {
            //converting shifts' dates from timestamp to date type
            Date date = new Date(shift.getDate().getTime());

            System.out.println(shift.getUserID());
            System.out.println(date);
            System.out.println(shift);

            shiftMap.put(new Pair<>(shift.getUserID(), date), shift);
        }
        System.out.println(shiftMap + "pain");
        return shiftMap;
    }
}
