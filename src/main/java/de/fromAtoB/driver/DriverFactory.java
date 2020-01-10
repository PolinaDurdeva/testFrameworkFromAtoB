package de.fromAtoB.driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;


public class DriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverFactory.class);
    private static final String CHROME = "chrome";
    private static final String FIREFOX = "firefox";

    /**
     * Create new remote driver instance
     */
    public static synchronized WebDriver setUpNewRemoteDriver(String gridUrl, String browserName) {
        DesiredCapabilities capability = setUpCapabilities(browserName);
        try {
            WebDriver driver = new RemoteWebDriver(new URL(gridUrl), capability);
            driver.manage().window().fullscreen();
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Not able to launch remote driver");
            throw new IllegalStateException("Not able to launch remote driver");
        }
    }

    /**
     * Create new local driver instance
     */
    public static synchronized WebDriver setUpNewDriver(String browserName) {
        WebDriver driver = null;
        if (browserName.equals(CHROME)){
            driver = new ChromeDriver();
        } else {
            throw new IllegalArgumentException("No configuration for the driver of" + browserName);
        }
        driver.manage().window().fullscreen();
        //driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        return driver;
    }

    /**
     * load capabilities of a browser from resource file {browser-name}.capabilities
     */
    private static DesiredCapabilities setUpCapabilities(String browser){
        try (InputStream resource = DriverFactory.class.getClassLoader().getResourceAsStream(browser + ".capabilities")) {
            Properties properties = new Properties();
            properties.load(resource);
            Map<String, String> propertiesAsMap = properties.keySet().stream().map(String::valueOf).collect(toMap(Function.identity(), properties::getProperty));
            DesiredCapabilities caps = new DesiredCapabilities(propertiesAsMap);
            LOGGER.info("Capabilities for browser: " + browser + "\n" + caps.asMap());
            return caps;
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Capabilities not found for " + browser);
            return null;
        }
    }
}

