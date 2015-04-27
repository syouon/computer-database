import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import model.Company;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import util.Utils;
import dao.CompanyDAOImpl;
import dao.ConnectionFactory;
import static dao.DatabaseNaming.COMPANY_TABLE;

public class CompanyDAOTest extends DBTestCase {

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
		List<Company> companies = CompanyDAOImpl.getInstance().findAll();

		Company company = CompanyDAOImpl.getInstance().find(
				companies.get(0).getId());
		assertEquals("Should be equal", company, companies.get(0));
	}

	@Test
	public void testFindAll() {
		List<Company> company = CompanyDAOImpl.getInstance().findAll();

		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT COUNT(*) FROM "
					+ COMPANY_TABLE + ";");
			result = statement.executeQuery();

			int lineNumber = 0;
			while (result.next()) {
				lineNumber = result.getInt(1);
			}

			assertEquals("must return the same number of line", company.size(),
					lineNumber);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.getInstance().closeResultSetAndStatement(
					statement, result);
			ConnectionFactory.getInstance().closeConnection();
		}
	}

	@Test
	public void testExists() {
		Company company = new Company("Apple");
		Company falseCompany = new Company("InexistantCorp");
		assertTrue("Apple should exist",
				CompanyDAOImpl.getInstance().exists(company));
		assertFalse("InexistantCorp should not exist", CompanyDAOImpl
				.getInstance().exists(falseCompany));
	}

	@Test
	public void testDelete() {
		try {
			assertNotNull(CompanyDAOImpl.getInstance().find(3));
			CompanyDAOImpl.getInstance().delete(3);
			assertNull(CompanyDAOImpl.getInstance().find(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass()
				.getClassLoader().getResourceAsStream("dataset.xml"));
	}
}
