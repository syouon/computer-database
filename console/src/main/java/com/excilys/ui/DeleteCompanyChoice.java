package com.excilys.ui;

import com.excilys.model.Company;

public class DeleteCompanyChoice extends Choice {

	@Override
	public void execute() {
		Company company = askForCompany();
		if (companyService.exists(company)) {
			webservice.deleteCompany(company.getId());
		}
	}

	@Override
	public String toString() {
		return "delete a company";
	}
}
