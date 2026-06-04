package webAutomation.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import webAutomation.pages.SampleLoginPage;
import webAutomation.pages.SampleWebPage;
import webAutomation.utilities.DriverManager;

public class SampleRetryWebTest extends DriverManager {

    SampleLoginPage sampleLoginPg;
    SampleWebPage sampleWebPg;

    @Test(
            priority = 1,
            groups = {"Smoke"},
            dataProvider = "getTestData",
            description = "Verify item sorting Zto A"
    )
    public void verifyItemSorting(String userName, String userPassword) {

        sampleLoginPg = new SampleLoginPage(getDriverContext());
        sampleWebPg = new SampleWebPage(getDriverContext());

        println("Inside @Test verifyItemSorting");
        sampleLoginPg.login(userName, userPassword);
        // Intentionally failing test to demonstrate retry mechanism
        Assert.fail();
    }

}
