package com.finalproject.hrtool.entities;

import com.finalproject.hrtool.entities.ids.DeptManagerId;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@Entity
@Table(name = "dept_manager")
@IdClass(DeptManagerId.class)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeptManager implements Comparable<DeptManager> {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private Departments dept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore // Stops recursion back to Employee
    @ToString.Exclude
    private Employees employee;

    public DeptManager(Employees emp, Departments dept, LocalDate fromDate, LocalDate toDate) {
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
    public int compareTo(DeptManager o) {
        return this.toDate.compareTo(o.toDate);
    }
}