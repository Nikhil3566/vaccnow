package org.nagarro.vaccine.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.nagarro.vaccine.enums.VaccineAvailability;
import org.nagarro.vaccine.exception.InvalidVaccineRequestException;
import org.nagarro.vaccine.model.Branch;
import org.nagarro.vaccine.model.Vaccine;
import org.nagarro.vaccine.respository.BranchRespository;
import org.nagarro.vaccine.util.SendEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccineService {

	@Autowired
	BranchRespository branchRepository;

	public List<String> getAllBranches() {
		return branchRepository.getAllBranches().values().stream().map(Branch::getBranchName)
				.collect(Collectors.toList());
	}

	public Map<String, List<String>> getAvailableVaccines() {
		return branchRepository.getAllBranches().values().stream()
				.collect(Collectors.toMap(Branch::getBranchName, Branch::getAvailableVaccines));
	}

	public String getSpecificAvailabilityByBranch(String branchName, String vaccineName) {
		if (branchRepository.getAllBranches().get(branchName).getAvailableVaccines().contains(vaccineName)) {
			return VaccineAvailability.AVAILABLE.name();
		}
		return VaccineAvailability.NOT_AVAILABLE.name();
	}

	public List<LocalDateTime> getAvailabileTimeForABranch(String branchName) {
		return branchRepository.getAllBranches().get(branchName).getAvailableTimeSlots();
	}

	public void scheduleVaccinationTimeslot(Vaccine vaccinationRequest) {

		Branch requestedBranch = branchRepository.getAllBranches().get(vaccinationRequest.getBranchName());

		if (requestedBranch.getAvailableVaccines().contains(vaccinationRequest.getVaccineName())
				&& requestedBranch.getAvailableTimeSlots().contains(vaccinationRequest.getScheduledTime())) {
			requestedBranch.getScheduledVaccines().add(vaccinationRequest);

			SendEmailUtil.sendEmail(vaccinationRequest);

			requestedBranch.getAvailableVaccines().remove(vaccinationRequest.getVaccineName());
			requestedBranch.getAvailableTimeSlots().remove(vaccinationRequest.getScheduledTime());
		} else {
			throw new InvalidVaccineRequestException(vaccinationRequest.getBranchName(),
					vaccinationRequest.getVaccineName(), vaccinationRequest.getScheduledTime());
		}
	}

	public Map<String, List<Vaccine>> getAppliedVaccinations() {
		return branchRepository.getAllBranches().values().stream()
				.filter(branch -> !branch.getScheduledVaccines().isEmpty())
				.collect(Collectors.toMap(Branch::getBranchName, Branch::getScheduledVaccines));
	}

	public Map<String, List<Vaccine>> getAppliedVaccinationsForATimePeriod(LocalDateTime from, LocalDateTime to) {
		return branchRepository.getAllBranches().values().stream()
				.filter(branch -> !branch.getScheduledVaccines().isEmpty())
				.filter(branch -> branch.getScheduledVaccines().stream()
						.allMatch(s -> s.getScheduledTime().isAfter(from) && s.getScheduledTime().isBefore(to)))
				.collect(Collectors.toMap(Branch::getBranchName, Branch::getScheduledVaccines));
	}
}
