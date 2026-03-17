package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Salary;
import com.example.demo.repository.SalaryRepository;

@Service
public class SalaryService {

    private final SalaryRepository repo;

    public SalaryService(SalaryRepository repo) {
        this.repo = repo;
    }

    public Salary saveSalary(Salary salary) {

        double basic = salary.getGross() * 0.6;
        salary.setBasic(basic);

        double hra = basic * 0.4;
        salary.setHra(hra);

        double variable = salary.getVariableAllowance();
        if (variable < 0) {
            variable = 0;
        }
        salary.setVariableAllowance(variable);

        double balance = salary.getGross() - basic - hra - variable;
        if (balance < 0) {
            balance = 0;
        }
        salary.setBalance(balance);

        salary.setTotal(salary.getGross());

        return repo.save(salary);
    }

    public List<Salary> getAllSalaries() {
        return repo.findAll();
    }

    public Salary getSalaryByEmpId(String empId) {
        return repo.findByEmpId(empId);
    }
}