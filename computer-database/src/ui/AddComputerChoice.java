package ui;

import java.time.LocalDateTime;

import model.Company;
import model.Computer;
import service.ComputerService;
import service.Services;

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
		LocalDateTime introduced = askForIntroductionDate();
		LocalDateTime discontinued = askForDiscontinuationDate();
		Company manufacturer = askForCompany();

		Computer computer = new Computer(name);
		computer.setIntroductionDate(introduced);
		computer.setDiscontinuationDate(discontinued);
		computer.setCompany(manufacturer);

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
