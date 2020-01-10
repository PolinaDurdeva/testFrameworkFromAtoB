package de.fromAtoB.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Represents a container of the result trip
 */
public class TripCardWidget {

    private WebElement webElement;
    private By selectButton = By.className("search-trip-card-button__cta");
    private By logoImage = By.className("logos__img");

    public TripCardWidget(WebElement webElement) {
        this.webElement = webElement;
    }

    public String getTransportCompany() {
       return webElement.findElement(logoImage).getAttribute("aria-label");
    }

    public void clickSelectTripButton(){
        webElement.findElement(selectButton).click();
    }
}
