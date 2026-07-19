package com.ui.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utility.BrowserUtility;

public class VisitorPage extends BrowserUtility {

	public VisitorPage(WebDriver driver) {
		super(driver);

	}

	private static final By ONLINE_BUTTON_LOCATOR = By.id("Hlink");

	private static final By VISITOR_NAME_LOCATOR = By.xpath("//label[normalize-space()='Name']/following::input[1]");

	private static final By VISITOR_EMAIL_LOCATOR = By.xpath("//label[normalize-space()='Name']/following::input[2]");

	private static final By VISITOR_SIGNINPAGE_SUBMIT_BUTTON_LOCATOR = By
			.xpath("//input[@class=\"submitbtn btn btnspace\"]");

	private static final By CHAT_TEXTBOX_LOCATOR = By.id("msg");

	private static final By CHAT_IFRAME_LOCATOR = By.id("chatpopup");

	private static final By MESSAGE_SEND_BUTTON_LOCATOR = By.xpath("//i[contains(@class,'chatsendbutton')]");

	private static final By AGENT_MESSAGES_LOCATOR = By
			.xpath("//div[@id='chat']//strong[contains(.,'Agent1')]/following-sibling::span/div/span");
	private static final By VISITOR_MESSAGES_LOCATOR = By
			.xpath("//div[@id='chat']//strong[contains(.,'You')]/following-sibling::span/div/span");

	private static final By FEEDBACK_FORM_NAME_TEXTBOX_LOCATOR = By
			.xpath("//label[normalize-space()='Name']/following-sibling::input");

	private static final By FEEDBACK_FORM_EMAIL_TEXTBOX_LOCATOR = By
			.xpath("//label[normalize-space()='Email']/following-sibling::input");

	private static final By FEEDBACK_FORM_ADDRESS_TEXTAREA_LOCATOR = By
			.xpath("//label[normalize-space()='Address']/following-sibling::textarea");

	private static final By FEEDBACK_FORM_SUBMIT_BUTTON_LOCATOR = By.cssSelector("input.submitbtn");

	private static final By FEEDBACK_FORM_TEXT_CHECKBOX_LOCATOR = By
			.xpath("//p[label[normalize-space()='This is for the text']]/input");

	private static final By FEEDBACK_FORM_FIVE_STAR_LOCATOR = By.xpath("//div[@class='stars']//input[@value='5']");

	private static final By FEEDBACK_FORM_ANSWER_TEXTBOX_LOCATOR = By.xpath(
			"//label[contains(normalize-space(),'This is great to the give the answer')]/following-sibling::input");

	private static final By FEEDBACK_SUBMITTED_FORM_LOCATOR = By.className("submited-form");

	public VisitorPage visitorSignup(String name, String email) {
		clickOn(ONLINE_BUTTON_LOCATOR);
		switchToFrame(CHAT_IFRAME_LOCATOR);
		enterText(VISITOR_NAME_LOCATOR, name);
		enterText(VISITOR_EMAIL_LOCATOR, email);
		clickOn(VISITOR_SIGNINPAGE_SUBMIT_BUTTON_LOCATOR);
		return this;
	}

	public boolean isOnlineButtonVisible() {
		return isElementDisplayed(ONLINE_BUTTON_LOCATOR);

	}

	public boolean isChatWindowDisplayed() {
		return isElementDisplayed(CHAT_TEXTBOX_LOCATOR);
	}

	public VisitorPage switchToChatFrame() {
		switchToFrame(CHAT_IFRAME_LOCATOR);
		return this;
	}

	public VisitorPage sendMessageFromVisitor(String message) {
		switchToChatFrame();
		enterText(CHAT_TEXTBOX_LOCATOR, message);
		clickOn(MESSAGE_SEND_BUTTON_LOCATOR);

		waitForVisitorMessage(message);
		return this;
	}

	public List<String> getAgentMessages() {

		List<WebElement> elements = getAllElements(AGENT_MESSAGES_LOCATOR);

		List<String> messages = new ArrayList<>();

		for (WebElement element : elements) {

			messages.add(element.getText().trim());

		}

		return messages;
	}

	public List<String> getVisitorMessages() {

		List<WebElement> elements = getAllElements(VISITOR_MESSAGES_LOCATOR);

		List<String> messages = new ArrayList<>();

		for (WebElement element : elements) {

			messages.add(element.getText().trim());

		}

		return messages;
	}

	public String submitFeedbackForm(String name, String email, String address, String answer) {

		switchToChatFrame();

		enterText(FEEDBACK_FORM_NAME_TEXTBOX_LOCATOR, name);
		enterText(FEEDBACK_FORM_EMAIL_TEXTBOX_LOCATOR, email);
		enterText(FEEDBACK_FORM_ADDRESS_TEXTAREA_LOCATOR, address);
		jsClick(FEEDBACK_FORM_TEXT_CHECKBOX_LOCATOR);
		enterText(FEEDBACK_FORM_ANSWER_TEXTBOX_LOCATOR, answer);
		jsClick(FEEDBACK_FORM_FIVE_STAR_LOCATOR);
		jsClick(FEEDBACK_FORM_SUBMIT_BUTTON_LOCATOR);

		return getVisibleText(FEEDBACK_SUBMITTED_FORM_LOCATOR);

	}


	private VisitorPage waitForVisitorMessage(String expectedMessage) {

		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

		wait.until(driver -> {

			List<WebElement> messages = getAllElements(VISITOR_MESSAGES_LOCATOR);

			if (messages.isEmpty()) {
				return false;
			}

			return messages.get(messages.size() - 1).getText().trim().equals(expectedMessage);
		});

		return this;
	}

}