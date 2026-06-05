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
import webAutomation.actionUtilities.automationFunctions.GeneralFunction;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.time.Duration;

public class DriverManager extends GeneralFunction {

    private final ContextManager contextManager = new ContextManager();
    private final ConfigurationManager configurationManager;

    public DriverManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    public void createDriver() {
        println("Executing createDriver");

        WebDriver webDriver = createWebDriver();
        FluentWait<WebDriver> fluentWait = createFluentWait(webDriver);
        WebDriverWait wait = createWebDriverWait(webDriver);

        webDriver.manage().window().maximize();
        webDriver.get(configurationManager.applicationURL);

        contextManager.initialize(webDriver, wait, fluentWait, configurationManager.browserName);
    }

    public void quitDriver() {
        println("Executing quitDriver");

        if (contextManager.getWebDriver() != null) {
            contextManager.getWebDriver().quit();
            contextManager.clear();
            println("Driver closed successfully");
        } else {
            println("Driver is not created or is already closed");
        }
    }

    public void resetDriver(ITestResult result) {
        IRetryAnalyzer analyzer = result.getMethod().getRetryAnalyzer(result);
        boolean isRetrying = analyzer instanceof RetryAnalyzer && ((RetryAnalyzer) analyzer).isRetrying();
        if (isRetrying) {
            quitDriver();
            createDriver();
        }
    }

    public ContextManager getDriverContext() {
        return contextManager;
    }

    private WebDriver createWebDriver() {
        println("Creating " + configurationManager.browserName + " Driver");

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
