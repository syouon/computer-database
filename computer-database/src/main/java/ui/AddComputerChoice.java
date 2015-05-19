package ui;

import java.time.LocalDate;

import model.Company;
import model.Computer;

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

		// computerService.addComputer(computer);
	}

	/**
	 * Ask for computer name.
	 * 
	 * @return the string
	 */
	private String askForComputerName() {
		System.out.print("> Choose a computer name: ");
		return Menu.getScanner().nextLine();
	}

	@Override
	public String toString() {
		return "add a computer";
	}

}
