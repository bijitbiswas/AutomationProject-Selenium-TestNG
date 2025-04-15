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

import java.lang.reflect.Method;
import java.time.Duration;

public class DriverManager {

    private final ContextManager context = new ContextManager();
    private final ReportingManager report = new ReportingManager();
    private ConfigurationManager configurationManager;

    @BeforeSuite
    public void setupSuite(ITestContext context) {
        System.out.println("=======Executing before suite======");
        configurationManager = new ConfigurationManager();

        report.setupExtentReport(context, configurationManager);
    }

    @BeforeClass
    public void createDriver() {

        WebDriver webDriver = createWebDriver();
        FluentWait<WebDriver> fluentWait = createFluentWait(webDriver);
        WebDriverWait wait = createWebDriverWait(webDriver);

        webDriver.manage().window().maximize();
        webDriver.get(configurationManager.applicationURL);

        context.webDriver = webDriver;
        context.wait = wait;
        context.fluentWait = fluentWait;
        context.driverName = configurationManager.browserName;
    }

    @BeforeMethod
    public void setupBeforeMethod(ITestResult result) {
        System.out.println("=======Executing before method======");
        context.extentTest = report.createTest(result);
    }

    @DataProvider(name = "getTestData")
    public String[][] getTestData(Method method) {
        return new ExcelManager().getMethodData(method.getName());
    }

    @AfterMethod
    public void addResultToRun(ITestResult result) {
        System.out.println("=======Executing after method======");
        report.updateStatusToReport(getDriverContext().webDriver, result);
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        System.out.println("========Closing Driver========");
        if (getDriverContext().webDriver != null) {
            getDriverContext().webDriver.quit();
            System.out.println("========Driver closed successfully========");
        } else
            System.out.println("========Driver is not created or is already closed========");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        System.out.println("=======Executing after suite======");
        report.closeExtentReport();
    }



    public ContextManager getDriverContext() {
        return context;
    }

    private WebDriver createWebDriver() {
        System.out.println("========Creating " + configurationManager.browserName +" Driver========");


        WebDriver webDriver = switch (configurationManager.browserName) {
            case "Chrome" -> new ChromeDriver(getChromeOptions());
            case "Edge" -> new EdgeDriver(getEdgeOptions());
            case "Safari" -> new SafariDriver(getSafariOptions());
            case "Firefox" -> new FirefoxDriver(getFirefoxOptions());
            default -> null;
        };

        System.out.println("========Driver created successfully========");
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

//
//    private DesiredCapabilities getDesiredCapabilities() {
//        return switch (configurationManager.driverName) {
//            case "Android" -> getAndroidCapabilities();
//            case "iOS" -> getIOSCapabilities();
//            case "Browserstack" -> getBrowserstackCapabilities();
//            default -> throw new IllegalArgumentException("Invalid driver name");
//        };
//    }
//
//    private DesiredCapabilities getAndroidCapabilities() {
//        DesiredCapabilities capabilities = configurationManager.androidCapabilities;
//        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("automationName", "UiAutomator2");
//        return capabilities;
//    }

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
