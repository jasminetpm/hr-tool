package com.finalproject.hrtool.entities.ids;

// Lombok imports
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

// Java SDK imports
import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class DeptEmpId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int employeeNumber;
    private String departmentNumber;
}
