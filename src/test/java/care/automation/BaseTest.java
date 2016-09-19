package care.automation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import care.util.Driver;
import care.util.ReportGenerator;
import care.util.Screenshot;
import care.variables.Variables;

public class BaseTest {
	
	/** Driver that runs the test, and holds the test parameters */
	protected Driver d;
	/** Flag to run the tests in local */
	private Boolean localFlag = false;
	/** Flag to run the tests on mobile sites */
	private Boolean mobileFlag = false;
	
	/**
	 * Initializes a remote or local driver based on the flags
	 * @param context  						 Test context which contains all the information for a given test run
	 * @param m								 Test case being run
	 * @param isLocal						 Local flag
	 * @param isMobile						 Mobile flag
	 * @throws MalformedURLException
	 */
	public void initializeDriver(ITestContext context, Method m, Boolean isLocal, Boolean isMobile) throws MalformedURLException {
		// Set local flag
		this.localFlag = isLocal;
		
		// Create Extent Report
		ReportGenerator.getTest(m.getName(), "This is a simple test from complex factory");
		
		// Initialize driver
		this.d = new Driver(m.getName(), context.getName(), isLocal, isMobile);
		d.getDriver().manage().deleteAllCookies();
		if (!d.getDeviceType().equalsIgnoreCase("mobile")) d.getDriver().manage().window().maximize();
		d.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/**
	 * Initializes a remote driver
	 * @param context						 Test context which contains all the information for a given test run
	 * @param m								 Test case being run
	 * @throws MalformedURLException
	 */
	public void initializeDriver(ITestContext context, Method m) throws MalformedURLException {
		this.initializeDriver(context, m, localFlag, mobileFlag);
	}
	
	@BeforeSuite(alwaysRun = true)
	public void parseData() throws IOException {

		// Load Environment Properties
		parseEnvironmentProperties();

	}
	
	public void parseEnvironmentProperties() throws IOException {

		// Create properties file object
		Properties properties = new Properties();

		// Initialize input stream
		InputStream inputStream;

		// Generate File name
		String fileName = "care"+Variables.environmentName.toLowerCase() + ".properties";

		// Get file path
		inputStream = new FileInputStream("./src/main/resources/care/properties/" + fileName);

		// Load property file
		properties.load(inputStream);

		Variables.environmentProperties = properties;
	}
	
	/**
	 * Sets results of tests on Saucelabs, and then quits the driver
	 * @param result			Result of a test
	 * @throws IOException
	 */
	@AfterMethod(alwaysRun=true)
	public void tearDown(ITestResult result, Method m) throws IOException {
		if(!localFlag) {
			RemoteWebDriver driver = (RemoteWebDriver) d.getDriver();
			String sauceSessionId = driver.getSessionId().toString();
			
			// if test passed, add passed information to job, else add failed
			if (result.isSuccess()) d.getSauceClient().jobPassed(sauceSessionId); 
			else d.getSauceClient().jobFailed(sauceSessionId);
		}
		
		if (localFlag) {
			if (!result.isSuccess()) {
				ReportGenerator.stepInfo("Test Failed");
			}else {
				ReportGenerator.stepPassed("Test Passed");
			}
			// Take screenshot
			Screenshot.GetScreenshot("./test-output/screenshots/", result.getName(), d.getDriver());
			
			// Close Test
			ReportGenerator.closeTest(m.getName());
		}
		
		// Close the driver
		d.getDriver().quit();
	}
	
	/**
	 * After suite will be responsible to close the report properly at the end
	 * You an have another afterSuite as well in the derived class and this one
	 * will be called in the end making it the last method to be called in test exe
	 */
	@AfterSuite
	public void closeReport() {
		ReportGenerator.closeReport();
	}
}
