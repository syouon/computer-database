package services;

import java.util.List;

import model.Company;

public enum CompanyService {
	INSTANCE;

	public List<Company> listCompanies() {
		return null;
	}
	
	public static CompanyService getInstance() {
		return INSTANCE;
	}
}
