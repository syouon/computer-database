package dao;

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
import java.sql.Timestamp;
import java.util.List;

import mapper.Mapper;
import model.Company;
import model.Computer;
import util.Utils;

public class ConcreteComputerDAO implements ComputerDAO {

	@Override
	public boolean create(Computer computer) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;

		Date introductionDate = computer.getIntroductionDate();
		Date discontinuationDate = computer.getDiscontinuationDate();

		Timestamp introduced = (introductionDate != null) ? (new Timestamp(
				introductionDate.getTime())) : null;
		Timestamp discontinued = (discontinuationDate != null) ? (new Timestamp(
				discontinuationDate.getTime())) : null;

		// Verification de l'existence de company
		Company company = computer.getCompany();
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
			throw new DAOException();
		} finally {
			Utils.closeResultSetAndStatement(statement, null);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	@Override
	public boolean delete(Computer computer) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;

		try {
			statement = conn.prepareStatement("DELETE FROM " + COMPUTER_TABLE
					+ " WHERE " + COMPUTER_ID + "=?;");
			statement.setLong(1, computer.getId());
			statement.executeUpdate();
			return true;

		} catch (SQLException e) {
			throw new DAOException();

		} finally {
			Utils.closeResultSetAndStatement(statement, null);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	@Override
	public boolean update(Computer computer) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement dateStatement = null;
		PreparedStatement companyStatement = null;

		try {
			// Mise a jour des dates
			dateStatement = conn
					.prepareStatement("UPDATE " + COMPUTER_TABLE + " SET "
							+ COMPUTER_INTRODUCED + "=?, "
							+ COMPUTER_DISCONTINUED + "=? WHERE " + COMPUTER_ID
							+ "=?;");
			dateStatement.setTimestamp(1, Mapper
					.localDateTimeToTimestamp(computer.getIntroductionDate()));
			dateStatement.setTimestamp(2,
					Mapper.localDateTimeToTimestamp(computer
							.getDiscontinuationDate()));
			dateStatement.executeUpdate();

			// Mise a jour de la company seulement si elle existe
			if (computer != null && companyExists(computer.getCompany())) {
				companyStatement = conn.prepareStatement("UPDATE "
						+ COMPUTER_TABLE + " SET " + COMPUTER_COMPANYID
						+ "=? WHERE " + COMPUTER_ID + "=?;");
				companyStatement.setLong(1, computer.getCompany().getId());
				companyStatement.setLong(2, computer.getId());
				companyStatement.executeUpdate();
			}

			return true;

		} catch (SQLException e) {
			throw new DAOException();
		} finally {
			Utils.closeResultSetAndStatement(companyStatement, null);
			Utils.closeResultSetAndStatement(dateStatement, null);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	@Override
	public Computer find(long id) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT c." + COMPUTER_NAME
					+ " as c_name, c." + COMPUTER_ID + " as c_id, "
					+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
					+ COMPUTER_COMPANYID + ", co." + COMPANY_NAME + " FROM "
					+ COMPUTER_TABLE + " as c LEFT OUTER JOIN " + COMPANY_TABLE
					+ " as co ON c." + COMPUTER_COMPANYID + "=co." + COMPANY_ID
					+ " WHERE c." + COMPUTER_ID + "=?;");
			statement.setLong(1, id);
			result = statement.executeQuery();
			return Mapper.toComputer(result);

		} catch (SQLException e) {
			throw new DAOException();
		} finally {
			Utils.closeResultSetAndStatement(statement, result);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	@Override
	public List<Computer> findAll() {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT " + COMPUTER_NAME + ","
					+ COMPUTER_ID + " FROM " + COMPUTER_TABLE + " ORDER BY "
					+ COMPUTER_NAME + ";");
			result = statement.executeQuery();
			return Mapper.toComputerList(result);

		} catch (SQLException e) {
			throw new DAOException();
		} finally {
			Utils.closeResultSetAndStatement(statement, result);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	private boolean companyExists(Company company) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try { // On suppose que le champs id n'est pas renseigne
			statement = conn.prepareStatement("SELECT * FROM " + COMPANY_TABLE
					+ " WHERE " + COMPANY_NAME + " LIKE '?';");
			statement.setString(1, company.getName());
			result = statement.executeQuery();

			if (result.next()) {
				long id = result.getLong(COMPANY_ID);
				company.setId(id);
				return true;
			}

			return false;

		} catch (SQLException e) {
			throw new DAOException();
		} finally {
			Utils.closeResultSetAndStatement(statement, result);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}
}
