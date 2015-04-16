package service;

import java.util.List;

import model.Company;
import dao.ConcreteCompanyDAO;

public enum CompanyService {
	INSTANCE;

	public List<Company> listCompanies(int start, int range) {
		return ConcreteCompanyDAO.getInstance().findAll(start, range);
	}

	public static CompanyService getInstance() {
		return INSTANCE;
	}
}
