package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critterdatalayer.entity.*;
import com.udacity.jdnd.course3.critterdatalayer.service.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.POST, path = "/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = convertToCustomerEntity(customerDTO);

        Customer savedCustomer = customerService.saveCustomer(customer, customerDTO.getPetIds());

        return convertToCustomerDTO(savedCustomer);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerService.getAllCustomers();

        List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>(customerList.size());

        for(Customer customer : customerList) {
            customerDTOList.add(convertToCustomerDTO(customer));
        }

        return customerDTOList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer customer = customerService.getOwnerByPet(petId);

        return convertToCustomerDTO(customer);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertToEmployeeEntity(employeeDTO);

        List<EmployeeSkill> skillList =  employeeDTO.getSkills() == null ? null : new ArrayList<EmployeeSkill>(employeeDTO.getSkills());
        List<DayOfWeek> weekList = employeeDTO.getDaysAvailable() == null ? null : new ArrayList<DayOfWeek>(employeeDTO.getDaysAvailable());

        Employee savedEmployee = null;
        try {
            savedEmployee = employeeService.saveEmployee(employee, skillList, weekList);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return convertToEmployeeDTO(savedEmployee);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee foundEmployee = employeeService.getEmployee(employeeId);

        return convertToEmployeeDTO(foundEmployee);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/employee/{employeeId}")
    public void setAvailability(@RequestBody AvailabilityDTO inputDTO, @PathVariable long employeeId) throws Exception {
        employeeService.setAvailability(inputDTO.getDaysAvailable(), employeeId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        Set<String> skillSet = new HashSet<String>(employeeDTO.getSkills().size());

        for(EmployeeSkill skill : employeeDTO.getSkills()) {
            skillSet.add(skill.toString());
        }

        List<Employee> employeeList = employeeService.getEmployeesForService(skillSet, employeeDTO.getDate());
        List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>(employeeList.size());

        // Convert To EmployeeDTO list
        for(Employee employee : employeeList) {
            employeeDTOList.add(convertToEmployeeDTO(employee));
        }

        return employeeDTOList;
    }

    private Employee convertToEmployeeEntity(EmployeeDTO inputEmployeeDTO) {
        Employee outEmployee = new Employee();
        BeanUtils.copyProperties(inputEmployeeDTO, outEmployee);
        return outEmployee;
    }

    private EmployeeDTO convertToEmployeeDTO(Employee inputEmployee) {
        EmployeeDTO outEmployeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(inputEmployee, outEmployeeDTO);

        // Populate Skill set
        Set<EmployeeSkill> skillSet = getSkillSet(inputEmployee.getSkills());
        outEmployeeDTO.setSkills(skillSet);

        // Populate DayOfWeek set
        Set<DayOfWeek> daySet = getDaySet(inputEmployee.getDaysAvailable());
        outEmployeeDTO.setDaysAvailable(daySet);

        return outEmployeeDTO;
    }

    private Set<EmployeeSkill> getSkillSet(List<Skill> skillList) {
        if(skillList == null) {
            return null;
        }

        Set<EmployeeSkill> skillSet = new HashSet<EmployeeSkill>(skillList.size());

        for(Skill skill : skillList)  {
            EmployeeSkill skillEnum = EmployeeSkill.valueOf(skill.getSkill());
            skillSet.add(skillEnum);
        }

        return skillSet;
    }

    private Set<DayOfWeek> getDaySet(List<WeekDay> dayList) {
        if(dayList == null) {
            return null;
        }

        Set<DayOfWeek> daySet = new HashSet<DayOfWeek>(dayList.size());
        for(WeekDay day : dayList) {
            DayOfWeek dayEnum = day.getDay();
            daySet.add(dayEnum);
        }

        return daySet;
    }

    private Customer convertToCustomerEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private CustomerDTO convertToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        List<Long> petIdList = getPetIds(customer.getPets());
        customerDTO.setPetIds(petIdList);

        return customerDTO;
    }

    private List<Long> getPetIds(List<Pet> petList) {
        if(petList == null) {
            return null;
        }

        List<Long> petIdList = new ArrayList<Long>(petList.size());
        for(Pet pet : petList) {
            petIdList.add(pet.getId());
        }

        return petIdList;
    }
}
