import static dao.DatabaseNaming.COMPUTER_TABLE;
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

import dao.ConcreteComputerDAO;
import dao.ConnectionFactory;

public class ComputerDAOTest {

	@Test
	public void testCreate() {
		List<Computer> currentComputers = ConcreteComputerDAO.getInstance()
				.findAll(0, 10);
		Computer computer = new Computer("ComputerTest");
		ConcreteComputerDAO.getInstance().create(computer);
		List<Computer> newComputers = ConcreteComputerDAO.getInstance()
				.findAll(0, 10);

		assertEquals("New list should be bigger by one element",
				currentComputers.size() + 1, newComputers.size());

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

		if (newComputer != null) {
			ConcreteComputerDAO.getInstance().delete(newComputer.getId());
		}
	}

	@Test
	public void testDelete() {
		Computer computer = new Computer("ComputerTest");
		ConcreteComputerDAO.getInstance().create(computer);
		List<Computer> computers = ConcreteComputerDAO.getInstance().findAll(0, 10);

		// on renseigne l'id obtenu lors de la creation dans computer
		for (Computer c : computers) {
			if (c.getName().equals(computer.getName())) {
				computer.setId(c.getId());
			}
		}

		// Suppression de l'element ajoute
		ConcreteComputerDAO.getInstance().delete(computer.getId());

		assertNull("ComputerTest should not exist", ConcreteComputerDAO
				.getInstance().find(computer.getId()));
	}

	@Test
	public void testUpdate() {
		Computer computer = new Computer("ComputerTest");
		ConcreteComputerDAO.getInstance().create(computer);
		
		List<Computer> computers = ConcreteComputerDAO.getInstance().findAll(0, 10);

		Computer updatedComputer = new Computer("ComputerTest");
		for (Computer c : computers) {
			if (c.getName().equals(computer.getName())) {
				computer.setId(c.getId());
				updatedComputer.setId(c.getId());
				break;
			}
		}
		ConcreteComputerDAO.getInstance().update(updatedComputer);
		Computer newComputer = ConcreteComputerDAO.getInstance().find(updatedComputer.getId());
		
		assertNotSame("Should not be equal", computer, newComputer);
		
		ConcreteComputerDAO.getInstance().delete(computer.getId());
	}

	@Test
	public void testFind() {
		Computer computer = new Computer("ComputerTest");
		ConcreteComputerDAO.getInstance().create(computer);
		List<Computer> computers = ConcreteComputerDAO.getInstance().findAll(0, 10);

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
		ConcreteComputerDAO.getInstance().delete(computer.getId());
	}

	@Test
	public void testFindAll() {
		List<Computer> computers = ConcreteComputerDAO.getInstance().findAll(0, 10);
		
		Connection conn = ConnectionFactory.getInstance().openConnection();
		Statement statement = null;
		ResultSet result = null;
		
		try {
			statement = conn.createStatement();
			result = statement.executeQuery("SELECT COUNT(*) FROM " + COMPUTER_TABLE + ";");
			
			int lineNumber = 0;
			if (result.next()) {
				lineNumber = result.getInt(1);
			}
			
			assertEquals("Should have the same size", computers.size(), lineNumber);

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
