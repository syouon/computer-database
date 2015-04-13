package mapper;

import static database.DatabaseNaming.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Company;
import model.Computer;

/* Fait le lien entre le modele objet et
 * les resultats obtenus par une requete
 */
public class Mapper {

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

	private static Date timestampToDate(Timestamp time) {
		if (time == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time.getTime());
		return new Date(calendar.getTimeInMillis());
	}

	public static Computer toComputer(ResultSet result) throws SQLException {
		Computer computer = null;

		while (result.next()) {
			long id = result.getLong(COMPUTER_ID);
			String name = result.getString(COMPUTER_NAME);
			Date introduced = timestampToDate(result
					.getTimestamp(COMPUTER_INTRODUCED));
			Date discontinued = timestampToDate(result
					.getTimestamp(COMPUTER_DISCONTINUED));
			long companyId = result.getLong("c1." + COMPANY_ID);
			String companyName = result.getString("c2." + COMPANY_NAME);
			Company manufacturer = new Company(companyId, companyName);

			computer = new Computer(id, name);
			computer.setIntroductionDate(introduced);
			computer.setDiscontinuationDate(discontinued);
			computer.setManufacturer(manufacturer);
		}

		return computer;
	}
}
