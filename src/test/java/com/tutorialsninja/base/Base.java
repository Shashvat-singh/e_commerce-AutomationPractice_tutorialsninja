package com.tutorialsninja.base;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
	WebDriver driver;
	Properties prop01;
	
	public WebDriver openBrowserAndRunApplicationURL(String browserName) {
		prop01 = new Properties();
		File file01 = new File("src\\test\\resources\\configuration.properties");
		try {
		FileInputStream fis01 = new FileInputStream(file01);
		prop01.load(fis01);
		}catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		if(browserName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}else if(browserName.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromiumdriver().setup();
			driver = new ChromeDriver();
		}else if(browserName.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}else {
			System.out.println("Browser Not Found...!!!");
		}
		driver.get(prop01.getProperty("url"));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(1000));
		return driver;
	}

}
