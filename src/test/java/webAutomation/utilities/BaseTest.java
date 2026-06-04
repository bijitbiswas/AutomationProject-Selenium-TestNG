package webAutomation.utilities;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import webAutomation.utilities.automationFunctions.GeneralFunction;

import java.lang.reflect.Method;

public class BaseTest extends GeneralFunction {

    private static final ConfigurationManager configurationManager = new ConfigurationManager();
    private static final ReportingManager reportingManager = new ReportingManager();

    private final DriverManager driverManager = new DriverManager(configurationManager);

    @BeforeSuite
    public void setupSuite(ITestContext context) {
        println("Executing @BeforeSuite");

        reportingManager.setupExtentReport(context, configurationManager);
    }

    @BeforeClass
    public void createDriver() {
        println("Executing @BeforeClass");

        driverManager.createDriver();
    }

    @BeforeMethod
    public void setupBeforeMethod(ITestResult result) {
        println("Executing @BeforeMethod");

        driverManager.resetDriver();
        driverManager.getDriverContext().extentTest = reportingManager.createTest(result);
    }

    @DataProvider(name = "getTestData")
    public String[][] getTestData(Method method) {
        return new ExcelManager().getMethodData(method.getName());
    }

    @AfterMethod
    public void addResultToRun(ITestResult result) {
        println("Executing @AfterMethod");

        reportingManager.updateStatusToReport(result, driverManager.getDriverContext().extentTest,
                driverManager.getDriverContext().webDriver);
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        println("Executing @AfterClass");

        driverManager.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        println("Executing @AfterSuite");

        reportingManager.closeExtentReport();
    }

    public ContextManager getDriverContext() {
        return driverManager.getDriverContext();
    }

}
