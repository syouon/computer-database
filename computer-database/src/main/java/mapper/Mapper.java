package mapper;

import static dao.DatabaseNaming.COMPANY_ID;
import static dao.DatabaseNaming.COMPANY_NAME;
import static dao.DatabaseNaming.COMPUTER_DISCONTINUED;
import static dao.DatabaseNaming.COMPUTER_ID;
import static dao.DatabaseNaming.COMPUTER_INTRODUCED;
import static dao.DatabaseNaming.COMPUTER_NAME;
import static dao.DatabaseNaming.COMPUTER_COMPANYID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.ConcreteCompanyDAO;
import model.Company;
import model.Computer;

/* Fait le lien entre le modele objet et
 * les resultats obtenus par une requete
 */
/**
 * The Class Mapper.
 */
public class Mapper {

	/**
	 * To computer list.
	 * 
	 * @param result
	 *            the result
	 * @return the list
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static List<Computer> toComputerList(ResultSet result)
			throws SQLException {
		List<Computer> computers = new ArrayList<>();

		while (result.next()) {
			long id = result.getLong(COMPUTER_ID);
			String name = result.getString(COMPUTER_NAME);
			LocalDateTime introduced = Mapper.timestampToLocalDateTime(result
					.getTimestamp(COMPUTER_INTRODUCED));
			LocalDateTime discontinued = Mapper.timestampToLocalDateTime(result
					.getTimestamp(COMPUTER_DISCONTINUED));
			long company_id = result.getLong(COMPUTER_COMPANYID);
			Company company = ConcreteCompanyDAO.getInstance().find(company_id);
			Computer computer = new Computer(id, name);
			computer.setIntroductionDate(introduced);
			computer.setDiscontinuationDate(discontinued);
			computer.setCompany(company);
			computers.add(computer);
		}

		return computers;
	}

	/**
	 * To company list.
	 * 
	 * @param result
	 *            the result
	 * @return the list
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static List<Company> toCompanyList(ResultSet result)
			throws SQLException {
		List<Company> companies = new ArrayList<>();

		while (result.next()) {
			long id = result.getLong(COMPANY_ID);
			String name = result.getString(COMPANY_NAME);
			Company company = new Company(id, name);
			companies.add(company);
		}

		return companies;
	}

	/**
	 * To computer.
	 * 
	 * @param result
	 *            the result
	 * @return the computer
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static Computer toComputer(ResultSet result) throws SQLException {
		Computer computer = null;

		while (result.next()) {
			long id = result.getLong("c_id");
			String name = result.getString("c_name");
			LocalDateTime introduced = timestampToLocalDateTime(result
					.getTimestamp(COMPUTER_INTRODUCED));
			LocalDateTime discontinued = timestampToLocalDateTime(result
					.getTimestamp(COMPUTER_DISCONTINUED));
			computer = new Computer(id, name);

			String companyName = result.getString(COMPANY_NAME);
			if (companyName != null) {
				long companyId = result.getLong(COMPUTER_COMPANYID);
				Company company = new Company(companyId, companyName);
				computer.setCompany(company);
			}

			computer.setIntroductionDate(introduced);
			computer.setDiscontinuationDate(discontinued);
		}

		return computer;
	}

	public static Company toCompany(ResultSet result) throws SQLException {
		Company company = null;

		if (result.next()) {
			long id = result.getLong(COMPANY_ID);
			String name = result.getString(COMPANY_NAME);
			company = new Company(id, name);
		}

		return company;
	}

	public static LocalDateTime timestampToLocalDateTime(Timestamp time) {
		return (time == null) ? null : time.toLocalDateTime();
	}

	public static Timestamp localDateTimeToTimestamp(LocalDateTime time) {
		return (time == null) ? null : Timestamp.valueOf(time);
	}
}
