package care.page.desktop;

import care.page.BasePage;
import care.util.Driver;
import care.util.ReportGenerator;

public class HomePage extends BasePage {

	public HomePage(Driver driver) {
		super(driver);
		// Create Report
		ReportGenerator.stepInfo("Navigating to Home Page");
		// Navigate to Home Page
		driver.getDriver().navigate().to(super.urlCreator(driver.getEnvironment()));
		ReportGenerator.stepInfo("Opened Home Page");
		System.out.println(loadEnvironmentProperties().getProperty("URL"));
	}
}
