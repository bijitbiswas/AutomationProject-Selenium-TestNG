package webAutomation.testcases;

import org.testng.annotations.Test;
import webAutomation.pages.SampleLoginBasePage;
import webAutomation.pages.SampleWebBasePage;
import webAutomation.BaseTest;


public class SampleNewWebTest extends BaseTest {

    SampleLoginBasePage sampleLoginPg;
    SampleWebBasePage sampleWebPg;

    @Test(
            priority = 1,
            groups = {"Smoke"},
            dataProvider = "getTestData",
            description = "Verify item sorting Zto A"
    )
    public void verifyItemSorting(String userName, String password) {

        sampleLoginPg = new SampleLoginBasePage(getDriverContext());
        sampleWebPg = new SampleWebBasePage(getDriverContext());

        sampleLoginPg.login(userName, password);

        sampleWebPg.verifyZtoASort();
    }

    @Test(
            priority = 2,
            groups = {"Sanity"},
            description = "Very error on checkout with out items in cart"
    )
    public void verifyCheckoutCartWithNoItems() {
        sampleWebPg = new SampleWebBasePage(getDriverContext());

        sampleWebPg.checkoutCartWithNoItems();
    }

}
