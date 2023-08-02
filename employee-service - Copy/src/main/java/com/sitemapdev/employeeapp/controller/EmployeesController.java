package com.sitemapdev.employeeapp.controller;


import com.sitemapdev.employeeapp.entity.Employees;
import com.sitemapdev.employeeapp.request.EmployeeRequest;
import com.sitemapdev.employeeapp.response.EmployeeResponse;
import com.sitemapdev.employeeapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
    	
    	List<EmployeeResponse> employeeList=employeeService.getAllEmployees();
    	return ResponseEntity.status(HttpStatus.OK).body(employeeList);
    }
    

    @GetMapping("/{id}")
    ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {

        System.out.println(id);
        EmployeeResponse employeeResponse = employeeService.getEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);

    }
    @PostMapping("/")
    public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse addedEmployee = employeeService.addEmployee(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedEmployee);
    }
    
    


}


