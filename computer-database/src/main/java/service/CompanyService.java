package service;

import java.util.List;

import model.Company;
import dao.CompanyDAOImpl;

public enum CompanyService {
	INSTANCE;

	public List<Company> listCompanies(int start, int range) {
		return CompanyDAOImpl.getInstance().findAll(start, range);
	}

	public List<Company> listCompanies() {
		return CompanyDAOImpl.getInstance().findAll();
	}

	public Company find(long id) {
		return CompanyDAOImpl.getInstance().find(id);
	}
	
	public static CompanyService getInstance() {
		return INSTANCE;
	}
}
