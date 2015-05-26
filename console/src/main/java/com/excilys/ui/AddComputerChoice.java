package com.excilys.ui;

import java.time.LocalDate;

import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * The Class AddComputerChoice.
 */
public class AddComputerChoice extends Choice {

	@Override
	public void execute() {
		String name = askForComputerName();
		LocalDate introduced = askForIntroductionDate();
		LocalDate discontinued = askForDiscontinuationDate();
		Company company = askForCompany();

		Computer computer = new Computer(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany(company);

		computerService.addComputer(computer);
	}

	@Override
	public String toString() {
		return "add a computer";
	}

}
