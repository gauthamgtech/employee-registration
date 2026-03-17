package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Salary;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.SalaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    private final SalaryService salaryService;
    private final EmployeeService employeeService;

    public SalaryController(SalaryService salaryService, EmployeeService employeeService) {
        this.salaryService = salaryService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String showSalaryPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return "redirect:/login";
        }

        List<Employee> employees = employeeService.getEmployeesByUser(username);
        model.addAttribute("employees", employees);

        model.addAttribute("salary", new Salary());
        return "salary";
    }

    @GetMapping("/{empId}")
    public String showSalaryForEmployee(@PathVariable String empId, Model model, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return "redirect:/login";
        }

        List<Employee> employees = employeeService.getEmployeesByUser(username);
        model.addAttribute("employees", employees);

        Employee emp = employeeService.findById(empId);
        if (emp == null || !emp.getCreatedBy().equals(username)) {
            model.addAttribute("error", "Employee not found!");
            model.addAttribute("salary", new Salary());
            return "salary";
        }

        Salary salary = new Salary();
        salary.setEmpId(emp.getEmpId());
        salary.setEmpName(emp.getName());
        salary.setGross(0);
        salary.setBasic(0);
        salary.setHra(0);
        salary.setVariableAllowance(0);
        salary.setBalance(0);
        salary.setTotal(0);

        model.addAttribute("salary", salary);
        return "salary";
    }

    @PostMapping("/save")
    public String saveSalary(@ModelAttribute Salary salary, Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        salaryService.saveSalary(salary);

        return "redirect:/attendance/" + salary.getEmpId();
    }
}