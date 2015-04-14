package service;

import java.util.List;

import model.Company;
import dao.ConcreteCompanyDAO;

public enum CompanyService {
	INSTANCE;

	public List<Company> listCompanies() {
		return ConcreteCompanyDAO.getInstance().findAll();
	}

	public static CompanyService getInstance() {
		return INSTANCE;
	}
}
