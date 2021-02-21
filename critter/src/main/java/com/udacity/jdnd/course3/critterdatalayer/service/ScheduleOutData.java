package com.udacity.jdnd.course3.critterdatalayer.service;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.time.LocalDate;
import java.util.*;

public class ScheduleOutData {
    //private LocalDate date;
    private Set<Long> employeeIds;
    private Set<Long> petIds;
    private Set<EmployeeSkill> activities;

    public ScheduleOutData() {
        employeeIds = new HashSet<Long>();
        petIds = new HashSet<Long>();
        activities = new HashSet<EmployeeSkill>();
    }

    public Set<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(Set<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public Set<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(Set<Long> petIds) {
        this.petIds = petIds;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
