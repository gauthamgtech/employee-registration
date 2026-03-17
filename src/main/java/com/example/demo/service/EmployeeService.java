package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    @Autowired
    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public Employee saveEmployee(Employee employee) {
        if (employee.getName() != null) {
            employee.setName(employee.getName().trim().toUpperCase());
        }
        return repo.save(employee);
    }

    public boolean existsById(String empId) {
        return repo.existsById(empId);
    }

    public Employee findById(String empId) {
        return repo.findById(empId).orElse(null);
    }

    public List<Employee> getEmployeesByUser(String username) {
        return repo.findByCreatedBy(username);
    }
}