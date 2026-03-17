package com.example.demo.controller;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Attendance;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Salary;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.SalaryService;
import com.example.demo.util.NumberToWordsUtil;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/paysheet")
public class PaysheetController {

    private final EmployeeService employeeService;
    private final SalaryService salaryService;
    private final AttendanceService attendanceService;

    public PaysheetController(EmployeeService employeeService,
                              SalaryService salaryService,
                              AttendanceService attendanceService) {
        this.employeeService = employeeService;
        this.salaryService = salaryService;
        this.attendanceService = attendanceService;
    }

    @GetMapping("/{empId}")
    public String showPaysheet(@PathVariable String empId, Model model) {

        Employee employee = employeeService.findById(empId);
        Salary salary = salaryService.getSalaryByEmpId(empId);
        Attendance attendance = attendanceService.getAttendanceByEmpId(empId);

        if (employee == null) {
            model.addAttribute("message", "Employee not found");
            return "error";
        }

        if (salary == null) {
            model.addAttribute("message", "Salary not found for employee");
            return "error";
        }

        if (attendance == null) {
            model.addAttribute("message", "Attendance not found");
            return "error";
        }

        double perDaySalary = salary.getTotal() / 30;
        double payAmount = perDaySalary * attendance.getPerDays();

        String amountInWords =
                NumberToWordsUtil.convertToWords((int) payAmount);

        model.addAttribute("employee", employee);
        model.addAttribute("salary", salary);
        model.addAttribute("attendance", attendance);
        model.addAttribute("payAmount", payAmount);
        model.addAttribute("amountInWords", amountInWords);

        return "paysheet";
    }

    @GetMapping("/excel/{empId}")
    public void exportExcel(@PathVariable String empId,
                            HttpServletResponse response) throws IOException {

        Employee employee = employeeService.findById(empId);
        Salary salary = salaryService.getSalaryByEmpId(empId);
        Attendance attendance = attendanceService.getAttendanceByEmpId(empId);

        double perDaySalary = salary.getTotal() / 30;
        double payAmount = perDaySalary * attendance.getPerDays();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Paysheet");

        Row title = sheet.createRow(0);
        title.createCell(0).setCellValue("Paysheet");

        Row header = sheet.createRow(2);

        header.createCell(0).setCellValue("Employee ID");
        header.createCell(1).setCellValue("Employee Name");
        header.createCell(2).setCellValue("Pay Days");
        header.createCell(3).setCellValue("Gross");
        header.createCell(4).setCellValue("Basic");
        header.createCell(5).setCellValue("HRA");
        header.createCell(6).setCellValue("Variable Allowance");
        header.createCell(7).setCellValue("Pay Amount");

        Row row = sheet.createRow(3);

        row.createCell(0).setCellValue(employee.getEmpId());
        row.createCell(1).setCellValue(employee.getName());
        row.createCell(2).setCellValue(attendance.getPerDays());
        row.createCell(3).setCellValue(salary.getGross());
        row.createCell(4).setCellValue(salary.getBasic());
        row.createCell(5).setCellValue(salary.getHra());
        row.createCell(6).setCellValue(salary.getVariableAllowance());
        row.createCell(7).setCellValue(payAmount);

        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        response.setHeader(
            "Content-Disposition",
            "attachment; filename=paysheet.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}