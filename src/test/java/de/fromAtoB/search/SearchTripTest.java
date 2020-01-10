package de.fromAtoB.search;

import de.fromAtoB.base.BaseTest;
import de.fromAtoB.constants.Constants;
import de.fromAtoB.pages.*;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


public class SearchTripTest extends BaseTest {

    private SearchFormWidget searchForm;
    private SearchResultPage searchResultPage;
    private OverviewPage overviewPage;

    @BeforeMethod
    public void openHomePage() {
        homePage.open(baseUrl);
        searchForm = homePage.getSearchForm();
    }

    @DataProvider(name = "trip-inside-germany-in-n-days")
    public Object[][] createData() {
        int departure = Integer.parseInt(testProperties.getProperty(Constants.DEPARTURE_DATE_IN_DAYS));
        String arrivalStop = testProperties.getProperty(Constants.ARRIVAL_STOP);
        String departureStop = testProperties.getProperty(Constants.DEPARTURE_STOP);
        String transportCompany = Constants.CompanyNames.DB;
        return new Object[][] { {arrivalStop, departureStop, departure, transportCompany}};
    }

    @Test(dataProvider = "trip-inside-germany-in-n-days",
            description = "User search for shortest trip from {arrivalStop} to {departureStop} in {days} days by train with {transportCompany} company")
    public void testShortestTripFromDB(String arrivalStop, String departureStop, int days, String transportCompany){
        inputDestinations(arrivalStop, departureStop);
        inputDates(days);
        makeSearch();
        filterSearchResult();
        selectFirstTrip(transportCompany);
        assertUserIsOnOverviewPage();
    }

    @Step("User searches for trip from {arrivalStop} to {departureStop}")
    private void inputDestinations(String arrivalStop, String departureStop){
        searchForm.inputDepartureStop(departureStop).inputArrivalStop(arrivalStop);
    }

    @Step("User selects trip dates which is {0} from current date")
    private void inputDates(int daysFromToday){
        searchForm.inputArrivalDate(daysFromToday);
    }

    @Step("User gets search result")
    private void makeSearch(){
        searchResultPage = searchForm.search();
    }

    @Step("User selects only trains and sorting by duration")
    private void filterSearchResult(){
        searchResultPage.
                getSearchFilter().
                sortBy(SearchFilterWidget.SearchResultOrder.DURATION).
                deselectBus().
                deselectCar().
                deselectPlane();
        searchResultPage.waitSearchResultIsLoaded();
    }

    @Step("User selects the first trip option for {company}")
    private void selectFirstTrip(String company){
        overviewPage = searchResultPage.selectTripOfTransportCompany(company);
    }

    @Step("Assert user is on Overview Page")
    private void assertUserIsOnOverviewPage(){
        assertTrue(overviewPage.isOnCheckoutOverviewPage(), "Checkout overview page was expected");
    }
}
