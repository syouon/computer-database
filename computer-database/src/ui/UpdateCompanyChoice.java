package ui;

import model.Company;
import services.Services;

public class UpdateCompanyChoice extends Choice {

	public UpdateCompanyChoice(Services services) {
		super(services);
	}

	@Override
	public void execute() {
		long id = askForComputerId();
		Company company = askForCompany();
		if (!services.updateCompany(id, company)) {
			System.out.println("Update failed");
		}
	}

	@Override
	public String toString() {
		return "update company";
	}
}
