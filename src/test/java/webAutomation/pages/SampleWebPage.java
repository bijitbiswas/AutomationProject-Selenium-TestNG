package webAutomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import webAutomation.utilities.ContextManager;
import webAutomation.utilities.PageActionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SampleWebPage extends PageActionManager {

    private final WebDriver driver;
    public SampleWebPage(ContextManager context) {
        super(context);
        driver = context.webDriver;
    }

    @FindBy(id = "add-to-cart")
    private WebElement addToCartButton;

    @FindBy(id = "shopping_cart_container")
    private WebElement cartBadge;

    @FindBy(xpath = "//a[@data-test='shopping-cart-link']")
    private WebElement cartIcon;

    @FindBy(xpath = "//span[@data-test='title']")
    private WebElement titleField;

    @FindBy(name = "checkout")
    private WebElement checkoutButton;

    @FindBy(name = "back-to-products")
    private WebElement backToProductsButton;

    @FindBy(xpath = "//*[@data-test='complete-header']")
    private WebElement completeHeader;

    @FindBy(xpath = "//*[@data-test='active-option']")
    private WebElement currentSortElement;

    @FindBy(xpath = "//select[@data-test='product-sort-container']")
    private WebElement sortDropdown;

    @FindBy(xpath = "//div[@data-test='inventory-item-name']")
    private List<WebElement> itemList;

    @FindBy(xpath = "//*[@data-test='error']")
    private WebElement errorText;

    @FindBy(xpath = "//*[@data-test='total-label']")
    private WebElement totalCartValue;






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

    public void verifyZtoASort() {
        String currentSortOrder = currentSortElement.getText();
        if(currentSortOrder.equals("Name (A to Z)")) {
            ArrayList<String> initialItemNames = getItemNames();
            WebElement sortDropdown = driver.findElement(By.xpath("//select[@data-test='product-sort-container']"));
            Select select = new Select(sortDropdown);
            select.selectByVisibleText("Name (Z to A)");
            ArrayList<String> sortedItemNames = getItemNames();
            Collections.reverse(initialItemNames);
            Assert.assertEquals(initialItemNames, sortedItemNames,
                    "Items are not sorted from Z to A");

            addSuccessLabelWithScreenshot("Items sorted from Z to A");
        }
    }

    public ArrayList<String> getItemNames() {
        ArrayList<String> itemNames = new ArrayList<>();
        for (WebElement webElement : itemList) {
            String itemName = webElement.getText();
            itemNames.add(itemName);
        }
        println("Item names in the list: " + itemNames);
        return itemNames;
    }

    public void checkoutCartWithNoItems() {
        click(cartIcon);
        waitForElementToBeVisible(checkoutButton, 3);
        click(checkoutButton);

        clickById("continue");
        Assert.assertEquals(errorText.getText(), "Error: First Name is required");
        addSuccessLabelWithScreenshot("First Name error message verified");

        typeById("first-name", "John");
        clickById("continue");
        Assert.assertEquals(errorText.getText(), "Error: Last Name is required");
        addSuccessLabel("Last Name error message verified");

        typeById("last-name", "Doe");
        clickById("continue");
        Assert.assertEquals(errorText.getText(), "Error: Postal Code is required");
        addSuccessLabel("Postal Code error message verified");

        typeById("postal-code", "12345");
        clickById("continue");

        println("Total cart value is: " + totalCartValue.getText());

        addSuccessLabelWithScreenshot("Checkout flow with no items verified");
    }

}
