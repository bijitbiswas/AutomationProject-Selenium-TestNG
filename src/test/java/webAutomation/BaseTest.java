package webAutomation;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import webAutomation.utilities.BaseManager;

import java.lang.reflect.Method;

public class BaseTest extends BaseManager {

    @BeforeSuite
    public void setupBeforeSuite(ITestContext context) {
        beforeSuite(context);
    }

    @BeforeClass
    public void setupBeforeClass(ITestContext context) {
        beforeClass(context);
    }

    @BeforeMethod
    public void setupBeforeMethod(ITestResult result) {
        beforeMethod(result);
    }

    @DataProvider(name = "getTestData")
    public String[][] getTestData(Method method) {
        return testData(method);
    }

    @AfterMethod
    public void setupAfterMethod(ITestResult result) {
        afterMethod(result);
    }

    @AfterClass(alwaysRun = true)
    public void setupAfterClass() {
        afterClass();
    }

    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
        afterSuite();
    }
}
