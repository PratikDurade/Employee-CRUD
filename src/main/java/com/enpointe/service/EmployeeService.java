package com.enpointe.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.enpointe.entity.Employee;
import com.enpointe.exception.EmployeeFoundException;
import com.enpointe.exception.EmployeeNotFoundException;

public interface EmployeeService {

	public ResponseEntity<Employee> addEmployee(Employee employee) throws EmployeeFoundException;
	public ResponseEntity<List<Employee>> getAllEmployees() throws EmployeeNotFoundException;
	public ResponseEntity<Employee> getEmployeeById(Integer id) throws EmployeeNotFoundException;
	public ResponseEntity<Employee> updateEmployee(Integer id, Employee updated) throws EmployeeNotFoundException;
	public ResponseEntity<String> deleteEmployee(Integer id) throws EmployeeNotFoundException;
	public ResponseEntity<List<Employee>> getPaginatedEmployees(int page, int size) throws EmployeeNotFoundException;
	public ResponseEntity<List<Employee>> getAllEmployeesSorted(String sortBy, String sortDir);
}	
