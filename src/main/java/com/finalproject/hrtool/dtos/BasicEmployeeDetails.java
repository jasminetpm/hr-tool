package com.finalproject.hrtool.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor // Good practice to have for serialization frameworks
public class BasicEmployeeDetails {

    @JsonProperty("emp_no") // Jackson equivalent of @SerializedName
    private int empNo;

    @JsonProperty("hire_date")
    private LocalDate hireDate;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    // This constructor is required for your JPQL Query in EmployeeRepository
    public BasicEmployeeDetails(int empNo, String fName, String lName, LocalDate hDate) {
        this.empNo = empNo;
        this.firstName = fName;
        this.lastName = lName;
        this.hireDate = hDate;
    }
}

