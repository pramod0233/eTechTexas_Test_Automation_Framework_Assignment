package com.ui.listerners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.constants.Environments;
import com.utility.PropertiesUtil;

public class MyRetryAnalyzer implements IRetryAnalyzer {

	private static final String ENVIRONMENT = System.getProperty("env", "STAGE");

	private static final Environments ENV = Environments.valueOf(ENVIRONMENT.toUpperCase());

	private static final int MAX_NUMBER_OF_ATTEMPTS = Integer
			.parseInt(PropertiesUtil.readProperty(ENV, "MAX_NUMBER_OF_ATTEMPTS"));

	private static int currentAttempt = 1;

	@Override
	public boolean retry(ITestResult result) {

		if (currentAttempt <= MAX_NUMBER_OF_ATTEMPTS) {

			currentAttempt++;
			return true;
		}
		return false;
	}

}
