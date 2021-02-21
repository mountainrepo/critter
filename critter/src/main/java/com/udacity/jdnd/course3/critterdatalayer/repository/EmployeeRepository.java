package com.udacity.jdnd.course3.critterdatalayer.repository;

import com.udacity.jdnd.course3.critterdatalayer.entity.Employee;
import com.udacity.jdnd.course3.critterdatalayer.entity.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e JOIN e.skills s JOIN e.daysAvailable d WHERE s.skill = :inputSkill AND d.day = :inputDay")
    public Set<Employee> getEmployeesBySkillAndDay(String inputSkill, String inputDay);

    @Query("SELECT s.skill FROM Employee e JOIN e.skills s WHERE e.id = :employeeId")
    public List<String> getSkillsByEmployeeId(Long employeeId);

    @Query("SELECT e FROM Employee e JOIN FETCH e.skills s WHERE e.id = :employeeId")
    public Employee getEmployeeById(Long employeeId);

    @Query("SELECT e.daysAvailable FROM Employee e WHERE e.id = :employeeId")
    public List<WeekDay> getDays(Long employeeId);
}
