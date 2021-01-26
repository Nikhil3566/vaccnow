package org.nagarro.vaccine.respository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.nagarro.vaccine.model.Branch;
import org.springframework.stereotype.Repository;

@Repository
public class BranchRespository {

	protected static Map<String, Branch> branches;
	static {
		branches = new HashMap<>();
		branches.put("Cairo_Cleopatra_Branch",
				new Branch("Cairo_Cleopatra_Branch", createVaccinesList(), createTimeSlots()));
		branches.put("Aswan_Victoria_Branch",
				new Branch("Aswan_Victoria_Branch", createVaccinesList(), createTimeSlots()));
		branches.put("Luxor_Arment_Branch", new Branch("Luxor_Arment_Branch", createVaccinesList(), createTimeSlots()));
		branches.put("Alexandria_Governorate_Branch",
				new Branch("Alexandria_Governorate_Branch", createVaccinesList(), createTimeSlots()));
		branches.put("Hurghada_RedSea_Branch",
				new Branch("Hurghada_RedSea_Branch", createVaccinesList(), createTimeSlots()));
	}

	private static LinkedList<String> createVaccinesList() {
		return new LinkedList<>(Arrays.asList("covid", "polio"));
	}

	private static List<LocalDateTime> createTimeSlots() {

		List<LocalDateTime> timeLocalDateTimes = new LinkedList<>();

		timeLocalDateTimes.add(LocalDateTime.of(2021, Month.JANUARY, 25, 12, 00, 30));
		timeLocalDateTimes.add(LocalDateTime.of(2021, Month.JANUARY, 25, 15, 30, 30));
		timeLocalDateTimes.add(LocalDateTime.of(2021, Month.JANUARY, 26, 14, 30, 30));
		timeLocalDateTimes.add(LocalDateTime.of(2021, Month.JANUARY, 28, 8, 00, 30));
		timeLocalDateTimes.add(LocalDateTime.of(2021, Month.JANUARY, 31, 14, 00, 30));

		return timeLocalDateTimes;
	}

	public Map<String, Branch> getAllBranches() {
		return branches;
	}

}
