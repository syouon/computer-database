package ui;

import java.time.LocalDateTime;

import services.Services;

/**
 * The Class UpdateDiscontinuationChoice.
 */
public class UpdateDiscontinuationChoice extends Choice {

	/**
	 * Instantiates a new update discontinuation choice.
	 *
	 * @param services
	 *            the services
	 */
	public UpdateDiscontinuationChoice(Services services) {
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
		LocalDateTime discontinued = askForDiscontinuationDate();
		if (!services.updateDiscontinuationDate(id, discontinued)) {
			System.out.println("Update failed");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "update discontinuation date";
	}
}
