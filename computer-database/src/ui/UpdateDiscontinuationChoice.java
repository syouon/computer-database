package ui;

import java.sql.Date;

import services.Services;

public class UpdateDiscontinuationChoice extends Choice {

	public UpdateDiscontinuationChoice(Services services) {
		super(services);
	}

	@Override
	public void execute() {
		long id = askForComputerId();
		Date discontinued = askForDiscontinuationDate();
		if (!services.updateDiscontinuationDate(id, discontinued)) {
			System.out.println("Update failed");
		}
	}

	@Override
	public String toString() {
		return "update discontinuation date";
	}
}
