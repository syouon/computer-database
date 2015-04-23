package ui;

import service.CompanyServiceImpl;
import model.Company;

public class DeleteCompanyChoice extends Choice {

	@Override
	public void execute() {
		Company company = askForCompany();
		if (!CompanyServiceImpl.getInstance().exists(company)
				|| !CompanyServiceImpl.getInstance().deleteCompany(
						company.getId())) {
			System.out.println("Deletion failed!");
		}
	}

	@Override
	public String toString() {
		return "delete a company";
	}
}
