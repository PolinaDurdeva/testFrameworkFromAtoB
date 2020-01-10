package de.fromAtoB.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a page which can perform
 * some  common for all other application pages actions using web driver
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 15;
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

    BasePage(WebDriver driver) {
        LOGGER.info("Init Base Page");
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void open(String URL) {
        driver.get(URL);
    }

    /**
     * wait for visibility of element
     */
    public WebElement waitVisibilityOfElementLocated(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}
