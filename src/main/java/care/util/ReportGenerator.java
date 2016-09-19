package care.util;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportGenerator {
	
	/*
	 * Create Extent Report Object
	 */
	public static ExtentReports reporter;
	public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
	
	/**
	 * Create Extent Report File
	 * @return	Returns Extent Report Object
	 */
	private synchronized static ExtentReports getExtentReport() {
		if (reporter == null) {
			
			// Create Extent Report Object
			reporter = new ExtentReports("ComplexReport.html", true, DisplayOrder.NEWEST_FIRST);
		}
		
		// Return Extent Report Object
		return reporter;
	}
	
	/**
	 * Start Extent Test
	 * @param testName
	 * @param testDescription
	 * @return	Return Extent Test Object
	 */
	public synchronized static ExtentTest getTest(String testName, String testDescription) {

		// if this test has already been created return
		if (!nameToTestMap.containsKey(testName)) {
			Long threadID = Thread.currentThread().getId();
			ExtentTest test = getExtentReport().startTest(testName, testDescription);
			nameToTestMap.put(testName, test);
			threadToExtentTestMap.put(threadID, testName);
		}
		
		// Return Extent Test Object
		return nameToTestMap.get(testName);
	}
	
	/**
	 * Start Extent Test
	 * @param testName
	 * @return	Return Extent Test Object
	 */
	public synchronized static ExtentTest getTest(String testName) {
		return getTest(testName, "");
	}
	
	public synchronized static ExtentTest getTest() {
		Long threadID = Thread.currentThread().getId();

		if (threadToExtentTestMap.containsKey(threadID)) {
			String testName = threadToExtentTestMap.get(threadID);
			return nameToTestMap.get(testName);
		}	
		//system log, this shouldnt happen but in this crazy times if it did happen log it.
		return null;
	}
	
	/**
	 *  Add Step Info
	 * @param info	info of step
	 */
	public synchronized static void stepInfo(String info){
		getTest().log(LogStatus.INFO, info);
	}
	
	/**
	 * Add Passed step
	 * @param info
	 */
	public synchronized static void stepPassed(String info){
		getTest().log(LogStatus.PASS, info);
	}
	
	/**
	 * Add Warning step
	 * @param info
	 */
	public synchronized static void stepWarning(String info){
		getTest().log(LogStatus.WARNING, info);
	}
	
	/**
	 * Add failed step
	 * @param info
	 */
	public synchronized static void stepFailed(String info){
		getTest().log(LogStatus.FAIL, info);
	}

	public synchronized static void closeTest(String testName) {

		if (!testName.isEmpty()) {
			ExtentTest test = getTest(testName);
			getExtentReport().endTest(test);
		}
	}

	public synchronized static void closeTest(ExtentTest test) {
		if (test != null) {
			getExtentReport().endTest(test);
		}
	}

	public synchronized static void closeTest()  {
		ExtentTest test = getTest();
		closeTest(test);
	}

	public synchronized static void closeReport() {
		if (reporter != null) {
			reporter.flush();
			reporter.close();
		}
	}
}
