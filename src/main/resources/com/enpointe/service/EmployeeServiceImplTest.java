package com.enpointe.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.enpointe.entity.Employee;
import com.enpointe.exception.EmployeeFoundException;
import com.enpointe.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1, "Pratik Durade", "pratikdurade@gmail.com", "Development", 100000.0, LocalDate.of(2021,12,15));
    }

    @Test
    void testAddEmployee_Success() throws EmployeeFoundException {
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        when(employeeRepository.save(employee)).thenReturn(employee);

        ResponseEntity<Employee> response = employeeService.addEmployee(employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    void testAddEmployee_AlreadyExists() {
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));

        assertThrows(EmployeeFoundException.class, () -> employeeService.addEmployee(employee));
    }
}
