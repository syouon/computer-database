package ui;

import model.Company;

public class DeleteCompanyChoice extends Choice {

	@Override
	public void execute() {
		Company company = askForCompany();
		if (!companyService.exists(company)
				|| !companyService.deleteCompany(company.getId())) {
			System.out.println("Deletion failed!");
		}
	}

	@Override
	public String toString() {
		return "delete a company";
	}
}
