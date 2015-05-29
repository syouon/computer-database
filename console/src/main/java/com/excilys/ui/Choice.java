package com.excilys.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import com.excilys.model.Company;

import org.springframework.context.ApplicationContext;

import com.excilys.service.*;

/* Un choix represente une entree du menu, une des options
 * offertes a l'utilisateur.
 * A un choix est associe son service correspondant.
 */
/**
 * The Class Choice.
 */
public abstract class Choice {

	protected CompanyService companyService;
	protected RestWebService webservice;

	public Choice() {
		ApplicationContext context = Main.getContext();
		companyService = context.getBean(CompanyService.class);
		webservice = RestWebService.INSTANCE;
	}

	/**
	 * Ask for computer id.
	 * 
	 * @return the long
	 */
	protected long askForComputerId() {
		long id = -1;

		while (true) {
			System.out.print("> Choose a computer id: ");
			String input = Menu.getScanner().nextLine();
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
	 * Ask for computer name.
	 * 
	 * @return the string
	 */
	protected String askForComputerName() {
		System.out.print("> Choose a computer name: ");
		return Menu.getScanner().nextLine();
	}

	/**
	 * Ask for company.
	 * 
	 * @return the company
	 */
	protected Company askForCompany() {
		System.out.print("> Choose a company name: ");
		String input = Menu.getScanner().nextLine();
		if (input.equals("")) {
			return null;
		}

		Company company = companyService.find(input);
		return company;
	}

	/**
	 * Ask for introduction date.
	 * 
	 * @return the date
	 */
	protected LocalDate askForIntroductionDate() {

		while (true) {
			System.out.print("> Introduced in: ");
			try {
				String input = Menu.getScanner().nextLine();
				if (input.equals("")) {
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
	 * Ask for discontinuation date.
	 * 
	 * @return the date
	 */
	protected LocalDate askForDiscontinuationDate() {

		while (true) {
			System.out.print("> Discontinued in: ");
			try {
				String input = Menu.getScanner().nextLine();
				if (input.equals("")) {
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
	 * Execute.
	 */
	public abstract void execute();
}
