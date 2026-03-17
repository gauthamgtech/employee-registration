package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public String listEmployees(Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        String username = (String) session.getAttribute("loggedInUser");

        List<Employee> employees = service.getEmployeesByUser(username);

        model.addAttribute("employees", employees);

        return "employee-list";
    }


    @GetMapping("/register")
    public String showEmployeeForm(Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        model.addAttribute("employee", new Employee());

        return "employee-register";
    }

    @PostMapping("/save")
    public String saveEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            @RequestParam("photoFile") MultipartFile file,
            Model model,
            HttpSession session
    ) throws IOException {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "employee-register";
        }

        if (service.existsById(employee.getEmpId())) {
            model.addAttribute("errorMessage", "Employee ID already exists.");
            return "employee-register";
        }

        String username = (String) session.getAttribute("loggedInUser");
        employee.setCreatedBy(username);

        if (!file.isEmpty()) {

            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);

            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            File saveFile = new File(uploadDir + fileName);

            file.transferTo(saveFile);

            employee.setPhoto(fileName);
        }

        service.saveEmployee(employee);

        return "redirect:/employees";
    }
}