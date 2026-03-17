package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Attendance;
import com.example.demo.service.AttendanceService;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/{empId}")
    public String showAttendancePage(@PathVariable String empId, Model model) {

        Attendance attendance = new Attendance();
        attendance.setEmployeeId(empId);

        model.addAttribute("attendance", attendance);

        return "attendance";
    }

    @PostMapping("/save")
    public String saveAttendance(@ModelAttribute Attendance attendance) {

        attendanceService.saveAttendance(attendance);

        return "redirect:/paysheet/" + attendance.getEmployeeId();
    }
}