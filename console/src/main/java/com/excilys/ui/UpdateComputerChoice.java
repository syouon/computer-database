package com.excilys.ui;

import java.time.LocalDate;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class UpdateComputerChoice extends Choice {

	@Override
	public void execute() {
		long id = askForComputerId();
		String newName = askForComputerName();
		LocalDate introduced = askForIntroductionDate();
		LocalDate discontinued = askForDiscontinuationDate();
		Company company = askForCompany();

		Computer computer = new Computer.Builder(newName).setId(id)
				.setIntroduced(introduced).setDiscontinued(discontinued)
				.build();

		if (company != null && (companyService.find(company.getName()) != null)) {
			computer.setCompany(company);
		}

		webservice.updateComputer(computer);
	}

	public String toString() {
		return "update a computer";
	}
}
