package de.fromAtoB.base;

import de.fromAtoB.constants.Constants;
import de.fromAtoB.pages.HomePage;
import de.fromAtoB.driver.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.InputStream;
import java.util.Properties;

public class BaseTest {

    protected static Properties testProperties;
    protected static Properties langProperties;
    protected WebDriver webDriver;
    protected static String gridHubUrl;
    protected static String baseUrl;
    protected HomePage homePage;
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    @Parameters({ "environment", "language" })
    @BeforeSuite(alwaysRun = true)
    public void setUpEnvironment(@Optional("prod") String env, @Optional("en") String language){
        loadTestProperties(env);
        loadLanguageProperties(language);
        setUpConfig();
    }

    @Parameters({ "browser", "remote" })
    @BeforeClass
    public void setUp(@Optional("chrome") String browser, @Optional("0") String remote) {
        LOGGER.info("Init new webDriver");
        if (remote.equals("1")){
            webDriver = DriverFactory.setUpNewRemoteDriver(gridHubUrl, browser);
        } else {
            webDriver = DriverFactory.setUpNewDriver(browser);
        }
        homePage = new HomePage(webDriver);
        homePage.open(baseUrl);
        homePage.acceptCookies();
    }

    @AfterClass
    public void shutDown() {
        LOGGER.info("Quit the driver");
        if (webDriver != null){
            webDriver.quit();
        }
    }

    /**
     * generate screenshot on failed test
     */
    @Attachment(value = "test screenshots on failed test", type = "image/png")
    @AfterMethod
    public byte[] recordFailed(ITestResult result){
        if (ITestResult.FAILURE == result.getStatus()) {
            var camera = (TakesScreenshot) webDriver;
            return camera.getScreenshotAs(OutputType.BYTES);
        }
        return null;
    }

    /**
     * load properties of a environment specific tests from resource file {env}.properties
     */
    private static void loadTestProperties(String env){
        try (InputStream resource = BaseTest.class.getClassLoader().getResourceAsStream(env + ".properties")) {
            testProperties = new Properties();
            if (resource != null) {
                testProperties.load(resource);
            } else {
                LOGGER.error("Environment properties are not found");
                System.exit(1);
            }
        } catch (Exception e){
            LOGGER.error("Error during loading environment properties");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * load properties of a language specific text elements from resource file {language}.properties
     */
    private static void loadLanguageProperties(String language){
        try (InputStream resource = BaseTest.class.getClassLoader().getResourceAsStream(language + ".properties")) {
            langProperties = new Properties();
            if (resource != null) {
                langProperties.load(resource);
            } else {
                LOGGER.error("Language properties are not found");
                System.exit(1);
            }
        } catch (Exception e){
            LOGGER.error("Error during loading language properties");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void setUpConfig(){
        baseUrl = testProperties.getProperty(Constants.BASE_URL);
        gridHubUrl = testProperties.getProperty(Constants.GRID_URL);
    }
}
