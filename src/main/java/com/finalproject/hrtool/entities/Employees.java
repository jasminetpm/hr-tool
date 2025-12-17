package com.finalproject.hrtool.entities;

// JPA imports
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

// Lombok imports
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Java SDK imports
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employees {
    @Id
    @Column(name ="emp_no", length = 11)
    private int empNo;

    @Column(name ="birth_date")
    private LocalDate birthDate;

    @Column(name ="hire_date")
    private LocalDate hireDate;

    @Column(name ="first_name", length = 14)
    private String firstName;

    @Column(name ="last_name", length = 16)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('M', 'F')")
    private Gender gender;

    public enum Gender { M, F }

    // Relationship mappings
    // We remove @Fetch(FetchMode.JOIN) and let Spring handle fetching strategies defaults
    // mappedBy refers to the field in the child entities

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Salaries> salaries;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<DeptEmp> deptEmp;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<DeptManager> deptMgr;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Titles> titles;
}