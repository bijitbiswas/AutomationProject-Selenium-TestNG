package webAutomation.utilities;

import webAutomation.actionUtilities.automationFunctions.GeneralFunction;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager extends GeneralFunction {

    {
        loadConfigurations();
    }

    static Properties properties;
    final public String browserName = getBrowserName();
    final Long waitTime = getWaitTime();
    final String applicationURL = getApplicationURL();
    final boolean isJenkinsRun = getIsJenkinsRun();
    final boolean isHeadless = getIsHeadless();

    // Resolution order (highest priority wins):
    //   1. JVM system property   -DKey=value
    //   2. Environment variable  KEY_NAME=value  (camelCase → UPPER_SNAKE_CASE)
    //   3. config.properties     on the test classpath (src/test/resources/)
    //   4. default.config.properties  bundled in the framework JAR
    private String resolve(String key) {
        String sysProp = System.getProperty(key);
        if (sysProp != null) return sysProp;

        String envVal = System.getenv(toEnvVarName(key));
        if (envVal != null) return envVal;

        return properties.getProperty(key);
    }

    // BrowserName → BROWSER_NAME,  ApplicationURL → APPLICATION_URL
    private String toEnvVarName(String key) {
        return key.replaceAll("(?<=[a-z])(?=[A-Z])", "_").toUpperCase();
    }

    private void loadConfigurations() {
        properties = new Properties();

        boolean userConfigExists = ConfigurationManager.class.getResource("/config.properties") != null;

        if (userConfigExists) {
            // Option 1: load user's classpath config.properties
            try (InputStream userClasspath = ConfigurationManager.class.getResourceAsStream("/config.properties")) {
                properties.load(userClasspath);
                println("Loaded config.properties from classpath");
            } catch (Exception e) {
                println("Could not load classpath config.properties: " + e.getMessage());
            }
        } else {
            // Option 2: load bundled defaults (lowest priority) — no user config found
            try (InputStream defaults = ConfigurationManager.class.getResourceAsStream("/default.config.properties")) {
                if (defaults != null) {
                    properties.load(defaults);
                    println("No config.properties found in src/test/resources. Loaded defaults from JAR. To OVERRIDE, CREATE src/test/resources/config.properties in your project with the following sample:\n" +
                            "  #BrowserName values can be Chrome, Firefox, Edge, Safari\n" +
                            "  BrowserName = Firefox\n" +
                            "  ApplicationURL = https://www.saucedemo.com/\n" +
                            "  WaitTime = 10");
                }
            } catch (Exception e) {
                println("Could not load default config: " + e.getMessage());
            }
        }
    }

    private String getBrowserName() {
        return resolve("BrowserName");
    }

    private Long getWaitTime() {
        return Long.parseLong(resolve("WaitTime"));
    }

    private String getApplicationURL() {
        String url = resolve("ApplicationURL");
        if (url == null || url.isBlank()) {
            throw new IllegalStateException(
                "ApplicationURL is not configured. Provide it via one of:\n" +
                "  1. src/test/resources/config.properties  →  ApplicationURL = https://your-app.com\n" +
                "  2. System property                       →  -DApplicationURL=https://your-app.com\n" +
                "  3. Environment variable                  →  APPLICATION_URL=https://your-app.com"
            );
        }
        return url;
    }

    private boolean getIsJenkinsRun() {
        return Boolean.parseBoolean(resolve("IsJenkinsRun"));
    }

    private boolean getIsHeadless() {
        return Boolean.parseBoolean(resolve("Headless"));
    }
}
