package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Attendance;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Salary;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.SalaryRepository;

@Service
public class PaysheetService {

    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;
    private final AttendanceRepository attendanceRepository;

    public PaysheetService(EmployeeRepository employeeRepository,
                           SalaryRepository salaryRepository,
                           AttendanceRepository attendanceRepository) {

        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public Employee getEmployee(String empId) {
        return employeeRepository.findById(empId).orElse(null);
    }

    public Salary getSalary(String empId) {
        return salaryRepository.findByEmpId(empId);
    }

    public Attendance getAttendance(String empId) {

        List<Attendance> list = attendanceRepository.findByEmployeeId(empId);

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(list.size() - 1); 
    }

    public double calculatePayAmount(Salary salary, Attendance attendance) {

        double perDaySalary = salary.getTotal() / 30;

        return perDaySalary * attendance.getPerDays();
    }
}