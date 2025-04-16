package webAutomation.utilities.automationFunctions;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralFunction {

    private static final Logger logger = LoggerFactory.getLogger(ValidationFunction.class);

    String getElementName(WebElement element) {
        String elementString = element.toString();
        String elementName = "";
        if (elementString.contains("->")) {
            String elementStringNext = elementString.split("->")[1];
            int indexOfBrace = elementStringNext.lastIndexOf("]");
            if (indexOfBrace != -1)
                elementName = elementStringNext.substring(0, indexOfBrace).trim();
            else
                elementName = elementStringNext;
        } else if (elementString.contains("By.")) {
            elementName = elementString.split("By.")[1];
        }
        return elementName;
    }

    public void println(String message) {
        System.out.println("========"+message+"========");
    }

}
