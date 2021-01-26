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

	public List<String> getAvailableVaccines() {
		return availableVaccines;
	}

	public List<LocalDateTime> getAvailableTimeSlots() {
		return availableTimeSlots;
	}

	public List<Vaccine> getScheduledVaccines() {
		return scheduledVaccines;
	}
}
