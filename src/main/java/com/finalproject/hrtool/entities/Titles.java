package com.finalproject.hrtool.entities;

import com.finalproject.hrtool.entities.ids.TitlesId;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@Entity
@Table(name="titles")
@IdClass(TitlesId.class)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Titles implements Comparable<Titles> {

    @Id
    @Column(name = "emp_no")
    @JsonIgnore // Redundant in JSON since it is nested in Employee
    private int employeeNumber;

    @Id
    @Column(name = "title", length = 50)
    private String title;

    @Id
    @Column(name = "from_date")
    @JsonProperty("from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    @JsonProperty("to_date")
    private LocalDate toDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore // Stops recursion
    @ToString.Exclude
    private Employees employee;

    public Titles(Employees emp, String jobTitle, LocalDate fromDate, LocalDate toDate) {
        this.employee = emp;
        this.employeeNumber = emp.getEmpNo();
        this.title = jobTitle;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public int compareTo(Titles o) {
        return this.toDate.compareTo(o.toDate);
    }
}