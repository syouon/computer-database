package ui;

import services.Services;

/**
 * The Class ShowDetailsChoice.
 */
public class ShowDetailsChoice extends Choice {

	/**
	 * Instantiates a new show details choice.
	 *
	 * @param services
	 *            the services
	 */
	public ShowDetailsChoice(Services services) {
		super(services);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		long id = askForComputerId();
		services.showComputerDetails(id);
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
