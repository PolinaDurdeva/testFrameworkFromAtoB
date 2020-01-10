package de.fromAtoB.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * LoginPage class represents login page
 */
public class LoginPage extends BasePage {

    private By emailField = By.name("user-reception-email");
    private By passwordField = By.name("user-reception-password");
    private By submitButton = By.cssSelector("button[type=submit]");
    private By errorAlert = By.cssSelector(".separator  + .user-reception__error-box");

    LoginPage(WebDriver driver) {
        super(driver);
    }

    public void inputEmail(String email){
        driver.findElement(emailField).sendKeys(email);
    }

    public void inputPassword(String password){
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton(){
        driver.findElement(submitButton).click();
    }

    public void loginWithCredentials(String email, String password){
        inputEmail(email);
        inputPassword(password);
        clickLoginButton();
    }

    /**
     * get error message that displayed in case of wrong credentials
     * @return String or null
     */
    public String getErrorMessage(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(errorAlert));
        if (element != null){
            return element.getText();
        }
        return null;
    }

}
