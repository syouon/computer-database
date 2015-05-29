package com.excilys.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * The Class DateMapper. Provide conversion methods between Java 8 LocalDate and
 * Timestamp. This class is automatically used by Computer model.
 * 
 * @see com.excilys.model.Computer
 */
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
