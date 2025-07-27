package com.enpointe.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.enpointe.entity.Employee;
import com.enpointe.exception.EmployeeFoundException;
import com.enpointe.exception.EmployeeNotFoundException;
import com.enpointe.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository repository;

	@Override
	public ResponseEntity<Employee> addEmployee(Employee employee) throws EmployeeFoundException {
		logger.info("ADDING A NEW EMPLOYEE: {}", employee);

		if (repository.findByEmail(employee.getEmail()).isPresent()) {
			logger.warn("EMPLOYEE ALREADY PRESENT WITH EMAIL-ID : {}", employee.getEmail());
			throw new EmployeeFoundException(employee.getEmail() + " EMAIL ALREADY PRESENT...!");
		}

		Employee saved = repository.save(employee);
		logger.info("EMPLOYEE SUCCESSFULLY ADDED WITH ID : {}", saved.getId());
		return new ResponseEntity<Employee>(saved, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() throws EmployeeNotFoundException {

		logger.info("FETCHING ALL EMPLOYEES FROM THE DATABASE...");
		List<Employee> employeeList = repository.findAll();
		if (employeeList.isEmpty()) {
			logger.warn("NO EMPLOYEES FOUND IN THE DATABASE...");
			throw new EmployeeNotFoundException("EMPLOYEE LIST IS EMPTY, PLEASE ADD SOME NEW RECORDS...!");
		}
		logger.info("RETRIEVED {} EMPLOYEES...", employeeList.size());
		return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(Integer employeeId) throws EmployeeNotFoundException {

		logger.info("FETCHING EMPLOYEE WITH ID: {}", employeeId);

		Optional<Employee> employee = repository.findById(employeeId);
		if (employee.isPresent())
			return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
		else {
			logger.error("EMPLOYEE NOT FOUND WITH ID : {}", employeeId);
			throw new EmployeeNotFoundException("EMPLOYEE WITH ID : " + employeeId + " NOT PRESENT...!");
		}
	}

	@Override
	public ResponseEntity<Employee> updateEmployee(Integer employeeId, Employee updatedEmployee)
			throws EmployeeNotFoundException {

		logger.info("UPDATING EMPLOYEE WITH ID: {}", employeeId);

		Employee employee = repository.findById(employeeId).orElse(null);
		if (employee != null) {
			employee.setName(updatedEmployee.getName());
			employee.setEmail(updatedEmployee.getEmail());
			employee.setDepartment(updatedEmployee.getDepartment());
			employee.setSalary(updatedEmployee.getSalary());
			employee.setJoiningDate(updatedEmployee.getJoiningDate());

			Employee savedEmployee = repository.save(employee);
			logger.info("EMPLOYEE UPDATED SUCCESSFULLY: {}");
			return new ResponseEntity<Employee>(savedEmployee, HttpStatus.OK);
		} else {
			logger.error("EMPLOYEE NOT FOUND FOR UPDATE WITH ID : {}", employeeId);
			throw new EmployeeNotFoundException(
					"EMPLOYEE WITH ID : " + employeeId + " NOT PRESENT TO PERFORM UPDATE OPERATION...!");
		}

	}

	@Override
	public ResponseEntity<String> deleteEmployee(Integer employeeId) throws EmployeeNotFoundException {

		logger.info("DELETING EMPLOYEE WITH ID: {}", employeeId);

		Optional<Employee> employee = repository.findById(employeeId);
		if (employee.isPresent()) {
			repository.deleteById(employeeId);
			logger.info("EMPLOYEE WITH ID: {} DELETED SUCCESSFULLY", employeeId);
			return new ResponseEntity<String>("EMPLOYEE WITH ID : " + employeeId + " DELETED SUCCESSFULLY...!",
					HttpStatus.OK);
		} else {
			logger.error("EMPLOYEE NOT FOUND FOR DELETION WITH ID: {}", employeeId);
			throw new EmployeeNotFoundException("EMPLOYEE WITH ID : " + employeeId + " NOT FOUND...!");
		}
	}

	@Override
	public ResponseEntity<List<Employee>> getPaginatedEmployees(int page, int size) throws EmployeeNotFoundException {

		logger.info("FETCHING EMPLOYEES FOR PAGE: {}, SIZE: {}", page, size);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		Page<Employee> employeesPage = repository.findAll(pageable);
		if (employeesPage.getContent().isEmpty()) {
			logger.warn("NO EMPLOYEES FOUND FOR PAGE {} WITH SIZE {}", page, size);
			throw new EmployeeNotFoundException("EMPLOYEE LIST IS EMPTY, PLEASE ADD SOME NEW RECORDS...!");
		}
		List<Employee> employees = employeesPage.getContent();
		logger.info("RETRIEVED {} EMPLOYEES ON PAGE {}", employees.size(), page);
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Employee>> getAllEmployeesSorted(String sortBy, String sortDir) {

		logger.info("FETCHING ALL EMPLOYEES SORTED BY {} IN {} ORDER", sortBy, sortDir);
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		List<Employee> sortefdEmployees = repository.findAll(sort);
		logger.info("RETRIEVED {} EMPLOYEES AFTER SORTING.", sortefdEmployees.size());
		return new ResponseEntity<>(sortefdEmployees, HttpStatus.OK);
	}

}
