package com.ui.dataproviders;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.utility.ExcelUtility;

public class LoginDataProvider {

	@DataProvider(name = "loginTestData")
	public Object[][] loginDataProvider() throws IOException {
		return ExcelUtility.getTestData("LogInTestData");
	}

}
