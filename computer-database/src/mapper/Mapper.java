package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Computer;
import database.DatabaseNaming;

/* Cette classe fait le lien entre le modele objet et
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
}
