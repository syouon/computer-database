package ui;

import java.time.LocalDateTime;

import model.Company;
import model.Computer;
import services.Services;

/**
 * The Class AddComputerChoice.
 */
public class AddComputerChoice extends Choice {

	/**
	 * Instantiates a new adds the computer choice.
	 *
	 * @param services
	 *            the services
	 */
	public AddComputerChoice(Services services) {
		super(services);
	}

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
		computer.setManufacturer(manufacturer);

		if (!services.addComputer(computer)) {
			System.out.println("Operation failed");
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
