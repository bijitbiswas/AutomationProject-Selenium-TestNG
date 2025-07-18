package webAutomation.utilities.automationFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webAutomation.utilities.ContextManager;
import webAutomation.utilities.automationInterfaces.InteractionInterface;

public class InteractionFunction extends GeneralFunction implements InteractionInterface  {

    private static final Logger logger = LoggerFactory.getLogger(InteractionFunction.class);

    WebDriver webDriver;
    WebDriverWait wait;
    FluentWait<WebDriver> fluentWait;

    public InteractionFunction(ContextManager context) {
        this.webDriver = context.webDriver;
        this.wait = context.wait;
        this.fluentWait = context.fluentWait;
    }


    @Override
    public void click(WebElement element) {
        String elementName = getElementName(element);
        WebElement actionElement;
        try {
            actionElement = fluentWait.until(ExpectedConditions.refreshed(ExpectedConditions
                    .visibilityOf(element)));
        } catch (Exception e){
            println("Failed to find element "+elementName+" to click======");
            logger.error("Failed to find element to click", e);
            throw e;
        }
        actionElement.click();
        println("Element "+elementName+" clicked");
    }

    @Override
    public void clickById(String elementId) {
        WebElement actionElement;
        try {
            actionElement = fluentWait.until(ExpectedConditions.refreshed(ExpectedConditions
                    .visibilityOfElementLocated(By.id(elementId))));
        } catch (TimeoutException e) {
            logger.error("Failed to find element "+elementId+" to click", e);
            throw e;
        }
        actionElement.click();
        println("Element id : "+elementId+" clicked");
    }

    @Override
    public void clickByXpath(String elementXpath) {
        WebElement actionElement;
        try {
            actionElement = fluentWait.until(ExpectedConditions.refreshed(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(elementXpath))));
        } catch (TimeoutException e) {
            logger.error("Failed to find element "+elementXpath+" to click", e);
            throw e;
        }
        actionElement.click();
        println("Element xpath : "+elementXpath+" clicked");
    }

    @Override
    public void clickByText(String elementText) {
        WebElement actionElement;
        try {
            actionElement = fluentWait.until(ExpectedConditions.refreshed(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//*[text()='" + elementText + "']"))));
        } catch (TimeoutException e) {
            logger.error("Failed to find element "+elementText+" to click", e);
            throw e;
        }
        actionElement.click();
        println("Element text : "+elementText+" clicked");
    }

    @Override
    public void type(WebElement element, String text) {
        String elementName = getElementName(element);
        WebElement actionElement;
        try {
            actionElement = fluentWait.until(ExpectedConditions.refreshed(ExpectedConditions
                    .visibilityOf(element)));
        } catch (TimeoutException e) {
            logger.error("Failed to find element "+elementName+" to type", e);
            throw e;
        }
        actionElement.clear();
        actionElement.sendKeys(text);
        println("Text "+text+" typed on element " +elementName);
    }

    @Override
    public void typeById(String elementId, String text) {
        WebElement actionElement;
        try {
            actionElement = fluentWait.until(ExpectedConditions.refreshed(ExpectedConditions
                    .visibilityOfElementLocated(By.id(elementId))));
        } catch (TimeoutException e) {
            logger.error("Failed to find element "+elementId+" to type by id", e);
            throw e;
        }
        actionElement.clear();
        actionElement.sendKeys(text);
        println("Text "+text+" typed on element " +elementId+ " by id");
    }

    
}
