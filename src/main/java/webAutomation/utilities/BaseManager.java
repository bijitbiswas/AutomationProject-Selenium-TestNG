package webAutomation.utilities;

import org.testng.ITestContext;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class BaseManager {

    private static final ConfigurationManager configurationManager = new ConfigurationManager();
    private static final ReportingManager reportingManager = new ReportingManager();

    private final DriverManager driverManager = new DriverManager(configurationManager);

    public void beforeSuite(ITestContext context) {
        System.out.println("********@BeforeSuite********");
        reportingManager.setupExtentReport(context, configurationManager);
    }

    public void beforeClass(ITestContext context) {
        System.out.println("********Started @BeforeClass********");
        driverManager.createDriver();
    }

    public void beforeMethod(ITestResult result) {
        System.out.println("********Started @BeforeMethod********");
        driverManager.resetDriver();
        driverManager.getDriverContext().setExtentTest(reportingManager.createTest(result));
    }

    public String[][] testData(Method method) {
        System.out.println("********Getting test data for method: " + method.getName() + "********");
        return new ExcelManager().getMethodData(method.getName());
    }

    public void afterMethod(ITestResult result) {
        System.out.println("********Started @AfterMethod********");
        reportingManager.updateStatusToReport(result, driverManager.getDriverContext().getExtentTest(),
                driverManager.getDriverContext().getWebDriver());
    }

    public void afterClass() {
        System.out.println("********Started @AfterClass********");
        driverManager.quitDriver();
    }

    public void afterSuite() {
        System.out.println("********Started @AfterSuite********");
        reportingManager.closeExtentReport();
    }

    public ContextManager getDriverContext() {
        return driverManager.getDriverContext();
    }
}
