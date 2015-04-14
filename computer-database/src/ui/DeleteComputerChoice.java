package ui;

import services.Services;

/**
 * The Class DeleteComputerChoice.
 */
public class DeleteComputerChoice extends Choice {

	/**
	 * Instantiates a new delete computer choice.
	 *
	 * @param services the services
	 */
	public DeleteComputerChoice(Services services) {
		super(services);
	}

	/* (non-Javadoc)
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		long id = askForComputerId();
		if (!services.deleteComputer(id)) {
			System.out.println("Delete failed!");
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "delete a computer";
	}
}
