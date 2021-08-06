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
import java.util.stream.Collectors;

@Component
public class ShiftPage {

    @Autowired
    private ShiftRepository shiftRepo;

    private List<Shift> shifts;
    private List<User> users;
    private HashMap<Pair<Long, Date>, Shift> shiftMap;
    private Set<Long> ids;
    // Hash set the user ids and filter for the users with a shift relevant to the week

    public ShiftPage(List<Shift> shifts, List<User> users) {
        this.shifts = shifts;
        this.users = users;
        shiftMap = buildShiftMap(shifts);
    }

    public HashMap<Pair<Long, Date>, Shift> getShiftMap() {
        return shiftMap;
    }

    public void setShiftMap(HashMap<Pair<Long, Date>, Shift> shiftMap) {
        this.shiftMap = shiftMap;
    }

    public Shift getShift(User user, Date date) {
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

    public static Date getStartDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // setting timestamp of date to 0 to match dates in db
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Date cDate = new Date();
        if (dayOfWeek == 1) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            cDate = cal.getTime();
        } else if (dayOfWeek == 2) {
            cDate = c.getTime();
        } else if (dayOfWeek == 3) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            cDate = cal.getTime();
        } else if (dayOfWeek == 4) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -2);
            cDate = cal.getTime();
        } else if (dayOfWeek == 5) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -3);
            cDate = cal.getTime();
        } else if (dayOfWeek == 6) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -4);
            cDate = cal.getTime();
        } else if (dayOfWeek == 7) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, -5);
            cDate = cal.getTime();
//            Instant instant = subtractDate.toInstant();
//            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        }

        return cDate;
    }

    public static Date getEndDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(getStartDate(date));
        c.add(Calendar.DATE, 6);
        return c.getTime();
//        ZoneId defaultZoneId = ZoneId.systemDefault();
//        Instant instant = date.toInstant();
//        LocalDate startDate = instant.atZone(defaultZoneId).toLocalDate();
//        return Date.from(startDate.plusDays(5).atStartOfDay(defaultZoneId).toInstant());
    }

//    public List<Date> dates() {
//        return dates(getStartDate());
//    }

    public List<Date> dates(Date date) {
        ArrayList<Date> weeksDates = new ArrayList<>();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = getStartDate(date).toInstant();
        LocalDate startDate = instant.atZone(defaultZoneId).toLocalDate();
        LocalDate endDate = startDate.plusDays(6);

        for (int i=0; i < 7; i++) {
            LocalDate newDate = startDate.plusDays(i);
            Date additionalDate = Date.from(newDate.atStartOfDay(defaultZoneId).toInstant());
            weeksDates.add(additionalDate);

        }

        // for the dates that are relevant return the relevant dates
        return weeksDates;

    }

//    public Date lastDate() {
//        return dates().get(6);
//    }

    public Date previous(Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        LocalDate plusdate = localDate.minusDays(7);
        Date prevDate = Date.from(plusdate.atStartOfDay(defaultZoneId).toInstant());
        return prevDate;
    }

    public Date next (Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        LocalDate plusdate = localDate.plusDays(7);
        Date nextDate = Date.from(plusdate.atStartOfDay(defaultZoneId).toInstant());
        return nextDate;
    }

    public HashMap<Pair<Long, Date>, Shift> buildShiftMap(List<Shift> shifts) {
        var shiftMap = new HashMap<Pair<Long, Date>, Shift>();
        for (var shift : shifts) {
            //converting shifts' dates from timestamp to date type
            Date date = new Date(shift.getDate().getTime());

            shiftMap.put(new Pair<>(shift.getUserID(), date), shift);
        }
        return shiftMap;
    }
}
