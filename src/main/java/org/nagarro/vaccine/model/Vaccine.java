package org.nagarro.vaccine.model;

import java.time.LocalDateTime;

public class Vaccine {

	private String vaccineName;
	private String branchName;
	private LocalDateTime scheduledDateTime;
	private String requesterEmailId;
	private String paymentMethod;

	public Vaccine(String vaccineName, String branchName, LocalDateTime scheduledDateTime, String requesterEmailId,
			String paymentMethod) {
		super();
		this.vaccineName = vaccineName;
		this.branchName = branchName;
		this.scheduledDateTime = scheduledDateTime;
		this.requesterEmailId = requesterEmailId;
		this.paymentMethod = paymentMethod;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public String getBranchName() {
		return branchName;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledDateTime;
	}

	public void setScheduledTime(LocalDateTime requestedTime) {
		this.scheduledDateTime = requestedTime;
	}

	public String getRequesterEmailId() {
		return requesterEmailId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}
}
