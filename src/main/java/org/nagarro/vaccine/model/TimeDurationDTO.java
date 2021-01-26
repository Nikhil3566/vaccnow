package org.nagarro.vaccine.model;

import java.time.LocalDateTime;

public class TimeDurationDTO {

	private LocalDateTime from;
	private LocalDateTime to;

	public TimeDurationDTO(LocalDateTime from, LocalDateTime to) {
		super();
		this.from = from;
		this.to = to;
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public LocalDateTime getTo() {
		return to;
	}
}
