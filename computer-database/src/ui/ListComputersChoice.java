package ui;

import java.util.List;

import model.Computer;
import services.Services;

public class ListComputersChoice extends Choice {

	public ListComputersChoice(Services services) {
		super(services);
	}

	@Override
	public void execute() {
		printResult(services.listComputers());
	}

	private void printResult(List<Computer> computers) {
		for (Computer computer : computers) {
			System.out.println(computer);
		}
	}

	@Override
	public String toString() {
		return "list computers";
	}
}
