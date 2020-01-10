package de.fromAtoB.login;


import de.fromAtoB.base.BaseTest;
import de.fromAtoB.constants.Constants;
import de.fromAtoB.pages.HomePage;
import de.fromAtoB.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@Feature("Login")
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @DataProvider(name = "wrong-credentials")
    public Object[][] createData() {
        String fakeEmail = testProperties.getProperty(Constants.FAKE_USER_EMAIL);
        String fakePassword = testProperties.getProperty(Constants.FAKE_USER_PASSWORD);
        String userEmail = testProperties.getProperty(Constants.USER_EMAIL);
        return new Object[][] { {fakeEmail, fakePassword}, {userEmail, fakePassword} };
    }

    @BeforeMethod
    public void openHomePage() {
        homePage.open(baseUrl);
    }

    @Test(dataProvider = "wrong-credentials", description = "Not registered user tries to login")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWithWrongCredentials(String email, String password){
        loginWith(email, password);
        String errorMessage = langProperties.getProperty(Constants.Translations.LOGIN_ERROR_MESSAGE);
        assertErrorMessageIsValid(errorMessage);
    }

    @Step("User logs in with credentials")
    public void loginWith(String username, String password){
        loginPage = homePage.goToLoginPage();
        loginPage.loginWithCredentials(username, password);
    }

    @Step("User should see error message")
    public void assertErrorMessageIsValid(String message){
        assertThat("Wrong error message", loginPage.getErrorMessage(), is(message));
    }

}
