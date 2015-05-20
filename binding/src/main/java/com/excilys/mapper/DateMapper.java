package com.excilys.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class DateMapper implements AttributeConverter<LocalDate, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDate time) {
		return (time == null) ? null : Timestamp.valueOf(time.atStartOfDay());
	}

	@Override
	public LocalDate convertToEntityAttribute(Timestamp time) {
		return (time == null) ? null : time.toLocalDateTime().toLocalDate();
	}
}
