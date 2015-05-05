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
		computer.setIntroductionDate(introduced);
		computer.setDiscontinuationDate(discontinued);

		if (company != null && dao.exists(company)) {
			computer.setCompany(company);
		}

		if (!computerService.updateComputer(computer)) {
			System.out.println("Update failed");
		}
	}

	public String toString() {
		return "update a computer";
	}
}
