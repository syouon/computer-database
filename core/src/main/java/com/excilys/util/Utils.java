package com.excilys.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.springframework.context.i18n.LocaleContextHolder;

import com.excilys.exception.PropertiesNotFoundException;

/**
 * The Class Utils provides several utilities methods.
 */
public class Utils {

	/**
	 * Checks if a date is valid.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param day
	 *            the day
	 * @return true, if the date is valid
	 */
	private static boolean isValidDate(int year, int month, int day) {
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

	/**
	 * Checks if the date is well formed.
	 *
	 * @param date
	 *            the date
	 * @return true, if the date is well formed
	 */
	public static boolean isWellFormedDate(String date) {
		if (date.isEmpty()) {
			return true;
		}

		Locale locale = LocaleContextHolder.getLocale();
		String english = new Locale("en").getLanguage();
		String french = new Locale("fr").getLanguage();
		if ((locale.getLanguage().equals(english) && !date
				.matches("\\d{4}-\\d{2}-\\d{2}"))
				|| (locale.getLanguage().equals(french) && !date
						.matches("\\d{2}-\\d{2}-\\d{4}"))) {
			return false;
		}

		String[] tokens = date.split("-");
		int year, day;
		int month = Integer.parseInt(tokens[1]);
		if (locale.getLanguage().equals(french)) {
			year = Integer.parseInt(tokens[2]);
			day = Integer.parseInt(tokens[0]);
		} else { // si locale en anglais
			year = Integer.parseInt(tokens[0]);
			day = Integer.parseInt(tokens[2]);
		}

		return isValidDate(year, month, day);
	}

	/**
	 * Normalize orderby parameter for DashboardController.
	 *
	 * @see com.excilys.controller.DashboardController
	 * @param orderBy
	 *            the orderby parameter
	 * @return the normalized orderby parameter
	 */
	public static String normalizeOrderBy(String orderBy) {
		switch (orderBy) {
		case "introduced":
		case "discontinued":
		case "name":
			return orderBy;
		case "company":
			return "company.name";
		default:
			return "id";
		}
	}

	/**
	 * Load a properties file.
	 *
	 * @param file
	 *            the file
	 * @return a Properties java object
	 * @see java.util.Properties
	 */
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
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				throw new PropertiesNotFoundException();
			}
		}
	}
}
