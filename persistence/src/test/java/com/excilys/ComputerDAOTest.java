package com.excilys;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.dao.ComputerDAO;
import com.excilys.model.Computer;
import com.excilys.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ComputerDAOTest extends DBTestCase {

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
		computerDAO.save(computer);

		List<Computer> newComputers = computerDAO.findAll();

		// L'element ajoute doit etre le meme que computer
		assertTrue("Computer added must be the same as ComputerTest",
				newComputers.contains(computer));
	}

	@Test
	public void testDelete() {
		Computer computer = new Computer("ComputerTest");
		computerDAO.save(computer);

		// Suppression de l'element ajoute
		computerDAO.delete(computer.getId());

		assertNull("ComputerTest should not exist",
				computerDAO.findOne(computer.getId()));
	}

	@Test
	public void testDeleteByCompany() {
		assertNotNull(computerDAO.findOne(3L));
		assertNotNull(computerDAO.findOne(4L));
		computerDAO.deleteByCompanyId(2);
		assertNull(computerDAO.findOne(3L));
		assertNull(computerDAO.findOne(4L));
	}

	@Test
	public void testUpdate() {
		Computer computer = new Computer("ComputerTest");
		computer = computerDAO.save(computer);

		Computer updatedComputer = new Computer(computer.getId(),
				"ComputerTest");
		updatedComputer.setIntroduced(LocalDate.now());

		computerDAO.save(updatedComputer);
		Computer newComputer = computerDAO.findOne(updatedComputer.getId());

		assertTrue("Should not be equal", !computer.equals(newComputer));
	}

	@Test
	public void testFind() {
		Computer computer = new Computer("ComputerTest");
		computerDAO.save(computer);

		List<Computer> computers = computerDAO.findAll();

		assertNotNull("Should not be null", computers.contains(computer));
	}

	@Test
	public void testFindAll() {
		List<Computer> computers = computerDAO.findAll();
		int lineNumber = (int) computerDAO.count();
		assertEquals("Should have the same size", computers.size(), lineNumber);
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass()
				.getClassLoader().getResourceAsStream("dataset.xml"));
	}

}
