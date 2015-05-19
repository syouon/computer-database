package ui;

import java.time.LocalDate;

import model.Company;
import model.Computer;

import org.springframework.beans.factory.annotation.Autowired;

import dao.CompanyDAO;

public class UpdateComputerChoice extends Choice {

	@Autowired
	private CompanyDAO dao;

	@Override
	public void execute() {
		long id = askForComputerId();
		LocalDate introduced = askForIntroductionDate();
		LocalDate discontinued = askForDiscontinuationDate();
		Company company = askForCompany();

		Computer computer = new Computer.Builder("").setId(id).build();
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);

		if (company != null && (dao.findByName(company.getName()) != null)) {
			computer.setCompany(company);
		}

		computerService.updateComputer(computer);
	}

	public String toString() {
		return "update a computer";
	}
}
