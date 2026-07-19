package com.ui.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ui.dataproviders.LoginDataProvider;
import com.ui.listerners.MyRetryAnalyzer;
import com.ui.pages.AgentPage;
import com.ui.pages.VisitorPage;

@Listeners(com.ui.listerners.TestListerner.class)
public class AgentVisitorChatFlowTest extends TestBase {

	private AgentPage agentPage;
	private VisitorPage visitorpage;

	@Test(description = "Scenario 1 – Agent Login & Logout", dataProvider = "loginTestData", dataProviderClass = LoginDataProvider.class, retryAnalyzer = MyRetryAnalyzer.class)
	public void testAgentLogInAndLogoutFunctionality(String ID, String Username, String Password) {

		// ==========================================
		// Login to the Application
		// ==========================================
		agentPage = loginpage.doLoginWithValidCredentials(ID, Username, Password);

		// ==========================================
		// Verify Welcome Screen is Displayed
		// ==========================================
		boolean isWelcomeScreenVisible = agentPage.isWelcomeScreenVisible();
		assertTrue(isWelcomeScreenVisible, "Welcome Screen Is Not Visible !!! ");

		// ==========================================
		// Logout from the Application
		// ==========================================
		loginpage = agentPage.logoutFromTheApplication();

		// ==========================================
		// Verify Login Screen is Displayed
		// ==========================================
		boolean isLoginScreenVisible = loginpage.isLoginScreenVisible();
		assertTrue(isLoginScreenVisible, "Login Screen Is Not Visible !!! ");

	}

	@Test(description = "Scenario 2 – Visitor Chat Flow", dataProvider = "loginTestData", dataProviderClass = LoginDataProvider.class, retryAnalyzer = MyRetryAnalyzer.class)
	public void testDataFlowBetweenAgentAndVisitorPage(String id, String username, String password) {

		// ==========================================
		// Login & Close Existing Chat
		// ==========================================

		agentPage = loginpage.doLoginWithValidCredentials(id, username, password);

		String agentTab = loginpage.getCurrentTab();

		// ==========================================
		// Open Visitor Chat
		// ==========================================

		visitorpage = agentPage.switchToVisitorPage("https://staging.enterice.com/testing/language21.html");

		assertTrue(visitorpage.isOnlineButtonVisible(), "Online button is not visible.");

		String visitorTab = loginpage.getCurrentTab();

		visitorpage.visitorSignup("test11", "test11@gmail.com");

		assertTrue(visitorpage.isChatWindowDisplayed(), "Chat window is not displayed.");

		// ==========================================
		// Accept Chat
		// ==========================================

		switchToAgent(agentTab);

		agentPage.acceptChat();

		// ==========================================
		// Test Data
		// ==========================================

		List<String> agentMessages = Arrays.asList("Hello How Can I Help You...", "Hello...", "Hi Am Agent");

		List<String> visitorMessages = Arrays.asList("Hi", "I am Visitor", "Nice To Meet You");

		// ==========================================
		// Conversation
		// ==========================================

		performConversation(agentTab, visitorTab, agentMessages, visitorMessages);

		// ==========================================
		// Verification
		// ==========================================

		verifyConversation(agentTab, visitorTab, agentMessages, visitorMessages);

		// ==========================================
		// Close Chat
		// ==========================================

		switchToAgent(agentTab);

		agentPage.closeCurrentChatSession();

		// ==========================================
		// Feedback Form
		// ==========================================

		switchToVisitor(visitorTab);

		String actualMessage = visitorpage.submitFeedbackForm("Test", "test@gmail.com", "TestAddress",
				"Automation Testing");

		assertEquals(actualMessage, "Thank you so much for your valuable feedback!",
				"Feedback success message mismatch.");
	}

	// ============================================================
	// Helper Methods
	// ============================================================

	private void switchToAgent(String agentTab) {
		loginpage.getDriver().switchTo().window(agentTab);
	}

	private void switchToVisitor(String visitorTab) {
		loginpage.getDriver().switchTo().window(visitorTab);
	}

	private void performConversation(String agentTab, String visitorTab, List<String> agentMessages,
			List<String> visitorMessages) {

		for (int i = 0; i < agentMessages.size(); i++) {

			switchToAgent(agentTab);
			agentPage.sendMessageFromAgent(agentMessages.get(i));

			switchToVisitor(visitorTab);
			visitorpage.sendMessageFromVisitor(visitorMessages.get(i));
		}
	}

	private void verifyConversation(String agentTab, String visitorTab, List<String> agentMessages,
			List<String> visitorMessages) {

		switchToAgent(agentTab);

		assertEquals(agentPage.getAgentMessages(), agentMessages, "Agent messages mismatch on Agent page.");

		assertEquals(agentPage.getVisitorMessages(), visitorMessages, "Visitor messages mismatch on Agent page.");

		switchToVisitor(visitorTab);

		visitorpage.switchToChatFrame();

		assertEquals(visitorpage.getAgentMessages(), agentMessages, "Agent messages mismatch on Visitor page.");

		assertEquals(visitorpage.getVisitorMessages(), visitorMessages, "Visitor messages mismatch on Visitor page.");
	}

}
