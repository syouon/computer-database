package ui;

import java.util.List;

import model.Computer;
import service.ComputerService;

/**
 * The Class ListComputersChoice.
 */
public class ListComputersChoice extends Choice {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		printResult(ComputerService.getInstance().listComputers());
	}

	/**
	 * Prints the result.
	 * 
	 * @param computers
	 *            the computers
	 */
	private void printResult(List<Computer> computers) {
		for (Computer computer : computers) {
			System.out.println(computer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "list computers";
	}
}
