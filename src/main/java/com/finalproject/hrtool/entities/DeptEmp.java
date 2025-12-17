package com.finalproject.hrtool.entities;

import com.finalproject.hrtool.entities.ids.DeptEmpId;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@Entity
@Table(name = "dept_emp")
@IdClass(DeptEmpId.class)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeptEmp implements Comparable<DeptEmp> {

    @Id
    @Column(name = "emp_no")
    @JsonIgnore
    private int employeeNumber;

    @Id
    @Column(name = "dept_no", length = 4)
    @JsonProperty("dept_no")
    private String departmentNumber;

    @Transient
    @JsonProperty("dept_name")
    private String deptName;

    @Column(name = "from_date")
    @JsonProperty("from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    @JsonProperty("to_date")
    private LocalDate toDate;

    // We allow fetching the Department info (like name) so you can see where they work,
    // but we ignored the list of employees inside the Departments class to prevent a loop.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no", insertable = false, updatable = false)
    @JsonIgnore // We use the @Transient deptName for display; ignore full object to keep JSON clean
    @ToString.Exclude
    private Departments dept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore // Stops recursion back to Employee
    @ToString.Exclude
    private Employees employee;

    public DeptEmp(Employees emp, Departments dept, LocalDate fromDate, LocalDate toDate) {
        this.employee = emp;
        this.dept = dept;
        this.employeeNumber = emp.getEmpNo();
        this.departmentNumber = dept.getDeptNo();
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @PostLoad
    public void setDeptName() {
        if (dept != null) {
            this.deptName = dept.getDeptName();
        }
    }

    @Override
    public int compareTo(DeptEmp o) {
        return this.toDate.compareTo(o.toDate);
    }
}