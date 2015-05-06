package dao;

import static dao.DatabaseNaming.COMPANY_NAME;
import static dao.DatabaseNaming.COMPUTER_COMPANYID;
import static dao.DatabaseNaming.COMPUTER_DISCONTINUED;
import static dao.DatabaseNaming.COMPUTER_INTRODUCED;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import model.Company;
import model.Computer;

import org.springframework.jdbc.core.RowMapper;

public class ComputerMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet result, int rowNum) throws SQLException {
		Computer computer = null;
		long id = result.getLong("c_id");
		String name = result.getString("c_name");
		LocalDate introduced = DateMapper.timestampToLocalDate(result
				.getTimestamp(COMPUTER_INTRODUCED));
		LocalDate discontinued = DateMapper.timestampToLocalDate(result
				.getTimestamp(COMPUTER_DISCONTINUED));
		computer = new Computer.Builder(name).setId(id).build();

		String companyName = result.getString(COMPANY_NAME);
		if (companyName != null) {
			long companyId = result.getLong(COMPUTER_COMPANYID);
			Company company = new Company(companyId, companyName);
			computer.setCompany(company);
		}

		computer.setIntroductionDate(introduced);
		computer.setDiscontinuationDate(discontinued);

		return computer;
	}

}
