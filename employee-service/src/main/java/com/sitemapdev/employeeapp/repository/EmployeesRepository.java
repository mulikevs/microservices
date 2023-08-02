package com.sitemapdev.employeeapp.repository;

import com.sitemapdev.employeeapp.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesRepository extends JpaRepository<Employees, Long> {
}
