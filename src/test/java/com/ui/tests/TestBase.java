package com.ui.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.constants.Browser;
import com.constants.Environments;
import com.ui.pages.LoginPage;
import com.utility.BrowserUtility;

public abstract class TestBase {

	protected LoginPage loginpage;

	@Parameters({ "env", "browser", "isHeadless" })
	@BeforeMethod(description = "Load the home page of the website")
	public void setup(@Optional("STAGE") String env, @Optional("chrome") String browser,
			@Optional("false") boolean isHeadless, ITestResult result) {

		loginpage = new LoginPage(Browser.valueOf(browser.toUpperCase()), isHeadless , Environments.valueOf(env.toUpperCase()));
	}

	@AfterMethod(description = "Tear down the browser")
	public void tearDown() {

		loginpage.quitBrowser();
	}

	public BrowserUtility getInstance() {
		return loginpage;
	}

}
