package webAutomation.utilities.automationInterfaces;

public interface WebGeneralInterface {

    /**
     * Navigates the browser back to the previous page in the session history.
     */
    void navigateBack();

    /**
     * Returns the name of the browser currently in use (e.g. "Chrome", "Firefox").
     *
     * @return the browser name as configured in the test context
     */
    String getBrowserName();

    /**
     * Returns the URL of the page currently loaded in the browser.
     *
     * @return the current page URL as a String
     */
    String getCurrentURL();

    /**
     * Pauses test execution for the specified number of seconds.
     * Prefer explicit waits over this method where possible.
     *
     * @param timeInSecs the number of seconds to sleep
     */
    void sleep(int timeInSecs);

    /**
     * Prints a message to the standard output with a timestamp or test context prefix.
     *
     * @param message the message to print
     */
    void println(String message);

}
