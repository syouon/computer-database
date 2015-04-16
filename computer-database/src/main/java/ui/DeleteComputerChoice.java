package ui;

import service.ComputerService;

/**
 * The Class DeleteComputerChoice.
 */
public class DeleteComputerChoice extends Choice {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		long id = askForComputerId();
		if (!ComputerService.getInstance().deleteComputer(id)) {
			System.out.println("Delete failed!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "delete a computer";
	}
}
