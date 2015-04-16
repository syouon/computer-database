package ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import model.Company;

/* Un choix represente une entree du menu, une des options
 * offertes a l'utilisateur.
 * A un choix est associe son service correspondant.
 */
/**
 * The Class Choice.
 */
public abstract class Choice {

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

		return new Company(input);
	}

	/**
	 * Ask for introduction date.
	 * 
	 * @return the date
	 */
	protected LocalDateTime askForIntroductionDate() {

		while (true) {
			System.out.print("> Introduced in: ");
			try {
				String input = Menu.getScanner().nextLine();
				if (input.equals("")) {
					return null;
				}

				return LocalDateTime.parse(input,
						DateTimeFormatter.ISO_LOCAL_DATE_TIME);

			} catch (DateTimeParseException e) {
				System.out
						.println("Bad format (should be YYYY-MM-DDTHH:mm:ss)");
				continue;
			}
		}
	}

	/**
	 * Ask for discontinuation date.
	 * 
	 * @return the date
	 */
	protected LocalDateTime askForDiscontinuationDate() {

		while (true) {
			System.out.print("> Discontinued in: ");
			try {
				String input = Menu.getScanner().nextLine();
				if (input.equals("")) {
					return null;
				}

				return LocalDateTime.parse(input,
						DateTimeFormatter.ISO_LOCAL_DATE_TIME);

			} catch (DateTimeParseException e) {
				System.out
						.println("Bad format (should be YYYY-MM-DDTHH:mm:ss)");
				continue;
			}
		}
	}

	/**
	 * Execute.
	 */
	public abstract void execute();
}
