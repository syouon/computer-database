package ui;

import services.Services;

public class ShowDetailsChoice extends Choice {

	public ShowDetailsChoice(Services services) {
		super(services);
	}

	@Override
	public void execute() {
		long id = askForComputerId();
		services.showComputerDetails(id);
	}

	@Override
	public String toString() {
		return "show computer details";
	}
}
