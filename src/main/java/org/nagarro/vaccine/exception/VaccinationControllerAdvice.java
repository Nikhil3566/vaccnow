package org.nagarro.vaccine.exception;

import org.nagarro.vaccine.enums.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VaccinationControllerAdvice {

	@ExceptionHandler(InvalidVaccineRequestException.class)
	public ResponseEntity<ApiErrorResponse> handleInvalidVaccineRequestException(InvalidVaccineRequestException ex) {
		ApiErrorResponse response = new ApiErrorResponse(ErrorCodes.INVALID_VACCINATION_REQUEST.getResponseCode(),
				String.format(ErrorCodes.INVALID_VACCINATION_REQUEST.getResponseMessage(), ex.getBranchName(),
						ex.getVaccineName(),ex.getScheduledTime()));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
