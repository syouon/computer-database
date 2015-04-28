import static org.junit.Assert.*;

import java.time.LocalDate;

import model.Company;
import model.Computer;

import org.junit.Test;
import org.mockito.Mockito;

public class ComputerTest {

	@Test
	public void testGetName() {
		Computer computer = new Computer("ComputerTest");
		assertEquals(computer.getName(), "ComputerTest");
	}

	@Test
	public void testGetIntroductionDate() {
		Computer computer = new Computer("ComputerTest");
		assertNull(computer.getIntroductionDate());

		computer.setIntroductionDate(LocalDate.parse("2015-04-01"));
		assertEquals(computer.getIntroductionDate().toString(), "2015-04-01");
	}

	@Test
	public void testGetDiscontinuationDate() {
		Computer computer = new Computer("ComputerTest");
		assertNull(computer.getDiscontinuationDate());

		computer.setDiscontinuationDate(LocalDate.parse("2015-04-01"));
		assertEquals(computer.getDiscontinuationDate().toString(), "2015-04-01");
	}

	@Test
	public void testGetCompany() {
		Company company = Mockito.mock(Company.class);
		Mockito.when(company.getName()).thenReturn("CompanyTest");

		Computer computer = new Computer("ComputerTest");
		assertNull(computer.getCompany());

		computer.setCompany(company);
		assertEquals(computer.getCompany().getName(), "CompanyTest");
	}

	@Test
	public void testSetIntroductionDate() {
		Computer computer = new Computer("ComputerTest");
		computer.setIntroductionDate(LocalDate.parse("2015-04-01"));
		assertEquals(computer.getIntroductionDate().toString(), "2015-04-01");
	}

	@Test
	public void testSetDiscontinuationDate() {
		Computer computer = new Computer("ComputerTest");
		computer.setDiscontinuationDate(LocalDate.parse("2015-04-01"));
		assertEquals(computer.getDiscontinuationDate().toString(), "2015-04-01");
	}

	@Test
	public void testSetCompany() {
		Computer computer = new Computer("ComputerTest");
		assertNull(computer.getCompany());

		Company company = Mockito.mock(Company.class);
		Mockito.when(company.getName()).thenReturn("CompanyTest");
		computer.setCompany(company);

		assertNotNull(computer.getCompany());
		assertEquals(computer.getCompany().getName(), "CompanyTest");
	}

	@Test
	public void testToString() {
		Computer computer = new Computer("ComputerTest");
		assertEquals(computer.toString(), "n°0) ComputerTest");

		computer.setIntroductionDate(LocalDate.parse("2015-04-01"));
		assertEquals(computer.toString(),
				"n°0) ComputerTest, introduced in 2015-04-01");

		computer.setDiscontinuationDate(LocalDate.parse("2015-04-01"));
		assertEquals(computer.toString(),
				"n°0) ComputerTest, introduced in 2015-04-01, discontinued in 2015-04-01");

		Company company = Mockito.mock(Company.class);
		Mockito.when(company.toString()).thenReturn("n°10) Apple Inc.");
		computer.setCompany(company);
		assertEquals(
				computer.toString(),
				"n°0) ComputerTest, introduced in 2015-04-01, discontinued in 2015-04-01, by n°10) Apple Inc.");
	}

	@Test
	public void testEqualsObject() {
		Computer computer1 = new Computer("ComputerTest1");
		Computer computer2 = new Computer("ComputerTest2");
		assertNotEquals(computer1, computer2);

		Computer computer = new Computer("ComputerTest1");
		assertEquals(computer, computer1);

		computer.setIntroductionDate(LocalDate.parse("2015-04-01"));
		assertNotEquals(computer, computer1);
		computer1.setIntroductionDate(LocalDate.parse("2015-04-01"));
		assertEquals(computer, computer1);

		Company company = new Company("CompanyTest");
		computer.setCompany(company);
		assertNotEquals(computer, computer1);
		computer1.setCompany(company);
		assertEquals(computer, computer1);

		Company company1 = new Company("CompanyTest");
		computer1.setCompany(company1);
		assertEquals(computer, computer1);
		company1.setName("CompanyTest1");
		assertNotEquals(computer, computer1);
	}

}