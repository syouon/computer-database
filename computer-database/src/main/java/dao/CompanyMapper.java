package dao;

import static dao.DatabaseNaming.COMPANY_ID;
import static dao.DatabaseNaming.COMPANY_NAME;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;

import org.springframework.jdbc.core.RowMapper;

public class CompanyMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet result, int arg1) throws SQLException {
		long id = result.getLong(COMPANY_ID);
		String name = result.getString(COMPANY_NAME);
		return new Company(id, name);
	}

}
