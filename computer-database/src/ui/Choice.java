package ui;

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

	public abstract void execute();
}
