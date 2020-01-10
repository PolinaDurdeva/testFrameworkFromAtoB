package de.fromAtoB.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * OverviewPage class represents overview page of chosen trip
 */
public class OverviewPage extends BasePage{

    private static final String URL = "/overview";
    private By checkoutContainerDiv = By.className("Checkout");

    OverviewPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnCheckoutOverviewPage(){
        WebElement checkoutContainer = null;
        try {
            checkoutContainer =  wait.until(ExpectedConditions.presenceOfElementLocated(checkoutContainerDiv));
        } catch (TimeoutException e){
            e.printStackTrace();
        }
        return checkoutContainer != null;
    }
}
