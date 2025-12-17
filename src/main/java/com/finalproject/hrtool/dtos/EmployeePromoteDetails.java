package com.finalproject.hrtool.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmployeePromoteDetails {

    @JsonProperty("emp_no")
    private int empNo;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("dept_no")
    private String deptNo;

    @JsonProperty("salary")
    private int salary;

    // Maps the JSON "from_date" to this field.
    // The old app parsed "yyyy-MM-dd" (ISO) for inputs, which is default for Spring/Jackson.
    @JsonProperty("from_date")
    private LocalDate promoteFromDate;

    @JsonProperty("isManager")
    private boolean isManager;

    // This field is not in the JSON input, but we initialize it to 9999-01-01
    // to match the previous logic for "current" records.
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate promoteToDate = LocalDate.of(9999, 1, 1);
}