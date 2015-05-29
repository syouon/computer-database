package com.excilys.ui;

/**
 * The Class DeleteComputerChoice.
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
