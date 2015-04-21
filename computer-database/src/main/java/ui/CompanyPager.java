package ui;

import java.util.List;

import model.Company;
import service.CompanyServiceImpl;

public class CompanyPager extends Pager {

	private List<Company> entities;

	public CompanyPager() {
		super();
		entities = CompanyServiceImpl.getInstance().listCompanies(start,
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
		entities = CompanyServiceImpl.getInstance().listCompanies(start,
				entitiesNumber);
	}

	@Override
	public void print() {
		for (Company company : entities) {
			System.out.println(company);
		}
	}

}
