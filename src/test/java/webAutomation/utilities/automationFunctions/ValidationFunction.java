package webAutomation.utilities.automationFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import webAutomation.Constants;
import webAutomation.utilities.ContextManager;
import webAutomation.utilities.automationInterfaces.ValidationInterface;

import java.time.Duration;
import java.util.function.Consumer;

public class ValidationFunction implements ValidationInterface {

    private static final Logger logger = LoggerFactory.getLogger(ValidationFunction.class);

    WebDriver webDriver;
    WebDriverWait wait;
    WebDriverWait waitInValidation;
    FluentWait<WebDriver> fluentWait;
    GeneralFunction generalFunction = new GeneralFunction();

    public ValidationFunction(ContextManager context) {
        this.webDriver = context.webDriver;
        this.wait = context.wait;
        this.fluentWait = context.fluentWait;
    }

    private void waitForElementVisibleWithWait(WebElement element, WebDriverWait customWait) {
        String elementName = generalFunction.getElementName(element);
        try {
            customWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
            System.out.println("=======Element " + elementName + " visible on screen=======");
        } catch (Exception e) {
            System.out.println("=======Failed waiting for element " + elementName + " to be visible======");
            logger.error("Failed waiting for element", e);
            throw e;
        }
    }

    private void waitForElementInvisibleWithWait(WebElement element, WebDriverWait customWait) {
        String elementName = generalFunction.getElementName(element);
        try {
            customWait.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOf(element)));
            System.out.println("=======Element " + elementName + " invisible on screen=======");
        } catch (Exception e) {
            System.out.println("=======Failed waiting for element " + elementName + " to be invisible======");
            logger.error("Failed waiting for element", e);
            throw e;
        }
    }

    @Override
    public void waitForElementToBeVisible(WebElement element) {
        waitForElementVisibleWithWait(element, this.wait);
    }

    @Override
    public void waitForElementToBeVisible(WebElement element, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(webDriver, Duration.ofSeconds(timeoutInSeconds));
        waitForElementVisibleWithWait(element, customWait);
    }

    @Override
    public void waitForElementToBeInvisible(WebElement element) {
        waitForElementInvisibleWithWait(element, this.wait);
    }

    @Override
    public void waitForElementToBeInvisible(WebElement element, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(webDriver, Duration.ofSeconds(timeoutInSeconds));
        waitForElementInvisibleWithWait(element, customWait);
    }

    @Override
    public boolean isElementVisible(WebElement element) {
        return conditionCheck( webDriverWait ->
                webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions
                        .visibilityOf(element))), "visible");
    }

    @Override
    public boolean isElementVisibleById(String elementId) {
        return conditionCheck( webDriverWait ->
                webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions
                        .visibilityOfElementLocated(By.id(elementId)))), "visibleById");
    }

    @Override
    public boolean isElementVisibleByText(String textValue) {
        String xpathExpression = "//*[contains(text(),'" + textValue + "')]";
        return conditionCheck( webDriverWait ->
                webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath(xpathExpression)))), "visibleByText");
    }

    @Override
    public boolean isElementVisibleByXpath(String xpathExpression) {
        return conditionCheck( webDriverWait ->
                webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath(xpathExpression)))), "visibleByXpath");
    }

    @Override
    public boolean isElementClickable(WebElement element) {
        return conditionCheck( webDriverWait ->
                webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions
                        .elementToBeClickable(element))), "clickable");
    }

    @Override
    public void validateElementText(WebElement element, String expectedText) {
        System.out.println("=======Validating element text to be "+expectedText+"=======");
        String elementText;
        WebElement actionElement;
        try {
            actionElement = wait.until(ExpectedConditions.refreshed(ExpectedConditions
                    .visibilityOf(element)));
            elementText = actionElement.getText().isEmpty()?"": actionElement.getText();
        } catch (Exception e){
            System.out.println("=======Failed to find element to click======");
            e.printStackTrace();
            throw e;
        }
        Assert.assertEquals(elementText, expectedText, "Element text "+expectedText+"is not displayed on element");
        System.out.println("=======Element text "+expectedText+" is displayed on element=======");
    }

    @Override
    public void validateText(String expectedText) {
        System.out.println("=======Validating element text to be "+expectedText+" on screen=======");
        String xpathExpression = "//*[contains(text(),'" + expectedText + "')]";
        Assert.assertTrue(isElementVisibleByXpath(xpathExpression), "Element text " +
                expectedText+" is not displayed on screen");
        System.out.println("=======Element text "+expectedText+" is displayed on screen=======");
    }

    private boolean conditionCheck(Consumer<WebDriverWait> func, String checkType) {
        WebDriverWait newWait = new WebDriverWait(webDriver, Duration.ofSeconds(Constants.SHORT_WAIT));
        boolean checkFlag = false;
        try {
            func.accept(newWait);
            checkFlag = true;
        } catch (Exception ignored) {
        }
        System.out.println("=======Element "+checkType+" is : "+checkFlag+"=======");
        return checkFlag;
    }




}
