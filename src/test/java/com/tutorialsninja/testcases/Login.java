package com.tutorialsninja.testcases;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tutorialsninja.base.Base;
import com.tutorialsninja.util.DataUtil;
import com.tutorialsninja.util.MyXLSReader;

import pageobjects.AccountPage;
import pageobjects.HomePage;
import pageobjects.LoginPage;

public class Login extends Base {

	WebDriver driver;
	MyXLSReader excelReader;

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		} else if (driver == null) {
			System.out.println("Driver is null...!!!");
		}
	}

	@Test(dataProvider = "dataSupplier")
	public void validInvalidLoginTest(HashMap<String, String> hMap) {
		if (!DataUtil.isRunnable(excelReader, "LoginTest", "Testcases") || hMap.get("Runmode").equals("N")) {
			throw new SkipException("Testcase is not runnable...!");
		}

		driver = openBrowserAndRunApplicationURL(hMap.get("Browser"));

		HomePage homePage = new HomePage(driver);
		homePage.clickOnMyAccountDropMenu();
		LoginPage loginPage = homePage.selectLoginOption();
		loginPage.enterEmailAddress(hMap.get("Username"));
		loginPage.enterPassword(hMap.get("Password"));
		AccountPage accountPage = loginPage.clickOnLoginButton();

		String expectedResult = hMap.get("ExpectedResult");
		boolean convertedExpectedResult = false;
		if (expectedResult.equalsIgnoreCase("Success")) {
			convertedExpectedResult = true;
		} else if (expectedResult.equalsIgnoreCase("Fail")) {
			convertedExpectedResult = false;
		}


		System.out.println("Login Successfully : " + accountPage.verifyLoginStatus());
		Assert.assertEquals(accountPage.verifyLoginStatus(), convertedExpectedResult);

	}

	@DataProvider
	public Object dataSupplier() {
		Object data = null;
		try {
			excelReader = new MyXLSReader("src\\test\\resources\\DemoExeData01.xlsx");
			data = DataUtil.getTestData(excelReader, "LoginTest", "Data");
		} catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return data;
	}
}
