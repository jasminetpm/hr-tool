package com.finalproject.hrtool.entities;

import com.finalproject.hrtool.entities.ids.SalariesId;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

@Entity
@Table(name="salaries")
@IdClass(SalariesId.class)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Salaries implements Comparable<Salaries> {

    @Id
    @Column(name = "emp_no")
    private int employeeNumber;

    @Column(name = "salary")
    private int salary;

    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore // <--- Prevents infinite recursion
    @ToString.Exclude
    private Employees employee;

    public Salaries(Employees emp, int sal, LocalDate fromDate, LocalDate toDate) {
        this.employee = emp;
        this.employeeNumber = emp.getEmpNo();
        this.salary = sal;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public int compareTo(Salaries o) {
        return this.toDate.compareTo(o.toDate);
    }
}