package de.fromAtoB.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchFilterWidget class represents different filters on SearchResultPage
 */
public class SearchFilterWidget extends BasePage{

    private By filterPlaneCheckbox = By.cssSelector("label[for=filter-plane]");
    private By filterBusCheckbox = By.cssSelector("label[for=filter-bus]");
    private By filterCarCheckbox = By.cssSelector("label[for=filter-ride_share]");
    private By filterTrainCheckbox = By.cssSelector("label[for=filter-train]");
    private By sortToggle = By.className("selected-sort-type");
    private By sortOptions = By.cssSelector("li.sort-list-item");

    public SearchFilterWidget(WebDriver driver) {
        super(driver);
    }

    public SearchFilterWidget deselectTrain(){
        waitAndClick(filterTrainCheckbox);
        return this;
    }

    public SearchFilterWidget deselectCar(){
        waitAndClick(filterCarCheckbox);
        return this;
    }

    public SearchFilterWidget deselectBus(){
        waitAndClick(filterBusCheckbox);
        return this;
    }

    public SearchFilterWidget deselectPlane(){
        waitAndClick(filterPlaneCheckbox);
        return this;
    }
    /**
     * SearchResultOrder map sorting criteria to index of element on the page
     */
    public enum SearchResultOrder {
        PRICE (0),
        DURATION (1);

        private int code;

        SearchResultOrder(int code) {
            this.code = code;
        }
    }

    public SearchFilterWidget sortBy(SearchResultOrder orderBy){
        waitAndClick(sortToggle);
        List<WebElement> options = new ArrayList(driver.findElements(sortOptions));
        options.get(orderBy.code).click();
        return this;
    }

    private void waitAndClick(By filter) {
        waitVisibilityOfElementLocated(filter).click();
    }

}
