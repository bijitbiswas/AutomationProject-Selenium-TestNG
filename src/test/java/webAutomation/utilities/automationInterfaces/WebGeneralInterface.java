package webAutomation.utilities.automationInterfaces;

public interface WebGeneralInterface {

    void navigateBack();

    String getBrowserName();

    String getCurrentURL();

    void sleep(int timeInSecs);

    void println(String message);

}
