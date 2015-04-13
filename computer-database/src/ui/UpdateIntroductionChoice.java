package ui;

import java.sql.Date;

import services.Services;

public class UpdateIntroductionChoice extends Choice {

	public UpdateIntroductionChoice(Services services) {
		super(services);
	}

	@Override
	public void execute() {
		long id = askForComputerId();
		Date introduced = askForIntroductionDate();
		if (!services.updateIntroductionDate(id, introduced)) {
			System.out.println("Update failed");
		}
	}

	@Override
	public String toString() {
		return "update introduction date";
	}
}
