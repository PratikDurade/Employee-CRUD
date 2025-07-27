package com.enpointe.controller;

import com.enpointe.entity.Employee;
import com.enpointe.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
        employee = new Employee(null, "MS Dhoni", "msd@gmail.com", "Operations", 90000.0, LocalDate.of(2023, 5, 15));
        repository.save(employee);
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/employee/getAllEmployees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("msd@gmail.com"));
    }

    @Test
    public void testAddEmployee() throws Exception {
        Employee newEmployee = new Employee(null, "Sachin Tendulkar", "sachin@gmail.com", "Sales", 95000.0, LocalDate.of(2022, 10, 10));

        mockMvc.perform(post("/employee/addEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("sachin@gmail.com"));
    }
}
