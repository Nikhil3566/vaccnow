package org.nagarro.vaccine.exception;

public class ApiErrorResponse {

	private int error;
	private String message;

	public ApiErrorResponse(int error, String message) {
		super();
		this.error = error;
		this.message = message;
	}

	public int getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

}