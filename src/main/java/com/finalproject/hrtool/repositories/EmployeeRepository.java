package com.finalproject.hrtool.repositories;

import com.finalproject.hrtool.dtos.BasicEmployeeDetails;
import com.finalproject.hrtool.entities.Employees;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employees, Integer> {

    // Replaces the NamedQuery "Employee.findAllByDept"
    @Query("SELECT new com.finalproject.hrtool.dtos.BasicEmployeeDetails(e.empNo, "
            + "e.firstName, e.lastName, e.hireDate) FROM Employees e "
            + "JOIN e.deptEmp de WHERE de.departmentNumber = :deptRef ORDER BY e.empNo ASC")
    List<BasicEmployeeDetails> findByDeptNo(@Param("deptRef") String deptNo, Pageable pageable);
}