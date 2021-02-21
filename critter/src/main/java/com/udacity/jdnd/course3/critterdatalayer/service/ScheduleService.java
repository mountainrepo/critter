package com.udacity.jdnd.course3.critterdatalayer.service;

import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critterdatalayer.dataObject.ScheduleData;
import com.udacity.jdnd.course3.critterdatalayer.entity.Schedule;

import java.util.*;

public interface ScheduleService {

    public ScheduleData createSchedule(ScheduleData schedule) throws Exception;

    public List<ScheduleData> getAllSchedules();

    public List<ScheduleData> getScheduleForPet(Long petId);

    public List<ScheduleData> getScheduleForEmployee(Long employeeId);

    public List<ScheduleData> getScheduleForCustomer(Long customerId);
}
