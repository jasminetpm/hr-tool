package com.finalproject.hrtool.controllers;

import com.finalproject.hrtool.dtos.BasicEmployeeDetails;
import com.finalproject.hrtool.dtos.EmployeePromoteDetails;
import com.finalproject.hrtool.entities.Departments;
import com.finalproject.hrtool.entities.Employees;
import com.finalproject.hrtool.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/ping")
    public String ping() {
        return "Service online";
    }

    @GetMapping("/getAllDepts")
    public List<Departments> getAllDepts() {
        return service.getAllDepartments();
    }

    @GetMapping("/searchByEmpNo")
    public ResponseEntity<?> searchByEmpNo(@RequestParam(defaultValue = "-1") int empNo) {
        if (empNo == -1) return ResponseEntity.badRequest().body("Invalid ID");

        Employees emp = service.getEmployeeById(empNo);
        if (emp == null) return ResponseEntity.status(404).body("Employee not found");

        return ResponseEntity.ok(emp);
    }

    @GetMapping("/searchByDept")
    public ResponseEntity<List<BasicEmployeeDetails>> searchByDept(
            @RequestParam String deptNo,
            @RequestParam(defaultValue = "1") int page) {
        // Spring pages are 0-indexed, so we subtract 1
        return ResponseEntity.ok(service.getEmployeesByDept(deptNo, page - 1));
    }

    @PostMapping("/promotion")
    public ResponseEntity<?> promote(@RequestBody EmployeePromoteDetails details) {
        try {
            service.promoteEmployee(details);
            return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}