package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    // Find salary by employee ID
    Salary findByEmpId(String empId);
}
