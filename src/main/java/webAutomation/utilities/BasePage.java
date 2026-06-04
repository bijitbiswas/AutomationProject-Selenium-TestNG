package webAutomation.utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import webAutomation.actionUtilities.automationFunctions.InteractionFunction;
import webAutomation.actionUtilities.automationFunctions.ReportingFunction;
import webAutomation.actionUtilities.automationFunctions.ValidationFunction;
import webAutomation.actionUtilities.automationFunctions.WebGeneralFunction;
import webAutomation.actionUtilities.automationInterfaces.InteractionInterface;
import webAutomation.actionUtilities.automationInterfaces.ReportingInterface;
import webAutomation.actionUtilities.automationInterfaces.ValidationInterface;
import webAutomation.actionUtilities.automationInterfaces.WebGeneralInterface;

// BasePage is the main controller to send the context(driver, wait etc.) to the actual implementation
public class BasePage implements
        InteractionInterface,
        ValidationInterface,
        WebGeneralInterface,
        ReportingInterface
{

    private final InteractionInterface interactionInterface;
    private final ValidationInterface validationInterface;
    private final WebGeneralInterface webGeneralInterface;
    private final ReportingInterface reportingInterface;

    public BasePage(ContextManager context) {
        // To initialize the page elements in a generic way
        PageFactory.initElements(new DefaultElementLocatorFactory(context.getWebDriver()), this);

        // Below Interfaces will Delegate to the Implementation function
        this.interactionInterface = new InteractionFunction(context);
        this.validationInterface = new ValidationFunction(context);
        this.webGeneralInterface = new WebGeneralFunction(context);
        this.reportingInterface = new ReportingFunction(context);
    }


    // ================== Interaction Functions ==================

    /**
     * Clicks the given WebElement using a WebDriverWait until the element is clickable.
     *
     * @param element the WebElement to click
     */
    public void click(WebElement element) {
        interactionInterface.click(element);
    }

    /**
     * Finds an element by its HTML id attribute and clicks it.
     *
     * @param elementId the id attribute value of the element to click
     */
    public void clickById(String elementId) {
        interactionInterface.clickById(elementId);
    }

    /**
     * Finds an element by an XPath expression and clicks it.
     *
     * @param elementXpath the XPath expression used to locate the element
     */
    public void clickByXpath(String elementXpath) {
        interactionInterface.clickByXpath(elementXpath);
    }

    /**
     * Finds an element by its visible text content and clicks it.
     *
     * @param elementText the exact visible text of the element to click
     */
    public void clickByText(String elementText) {
        interactionInterface.clickByText(elementText);
    }

    /**
     * Clears the given input WebElement and types the specified text into it.
     *
     * @param element the input WebElement to type into
     * @param text    the text to enter into the element
     */
    public void type(WebElement element, String text) {
        interactionInterface.type(element, text);
    }

    /**
     * Finds an input element by its HTML id attribute and types the specified text into it.
     *
     * @param elementId the id attribute value of the input element
     * @param text      the text to enter into the element
     */
    public void typeById(String elementId, String text) {
        interactionInterface.typeById(elementId, text);
    }


    // ================== Validation Functions ==================

    /**
     * Waits for the given element to become visible using the default configured timeout.
     *
     * @param element the WebElement to wait for
     */
    public void waitForElementToBeVisible(WebElement element) {
        validationInterface.waitForElementToBeVisible(element);
    }

    /**
     * Waits for the given element to become visible within the specified timeout.
     *
     * @param element          the WebElement to wait for
     * @param timeoutInSeconds maximum number of seconds to wait
     */
    public void waitForElementToBeVisible(WebElement element, int timeoutInSeconds) {
        validationInterface.waitForElementToBeVisible(element, timeoutInSeconds);
    }

    /**
     * Waits for the given element to become invisible using the default configured timeout.
     *
     * @param element the WebElement to wait for invisibility
     */
    public void waitForElementToBeInvisible(WebElement element) {
        validationInterface.waitForElementToBeInvisible(element);
    }

    /**
     * Waits for the given element to become invisible within the specified timeout.
     *
     * @param element          the WebElement to wait for invisibility
     * @param timeoutInSeconds maximum number of seconds to wait
     */
    public void waitForElementToBeInvisible(WebElement element, int timeoutInSeconds) {
        validationInterface.waitForElementToBeInvisible(element, timeoutInSeconds);
    }

    /**
     * Checks whether the given WebElement is currently visible on the page.
     *
     * @param element the WebElement to check
     * @return {@code true} if the element is visible, {@code false} otherwise
     */
    @Override
    public boolean isElementVisible(WebElement element) {
        return validationInterface.isElementVisible(element);
    }

    /**
     * Checks whether an element with the given id attribute is currently visible on the page.
     *
     * @param elementId the id attribute value of the element to check
     * @return {@code true} if the element is visible, {@code false} otherwise
     */
    @Override
    public boolean isElementVisibleById(String elementId) {
        return validationInterface.isElementVisibleById(elementId);
    }

    /**
     * Checks whether an element matching the given visible text is currently present on the page.
     *
     * @param textValue the exact visible text of the element to check
     * @return {@code true} if an element with that text is visible, {@code false} otherwise
     */
    @Override
    public boolean isElementVisibleByText(String textValue) {
        return validationInterface.isElementVisibleByText(textValue);
    }

    /**
     * Checks whether an element matching the given XPath expression is currently visible on the page.
     *
     * @param xpathExpression the XPath expression used to locate the element
     * @return {@code true} if the element is visible, {@code false} otherwise
     */
    @Override
    public boolean isElementVisibleByXpath(String xpathExpression) {
        return validationInterface.isElementVisibleByXpath(xpathExpression);
    }

    /**
     * Checks whether the given WebElement is currently clickable (visible and enabled).
     *
     * @param element the WebElement to check
     * @return {@code true} if the element is clickable, {@code false} otherwise
     */
    @Override
    public boolean isElementClickable(WebElement element) {
        return validationInterface.isElementClickable(element);
    }

    /**
     * Asserts that the given WebElement's text matches the expected value.
     * Fails the test if the text does not match.
     *
     * @param element      the WebElement whose text is to be validated
     * @param expectedText the expected text value of the element
     */
    @Override
    public void validateElementText(WebElement element, String expectedText) {
        validationInterface.validateElementText(element, expectedText);
    }

    /**
     * Asserts that the expected text is present somewhere on the current page.
     * Fails the test if the text is not found.
     *
     * @param expectedText the text expected to be present on the page
     */
    @Override
    public void validateText(String expectedText) {
        validationInterface.validateText(expectedText);
    }

    // ================== Web General Functions ==================

    /**
     * Navigates the browser back to the previous page in the session history.
     */
    public void navigateBack() {
        webGeneralInterface.navigateBack();
    }

    /**
     * Returns the name of the browser currently in use (e.g. "Chrome", "Firefox").
     *
     * @return the browser name as configured in the test context
     */
    public String getBrowserName() {
        return webGeneralInterface.getBrowserName();
    }

    /**
     * Returns the URL of the page currently loaded in the browser.
     *
     * @return the current page URL as a String
     */
    public String getCurrentURL() {
        return webGeneralInterface.getCurrentURL();
    }

    /**
     * Pauses test execution for the specified number of seconds.
     * Prefer explicit waits over this method where possible.
     *
     * @param timeInSecs the number of seconds to sleep
     */
    public void sleep(int timeInSecs) {
        webGeneralInterface.sleep(timeInSecs);
    }

    /**
     * Prints a message to the standard output with a timestamp or test context prefix.
     *
     * @param message the message to print
     */
    public void println(String message) {
        webGeneralInterface.println(message);
    }

    // ================== Reporting Functions ==================

    /**
     * Logs a success step in the Extent Report with the given label and attaches a screenshot.
     *
     * @param labelName the label text to display in the report for this step
     */
    public void addSuccessLabelWithScreenshot(String labelName) {
        reportingInterface.addSuccessLabelWithScreenshot(labelName);
    }

    /**
     * Logs a success step in the Extent Report with the given label (no screenshot attached).
     *
     * @param labelName the label text to display in the report for this step
     */
    public void addSuccessLabel(String labelName) {
        reportingInterface.addSuccessLabel(labelName);
    }
}
