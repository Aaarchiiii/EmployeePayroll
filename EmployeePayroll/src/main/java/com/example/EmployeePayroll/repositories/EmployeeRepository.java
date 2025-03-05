package com.example.EmployeePayroll.repositories;

import com.example.EmployeePayroll.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {


}