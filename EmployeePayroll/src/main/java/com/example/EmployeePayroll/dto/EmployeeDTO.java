package com.example.EmployeePayroll.dto;

public class EmployeeDTO {

    String name;
    Long salary;

    Long id;

    public EmployeeDTO(String name, Long salary, Long id) {
        this.name = name;
        this.salary = salary;

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getSalary() {
        return salary;
    }

    public Long getId() {
        return id;
    }
}