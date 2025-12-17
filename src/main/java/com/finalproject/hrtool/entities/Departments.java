package com.finalproject.hrtool.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Departments {
    @Id
    @Column(name ="dept_no" ,length=4)
    @JsonProperty("dept_no")
    private String deptNo;

    @Column(name ="dept_name" ,length=40)
    @JsonProperty("dept_name")
    private String deptName;

    // We ignore these lists because getting all employees for a department
    // is a heavy operation better handled by the specific /searchByDept endpoint
    // rather than embedding it inside every Department object.

    @OneToMany(mappedBy="dept")
    @JsonIgnore
    @ToString.Exclude
    private List<DeptEmp> deptEmp;

    @OneToMany(mappedBy="dept")
    @JsonIgnore
    @ToString.Exclude
    private List<DeptManager> deptMgt;
}