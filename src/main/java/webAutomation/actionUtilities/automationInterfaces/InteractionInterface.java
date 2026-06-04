package webAutomation.actionUtilities.automationInterfaces;

import org.openqa.selenium.WebElement;

public interface InteractionInterface {

    /**
     * Clicks the given WebElement using a WebDriverWait until the element is clickable.
     *
     * @param element the WebElement to click
     */
    void click(WebElement element);

    /**
     * Finds an element by its HTML id attribute and clicks it.
     *
     * @param elementId the id attribute value of the element to click
     */
    void clickById(String elementId);

    /**
     * Finds an element by an XPath expression and clicks it.
     *
     * @param elementXpath the XPath expression used to locate the element
     */
    void clickByXpath(String elementXpath);

    /**
     * Finds an element by its visible text content and clicks it.
     *
     * @param elementText the exact visible text of the element to click
     */
    void clickByText(String elementText);

    /**
     * Clears the given input WebElement and types the specified text into it.
     *
     * @param element the input WebElement to type into
     * @param text    the text to enter into the element
     */
    void type(WebElement element, String text);

    /**
     * Finds an input element by its HTML id attribute and types the specified text into it.
     *
     * @param elementId the id attribute value of the input element
     * @param text      the text to enter into the element
     */
    void typeById(String elementId, String text);

}
