package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Attendance;
import com.example.demo.repository.AttendanceRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository repo;

    public AttendanceService(AttendanceRepository repo) {
        this.repo = repo;
    }

    public Attendance saveAttendance(Attendance attendance) {
        return repo.save(attendance);
    }

    public Attendance getAttendanceByEmpId(String empId) {

        List<Attendance> list = repo.findByEmployeeId(empId);

        if(list.isEmpty()){
            return null;
        }

        return list.get(list.size()-1); 
    }
}
