import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SearchComputerTest {
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
	public void testSearchComputer() throws Exception {
		driver.get(baseUrl + "computer-database/DashboardServlet");
		driver.findElement(By.id("searchbox")).clear();
		driver.findElement(By.id("searchbox")).sendKeys("apple");
		driver.findElement(By.id("searchsubmit")).click();

		String total = driver.findElement(By.id("homeTitle")).getText();
		String[] tokens = total.split(" ");
		if (tokens[0].matches("\\d*")) {
			assertEquals("Should have found 46 computers", 46,
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
