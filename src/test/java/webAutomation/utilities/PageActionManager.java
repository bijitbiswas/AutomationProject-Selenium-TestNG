package webAutomation.utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import webAutomation.utilities.automationFunctions.InteractionFunction;
import webAutomation.utilities.automationFunctions.ReportingFunction;
import webAutomation.utilities.automationFunctions.ValidationFunction;
import webAutomation.utilities.automationFunctions.WebGeneralFunction;
import webAutomation.utilities.automationInterfaces.InteractionInterface;
import webAutomation.utilities.automationInterfaces.ReportingInterface;
import webAutomation.utilities.automationInterfaces.ValidationInterface;
import webAutomation.utilities.automationInterfaces.WebGeneralInterface;

// PageActionsManager is the main controller to send the context(driver, wait etc.) to the actual implementation
public class PageActionManager implements
        InteractionInterface,
        ValidationInterface,
        WebGeneralInterface,
        ReportingInterface
{

    private final InteractionInterface interactionInterface;
    private final ValidationInterface validationInterface;
    private final WebGeneralInterface webGeneralInterface;
    private final ReportingInterface reportingInterface;

    public PageActionManager(ContextManager context) {
        // To initialize the page elements in a generic way
        PageFactory.initElements(new DefaultElementLocatorFactory(context.webDriver), this);

        // Below Interfaces will Delegate to the Implementation function
        this.interactionInterface = new InteractionFunction(context);
        this.validationInterface = new ValidationFunction(context);
        this.webGeneralInterface = new WebGeneralFunction(context);
        this.reportingInterface = new ReportingFunction(context);
    }


    // ================== Interaction Functions ==================
    public void click(WebElement element) {
        interactionInterface.click(element);
    }

    public void clickById(String elementId) {
        interactionInterface.clickById(elementId);
    }

    public void clickByXpath(String elementXpath) {
        interactionInterface.clickByXpath(elementXpath);
    }

    public void clickByText(String elementText) {
        interactionInterface.clickByText(elementText);
    }

    public void type(WebElement element, String text) {
        interactionInterface.type(element, text);
    }

    public void typeById(String elementId, String text) {
        interactionInterface.typeById(elementId, text);
    }



    // ================== Validation Functions ==================

    public void waitForElementToBeVisible(WebElement element) {
        validationInterface.waitForElementToBeVisible(element);
    }

    public void waitForElementToBeVisible(WebElement element, int timeoutInSeconds) {
        validationInterface.waitForElementToBeVisible(element, timeoutInSeconds);
    }

    public void waitForElementToBeInvisible(WebElement element) {
        validationInterface.waitForElementToBeInvisible(element);
    }

    public void waitForElementToBeInvisible(WebElement element, int timeoutInSeconds) {
        validationInterface.waitForElementToBeInvisible(element, timeoutInSeconds);
    }

    @Override
    public boolean isElementVisible(WebElement element) {
        return validationInterface.isElementVisible(element);
    }

    @Override
    public boolean isElementVisibleById(String elementId) {
        return validationInterface.isElementVisibleById(elementId);
    }

    @Override
    public boolean isElementVisibleByText(String textValue) {
        return validationInterface.isElementVisibleByText(textValue);
    }

    @Override
    public boolean isElementVisibleByXpath(String xpathExpression) {
        return validationInterface.isElementVisibleByXpath(xpathExpression);
    }

    @Override
    public boolean isElementClickable(WebElement element) {
        return validationInterface.isElementClickable(element);
    }

    @Override
    public void validateElementText(WebElement element, String expectedText) {
        validationInterface.validateElementText(element, expectedText);
    }

    @Override
    public void validateText(String expectedText) {
        validationInterface.validateText(expectedText);
    }

    // ================== Mobile General Functions ==================
    public void navigateBack() {
        webGeneralInterface.navigateBack();
    }

    public String getBrowserName() {
        return webGeneralInterface.getBrowserName();
    }

    public String getCurrentURL() {
        return webGeneralInterface.getCurrentURL();
    }

    public void sleep(int timeInSecs) {
        webGeneralInterface.sleep(timeInSecs);
    }

    public void println(String message) {
        webGeneralInterface.println(message);
    }

    // ================== Reporting Functions ==================
    public void addSuccessLabelWithScreenshot(String labelName) {
        reportingInterface.addSuccessLabelWithScreenshot(labelName);
    }

    public void addSuccessLabel(String labelName) {
        reportingInterface.addSuccessLabel(labelName);
    }
}
