package com.sjprograming.restapi.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sjprograming.restapi.employee.model.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
