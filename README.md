# AutomationProject-Selenium-TestNG

A lightweight, scalable web UI automation framework built on **Selenium 4** and **TestNG**, following the **Page Object Model** pattern. It provides a structured base for writing reliable, data-driven tests with built-in retry logic, parallel execution, and HTML reporting — all configurable without touching test code.

---

## Key Features

- **Page Object Model (POM)** — Clean separation between test logic and UI interactions via page classes
- **Interface-driven design** — Interaction, Validation, Reporting, and Web actions are defined as interfaces and injected into pages through `BasePage`, making the contract explicit and implementations swappable
- **Data-driven testing** — Test data loaded from Excel (`.xlsx`) via Apache POI, mapped to test methods by name through a `@DataProvider`
- **Retry mechanism** — Failed tests are automatically retried once (configurable) with a full browser restart to avoid stale state, using a thread-safe `ThreadLocal` counter
- **Parallel execution** — Tests run concurrently at the `<test>` level via TestNG suite XML; each test class gets its own `DriverManager` instance
- **Extent HTML Reports** — Step-level pass/fail logging with screenshots captured automatically on each test method completion
- **Multi-browser support** — Chrome, Firefox, Edge, and Safari selectable via `config.properties` or Jenkins parameter
- **CI/CD ready** — `Jenkinsfile` included with parameterised browser and suite selection, dynamic config generation, and report publishing

---

## Prerequisites

| Tool | Version | Notes                          |
|---|---|--------------------------------|
| Java JDK | 17 | Required                       |
| Maven | 3.6+ | Required                       |
| Chrome / Firefox / Edge / Safari | Latest stable | At least one required          |
| IntelliJ IDEA (or any IDE) | Any | Recommended                    |
| Jenkins | 2.x+ | Only for CI/CD runs (Optional) |

---

## Project Structure

```
AutomationProject-Selenium-TestNG/
│
├── config/
│   └── config.properties            # Browser, URL, wait time settings
│
├── WebTestSuites/
│   ├── SampleSuite.xml              # Parallel suite: SampleWebTest + SampleNewWebTest
│   └── SampleRetrySuite.xml         # Retry suite: SampleRetryWebTest
│
├── src/
│   │
│   ├── main/java/webAutomation/
│   │   └── utilities/
│   │       ├── BaseManager.java         # Core lifecycle logic: beforeSuite, beforeClass, beforeMethod, testData
│   │       ├── BasePage.java            # Delegates all page actions to function classes
│   │       ├── ContextManager.java      # Holds WebDriver, waits, and ExtentTest per thread
│   │       ├── DriverManager.java       # Browser creation, quit, and retry reset
│   │       ├── ConfigurationManager.java# Reads config.properties at startup
│   │       ├── ReportingManager.java    # Extent report setup and step logging
│   │       ├── ExcelManager.java        # Reads test data from Testdata.xlsx
│   │       ├── RetryAnalyzer.java       # ThreadLocal retry counter logic
│   │       ├── RetryListener.java       # Applies RetryAnalyzer to all tests via IAnnotationTransformer
│   │       ├── Constants.java           # Shared constant values
│   │       │
│   │       ├── automationFunctions/     # Concrete implementations of each interface
│   │       │   ├── InteractionFunction.java
│   │       │   ├── ValidationFunction.java
│   │       │   ├── WebGeneralFunction.java
│   │       │   ├── ReportingFunction.java
│   │       │   └── GeneralFunction.java
│   │       │
│   │       └── automationInterfaces/    # Contracts consumed by BasePage
│   │           ├── InteractionInterface.java
│   │           ├── ValidationInterface.java
│   │           ├── WebGeneralInterface.java
│   │           └── ReportingInterface.java
│   │
│   └── test/java/webAutomation/
│       │
│       ├── BaseTest.java                # TestNG @Before/@After annotations — delegates to BaseManager
│       │
│       ├── pages/                       # Page Object classes
│       │   ├── SampleLoginBasePage.java
│       │   └── SampleWebBasePage.java
│       │
│       ├── testcases/                   # Test classes (extend BaseTest)
│       │   ├── SampleWebTest.java
│       │   ├── SampleNewWebTest.java
│       │   └── SampleRetryWebTest.java
│       │
│       └── testData/
│           └── Testdata.xlsx            # Excel test data (sheet per test method)
│
├── Jenkinsfile                          # Parameterised Jenkins pipeline
└── pom.xml
```

---

## Initial Setup

1. Install prerequisites listed above and verify `mvn -v`.
2. Configure `config/config.properties` as outlined in the **Configuration** section, including driver selection, capability details, and any optional wait settings.

---

## Writing Your First Test

### Step 1: Create a Page Class

Create a page class** extending `BasePage`. Pass the `ContextManager` received from the test class to the `super` constructor.

```java
// src/test/java/webAutomation/pages/LoginPage.java
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public LoginPage(ContextManager context) {
        super(context);
    }

    public void login(String username, String password) {
        type(usernameField, username);
        clickById("password");
        click(loginButton);
    }
}
```

### Step 2: Create a BaseTest Class

Create a `BaseTest` extending `BaseManager` and call the delegated TestNG lifecycle methods. **You never modify this class** — it is the fixed bridge between TestNG and the framework.

```java
// src/test/java/webAutomation/BaseTest.java
public class BaseTest extends BaseManager {

    @BeforeSuite
    public void setupBeforeSuite(ITestContext context) {
        beforeSuite(context);        // sets up Extent Report
    }

    @BeforeClass
    public void setupBeforeClass(ITestContext context) {
        beforeClass(context);        // creates the WebDriver
    }

    @BeforeMethod
    public void setupBeforeMethod(ITestResult result) {
        beforeMethod(result);        // resets driver, creates test node in report
    }

    @DataProvider(name = "getTestData")
    public String[][] getTestData(Method method) {
        return testData(method);     // loads rows from Testdata.xlsx by method name
    }

    @AfterMethod
    public void setupAfterMethod(ITestResult result) {
        afterMethod(result);         // captures screenshot, logs pass/fail
    }

    @AfterClass(alwaysRun = true)
    public void setupAfterClass() {
        afterClass();                // quits the WebDriver
    }

    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
        afterSuite();                // closes the Extent Report
    }
}
```

> `BaseManager` owns all the logic. `BaseTest` (in `src/test`) is purely a thin TestNG adapter — it only maps annotations to `BaseManager` calls. This keeps framework infrastructure free of TestNG lifecycle coupling.

### Step 3: Create a Test Class

Create a test class extending `BaseTest`.

```java
// src/test/java/webAutomation/testcases/LoginTest.java
public class LoginTest extends BaseTest {

    @Test(
        groups = {"Smoke"},
        dataProvider = "getTestData",
        description = "Verify successful login"
    )
    public void verifyLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(getDriverContext());
        loginPage.login(username, password);
        // assertions here
    }
}
```

### Step 4: Add Test Data

Add test data to Testdata.xlsx in a sheet named with test name like `verifyLogin`. Add a row for every `@Test` method that uses `dataProvider = "getTestData"`, with the method name in the first column followed by the parameter values.

```
Sheet name : Sheet1
┌───────────────┬──────────────┬──────────────┐
│ TestCaseName  │ username     │ password     │
├───────────────┼──────────────┼──────────────┤
│ verifyLogin   │ standard_user│ secret_sauce │
└───────────────┴──────────────┴──────────────┘
```

### Step 5: Register in Suite XML

Add the test class to a suite file:

```xml
<test name="Login Tests">
    <classes>
        <class name="webAutomation.testcases.LoginTest"/>
    </classes>
</test>
```

---

## Configuration
1. Open / Create `config/config.properties` at root project directory.
2. Set `BrowserName` to one of `Chrome`, `Firefox`, `Edge`, `Safari`.
3. Set `ApplicationURL` to the target web application.
Sample `config/config.properties`:
```properties
# Accepted values: Chrome, Firefox, Edge, Safari
BrowserName = Chrome

ApplicationURL = https://www.saucedemo.com/

# Default wait time in seconds for explicit and fluent waits
WaitTime = 10
```

---

## Running Tests

**Run a specific suite via Maven:**
```bash
mvn clean test -Dsurefire.suiteXmlFiles=WebTestSuites/SampleSuite.xml
```

**Run the retry suite:**
```bash
mvn clean test -Dsurefire.suiteXmlFiles=WebTestSuites/SampleRetrySuite.xml
```

**Run from IntelliJ IDEA:**
Right-click any suite XML file under `WebTestSuites/` → **Run**.

**Run from Jenkins:**

The `Jenkinsfile` exposes two pipeline parameters:

| Parameter | Options |
|---|---|
| `BROWSER_NAME` | Chrome, Firefox, Edge, Safari |
| `SUITE` | SampleSuite, SampleRetrySuite |

Trigger a build in Jenkins, select the parameters, and the pipeline will configure, execute, and publish the report automatically.

---

## Reports and Logs

**HTML Report (Extent Reports)**

After each run, an HTML report is generated at:
```
TestReport/Report_<timestamp>/<SuiteName>.html
```
When `IsJenkinsRun=true`, the timestamp is omitted and the report always writes to:
```
TestReport/Report_Folder/<SuiteName>.html
```
This fixed path is what Jenkins picks up for the **Publish HTML Reports** post-build step.

Each test node in the report includes:
- Pass / Fail / Skip status with colour-coded labels
- Screenshot attached at the final step of each `@Test`
- Failure stack trace logged inline on failure
- Test group tags (Smoke, Sanity, Regression)

**Console Logs**

All key actions — driver creation, retries, step execution — are printed to the console via `GeneralFunction.println()`, prefixed with a timestamp for easy correlation with report entries.

**Screenshots**

Screenshots are saved alongside the HTML report in the same `TestReport/` folder and referenced with relative paths, so the report remains portable.

---

## Troubleshooting Tips

**Browser does not launch**
- Verify the `BrowserName` value in `config.properties` exactly matches one of: `Chrome`, `Firefox`, `Edge`, `Safari` (case-sensitive).
- Ensure the browser is installed and up to date. Selenium 4 uses browser-bundled drivers — no separate WebDriver download needed.

**`NullPointerException` on `getDriverContext()`**
- This usually means a page object is being instantiated before `@BeforeClass` completes. Ensure page objects are created inside `@Test` methods or after the driver is initialised, not as class-level field initialisers.

**Test data not found / `@DataProvider` returns empty**
- Check that the sheet name in `Testdata.xlsx` exactly matches the `@Test` method name (case-sensitive).
- Ensure the Excel file is saved and not open in another application at runtime.

**Tests not retrying**
- Confirm `RetryListener` is registered in the suite XML under `<listeners>`.
- Do not add `retryAnalyzer =` directly to `@Test` — the listener handles it globally. Mixing both causes double-registration.

**Report not generated / Jenkins build fails at publish step**
- Set `IsJenkinsRun=true` in `config.properties` (or via the Jenkinsfile's dynamic config step) so the report lands at the fixed `Report_Folder/` path Jenkins expects.
- Ensure the **Publish HTML Plugin** is installed in Jenkins.

**Parallel tests interfering with each other**
- Each `<test>` block in the suite XML gets its own `BaseTest` instance and therefore its own `DriverManager` and `ContextManager`. Never share state between test classes through `static` fields.
- The retry counter uses `ThreadLocal` — safe for parallel execution at the `tests` level.

---

## License

This project is made available for **trial and evaluation purposes only**.

- You may use, run, and modify this framework for personal learning, internal evaluation, or proof-of-concept work.
- Redistribution, sublicensing, or use in commercial products without explicit written permission from the author is not permitted.
- This software is provided **as is**, without warranty of any kind. The author is not liable for any damages arising from its use.

For commercial licensing or extended use, contact the project maintainer via the GitHub repository.

**See [LICENSE](./LICENSE)**

---

Contact: biswas.bijit1994@gmail.com
*Built with Selenium 4.44.0 · TestNG 7.11.0 · ExtentReports 5.1.2*
