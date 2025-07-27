package com.enpointe.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.enpointe.exception.EmployeeFoundException;
import com.enpointe.exception.EmployeeNotFoundException;

@RestControllerAdvice
public class EmployeeExceptionHandlerAdvice {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmployeeNotFoundException.class)
	public Map<String, String> handleEmployeeNotFoundException(EmployeeNotFoundException e){
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("errorMessage", e.getLocalizedMessage());
		return errorMap;
	}	
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(EmployeeFoundException.class)
	public Map<String, String> handleEmployeeFoundException(EmployeeFoundException e){
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("errorMessage", e.getLocalizedMessage());
		return errorMap;
	}	
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		
		Map<String, String> errorMap = new HashMap<>();
		
		e.getBindingResult().getFieldErrors().forEach(err -> errorMap.put(err.getField(), err.getDefaultMessage()));
		
		return errorMap;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleInvalidFormat(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("errorMessage", "INVALID INPUT FORMAT: PLEASE MAKE SURE 'JOININGDATE' IS IN 'YYYY-MM-DD' FORMAT.");
        return error;
    }
}
