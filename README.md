# eTechTexas UI Automation Framework

A robust UI Automation Framework developed using **Java, Selenium WebDriver, TestNG, Maven, Page Object Model (POM), Data-Driven Framework, Log4j2, and Extent Reports** to automate the Agent Portal and Visitor Chat scenarios.

---

# Project Objective

This framework automates the following scenarios:

- Agent Login & Logout
- Visitor Chat Flow
- End-to-End Chat Validation

The framework is designed following industry best practices with reusable components and maintainable code.

---

# Tech Stack

| Technology | Version |
|------------|----------|
| Java | 17 |
| Selenium WebDriver | Latest |
| TestNG | Latest |
| Maven | Build Tool |
| Log4j2 | Logging |
| Extent Reports | Reporting |
| Apache POI | Excel Data Handling |

---

# Framework Design

The framework is developed using:

- ✅ Page Object Model (POM)
- ✅ Data Driven Framework
- ✅ Data Provider (TestNG)
- ✅ Reusable Utility Methods
- ✅ Explicit Waits
- ✅ Cross Browser Support
- ✅ Logging using Log4j2
- ✅ Extent Report
- ✅ Maven Project Structure
- ✅ Exception Handling
- ✅ Retry Mechanism
- ✅ Test Listeners
- ✅ Clean and Maintainable Code

---

# Project Structure

```
src/test/java
├── com.constants
├── com.ui.dataproviders
├── com.ui.listeners
├── com.ui.pages
├── com.ui.tests
└── com.utility
```

# Design Pattern Used

## Page Object Model (POM)

Each web page has its own Java class containing:

- Web Elements
- Page Actions
- Business Methods

Example:

```
LoginPage.java

AgentPage.java

VisitorPage.java
```

---

# Data Driven Framework

The framework follows a Data-Driven approach.

Test data is separated from the test scripts.

Benefits:

- No hardcoded test data
- Easy maintenance
- Supports multiple datasets

---

# Utility Classes

Reusable utility classes have been created for:

- Browser Utility
- Excel Utility
- Extent Report Utility
- Logger Utility
- Properties Utility

These utilities help avoid code duplication.

---

# Logging

Log4j2 is integrated for execution logging.

Execution logs are generated inside:

```
logs/
```

---

# Reporting

Extent Reports are integrated.

After execution, HTML reports are generated showing:

- Passed Tests
- Failed Tests
- Skipped Tests
- Execution Time
- Screenshots (if configured)

---

# Test Scenarios Automated

## Scenario 1 - Agent Login & Logout

- Launch Agent Portal
- Login using valid credentials
- Verify successful login
- Verify Welcome Screen
- Logout
- Verify successful logout

---

## Scenario 2 - Visitor Chat Flow

- Login as Agent
- Open Visitor Chat in separate browser context
- Verify Online Chat button
- Fill Visitor Details
- Submit Chat Request
- Accept Chat Request
- Exchange Messages
- Verify Messages
- End Chat
- Submit Disposition
- Submit Visitor Feedback
- Verify Successful Feedback Submission

---

# Framework Features

- Page Object Model
- Data Driven Framework
- TestNG Data Provider
- Explicit Waits
- Reusable Methods
- Centralized Locators
- Retry Analyzer
- Test Listeners
- Logging
- Extent Reports
- Cross Browser Support
- Configuration through Properties File
- Maven Build Management

---

# Configuration

Application URLs and environment-specific values are maintained separately using:

```
PropertiesUtil.java
```

This allows framework execution without changing the source code.

---

# Browser Support

The framework supports:

- Google Chrome
- Microsoft Edge
- Mozilla Firefox

---

# Assertions

TestNG Assertions are used to validate:

- Login
- Logout
- Welcome Screen
- Online Chat Button
- Message Exchange
- Chat End
- Feedback Submission

---

# Wait Strategy

The framework uses **Explicit Waits**.

No hardcoded waits (`Thread.sleep()`) are used.

---

# Retry Mechanism

Implemented using:

```
MyRetryAnalyzer.java
```

Automatically retries failed test cases.

---

# Test Listener

Implemented using:

```
TestListener.java
```

Used for:

- Logging
- Report Generation
- Screenshot Capture (if configured)

---

# Running the Project

Clone the repository:

```bash
git clone <repository-url>
```

Navigate to project:

```bash
cd eTechTexas_Test_Automation_Framework
```

Run tests:

```bash
mvn clean test
```

---

# Test Reports

After execution, reports can be found in:

```
test-output/

or

ExtentReports/
```

(depending on framework configuration)

---

# Future Enhancements

- Jenkins CI/CD Integration
- GitHub Actions Pipeline
- Docker Support
- Parallel Test Execution
- Allure Reports
- Screenshot on Failure for all tests
- Email Report Integration

---

# Author

**Pramod Bansode**

QA Automation Engineer

---
