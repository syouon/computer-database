package services;

import static database.DatabaseNaming.COMPANY_ID;
import static database.DatabaseNaming.COMPANY_NAME;
import static database.DatabaseNaming.COMPANY_TABLE;
import static database.DatabaseNaming.COMPUTER_COMPANYID;
import static database.DatabaseNaming.COMPUTER_DISCONTINUED;
import static database.DatabaseNaming.COMPUTER_ID;
import static database.DatabaseNaming.COMPUTER_INTRODUCED;
import static database.DatabaseNaming.COMPUTER_NAME;
import static database.DatabaseNaming.COMPUTER_TABLE;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import mapper.Mapper;
import model.Company;
import model.Computer;

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

		} finally {
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
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = conn.createStatement();

			if (companyIsNotNull(id)) {
				/*
				 * On joint les deux tables pour recuperer le nom de
				 * l'entreprise
				 */
				result = statement.executeQuery("SELECT * FROM "
						+ COMPUTER_TABLE + " as c1 JOIN " + COMPANY_TABLE
						+ " as c2 WHERE c1." + COMPUTER_ID + "=" + id
						+ " AND c1." + COMPUTER_COMPANYID + "=c2." + COMPANY_ID
						+ ";");
			} else {
				result = statement.executeQuery("SELECT * FROM "
						+ COMPUTER_TABLE + " WHERE " + COMPUTER_ID + "=" + id
						+ ";");
			}

			System.out.println(Mapper.toComputer(result));

		} catch (SQLException e) {
			return;
		} finally {
			closeResultSetAndStatement(statement, result);
		}
	}

	@Override
	public boolean addComputer(Computer computer) {
		PreparedStatement statement = null;

		Date introductionDate = computer.getIntroductionDate();
		Date discontinuationDate = computer.getDiscontinuationDate();

		Timestamp introduced = (introductionDate != null) ? (new Timestamp(
				introductionDate.getTime())) : null;
		Timestamp discontinued = (discontinuationDate != null) ? (new Timestamp(
				discontinuationDate.getTime())) : null;

		// Verification de l'existence de company
		Company company = computer.getManufacturer();
		Long companyId = null;
		if (company != null && companyExists(company)) {
			companyId = company.getId();
		}

		try {
			if (companyId == null) { // si company n'existe pas
				statement = conn.prepareStatement("INSERT INTO "
						+ COMPUTER_TABLE + "(" + COMPUTER_NAME + ","
						+ COMPUTER_INTRODUCED + "," + COMPUTER_DISCONTINUED
						+ ") VALUES (?,?,?);");
			} else {
				statement = conn.prepareStatement("INSERT INTO "
						+ COMPUTER_TABLE + "(" + COMPUTER_NAME + ","
						+ COMPUTER_INTRODUCED + "," + COMPUTER_DISCONTINUED
						+ "," + COMPUTER_COMPANYID + ") VALUES (?,?,?,?);");
			}
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, introduced);
			statement.setTimestamp(3, discontinued);
			if (companyId != null) {
				statement.setLong(4, companyId);
			}
			statement.executeUpdate();

			return true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			closeResultSetAndStatement(statement, null);
		}
	}

	@Override
	public boolean updateIntroductionDate(long id, Date introduced) {
		PreparedStatement statement = null;
		Timestamp time = new Timestamp(introduced.getTime());

		try {
			statement = conn.prepareStatement("UPDATE " + COMPUTER_TABLE
					+ " SET " + COMPUTER_INTRODUCED + "=? WHERE " + COMPUTER_ID
					+ "=?;");
			statement.setTimestamp(1, time);
			statement.setLong(2, id);
			statement.executeUpdate();
			return true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateDiscontinuationDate(long id, Date discontinued) {
		PreparedStatement statement = null;
		Timestamp time = new Timestamp(discontinued.getTime());

		try {
			statement = conn.prepareStatement("UPDATE " + COMPUTER_TABLE
					+ " SET " + COMPUTER_DISCONTINUED + "=? WHERE "
					+ COMPUTER_ID + "=?;");
			statement.setTimestamp(1, time);
			statement.setLong(2, id);
			statement.executeUpdate();
			return true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateCompany(long id, Company company) {
		if (!companyExists(company)) {
			return false;
		}

		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("UPDATE " + COMPUTER_TABLE + " SET "
					+ COMPUTER_COMPANYID + "=" + company.getId() + " WHERE "
					+ COMPUTER_ID + "=" + id + ";");

			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			closeResultSetAndStatement(statement, null);
		}
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

	private boolean companyIsNotNull(long id) {
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = conn.createStatement();
			result = statement.executeQuery("SELECT * FROM " + COMPUTER_TABLE
					+ " WHERE " + COMPUTER_ID + "=" + id + " AND "
					+ COMPUTER_COMPANYID + " IS NOT NULL;");
			if (result.next()) {
				return true;
			}

			return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private boolean companyExists(Company company) {
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = conn.createStatement();
			result = statement.executeQuery("SELECT * FROM " + COMPANY_TABLE
					+ " WHERE " + COMPANY_NAME + " LIKE '" + company.getName()
					+ "';");

			if (result.next()) {
				long id = result.getLong(COMPANY_ID);
				company.setId(id);
				return true;
			}

			return false;

		} catch (SQLException e) {
			System.out.println("Company existence test failed");
			return false;
		}
	}
}
