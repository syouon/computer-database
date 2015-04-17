package ui;

import java.time.LocalDate;

import model.Company;
import model.Computer;
import service.ComputerService;
import dao.ConcreteCompanyDAO;

public class UpdateComputerChoice extends Choice {

	@Override
	public void execute() {
		long id = askForComputerId();
		LocalDate introduced = askForIntroductionDate();
		LocalDate discontinued = askForDiscontinuationDate();
		Company company = askForCompany();

		Computer computer = new Computer(id, "");
		computer.setIntroductionDate(introduced);
		computer.setDiscontinuationDate(discontinued);

		if (company != null && ConcreteCompanyDAO.getInstance().exists(company)) {
			computer.setCompany(company);
		}

		if (!ComputerService.getInstance().updateComputer(computer)) {
			System.out.println("Update failed");
		}
	}

	public String toString() {
		return "update a computer";
	}
}
