package ui;

import service.ComputerService;

/**
 * The Class ShowDetailsChoice.
 */
public class ShowDetailsChoice extends Choice {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		long id = askForComputerId();
		System.out.println(ComputerService.getInstance()
				.showComputerDetails(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "show computer details";
	}
}
