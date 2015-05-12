package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import exception.BadOrderValueException;
import exception.PropertiesNotFoundException;

public class Utils {

	public static boolean isWellFormedDate(String date) {
		if (date.isEmpty()) {
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
			// si annee bissextile
			if (year % 4 == 0 && year % 100 != 0) {
				if (day > 29) {
					return false;
				}
			} else if (year % 400 == 0) {
				if (day > 29) {
					return false;
				}
			} else { // sinon
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

	public static Properties loadProperties(String file) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = Utils.class.getClassLoader().getResourceAsStream(file);

			if (input != null) {
				prop.load(input);
			}

			return prop;
		} catch (IOException e) {
			throw new PropertiesNotFoundException();
		} /*
		 * finally { try { input.close(); } catch (IOException e) { throw new
		 * PropertiesNotFoundException(); } }
		 */
	}
}
