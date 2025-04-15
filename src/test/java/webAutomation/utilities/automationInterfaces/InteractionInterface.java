package webAutomation.utilities.automationInterfaces;

import org.openqa.selenium.WebElement;

public interface InteractionInterface {

    void click(WebElement element);

    void clickById(String elementId);

    void clickByXpath(String elementXpath);

    void clickByText(String elementText);

    void type(WebElement element, String text);

    void typeById(String elementId, String text);

}
