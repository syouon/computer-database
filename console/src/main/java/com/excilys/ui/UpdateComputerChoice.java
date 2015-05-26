package com.excilys.ui;

import java.time.LocalDate;

import com.excilys.model.Company;
import com.excilys.model.Computer;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.dao.CompanyDAO;

public class UpdateComputerChoice extends Choice {

	@Autowired
	private CompanyDAO dao;

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

		if (company != null && (dao.findByName(company.getName()) != null)) {
			computer.setCompany(company);
		}

		computerService.updateComputer(computer);
	}

	public String toString() {
		return "update a computer";
	}
}
