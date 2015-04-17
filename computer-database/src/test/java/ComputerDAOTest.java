import static dao.DatabaseNaming.COMPUTER_TABLE;
import static dao.DatabaseNaming.COMPUTER_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotSame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.Computer;

import org.junit.Test;

import dao.ComputerDAOImpl;
import dao.ConnectionFactory;

public class ComputerDAOTest {

	@Test
	public void testCreate() {
		Computer computer = new Computer("ComputerTest");
		ComputerDAOImpl.getInstance().create(computer);
		List<Computer> newComputers = ComputerDAOImpl.getInstance()
				.findAll();

		// Suppression du computer ajoute pour ne pas modifier la bdd
		Computer newComputer = null;
		for (Computer c : newComputers) {
			if (c.getName().equals(computer.getName())) {
				newComputer = c;
				computer.setId(c.getId());
				break;
			}
		}

		// L'element ajoute doit etre le meme que computer
		assertTrue("Computer added must be the same as ComputerTest",
				computer.equals(newComputer));

		ComputerDAOImpl.getInstance().delete(computer.getId());

		if (newComputer != null) {
			ComputerDAOImpl.getInstance().delete(newComputer.getId());
		}
	}

	@Test
	public void testDelete() {
		Computer computer = new Computer("ComputerTest");
		ComputerDAOImpl.getInstance().create(computer);
		List<Computer> computers = ComputerDAOImpl.getInstance().findAll();

		// on renseigne l'id obtenu lors de la creation dans computer
		for (Computer c : computers) {
			if (c.getName().equals(computer.getName())) {
				computer.setId(c.getId());
			}
		}

		// Suppression de l'element ajoute
		ComputerDAOImpl.getInstance().delete(computer.getId());

		assertNull("ComputerTest should not exist", ComputerDAOImpl
				.getInstance().find(computer.getId()));
	}

	@Test
	public void testUpdate() {
		Computer computer = new Computer("ComputerTest");
		ComputerDAOImpl.getInstance().create(computer);

		List<Computer> computers = ComputerDAOImpl.getInstance().findAll();

		Computer updatedComputer = new Computer("ComputerTest");
		for (Computer c : computers) {
			if (c.getName().equals(computer.getName())) {
				computer.setId(c.getId());
				updatedComputer.setId(c.getId());
				break;
			}
		}
		ComputerDAOImpl.getInstance().update(updatedComputer);
		Computer newComputer = ComputerDAOImpl.getInstance().find(
				updatedComputer.getId());

		assertNotSame("Should not be equal", computer, newComputer);

		ComputerDAOImpl.getInstance().delete(computer.getId());
	}

	@Test
	public void testFind() {
		Computer computer = new Computer("ComputerTest");
		ComputerDAOImpl.getInstance().create(computer);
		List<Computer> computers = ComputerDAOImpl.getInstance().findAll();

		Computer addedComputer = null;
		for (Computer c : computers) {
			if (c.getName().equals(computer.getName())) {
				computer.setId(c.getId());
				addedComputer = c;
				break;
			}
		}

		assertNotNull("Should not be null", addedComputer);

		// Suppression de l'element
		ComputerDAOImpl.getInstance().delete(computer.getId());
	}

	@Test
	public void testFindAll() {
		List<Computer> computers = ComputerDAOImpl.getInstance().findAll();

		Connection conn = ConnectionFactory.getInstance().openConnection();
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = conn.createStatement();
			result = statement.executeQuery("SELECT COUNT(*) FROM "
					+ COMPUTER_TABLE + ";");

			int lineNumber = 0;
			while (result.next()) {
				lineNumber = result.getInt(1);
			}

			assertEquals("Should have the same size", computers.size(),
					lineNumber);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null) {
					result.close();
				}

				if (statement != null) {
					statement.close();
				}

				ConnectionFactory.getInstance().closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
