package webAutomation.testcases;

import org.testng.annotations.Test;
import webAutomation.pages.SampleLoginPage;
import webAutomation.pages.SampleWebPage;
import webAutomation.utilities.DriverManager;


public class SampleNewWebTest extends DriverManager {

    SampleLoginPage sampleLoginPg;
    SampleWebPage sampleWebPg;

    @Test(
            priority = 1,
            groups = {"Smoke"},
            dataProvider = "getTestData",
            description = "Verify item sorting Zto A"
    )
    public void verifyItemSorting(String userName, String password) {

        sampleLoginPg = new SampleLoginPage(getDriverContext());
        sampleWebPg = new SampleWebPage(getDriverContext());

        sampleLoginPg.login(userName, password);

        sampleWebPg.verifyZtoASort();
    }

    @Test(
            priority = 2,
            groups = {"Sanity"},
            description = "Very error on checkout with out items in cart"
    )
    public void verifyCheckoutCartWithNoItems() {
        sampleWebPg.checkoutCartWithNoItems();
    }

}
