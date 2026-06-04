package webAutomation.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import webAutomation.pages.SampleLoginBasePage;
import webAutomation.pages.SampleWebBasePage;
import webAutomation.utilities.BaseTest;

public class SampleRetryWebTest extends BaseTest {

    SampleLoginBasePage sampleLoginPg;
    SampleWebBasePage sampleWebPg;

    @Test(
            priority = 1,
            groups = {"Smoke"},
            dataProvider = "getTestData",
            description = "Verify item sorting Zto A"
    )
    public void verifyItemSorting(String userName, String userPassword) {

        sampleLoginPg = new SampleLoginBasePage(getDriverContext());
        sampleWebPg = new SampleWebBasePage(getDriverContext());

        println("Inside @Test verifyItemSorting");
        sampleLoginPg.login(userName, userPassword);
        // Intentionally failing test to demonstrate retry mechanism
        Assert.fail();
    }

}
