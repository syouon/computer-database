package mapper;

import static dao.DatabaseNaming.COMPANY_ID;
import static dao.DatabaseNaming.COMPANY_NAME;
import static dao.DatabaseNaming.COMPUTER_DISCONTINUED;
import static dao.DatabaseNaming.COMPUTER_ID;
import static dao.DatabaseNaming.COMPUTER_INTRODUCED;
import static dao.DatabaseNaming.COMPUTER_NAME;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
			Computer computer = new Computer(id, name);
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
			long id = result.getLong(COMPUTER_ID);
			String name = result.getString(COMPUTER_NAME);
			LocalDateTime introduced = timestampToLocalDateTime(result
					.getTimestamp(COMPUTER_INTRODUCED));
			LocalDateTime discontinued = timestampToLocalDateTime(result
					.getTimestamp(COMPUTER_DISCONTINUED));
			computer = new Computer(id, name);

			/*
			 * le bloc try catch empeche juste d'executer toutes ses
			 * instructions lorsque la jointure entre les deux tables n'a pas
			 * ete faites (sans jointure pas d'alias c2)
			 */
			try {
				long companyId = result.getLong("c2." + COMPANY_ID);
				String companyName = result.getString("c2." + COMPANY_NAME);
				Company manufacturer = new Company(companyId, companyName);
				computer.setCompany(manufacturer);

			} catch (SQLException e) {
			} // erreur non dangereuse

			computer.setIntroductionDate(introduced);
			computer.setDiscontinuationDate(discontinued);
		}

		return computer;
	}

	public static LocalDateTime timestampToLocalDateTime(Timestamp time) {
		return (time == null) ? null : time.toLocalDateTime();
	}

	public static Timestamp localDateTimeToTimestamp(LocalDateTime time) {
		return (time == null) ? null : Timestamp.valueOf(time);
	}
}
