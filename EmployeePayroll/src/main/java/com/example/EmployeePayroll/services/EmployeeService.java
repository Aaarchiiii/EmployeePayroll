package com.example.EmployeePayroll.services;

import com.example.EmployeePayroll.controller.EmployeeController;
import com.example.EmployeePayroll.dto.EmployeeDTO;
import com.example.EmployeePayroll.entities.EmployeeEntity;
import com.example.EmployeePayroll.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.EmployeePayroll.model.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private List<EmployeeEntity> employees = new ArrayList<>();

    @Autowired
    private EmployeeRepository employeeRepository;

//    public EmployeeService(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }

    public EmployeeDTO get(Long id){
        Optional<EmployeeEntity> empFound = employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();
        if(empFound.isEmpty()){
            throw new RuntimeException("Cannot find employee with given id");
        }

//        EmployeeEntity empFound = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find Employee with given id"));

//        EmployeeDTO empDto = new EmployeeDTO(empFound.getName(), empFound.getSalary());
//        empDto.setId(empFound.getId());
        Employee employee = new Employee(empFound.get().getId(), empFound.get().getName(), empFound.get().getSalary());
        return employee.toDTO();

//        return empDto;

    }

    public EmployeeDTO create(EmployeeDTO newEmp){
        Employee employee = new Employee(newEmp);

        EmployeeEntity newEntity = new EmployeeEntity(employee.getName(), employee.getSalary());

        long newId = employees.size() + 1L;
        newEntity.setId(newId);
        employees.add(newEntity);

//        employeeRepository.save(newEntity);

//        EmployeeDTO emp = new EmployeeDTO(newEntity.getName(), newEntity.getSalary());
//        EmployeeEntity savedEntity = employeeRepository.save(newEntity);
        employee.setId(newEntity.getId());

//        return emp;
        return employee.toDTO();
    }

    public EmployeeDTO edit(EmployeeDTO emp, Long id){
        //finding employee
//        EmployeeEntity foundEmp = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("No employee found for given id"));
          EmployeeEntity foundEmp = employees.stream()
                  .filter(e -> e.getId().equals(id))
                          .findFirst()
                                  .orElseThrow(() -> new RuntimeException("No employees found for given id"));
        //updating details
        foundEmp.setName(emp.getName());
        foundEmp.setSalary(emp.getSalary());

        //saving in database
//        employeeRepository.save(foundEmp);

        //creating dto to return
//        EmployeeDTO employeeDTO = new EmployeeDTO(foundEmp.getName(), foundEmp.getSalary());
//        employeeDTO.setId(foundEmp.getId());
//
//
//        return employeeDTO;
        Employee employee = new Employee(foundEmp.getId(), foundEmp.getName(), foundEmp.getSalary());
        return employee.toDTO();

    }

    public String delete(Long id){

//        EmployeeEntity foundEmp = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("No employee found for given id"));
          boolean removed = employees.removeIf(emp -> emp.getId().equals(id));
          if(!removed){
              throw new RuntimeException("No employee found for given id");
          }
//        employeeRepository.delete(foundEmp);

        return "Employee Deleted";

    }
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> empList = new ArrayList<>();
        for (EmployeeEntity emp : employees) {
            empList.add(new Employee(emp.getId(), emp.getName(), emp.getSalary()).toDTO());
        }
        return empList;
    }

}