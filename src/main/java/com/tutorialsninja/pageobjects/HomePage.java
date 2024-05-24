package com.tutorialsninja.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;
	public HomePage(WebDriver driver) {
		driver = this.driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//span[contains(text(), 'My Account')]")
	private WebElement myAccountDropMenuButton;
	
	@FindBy(xpath="//a[contains(text(), 'Login')]")
	private WebElement loginButton;
	
	public void clickOnMyAccountDropMenuButton() {
		myAccountDropMenuButton.click();
	}
	
	public void clickOnLoginButton() {
		loginButton.click();
	}

}
