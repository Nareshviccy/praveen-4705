package care.page;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.ExtentTest;

import care.util.Driver;
import care.variables.Variables;

public class BasePage {

	/** Driver Reference */
	protected Driver driver;
	
	private ExtentTest testScript;
	
	 /** URL of the env being tested */
	 private URL testURL;

	/**
	 * Base Page constructor, sets the driver
	 * @param driver	driver created by BaseTest class
	 */
	public BasePage(Driver driver) {
		this.driver = driver;
	}
	
	/**
	 * Locate element on the page
	 * @param by 	Unique identifier of the element
	 * @return 		Returns the element or throws an exception
	 */
	public WebElement locateElement(By by) {
		try {
			// Get the element by Unique identifier of the element
			WebElement w = driver.getDriver().findElement(by);
			
			// Return WebElement
			return w;
		} catch (NoSuchElementException e) {

			// Shown Element not found message if the element is not found
			throw new NoSuchElementException("Element not found");
		}
	}
	
	/**
	 * Checks whether an element is present on the page or not
	 * @param by 	Unique identifier of the element
	 * @return 		Returns true if the element is found, returns false otherwise
	 */
	public Boolean verifyElement(By by) {
		try {

			// Get the element using the Unique identifier of the element
			driver.getDriver().findElement(by);

		} catch (NoSuchElementException e) {

			// Return false if element is not found
			return false;
		} catch (Exception e) {

			// Return false if element is not found
			return false;
		}
		// Return true if element is found
		return true;
	}
	
	/**
	 * Write the String in the field
	 * @param by	Unique identifier of the element
	 * @param s		String to be written into the field
	 */
	public void type(By by, String s) {
		// Locate the element
		WebElement w = locateElement(by);
		
		// Type the given string to the field
		type(w, s);
	}
	
	/**
	 * Clears the field and then writes in it
	 * @param w		Element that will be written into
	 * @param s		String to be written into the field
	 */
	public void type(WebElement w, String s) {
		// Clear the field
		w.clear();
		
		// Write the string in it
		w.sendKeys(s);
	}
	
	/**
	 * Hover over the given element
	 * @param w		Web Element
	 */
	public void hoverOverElement(WebElement w) {
		
		// Create Actions object
		Actions hover = new Actions(driver.getDriver());
		
		// hover on the element
		hover.moveToElement(w);
		
		// Perform the Action
		hover.perform();
	}
	
	
	/**
	 * Verify error message is on page, locate, check the length of the message
	 * Fails if the error message is not visible,  or length is 0
	 * @param by Unique identifier of the element
	 */
	public void checkErrorMessage(By by) {
		//Check  presence of the element
		Assert.assertTrue(verifyElement(by));
		//Check the length of the error message
		Assert.assertFalse(locateElement(by).getText().length() == 0);
	}
	
	/**
	 * Verify error message is on page, locate, check the length and the message
	 * @param by		Unique identifier of the element
	 * @param message	Message to be checked
	 */
	public void checkErrorMessage(By by, String message) {
		//Verify  message is on page, locate, check the length and the message
		checkErrorMessage(by);
		//Check the error message is equals with the given message or not
		Assert.assertTrue(locateElement(by).getText().equalsIgnoreCase(message));
	}
	
	/**
	 * Verify  message is on page, locate, check the length and the message
	 * @param by	Unique identifier of the element
	 */
	public void checkMessage(By by) {
		//Check presence of the element
		Assert.assertTrue(verifyElement(by));
		//Check message is contains any ??? symbols or not
		Assert.assertFalse(locateElement(by).getText().contains("???"));
		//Check the length of the error message
		Assert.assertFalse(locateElement(by).getText().length() == 0);
	}
	
	/**
	 * Verify  message is on page, locate, check the length and the message
	 * @param by		Unique identifier of the element
	 * @param message	Message to be checked
	 */
	public void checkMessage(By by, String message) {
		//Verify  message is on page, locate, check the length and the message
		checkMessage(by);
		//Check the message is equals with the given message or not
		Assert.assertTrue(locateElement(by).getText().equalsIgnoreCase(message));
	}
	
	/**
	 * Verify the text in Text Box
	 * @param by		Unique identifier of the element
	 * @param message	Message to be checked
	 */
	public void checkTextInTextBox(By by, String message){
		//Check the presence of the element
		Assert.assertTrue(verifyElement(by));
		
		//Check the keyed text in text box is equals with the message or not
		Assert.assertTrue(locateElement(by).getAttribute("value").equalsIgnoreCase(message));
	}
	
	/**
	 * Get inserted value from Text Box
	 * @param by
	 * @return	Return String
	 */
	public String getValueFromTextBox(By by){
		
		// Get Text box element
		WebElement w = locateElement(by);
		
		// Return text box value
		return w.getAttribute("value").trim();
	}
	
	/**
	 * Creates the URL that will be used for testing
	 * @param env	Environment to be tested (uat1, uat2, uat3, etc)
	 * @return		Returns the URL
	 */
	public URL urlCreator(String env){
		
		String path = "http://";
		
		switch (env.toLowerCase()) {
		case "uat1":
			path = path+"www."+env.toLowerCase()+".carezen.net";
			break;
		case "uat2":
			path = path+"www."+env.toLowerCase()+".carezen.net";
			break;

		default:
			break;
		}
		
		// Create URL and set the variable
		try {
			this.testURL = new URL(path);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
  	  	return this.testURL;
	}
	
	/**
	 * Load Environment properties
	 * @return	Return Property file object
	 */
	public Properties loadEnvironmentProperties() {
		Properties properties = Variables.environmentProperties;
		return properties;
	}

	public ExtentTest getTestScript() {
		return testScript;
	}

	public void setTestScript(ExtentTest testScript) {
		this.testScript = testScript;
	}
}
