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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("computerDAO")
public class ComputerDAOImpl implements ComputerDAO {
	private Logger logger;

	private ComputerMapper mapper;

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private NamedParameterJdbcTemplate namedJdbc;

	@Autowired
	private CompanyDAO companyDAO;

	private ComputerDAOImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
		mapper = new ComputerMapper();
	}

	@Override
	public void create(Computer computer) {
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

		StringBuilder request = new StringBuilder("INSERT INTO ")
				.append(COMPUTER_TABLE).append("(").append(COMPUTER_NAME)
				.append(",").append(COMPUTER_INTRODUCED).append(",")
				.append(COMPUTER_DISCONTINUED);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (companyId == null) {
			jdbc.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(request
							.append(") VALUES (?,?,?)").toString(),
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
							request.append(",").append(COMPUTER_COMPANYID)
									.append(") VALUES (?,?,?,?)").toString(),
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, computer.getName());
					ps.setTimestamp(2, introduced);
					ps.setTimestamp(3, discontinued);
					ps.setLong(4, copyId);
					return ps;
				}
			}, keyHolder);
		}

		computer.setId((long) keyHolder.getKey());
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
		StringBuilder request = new StringBuilder("UPDATE ")
				.append(COMPUTER_TABLE).append(" SET ").append(COMPUTER_NAME)
				.append("=? WHERE ").append(COMPUTER_ID).append("=?");
		jdbc.update(request.toString(), computer.getName(), computer.getId());
		logger.debug("Computer n°{}'s name is now {}", computer.getId(),
				computer.getName());
		return true;
	}

	private boolean updateDate(Computer computer) {
		Timestamp introduced = DateMapper.localDateToTimestamp(computer
				.getIntroductionDate());
		Timestamp discontinued = DateMapper.localDateToTimestamp(computer
				.getDiscontinuationDate());

		StringBuilder request = new StringBuilder("UPDATE ")
				.append(COMPUTER_TABLE).append(" SET ")
				.append(COMPUTER_INTRODUCED).append("=?, ")
				.append(COMPUTER_DISCONTINUED).append("=? WHERE ")
				.append(COMPUTER_ID).append("=?");

		jdbc.update(request.toString(), introduced, discontinued,
				computer.getId());
		logger.debug("Computer {}'s dates updated", computer.getName());
		return true;
	}

	private boolean updateCompany(Computer computer) {
		StringBuilder request = new StringBuilder("UPDATE ")
				.append(COMPUTER_TABLE).append(" SET ")
				.append(COMPUTER_COMPANYID);
		StringBuilder requestEnd = new StringBuilder(" WHERE ").append(
				COMPUTER_ID).append("=?");

		if (computer.getCompany() != null) {
			if (companyDAO.exists(computer.getCompany())) {
				jdbc.update(request.append("=?").append(requestEnd).toString(),
						computer.getCompany().getId(), computer.getId());
				return true;
			}

			return false;
		} else {
			jdbc.update(request.append("=NULL").append(requestEnd).toString(),
					computer.getId());
			return true;
		}
	}

	@Override
	public Computer find(long id) {
		StringBuilder request = new StringBuilder("SELECT c.")
				.append(COMPUTER_NAME).append(" as c_name, c.")
				.append(COMPUTER_ID).append(" as c_id, ")
				.append(COMPUTER_INTRODUCED).append(", ")
				.append(COMPUTER_DISCONTINUED).append(", ")
				.append(COMPUTER_COMPANYID).append(", co.")
				.append(COMPANY_NAME).append(" FROM ").append(COMPUTER_TABLE)
				.append(" c LEFT OUTER JOIN ").append(COMPANY_TABLE)
				.append(" co ON c.").append(COMPUTER_COMPANYID).append("=co.")
				.append(COMPANY_ID).append(" WHERE c.").append(COMPUTER_ID)
				.append("=?");

		Object[] params = { id };
		List<Computer> computers = jdbc.query(request.toString(), params,
				mapper);

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
		StringBuilder request = new StringBuilder("SELECT ")
				.append(COMPUTER_ID).append(" as c_id, ").append(COMPUTER_NAME)
				.append(" as c_name, ").append(COMPUTER_INTRODUCED)
				.append(", ").append(COMPUTER_DISCONTINUED).append(", ")
				.append(COMPUTER_COMPANYID).append(" FROM ")
				.append(COMPUTER_TABLE);

		List<Computer> computers = jdbc.query(request.toString(),
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
		StringBuilder request = new StringBuilder("SELECT ")
				.append(COMPUTER_ID).append(" as c_id, ").append(COMPUTER_NAME)
				.append(" as c_name, ").append(COMPUTER_INTRODUCED)
				.append(", ").append(COMPUTER_DISCONTINUED).append(", ")
				.append(COMPUTER_COMPANYID).append(" FROM ")
				.append(COMPUTER_TABLE).append(" LIMIT ? OFFSET ?");

		Object[] params = { range, start };
		List<Computer> computers = jdbc.query(request.toString(), params,
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
	public List<Computer> findAll(int start, int range, String orderBy,
			boolean desc) {
		StringBuilder request = new StringBuilder("SELECT c.")
				.append(COMPUTER_NAME).append(" as c_name, c.")
				.append(COMPUTER_ID).append(" as c_id, ")
				.append(COMPUTER_INTRODUCED).append(", ")
				.append(COMPUTER_DISCONTINUED).append(", ")
				.append(COMPUTER_COMPANYID).append(", co.")
				.append(COMPANY_NAME).append(" FROM ").append(COMPUTER_TABLE)
				.append(" c LEFT OUTER JOIN ").append(COMPANY_TABLE)
				.append(" co ON c.").append(COMPUTER_COMPANYID).append("=co.")
				.append(COMPANY_ID);

		List<Computer> computers = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("range", range);
		params.addValue("start", start);

		if (desc) {
			computers = namedJdbc.query(
					request.append(" ORDER BY ").append(orderBy)
							.append(" DESC LIMIT :range OFFSET :start")
							.toString(), params, mapper);
		} else {
			computers = namedJdbc.query(
					request.append(" ORDER BY ").append(orderBy)
							.append(" LIMIT :range OFFSET :start").toString(),
					params, mapper);
		}

		return computers;
	}

	@Override
	public List<Computer> findAll(String regex, int start, int range) {
		StringBuilder request = new StringBuilder("SELECT c.")
				.append(COMPUTER_NAME).append(" as c_name, c.")
				.append(COMPUTER_ID).append(" as c_id, ")
				.append(COMPUTER_INTRODUCED).append(", ")
				.append(COMPUTER_DISCONTINUED).append(", ")
				.append(COMPUTER_COMPANYID).append(", co.")
				.append(COMPANY_NAME).append(" FROM ").append(COMPUTER_TABLE)
				.append(" c LEFT OUTER JOIN ").append(COMPANY_TABLE)
				.append(" co ON c.").append(COMPUTER_COMPANYID).append("=co.")
				.append(COMPANY_ID).append(" WHERE (c.").append(COMPUTER_NAME)
				.append(" LIKE :regex OR co.").append(COMPANY_NAME)
				.append(" LIKE :regex) LIMIT :range OFFSET :start");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("regex", "%" + regex + "%");
		params.addValue("range", range);
		params.addValue("start", start);
		List<Computer> computers = namedJdbc.query(request.toString(), params,
				mapper);

		return computers;
	}

	@Override
	public List<Computer> findAll(String regex, int start, int range,
			String orderBy, boolean desc) {
		StringBuilder request = new StringBuilder("SELECT c.")
				.append(COMPUTER_NAME).append(" as c_name, c.")
				.append(COMPUTER_ID).append(" as c_id, ")
				.append(COMPUTER_INTRODUCED).append(", ")
				.append(COMPUTER_DISCONTINUED).append(", ")
				.append(COMPUTER_COMPANYID).append(", co.")
				.append(COMPANY_NAME).append(" FROM ").append(COMPUTER_TABLE)
				.append(" c LEFT OUTER JOIN ").append(COMPANY_TABLE)
				.append(" co ON c.").append(COMPUTER_COMPANYID).append("=co.")
				.append(COMPANY_ID).append(" WHERE (c.").append(COMPUTER_NAME)
				.append(" LIKE :regex OR co.").append(COMPANY_NAME);

		List<Computer> computers = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("regex", "%" + regex + "%");
		params.addValue("range", range);
		params.addValue("start", start);
		if (desc) {
			computers = namedJdbc.query(
					request.append(" LIKE :regex) ORDER BY ").append(orderBy)
							.append(" DESC LIMIT :range OFFSET :start")
							.toString(), params, mapper);
		} else {
			computers = namedJdbc.query(
					request.append(" LIKE :regex) ORDER BY ").append(orderBy)
							.append(" LIMIT :range OFFSET :start").toString(),
					params, mapper);
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
		StringBuilder request = new StringBuilder("SELECT COUNT(*) FROM ")
				.append(COMPUTER_TABLE).append(" c LEFT OUTER JOIN ")
				.append(COMPANY_TABLE).append(" co ON ")
				.append(COMPUTER_COMPANYID).append("=co.").append(COMPANY_ID)
				.append(" WHERE (c.").append(COMPUTER_NAME)
				.append(" LIKE :regex OR co.").append(COMPANY_NAME)
				.append(" LIKE :regex)");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("regex", "%" + regex + "%");
		return namedJdbc.queryForObject(request.toString(), params,
				Integer.class);
	}
}
