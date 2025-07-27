package com.enpointe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enpointe.entity.Employee;
import com.enpointe.exception.EmployeeFoundException;
import com.enpointe.exception.EmployeeNotFoundException;
import com.enpointe.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
@Tag(name = "EMPLOYEE MANAGEMENT", description = "CRUD OPERATIONS FOR EMPLOYEES")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
    @Operation(summary = "ADD A NEW EMPLOYEE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "EMPLOYEE ADDED SUCCESSFULLY"),
        @ApiResponse(responseCode = "409", description = "EMPLOYEE ALREADY EXISTS", content = @Content),
        @ApiResponse(responseCode = "400", description = "INVALID INPUT", content = @Content)
    })
	@PostMapping("/addEmployee")
	public ResponseEntity<Employee> addEmployee(@RequestBody @Valid Employee employee) throws EmployeeFoundException {
		return employeeService.addEmployee(employee);
	}
	
    @Operation(summary = "GET PAGINATED LIST OF EMPLOYEES")
	@GetMapping("/page/employees")
	public ResponseEntity<List<Employee>> getPaginatedEmployees(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) throws EmployeeNotFoundException {

	    return employeeService.getPaginatedEmployees(page, size);
	}
	
    @Operation(summary = "GET ALL EMPLOYEES")
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() throws EmployeeNotFoundException {
        return employeeService.getAllEmployees();
    }
    
    @Operation(summary = "GET ALL EMPLOYEES WITH SORTING")
    @GetMapping("/getAllEmployeesSorted")
    public ResponseEntity<List<Employee>> getAllEmployeesSorted(
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {

        return employeeService.getAllEmployeesSorted(sortBy, sortDir);
    }
    
    @Operation(summary = "GET EMPLOYEE BY ID")
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") Integer employeeId) throws EmployeeNotFoundException {
        return employeeService.getEmployeeById(employeeId);
    }
    
    @Operation(summary = "UPDATE EMPLOYEE DETAILS")
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") Integer id, @Valid @RequestBody Employee employee) throws EmployeeNotFoundException {
        return employeeService.updateEmployee(id, employee);
    }
    
    @Operation(summary = "DELETE EMPLOYEE BY ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "EMPLOYEE DELETED"),
        @ApiResponse(responseCode = "404", description = "EMPLOYEE NOT FOUND")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) throws EmployeeNotFoundException {
        
        return employeeService.deleteEmployee(id);
    }
}
