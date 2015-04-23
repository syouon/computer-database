package util;

import exception.BadOrderValueException;

public class Utils {

	public static boolean isWellFormedDate(String date) {
		if (date.equals("")) {
			return true;
		}
		
		if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
			return false;
		}

		String[] tokens = date.split("-");
		int year = Integer.parseInt(tokens[0]);
		int month = Integer.parseInt(tokens[1]);
		int day = Integer.parseInt(tokens[2]);

		if (month > 12 || day > 31) {
			return false;
		}

		if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day > 30) {
				return false;
			}
		}

		// fevrier
		if (month == 2) {
			// si annee bisextile
			if (year % 4 == 0 && year % 100 != 0) {
				if (day > 29) {
					return false;
				}
			} else if (year % 400 == 0) {
				if (day > 29) {
					return false;
				}
			} else {
				if (day > 28) {
					return false;
				}
			}
		}

		return true;
	}
	
	public static String normalizeOrderBy(String orderBy) {
		switch (orderBy) {
		case "introduced":
		case "discontinued":
			return orderBy;
		case "name":
			return "c." + orderBy;
		case "company":
			return "co.name";
		default:
			throw new BadOrderValueException();	
		}
	}
}
