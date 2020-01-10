package de.fromAtoB.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a page with search result
 */
public class SearchResultPage extends BasePage{

    private By tripCard = By.className("search-trip-card");
    private By searchLoader = By.id("SpinnerBar");

    SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public SearchFilterWidget getSearchFilter(){
        return new SearchFilterWidget(driver);
    }

    public SearchResultPage waitSearchResultIsLoaded(){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(searchLoader));
        return this;
    }

    public List<TripCardWidget> getListOfSuggestedTrips(){
        return driver.findElements(tripCard).stream().map(TripCardWidget::new).collect(Collectors.toList());
    }

    /**
     * choose the first trip in the search result from particular transport company
     * @param companyName
     * @return OverviewPage
     */
    public OverviewPage selectTripOfTransportCompany(String companyName){
        List<TripCardWidget> suggestedTrips = getListOfSuggestedTrips();
        TripCardWidget selectedTrip =  suggestedTrips.stream().filter(card->card.getTransportCompany().equalsIgnoreCase(companyName)).findFirst().orElse(null);
        if (selectedTrip != null){
            selectedTrip.clickSelectTripButton();
            return new OverviewPage(driver);
        } else {
            throw new NotFoundException("Company " + companyName + "is not found in result");
        }
    }

}
