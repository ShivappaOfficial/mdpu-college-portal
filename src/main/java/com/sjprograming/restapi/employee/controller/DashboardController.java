package com.sjprograming.restapi.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjprograming.restapi.employee.model.Employee;
import com.sjprograming.restapi.employee.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @GetMapping("/stats")
    public Map<String, Object> getDashboardStats() {

        List<Employee> employees = employeeRepo.findAll();

        int totalEmployees = employees.size();

        long totalSalary = employees.stream()
                .mapToLong(e -> e.getSalary() != null ? e.getSalary() : 0)
                .sum();

        long developers = employees.stream()
                .filter(e -> e.getRole() != null &&
                             e.getRole().equalsIgnoreCase("Developer"))
                .count();

        long managers = employees.stream()
                .filter(e -> e.getRole() != null &&
                             e.getRole().equalsIgnoreCase("Manager"))
                .count();

        Map<String, Object> response = new HashMap<>();
        response.put("totalEmployees", totalEmployees);
        response.put("totalSalary", totalSalary);
        response.put("developers", developers);
        response.put("managers", managers);

        return response;
    }
}
