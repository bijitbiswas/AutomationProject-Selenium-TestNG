package webAutomation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webAutomation.utilities.ContextManager;
import webAutomation.utilities.PageActionManager;

public class SampleWebPage extends PageActionManager {

    public SampleWebPage(ContextManager context) {
        super(context);
    }

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



    public void addItemToCart(String itemName) {
        clickByText(itemName);
        waitForElementToBeInvisible(titleField, 3);
        click(addToCartButton);
        click(backToProductsButton);
        addSuccessLabel("Item "+itemName+"added to cart");
    }

    public void viewCartAndVerifyItems(String... items) {
        click(cartBadge);
        waitForElementToBeVisible(checkoutButton, 3);
        for (String item : items) {
            validateText(item);
        }
        addSuccessLabelWithScreenshot("Items verified in cart");
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

        sleep(3);
        navigateBack();
        String currentURL = getCurrentURL();
        String browser = getBrowserName();
        println("Current URL: " + currentURL+" and browser: "+browser);
    }
}
