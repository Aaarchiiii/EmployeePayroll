package com.example.EmployeePayroll.services;

import com.example.EmployeePayroll.dto.EmployeeDTO;
import com.example.EmployeePayroll.entities.EmployeeEntity;
import com.example.EmployeePayroll.interfaces.IEmployeeService;
import com.example.EmployeePayroll.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.EmployeePayroll.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {

    List<EmployeeEntity> employees = new ArrayList<>();

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO get(Long id){
        Optional<EmployeeEntity> empFound = employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();
        if(empFound.isEmpty()){
            throw new RuntimeException("Cannot find employee with given id");
        }

        Employee employee = new Employee(empFound.get().getId(), empFound.get().getName(), empFound.get().getSalary());
        return employee.toDTO();
    }

    @Override
    public EmployeeDTO create(EmployeeDTO newEmp){
        Employee employee = new Employee(newEmp);

        EmployeeEntity newEntity = new EmployeeEntity(employee.getName(), employee.getSalary());

        long newId = employees.size() + 1L;
        newEntity.setId(newId);
        employees.add(newEntity);

        employee.setId(newEntity.getId());
        return employee.toDTO();
    }

    @Override
    public EmployeeDTO edit(EmployeeDTO emp, Long id){
          EmployeeEntity foundEmp = employees.stream()
                  .filter(e -> e.getId().equals(id))
                  .findFirst()
                  .orElseThrow(() -> new RuntimeException("No employees found for given id"));
        //updating details
        foundEmp.setName(emp.getName());
        foundEmp.setSalary(emp.getSalary());

        Employee employee = new Employee(foundEmp.getId(), foundEmp.getName(), foundEmp.getSalary());
        return employee.toDTO();
    }

    @Override
    public String delete(Long id){
          boolean removed = employees.removeIf(emp -> emp.getId().equals(id));
          if(!removed){
              throw new RuntimeException("No employee found for given id");
          }
        return "Employee Deleted";
    }
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> empList = new ArrayList<>();
        for (EmployeeEntity emp : employees) {
            empList.add(new Employee(emp.getId(), emp.getName(), emp.getSalary()).toDTO());
        }
        return empList;
    }
}