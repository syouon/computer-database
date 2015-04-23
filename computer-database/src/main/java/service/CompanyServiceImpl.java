package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.Company;
import dao.CompanyDAO;
import dao.CompanyDAOImpl;
import dao.ComputerDAOImpl;
import dao.ConnectionFactory;
import exception.RollBackException;

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
		Connection conn = ConnectionFactory.getInstance().openConnection();

		try {
			// Debut de la transaction
			conn.setAutoCommit(false);
			ComputerDAOImpl.getInstance().deleteByCompany(id, conn);
			dao.delete(id, conn);

			// fin de la transaction
			conn.commit();
			conn.setAutoCommit(true);

			return true;
		} catch (SQLException e) {
			// rollback de la transaction
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new RollBackException();
			}
			return false;
		} finally {
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	public static CompanyServiceImpl getInstance() {
		return INSTANCE;
	}
}
