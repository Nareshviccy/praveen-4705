package care.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import care.variables.Variables;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.saucerest.SauceREST;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;

public class Driver implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {
	
	/** Selenium web driver. */
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	
	/** Saucelabs Session ID of the job. Unique for each job. */
	private ThreadLocal<String> sessionId = new ThreadLocal<String>();
	
	/** Browser capabilities being used. For list of capabilities available, 
	 */
	private DesiredCapabilities capabilities = new DesiredCapabilities();
	
	/** Saucelabs Authenticator. Uses Saucelabs Username and API Key. */
	private SauceOnDemandAuthentication sauceAuth;
    
	/** Saucelabs REST Client. Uses Saucelabs Username and API Key. */
	private SauceREST sauceClient;
    
	/** Name of the test case. */
	private String testName;
	
	/** Name of the test defined in the TestNG file. */
	private String testArea;
	
	/** Name of the release defined in Variables file. */
	private String releaseName;
	
	/** Environment being tested; either defined in Variables file or Jenkins.	 */
	private String environment;
	
	/** Type of the device being tested; either defined in Variables file or Jenkins. */
	private String deviceType;
	
	/** Web protocol in use. */
	private String protocol = "http://";
	
	/** List of tags getting uploaded to Saucelabs at the end of the test. */
	private ArrayList<String> tags = new ArrayList<String>();
	
	/** Saucelabs username being used to run the job; either defined in Variables file or Jenkins. */
	private String sauceUsername;
	
	/** Saucelabs API Key being used to run the job; either defined in Variables file or Jenkins. */
	private String sauceAccessKey;
	
	private Properties environmentProperties;
	
	/**
	 * 
	 * Driver Constructor. 
	 * Creates a local or remote driver using the given parameters.
	 * @param testName		Name of the test case.
	 * @param testArea		Name of the test defined in the TestNG file
	 * @param isLocal		Local Flag. Sets type of the driver. (Local or Remote)
	 * @param isMobile		Type of the Web Application being tested
	 * @param isAndroid		Type of the Native Application being tested
	 * @param isiOS			Type of the Native Application being tested
	 */
	public Driver(String testName, String testArea, Boolean isLocal, Boolean isMobileWeb){
		
		// Set variables
		this.testName	= testName;
		this.testArea	= testArea;
		
		// Create driver
		createDriver(isLocal, isMobileWeb);
	}
	
	/**
	 * Creates a local or remote driver using the given parameters.
	 * @param isLocal		Local Flag. Sets type of the driver. (Local or Remote)
	 * @param isMobile		Type of the device being tested
	 * @throws MalformedURLException
	 */
	private void createDriver(Boolean isLocal, Boolean isMobileWeb){
		    	
    	// Set the Environment from Jenkins, if null, get it from Variables file
    	if (System.getenv("Environment") == null) this.environment = Variables.environmentName;
    	else this.environment = System.getenv("Environment");
    	
    	if (isLocal) {
    		
    		if (!isMobileWeb) {
				
    			this.deviceType = "Desktop";
    			
    			if (Variables.browserName.equalsIgnoreCase("Firefox")) {
				
        			// Create Firefox Profile
//        			FirefoxProfile firefoxProfile = new FirefoxProfile();
        			
    				// Set driver with profile
    				Driver.driver.set(new FirefoxDriver());
    				
				}else if (Variables.browserName.equalsIgnoreCase("Chrome")) {
					
					System.setProperty("webdriver.chrome.driver", "src/test/resources/com/browser/drivers/chromedriver.exe");
				
    				// Set driver with profile
    				Driver.driver.set(new ChromeDriver(capabilities));
				}
				
				// Maximize window
				Driver.driver.get().manage().window().maximize();
			}
			
		}else {
			
			if (!isMobileWeb) {
				this.deviceType = "Desktop";
			}
			
			// Set user info for Saucelabs
			if (System.getenv("SAUCE_USER_NAME") == null) sauceUsername = Variables.sauceUsername;
		   	else sauceUsername = System.getenv("SAUCE_USER_NAME");
			    	
		 	if (System.getenv("SAUCE_API_KEY") == null) sauceAccessKey = Variables.sauceAccessKey;
		 	else sauceAccessKey = System.getenv("SAUCE_API_KEY");
					
		 	// Create Saucelabs authenticator and REST client
		 	this.sauceAuth 	= new SauceOnDemandAuthentication(sauceUsername, sauceAccessKey);
	    	this.sauceClient = new SauceREST(sauceUsername, sauceAccessKey);
			
	    	// Set OS from Jenkins, if null, get it from Variables file
	    	if (System.getenv("SELENIUM_PLATFORM") == null) capabilities.setCapability(CapabilityType.PLATFORM, Variables.saucePlatform);
	    	else capabilities.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
	    	
	    	// Set the Browser from Jenkins, if null, get it from Variables file
	    	if (System.getenv("SELENIUM_BROWSER") == null) {
	    		capabilities.setCapability(CapabilityType.BROWSER_NAME, Variables.sauceBrowser);
	    		capabilities.setCapability(CapabilityType.VERSION, Variables.sauceBrowserVersion);
	    	}
	    	else {
	    		capabilities.setCapability(CapabilityType.BROWSER_NAME, System.getenv("SELENIUM_BROWSER"));
	    		capabilities.setCapability(CapabilityType.VERSION, System.getenv("SELENIUM_VERSION"));
	    	}
	    	
	    	// Add the environment tag
	    	tags.add(this.environment);
	    	// Add the device type
	    	tags.add(this.deviceType);
	    	// Add the test area
	    	tags.add(this.testArea);
	    	
	    	// Set the test name, and the tags
			capabilities.setCapability("name", testName);
			capabilities.setCapability("tags", tags);
			
			try {
				
				// Set the driver
				driver.set(new RemoteWebDriver(new URL("http://" + this.getAuthentication().getUsername() 
						+ ":" 
						+ this.getAuthentication().getAccessKey() 
						+ "@ondemand.saucelabs.com:80/wd/hub"), capabilities));
				
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			}
			
			// Get the session ID from Saucelabs, and set the local value
			setSessionId(((RemoteWebDriver) getDriver()).getSessionId().toString());	
			
			// Print the Session ID
	    	String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", ((RemoteWebDriver) getDriver()).getSessionId().toString(), this.getTestName());
	    	System.out.println(message);
		}
	}


	public WebDriver getDriver() {
		return driver.get();
	}
	
	public static void setDriver(ThreadLocal<WebDriver> driver) {
		Driver.driver = driver;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestArea() {
		return testArea;
	}

	public void setTestArea(String testArea) {
		this.testArea = testArea;
	}

	public DesiredCapabilities getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(DesiredCapabilities capabilities) {
		this.capabilities = capabilities;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(String type) {
		this.deviceType = type;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getSauceUsername() {
		return sauceUsername;
	}

	public void setSauceUsername(String sauceUsername) {
		this.sauceUsername = sauceUsername;
	}

	public String getSauceAccessKey() {
		return sauceAccessKey;
	}

	public void setSauceAccessKey(String sauceAccessKey) {
		this.sauceAccessKey = sauceAccessKey;
	}

	@Override
	public SauceOnDemandAuthentication getAuthentication() {
		return sauceAuth;
	}

	public void setAuthentication(SauceOnDemandAuthentication sauceAuth) {
		this.sauceAuth = sauceAuth;
	}
	
	public SauceREST getSauceClient() {
		return sauceClient;
	}

	public void setSauceClient(SauceREST sauceClient) {
		this.sauceClient = sauceClient;
	}

	@Override
	public String getSessionId() {
		return sessionId.get();
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId.set(sessionId);
	}

	public void setSessionId(ThreadLocal<String> sessionId) {
		this.sessionId = sessionId;
	}

	public Properties getEnvironmentProperties() {
		return environmentProperties;
	}

	public void setEnvironmentProperties(Properties environmentProperties) {
		this.environmentProperties = environmentProperties;
	}
}
