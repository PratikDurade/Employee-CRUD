package com.enpointe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enpointe.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Optional<Employee> findByEmail(String email);

}
