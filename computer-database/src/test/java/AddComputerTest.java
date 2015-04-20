import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AddComputerTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testAddComputer() throws Exception {
		int totalComputers = 0;

		driver.get(baseUrl + "computer-database/DashboardServlet");

		// recuperation du nombre de computers avant ajout
		String oldTotal = driver.findElement(By.id("homeTitle")).getText();
		String[] tokens = oldTotal.split(" ");
		if (tokens[0].matches("\\d*")) {
			totalComputers = Integer.parseInt(tokens[0]);
		}

		driver.findElement(By.id("addComputer")).click();
		driver.findElement(By.id("computerName")).clear();
		driver.findElement(By.id("computerName")).sendKeys("AAA");
		driver.findElement(By.id("introduced")).clear();
		driver.findElement(By.id("introduced")).sendKeys("2015-05-12");
		new Select(driver.findElement(By.id("companyId")))
				.selectByVisibleText("Sony");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

		// recuperation du nombre de computers apres ajout
		String newTotal = driver.findElement(By.id("homeTitle")).getText();
		tokens = newTotal.split(" ");
		if (tokens[0].matches("\\d*")) {
			assertEquals("Should have one computer added", totalComputers + 1,
					Integer.parseInt(tokens[0]));
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
