package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.Computer;
import database.DatabaseNaming;

/* Fait le lien entre le modele objet et
 * les resultats obtenus par une requete
 */
public class Mapper {

	public static List<Computer> toComputerList(ResultSet result)
			throws SQLException {
		List<Computer> computers = new ArrayList<>();
		
		while (result.next()) {
			long id = result.getLong(DatabaseNaming.COMPUTER_ID);
			String name = result.getString(DatabaseNaming.COMPUTER_NAME);
			Computer computer = new Computer(id, name);
			computers.add(computer);
		}
		
		return computers;
	}
	
	public static List<Company> toCompanyList(ResultSet result)
			throws SQLException {
		List<Company> companies = new ArrayList<>();
		
		while (result.next()) {
			long id = result.getLong(DatabaseNaming.COMPANY_ID);
			String name = result.getString(DatabaseNaming.COMPANY_NAME);
			Company company = new Company(id, name);
			companies.add(company);
		}
		
		return companies;
	}
}
