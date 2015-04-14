package ui;

import model.Company;
import services.Services;

/**
 * The Class UpdateCompanyChoice.
 */
public class UpdateCompanyChoice extends Choice {

	/**
	 * Instantiates a new update company choice.
	 *
	 * @param services the services
	 */
	public UpdateCompanyChoice(Services services) {
		super(services);
	}

	/* (non-Javadoc)
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		long id = askForComputerId();
		Company company = askForCompany();
		if (!services.updateCompany(id, company)) {
			System.out.println("Update failed");
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "update company";
	}
}
