package service;

import java.sql.SQLException;
import java.util.List;

import model.Company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CompanyDAO;
import dao.ComputerDAO;
import dao.ConnectionFactory;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private ConnectionFactory factory;

	private CompanyServiceImpl() {
		// dao = CompanyDAOImpl.getInstance();
	}

	public List<Company> listCompanies(int start, int range) {
		return companyDAO.findAll(start, range);
	}

	public List<Company> listCompanies() {
		return companyDAO.findAll();
	}

	public Company find(long id) {
		return companyDAO.find(id);
	}

	public boolean exists(Company company) {
		return companyDAO.exists(company);
	}

	public boolean deleteCompany(long id) {

		try {
			factory.startTransaction();
			computerDAO.deleteByCompany(id);
			companyDAO.delete(id);
			factory.endTransaction();

			return true;
		} catch (SQLException e) {
			factory.rollback();
			return false;
		} finally {
			factory.closeConnection();
		}
	}
}
