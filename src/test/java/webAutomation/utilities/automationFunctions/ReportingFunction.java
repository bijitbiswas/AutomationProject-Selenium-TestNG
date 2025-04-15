package webAutomation.utilities.automationFunctions;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import webAutomation.utilities.ContextManager;
import webAutomation.utilities.ReportingManager;
import webAutomation.utilities.automationInterfaces.ReportingInterface;

public class ReportingFunction implements ReportingInterface {

    WebDriver webDriver;
    ExtentTest extentTest;

    public ReportingFunction(ContextManager context) {
        this.webDriver = context.webDriver;
        this.extentTest = context.extentTest;
    }

    @Override
    public void addSuccessLabel(String labelName) {
        extentTest.log(Status.PASS, MarkupHelper.createLabel(labelName, ExtentColor.GREEN));
    }

    @Override
    public void addSuccessLabelWithScreenshot(String labelName) {
        String screenshotPath = new ReportingManager().captureScreenshot(webDriver, labelName);
        extentTest.log(Status.PASS, MarkupHelper.createLabel(labelName, ExtentColor.GREEN),
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
        );
    }
}
