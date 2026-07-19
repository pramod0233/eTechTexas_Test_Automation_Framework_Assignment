package com.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.constants.Browser;

public abstract class BrowserUtility {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private Logger logger = LoggerUtility.getLogger(this.getClass());
	private WebDriverWait wait;

	public WebDriver getDriver() {
		return driver.get();
	}

	public BrowserUtility(WebDriver driver) {
		super();
		this.driver.set(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(30L));
	}

	public BrowserUtility(Browser browserName, boolean isHeadless) {

		logger.info("Lounching Browser for " + browserName);

		if (browserName == Browser.CHROME) {

			if (isHeadless) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				options.addArguments("--window-size=1920,1080");
				options.addArguments("disable-gpu");
				driver.set(new ChromeDriver(options));
				wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

			} else {
				ChromeOptions options = new ChromeOptions();
				driver.set(new ChromeDriver(options));
				wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));
			}
		} else if (browserName == Browser.EDGE) {

			if (isHeadless) {
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--headless");
				options.addArguments("--window-size=1920,1080");
				options.addArguments("disable-gpu");
				driver.set(new EdgeDriver(options));
				wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));
			} else {
				driver.set(new EdgeDriver());
				wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));
			}

		} else if (browserName == Browser.FIREFOX) {

			if (isHeadless) {
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--headless");
				options.addArguments("disable-gpu");
				options.addArguments("--window-size=1920,1080");
				driver.set(new FirefoxDriver(options));
				wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));
			} else {
				driver.set(new FirefoxDriver());
				wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));
			}

		} else {

			logger.error("Invalid Browser Name...!!! Please select browser name - Chrome, Edge, Firefox only");
			System.err.println("Invalid Browser Name...!!! Please select browser name - Chrome, Edge, Firefox only");
		}
	}

	public void goToWebsite(String url) {
		logger.info("Visiting the website " + url);
		driver.get().get(url);
	}

	public void maximizeWindow() {
		logger.info("Maximize the browser window");
		driver.get().manage().window().maximize();
	}

	public boolean isElementDisplayed(By locator) {
		logger.info("Checking if element is displayed: " + locator);
		boolean isDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
		logger.info("Element display status: " + isDisplayed);
		return isDisplayed;
	}

	public void switchToFrame(By frameLocator) {
		logger.info("Switching to iframe using locator: " + frameLocator);
		WebElement frame = wait.until(ExpectedConditions.visibilityOfElementLocated(frameLocator));
		driver.get().switchTo().frame(frame);
	}

	public void openNewBrowserTab(String url) {
		logger.info("Opening new browser tab.");
		driver.get().switchTo().newWindow(WindowType.TAB);
		logger.info("Navigating to URL: " + url);
		driver.get().get(url);
		logger.info("Successfully opened URL in new tab.");
	}

	public void clickOn(By locator) {

		logger.info("Finding element with the locator " + locator);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		logger.info("Element found and now performing click");
		element.click();
	}

	public void clickOnCheckBox(By locator) {

		logger.info("Finding element with the locator " + locator);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		logger.info("Element found and now performing click");
		element.click();
	}

	public void clickOn(WebElement element) {

		logger.info("Element found and now performing click");
		element.click();
	}

	public void enterText(By locator, String textToEnter) {

		logger.info("Finding element with the locator " + locator);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		logger.info("Element found and now entering the text: " + textToEnter);
		element.sendKeys(textToEnter);
	}

	public void clearText(By textBoxlocator) {

		logger.info("Finding element with the locator " + textBoxlocator);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(textBoxlocator));
		logger.info("Element found and now clearing the text box fild");
		element.clear();
	}

	public void enterKey(By locator, Keys keysToEnter) {

		logger.info("Finding element with the locator " + locator);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		logger.info("Element found and now entering the keys: " + keysToEnter);
		element.sendKeys(keysToEnter);
	}

	public String getVisibleText(By locator) {

		logger.info("Finding all elements with the locator " + locator);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		logger.info("Element found and now returning the visible text: " + element.getText());
		return element.getText();
	}

	public List<String> getAllVisibleText(By locator) {

		logger.info("Finding element with the locator " + locator);
		List<WebElement> elementList = driver.get().findElements(locator);
		logger.info("Elements found and now printing the list of elements");

		List<String> visibleTextList = new ArrayList<String>();
		for (WebElement element : elementList) {

			System.out.println(getVisibleText(element));
			visibleTextList.add(getVisibleText(element));
		}
		return visibleTextList;

	}

	public void selectRadioButton(By locator, int indexNumber) {

		logger.info("Finding element with the locator " + locator);
		List<WebElement> elementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		logger.info("Total Radio button elements found: " + elementList.size());

		WebElement radioButton = elementList.get(indexNumber);

		if (!radioButton.isSelected()) {
			radioButton.click();
			logger.info("Radio button selected at index: " + indexNumber);
		} else {
			logger.info("Radio button at index " + indexNumber + " is already selected.");
		}

	}

	public WebElement getElement(By locator) {
		logger.info("Finding element with the locator " + locator);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		logger.info("Elements found and now printing the list of elements");

		return element;
	}

	public List<WebElement> getAllElements(By locator) {

		logger.info("Finding element with the locator " + locator);
		List<WebElement> elementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		logger.info("Elements found and now printing the list of elements");

		return elementList;

	}

	public String getVisibleText(WebElement element) {

		logger.info("Returning the visible text: " + element.getText());
		return element.getText();
	}

	public String getCurrentTab() {
		String currentTab = driver.get().getWindowHandle();
		logger.info("Current browser tab handle: " + currentTab);
		return currentTab;
	}

	public void selectFromDropDown(By dropDownLocator, String optionToSelect) {

		logger.info("Finding all elements with the locator " + dropDownLocator);
		WebElement element = driver.get().findElement(dropDownLocator);
		Select select = new Select(element);
		logger.info("Option to select " + optionToSelect);
		select.selectByVisibleText(optionToSelect);

	}

	public void selectFromDropDownByValue(By dropDownLocator, String optionValue) {

		logger.info("Finding all elements with the locator " + dropDownLocator);
		WebElement element = driver.get().findElement(dropDownLocator);
		Select select = new Select(element);
		logger.info("Option to select " + optionValue);
		select.selectByValue(optionValue);

	}

	public void selectCheckbox(By locator) {

		logger.info("Finding element with the locator " + locator);
		WebElement element = driver.get().findElement(locator);
		logger.info("Element found and now clickng on the element.");
		if (!element.isSelected()) {

			element.click();
			logger.info("Checkbox is selected.");

		} else {

			logger.info("Checkbox is showing already selected.");
		}

	}

	public void closeTheBrowser() {

		logger.info("Closing the browser window");
		driver.get().quit();
		logger.info("Browser window is closed");
	}

	public String takeScreenShot(String testName) {

		TakesScreenshot screenshot = (TakesScreenshot) driver.get();
		File screenshotData = screenshot.getScreenshotAs(OutputType.FILE);
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = simpleDateFormat.format(date);
		String filePath = "./screenshots/" + testName + " - " + timestamp + ".png";
		File screenshotFile = new File(filePath);
		try {
			FileUtils.copyFile(screenshotData, screenshotFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filePath;
	}

	public void jsClick(By locator) {

		logger.info("Finding element for JavaScript click: " + locator);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		logger.info("Scrolling element into view.");
		((JavascriptExecutor) driver.get()).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
		logger.info("Performing JavaScript click.");
		((JavascriptExecutor) driver.get()).executeScript("arguments[0].click();", element);
		logger.info("JavaScript click performed successfully.");
	}

}
