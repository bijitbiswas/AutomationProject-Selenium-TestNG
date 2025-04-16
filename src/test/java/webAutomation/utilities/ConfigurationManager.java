package webAutomation.utilities;

import webAutomation.utilities.automationFunctions.GeneralFunction;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationManager extends GeneralFunction {

    {
        loadCapabilities();
    }

    static Properties properties;
    final public String browserName = getBrowserName();
    final Long waitTime = getWaitTime();
    final String applicationURL = getApplicationURL();


    /**
     * Below methods are the supporting methods to load the configuration from config.properties file
     */

    private void loadCapabilities() {
        println("Loading configuration from config.properties");
        File src = new File("config" + File.separator + "config.properties");
        try {
            FileInputStream fis = new FileInputStream(src);
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
            println("Exception message : " + e.getMessage());
        }
        println("Configuration loaded successfully");
    }

    private String getBrowserName() {
        assert properties != null;
        return properties.getProperty("BrowserName");
    }

    private Long getWaitTime() {
        assert properties != null;
        return Long.parseLong(properties.getProperty("WaitTime"));
    }

    private String getApplicationURL() {
        assert properties != null;
        return properties.getProperty("applicationURL");
    }

}
