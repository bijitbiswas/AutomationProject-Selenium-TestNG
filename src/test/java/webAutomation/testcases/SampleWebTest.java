package webAutomation.testcases;

import org.testng.annotations.Test;
import webAutomation.pages.SampleLoginPage;
import webAutomation.pages.SampleWebPage;
import webAutomation.utilities.DriverManager;


public class SampleWebTest extends DriverManager {

    SampleLoginPage sampleLoginPg;
    SampleWebPage sampleWebPg;

    @Test(
            groups = {"Smoke", "Regression"},
            dataProvider = "getTestData",
            description = "Login and add items to cart"
    )
    public void addItemsToCart(String userName, String password) {

        sampleLoginPg = new SampleLoginPage(getDriverContext());
        sampleWebPg = new SampleWebPage(getDriverContext());

        sampleLoginPg.login(userName, password);

        sampleWebPg.addItemToCart("Sauce Labs Backpack");

        sampleWebPg.addItemToCart("Sauce Labs Bike Light");

        sampleWebPg.viewCartAndVerifyItems("Sauce Labs Backpack", "Sauce Labs Bike Light");
    }

    @Test(
            groups = {"Sanity"},
            description = "Remove item and checkout"
    )
    public void removeItemAndCheckout() {
        sampleWebPg = new SampleWebPage(getDriverContext());

        sampleWebPg.removeItemFromCart("Sauce Labs Backpack");

        sampleWebPg.checkoutCart();
    }

}
