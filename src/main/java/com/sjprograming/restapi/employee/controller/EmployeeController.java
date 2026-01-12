package com.sjprograming.restapi.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sjprograming.restapi.employee.model.Employee;
import com.sjprograming.restapi.employee.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // ✅ CREATE
    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        return service.save(employee);
    }

    // ✅ READ
    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee employee) {
        return service.update(id, employee);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
