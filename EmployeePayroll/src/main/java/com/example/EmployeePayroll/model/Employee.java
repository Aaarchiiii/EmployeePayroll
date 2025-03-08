package com.example.EmployeePayroll.model;

import com.example.EmployeePayroll.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    private String name;
    private Long salary;

    public Employee(EmployeeDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.salary = dto.getSalary();
    }

    public EmployeeDTO toDTO() {

        return new EmployeeDTO(id, name, salary);
    }
}