package webAutomation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webAutomation.utilities.ContextManager;
import webAutomation.utilities.PageActionManager;

public class SampleLoginPage extends PageActionManager {

    public SampleLoginPage(ContextManager context) {
        super(context);
    }

    @FindBy(id = "user-name")
    private WebElement userNameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(xpath = "//span[@data-test='title']")
    private WebElement titleField;


    public void login(String username, String password) {
        type(userNameField, username);
        type(passwordField, password);
        click(loginButton);
        waitForElementToBeVisible(titleField);
        addSuccessLabelWithScreenshot("Login successful");
    }
}
