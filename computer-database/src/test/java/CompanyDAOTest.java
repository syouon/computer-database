import static dao.DatabaseNaming.COMPANY_TABLE;
import static dao.DatabaseNaming.COMPANY_NAME;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.Company;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.ConcreteCompanyDAO;
import dao.ConnectionFactory;

public class CompanyDAOTest {

	private Connection conn;
	private Statement statement;
	private ResultSet result;

	@Before
	public void initResource() {
		conn = ConnectionFactory.getInstance().openConnection();
		statement = null;
		result = null;
	}

	@After
	public void closeResource() {
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

	@Test
	public void testFind() {
		List<Company> companies = ConcreteCompanyDAO.getInstance().findAll();
		
		Company company = ConcreteCompanyDAO.getInstance().find(companies.get(0).getId());
		assertEquals("Should be equal", company, companies.get(0));
	}
	
	@Test
	public void testFindAll() {
		List<Company> company = ConcreteCompanyDAO.getInstance().findAll();

		try {
			statement = conn.createStatement();
			result = statement.executeQuery("SELECT COUNT(*) FROM "
					+ COMPANY_TABLE + ";");

			int lineNumber = 0;
			while (result.next()) {
				lineNumber = result.getInt(1);
			}

			assertEquals("must return the same number of line", company.size(),
					lineNumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testExists() {
		Company company = new Company("Apple Inc.");
		Company falseCompany = new Company("InexistantCorp");
		assertTrue("Apple Inc. should exist", ConcreteCompanyDAO.getInstance()
				.exists(company));
		assertFalse("InexistantCorp should not exist", ConcreteCompanyDAO
				.getInstance().exists(falseCompany));
	}
}
