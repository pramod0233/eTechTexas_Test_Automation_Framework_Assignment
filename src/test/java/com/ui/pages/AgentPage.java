package com.ui.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utility.BrowserUtility;

public class AgentPage extends BrowserUtility {

	public AgentPage(WebDriver driver) {
		super(driver);

	}

	private static final By WELCOME_SCREEN_LOCATOR = By.xpath("//div[contains(@class,'welcome')]");
	private static final By PROFILE_DROPDOWN_LOCATOR = By.id("profiledd");
	private static final By LOGOUT_LINK_LOCATOR = By.xpath("//ul[@id='dropdownChat']//a[text()='Logout']");
	private static final By LOGOUT_REASON_DROPDOWN_LOCATOR = By.id("logoutReason");
	private static final By LOGOUT_REASON_OPTION_XPATH_TEMPLATE = By.xpath("//span[text()='Tea Breaus']");
	private static final By OK_BUTTON_LOCATOR = By.xpath("//div[@class='modal-body modal-height']//input[@value='OK']");
	private static final By HANGUP_CHAT_CROSS_BUTTON_LOCATOR = By.xpath("//a[@data-tooltip=\"Hang-up Chat\"]/em");
	private static final By HANGUP_CHAT_BUTTON_LOCATOR = By
			.xpath("//div[@class='input-field col-md-12']/input[@type=\"submit\"]");
	private static final By DISPOSITION_DROPDOWN_LOCATOR = By
			.xpath("//strong[normalize-space()='Disposition']/ancestor::p/following-sibling::ng-select");
	private static final By LIST_BOX_LOCATOR = By.xpath("//div[@role=\"option\"]");
	private static final By OPERATOR_SURVEY_CHECKBOX_LOCATOR = By
			.xpath("//input[@type=\"checkbox\"]/parent::p[@class='d-flex ng-star-inserted']");
	private static final By SUBMIT_BUTTON_LOCATOR = By
			.xpath("//div[contains(@class , 'ng-star-inserted')]/input[@value=\"Submit\"]");
	private static final By ACTIVE_CHAT_LOCATOR = By.cssSelector("div.chatlistgrid.active-chat-new");
	private static final By MESSAGE_TEXT_BOX_LOCATOR = By.id("msg");
	private static final By SEND_MESSAGE_LOCATOR = By.xpath("//input[@value=\"Send\" and @class=\"submitbtn\"]");
	private static final By AGENT_MESSAGES_LOCATOR = By.xpath(
			"//div[@id='messageContainer']//strong[contains(normalize-space(),'Agent1')]/following-sibling::div/span");

	private static final By VISITOR_MESSAGES_LOCATOR = By.xpath(
			"//div[@id='messageContainer']//strong[contains(normalize-space(),'test11')]/following-sibling::div/span");

	public boolean isWelcomeScreenVisible() {
		return isElementDisplayed(WELCOME_SCREEN_LOCATOR);
	}

	public LoginPage logoutFromTheApplication() {
		clickOn(PROFILE_DROPDOWN_LOCATOR);
		clickOn(LOGOUT_LINK_LOCATOR);
		clickOn(LOGOUT_REASON_DROPDOWN_LOCATOR);
		clickOn(LOGOUT_REASON_OPTION_XPATH_TEMPLATE);
		clickOn(OK_BUTTON_LOCATOR);

		return new LoginPage(getDriver());
	}

	public VisitorPage switchToVisitorPage(String url) {
		openNewBrowserTab(url);
		return new VisitorPage(getDriver());
	}

	public AgentPage closeCurrentChatSession() {

		clickOn(HANGUP_CHAT_CROSS_BUTTON_LOCATOR);
		clickOn(HANGUP_CHAT_BUTTON_LOCATOR);
		clickOn(DISPOSITION_DROPDOWN_LOCATOR);
		List<WebElement> dropdownList = getAllElements(LIST_BOX_LOCATOR);
		clickOn(dropdownList.get(1));
		List<WebElement> checkBoxList = getAllElements(OPERATOR_SURVEY_CHECKBOX_LOCATOR);
		clickOn(checkBoxList.get(1));
		clickOn(SUBMIT_BUTTON_LOCATOR);

		return this;
	}

	public AgentPage acceptChat() {
		clickOn(ACTIVE_CHAT_LOCATOR);
		return this;
	}

	public AgentPage sendMessageFromAgent(String message) {

		enterText(MESSAGE_TEXT_BOX_LOCATOR, message);
		clickOn(SEND_MESSAGE_LOCATOR);

		waitForAgentMessage(message);
		return this;
	}

	public List<String> getAgentMessages() {

		List<WebElement> elements = getAllElements(AGENT_MESSAGES_LOCATOR);

		List<String> messages = new ArrayList<>();

		for (WebElement element : elements) {

			String text = element.getText().trim();

			if (!text.equalsIgnoreCase("Agent1 joined the conversation") && !text.equalsIgnoreCase("greettings")) {

				messages.add(text);
			}
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

	private AgentPage waitForAgentMessage(String expectedMessage) {

		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

		wait.until(driver -> {

			List<WebElement> messages = getAllElements(AGENT_MESSAGES_LOCATOR);

			if (messages.isEmpty()) {
				return false;
			}

			return messages.get(messages.size() - 1).getText().trim().equals(expectedMessage);
		});

		return this;
	}

}
