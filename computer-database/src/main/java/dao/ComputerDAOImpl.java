package dao;

import static dao.DatabaseNaming.COMPANY_ID;
import static dao.DatabaseNaming.COMPANY_NAME;
import static dao.DatabaseNaming.COMPANY_TABLE;
import static dao.DatabaseNaming.COMPUTER_COMPANYID;
import static dao.DatabaseNaming.COMPUTER_DISCONTINUED;
import static dao.DatabaseNaming.COMPUTER_ID;
import static dao.DatabaseNaming.COMPUTER_INTRODUCED;
import static dao.DatabaseNaming.COMPUTER_NAME;
import static dao.DatabaseNaming.COMPUTER_TABLE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import model.Company;
import model.Computer;
import exception.DAOException;

public enum ComputerDAOImpl implements ComputerDAO {
	INSTANCE;

	public static ComputerDAO getInstance() {
		return INSTANCE;
	}

	@Override
	public long create(Computer computer) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		LocalDate introductionDate = computer.getIntroductionDate();
		LocalDate discontinuationDate = computer.getDiscontinuationDate();

		Timestamp introduced = (introductionDate != null) ? (DateMapper
				.localDateToTimestamp(introductionDate)) : null;
		Timestamp discontinued = (discontinuationDate != null) ? (DateMapper
				.localDateToTimestamp(discontinuationDate)) : null;

		// Verification de l'existence de company
		Company company = computer.getCompany();
		Long companyId = null;
		if (company != null && CompanyDAOImpl.getInstance().exists(company)) {
			companyId = company.getId();
		}

		try {
			if (companyId == null) { // si company n'existe pas
				statement = conn.prepareStatement("INSERT INTO "
						+ COMPUTER_TABLE + "(" + COMPUTER_NAME + ","
						+ COMPUTER_INTRODUCED + "," + COMPUTER_DISCONTINUED
						+ ") VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
			} else {
				statement = conn.prepareStatement("INSERT INTO "
						+ COMPUTER_TABLE + "(" + COMPUTER_NAME + ","
						+ COMPUTER_INTRODUCED + "," + COMPUTER_DISCONTINUED
						+ "," + COMPUTER_COMPANYID + ") VALUES (?,?,?,?);",
						Statement.RETURN_GENERATED_KEYS);
			}
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, introduced);
			statement.setTimestamp(3, discontinued);
			if (companyId != null) {
				statement.setLong(4, companyId);
			}
			statement.executeUpdate();
			result = statement.getGeneratedKeys();

			long lastId = -1;
			if (result.next()) {
				lastId = result.getLong(1);
			}

			return lastId;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(null,
					result);
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, null);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	@Override
	public boolean delete(long id) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;

		try {
			statement = conn.prepareStatement("DELETE FROM " + COMPUTER_TABLE
					+ " WHERE " + COMPUTER_ID + "=?;");
			statement.setLong(1, id);
			statement.executeUpdate();
			return true;

		} catch (SQLException e) {
			throw new DAOException();

		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, null);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	@Override
	public boolean update(Computer computer) {
		return updateName(computer) && updateDate(computer)
				&& updateCompany(computer);
	}

	private boolean updateName(Computer computer) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement nameStatement = null;

		try {
			nameStatement = conn.prepareStatement("UPDATE " + COMPUTER_TABLE
					+ " SET " + COMPUTER_NAME + "=? WHERE " + COMPUTER_ID
					+ "=?;");
			nameStatement.setString(1, computer.getName());
			nameStatement.setLong(2, computer.getId());
			nameStatement.executeUpdate();

			return true;
		} catch (SQLException e) {
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					nameStatement, null);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	private boolean updateDate(Computer computer) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement dateStatement = null;

		try {
			// Mise a jour des dates
			dateStatement = conn
					.prepareStatement("UPDATE " + COMPUTER_TABLE + " SET "
							+ COMPUTER_INTRODUCED + "=?, "
							+ COMPUTER_DISCONTINUED + "=? WHERE " + COMPUTER_ID
							+ "=?;");
			dateStatement.setTimestamp(1, DateMapper
					.localDateToTimestamp(computer.getIntroductionDate()));
			dateStatement.setTimestamp(2, DateMapper
					.localDateToTimestamp(computer.getDiscontinuationDate()));
			dateStatement.setLong(3, computer.getId());
			dateStatement.executeUpdate();

			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					dateStatement, null);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	private boolean updateCompany(Computer computer) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement companyStatement = null;

		try {
			if (computer.getCompany() != null) {
				if (CompanyDAOImpl.getInstance().exists(computer.getCompany())) {
					companyStatement = conn.prepareStatement("UPDATE "
							+ COMPUTER_TABLE + " SET " + COMPUTER_COMPANYID
							+ "=? WHERE " + COMPUTER_ID + "=?;");
					companyStatement.setLong(1, computer.getCompany().getId());
					companyStatement.setLong(2, computer.getId());
					companyStatement.executeUpdate();
					return true;
				}

				return false;
			}

			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					companyStatement, null);
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
					+ COMPUTER_TABLE + " c LEFT OUTER JOIN " + COMPANY_TABLE
					+ " co ON c." + COMPUTER_COMPANYID + "=co." + COMPANY_ID
					+ " WHERE c." + COMPUTER_ID + "=?;");
			statement.setLong(1, id);
			result = statement.executeQuery();
			return DatabaseMapper.toComputer(result);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	@Override
	public List<Computer> findAll(int start, int range) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT * FROM " + COMPUTER_TABLE
					+ " ORDER BY " + COMPUTER_NAME + " LIMIT ? OFFSET ?;");
			statement.setInt(1, range);
			statement.setInt(2, start);
			result = statement.executeQuery();
			return DatabaseMapper.toComputerList(result);

		} catch (SQLException e) {
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	@Override
	public List<Computer> findAll() {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT * FROM " + COMPUTER_TABLE
					+ ";");
			result = statement.executeQuery();
			return DatabaseMapper.toComputerList(result);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}

	@Override
	public int count() {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT COUNT(*) FROM "
					+ COMPUTER_TABLE);
			result = statement.executeQuery();
			result.next();
			return result.getInt(1);

		} catch (SQLException e) {
			throw new DAOException();
		}
	}
}
