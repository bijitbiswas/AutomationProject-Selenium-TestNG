package webAutomation.testcases;

import org.testng.annotations.Test;
import webAutomation.pages.SamplePage;
import webAutomation.utilities.DriverManager;


public class SampleWebTest extends DriverManager {

    SamplePage page;

    @Test(
            groups = {"Smoke", "Regression"},
            dataProvider = "getTestData",
            description = "This is a sample TestNG test"
    )
    public void addAndRemoveCartItem(String userName, String password) {
        System.out.println("Starting Sample Test");

        page = new SamplePage(getDriverContext());

        page.login(userName, password);

        page.addItemToCart("Sauce Labs Backpack");

        page.addItemToCart("Sauce Labs Bike Light");

        page.viewCartAndVerifyItems("Sauce Labs Backpack", "Sauce Labs Bike Light");

        page.removeItemFromCart("Sauce Labs Backpack");

        page.checkoutCart();

    }

}
