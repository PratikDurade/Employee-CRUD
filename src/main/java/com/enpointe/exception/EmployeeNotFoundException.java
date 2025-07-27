package com.enpointe.exception;

public class EmployeeNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 47545245483404065L;

	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
