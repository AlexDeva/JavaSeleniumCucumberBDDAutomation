# Selenium + Cucumber BDD + Java Automation Framework Prompts

A consolidated guide of modular, production-grade prompts to generate and set up a UI Test Automation Framework using **Java 17+**, **Selenium 4**, **Cucumber 7+**, **JUnit 5**, and **Maven**.

---

### 1. Framework Architecture & Project Setup Prompt
> **Prompt:**  
> "Act as a Lead Test Automation Architect. Design a production-grade UI Test Automation framework project structure using Java 17+, Selenium 4, Cucumber 7+, Maven, and JUnit 5.  
>  
> Please provide:  
> 1. A clean standard Maven folder directory tree structure (`src/main/java`, `src/test/java`, `src/test/resources`).  
> 2. A complete `pom.xml` configured with dependencies for Selenium Java, Cucumber Java, Cucumber JUnit Platform Engine, PicoContainer (for dependency injection), ExtentReports/Allure, and necessary Maven plugins (`maven-compiler-plugin`, `maven-surefire-plugin` configured for parallel execution).  
> 3. A `junit-platform.properties` file setup for parallel execution settings."

---

### 2. Thread-Safe Driver Management Prompt
> **Prompt:**  
> "Write a thread-safe `DriverManager` and `DriverFactory` class in Java using `ThreadLocal<WebDriver>`.  
>  
> Requirements:  
> - Support Chrome, Firefox, and Edge browsers via dynamic parameters or configuration properties.  
> - Use Selenium 4 `WebDriverManager` / modern Selenium 4 driver auto-management.  
> - Include methods for `getDriver()`, `initDriver(String browser)`, and `quitDriver()` to guarantee clean thread isolation during parallel test execution.  
> - Add headless browser options and standard ChromeOptions (e.g., `--disable-notifications`, `--start-maximized`)."

---

### 3. Page Object Model (POM) & Base Page Prompt
> **Prompt:**  
> "Create a robust Base Page object (`BasePage.java`) and a sample Page Object class (`LoginPage.java`) for a Selenium 4 framework.  
>  
> Requirements:  
> - The `BasePage` must encapsulate Selenium 4 `WebDriverWait` and `ExpectedConditions` (Explicit Waits only—avoid hardcoded sleep or mixing with implicit waits).  
> - Provide reusable wrapper methods for standard interactions: `click()`, `sendKeys()`, `getText()`, `waitForVisibility()`, and JS click.  
> - Implement locators using `By` objects inside `LoginPage.java` keeping encapsulation intact."

---

### 4. Gherkin Feature File & Step Definitions Prompt
> **Prompt:**  
> "Create a Cucumber BDD feature file and matching Step Definition class for an e-commerce login and checkout workflow.  
>  
> Requirements:  
> - **Feature File (`checkout.feature`):** Write readable Gherkin with `@smoke` and `@regression` tags. Include a `Scenario Outline` with `Examples` for positive/negative cases, and a scenario utilizing a Cucumber `DataTable`.  
> - **Step Definition (`CheckoutSteps.java`):** Implement the steps using clean Java code. Use Cucumber PicoContainer dependency injection to pass page objects/state between step files cleanly."

---

### 5. Hooks, Utilities & Failed Test Screenshots Prompt
> **Prompt:**  
> "Write a Cucumber `Hooks.java` class with `@Before` and `@After` annotations.  
>  
> Requirements:  
> - `@Before`: Read environment config (`config.properties`) and initialize the browser thread via `DriverManager`.  
> - `@After`: Check if `scenario.isFailed()`. If failed, capture a full-page screenshot using Selenium `TakesScreenshot` and attach it directly to the Cucumber report scenario (`scenario.attach(...)`).  
> - Ensure `quitDriver()` is called safely in a `finally` block to prevent lingering browser processes."

---

### 6. Test Runner & Reporting Prompt
> **Prompt:**  
> "Provide the code and configuration for running Cucumber tests using JUnit 5 Suite / Cucumber Engine.  
>  
> Requirements:  
> 1. A JUnit 5 `TestRunner.java` annotated with `@Suite`, `@SelectClasspathResource`, and configuration properties for feature paths, step glue paths, and tag filtering.  
> 2. Configure reporting plugins: standard Cucumber HTML, JSON, and ExtentReports or Masterthought Cucumber reporting.  
> 3. Provide the Maven terminal commands (`mvn test ...`) to execute tests by specific tags or target environments."