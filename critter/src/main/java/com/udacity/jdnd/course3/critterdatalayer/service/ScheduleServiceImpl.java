package com.udacity.jdnd.course3.critterdatalayer.service;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critterdatalayer.dataObject.ScheduleData;
import com.udacity.jdnd.course3.critterdatalayer.entity.*;
import com.udacity.jdnd.course3.critterdatalayer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final int MAX_VISITS_OFFERED = 10;

    public ScheduleData createSchedule(ScheduleData schedule) throws Exception {
        HashMap<String, List<Long>> skillEmployeeMap = new HashMap<String, List<Long>>();
        HashMap<String, Integer> skillPositionMap = new HashMap<String, Integer>();
        HashMap<Long, Integer> employeeVisitMap = new HashMap<Long, Integer>();

        // Get Employee Skills and track assignment position for Round-Robin assignment
        populateEmployeeSkillMap(schedule.getEmployeeIds(), skillEmployeeMap);
        initializeSkillPositionMap(skillEmployeeMap, skillPositionMap);

        // Get Remaining Employee Visits
        getEmployeeRemainingVisits(schedule.getEmployeeIds(), schedule.getDate(), employeeVisitMap);

        List<Schedule> scheduleList = null;
        try {
            // Create Schedule
            scheduleList = createSchedule(schedule.getDate(), schedule.getPetIds(), schedule.getActivities(), skillEmployeeMap, skillPositionMap, employeeVisitMap);
        }
        catch(Exception ex) {
            throw ex;
        }

        // Convert to ScheduleData and return
        ScheduleData scheduleData = getScheduleData(scheduleList);
        return scheduleData;
    }

    private void populateEmployeeSkillMap(List<Long> employeeIdList, HashMap<String, List<Long>> skillEmployeeMap) {
        for(Long employeeId: employeeIdList) {
            List<String> skillList = employeeRepository.getSkillsByEmployeeId(employeeId);

            for(String skill : skillList) {
                if(skillEmployeeMap.containsKey(skill)) {
                    skillEmployeeMap.get(skill).add(employeeId);
                }
                else {
                    List<Long> empList = new ArrayList<Long>();
                    empList.add(employeeId);

                    skillEmployeeMap.put(skill, empList);
                }
            }
        }
    }

    private void initializeSkillPositionMap(HashMap<String, List<Long>> skillEmployeeMap, HashMap<String, Integer> skillPositionMap) {
        for(Map.Entry<String, List<Long>> entry: skillEmployeeMap.entrySet()) {
            skillPositionMap.put(entry.getKey(), 0);
        }
    }

    private void getEmployeeRemainingVisits(List<Long> employeeIdList, LocalDate date, HashMap<Long, Integer> employeeVisitMap) {
        for(Long employeeId : employeeIdList) {
            int count = scheduleRepository.getVisitCountByEmployeeAndDate(employeeId, date);

            employeeVisitMap.put(employeeId, MAX_VISITS_OFFERED - count);
        }
    }

    private List<Schedule> createSchedule(LocalDate date, List<Long> petIdList, Set<EmployeeSkill> skillList, HashMap<String, List<Long>> skillEmployeeMap, HashMap<String, Integer> skillPositionMap, HashMap<Long, Integer> employeeVisitMap) throws Exception {
        List<Schedule> scheduleList = new LinkedList<Schedule>();

        for(Long petId : petIdList) {
            for(EmployeeSkill skill : skillList) {
                String skillString = skill.toString();

                // Get current position
                int position = skillPositionMap.get(skillString);

                boolean foundEmployee = false;
                Long employeeId = null;

                // Round-Robin
                while(!foundEmployee) {
                    employeeId = skillEmployeeMap.get(skillString).get(position);
                    int count = employeeVisitMap.get(employeeId);

                    if (count == 0) {
                        skillEmployeeMap.get(skillString).remove(position);
                    }
                    else {
                        foundEmployee = true;
                    }
                }

                // Save schedule
                Schedule schedule = saveSchedule(date, petId, skillString, employeeId);
                scheduleList.add(schedule);

                // Progress position
                List<Long> employeeIdList = skillEmployeeMap.get(skillString);

                if((employeeIdList.size() - 1) > position) {
                    position++;
                    skillPositionMap.put(skillString, position);
                }

                // Reduce remaining visit offered by 1
                int remainingCount = employeeVisitMap.get(employeeId);
                employeeVisitMap.put(employeeId, remainingCount - 1);
            }
        }

        return scheduleList;
    }

    private Schedule saveSchedule(LocalDate date, Long petId, String skill, Long employeeId) throws Exception {
        Schedule newSchedule = null;

        try {
            Optional<Pet> pet = petRepository.findById(petId);
            if(!pet.isPresent()) {
                throw new Exception("Pet " + petId + "not found in database");
            }

            Optional<Customer> customer = customerRepository.findById(pet.get().getOwnerId());
            if(!customer.isPresent()) {
                throw new Exception("Customer " + customer.get().getId() + "not found in database");
            }

            Skill service = skillRepository.findSkill(skill);
            if(service == null) {
                throw new Exception("Service " + skill + "not found in database");
            }

            Optional<Employee> employee = employeeRepository.findById(employeeId);
            if(!employee.isPresent()) {
                throw new Exception("Employee " + employeeId + "not found in database");
            }

            newSchedule = scheduleRepository.save(new Schedule(date, pet.get(), service, employee.get(), customer.get()));
        }
        catch(Exception ex) {
            throw ex;
        }

        return newSchedule;
    }

    public List<ScheduleData> getAllSchedules() {
        List<ScheduleData> scheduleDataList = null;

        try {
            List<Schedule> scheduleList = scheduleRepository.findAll();

            HashMap<LocalDate, ScheduleOutData> dateScheduleMap = getScheduleOutData(scheduleList);
            scheduleDataList = convertToScheduleData(dateScheduleMap);
        }
        catch(Exception ex) {
            throw ex;
        }

        return scheduleDataList;
    }

    public List<ScheduleData> getScheduleForPet(Long petId) {
        List<ScheduleData> scheduleDataList = null;

        try {
            List<Schedule> scheduleList = scheduleRepository.getScheduleForPet(petId);

            HashMap<LocalDate, ScheduleOutData> dateScheduleMap = getScheduleOutData(scheduleList);
            scheduleDataList = convertToScheduleData(dateScheduleMap);
        }
        catch(Exception ex) {
            throw ex;
        }

        return scheduleDataList;
    }

    public List<ScheduleData> getScheduleForEmployee(Long employeeId) {
        List<ScheduleData> scheduleDataList = null;

        try {
            List<Schedule> scheduleList = scheduleRepository.getScheduleForEmployee(employeeId);

            HashMap<LocalDate, ScheduleOutData> dateScheduleMap = getScheduleOutData(scheduleList);
            scheduleDataList = convertToScheduleData(dateScheduleMap);
        }
        catch(Exception ex) {
            throw ex;
        }

        return scheduleDataList;
    }

    public List<ScheduleData> getScheduleForCustomer(Long customerId) {
        List<ScheduleData> scheduleDataList = null;

        try {
            List<Schedule> scheduleList = scheduleRepository.getScheduleForCustomer(customerId);

            HashMap<LocalDate, ScheduleOutData> dateScheduleMap = getScheduleOutData(scheduleList);
            scheduleDataList = convertToScheduleData(dateScheduleMap);
        }
        catch(Exception ex) {
            throw ex;
        }

        return scheduleDataList;
    }

    private HashMap<LocalDate, ScheduleOutData> getScheduleOutData(List<Schedule> scheduleList) {
        HashMap<LocalDate, ScheduleOutData> dateScheduleMap = new HashMap<LocalDate, ScheduleOutData>();

        for(Schedule schedule : scheduleList) {
            LocalDate date = schedule.getDate();

            if(!dateScheduleMap.containsKey(date)) {
                ScheduleOutData data = new ScheduleOutData();
                dateScheduleMap.put(date, data);
            }

            ScheduleOutData data = dateScheduleMap.get(date);

            data.getEmployeeIds().add(schedule.getEmployee().getId());
            data.getPetIds().add(schedule.getPet().getId());

            EmployeeSkill skill = toEnum(schedule.getSkill().getSkill());
            data.getActivities().add(skill);
        }

        return dateScheduleMap;
    }

    private List<ScheduleData> convertToScheduleData(HashMap<LocalDate, ScheduleOutData> scheduleOutDataMap) {
        List<ScheduleData> scheduleDataList = new ArrayList<ScheduleData>(scheduleOutDataMap.size());

        for(Map.Entry<LocalDate, ScheduleOutData> entry : scheduleOutDataMap.entrySet()) {
            ScheduleData data = new ScheduleData();
            // Set Date
            data.setDate(entry.getKey());

            ScheduleOutData outData = entry.getValue();

            // Set EmployeeIds
            List<Long> employeeIdList = new ArrayList<Long>(outData.getEmployeeIds().size());
            employeeIdList.addAll(entry.getValue().getEmployeeIds());
            data.setEmployeeIds(employeeIdList);

            // Set PetIds
            List<Long> petIdList = new ArrayList<Long>(outData.getPetIds().size());
            petIdList.addAll(entry.getValue().getPetIds());
            data.setPetIds(petIdList);

            // Set activities
            data.setActivities(outData.getActivities());

            // Add to ScheduleDataList
            scheduleDataList.add(data);
        }

        return scheduleDataList;
    }

    private ScheduleData getScheduleData(List<Schedule> scheduleList) {
        HashMap<LocalDate, ScheduleOutData> dateScheduleMap = getScheduleOutData(scheduleList);
        List<ScheduleData> scheduleDataList = convertToScheduleData(dateScheduleMap);

        return scheduleDataList.get(0);
    }

    private EmployeeSkill toEnum(String value) {
        switch(value) {
            case "PETTING" : return EmployeeSkill.PETTING;
            case "WALKING" : return EmployeeSkill.WALKING;
            case "SHAVING" : return EmployeeSkill.SHAVING;
            case "MEDICATING" : return EmployeeSkill.MEDICATING;
            case "FEEDING" : return EmployeeSkill.FEEDING;
        }

        return null;
    }
}
