package webAutomation.utilities;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import webAutomation.Constants;
import webAutomation.utilities.automationFunctions.GeneralFunction;

import java.lang.reflect.Method;
import java.time.Duration;

public class DriverManager extends GeneralFunction {

    private  final ContextManager contextManager = new ContextManager();
    private static final ConfigurationManager configurationManager = new ConfigurationManager();
    private static final ReportingManager reportingManager = new ReportingManager();


    @BeforeSuite
    public void setupSuite(ITestContext context) {
        println("Executing before suite");

        reportingManager.setupExtentReport(context, configurationManager);
    }

    @BeforeClass
    public void createDriver() {
        println("Executing before class");

        WebDriver webDriver = createWebDriver();
        FluentWait<WebDriver> fluentWait = createFluentWait(webDriver);
        WebDriverWait wait = createWebDriverWait(webDriver);

        webDriver.manage().window().maximize();
        webDriver.get(configurationManager.applicationURL);

        contextManager.webDriver = webDriver;
        contextManager.wait = wait;
        contextManager.fluentWait = fluentWait;
        contextManager.browserName = configurationManager.browserName;
    }

    @BeforeMethod
    public void setupBeforeMethod(ITestResult result) {
        println("Executing before method");

        contextManager.extentTest = reportingManager.createTest(result);
    }

    @DataProvider(name = "getTestData")
    public String[][] getTestData(Method method) {
        return new ExcelManager().getMethodData(method.getName());
    }

    @AfterMethod
    public void addResultToRun(ITestResult result) {
        println("Executing after method");

        reportingManager.updateStatusToReport(result, getDriverContext().webDriver);
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        println("Closing Driver");
        if (getDriverContext().webDriver != null) {
            getDriverContext().webDriver.quit();
            println("Driver closed successfully");
        } else
            println("Driver is not created or is already closed");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        println("Executing after suite");

        reportingManager.closeExtentReport();
    }



    public ContextManager getDriverContext() {
        return contextManager;
    }

    private WebDriver createWebDriver() {
        println("Creating " + configurationManager.browserName +" Driver");

        WebDriver webDriver = switch (configurationManager.browserName) {
            case "Chrome" -> new ChromeDriver(getChromeOptions());
            case "Edge" -> new EdgeDriver(getEdgeOptions());
            case "Safari" -> new SafariDriver(getSafariOptions());
            case "Firefox" -> new FirefoxDriver(getFirefoxOptions());
            default -> null;
        };

        println("Driver created successfully");
        return webDriver;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-extensions");
        options.setPageLoadTimeout(Duration.ofSeconds(configurationManager.waitTime));
        return options;
    }

    private FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
//        options.addArguments("--disable-extensions");
        options.setPageLoadTimeout(Duration.ofSeconds(configurationManager.waitTime));
        return options;
    }

    private SafariOptions getSafariOptions() {
        SafariOptions options = new SafariOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setPageLoadTimeout(Duration.ofSeconds(configurationManager.waitTime));
        return options;
    }

    private EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("inprivate");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setPageLoadTimeout(Duration.ofSeconds(configurationManager.waitTime));
        return options;
    }


    private FluentWait<WebDriver> createFluentWait(WebDriver webDriver) {
        return new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(configurationManager.waitTime))
                .pollingEvery(Duration.ofSeconds(Constants.FLUENT_WAIT_POLLING_TIME_IN_SECS))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        // If .ignoring is not used, the fluent wait will fail immediately when such exception is thrown
        // and won't consider the repeated checking after polling time
    }

    private WebDriverWait createWebDriverWait(WebDriver webDriver) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(configurationManager.waitTime));
    }

}
