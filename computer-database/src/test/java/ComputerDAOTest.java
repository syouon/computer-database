import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import model.Computer;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import util.Utils;
import dao.ComputerDAOImpl;
import dao.ConnectionFactory;
import dao.DatabaseNaming;

public class ComputerDAOTest extends DBTestCase {

	public ComputerDAOTest() {
		Properties prop = Utils.loadProperties("database.properties");
		System.setProperty(
				PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
				prop.getProperty("driver"));
		System.setProperty(
				PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
				prop.getProperty("url"));
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
				prop.getProperty("user"));
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
				prop.getProperty("password"));
	}

	@Test
	public void testCreate() {
		Computer computer = new Computer("ComputerTest");
		long id = ComputerDAOImpl.getInstance().create(computer);
		computer.setId(id);

		List<Computer> newComputers = ComputerDAOImpl.getInstance().findAll();

		// L'element ajoute doit etre le meme que computer
		assertTrue("Computer added must be the same as ComputerTest",
				newComputers.contains(computer));
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
	public void testDeleteByCompany() {
		try {
			assertNotNull(ComputerDAOImpl.getInstance().find(3));
			assertNotNull(ComputerDAOImpl.getInstance().find(4));
			ComputerDAOImpl.getInstance().deleteByCompany(2);
			assertNull(ComputerDAOImpl.getInstance().find(3));
			assertNull(ComputerDAOImpl.getInstance().find(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	}

	@Test
	public void testFind() {
		Computer computer = new Computer("ComputerTest");
		long id = ComputerDAOImpl.getInstance().create(computer);
		computer.setId(id);

		List<Computer> computers = ComputerDAOImpl.getInstance().findAll();

		assertNotNull("Should not be null", computers.contains(computer));
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
					+ DatabaseNaming.COMPUTER_TABLE + ";");

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

				ConnectionFactory.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass()
				.getClassLoader().getResourceAsStream("dataset.xml"));
	}

}
