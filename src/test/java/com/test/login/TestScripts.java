package com.test.login;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import care.automation.BaseTest;
import care.page.desktop.HomePage;

public class TestScripts extends BaseTest {
	
	@BeforeMethod(alwaysRun=true)
	public void initializeDriver(ITestContext context, Method m) throws MalformedURLException {
		super.initializeDriver(context, m, true, false);
	}
	
	@Test
	public void testCase1() throws IOException{
		HomePage homePage = new HomePage(d);
	}
	
	@Test
	public void testCase2(){
		HomePage homePage = new HomePage(d);
	}
	
	@Test
	public void testCase3(){
		HomePage homePage = new HomePage(d);
	}
}
