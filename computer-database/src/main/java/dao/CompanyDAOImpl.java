package dao;

import static dao.DatabaseNaming.COMPANY_ID;
import static dao.DatabaseNaming.COMPANY_NAME;
import static dao.DatabaseNaming.COMPANY_TABLE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.DAOException;

public enum CompanyDAOImpl implements CompanyDAO {
	INSTANCE;
	private Logger logger;

	private CompanyDAOImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public static CompanyDAOImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public Company find(long id) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT * FROM " + COMPANY_TABLE
					+ " WHERE " + COMPANY_ID + "=?;");
			statement.setLong(1, id);
			result = statement.executeQuery();

			return DatabaseMapper.toCompany(result);
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Override
	public List<Company> findAll(int start, int range) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT " + COMPANY_NAME + ","
					+ COMPANY_ID + " FROM " + COMPANY_TABLE + " ORDER BY "
					+ COMPANY_NAME + " LIMIT ? OFFSET ?;");
			statement.setInt(1, range);
			statement.setInt(2, start);
			result = statement.executeQuery();
			return DatabaseMapper.toCompanyList(result);

		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Override
	public List<Company> findAll() {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT " + COMPANY_NAME + ","
					+ COMPANY_ID + " FROM " + COMPANY_TABLE + ";");
			result = statement.executeQuery();
			return DatabaseMapper.toCompanyList(result);

		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	/* Modifie l'objet company en ajoutant son id */
	@Override
	public boolean exists(Company company) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try { // On suppose que le champs id n'est pas renseigne
			statement = conn.prepareStatement("SELECT * FROM " + COMPANY_TABLE
					+ " WHERE " + COMPANY_NAME + " LIKE ?;");
			statement.setString(1, company.getName());
			result = statement.executeQuery();

			if (result.next()) {
				long id = result.getLong(COMPANY_ID);
				company.setId(id);
				return true;
			}

			return false;

		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Override
	public boolean delete(long id) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;

		try {
			statement = conn.prepareStatement("DELETE FROM " + COMPANY_TABLE
					+ " WHERE " + COMPANY_ID + "=?;");
			statement.setLong(1, id);
			statement.executeUpdate();

			return true;
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, null);
		}
	}
}
