package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import mapper.Mapper;
import model.Company;
import model.Computer;
import static database.DatabaseNaming.*;

public final class MySQLServices extends Services {

	public MySQLServices(Connection conn) {
		super(conn);
	}

	private void closeResultSetAndStatement(Statement statement,
			ResultSet result) {

		try {
			if (result != null) {
				result.close();
			}

			if (statement != null) {
				statement.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Computer> listComputers() {
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = conn.createStatement();
			result = statement.executeQuery("SELECT " + COMPUTER_NAME + ","
					+ COMPUTER_ID + " FROM " + COMPUTER_TABLE + " ORDER BY "
					+ COMPUTER_NAME + ";");
			return Mapper.toComputerList(result);

		} catch (SQLException e) {
			return null;

		} finally { // fermeture du result et du statement
			closeResultSetAndStatement(statement, result);
		}
	}

	@Override
	public List<Company> listCompanies() {
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = conn.createStatement();
			result = statement.executeQuery("SELECT " + COMPANY_NAME + ","
					+ COMPANY_ID + " FROM " + COMPANY_TABLE + " ORDER BY "
					+ COMPANY_NAME + ";");
			return Mapper.toCompanyList(result);

		} catch (SQLException e) {
			return null;

		} finally {
			closeResultSetAndStatement(statement, result);
		}
	}

	@Override
	public void showComputerDetails(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addComputer(Computer computer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateComputer(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteComputer(long id) {
		Statement statement = null;

		try {
			statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM " + COMPUTER_TABLE + " WHERE "
					+ COMPUTER_ID + "=" + id + ";");
			return true;

		} catch (SQLException e) {
			return false;

		} finally {
			closeResultSetAndStatement(statement, null);
		}
	}
}
