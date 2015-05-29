package com.excilys.ui;

/**
 * The Class DeleteComputerChoice. Deleting a computer option in the menu.
 */
public class DeleteComputerChoice extends Choice {

	@Override
	public void execute() {
		long id = askForComputerId();
		webservice.deleteComputer(id);
	}

	@Override
	public String toString() {
		return "delete a computer";
	}
}
