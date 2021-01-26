package org.nagarro.vaccine.enums;

public enum ErrorCodes {

	INVALID_VACCINATION_REQUEST(400, "Branch %s does not have %s vaccine available at %s time");

	private int responseCode;
	private String responseMessage;

	private ErrorCodes(int responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}
}
