package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critterdatalayer.dataObject.ScheduleData;
import com.udacity.jdnd.course3.critterdatalayer.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.POST)
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) throws Exception {
        ScheduleData inputData = convertToScheduleEntity(scheduleDTO);

        ScheduleData savedData = scheduleService.createSchedule(inputData);

        return convertToScheduleDTO(savedData);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleData> scheduleDataList = scheduleService.getAllSchedules();

        List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>(scheduleDataList.size());

        for(ScheduleData data : scheduleDataList) {
            ScheduleDTO dto = convertToScheduleDTO(data);
            scheduleDTOList.add(dto);
        }

        return scheduleDTOList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleData> scheduleDataList = scheduleService.getScheduleForPet(petId);

        List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>(scheduleDataList.size());

        for(ScheduleData data : scheduleDataList) {
            ScheduleDTO dto = convertToScheduleDTO(data);
            scheduleDTOList.add(dto);
        }

        return scheduleDTOList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleData> scheduleDataList = scheduleService.getScheduleForEmployee(employeeId);

        List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>(scheduleDataList.size());

        for(ScheduleData data : scheduleDataList) {
            ScheduleDTO dto = convertToScheduleDTO(data);
            scheduleDTOList.add(dto);
        }

        return scheduleDTOList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleData> scheduleDataList = scheduleService.getScheduleForCustomer(customerId);

        List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>(scheduleDataList.size());

        for(ScheduleData data : scheduleDataList) {
            ScheduleDTO dto = convertToScheduleDTO(data);
            scheduleDTOList.add(dto);
        }

        return scheduleDTOList;
    }

    private ScheduleDTO convertToScheduleDTO(ScheduleData scheduleData) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(scheduleData, scheduleDTO);

        return scheduleDTO;
    }

    private ScheduleData convertToScheduleEntity(ScheduleDTO scheduleDTO) {
        ScheduleData schedule = new ScheduleData();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        return schedule;
    }
}
