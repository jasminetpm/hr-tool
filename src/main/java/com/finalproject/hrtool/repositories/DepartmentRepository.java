package com.finalproject.hrtool.repositories;

import com.finalproject.hrtool.entities.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Departments, String> {
}