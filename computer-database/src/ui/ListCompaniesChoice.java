package ui;

import java.util.List;

import model.Company;
import services.Services;

/**
 * The Class ListCompaniesChoice.
 */
public class ListCompaniesChoice extends Choice {

	/**
	 * Instantiates a new list companies choice.
	 *
	 * @param services
	 *            the services
	 */
	public ListCompaniesChoice(Services services) {
		super(services);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		printResult(services.listCompanies());
	}

	/**
	 * Prints the result.
	 *
	 * @param companies
	 *            the companies
	 */
	private void printResult(List<Company> companies) {
		for (Company company : companies) {
			System.out.println(company);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "list companies";
	}
}
