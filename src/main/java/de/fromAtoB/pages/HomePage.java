package de.fromAtoB.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HomePage class presents home page
 */
public class HomePage extends BasePage{

    private static final String URL = "/";
    private Menu menu;
    private By acceptCookiesButton = By.className("cc-dismiss");
    private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);


    public HomePage(WebDriver driver){
        super(driver);
        // the first step for dependency injection
        menu = new HeaderNavigationMenu(driver);
    }

    public LoginPage goToLoginPage(){
        return menu.clickLoginButton();
    }

    /**
     * get Search Form Container
     */
    public SearchFormWidget getSearchForm(){
        return new SearchFormWidget(driver);
    }

    /**
     * accept Cookies on page
     */
    public void acceptCookies(){
        try {
            WebElement acceptButton = waitVisibilityOfElementLocated(acceptCookiesButton);
            acceptButton.click();
        }catch (TimeoutException e){
            LOGGER.warn("Cookies were not found on page");
        }
    }

}
