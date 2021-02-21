package com.udacity.jdnd.course3.critterdatalayer.repository;

import com.udacity.jdnd.course3.critterdatalayer.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.*;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s JOIN s.pet p WHERE p.id = :petId")
    public List<Schedule> getScheduleForPet(Long petId);

    @Query("SELECT s FROM Schedule s JOIN s.employee e WHERE e.id = :employeeId")
    public List<Schedule> getScheduleForEmployee(Long employeeId);

    @Query("SELECT s FROM Schedule s JOIN s.customer c WHERE c.id = :customerId")
    public List<Schedule> getScheduleForCustomer(Long customerId);

    @Query("SELECT COUNT(*) FROM Schedule s WHERE s.id = :employeeId AND s.date = :date")
    public int getVisitCountByEmployeeAndDate(Long employeeId, LocalDate date);
}
