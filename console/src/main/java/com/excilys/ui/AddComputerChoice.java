package com.excilys.ui;

import java.time.LocalDate;

import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * The Class AddComputerChoice. Adding a computer option in the menu.
 * 
 * @see com.excilys.ui.Choice
 */
public class AddComputerChoice extends Choice {

	@Override
	public void execute() {
		// Retrieve information in command-line
		String name = askForComputerName();
		LocalDate introduced = askForIntroductionDate();
		LocalDate discontinued = askForDiscontinuationDate();
		Company company = askForCompany();

		// Set the computer to add
		Computer computer = new Computer(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany(company);

		webservice.addComputer(computer);
	}

	@Override
	public String toString() {
		return "add a computer";
	}

}
