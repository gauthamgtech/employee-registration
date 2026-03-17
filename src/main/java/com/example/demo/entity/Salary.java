package com.example.demo.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    @NotNull
    @Column(name = "emp_id")
    private String empId;  

    @Column(name = "emp_name")
    private String empName;

    @NotNull
    @Min(value = 0, message = "Basic must be >= 0")
    @Column(name = "basic")
    private double basic;

    @NotNull
    @Min(value = 0, message = "Gross must be >= 0")
    @Column(name = "gross")
    private double gross;

    @NotNull
    @Min(value = 0, message = "HRA must be >= 0")
    @Column(name = "hra")
    private double hra;

    @NotNull
    @Min(value = 0, message = "Variable Allowance must be >= 0")
    @Column(name = "variable_allowance")
    private double variableAllowance;

    @Column(name = "balance")
    private double balance;

    @Column(name = "total")
    private double total;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public double getBasic() {
        return basic;
    }
    public void setBasic(double basic) {
        this.basic = basic;
    }

    public double getGross() {
        return gross;
    }
    public void setGross(double gross) {
        this.gross = gross;
    }

    public double getHra() {
        return hra;
    }
    public void setHra(double hra) {
        this.hra = hra;
    }

    public double getVariableAllowance() {
        return variableAllowance;
    }
    public void setVariableAllowance(double variableAllowance) {
        this.variableAllowance = variableAllowance;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}