package ui;

import java.time.LocalDate;

import model.Company;
import model.Computer;
import service.ComputerService;

/**
 * The Class AddComputerChoice.
 */
public class AddComputerChoice extends Choice {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		String name = askForComputerName();
		LocalDate introduced = askForIntroductionDate();
		LocalDate discontinued = askForDiscontinuationDate();
		Company company = askForCompany();

		Computer computer = new Computer(name);
		computer.setIntroductionDate(introduced);
		computer.setDiscontinuationDate(discontinued);
		computer.setCompany(company);

		if (!ComputerService.getInstance().addComputer(computer)) {
			System.out.println("Addition failed");
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "add a computer";
	}

}
