package webAutomation.utilities;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContextManager {

    private WebDriver webDriver;
    private String browserName;
    private WebDriverWait wait;
    private FluentWait<WebDriver> fluentWait;
    private ExtentTest extentTest;

    /**
     * Initializes all driver-related fields atomically. Called once per @BeforeClass by DriverManager.
     */
    public void initialize(WebDriver webDriver, WebDriverWait wait,
                           FluentWait<WebDriver> fluentWait, String browserName) {
        this.webDriver = webDriver;
        this.wait = wait;
        this.fluentWait = fluentWait;
        this.browserName = browserName;
    }

    /**
     * Nulls out driver-related fields after the browser is quit. Called by DriverManager.quitDriver().
     */
    public void clear() {
        this.webDriver = null;
        this.wait = null;
        this.fluentWait = null;
    }

    /**
     * Sets the current test's Extent report node. Called once per @BeforeMethod by BaseTest.
     */
    public void setExtentTest(ExtentTest extentTest) {
        this.extentTest = extentTest;
    }

    public WebDriver getWebDriver()              { return webDriver; }
    public String getBrowserName()               { return browserName; }
    public WebDriverWait getWait()               { return wait; }
    public FluentWait<WebDriver> getFluentWait() { return fluentWait; }
    public ExtentTest getExtentTest()            { return extentTest; }

}
