package org.nagarro.vaccine.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.nagarro.vaccine.model.TimeDurationDTO;
import org.nagarro.vaccine.model.Vaccine;
import org.nagarro.vaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VaccineController {

	@Autowired
	VaccineService service;

	@GetMapping("/availability/branches")
	public ResponseEntity<List<String>> getAllBranches() {
		return ResponseEntity.ok(service.getAllBranches());
	}

	@GetMapping("/availability/vaccines")
	public ResponseEntity<Map<String, List<String>>> getAvailableVaccines() {
		return ResponseEntity.ok(service.getAvailableVaccines());
	}

	@GetMapping("/availability/branchName/{branchName}/vaccineName/{vaccineName}")
	public ResponseEntity<String> getSpecificAvailabilityByBranch(@PathVariable("branchName") String branchName,
			@PathVariable("vaccineName") String vaccineName) {
		return ResponseEntity.ok(service.getSpecificAvailabilityByBranch(branchName, vaccineName));
	}

	@GetMapping("/availability/availableTimeSlots/branchName/{branchName}")
	public ResponseEntity<List<LocalDateTime>> getAvailabileTimeForABranch(
			@PathVariable("branchName") String branchName) {
		return ResponseEntity.ok(service.getAvailabileTimeForABranch(branchName));
	}

	@PostMapping("/vaccination/schedule")
	public ResponseEntity<Vaccine> scheduleVaccinationTimeslot(@RequestBody Vaccine vaccinationRequest) {
		service.scheduleVaccinationTimeslot(vaccinationRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/reporting/appliedVaccinations")
	public ResponseEntity<Map<String, List<Vaccine>>> getAppliedVaccinations() {
		return ResponseEntity.ok(service.getAppliedVaccinations());
	}
	
	@PostMapping("/reporting/appliedVaccinations")
	public ResponseEntity<Map<String, List<Vaccine>>> getAppliedVaccinationsForATimePeriod(@RequestBody TimeDurationDTO timeDurationDTO) {
		return ResponseEntity.ok(service.getAppliedVaccinationsForATimePeriod(timeDurationDTO.getFrom(),timeDurationDTO.getTo()));
	}
}
