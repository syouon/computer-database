import static dao.DatabaseNaming.COMPUTER_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import model.Computer;

import org.junit.Test;

import dao.ComputerDAOImpl;
import dao.ConnectionFactory;

public class ComputerDAOTest {

	@Test
	public void testCreate() {
		Computer computer = new Computer("ComputerTest");
		long id = ComputerDAOImpl.getInstance().create(computer);
		computer.setId(id);

		List<Computer> newComputers = ComputerDAOImpl.getInstance().findAll();

		// L'element ajoute doit etre le meme que computer
		assertTrue("Computer added must be the same as ComputerTest",
				newComputers.contains(computer));

		ComputerDAOImpl.getInstance().delete(computer.getId());
	}

	@Test
	public void testDelete() {
		Computer computer = new Computer("ComputerTest");
		long id = ComputerDAOImpl.getInstance().create(computer);
		computer.setId(id);

		// Suppression de l'element ajoute
		ComputerDAOImpl.getInstance().delete(computer.getId());

		assertNull("ComputerTest should not exist", ComputerDAOImpl
				.getInstance().find(computer.getId()));
	}

	@Test
	public void testUpdate() {
		Computer computer = new Computer("ComputerTest");
		long id = ComputerDAOImpl.getInstance().create(computer);
		computer.setId(id);

		Computer updatedComputer = new Computer(id, "ComputerTest");
		updatedComputer.setIntroductionDate(LocalDate.now());

		ComputerDAOImpl.getInstance().update(updatedComputer);
		Computer newComputer = ComputerDAOImpl.getInstance().find(
				updatedComputer.getId());

		assertTrue("Should not be equal", !computer.equals(newComputer));

		ComputerDAOImpl.getInstance().delete(computer.getId());
	}

	@Test
	public void testFind() {
		Computer computer = new Computer("ComputerTest");
		long id = ComputerDAOImpl.getInstance().create(computer);
		computer.setId(id);

		List<Computer> computers = ComputerDAOImpl.getInstance().findAll();

		assertNotNull("Should not be null", computers.contains(computer));

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
