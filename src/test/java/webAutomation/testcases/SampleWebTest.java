package webAutomation.testcases;

import org.testng.annotations.Test;
import webAutomation.pages.SampleLoginPage;
import webAutomation.pages.SamplePage;
import webAutomation.utilities.DriverManager;


public class SampleWebTest extends DriverManager {

    SampleLoginPage loginPg;
    SamplePage samplePg;

    @Test(
            groups = {"Smoke", "Regression"},
            dataProvider = "getTestData",
            description = "Login and add items to cart"
    )
    public void addItemsToCart(String userName, String password) {

        loginPg = new SampleLoginPage(getDriverContext());
        samplePg = new SamplePage(getDriverContext());

        loginPg.login(userName, password);

        samplePg.addItemToCart("Sauce Labs Backpack");

        samplePg.addItemToCart("Sauce Labs Bike Light");

        samplePg.viewCartAndVerifyItems("Sauce Labs Backpack", "Sauce Labs Bike Light");
    }

    @Test(
            groups = {"Sanity"},
            description = "Remove item and checkout"
    )
    public void removeItemAndCheckout() {
        samplePg.removeItemFromCart("Sauce Labs Backpack");

        samplePg.checkoutCart();
    }

}
