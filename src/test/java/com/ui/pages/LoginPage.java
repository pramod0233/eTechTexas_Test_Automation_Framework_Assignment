package com.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.constants.Browser;
import com.constants.Environments;
import com.utility.BrowserUtility;
import com.utility.PropertiesUtil;

public class LoginPage extends BrowserUtility {

	private static final By ORGANIZATION_ID_TEXT_BOX_LOCATOR = By.id("OrgID");
	private static final By USERNAME_TEXT_BOX_LOCATOR = By.id("Username");
	private static final By PAASWORD_TEXT_BOX_LOCATOR = By.id("txtPassword");
	private static final By LOGIN_BUTTON_LOCATOR = By.xpath("//input[@value='Log in']");
	private static final By LOGIN_SCREEN_LOCATOR = By.cssSelector(".loginscreen");

	public LoginPage(Browser browserName, boolean isHeadless, Environments environments) {
		super(browserName, isHeadless);

		goToWebsite(PropertiesUtil.readProperty(environments, "URL"));

	}

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void quitBrowser() {
		closeTheBrowser();
	}

	public AgentPage doLoginWithValidCredentials(String ID, String Username, String Password) {

		enterText(ORGANIZATION_ID_TEXT_BOX_LOCATOR, ID);
		enterText(USERNAME_TEXT_BOX_LOCATOR, Username);
		enterText(PAASWORD_TEXT_BOX_LOCATOR, Password);
		clickOn(LOGIN_BUTTON_LOCATOR);

		return new AgentPage(getDriver());
	}

	public boolean isLoginScreenVisible() {
		return isElementDisplayed(LOGIN_SCREEN_LOCATOR);
	}
}
