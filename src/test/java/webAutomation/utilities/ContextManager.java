package webAutomation.utilities;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContextManager {

    public WebDriver webDriver;
    public String browserName;
    public WebDriverWait wait;
    public FluentWait<WebDriver> fluentWait;
    public ExtentTest extentTest;


}
