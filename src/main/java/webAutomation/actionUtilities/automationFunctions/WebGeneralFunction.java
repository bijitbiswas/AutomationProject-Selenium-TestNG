package webAutomation.actionUtilities.automationFunctions;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import webAutomation.utilities.ContextManager;
import webAutomation.actionUtilities.automationInterfaces.WebGeneralInterface;


public class WebGeneralFunction extends GeneralFunction implements WebGeneralInterface {

    WebDriver webDriver;
    WebDriverWait wait;
    FluentWait<WebDriver> fluentWait;
    private final String browserName;
    GeneralFunction generalFunction = new GeneralFunction();

    public WebGeneralFunction(ContextManager context) {
        this.webDriver = context.getWebDriver();
        this.wait = context.getWait();
        this.fluentWait = context.getFluentWait();
        this.browserName = context.getBrowserName();
    }


    @Override
    public void navigateBack() {
        webDriver.navigate().back();
        println("Navigated back in application");
    }

    @Override
    public String getBrowserName() {
        return browserName;
    }

    @Override
    public String getCurrentURL() {
        return webDriver.getCurrentUrl();
    }

    @Override
    public void sleep(int timeInSecs) {
        try {
            Thread.sleep(timeInSecs * 1000L);
            println("Slept for " + timeInSecs + " seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void println(String message) {
        generalFunction.println(message);
    }
}
