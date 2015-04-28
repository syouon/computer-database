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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;
import model.Computer;
import exception.DAOException;

public enum ComputerDAOImpl implements ComputerDAO {
	INSTANCE;
	private Logger logger;

	private ComputerDAOImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public static ComputerDAO getInstance() {
		return INSTANCE;
	}

	@Override
	public long create(Computer computer) {
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

		Connection conn = ConnectionFactory.getInstance().openConnection();

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
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(null,
					result);
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, null);
			ConnectionFactory.getInstance().closeConnection();
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
			logger.error(e.getMessage());
			throw new DAOException();

		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, null);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Override
	public boolean deleteByCompany(long companyId) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;

		try {
			statement = conn.prepareStatement("DELETE FROM " + COMPUTER_TABLE
					+ " WHERE " + COMPUTER_COMPANYID + "=?;");
			statement.setLong(1, companyId);

			statement.executeUpdate();

			return true;
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, null);
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
			logger.debug("Name updated");

			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					nameStatement, null);
			ConnectionFactory.getInstance().closeConnection();
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
			logger.debug("Dates updated");

			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					dateStatement, null);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	private boolean updateCompany(Computer computer) {
		Connection conn = null;
		PreparedStatement companyStatement = null;

		try {
			if (computer.getCompany() != null) {
				if (CompanyDAOImpl.getInstance().exists(computer.getCompany())) {
					conn = ConnectionFactory.getInstance().openConnection();
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
			logger.debug("Company updated");

			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					companyStatement, null);
			ConnectionFactory.getInstance().closeConnection();
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
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Override
	public List<Computer> findAll() {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT " + COMPUTER_ID
					+ " as c_id, " + COMPUTER_NAME + " as c_name, "
					+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
					+ COMPUTER_COMPANYID + " FROM " + COMPUTER_TABLE + ";");
			result = statement.executeQuery();
			return DatabaseMapper.toComputerList(result);

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Override
	public List<Computer> findAll(int start, int range) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT " + COMPUTER_ID
					+ " as c_id, " + COMPUTER_NAME + " as c_name, "
					+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
					+ COMPUTER_COMPANYID + " FROM " + COMPUTER_TABLE
					+ " LIMIT ? OFFSET ?;");
			statement.setInt(1, range);
			statement.setInt(2, start);
			result = statement.executeQuery();
			return DatabaseMapper.toComputerList(result);

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Override
	public List<Computer> findAll(int start, int range, String orderBy,
			boolean desc) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			if (desc) {
				statement = conn.prepareStatement("SELECT c." + COMPUTER_NAME
						+ " as c_name, c." + COMPUTER_ID + " as c_id, "
						+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED
						+ ", " + COMPUTER_COMPANYID + ", co." + COMPANY_NAME
						+ " FROM " + COMPUTER_TABLE + " c LEFT OUTER JOIN "
						+ COMPANY_TABLE + " co ON c." + COMPUTER_COMPANYID
						+ "=co." + COMPANY_ID + " ORDER BY " + orderBy
						+ " DESC LIMIT ? OFFSET ?;");
			} else {
				statement = conn.prepareStatement("SELECT c." + COMPUTER_NAME
						+ " as c_name, c." + COMPUTER_ID + " as c_id, "
						+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED
						+ ", " + COMPUTER_COMPANYID + ", co." + COMPANY_NAME
						+ " FROM " + COMPUTER_TABLE + " c LEFT OUTER JOIN "
						+ COMPANY_TABLE + " co ON c." + COMPUTER_COMPANYID
						+ "=co." + COMPANY_ID + " ORDER BY " + orderBy
						+ " LIMIT ? OFFSET ?;");
			}
			statement.setInt(1, range);
			statement.setInt(2, start);
			result = statement.executeQuery();
			return DatabaseMapper.toComputerList(result);

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Override
	public List<Computer> findAll(String regex, int start, int range) {
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
					+ " WHERE (c." + COMPUTER_NAME + " LIKE ? OR co."
					+ COMPANY_NAME + " LIKE ?) LIMIT ? OFFSET ?;");
			statement.setString(1, "%" + regex + "%");
			statement.setString(2, "%" + regex + "%");
			statement.setInt(3, range);
			statement.setInt(4, start);
			result = statement.executeQuery();

			return DatabaseMapper.toComputerList(result);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Override
	public List<Computer> findAll(String regex, int start, int range,
			String orderBy, boolean desc) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			if (desc) {
				statement = conn.prepareStatement("SELECT c." + COMPUTER_NAME
						+ " as c_name, c." + COMPUTER_ID + " as c_id, "
						+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED
						+ ", " + COMPUTER_COMPANYID + ", co." + COMPANY_NAME
						+ " FROM " + COMPUTER_TABLE + " c LEFT OUTER JOIN "
						+ COMPANY_TABLE + " co ON c." + COMPUTER_COMPANYID
						+ "=co." + COMPANY_ID + " WHERE (c." + COMPUTER_NAME
						+ " LIKE ? OR co." + COMPANY_NAME
						+ " LIKE ?) ORDER BY " + orderBy
						+ " DESC LIMIT ? OFFSET ?;");
			} else {
				statement = conn
						.prepareStatement("SELECT c." + COMPUTER_NAME
								+ " as c_name, c." + COMPUTER_ID + " as c_id, "
								+ COMPUTER_INTRODUCED + ", "
								+ COMPUTER_DISCONTINUED + ", "
								+ COMPUTER_COMPANYID + ", co." + COMPANY_NAME
								+ " FROM " + COMPUTER_TABLE
								+ " c LEFT OUTER JOIN " + COMPANY_TABLE
								+ " co ON c." + COMPUTER_COMPANYID + "=co."
								+ COMPANY_ID + " WHERE (c." + COMPUTER_NAME
								+ " LIKE ? OR co." + COMPANY_NAME
								+ " LIKE ?) ORDER BY " + orderBy
								+ " LIMIT ? OFFSET ?;");
			}
			statement.setString(1, "%" + regex + "%");
			statement.setString(2, "%" + regex + "%");
			statement.setInt(3, range);
			statement.setInt(4, start);
			result = statement.executeQuery();

			return DatabaseMapper.toComputerList(result);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
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
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Override
	public int countSearchResult(String regex) {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT COUNT(*) FROM "
					+ COMPUTER_TABLE + " c LEFT OUTER JOIN " + COMPANY_TABLE
					+ " co ON " + COMPUTER_COMPANYID + "=co." + COMPANY_ID
					+ " WHERE (c." + COMPUTER_NAME + " LIKE ? OR co."
					+ COMPANY_NAME + " LIKE ?);");
			statement.setString(1, "%" + regex + "%");
			statement.setString(2, "%" + regex + "%");
			result = statement.executeQuery();
			result.next();
			return result.getInt(1);

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}
}
