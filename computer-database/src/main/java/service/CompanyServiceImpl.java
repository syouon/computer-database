package service;

import java.util.List;

import model.Company;
import dao.CompanyDAO;
import dao.CompanyDAOImpl;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;

	private CompanyDAO dao;
	
	private CompanyServiceImpl() {
		dao = CompanyDAOImpl.getInstance();
	}
	
	public List<Company> listCompanies(int start, int range) {
		return dao.findAll(start, range);
	}

	public List<Company> listCompanies() {
		return dao.findAll();
	}

	public Company find(long id) {
		return dao.find(id);
	}

	public static CompanyServiceImpl getInstance() {
		return INSTANCE;
	}
}
