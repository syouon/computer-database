package dao;

import static dao.DatabaseNaming.COMPANY_ID;
import static dao.DatabaseNaming.COMPANY_NAME;
import static dao.DatabaseNaming.COMPANY_TABLE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.Company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import exception.DAOException;

@Repository("companyDAO")
public class CompanyDAOImpl implements CompanyDAO {
	private Logger logger;

	@Autowired
	private ConnectionFactory connection;

	private CompanyDAOImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public Company find(long id) {
		Connection conn = connection.openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT * FROM " + COMPANY_TABLE
					+ " WHERE " + COMPANY_ID + "=?;");
			statement.setLong(1, id);
			result = statement.executeQuery();
			logger.debug("Finding done");

			return DatabaseMapper.toCompany(result);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		}/*
		 * finally { connection.closeResultSetAndStatement(statement, result);
		 * connection.closeConnection(); }
		 */
	}

	@Override
	public List<Company> findAll(int start, int range) {
		Connection conn = connection.openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT " + COMPANY_NAME + ","
					+ COMPANY_ID + " FROM " + COMPANY_TABLE + " ORDER BY "
					+ COMPANY_NAME + " LIMIT ? OFFSET ?;");
			statement.setInt(1, range);
			statement.setInt(2, start);
			result = statement.executeQuery();
			logger.debug("Finding All done");

			return DatabaseMapper.toCompanyList(result);

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			connection.closeResultSetAndStatement(statement, result);
			// connection.closeConnection();
		}
	}

	@Override
	public List<Company> findAll() {
		Connection conn = connection.openConnection();
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = conn.createStatement();
			result = statement.executeQuery("SELECT " + COMPANY_NAME + ","
					+ COMPANY_ID + " FROM " + COMPANY_TABLE + ";");
			logger.debug("Finding All done");
			return DatabaseMapper.toCompanyList(result);

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			connection.closeResultSetAndStatement(statement, result);
			// connection.closeConnection();
		}
	}

	/* Modifie l'objet company en ajoutant son id */
	@Override
	public boolean exists(Company company) {
		Connection conn = connection.openConnection();
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
				logger.debug("Company exists");
				return true;
			}

			logger.debug("Company doesn't exists");
			return false;

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			connection.closeResultSetAndStatement(statement, result);
			// connection.closeConnection();
		}
	}

	@Override
	public boolean delete(long id) throws SQLException {
		Connection conn = connection.openConnection();
		PreparedStatement statement = null;

		try {
			statement = conn.prepareStatement("DELETE FROM " + COMPANY_TABLE
					+ " WHERE " + COMPANY_ID + "=?;");
			statement.setLong(1, id);
			statement.executeUpdate();
			logger.debug("Delete done");

			return true;
		} finally {
			connection.closeResultSetAndStatement(statement, null);
			// connection.closeConnection();
		}
	}
}
