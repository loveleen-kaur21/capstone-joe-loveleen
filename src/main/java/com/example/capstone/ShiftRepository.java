package com.example.capstone;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long>{

    Shift findByUserIDAndShiftAndDate(Long user_id, String shift, Date date);

    List<Shift> findAllByDate(Date date);

    @Query(value = "SELECT * FROM Shift ORDER BY date DESC, id DESC LIMIT 1", nativeQuery = true)
    Shift findLastShift();

    @Query(value = "SELECT * FROM Shift", nativeQuery = true)
    Shift findAllShift();

}
