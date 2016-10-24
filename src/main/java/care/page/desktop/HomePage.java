package care.page.desktop;

import care.page.BasePage;
import care.util.Driver;
import care.util.ReportGenerator;
import care.variables.Variables;

public class HomePage extends BasePage {

	public HomePage(Driver driver) {
		super(driver);
		// Navigate to Home Page
		driver.getDriver().navigate().to(super.urlCreator(driver.getEnvironment()));
		
		System.out.println(loadEnvironmentProperties().getProperty("URL"));
	}
	
	/**
	 * Navigate to enrollment Page
	 * @return	Returns Enrollment page object
	 */
	public EnrollmentPage navigateToEnrollmentPage(){
		
		// Click on JOIN Now link
		locateElement(Variables.homePage_link_JoinNow).click();
		
		//Return Enrollment Page Object
		return new EnrollmentPage(driver);
	}
}
