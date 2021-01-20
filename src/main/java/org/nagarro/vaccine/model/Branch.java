package org.nagarro.vaccine.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Branch {

	private String branchName;
	private List<String> availableVaccines;
	private List<LocalDateTime> availableTimeSlots;
	private List<Vaccine> scheduledVaccines;

	public Branch(String branchName, List<String> availableVaccines, List<LocalDateTime> availableTimeSlots) {
		super();
		this.branchName = branchName;
		this.availableVaccines = availableVaccines;
		this.availableTimeSlots = availableTimeSlots;
		scheduledVaccines = new LinkedList<>();
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public List<String> getAvailableVaccines() {
		return availableVaccines;
	}

	public void setAvailableVaccines(List<String> availableVaccines) {
		this.availableVaccines = availableVaccines;
	}

	public List<LocalDateTime> getAvailableTimeSlots() {
		return availableTimeSlots;
	}

	public void setAvailableTimeSlots(List<LocalDateTime> availableTimeSlots) {
		this.availableTimeSlots = availableTimeSlots;
	}

	public List<Vaccine> getScheduledVaccines() {
		return scheduledVaccines;
	}

	public void addScheduledVaccines(Vaccine vaccinationRequest) {
		this.scheduledVaccines.add(vaccinationRequest);
	}

}
