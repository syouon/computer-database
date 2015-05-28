package com.excilys.ui;

import java.util.List;

import com.excilys.model.Company;

public class CompanyPager extends Pager {

	private List<Company> entities;

	public CompanyPager() {
		super();
		entities = webservice.listCompanies(page);
	}

	@Override
	public void next() {
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
