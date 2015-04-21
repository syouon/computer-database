package ui;

import service.ComputerServiceImpl;

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
		System.out.println(ComputerServiceImpl.getInstance()
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
