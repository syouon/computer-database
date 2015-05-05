package ui;

import java.util.List;

import model.Company;

public class CompanyPager extends Pager {

	private List<Company> entities;

	public CompanyPager() {
		super();
		entities = companyService.listCompanies(start,
				entitiesNumber);
	}

	@Override
	public void next() {
		if (entities.size() == entitiesNumber) {
			super.next();
		}
	}
	
	@Override
	public void refresh() {
		entities = companyService.listCompanies(start,
				entitiesNumber);
	}

	@Override
	public void print() {
		for (Company company : entities) {
			System.out.println(company);
		}
	}

}
