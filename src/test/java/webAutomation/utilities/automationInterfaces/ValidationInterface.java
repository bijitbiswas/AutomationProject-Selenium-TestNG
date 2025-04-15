package webAutomation.utilities.automationInterfaces;

import org.openqa.selenium.WebElement;

public interface ValidationInterface {

    void waitForElementToBeVisible(WebElement element);

    void waitForElementToBeVisible(WebElement element, int timeoutInSeconds);

    void waitForElementToBeInvisible(WebElement element);

    void waitForElementToBeInvisible(WebElement element, int timeoutInSeconds);

    boolean isElementVisible(WebElement element);

    boolean isElementVisibleById(String elementId);

    boolean isElementVisibleByText(String textValue);

    boolean isElementVisibleByXpath(String xpathExpression);

    boolean isElementClickable(WebElement element);

    void validateElementText(WebElement element, String expectedText);

    void validateText(String expectedText);
}
