package com.test.login;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import care.automation.BaseTest;
import care.page.desktop.EnrollmentPage;
import care.page.desktop.HomePage;
import care.util.EmailTestReport;

public class TestScripts extends BaseTest {
	
	@BeforeMethod(alwaysRun=true)
	public void initializeDriver(ITestContext context, Method m) throws MalformedURLException {
		super.initializeDriver(context, m, true, false);
	}
	
	@Test
	public void seekerEnrollment() throws IOException{
		// Open Home Page
		HomePage homePage = new HomePage(d);
//		// Navigate to Enrollment Page
//		EnrollmentPage enrollmentPage = homePage.navigateToEnrollmentPage();
//		// Register
//		enrollmentPage.completeRegistration();
	}
}
