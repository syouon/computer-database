package ui;

import java.sql.Date;

import services.Services;

/**
 * The Class UpdateIntroductionChoice.
 */
public class UpdateIntroductionChoice extends Choice {

	/**
	 * Instantiates a new update introduction choice.
	 *
	 * @param services the services
	 */
	public UpdateIntroductionChoice(Services services) {
		super(services);
	}

	/* (non-Javadoc)
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		long id = askForComputerId();
		Date introduced = askForIntroductionDate();
		if (!services.updateIntroductionDate(id, introduced)) {
			System.out.println("Update failed");
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "update introduction date";
	}
}
