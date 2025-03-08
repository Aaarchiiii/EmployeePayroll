package com.example.EmployeePayroll.services;

import com.example.EmployeePayroll.dto.EmployeeDTO;
import com.example.EmployeePayroll.entities.EmployeeEntity;
import com.example.EmployeePayroll.interfaces.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import com.example.EmployeePayroll.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.EmployeePayroll.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeService implements IEmployeeService {

    List<EmployeeEntity> employees = new ArrayList<>();

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO get(Long id){
        log.info("Fetching employee with id: {}", id);
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
        log.info("Creating new employee: {}", newEmp);
        Employee employee = new Employee(newEmp);

        EmployeeEntity newEntity = new EmployeeEntity(employee.getName(), employee.getSalary());

        long newId = employees.size() + 1L;
        newEntity.setId(newId);
        employees.add(newEntity);
        log.debug("New Employee added: {}", newEntity);

        employee.setId(newEntity.getId());
        return employee.toDTO();
    }

    @Override
    public EmployeeDTO edit(EmployeeDTO emp, Long id){
        log.info("Updating employee with id: {}", id);
          EmployeeEntity foundEmp = employees.stream()
                  .filter(e -> e.getId().equals(id))
                  .findFirst()
                  .orElseThrow(() -> new RuntimeException("No employees found for given id"));

        foundEmp.setName(emp.getName());
        foundEmp.setSalary(emp.getSalary());
        log.debug("Employee updated: {}", foundEmp);

        Employee employee = new Employee(foundEmp.getId(), foundEmp.getName(), foundEmp.getSalary());
        return employee.toDTO();
    }

    @Override
    public String delete(Long id){
        log.info("Deleting employee with id: {}", id);
          boolean removed = employees.removeIf(emp -> emp.getId().equals(id));
          if(!removed){
              log.error("Employee deletion failed. No employee found with id: {}", id);
              throw new RuntimeException("No employee found for given id");
          }
          log.warn("Employee deleted successfully with id: {}", id);
        return "Employee Deleted";
    }
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees");
        List<EmployeeDTO> empList = new ArrayList<>();
        for (EmployeeEntity emp : employees) {
            empList.add(new Employee(emp.getId(), emp.getName(), emp.getSalary()).toDTO());
        }
        return empList;
    }
}