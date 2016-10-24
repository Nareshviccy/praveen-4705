package care.variables;

import java.util.Properties;

import org.openqa.selenium.By;

public class Variables {
	
	/** Environment to be tested */
	public static final String environmentName 	= "uat2";
	/** Default Browser to choose */
	public static final String browserName		= "Firefox";
	/** Create environment properties file*/
	public static Properties environmentProperties = new Properties();
	
	/** SauceLabs User Name */
	public static final String sauceUsername	= "";
	/** SauceLabs Access Key */
	public static final String sauceAccessKey	= "";
	
	/** Sauce's supported browser resolutions
	 *  Verify here the supported resolutions: 
	 *  https://docs.saucelabs.com/reference/test-configuration/#specifying-the-screen-resolution
	 */
	public static final String sauceBrowserResolution	= "1024x768";
	/** Default Platform to choose for SauceLabs when nothing is specified */
	public static final String saucePlatform			= "Windows 8";
	/** Default Browser to choose for SauceLabs when nothing is specified */
	public static final String sauceBrowser				= "Firefox";
	/** Default Browser Version to choose for SauceLabs when nothing is specified */
	public static final String sauceBrowserVersion		= "35";
	
	/*
	 * General Information
	 */
	
	public static final String gen_Password				= "letmein1";
	
	/*
	 * Home Page Elements
	 */
	
	public static final By homePage_link_JoinNow		= By.cssSelector("a.joinNowLink");
	
	/* **************************
	 * Enrollment Page Elements
	 * **************************/
	
	public static final By enrollmentPage_tb_FirstName			= By.cssSelector("input#firstName");
	public static final By enrollmentPage_tb_LastName			= By.cssSelector("input#lastName");
	public static final By enrollmentPage_tb_Address			= By.cssSelector("input#addressLine1");
	public static final By enrollmentPage_tb_Email				= By.cssSelector("input#email");
	public static final By enrollmentPage_tb_Password			= By.cssSelector("input#password");
	public static final By enrollmentPage_btn_Join				= By.cssSelector("input#submitButton");

}
