package ui;

import java.util.List;

import model.Company;
import services.Services;

public class ListCompaniesChoice extends Choice {

	public ListCompaniesChoice(Services services) {
		super(services);
	}

	@Override
	public void execute() {
		printResult(services.listCompanies());
	}

	private void printResult(List<Company> companies) {
		for (Company company : companies) {
			System.out.println(company);
		}
	}

	@Override
	public String toString() {
		return "list companies";
	}
}
