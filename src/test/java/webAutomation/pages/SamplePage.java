package webAutomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webAutomation.utilities.ContextManager;
import webAutomation.utilities.PageActionManager;

public class SamplePage extends PageActionManager {

    public SamplePage(ContextManager context) {
        super(context);
    }

    @FindBy(id = "user-name")
    private WebElement userNameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(id = "add-to-cart")
    private WebElement addToCartButton;

    @FindBy(id = "shopping_cart_container")
    private WebElement cartBadge;

    @FindBy(xpath = "//span[@data-test='title']")
    private WebElement titleField;

    @FindBy(name = "checkout")
    private WebElement checkoutButton;

    @FindBy(name = "back-to-products")
    private WebElement backToProductsButton;

    @FindBy(xpath = "//*[@data-test='complete-header']")
    private WebElement completeHeader;








    public void login(String username, String password) {
        type(userNameField, username);
        type(passwordField, password);
        click(loginButton);
        waitForElementToBeVisible(titleField);
    }

    public void addItemToCart(String itemName) {
        clickByText(itemName);
        waitForElementToBeInvisible(titleField, 3);
        click(addToCartButton);
        click(backToProductsButton);
    }

    public void viewCartAndVerifyItems(String... items) {
        click(cartBadge);
        waitForElementToBeVisible(checkoutButton, 3);
        for (String item : items) {
            validateText(item);
        }
    }

    public void removeItemFromCart(String itemName) {
        String itemRemoveButtonXpath = "//*[text()='"+itemName+"']/ancestor::div[@class='cart_item_label']//button";
        clickByXpath(itemRemoveButtonXpath);
    }

    public void checkoutCart() {
        click(checkoutButton);
        typeById("first-name", "John");
        typeById("last-name", "Doe");
        typeById("postal-code", "12345");
        clickById("continue");
        clickById("finish");
        validateElementText(completeHeader, "Thank you for your order!");
    }
}
