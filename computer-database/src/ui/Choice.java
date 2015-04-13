package ui;

import java.sql.Date;

import services.Services;

/* Un choix represente une entree du menu, une des options
 * offertes a l'utilisateur.
 * A un choix est associe son service correspondant.
 */
public abstract class Choice {

	protected Services services;

	public Choice(Services services) {
		this.services = services;
	}

	protected long askForComputerId() {
		long input = -1;

		while (true) {
			System.out.print("> Choose a computer id: ");
			try {
				input = Long.parseLong(Menu.getScanner().nextLine());
				break;
			} catch (NumberFormatException e) {
				System.out.println("O_o");
				continue;
			}
		}

		return input;
	}

	protected Date askForIntroductionDate() {

		while (true) {
			System.out.print("> Introduced in: ");
			try {
				String input = Menu.getScanner().nextLine();
				if (input.equals("")) {
					return null;
				}

				return Date.valueOf(input);

			} catch (IllegalArgumentException e) {
				System.out.println("Bad format (should be yyyy-[m]m-[d]d)");
				continue;
			}
		}
	}

	protected Date askForDiscontinuationDate() {

		while (true) {
			System.out.print("> discontinued in: ");
			try {
				String input = Menu.getScanner().nextLine();
				if (input.equals("")) {
					return null;
				}

				return Date.valueOf(input);

			} catch (IllegalArgumentException e) {
				System.out.println("Bad format (should be yyyy-[m]m-[d]d)");
				continue;
			}
		}
	}
	
	public abstract void execute();
}
