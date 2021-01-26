package org.nagarro.vaccine.exception;

import java.time.LocalDateTime;

public class InvalidVaccineRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String branchName;
	private final String vaccineName;
	private final LocalDateTime scheduledTime;

	public InvalidVaccineRequestException(String branchName, String vaccineName, LocalDateTime scheduledTime) {
		super();
		this.branchName = branchName;
		this.vaccineName = vaccineName;
		this.scheduledTime = scheduledTime;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

}