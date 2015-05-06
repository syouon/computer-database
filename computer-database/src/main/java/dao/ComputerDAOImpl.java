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

import javax.sql.DataSource;

import model.Company;
import model.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("computerDAO")
public class ComputerDAOImpl implements ComputerDAO {
	private Logger logger;

	private ComputerMapper mapper;
	private JdbcTemplate jdbc;

	@Autowired
	private CompanyDAO companyDAO;

	private ComputerDAOImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
		mapper = new ComputerMapper();
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbc = new JdbcTemplate(dataSource);
	}

	@Override
	public long create(Computer computer) {
		LocalDate introductionDate = computer.getIntroductionDate();
		LocalDate discontinuationDate = computer.getDiscontinuationDate();

		Timestamp introduced = (introductionDate != null) ? (DateMapper
				.localDateToTimestamp(introductionDate)) : null;
		Timestamp discontinued = (discontinuationDate != null) ? (DateMapper
				.localDateToTimestamp(discontinuationDate)) : null;

		// Verification de l'existence de company
		Company company = computer.getCompany();
		Long companyId = null;
		if (company != null && companyDAO.exists(company)) {
			companyId = company.getId();
		}

		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (companyId == null) {
			jdbc.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							"INSERT INTO " + COMPUTER_TABLE + "("
									+ COMPUTER_NAME + "," + COMPUTER_INTRODUCED
									+ "," + COMPUTER_DISCONTINUED
									+ ") VALUES (?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, computer.getName());
					ps.setTimestamp(2, introduced);
					ps.setTimestamp(3, discontinued);
					return ps;
				}
			}, keyHolder);
		} else {
			final Long copyId = companyId;
			jdbc.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							"INSERT INTO " + COMPUTER_TABLE + "("
									+ COMPUTER_NAME + "," + COMPUTER_INTRODUCED
									+ "," + COMPUTER_DISCONTINUED + ","
									+ COMPUTER_COMPANYID
									+ ") VALUES (?,?,?,?);",
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, computer.getName());
					ps.setTimestamp(2, introduced);
					ps.setTimestamp(3, discontinued);
					ps.setLong(4, copyId);
					return ps;
				}
			}, keyHolder);
		}

		return (long) keyHolder.getKey();
	}

	@Override
	public boolean delete(long id) {
		jdbc.update("DELETE FROM " + COMPUTER_TABLE + " WHERE " + COMPUTER_ID
				+ "=?", id);
		logger.debug("Computer n°{} deleted", id);
		return true;
	}

	@Override
	public boolean deleteByCompany(long companyId) throws DataAccessException {
		jdbc.update("DELETE FROM " + COMPUTER_TABLE + " WHERE "
				+ COMPUTER_COMPANYID + "=?", companyId);
		logger.debug("Computer n°{} deleted", companyId);
		return true;
	}

	@Override
	public boolean update(Computer computer) {
		return updateName(computer) && updateDate(computer)
				&& updateCompany(computer);
	}

	private boolean updateName(Computer computer) {
		jdbc.update("UPDATE " + COMPUTER_TABLE + " SET " + COMPUTER_NAME
				+ "=? WHERE " + COMPUTER_ID + "=?", computer.getName(),
				computer.getId());
		logger.debug("Computer n°{}'s name is now {}", computer.getId(),
				computer.getName());
		return true;
	}

	private boolean updateDate(Computer computer) {
		Timestamp introduced = DateMapper.localDateToTimestamp(computer
				.getIntroductionDate());
		Timestamp discontinued = DateMapper.localDateToTimestamp(computer
				.getDiscontinuationDate());

		jdbc.update("UPDATE " + COMPUTER_TABLE + " SET " + COMPUTER_INTRODUCED
				+ "=?, " + COMPUTER_DISCONTINUED + "=? WHERE " + COMPUTER_ID
				+ "=?", introduced, discontinued, computer.getId());
		logger.debug("Computer {}'s dates updated", computer.getName());
		return true;
	}

	private boolean updateCompany(Computer computer) {
		if (computer.getCompany() != null) {
			if (companyDAO.exists(computer.getCompany())) {
				jdbc.update(
						"UPDATE " + COMPUTER_TABLE + " SET "
								+ COMPUTER_COMPANYID + "=? WHERE "
								+ COMPUTER_ID + "=?", computer.getCompany()
								.getId(), computer.getId());
				return true;
			}

			return false;
		} else {
			jdbc.update(
					"UPDATE " + COMPUTER_TABLE + " SET " + COMPUTER_COMPANYID
							+ "=NULL WHERE " + COMPUTER_ID + "=?;",
					computer.getId());
			return true;
		}
	}

	@Override
	public Computer find(long id) {
		Object[] params = { id };
		List<Computer> computers = jdbc.query("SELECT c." + COMPUTER_NAME
				+ " as c_name, c." + COMPUTER_ID + " as c_id, "
				+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
				+ COMPUTER_COMPANYID + ", co." + COMPANY_NAME + " FROM "
				+ COMPUTER_TABLE + " c LEFT OUTER JOIN " + COMPANY_TABLE
				+ " co ON c." + COMPUTER_COMPANYID + "=co." + COMPANY_ID
				+ " WHERE c." + COMPUTER_ID + "=?", params, mapper);

		if (computers.isEmpty()) {
			logger.debug("Computer n°{} doesn't exist", id);
			return null;
		} else {
			logger.debug("Computer n°{} exist", id);
			return computers.get(0);
		}
	}

	@Override
	public List<Computer> findAll() {
		List<Computer> computers = jdbc.query("SELECT " + COMPUTER_ID
				+ " as c_id, " + COMPUTER_NAME + " as c_name, "
				+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
				+ COMPUTER_COMPANYID + " FROM " + COMPUTER_TABLE,
				new RowMapper<Computer>() {

					@Override
					public Computer mapRow(ResultSet result, int rowNum)
							throws SQLException {
						long id = result.getLong("c_id");
						String name = result.getString("c_name");
						LocalDate introduced = DateMapper
								.timestampToLocalDate(result
										.getTimestamp(COMPUTER_INTRODUCED));
						LocalDate discontinued = DateMapper
								.timestampToLocalDate(result
										.getTimestamp(COMPUTER_DISCONTINUED));
						long company_id = result.getLong(COMPUTER_COMPANYID);
						Company company = companyDAO.find(company_id);
						Computer computer = new Computer.Builder(name)
								.setId(id).build();
						computer.setIntroductionDate(introduced);
						computer.setDiscontinuationDate(discontinued);
						computer.setCompany(company);
						return computer;
					}

				});
		return computers;
	}

	@Override
	public List<Computer> findAll(int start, int range) {
		Object[] params = { range, start };
		List<Computer> computers = jdbc.query("SELECT " + COMPUTER_ID
				+ " as c_id, " + COMPUTER_NAME + " as c_name, "
				+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
				+ COMPUTER_COMPANYID + " FROM " + COMPUTER_TABLE
				+ " LIMIT ? OFFSET ?;", params, new RowMapper<Computer>() {

			@Override
			public Computer mapRow(ResultSet result, int rowNum)
					throws SQLException {
				long id = result.getLong("c_id");
				String name = result.getString("c_name");
				LocalDate introduced = DateMapper.timestampToLocalDate(result
						.getTimestamp(COMPUTER_INTRODUCED));
				LocalDate discontinued = DateMapper.timestampToLocalDate(result
						.getTimestamp(COMPUTER_DISCONTINUED));
				long company_id = result.getLong(COMPUTER_COMPANYID);
				Company company = companyDAO.find(company_id);
				Computer computer = new Computer.Builder(name).setId(id)
						.build();
				computer.setIntroductionDate(introduced);
				computer.setDiscontinuationDate(discontinued);
				computer.setCompany(company);
				return computer;
			}

		});
		return computers;
	}

	@Override
	public List<Computer> findAll(int start, int range, String orderBy,
			boolean desc) {
		List<Computer> computers = null;
		Object[] params = { range, start };
		if (desc) {
			computers = jdbc.query("SELECT c." + COMPUTER_NAME
					+ " as c_name, c." + COMPUTER_ID + " as c_id, "
					+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
					+ COMPUTER_COMPANYID + ", co." + COMPANY_NAME + " FROM "
					+ COMPUTER_TABLE + " c LEFT OUTER JOIN " + COMPANY_TABLE
					+ " co ON c." + COMPUTER_COMPANYID + "=co." + COMPANY_ID
					+ " ORDER BY " + orderBy + " DESC LIMIT ? OFFSET ?",
					params, mapper);
		} else {
			computers = jdbc.query("SELECT c." + COMPUTER_NAME
					+ " as c_name, c." + COMPUTER_ID + " as c_id, "
					+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
					+ COMPUTER_COMPANYID + ", co." + COMPANY_NAME + " FROM "
					+ COMPUTER_TABLE + " c LEFT OUTER JOIN " + COMPANY_TABLE
					+ " co ON c." + COMPUTER_COMPANYID + "=co." + COMPANY_ID
					+ " ORDER BY " + orderBy + " LIMIT ? OFFSET ?;", params,
					mapper);
		}

		return computers;
	}

	@Override
	public List<Computer> findAll(String regex, int start, int range) {
		Object[] params = { "%" + regex + "%", "%" + regex + "%", range, start };
		List<Computer> computers = jdbc.query("SELECT c." + COMPUTER_NAME
				+ " as c_name, c." + COMPUTER_ID + " as c_id, "
				+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
				+ COMPUTER_COMPANYID + ", co." + COMPANY_NAME + " FROM "
				+ COMPUTER_TABLE + " c LEFT OUTER JOIN " + COMPANY_TABLE
				+ " co ON c." + COMPUTER_COMPANYID + "=co." + COMPANY_ID
				+ " WHERE (c." + COMPUTER_NAME + " LIKE ? OR co."
				+ COMPANY_NAME + " LIKE ?) LIMIT ? OFFSET ?;", params, mapper);

		return computers;
	}

	@Override
	public List<Computer> findAll(String regex, int start, int range,
			String orderBy, boolean desc) {
		List<Computer> computers = null;
		Object[] params = { "%" + regex + "%", "%" + regex + "%", range, start };
		if (desc) {
			computers = jdbc.query("SELECT c." + COMPUTER_NAME
					+ " as c_name, c." + COMPUTER_ID + " as c_id, "
					+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
					+ COMPUTER_COMPANYID + ", co." + COMPANY_NAME + " FROM "
					+ COMPUTER_TABLE + " c LEFT OUTER JOIN " + COMPANY_TABLE
					+ " co ON c." + COMPUTER_COMPANYID + "=co." + COMPANY_ID
					+ " WHERE (c." + COMPUTER_NAME + " LIKE ? OR co."
					+ COMPANY_NAME + " LIKE ?) ORDER BY " + orderBy
					+ " DESC LIMIT ? OFFSET ?;", params, mapper);
		} else {
			computers = jdbc.query("SELECT c." + COMPUTER_NAME
					+ " as c_name, c." + COMPUTER_ID + " as c_id, "
					+ COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", "
					+ COMPUTER_COMPANYID + ", co." + COMPANY_NAME + " FROM "
					+ COMPUTER_TABLE + " c LEFT OUTER JOIN " + COMPANY_TABLE
					+ " co ON c." + COMPUTER_COMPANYID + "=co." + COMPANY_ID
					+ " WHERE (c." + COMPUTER_NAME + " LIKE ? OR co."
					+ COMPANY_NAME + " LIKE ?) ORDER BY " + orderBy
					+ " LIMIT ? OFFSET ?;", params, mapper);
		}

		return computers;
	}

	@Override
	public int count() {
		return jdbc.queryForObject("SELECT COUNT(*) FROM " + COMPUTER_TABLE,
				Integer.class);
	}

	@Override
	public int countSearchResult(String regex) {
		Object[] params = { "%" + regex + "%", "%" + regex + "%" };
		return jdbc.queryForObject("SELECT COUNT(*) FROM " + COMPUTER_TABLE
				+ " c LEFT OUTER JOIN " + COMPANY_TABLE + " co ON "
				+ COMPUTER_COMPANYID + "=co." + COMPANY_ID + " WHERE (c."
				+ COMPUTER_NAME + " LIKE ? OR co." + COMPANY_NAME + " LIKE ?)",
				Integer.class, params);
	}
}
