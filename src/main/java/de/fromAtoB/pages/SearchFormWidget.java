package de.fromAtoB.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDate;

/**
 * SearchFormWidget class represents Search Form
 */
public class SearchFormWidget {

    private static final int PAUSE = 500;
    private WebDriver driver;
    private By departureField = By.cssSelector("input[class*=departure]");
    private By arrivalField = By.cssSelector("input[class*=arrival]");
    private By searchButton = By.cssSelector("button[type=submit]");
    private By calendarNextButton = By.className("calendar__next");
    private By calendarStartDate = By.className("ssc-dates__start");
    private By calendarEndDate = By.className("ssc-dates__end");
    private By partnerCheckbox = By.className("ssc-db__checkbox");

    public SearchFormWidget(WebDriver driver) {
        this.driver = driver;
    }

    public SearchFormWidget inputDepartureStop(String departureStop){
        Actions action = new Actions(driver);
        WebElement dep = driver.findElement(departureField);
        action.sendKeys(dep, departureStop).pause(PAUSE).sendKeys(Keys.ENTER).perform();
        return this;
    }

    public SearchFormWidget inputArrivalStop(String arrivalStop){
        Actions action = new Actions(driver);
        WebElement dep = driver.findElement(arrivalField);
        action.sendKeys(dep, arrivalStop).pause(PAUSE).sendKeys(Keys.ENTER).perform();
        return this;
    }

    public SearchFormWidget inputArrivalDate(int daysFromToday){
        LocalDate targetDate = LocalDate.now().plusDays(daysFromToday);
        driver.findElement(calendarStartDate).click();
        WebElement targetDay = driver.findElement(By.className(generateClassNameForDatesInCalendar(targetDate)));
        targetDay.click();
        return this;
    }

    public SearchResultPage search() {
        driver.findElement(partnerCheckbox).click();
        driver.findElement(searchButton).click();
        // work around in case partner checkbox is not disabled
        //List<String> handles = new ArrayList(driver.getWindowHandles());
        //driver.switchTo().window(handles.get(1));
        return new SearchResultPage(driver).waitSearchResultIsLoaded();
    }

    /**
     * method to generate classname from given date in format day-2020-1-01
     * classname is used by day items of calendar
     * @param targetDate
     * @return String
     */
    private String generateClassNameForDatesInCalendar(LocalDate targetDate){
        int year = targetDate.getYear();
        int month =  targetDate.getMonthValue();
        int day = targetDate.getDayOfMonth();
        StringBuilder className = new StringBuilder("day-");
        className.append(year).append('-').append(month).append("-");
        if (day < 10 ){
            className.append(0);
        }
        className.append(day);
        return className.toString();
    }
}
