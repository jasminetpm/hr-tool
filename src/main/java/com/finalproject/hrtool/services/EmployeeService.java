package com.finalproject.hrtool.services;

import com.finalproject.hrtool.dtos.*;
import com.finalproject.hrtool.entities.*;
import com.finalproject.hrtool.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;
    @Autowired
    private DepartmentRepository deptRepo;

    public List<Departments> getAllDepartments() {
        return deptRepo.findAll();
    }

    public Employees getEmployeeById(int id) {
        return empRepo.findById(id).orElse(null);
    }

    public List<BasicEmployeeDetails> getEmployeesByDept(String deptNo, int page) {
        return empRepo.findByDeptNo(deptNo, PageRequest.of(page, 20));
    }

    @Transactional
    public void promoteEmployee(EmployeePromoteDetails epd) {
        Employees emp = empRepo.findById(epd.getEmpNo())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // 1. Logic for Salary Update
        Salaries newSal = new Salaries(emp, epd.getSalary(), epd.getPromoteFromDate(), epd.getPromoteToDate());
        List<Salaries> salaries = emp.getSalaries();
        Collections.sort(salaries); // Sort to find the last record
        if (!salaries.isEmpty()) {
            Salaries last = salaries.get(salaries.size() - 1);
            last.setToDate(epd.getPromoteFromDate()); // Close previous record
        }
        salaries.add(newSal);

        // 2. Logic for Titles Update
        Titles newTitle = new Titles(emp, epd.getJobTitle(), epd.getPromoteFromDate(), epd.getPromoteToDate());
        List<Titles> titles = emp.getTitles();
        Collections.sort(titles);
        if (!titles.isEmpty()) {
            Titles last = titles.get(titles.size() - 1);
            last.setToDate(epd.getPromoteFromDate());
        }
        titles.add(newTitle);

        // 3. Logic for Department Transfer
        Departments newDept = deptRepo.findById(epd.getDeptNo())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        // Find current department logic from your original code
        DeptEmp currentDeptEmp = null;
        List<DeptEmp> deptEmps = emp.getDeptEmp();
        Collections.sort(deptEmps);
        if (!deptEmps.isEmpty()) {
            DeptEmp last = deptEmps.get(deptEmps.size() - 1);
            if (last.getToDate().getYear() == 9999) {
                currentDeptEmp = last;
            }
        }

        if (currentDeptEmp != null && !currentDeptEmp.getDepartmentNumber().equals(epd.getDeptNo())) {
            // Department changed, close old and open new
            currentDeptEmp.setToDate(epd.getPromoteFromDate());
            DeptEmp newDe = new DeptEmp(emp, newDept, epd.getPromoteFromDate(), epd.getPromoteToDate());
            deptEmps.add(newDe);
        }

        // 4. Logic for Manager Promotion
        if (epd.isManager()) {
            DeptManager newDm = new DeptManager(emp, newDept, epd.getPromoteFromDate(), epd.getPromoteToDate());
            List<DeptManager> managers = emp.getDeptMgr();
            Collections.sort(managers);
            if (!managers.isEmpty()) {
                DeptManager last = managers.get(managers.size() - 1);
                last.setToDate(epd.getPromoteFromDate());
            }
            managers.add(newDm);
        }

        // Save everything (CascadeType.ALL handles children)
        empRepo.save(emp);
    }
}