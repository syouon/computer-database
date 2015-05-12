package dao;

import static dao.DatabaseNaming.COMPANY_ID;
import static dao.DatabaseNaming.COMPANY_NAME;
import static dao.DatabaseNaming.COMPANY_TABLE;

import java.util.List;

import model.Company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("companyDAO")
public class CompanyDAOImpl implements CompanyDAO {
	private Logger logger;

	private CompanyMapper mapper;
	@Autowired
	private JdbcTemplate jdbc;

	private CompanyDAOImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
		mapper = new CompanyMapper();
	}

	@Override
	public Company find(long id) {
		Object[] params = { id };
		List<Company> companies = jdbc.query("SELECT * FROM " + COMPANY_TABLE
				+ " WHERE " + COMPANY_ID + "=?", params, mapper);
		if (companies.isEmpty()) {
			logger.debug("Company n°{} doesn't exist", id);
			return null;
		} else {
			logger.debug("Company n°{} exist", id);
			return companies.get(0);
		}
	}

	@Override
	public List<Company> findAll(int start, int range) {
		StringBuilder request = new StringBuilder("SELECT ")
				.append(COMPANY_NAME).append(",").append(COMPANY_ID)
				.append(" FROM ").append(COMPANY_TABLE).append(" ORDER BY ")
				.append(COMPANY_NAME).append(" LIMIT ? OFFSET ?");

		Object[] params = { range, start };
		List<Company> companies = jdbc
				.query(request.toString(), params, mapper);
		return companies;
	}

	@Override
	public List<Company> findAll() {
		List<Company> companies = jdbc.query("SELECT " + COMPANY_NAME + ","
				+ COMPANY_ID + " FROM " + COMPANY_TABLE, mapper);
		return companies;
	}

	/* Modifie l'objet company en ajoutant son id */
	@Override
	public boolean exists(Company company) {
		Object[] params = { company.getName() };
		List<Company> companies = jdbc.query("SELECT * FROM " + COMPANY_TABLE
				+ " WHERE " + COMPANY_NAME + " LIKE ?", params, mapper);

		if (companies.isEmpty()) {
			logger.debug("{} doesn't exist", company.getName());
			return false;
		} else {
			logger.debug("{} exist", company.getName());
			company.setId(companies.get(0).getId());
			return true;
		}
	}

	@Override
	public boolean delete(long id) throws DataAccessException {
		Object[] params = { id };
		jdbc.update("DELETE FROM " + COMPANY_TABLE + " WHERE " + COMPANY_ID
				+ "=?", params);
		logger.debug("Company n°{} deleted", id);
		return true;
	}
}
