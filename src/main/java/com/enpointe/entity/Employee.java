package com.enpointe.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "EMPLOYEE ENTITY REPRESENTING AN EMPLOYEE RECORD")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "UNIQUE EMPLOYEE-ID OF THE EMPLOYEE", example = "1")
    private Integer id;
    
    @Schema(description = "NAME OF THE EMPLOYEE", example = "Rohit Sharma")
    @NotBlank(message = "NAME IS MANDATORY, IT SHOULD NOT BE NULL OR EMPTY")
    private String name;

    @Schema(description = "EMAIL ADDRESS OF THE EMPLOYEE", example = "rohitsharma@gmail.com")
    @Email(message = "PLEASE ENTER VALID EMAIL ADDRESS")
    @NotBlank(message = "EMAIL IS MANDATORY, IT SHOULD NOT BE NULL OR EMPTY")
    @Column(unique = true)
    private String email;
    
    @Schema(description = "DEPARTMENT OF THE EMPLOYEE", example = "IT")
    @NotBlank(message = "DEPARTMENT IS MANDATORY, IT SHOULD NOT BE NULL OR EMPTY")
    private String department;
    
    @Schema(description = "SALARY OF THE EMPLOYEE", example = "55000")
    @NotNull(message = "SALARY IS MANDATORY, IT SHOULD NOT BE NULL")
    @Positive(message = "SALARY MUST BE POSITIVE")
    private Double salary;
    
    @Schema(description = "DATE OF JOINING", example = "2024-05-10")
    @NotNull(message = "JOINING-DATE IS MANDATORY, IT SHOULD NOT BE NULL OR EMPTY")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;
}