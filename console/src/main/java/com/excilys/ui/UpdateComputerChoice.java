package com.excilys.ui;

import java.time.LocalDate;

import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * The Class UpdateComputerChoice. Editing a computer option in the menu.
 */
public class UpdateComputerChoice extends Choice {

	@Override
	public void execute() {
		// Retrieve user's input for the new fields of the computer
		long id = askForComputerId();
		String newName = askForComputerName();
		LocalDate introduced = askForIntroductionDate();
		LocalDate discontinued = askForDiscontinuationDate();
		Company company = askForCompany();

		// Set the new computer's fields
		Computer computer = new Computer.Builder(newName).setId(id)
				.setIntroduced(introduced).setDiscontinued(discontinued)
				.build();

		// If the company chosen by the user exists
		if (company != null && (companyService.find(company.getName()) != null)) {
			computer.setCompany(company);
		}

		webservice.updateComputer(computer);
	}

	public String toString() {
		return "update a computer";
	}
}
