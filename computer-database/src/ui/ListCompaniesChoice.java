package ui;

import services.Services;

public class ListCompaniesChoice extends Choice {

	public ListCompaniesChoice(Services services) {
		super(services);
	}

	@Override
	public void execute() {
		services.listCompanies();
	}

	@Override
	public String toString() {
		return "list companies";
	}
}
