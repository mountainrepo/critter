package com.udacity.jdnd.course3.critterdatalayer.service;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critterdatalayer.entity.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EmployeeService {

    public Employee saveEmployee(Employee newEmployee, List<EmployeeSkill> skillList, List<DayOfWeek> weekDayList) throws Exception;

    public Employee getEmployee(Long employeeId);

    public List<Employee> getEmployeesForService(Set<String> skillSet, LocalDate serviceDate);

    public void setAvailability(Set<DayOfWeek> availabilitySet, Long employeeId) throws Exception;
}
