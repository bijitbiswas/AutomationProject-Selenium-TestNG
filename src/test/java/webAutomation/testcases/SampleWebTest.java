package webAutomation.testcases;

import org.testng.annotations.Test;
import webAutomation.pages.SampleLoginBasePage;
import webAutomation.pages.SampleWebBasePage;
import webAutomation.BaseTest;


public class SampleWebTest extends BaseTest {

    SampleLoginBasePage sampleLoginPg;
    SampleWebBasePage sampleWebPg;

    @Test(
            groups = {"Smoke", "Regression"},
            dataProvider = "getTestData",
            description = "Login and add items to cart"
    )
    public void addItemsToCart(String userName, String password) {

        sampleLoginPg = new SampleLoginBasePage(getDriverContext());
        sampleWebPg = new SampleWebBasePage(getDriverContext());

        sampleLoginPg.login(userName, password);

        sampleWebPg.addItemToCart("Sauce Labs Backpack");

        sampleWebPg.addItemToCart("Sauce Labs Bike Light");

        sampleWebPg.viewCartAndVerifyItems("Sauce Labs Backpack", "Sauce Labs Bike Light");
    }

    @Test(
            groups = {"Sanity"},
            description = "Remove item and checkout",
            dependsOnMethods = {"addItemsToCart"}
    )
    public void removeItemAndCheckout() {
        sampleWebPg = new SampleWebBasePage(getDriverContext());

        sampleWebPg.removeItemFromCart("Sauce Labs Backpack");

        sampleWebPg.checkoutCart();
    }

}
