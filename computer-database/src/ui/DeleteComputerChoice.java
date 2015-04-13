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

	private long askForComputerId() {
		long input = -1;

		while (true) {
			System.out.print("> Choose a computer id: ");
			try {
				input = Long.parseLong(Menu.getScanner().nextLine());
				break;
			} catch (NumberFormatException e) {
				System.out.println("O_o");
				continue;
			}
		}

		return input;
	}

	@Override
	public String toString() {
		return "delete a computer";
	}
}
