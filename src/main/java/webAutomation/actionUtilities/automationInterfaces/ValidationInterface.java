package webAutomation.actionUtilities.automationInterfaces;

import org.openqa.selenium.WebElement;

public interface ValidationInterface {

    /**
     * Waits for the given element to become visible using the default configured timeout.
     *
     * @param element the WebElement to wait for
     */
    void waitForElementToBeVisible(WebElement element);

    /**
     * Waits for the given element to become visible within the specified timeout.
     *
     * @param element          the WebElement to wait for
     * @param timeoutInSeconds maximum number of seconds to wait
     */
    void waitForElementToBeVisible(WebElement element, int timeoutInSeconds);

    /**
     * Waits for the given element to become invisible using the default configured timeout.
     *
     * @param element the WebElement to wait for invisibility
     */
    void waitForElementToBeInvisible(WebElement element);

    /**
     * Waits for the given element to become invisible within the specified timeout.
     *
     * @param element          the WebElement to wait for invisibility
     * @param timeoutInSeconds maximum number of seconds to wait
     */
    void waitForElementToBeInvisible(WebElement element, int timeoutInSeconds);

    /**
     * Checks whether the given WebElement is currently visible on the page.
     *
     * @param element the WebElement to check
     * @return {@code true} if the element is visible, {@code false} otherwise
     */
    boolean isElementVisible(WebElement element);

    /**
     * Checks whether an element with the given id attribute is currently visible on the page.
     *
     * @param elementId the id attribute value of the element to check
     * @return {@code true} if the element is visible, {@code false} otherwise
     */
    boolean isElementVisibleById(String elementId);

    /**
     * Checks whether an element matching the given visible text is currently present on the page.
     *
     * @param textValue the exact visible text of the element to check
     * @return {@code true} if an element with that text is visible, {@code false} otherwise
     */
    boolean isElementVisibleByText(String textValue);

    /**
     * Checks whether an element matching the given XPath expression is currently visible on the page.
     *
     * @param xpathExpression the XPath expression used to locate the element
     * @return {@code true} if the element is visible, {@code false} otherwise
     */
    boolean isElementVisibleByXpath(String xpathExpression);

    /**
     * Checks whether the given WebElement is currently clickable (visible and enabled).
     *
     * @param element the WebElement to check
     * @return {@code true} if the element is clickable, {@code false} otherwise
     */
    boolean isElementClickable(WebElement element);

    /**
     * Asserts that the given WebElement's text matches the expected value.
     * Fails the test if the text does not match.
     *
     * @param element      the WebElement whose text is to be validated
     * @param expectedText the expected text value of the element
     */
    void validateElementText(WebElement element, String expectedText);

    /**
     * Asserts that the expected text is present somewhere on the current page.
     * Fails the test if the text is not found.
     *
     * @param expectedText the text expected to be present on the page
     */
    void validateText(String expectedText);
}
