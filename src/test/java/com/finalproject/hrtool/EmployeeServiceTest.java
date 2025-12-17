package com.finalproject.hrtool;

import com.finalproject.hrtool.dtos.BasicEmployeeDetails;
import com.finalproject.hrtool.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testGetEmployeesByDept() {
        // 1. Action
        List<BasicEmployeeDetails> result = employeeService.getEmployeesByDept("d005", 0);

        // 2. Verification
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Department d005 should have employees");
        assertEquals(20, result.size(), "Should return exactly 20 records (page size)");

        System.out.println("Test Passed! Found " + result.size() + " employees.");
    }
}