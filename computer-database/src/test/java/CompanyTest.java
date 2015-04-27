import static org.junit.Assert.*;
import model.Company;

import org.junit.Test;

public class CompanyTest {

	@Test
	public void testGetName() {
		Company company = new Company("CompanyTest");
		assertEquals(company.getName(), "CompanyTest");
	}

	@Test
	public void testSetName() {
		Company company = new Company("CompanyTest");
		company.setName("NewCompanyTest");
		assertNotEquals(company.getName(), "CompanyTest");
		assertEquals(company.getName(), "NewCompanyTest");
	}

	@Test
	public void testToString() {
		Company company = new Company("CompanyTest");
		assertEquals(company.toString(), "n°0) CompanyTest");
	}

	@Test
	public void testEqualsObject() {
		Company company1 = new Company("CompanyTest1");
		Company company2 = new Company("CompanyTest2");
		assertNotEquals(company1, company2);

		Company company = new Company("CompanyTest1");
		assertEquals(company, company1);
	}

}
