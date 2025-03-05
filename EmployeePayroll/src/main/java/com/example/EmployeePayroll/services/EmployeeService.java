package com.example.EmployeePayroll.services;

import com.example.EmployeePayroll.controller.EmployeeController;
import com.example.EmployeePayroll.dto.EmployeeDTO;
import com.example.EmployeePayroll.entities.EmployeeEntity;
import com.example.EmployeePayroll.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDTO get(Long id){

        EmployeeEntity empFound = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find Employee with given id"));

        EmployeeDTO empDto = new EmployeeDTO(empFound.getName(), empFound.getSalary());
        empDto.setId(empFound.getId());

        return empDto;

    }

    public EmployeeDTO create(EmployeeDTO newEmp){

        EmployeeEntity newEntity = new EmployeeEntity(newEmp.getName(), newEmp.getSalary());

        employeeRepository.save(newEntity);

        EmployeeDTO emp = new EmployeeDTO(newEntity.getName(), newEntity.getSalary());

        emp.setId(newEntity.getId());

        return emp;
    }

    public EmployeeDTO edit(EmployeeDTO emp, Long id){
        //finding employee
        EmployeeEntity foundEmp = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("No employee found for given id"));

        //updating details
        foundEmp.setName(emp.getName());
        foundEmp.setSalary(emp.getSalary());

        //saving in database
        employeeRepository.save(foundEmp);

        //creating dto to return
        EmployeeDTO employeeDTO = new EmployeeDTO(foundEmp.getName(), foundEmp.getSalary());
        employeeDTO.setId(foundEmp.getId());


        return employeeDTO;

    }

    public String delete(Long id){

        EmployeeEntity foundEmp = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("No employee found for given id"));

        employeeRepository.delete(foundEmp);

        return "Employee Deleted";

    }


}