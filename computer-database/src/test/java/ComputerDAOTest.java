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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import util.Utils;
import dao.ComputerDAO;
import dao.ConnectionFactory;
import dao.DatabaseNaming;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ComputerDAOTest extends DBTestCase {

	@Autowired
	private ConnectionFactory connection;
	@Autowired
	private ComputerDAO computerDAO;

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
		long id = computerDAO.create(computer);
		computer.setId(id);

		List<Computer> newComputers = computerDAO.findAll();

		// L'element ajoute doit etre le meme que computer
		assertTrue("Computer added must be the same as ComputerTest",
				newComputers.contains(computer));
	}

	@Test
	public void testDelete() {
		Computer computer = new Computer("ComputerTest");
		long id = computerDAO.create(computer);
		computer.setId(id);

		// Suppression de l'element ajoute
		computerDAO.delete(computer.getId());

		assertNull("ComputerTest should not exist",
				computerDAO.find(computer.getId()));
	}

	@Test
	public void testDeleteByCompany() {
		try {
			assertNotNull(computerDAO.find(3));
			assertNotNull(computerDAO.find(4));
			computerDAO.deleteByCompany(2);
			assertNull(computerDAO.find(3));
			assertNull(computerDAO.find(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate() {
		Computer computer = new Computer("ComputerTest");
		long id = computerDAO.create(computer);
		computer.setId(id);

		Computer updatedComputer = new Computer(id, "ComputerTest");
		updatedComputer.setIntroductionDate(LocalDate.now());

		computerDAO.update(updatedComputer);
		Computer newComputer = computerDAO.find(updatedComputer.getId());

		assertTrue("Should not be equal", !computer.equals(newComputer));
	}

	@Test
	public void testFind() {
		Computer computer = new Computer("ComputerTest");
		long id = computerDAO.create(computer);
		computer.setId(id);

		List<Computer> computers = computerDAO.findAll();

		assertNotNull("Should not be null", computers.contains(computer));
	}

	@Test
	public void testFindAll() {
		List<Computer> computers = computerDAO.findAll();

		Connection conn = connection.openConnection();
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

				connection.closeConnection();
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
