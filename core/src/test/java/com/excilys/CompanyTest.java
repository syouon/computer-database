package com.excilys;

import static org.junit.Assert.*;
import com.excilys.model.Company;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
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
