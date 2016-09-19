package care.variables;

import java.util.Properties;

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

}
