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

	public abstract void execute();
}
