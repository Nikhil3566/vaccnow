package org.nagarro.vaccine.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nagarro.vaccine.VaccineApplication;
import org.nagarro.vaccine.model.TimeDurationDTO;
import org.nagarro.vaccine.model.Vaccine;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VaccineApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class VaccineIntegrationTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testWholeFlow() {

		// TEST 1 : Get a list of all branches

		HttpEntity<String> entity1 = new HttpEntity<String>(headers);

		ResponseEntity<String[]> response1 = restTemplate.exchange(createURLWithPort("/vaccnow/availability/branches"),
				HttpMethod.GET, entity1, String[].class);

		assertEquals(HttpStatus.OK, response1.getStatusCode());
		assertArrayEquals(new String[] { "Luxor_Arment_Branch", "Hurghada_RedSea_Branch",
				"Alexandria_Governorate_Branch", "Aswan_Victoria_Branch", "Cairo_Cleopatra_Branch" },
				response1.getBody());

		// TEST 2 : Get a list of all available vaccines per branch

		HttpEntity<String> entity2 = new HttpEntity<String>(headers);

		ResponseEntity<Object> response2 = restTemplate.exchange(createURLWithPort("/vaccnow/availability/vaccines"),
				HttpMethod.GET, entity2, Object.class);

		LinkedHashMap<String, List<String>> expectedResponseBody = new LinkedHashMap<>();
		expectedResponseBody.put("Luxor_Arment_Branch", Arrays.asList("covid", "polio"));
		expectedResponseBody.put("Hurghada_RedSea_Branch", Arrays.asList("covid", "polio"));
		expectedResponseBody.put("Alexandria_Governorate_Branch", Arrays.asList("covid", "polio"));
		expectedResponseBody.put("Aswan_Victoria_Branch", Arrays.asList("covid", "polio"));
		expectedResponseBody.put("Cairo_Cleopatra_Branch", Arrays.asList("covid", "polio"));

		assertEquals(HttpStatus.OK, response2.getStatusCode());
		assertEquals(expectedResponseBody, response2.getBody());

		// TEST 3 : Get a specific availability by branch

		HttpEntity<String> entity3 = new HttpEntity<String>(headers);

		ResponseEntity<String> response3 = restTemplate.exchange(
				createURLWithPort("/vaccnow/availability/branchName/Hurghada_RedSea_Branch/vaccineName/covid"),
				HttpMethod.GET, entity3, String.class);

		assertEquals(HttpStatus.OK, response3.getStatusCode());
		assertEquals("AVAILABLE", response3.getBody());

		// TEST 3 : Get a specific availability by branch

		response3 = restTemplate.exchange(
				createURLWithPort("/vaccnow/availability/branchName/Hurghada_RedSea_Branch/vaccineName/hiv"),
				HttpMethod.GET, entity3, String.class);

		assertEquals(HttpStatus.OK, response3.getStatusCode());
		assertEquals("NOT_AVAILABLE", response3.getBody());

		// TEST 4 : Get available time for a branch

		HttpEntity<String> entity4 = new HttpEntity<String>(headers);

		ResponseEntity<Object> response4 = restTemplate.exchange(
				createURLWithPort("/vaccnow/availability/availableTimeSlots/branchName/Cairo_Cleopatra_Branch"),
				HttpMethod.GET, entity4, Object.class);

		List<LocalDateTime> expectedResponse = new LinkedList<>();

		expectedResponse.add(LocalDateTime.of(2021, Month.JANUARY, 25, 12, 00, 30));
		expectedResponse.add(LocalDateTime.of(2021, Month.JANUARY, 25, 15, 30, 30));
		expectedResponse.add(LocalDateTime.of(2021, Month.JANUARY, 26, 14, 30, 30));
		expectedResponse.add(LocalDateTime.of(2021, Month.JANUARY, 28, 8, 00, 30));
		expectedResponse.add(LocalDateTime.of(2021, Month.JANUARY, 31, 14, 00, 30));

		List<LocalDateTime> availableSlots = (List<LocalDateTime>) response4.getBody();

		assertEquals(HttpStatus.OK, response4.getStatusCode());
		// assertTrue(availableSlots.containsAll(expectedResponse));

		// TEST 5 : Schedule vaccination timeslots

		// Schedule another vaccine
		Vaccine requestBody5 = new Vaccine("covid", "Cairo_Cleopatra_Branch",
				LocalDateTime.of(2021, Month.JANUARY, 25, 12, 00, 30), "nikhilagrawal.it@gmail.com", "cash");

		HttpEntity<Vaccine> entity5 = new HttpEntity<>(requestBody5, headers);

		ResponseEntity<Object> response5 = restTemplate.exchange(createURLWithPort("/vaccnow/vaccination/schedule"),
				HttpMethod.POST, entity5, Object.class);

		assertEquals(HttpStatus.CREATED, response5.getStatusCode());

		// Schedule another vaccine
		Vaccine requestBody6 = new Vaccine("polio", "Alexandria_Governorate_Branch",
				LocalDateTime.of(2021, Month.JANUARY, 31, 14, 00, 30), "nikhilagrawal.it@gmail.com", "cash");

		HttpEntity<Vaccine> entity6 = new HttpEntity<>(requestBody6, headers);

		ResponseEntity<Object> response6 = restTemplate.exchange(createURLWithPort("/vaccnow/vaccination/schedule"),
				HttpMethod.POST, entity6, Object.class);

		assertEquals(HttpStatus.CREATED, response6.getStatusCode());

		// Attempt to schedule a vaccine that is not available
		Vaccine requestBodyInvalid = new Vaccine("hiv", "Alexandria_Governorate_Branch",
				LocalDateTime.of(2021, Month.JANUARY, 31, 14, 00, 30), "nikhilagrawal.it@gmail.com", "cash");

		HttpEntity<Vaccine> entityInvalid = new HttpEntity<>(requestBodyInvalid, headers);

		ResponseEntity<Object> responseInvalid = restTemplate.exchange(createURLWithPort("/vaccnow/vaccination/schedule"),
				HttpMethod.POST, entityInvalid, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseInvalid.getStatusCode());
		

		// TEST 6 : Get a list of all applied vaccination per branch

		HttpEntity<String> entity7 = new HttpEntity<String>(headers);

		ResponseEntity<LinkedHashMap> response7 = restTemplate.exchange(
				createURLWithPort("/vaccnow/reporting/appliedVaccinations"), HttpMethod.GET, entity7,
				LinkedHashMap.class);

		LinkedHashMap<String, List<Vaccine>> responseMap7 = (LinkedHashMap<String, List<Vaccine>>) response7.getBody();

		assertEquals(HttpStatus.OK, response7.getStatusCode());
		assertThat(responseMap7.get("Cairo_Cleopatra_Branch").contains(new Vaccine("covid", "Cairo_Cleopatra_Branch",
				LocalDateTime.of(2021, Month.JANUARY, 25, 12, 00, 30), "nikhilagrawal.it@gmail.com", "cash")));
		assertThat(responseMap7.get("Alexandria_Governorate_Branch")
				.contains(new Vaccine("polio", "Alexandria_Governorate_Branch",
						LocalDateTime.of(2021, Month.JANUARY, 31, 14, 00, 30), "nikhilagrawal.it@gmail.com", "cash")));

		// TEST 7 : Get a list of all applied vaccination per day/period
		TimeDurationDTO requestBody8 = new TimeDurationDTO(LocalDateTime.of(2021, Month.JANUARY, 29, 14, 00, 30),
				LocalDateTime.of(2021, Month.FEBRUARY, 15, 14, 00, 30));

		HttpEntity<TimeDurationDTO> entity8 = new HttpEntity<TimeDurationDTO>(requestBody8, headers);

		ResponseEntity<LinkedHashMap> response8 = restTemplate.exchange(
				createURLWithPort("/vaccnow/reporting/appliedVaccinations"), HttpMethod.POST, entity8,
				LinkedHashMap.class);

		LinkedHashMap<String, List<Vaccine>> responseMap8 = (LinkedHashMap<String, List<Vaccine>>) response8.getBody();

		assertEquals(HttpStatus.OK, response8.getStatusCode());
		assertThat((responseMap8.get("Alexandria_Governorate_Branch"))
				.contains(new Vaccine("polio", "Alexandria_Governorate_Branch",
						LocalDateTime.of(2021, Month.JANUARY, 31, 14, 00, 30), "nikhilagrawal.it@gmail.com", "cash")));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}