package service;

import java.sql.SQLException;
import java.util.List;

import model.Company;
import dao.CompanyDAO;
import dao.CompanyDAOImpl;
import dao.ComputerDAOImpl;
import dao.ConnectionFactory;

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

	public boolean exists(Company company) {
		return dao.exists(company);
	}

	public boolean deleteCompany(long id) {
		ConnectionFactory factory = ConnectionFactory.getInstance();

		try {
			factory.startTransaction();
			ComputerDAOImpl.getInstance().deleteByCompany(id);
			dao.delete(id);
			factory.endTransaction();

			return true;
		} catch (SQLException e) {
			factory.rollback();
			return false;
		} finally {
			factory.closeConnection();
		}
	}

	public static CompanyServiceImpl getInstance() {
		return INSTANCE;
	}
}
