package ui;

import java.sql.Date;

import model.Company;
import model.Computer;
import services.Services;

public class AddComputerChoice extends Choice {

	public AddComputerChoice(Services services) {
		super(services);
	}

	@Override
	public void execute() {
		String name = askForComputerName();
		Date introduced = askForIntroductionDate();
		Date discontinued = askForDiscontinuationDate();
		Company manufacturer = askForCompany();

		Computer computer = new Computer(name);
		computer.setIntroductionDate(introduced);
		computer.setDiscontinuationDate(discontinued);
		computer.setManufacturer(manufacturer);

		if (!services.addComputer(computer)) {
			System.out.println("Operation failed");
		}
	}

	private String askForComputerName() {
		System.out.print("> Choose a computer name: ");
		return Menu.getScanner().nextLine();
	}

	private Company askForCompany() {
		System.out.print("> Choose a company name: ");
		String input = Menu.getScanner().nextLine();
		if (input.equals("")) {
			return null;
		}

		return new Company(input);
	}

	@Override
	public String toString() {
		return "add a computer";
	}

}
