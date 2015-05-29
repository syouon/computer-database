package com.excilys.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import com.excilys.model.Company;

import org.springframework.context.ApplicationContext;

import com.excilys.service.*;

/**
 * The Class Choice. Represents an Option in the Command-Line Interface. Each
 * choice is associated with services. Provides methods to get user input in
 * command-line.
 */
public abstract class Choice {

	protected CompanyService companyService;
	protected RestWebService webservice;

	public Choice() {
		// Get Spring context to initialize company service
		ApplicationContext context = Main.getContext();
		companyService = context.getBean(CompanyService.class);
		webservice = RestWebService.INSTANCE;
	}

	/**
	 * Ask for computer id in command-line.
	 * 
	 * @return the computer id
	 */
	protected long askForComputerId() {
		long id = -1;

		while (true) {
			System.out.print("> Choose a computer id: ");
			String input = Menu.getScanner().nextLine();
			// Verify if input is a number
			boolean match = Pattern.matches("\\d*", input);
			if (match) {
				id = Long.parseLong(input);
				break;
			}

			System.out.println("O_o");
		}

		return id;
	}

	/**
	 * Ask for computer name in command-line.
	 * 
	 * @return the name
	 */
	protected String askForComputerName() {
		System.out.print("> Choose a computer name: ");
		return Menu.getScanner().nextLine();
	}

	/**
	 * Ask for company in command-line.
	 * 
	 * @return the company
	 */
	protected Company askForCompany() {
		System.out.print("> Choose a company name: ");
		String input = Menu.getScanner().nextLine();
		if (input.equals("")) { // Company name is optionnal
			return null;
		}

		Company company = companyService.find(input);
		return company;
	}

	/**
	 * Ask for introduction date in command-line.
	 * 
	 * @return the date
	 */
	protected LocalDate askForIntroductionDate() {

		while (true) {
			System.out.print("> Introduced in: ");
			try {
				String input = Menu.getScanner().nextLine();
				if (input.equals("")) { // Introduction date is optionnal
					return null;
				}

				return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);

			} catch (DateTimeParseException e) {
				System.out.println("Bad format (should be YYYY-MM-DD)");
				continue;
			}
		}
	}

	/**
	 * Ask for discontinuation date in command-line.
	 * 
	 * @return the date
	 */
	protected LocalDate askForDiscontinuationDate() {

		while (true) {
			System.out.print("> Discontinued in: ");
			try {
				String input = Menu.getScanner().nextLine();
				if (input.equals("")) { // Discontinuation date is optionnal
					return null;
				}

				return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);

			} catch (DateTimeParseException e) {
				System.out.println("Bad format (should be YYYY-MM-DD)");
				continue;
			}
		}
	}

	/**
	 * Execute action associated with this choice.
	 */
	public abstract void execute();
}
