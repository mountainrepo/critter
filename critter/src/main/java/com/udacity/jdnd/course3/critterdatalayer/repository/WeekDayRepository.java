package com.udacity.jdnd.course3.critterdatalayer.repository;

import com.udacity.jdnd.course3.critterdatalayer.entity.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeekDayRepository extends JpaRepository<WeekDay, Long> {

    @Query("SELECT w FROM WeekDay w WHERE w.day = :day")
    public WeekDay findByDay(String day);
}
