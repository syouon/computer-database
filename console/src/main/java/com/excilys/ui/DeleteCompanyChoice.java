package com.excilys.ui;

import com.excilys.model.Company;

/**
 * The Class DeleteCompanyChoice. Deleting a company option in the menu.
 * 
 * @see com.excilys.ui.Choice
 */
public class DeleteCompanyChoice extends Choice {

	@Override
	public void execute() {
		Company company = askForCompany();
		// if company exists, we can delete it
		if (companyService.find(company.getName()) != null) {
			webservice.deleteCompany(company.getId());
		}
	}

	@Override
	public String toString() {
		return "delete a company";
	}
}
