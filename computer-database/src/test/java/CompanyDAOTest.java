import static dao.DatabaseNaming.COMPANY_TABLE;

import java.util.List;
import java.util.Properties;

import model.Company;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import util.Utils;
import dao.CompanyDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CompanyDAOTest extends DBTestCase {

	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private JdbcTemplate jdbc;

	public CompanyDAOTest() {
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
	public void testFind() {
		List<Company> companies = companyDAO.findAll();

		Company company = companyDAO.findOne(companies.get(0).getId());
		assertEquals("Should be equal", company, companies.get(0));
	}

	@Test
	public void testFindAll() {
		List<Company> company = companyDAO.findAll();

		int lineNumber = jdbc.queryForObject("SELECT COUNT(*) FROM "
				+ COMPANY_TABLE, Integer.class);

		assertEquals("must return the same number of line", company.size(),
				lineNumber);
	}

	@Test
	public void testExists() {
		Company company = new Company("Apple");
		Company falseCompany = new Company("InexistantCorp");
		assertNotNull("Apple should exist ",
				companyDAO.findByName(company.getName()));
		assertNull("InexistantCorp should not exist",
				companyDAO.findByName(falseCompany.getName()));
	}

	@Test
	public void testDelete() {
		assertNotNull(companyDAO.findOne(3L));
		companyDAO.delete(3L);
		assertNull(companyDAO.findOne(3L));
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass()
				.getClassLoader().getResourceAsStream("dataset.xml"));
	}
}
