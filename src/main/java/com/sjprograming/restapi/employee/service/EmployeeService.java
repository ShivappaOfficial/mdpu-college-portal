package com.sjprograming.restapi.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjprograming.restapi.employee.model.Employee;
import com.sjprograming.restapi.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    // ✅ SAVE
    public Employee save(Employee employee) {
        return repo.save(employee);
    }

    // ✅ GET ALL
    public List<Employee> getAll() {
        return repo.findAll();
    }

    // ✅ UPDATE
    public Employee update(Long id, Employee employee) {
        Employee existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setName(employee.getName());
        existing.setEmail(employee.getEmail());
        existing.setRole(employee.getRole());
        existing.setSalary(employee.getSalary());

        return repo.save(existing);
    }

    // ✅ DELETE
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
