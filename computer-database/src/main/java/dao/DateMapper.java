package dao;

import java.sql.Timestamp;
import java.time.LocalDate;

public class DateMapper {

	public static LocalDate timestampToLocalDate(Timestamp time) {
		return (time == null) ? null : time.toLocalDateTime().toLocalDate();
	}

	public static Timestamp localDateToTimestamp(LocalDate time) {
		return (time == null) ? null : Timestamp.valueOf(time.atStartOfDay());
	}
}
