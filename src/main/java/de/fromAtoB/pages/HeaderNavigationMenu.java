package de.fromAtoB.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * HeaderNavigationMenu class represents top menu navigation
 */
public class HeaderNavigationMenu implements Menu{

    private WebDriver driver;
    private By loginButton = By.id("sign-in-button");

    public HeaderNavigationMenu(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage clickLoginButton(){
        driver.findElement(loginButton).click();
        return new LoginPage(driver);
    }
}
