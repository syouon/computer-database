package ui;

import java.util.List;

import model.Company;
import service.CompanyService;

/**
 * The Class ListCompaniesChoice.
 */
public class ListCompaniesChoice extends Choice {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		printResult(CompanyService.getInstance().listCompanies());
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
