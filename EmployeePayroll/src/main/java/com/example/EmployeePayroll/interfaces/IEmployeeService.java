package com.example.EmployeePayroll.interfaces;

import com.example.EmployeePayroll.dto.EmployeeDTO;
import java.util.List;

public interface IEmployeeService {
    EmployeeDTO get(Long id);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO create(EmployeeDTO newEmp);
    EmployeeDTO edit(EmployeeDTO emp, Long id);
    String delete(Long id);
}
