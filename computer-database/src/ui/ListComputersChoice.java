package ui;

import services.Services;

public class ListComputersChoice extends Choice {

	public ListComputersChoice(Services services) {
		super(services);
	}

	@Override
	public void execute() {
		services.listComputers();
	}

	@Override
	public String toString() {
		return "list computers";
	}
}
