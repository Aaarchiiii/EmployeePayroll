package com.example.EmployeePayroll.services;

import com.example.EmployeePayroll.dto.EmployeeDTO;
import com.example.EmployeePayroll.entities.EmployeeEntity;
import com.example.EmployeePayroll.interfaces.IEmployeeService;
import com.example.EmployeePayroll.repositories.EmployeeRepository;
import com.example.EmployeePayroll.model.Employee;
import com.example.EmployeePayroll.exception.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeService implements IEmployeeService {

    private final List<EmployeeEntity> employees = new ArrayList<>();

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO get(Long id) {
        log.info("Fetching employee with id: {}", id);

        Optional<EmployeeEntity> empFound = employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();

        if (empFound.isPresent()) {
            return new Employee(empFound.get().getId(), empFound.get().getName(), empFound.get().getSalary()).toDTO();
        }

        EmployeeEntity empFromDB = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found in database"));

        return new Employee(empFromDB.getId(), empFromDB.getName(), empFromDB.getSalary()).toDTO();
    }

    @Override
    public EmployeeDTO create(EmployeeDTO newEmp) {
        log.info("Creating new employee: {}", newEmp);

        Employee employee = new Employee(newEmp);
        EmployeeEntity newEntity = new EmployeeEntity(employee.getName(), employee.getSalary());

        newEntity = employeeRepository.save(newEntity);
        employees.add(newEntity);

        log.debug("New Employee added: {}", newEntity);
        employee.setId(newEntity.getId());
        return employee.toDTO();
    }

    @Override
    public EmployeeDTO edit(EmployeeDTO emp, Long id) {
        log.info("Updating employee with id: {}", id);

        EmployeeEntity foundEmp = employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseGet(() -> employeeRepository.findById(id)
                        .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found")));

        foundEmp.setName(emp.getName());
        foundEmp.setSalary(emp.getSalary());

        employeeRepository.save(foundEmp);
        log.debug("Employee updated: {}", foundEmp);

        return new Employee(foundEmp.getId(), foundEmp.getName(), foundEmp.getSalary()).toDTO();
    }

    @Override
    public String delete(Long id) {
        log.info("Deleting employee with id: {}", id);

        boolean removed = employees.removeIf(emp -> emp.getId().equals(id));

        if (!removed) {
            EmployeeEntity empFromDB = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));

            employeeRepository.delete(empFromDB);
            log.warn("Employee deleted successfully from database with id: {}", id);
        } else {
            log.warn("Employee deleted successfully from list with id: {}", id);
        }

        return "Employee Deleted";
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees");

        List<EmployeeDTO> empList = new ArrayList<>();
        empList.addAll(employees.stream()
                .map(emp -> new Employee(emp.getId(), emp.getName(), emp.getSalary()).toDTO())
                .collect(Collectors.toList()));

        empList.addAll(employeeRepository.findAll()
                .stream()
                .filter(emp -> employees.stream().noneMatch(e -> e.getId().equals(emp.getId()))) // Jo list me nahi hain sirf wahi lo
                .map(emp -> new Employee(emp.getId(), emp.getName(), emp.getSalary()).toDTO())
                .collect(Collectors.toList()));

        return empList;
    }
}
