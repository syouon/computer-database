package ui;

import services.Services;

public class DeleteComputerChoice extends Choice {

	public DeleteComputerChoice(Services services) {
		super(services);
	}

	@Override
	public void execute() {
		long id = askForComputerId();
		if (!services.deleteComputer(id)) {
			System.out.println("Delete failed!");
		}
	}

	@Override
	public String toString() {
		return "delete a computer";
	}
}
