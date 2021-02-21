package com.udacity.jdnd.course3.critterdatalayer.service;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critterdatalayer.entity.*;
import com.udacity.jdnd.course3.critterdatalayer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WeekDayRepository weekdayRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Transactional
    public Employee saveEmployee(Employee newEmployee, List<EmployeeSkill> skillList, List<DayOfWeek> weekDayList) throws Exception {
        Employee savedEmployee = null;

        try {
            List<Skill> skills = getSkills(skillList);
            List<WeekDay> days = getDays(weekDayList);

            newEmployee.setSkills(skills);
            newEmployee.setDaysAvailable(days);

            savedEmployee = employeeRepository.save(newEmployee);

            savedEmployee.getSkills().size();
        }
        catch(Exception ex) {
            throw ex;
        }

        return savedEmployee;
    }

    private List<Skill> getSkills(List<EmployeeSkill> skillList) {
        if(skillList == null) {
            return null;
        }

        List<Skill> skills = new ArrayList<Skill>(skillList.size());

        for(EmployeeSkill skill : skillList) {
            Skill foundSkill = skillRepository.findSkill(skill.toString());
            skills.add(foundSkill);
        }

        return skills;
    }

    private List<WeekDay> getDays(List<DayOfWeek> weekDayList) {
        if(weekDayList == null) {
            return null;
        }

        List<WeekDay> days = new ArrayList<WeekDay>(weekDayList.size());

        for(DayOfWeek day: weekDayList) {
            WeekDay foundDay = weekdayRepository.findByDay(day.toString());
            days.add(foundDay);
        }

        return days;
    }

    @Transactional
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> foundEmployee = null;

        try {
            foundEmployee = employeeRepository.findById(employeeId);

            foundEmployee.get().getSkills().size();
            foundEmployee.get().getDaysAvailable().size();
        }
        catch(Exception ex) {
            throw ex;
        }

        if(foundEmployee.isPresent()) {
            return foundEmployee.get();
        }
        else {
            return null;
        }
    }

    @Transactional
    public List<Employee> getEmployeesForService(Set<String> skillSet, LocalDate date) {
        /* Find day of the date
           For each skill
                Get the list of employees available on that day. Pass day, skill and get employee
                Add each employee to the list if the employee id is not in the set
        */
        HashSet<Long> employeeIdSet = new HashSet<Long>();
        List<Employee> employeeList = new LinkedList<Employee>();

        String day = date.getDayOfWeek().toString();

        try {
            for (String skill : skillSet) {
                Set<Employee> availableEmployeeList = employeeRepository.getEmployeesBySkillAndDay(skill, day);

                for (Employee availableEmployee : availableEmployeeList) {
                    if (!employeeIdSet.contains(availableEmployee.getId())) {
                        availableEmployee.getSkills().size();
                        availableEmployee.getDaysAvailable().size();

                        employeeList.add(availableEmployee);
                        employeeIdSet.add(availableEmployee.getId());
                    }
                }
            }
        }
        catch(Exception ex) {
            throw ex;
        }

        return employeeList;
    }

    @Transactional
    public void setAvailability(Set<DayOfWeek> availabilitySet, Long employeeId) throws Exception {
        List<WeekDay> availableDaysList = new ArrayList<WeekDay>(availabilitySet.size());

        try {
            Optional<Employee> foundEmployee = employeeRepository.findById(employeeId);

            if(!foundEmployee.isPresent()) {
                throw new Exception("Employee not found");
            }

            for(DayOfWeek day : availabilitySet) {
                WeekDay weekDay = weekdayRepository.findByDay(day.toString());
                availableDaysList.add(weekDay);
            }

            Employee employee = foundEmployee.get();
            employee.setDaysAvailable(availableDaysList);

            employeeRepository.save(employee);
        }
        catch(Exception ex) {
            throw ex;
        }
    }
}
