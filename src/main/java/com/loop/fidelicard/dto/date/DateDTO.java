package com.loop.fidelicard.dto.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

public class DateDTO {
	private int day, month, year, hour, minute;
	private String formattedDate;

	public DateDTO(LocalDateTime date) {
		setDay(date.getDayOfMonth());
		setMonth(date.getMonthValue());
		setYear(date.getYear());
		setHour(date.getHour());
		setMinute(date.getMinute());
		setFormattedDate(date);
	}

	private void setFormattedDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		String formatDateTime = date.format(formatter);
		setFormattedDate(formatDateTime);
	}

	private void setFormattedDate(String formattedDateTime) {
		this.formattedDate = formattedDateTime;
	}
}
