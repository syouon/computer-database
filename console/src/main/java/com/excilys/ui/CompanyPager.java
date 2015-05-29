package com.excilys.ui;

import java.util.List;

import com.excilys.model.Company;

/**
 * The Class CompanyPager. Provides Pagination for company listing.
 */
public class CompanyPager extends Pager {

	private List<Company> entities;

	public CompanyPager() {
		entities = webservice.listCompanies(page);
	}

	@Override
	public void next() {
		// Go to next page only if not at the end of pages
		if (entities.size() == entitiesNumber) {
			super.next();
		}
	}

	@Override
	public void refresh() {
		entities = webservice.listCompanies(page);
	}

	@Override
	public void print() {
		for (Company company : entities) {
			System.out.println(company);
		}
	}
}
