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
import com.tutorialsninja.pageobjects.HomePage;
import com.tutorialsninja.util.DataUtil;
import com.tutorialsninja.util.MyXLSReader;

public class Login extends Base{

	WebDriver driver;
	MyXLSReader excelReader;
	
	@AfterMethod
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}else if (driver == null) {
			System.out.println("Driver is null...!!!");
		}
	}
	
	@Test (dataProvider = "dataSupplier")
	public void validInvalidLoginTest(HashMap<String, String>hMap) {
		if (!DataUtil.isRunnable(excelReader, "LoginTest", "Testcases")|| hMap.get("Runmode").equals("N")) {
			throw new SkipException("Testcase is not runnable...!");
		}
		
		driver = openBrowserAndRunApplicationURL(hMap.get("Browser"));
	
		
		driver.findElement(By.xpath("//span[contains(text(), 'My Account')]")).click();
		driver.findElement(By.xpath("//a[contains(text(), 'Login')]")).click();
		driver.findElement(By.id("input-email")).sendKeys(hMap.get("Username"));
		driver.findElement(By.id("input-password")).sendKeys(hMap.get("Password"));
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		String expectedResult = hMap.get("ExpectedResult");
		boolean convertedExpectedResult = false;
		if(expectedResult.equalsIgnoreCase("Success")) {
			convertedExpectedResult = true;
		}else if (expectedResult.equalsIgnoreCase("Fail")) {
			convertedExpectedResult = false;
		}
		
		boolean actualResult;
		try {
		actualResult = driver.findElement(By.xpath("//div[@id='content']/h2")).isDisplayed();
		}catch (Throwable e) {
			// TODO: handle exception
			actualResult = false;
		}
		
		System.out.println("Login Successfully : "+ actualResult);
		Assert.assertEquals(actualResult, convertedExpectedResult);
		     
		
	}
	@DataProvider
	public Object dataSupplier() {
		Object data = null;
		try {
		excelReader = new MyXLSReader("src\\test\\resources\\DemoExeData01.xlsx");
		data = DataUtil.getTestData(excelReader, "LoginTest", "Data");
		}catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return data;
	}
}
